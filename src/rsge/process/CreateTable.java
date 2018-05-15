/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MColumn;
import org.compiere.model.MReference;
import org.compiere.model.MTable;
import org.compiere.model.M_Element;
import org.compiere.model.X_AD_Column;
import org.compiere.model.X_AD_Reference;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * Create Initial Columns
 * @author Fanny
 *
 */
public class CreateTable extends SvrProcess {

	/** Table Name					*/
	private String				p_tableName;
	/** Name						*/
	private String				p_name;
	/** Table Type					*/
	private String				p_tableType;
	/** Base Table 					*/
	private int					p_baseTable;
	/** Parent Key					*/
	private String				p_ParentKey;
	/** Access Level				*/
	private String				p_AccessLevel;
	/** Transaction Type			*/
	private String				p_TableTrxType;
	/** Records Deletable			*/
	private boolean				p_IsDeleteable = false;
	/** Set Change Log				*/
	private String				p_ChangeLogLevel;
	/** Entity Type					*/
	private String				p_EntityType;

	/** Table object				*/
	private MTable 				table = null;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null && element.getParameter_To() == null)
				;
			else if (name.equals("TableName"))
				p_tableName = (String) element.getParameter();
			else if (name.equals("Name"))
				p_name = (String) element.getParameter();
			else if (name.equals("TableType"))
				p_tableType = (String)element.getParameter();
			else if (name.equals("BaseTable_ID"))
				p_baseTable = element.getParameterAsInt();
			else if (name.equals("ParentKey"))
				p_ParentKey = (String)element.getParameter();
			else if (name.equals("AccessLevel"))
				p_AccessLevel = (String) element.getParameter();
			else if (name.equals("TableTrxType"))
				p_TableTrxType = (String) element.getParameter();
			else if (name.equals("IsDeleteable"))
				p_IsDeleteable = "Y".equals(element.getParameter());
			else if (name.equals("ChangeLogLevel"))
				p_ChangeLogLevel = (String) element.getParameter();
			else if (name.equals("EntityType"))
				p_EntityType = (String) element.getParameter();
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// Check Table Name prefix
		StringBuilder prefix = new StringBuilder();
		String c;
		for (int i = 0; i < p_tableName.length(); i++)
		{
			c = p_tableName.substring(i, i+1);
			prefix.append(c);
			if(c.equalsIgnoreCase("_"))
				break;
		}
		
		String tablePrefix = prefix.toString();
		
		if(p_tableType.equalsIgnoreCase("07") || p_tableType.equalsIgnoreCase("08")) // Child Table
		{
			if(p_ParentKey == null)
				return "Child Table must have Parent Key. Process cancel";
			if(getColumnID(p_ParentKey)==0)
				return "No parent key column found in the system. Process cancel";
			
		}
		else if(p_tableType.equalsIgnoreCase("09")) // Import Table
		{
			if(!tablePrefix.equalsIgnoreCase("I_"))
				return "Import Table name can only have I_ as prefix. Process cancel";
		}
		else if(p_tableType.equalsIgnoreCase("10")) // Temp Table
		{
			if(!tablePrefix.equalsIgnoreCase("T_"))
				return "Temporary Table name can only have T_ as prefix. Process cancel";
		}
		else
		{
			// Check prefix. Must not use AD_, C_, CM_, GL_, K_, M_, MRP_, PA_, R_, S_, W_
			if(tablePrefix.equalsIgnoreCase("AD_") || tablePrefix.equalsIgnoreCase("C_") || tablePrefix.equalsIgnoreCase("CM_")
					|| tablePrefix.equalsIgnoreCase("GL_") || tablePrefix.equalsIgnoreCase("K_") || tablePrefix.equalsIgnoreCase("M_")
					|| tablePrefix.equalsIgnoreCase("MRP_") || tablePrefix.equalsIgnoreCase("PA_") || tablePrefix.equalsIgnoreCase("R_")
					|| tablePrefix.equalsIgnoreCase("S_") || tablePrefix.equalsIgnoreCase("W_"))
			{
				return "Table name must not use AD, C, CM, GL, K, M, MRP, PA, R, S, W as prefix";
			}
			// Check prefix digit. Must not exceed 4 digit (include _)
			if(tablePrefix.length()>4)
			{
				return "Table name prefix length must not exceed 4 characters";
			}
		}
		
		// Check if table name is already exists
		String sql = "SELECT 1 " +
				"FROM AD_Table " +
				"WHERE TableName = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setString(1, p_tableName);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				return "Table already exists. Process cancel";
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		// Create new Table
		table = new MTable(getCtx(), 0, get_Trx());
		table.setTableName(p_tableName);
		table.setName(p_name);
		table.setAccessLevel(p_AccessLevel);
		table.setTableTrxType(p_TableTrxType);
		table.setIsDeleteable(p_IsDeleteable);
		table.setChangeLogLevel(p_ChangeLogLevel);
		table.setEntityType(p_EntityType);
		table.save();
		
		// Primary Key
		// Name format Table Name + _ID
		// If table is join table, skip this column
		if(!p_tableType.equalsIgnoreCase("04")) // Not join table -> Regular table
		{
			// Get Table Name
			StringBuilder pk = new StringBuilder(p_tableName);
			// Add _ID to table name
			pk.append("_ID");
			
			// Check if PK column exists in the table
			if(!checkColumn(pk.toString(), table.getTableName()))
			{
				int key = getColumnID(pk.toString());
				if(!createColumn(key, true, false))
					log.saveError("Cannot save PK", "Cannot save PK");
			}
			else 
			{
				log.fine(pk + " is already exists. Skip " + pk);
			}
		}
		else
		{
			log.fine("Table is join table. Skip primary key");
		}
		if(p_tableType.equalsIgnoreCase("01")||p_tableType.equalsIgnoreCase("02")) // Value
		{
			// Create Value
			if(!createColumn(getColumnID("Value"), false, true))
				log.saveError("Cannot save Value", "Cannot save Value");				
			// Create Description
			if(!createColumn(getColumnID("Description"), false, false))
				log.saveError("Cannot save Description", "Cannot save Description");			

		}
		if(p_tableType.equalsIgnoreCase("01")) // Name not mandatory
		{
			// Create Name
			if(!createColumn(getColumnID("Name"), false, false))
				log.saveError("Cannot save Name", "Cannot save Name");				
			// Create Description
			if(!createColumn(getColumnID("Description"), false, false))
				log.saveError("Cannot save Description", "Cannot save Description");			

		}
		if(p_tableType.equalsIgnoreCase("03")) // Name is mandatory
		{
			// Create Name
			if(!createColumn(getColumnID("Name"), false, true))
				log.saveError("Cannot save Name", "Cannot save Name");				
			// Create Description
			if(!createColumn(getColumnID("Description"), false, false))
				log.saveError("Cannot save Description", "Cannot save Description");			
		}
		if(p_tableType.equalsIgnoreCase("07") || p_tableType.equalsIgnoreCase("08")) // Child Table
		{
			// Create Parent column
			if(!createColumn(getColumnID(p_ParentKey), false, false))
			{
				log.saveError("Cannot save Parent Key", "Cannot save Parent Key");
			}
			if(p_tableType.equalsIgnoreCase("08")) // child table with sequence
			{
				// Create line sequence column
				if(!createLineColumn(p_ParentKey))
					log.saveError("Cannot save Parent Key", "Cannot save Parent Key");
			}
		}

		if(!p_tableType.equalsIgnoreCase("10")) // Not temporary table
		{
			// Create AD_Client_ID 
			if(!createColumn(getColumnID("AD_Client_ID"), false, false))
				log.saveError("Cannot save AD_Client_ID", "Cannot save AD_Client_ID");

			// Create AD_Org_ID
			if(!createColumn(getColumnID("AD_Org_ID"), false, false))
				log.saveError("Cannot save AD_Org_ID", "Cannot save AD_Org_ID");
			
			// Create Created 
			if(!createColumn(getColumnID("Created"), false, false))
				log.saveError("Cannot save Created", "Cannot save Created");

			// Create CreatedBy
			if(!createColumn(getColumnID("CreatedBy"), false, false))
				log.saveError("Cannot save CreatedBy", "Cannot save CreatedBy");

			// Create Updated 
			if(!createColumn(getColumnID("Updated"), false, false))
				log.saveError("Cannot save Updated", "Cannot save Updated");

			// Create UpdatedBy
			if(!createColumn(getColumnID("UpdatedBy"), false, false))
				log.saveError("Cannot save UpdatedBy", "Cannot save UpdatedBy");

			// Create Is Active
			if(!createColumn(getColumnID("IsActive"), false, false))
				log.saveError("Cannot save IsActive", "Cannot save IsActive");

		}
		
		// Columns for import table and transaction
		if(p_tableType.equalsIgnoreCase("05") || p_tableType.equalsIgnoreCase("06") || p_tableType.equalsIgnoreCase("09"))
		{
			// Create Processed
			if(!createColumn(getColumnID("Processed"), false, false))
				log.saveError("Cannot save Processed", "Cannot save Processed");
			// Create Processing
			if(!createColumn(getColumnID("Processing"), false, false))
				log.saveError("Cannot save Processing", "Cannot save Processing");			
			
			// For Import Table only
			if(p_tableType.equalsIgnoreCase("09"))
			{
				// Create I_ErrorMsg
				if(!createColumn(getColumnID("I_ErrorMsg"), false, false))
					log.saveError("Cannot save I_ErrorMsg", "Cannot save I_ErrorMsg");
				// Create I_IsImported
				if(!createColumn(getColumnID("I_IsImported"), false, false))
					log.saveError("Cannot save I_IsImported", "Cannot save I_IsImported");								
				createImportColumn(table);
			}

		}
		// Columns for transaction table only
		if(p_tableType.equalsIgnoreCase("05") || p_tableType.equalsIgnoreCase("06"))
		{
			// Create Document No
			if(!createColumn(getColumnID("DocumentNo"), false, true))
				log.saveError("Cannot save Document No", "Cannot save Document No");
			// Create Date Doc
			if(!createColumn(getColumnID("DateDoc"), false, false))
				log.saveError("Cannot save Date Doc", "Cannot save Date Doc");			
			if(p_tableType.equalsIgnoreCase("06"))
			{
				// Create Is Approved
				if(!createColumn(getColumnID("IsApproved"), false, false))
					log.saveError("Cannot save IsApproved", "Cannot save IsApproved");			
				// Create DocAction
				if(!createColumn(getColumnID("DocAction"), false, false))
					log.saveError("Cannot save DocAction", "Cannot save DocAction");			
				// Create DocStatus
				if(!createColumn(getColumnID("DocStatus"), false, false))
					log.saveError("Cannot save DocStatus", "Cannot save DocStatus");
			}
		}
		return "Table Created";
	}
	
	private boolean createImportColumn(MTable table)
	{
		String sql = "SELECT * FROM AD_Column WHERE AD_Table_ID = ? " +
				"AND ColumnName NOT IN ('AD_Client_ID', 'AD_Org_ID', 'IsActive', 'Created', 'CreatedBy', 'Updated', 'UpdatedBy') ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, p_baseTable);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MColumn sourceColumn = new MColumn(getCtx(), rs, get_Trx());				
				MColumn column = copyColumn(sourceColumn, table);
				column.save();
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean checkColumn(String columnName, String tableName)
	{
		String sql = "SELECT 1 " +
				"FROM AD_Column col " +
				"INNER JOIN AD_Table t ON (col.AD_Table_ID = t.AD_Table_ID) " +
				"WHERE col.ColumnName = ? " +
				"AND t.TableName = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setString(1, columnName);
			pstmt.setString(2, tableName);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				return true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		return false;
	}
	
	private int getColumnID(String columnName)
	{
		int columnID = 0;
		
		String sql = "SELECT AD_Element_ID " +
				"FROM AD_Element " +
				"WHERE ColumnName =  ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setString(1, columnName);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				columnID = rs.getInt(1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		if(columnID==0) // No System Element found. Create new one
		{
			M_Element e = new M_Element(getCtx(), 0, get_Trx());
			e.setColumnName(columnName);
			e.setName(columnName);
			e.setPrintName(columnName);
			e.setEntityType(p_EntityType);
			if(e.save())
			{
				columnID = e.getAD_Element_ID();
			}
		}
		return columnID;
	}
	
	private boolean createColumn(int elementID, boolean isPrimaryKey, boolean isIdentifier)
	{
		createTableColumn(elementID, isPrimaryKey, isIdentifier);		
		return true;
	}	
	
	private MColumn createTableColumn(int elementID, boolean isPrimaryKey, boolean isIdentifier)
	{
		M_Element e = new M_Element(getCtx(), elementID, get_Trx());
		MColumn column = new MColumn(table);
		String columnName = e.getColumnName();
		// Set DB Column, System Element and Name
		column.setColumnName(columnName);
		column.setAD_Element_ID(elementID);
		column.setName(e.getName());
		column.setEntityType(p_EntityType);
		
		// Set Reference
		if(isPrimaryKey)
		{
			column.setIsKey(true);
			column.setFieldLength(10);
			column.setAD_Reference_ID(getReference("ID"));
			column.setIsCopy(true);
		}
		else if(columnName.equalsIgnoreCase("AD_Client_ID"))
		{
			column.setFieldLength(10);
			column.setAD_Reference_ID(getReference("Table Direct"));
			column.setAD_Val_Rule_ID(getValidation("AD_Client Login"));
			column.setDefaultValue("@#AD_Client_ID@");
			column.setIsUpdateable(false);
			column.setConstraintType(X_AD_Column.CONSTRAINTTYPE_Restrict);			
			column.setIsMandatory(true);
			column.setIsMandatoryUI(true);
			column.setIsCopy(true);
		}
		else if(columnName.equalsIgnoreCase("AD_Org_ID"))
		{
			column.setFieldLength(10);
			column.setAD_Reference_ID(getReference("Table Direct"));
			column.setAD_Val_Rule_ID(getValidation("AD_Org Security validation"));
			if(p_tableType.equalsIgnoreCase("07") || p_tableType.equalsIgnoreCase("08")) // Child Table
				column.setDefaultValue("@AD_Org_ID@");
			else 
				column.setDefaultValue("@#AD_Org_ID@");
			column.setIsUpdateable(false);
			column.setConstraintType(X_AD_Column.CONSTRAINTTYPE_Restrict);			
			column.setIsMandatory(true);
			column.setIsMandatoryUI(true);
			column.setIsCopy(true);
		}
		else if(columnName.equalsIgnoreCase("Created") 
				|| columnName.equalsIgnoreCase("Updated"))
		{
			column.setFieldLength(7);
			column.setAD_Reference_ID(getReference("Date+Time"));		
			column.setIsMandatory(true);
			column.setIsCopy(true);
		}
		else if(columnName.equalsIgnoreCase("CreatedBy") 
				|| columnName.equalsIgnoreCase("UpdatedBy"))
		{
			column.setFieldLength(10);
			column.setAD_Reference_ID(getReference("Table"));
			column.setAD_Reference_Value_ID(getReference("AD_User"));
			column.setConstraintType(X_AD_Column.CONSTRAINTTYPE_DoNOTCreate);
			column.setIsMandatory(true);
			column.setIsCopy(true);
		}
		else if(columnName.equalsIgnoreCase("Description"))
		{
			column.setFieldLength(255);
			column.setAD_Reference_ID(getReference("String"));
		}

		else if(columnName.equalsIgnoreCase("Name"))
		{
			column.setFieldLength(60);
			column.setAD_Reference_ID(getReference("String"));
			column.setIsMandatory(true);
			column.setIsMandatoryUI(true);
			column.setIsIdentifier(isIdentifier);
		}
		else if(columnName.equalsIgnoreCase("Value"))
		{
			column.setFieldLength(40);
			column.setAD_Reference_ID(getReference("String"));
			column.setIsMandatory(true);
			column.setIsMandatoryUI(true);
			column.setIsIdentifier(isIdentifier);
		}
		else if(columnName.equalsIgnoreCase("DocAction"))
		{
			column.setFieldLength(2);
			column.setAD_Reference_ID(getReference("Button"));
			column.setAD_Reference_Value_ID(getReference("_Document Action"));
			column.setDefaultValue("CO");
		}				
		else if(columnName.equalsIgnoreCase("DocStatus"))
		{
			column.setFieldLength(2);
			column.setAD_Reference_ID(getReference("List"));
			column.setAD_Reference_Value_ID(getReference("_Document Status"));
			column.setDefaultValue("DR");
		}				
		else if(columnName.equalsIgnoreCase("IsActive"))
		{
			column.setFieldLength(1);
			column.setAD_Reference_ID(getReference("Yes-No"));
			column.setIsMandatory(true);
			column.setIsMandatoryUI(true);
			column.setDefaultValue("Y");
		}		
		else if(columnName.equalsIgnoreCase("I_ErrorMsg"))
		{
			column.setFieldLength(2000);
			column.setAD_Reference_ID(getReference("String"));
		}
		else if(columnName.equalsIgnoreCase("I_IsImported"))
		{
			column.setFieldLength(1);
			column.setAD_Reference_ID(getReference("Yes-No"));
			column.setIsMandatory(true);
			column.setIsMandatoryUI(true);
			column.setDefaultValue("N");
		}				
		else if(columnName.equalsIgnoreCase("IsApproved"))
		{
			column.setFieldLength(1);
			column.setAD_Reference_ID(getReference("Yes-No"));
			column.setIsMandatory(true);
			column.setIsMandatoryUI(true);
			column.setDefaultValue("N");
		}				
		else if(columnName.equalsIgnoreCase("DocumentNo"))
		{
			column.setFieldLength(22);
			column.setAD_Reference_ID(getReference("String"));
			column.setIsMandatory(true);
			column.setIsMandatoryUI(true);
			column.setIsIdentifier(isIdentifier);
		}				
		else if(columnName.equalsIgnoreCase("DateDoc"))
		{
			column.setFieldLength(7);
			column.setAD_Reference_ID(getReference("Date"));
			column.setIsMandatory(true);
			column.setIsMandatoryUI(true);
		}				
		else if(columnName.equalsIgnoreCase("Processed"))
		{
			column.setFieldLength(1);
			column.setAD_Reference_ID(getReference("Yes-No"));
			column.setIsMandatory(true);
			column.setIsMandatoryUI(true);
			column.setDefaultValue("N");
		}				
		else if(columnName.equalsIgnoreCase("Processing"))
		{
			column.setFieldLength(1);
			column.setAD_Reference_ID(getReference("Button"));
			column.setDefaultValue("N");
		}
		else if(columnName.equalsIgnoreCase(p_ParentKey))
		{
			column.setFieldLength(22);
			column.setAD_Reference_ID(getReference("Table Direct"));
			column.setConstraintType(X_AD_Column.CONSTRAINTTYPE_Restrict);			
			column.setIsMandatory(true);
			column.setIsCopy(true);
		}

		column.save();

		return column;
	}	
	
	private MColumn copyColumn(MColumn sourceColumn, MTable table)
	{		
		MColumn column = new MColumn(table);
		column.setColumnName(sourceColumn.getColumnName());
		column.setAD_Element_ID(sourceColumn.getAD_Element_ID());
		column.setName(sourceColumn.getName());		
		column.setIsKey(sourceColumn.isKey());
		column.setFieldLength(sourceColumn.getFieldLength());
		column.setAD_Reference_ID(sourceColumn.getAD_Reference_ID());
		column.setAD_Reference_Value_ID(sourceColumn.getAD_Reference_Value_ID());
		column.setIsCopy(sourceColumn.isCopy());
		column.setAD_Val_Rule_ID(sourceColumn.getAD_Val_Rule_ID());
		column.setDefaultValue(sourceColumn.getDefaultValue());
		column.setIsUpdateable(sourceColumn.isUpdateable());
		column.setConstraintType(sourceColumn.getConstraintType());			
		column.setIsMandatory(sourceColumn.isMandatory());
		column.setIsMandatoryUI(sourceColumn.isMandatoryUI());
		column.setIsIdentifier(sourceColumn.isIdentifier());
	
		return column;
	}


	private boolean createLineColumn(String parentKey)
	{
		String sql = "SELECT AD_Element_ID " +
				"FROM AD_Element " +
				"WHERE ColumnName = 'Line' ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		int elementID = 0;
		
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
				elementID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		M_Element e = new M_Element(getCtx(), elementID, get_Trx());
		MColumn column = new MColumn(table);
		String columnName = e.getColumnName();
		
		// Set DB Column, System Element and Name
		column.setColumnName(columnName);
		column.setAD_Element_ID(elementID);
		column.setName(e.getName());
		column.setEntityType(p_EntityType);		
		column.setFieldLength(22);
		column.setAD_Reference_ID(getReference("Integer"));
		
		StringBuilder defaultValue = new StringBuilder("@SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM ");
		defaultValue.append(p_tableName);
		defaultValue.append(" WHERE ");
		defaultValue.append(parentKey);
		defaultValue.append(" = @");
		defaultValue.append(parentKey);
		defaultValue.append("@");
		
		column.setDefaultValue(defaultValue.toString());
		column.setIsMandatory(true);
		column.setIsIdentifier(true);
		column.setIsCopy(true);

		column.save();

		return true;
	}	

	private int getReference(String referenceType)
	{		
		int referenceID = 0;
		
		String sql = "SELECT AD_Reference_ID " +
				"FROM AD_Reference " +
				"WHERE Name = ? ";
			
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setString(1, referenceType);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				referenceID = rs.getInt(1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
	
		return referenceID;
	}
	
	private int getValidation(String validationName)
	{
		int validationID = 0;
		String sql = "SELECT AD_Val_Rule_ID " +
				"FROM AD_Val_Rule " +
				"WHERE Name = ? ";
			
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setString(1, validationName);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				validationID = rs.getInt(1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}		
		return validationID;
	}

}
