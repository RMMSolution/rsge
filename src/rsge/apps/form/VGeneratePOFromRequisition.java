package rsge.apps.form;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.apps.form.VCharge;
import org.compiere.common.constants.DisplayTypeConstants;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.MDocBaseType;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.MOrder;
import org.compiere.model.X_M_Requisition;
import org.compiere.plaf.CompiereColor;
import org.compiere.process.DocumentEngine;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

import rsge.model.MOrderLine;
import rsge.model.MPurchaseRequisition;
import rsge.model.MPurchaseRequisitionLine;
import rsge.model.MRequisition;
import rsge.model.MRequisitionLine;
import rsge.utils.GeneralEnhancementUtils;

public class VGeneratePOFromRequisition extends CPanel implements FormPanel, ActionListener, ListSelectionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame parent frame
	 */
	public void init (int WindowNo, FormFrame frame)
	{
		log.info("");
		try
		{
			fillPicks(WindowNo);
			jbInit();
			frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
			frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
	}	//	init

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(VCharge.class);
	//
	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel newPanel = new CPanel();
	private GridBagLayout newLayout = new GridBagLayout();
	private JLabel requisitionLabel = new JLabel();
	private JTextField	requisitionField = new JTextField();
	private VLookup	productSearch = null;
	private	CLabel	productSearchLabel = new CLabel();
	private VLookup	productCatSearch = null;
	private	CLabel	productCatSearchLabel = new CLabel();
	private VLookup	chargeSearch = null;
	private	CLabel	chargeSearchLabel = new CLabel();
	private VLookup currencySearch = null;
	private CLabel 	currencySeachLabel = new CLabel();
	private VLookup	empSearch = null;
	private CLabel	empSearchLabel = new CLabel();
	private VDate 	dateFrom = new VDate("DateFrom", false, false, true, DisplayTypeConstants.Date, "DateFrom");
	private CLabel	dateFromLabel = new CLabel("Date From");
	private VDate 	dateTo = new VDate("DateTo", false, false, true, DisplayTypeConstants.Date, "DateTo");
	private CLabel	dateToLabel = new CLabel("To");

	private VDate 	requiredDateFrom = new VDate("DateRequiredFrom", false, false, true, DisplayTypeConstants.Date, "DateRequiredFrom");
	private CLabel	requiredDateFromLabel = new CLabel("Required From");
	private VDate 	requiredDateTo = new VDate("DateRequiredTo", false, false, true, DisplayTypeConstants.Date, "DateRequiredTo");
	private CLabel	requiredDateToLabel = new CLabel("To");
	private VLookup	prSearch = null;
	private	CLabel	prSearchLabel = new CLabel();	
	private JButton	searchBtn = new JButton();
	
	private CPanel centerPanel = new CPanel();	
	private BorderLayout cPanelLayout = new BorderLayout();
	private CPanel	resultPanel = new CPanel();
	private BorderLayout	resultLayout = new BorderLayout();
	
	private CPanel searchVendorPanel = new CPanel();
	private GridBagLayout searchVendorLayout = new GridBagLayout();
	private JButton searchVendorBtn = new JButton();
	
	private MiniTable miniTable = new MiniTable();
	private JScrollPane scrollPane = new JScrollPane();
	
	private CPanel svPanel = new CPanel();
	private MiniTable svTable = new MiniTable();
	private JScrollPane svScrollPane = new JScrollPane();	
	private BorderLayout	svLayout = new BorderLayout();
	
	private CPanel 	processPanel = new CPanel();
	
	private VLookup vendorSearch = null;
	private CLabel	vendorSearchLabel = new CLabel();
	private VLookup repSearch = null;
	private CLabel	repSearchLabel = new CLabel();
	private VDate	poDate = new VDate();
	private CLabel	poDateLabel = new CLabel(Msg.getElement(Env.getCtx(), "PODate"));
	private VDate	promisedDate = new VDate();
	private CLabel	promisedDateLabel = new CLabel(Msg.getElement(Env.getCtx(), "DatePromised"));
	private VLookup	paymentTerm = null;
	private CLabel	paymentTermLabel = new CLabel();
	private JButton	processBtn = new JButton();

	private StatusBar statusBar = new StatusBar();
	
	private void fillPicks(int WindowNo)
	{
		// Product
		productSearch = VLookup.createProduct(WindowNo);

		// Product Category
		MLookup pcL = MLookupFactory.get(Env.getCtx(), WindowNo, getColumnID("M_Product_Category", "M_Product_Category_ID"), DisplayTypeConstants.TableDir);
		productCatSearch = new VLookup("M_Product_Category_ID", false, false, true, pcL);
		productCatSearchLabel.setText(Msg.getElement(Env.getCtx(), "M_Product_Category_ID")); 

		// Charge
		MLookup cL = MLookupFactory.get(Env.getCtx(), WindowNo, getColumnID("M_RequisitionLine", "C_Charge_ID"), DisplayTypeConstants.TableDir);
		chargeSearch = new VLookup("C_Charge_ID", false, false, true, cL);
		chargeSearchLabel.setText(Msg.getElement(Env.getCtx(), "C_Charge_ID")); 
		
		// Currency
		MLookup currL = MLookupFactory.get(Env.getCtx(), WindowNo, getColumnID("C_Currency", "C_Currency_ID"), DisplayTypeConstants.TableDir);
		currencySearch = new VLookup("C_Currency_ID", false, false, true, currL);
		currencySeachLabel.setText(Msg.getElement(Env.getCtx(), "C_Currency_ID"));
		
		// Employee
		String empValidationCode = "C_BPartner.IsActive = 'Y' AND C_BPartner.IsEmployee = 'Y' ";
		MLookup empL = new MLookup(Env.getCtx(), WindowNo, DisplayTypeConstants.Search);
		MLookupInfo info = MLookupFactory.getLookupInfo(empL, getColumnID("C_BPartner",  "C_BPartner_ID"), Env.getLanguage(Env.getCtx()), "C_BPartner_ID", 0, true, empValidationCode);
		empL.initialize(info);
		empSearch = new VLookup("C_BPartner_ID", false, false, true, empL);
		empSearchLabel.setText(Msg.getElement(Env.getCtx(), "RequestByEmp_ID"));
		
		// Purchase Requisition
		StringBuilder prValidationCode = new StringBuilder("XX_PurchaseRequisition.DocStatus IN ('CO') "
				+ "AND XX_PurchaseRequisition.XX_PurchaseRequisition_ID IN ("
				+ "SELECT XX_PurchaseRequisition_ID FROM XX_PurchaseRequisitionLine "
				+ "WHERE Qty > QtyDelivered "
				+ ") ");		
		MLookup prL = new MLookup(Env.getCtx(), WindowNo, DisplayTypeConstants.TableDir);
		MLookupInfo li = MLookupFactory.getLookupInfo(prL, getColumnID("XX_PurchaseRequisition", "XX_PurchaseRequisition_ID"), Env.getLanguage(Env.getCtx()), "XX_PurchaseRequisition_ID", 0, true, prValidationCode.toString());
		prL.initialize(li);
		prSearch = new VLookup("XX_PurchaseRequisition_ID", false, false, true, prL);
		prSearchLabel.setText(Msg.getElement(Env.getCtx(), "XX_PurchaseRequisition_ID"));
		
		// Vendor
		String vendorValidationCode = "C_BPartner.IsActive = 'Y' AND C_BPartner.IsVendor = 'Y' ";
		MLookup vendorL = new MLookup(Env.getCtx(), WindowNo, DisplayTypeConstants.Search);
		MLookupInfo vInfo = MLookupFactory.getLookupInfo(vendorL, getColumnID("C_BPartner",  "C_BPartner_ID"), Env.getLanguage(Env.getCtx()), "C_BPartner_ID", 0, true, vendorValidationCode);
		vendorL.initialize(vInfo);
		vendorSearch = new VLookup("C_BPartner_ID", true, false, true, vendorL);
		vendorSearchLabel.setText(Msg.getElement(Env.getCtx(), "Vendor_ID"));
		
		// Representative
		String repValidationCode = "AD_User.IsActive = 'Y' AND AD_User.C_BPartner_ID IN (SELECT C_BPartner_ID FROM C_BPartner WHERE IsEmployee = 'Y' AND IsSalesRep = 'Y') ";
		MLookup repL = new MLookup(Env.getCtx(), WindowNo, DisplayTypeConstants.Search);
		MLookupInfo repInfo = MLookupFactory.getLookupInfo(repL, getColumnID("AD_User",  "AD_User_ID"), Env.getLanguage(Env.getCtx()), "AD_User_ID", 0, true, repValidationCode);
		repL.initialize(repInfo);
		repSearch = new VLookup("AD_User_ID", true, false, true, repL);
		repSearchLabel.setText(Msg.getElement(Env.getCtx(), "SalesRep_ID"));

		// Payment Term
		String ptValidationCode = "C_PaymentTerm.IsActive = 'Y' ";
		MLookup paymentTermL = new MLookup(Env.getCtx(), WindowNo, DisplayTypeConstants.TableDir);
		MLookupInfo paymentTermInfo = MLookupFactory.getLookupInfo(repL, getColumnID("C_PaymentTerm",  "C_PaymentTerm_ID"), Env.getLanguage(Env.getCtx()), "C_PaymentTerm_ID", 0, true, ptValidationCode);
		paymentTermL.initialize(paymentTermInfo);
		paymentTerm = new VLookup("C_PaymentTerm_ID", true, false, true, paymentTermL);
		paymentTermLabel.setText(Msg.getElement(Env.getCtx(), "C_PaymentTerm_ID"));

	}
	
	/**
	 *  Static Init
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		CompiereColor.setBackground(this);
		mainPanel.setLayout(mainLayout);
		newPanel.setLayout(newLayout);
		mainPanel.add(newPanel,BorderLayout.NORTH);

		requisitionLabel.setText(Msg.getMsg(Env.getCtx(), "Requisition"));
		requisitionField.setColumns(10);
		productSearchLabel.setText(Msg.getElement(Env.getCtx(), "M_Product_ID"));
		
		newPanel.add(productSearchLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(productSearch, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

		newPanel.add(productCatSearchLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(productCatSearch, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

		newPanel.add(chargeSearchLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(chargeSearch, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		
		newPanel.add(currencySeachLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(currencySearch, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

		newPanel.add(dateFromLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(dateFrom, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		
		newPanel.add(dateToLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(dateTo, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

		newPanel.add(requiredDateFromLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(requiredDateFrom, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		
		newPanel.add(requiredDateToLabel, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(requiredDateTo, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		
		newPanel.add(empSearchLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(empSearch, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		
		newPanel.add(prSearchLabel, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		newPanel.add(prSearch, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		
		searchBtn.setText("Search");
		newPanel.add(searchBtn,   new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 12), 0, 0));

		mainPanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(cPanelLayout);
		centerPanel.add(resultPanel, BorderLayout.NORTH);
		TitledBorder resultPanelTitle = new TitledBorder(Msg.getElement(Env.getCtx(), "XX_PurchaseRequisition_ID"));
		resultPanel.setBorder(resultPanelTitle);
		resultPanel.setLayout(resultLayout);
		resultPanel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getViewport().add(miniTable, null);
		scrollPane.setPreferredSize(new Dimension(450, 200));
		
		centerPanel.add(searchVendorPanel, BorderLayout.CENTER);
		searchVendorPanel.setLayout(searchVendorLayout);
		searchVendorPanel.add(searchVendorBtn, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		searchVendorBtn.setText("Search Vendor");
		searchVendorBtn.addActionListener(this);
		
		centerPanel.add(svPanel, BorderLayout.SOUTH);
		TitledBorder svPanelTitle = new TitledBorder(Msg.getElement(Env.getCtx(), "SuggestedVendor"));
		svPanel.setBorder(svPanelTitle);
		svPanel.setLayout(svLayout);
		svPanel.add(svScrollPane, BorderLayout.CENTER);
		svScrollPane.getViewport().add(svTable, null);
		svScrollPane.setPreferredSize(new Dimension(450, 200));

		mainPanel.add(processPanel, BorderLayout.SOUTH);
		processPanel.setLayout(newLayout);

		processPanel.add(vendorSearchLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		processPanel.add(vendorSearch, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

		processPanel.add(poDateLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		processPanel.add(poDate, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		promisedDate.setMandatory(true);		
		
		processPanel.add(promisedDateLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		processPanel.add(promisedDate, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		
		processPanel.add(paymentTermLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		processPanel.add(paymentTerm, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		paymentTerm.setMandatory(true);
		
		processPanel.add(repSearchLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		processPanel.add(repSearch, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

		processPanel.add(processBtn, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		processBtn.setText(Msg.getElement(Env.getCtx(), "Processing"));
		processBtn.addActionListener(this);		
		searchBtn.addActionListener(this);
		
		statusBar.setStatusLine("Ready");
		statusBar.setStatusDB(0);

		// 
	}	
	
	private void executeQuery()
	{
		log.info("");
		int AD_Client_ID = Env.getCtx().getAD_Client_ID();

		miniTable.dispose();
		svTable.dispose();
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuilder sql = new StringBuilder("SELECT rl.XX_PurchaseRequisitionLine_ID, COALESCE(p.Name, c.Name) AS Name, org.Value AS OrgValue, pr.DocumentNo, "
				+ "pr.DateDoc, pr.DateRequired, currency.ISO_Code, COALESCE(rl.Description, pr.Description) AS Description, "
				+ "rl.Qty, rl.PriceActual, u.UOMSymbol, rl.LineNetAmt "
				+ "FROM XX_PurchaseRequisitionLine rl "
				+ "INNER JOIN XX_PurchaseRequisition pr ON rl.XX_PurchaseRequisition_ID = pr.XX_PurchaseRequisition_ID "
				+ "INNER JOIN AD_Org org ON rl.AD_Org_ID = org.AD_Org_ID "
				+ "INNER JOIN C_Currency currency ON pr.C_Currency_ID = currency.C_Currency_ID "
				+ "INNER JOIN C_UOM u ON rl.C_UOM_ID = u.C_UOM_ID "
				+ "LEFT OUTER JOIN M_Product p ON rl.M_Product_ID = p.M_Product_ID "
				+ "LEFT OUTER JOIN C_Charge c ON rl.C_Charge_ID = c.C_Charge_ID "
				+ "WHERE pr.DocStatus IN ('CO') "
				+ "AND rl.M_RequisitionLine_ID IS NULL "
				+ "AND rl.AD_Client_ID = ? ");
		
		if(productSearch.getValue()!=null)
			sql.append(" AND rl.M_Product_ID = ").append(productSearch.getValue());
		else if(productCatSearch.getValue()!=null)
			sql.append(" AND p.M_Product_Category_ID = ").append(productCatSearch.getValue());
		else if(chargeSearch.getValue()!=null)
			sql.append(" AND rl.C_Charge_ID = ").append(chargeSearch.getValue());

		if(currencySearch.getValue()!=null)
			sql.append(" AND pr.C_Currency_ID = ").append(currencySearch.getValue());

		if(dateFrom.getValue()!=null)
		{
			sql.append(" AND pr.DateDoc ");
			if(dateTo.getValue()!=null)
				sql.append("BETWEEN ? AND ?");
			else
				sql.append(">= ?");
		}
		
		if(requiredDateFrom.getValue()!=null)
		{
			sql.append(" AND pr.DateRequired ");
			if(requiredDateTo.getValue()!=null)
				sql.append("BETWEEN ? AND ?");
			else
				sql.append(">= ?");
		}
		
		if(empSearch.getValue()!=null)
			sql.append(" AND pr.RequestByEmp_ID = ").append(empSearch.getValue());

		if(prSearch.getValue()!=null)
			sql.append(" AND pr.XX_PurchaseRequisition_ID = ").append(prSearch.getValue());

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), (Trx)null);
		ResultSet rs = null;
		
		//  reset table
		int row = 0;
		miniTable.setRowCount(row);

		try{
			int index=1;
			pstmt.setInt(index++, AD_Client_ID);
			if(dateFrom.getValue()!=null)
			{
				pstmt.setTimestamp(index++, (Timestamp)dateFrom.getValue());
				if(dateTo.getValue()!=null)
					pstmt.setTimestamp(index++, (Timestamp)dateTo.getValue());
			}
			
			if(requiredDateFrom.getValue()!=null)
			{
				pstmt.setTimestamp(index++, (Timestamp)requiredDateFrom.getValue());
				if(requiredDateTo.getValue()!=null)
					pstmt.setTimestamp(index++, (Timestamp)requiredDateTo.getValue());
			}

			rs = pstmt.executeQuery();
			while(rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(Boolean.valueOf(false));       //  0-Selection
				
				KeyNamePair pp = new KeyNamePair(rs.getInt("XX_PurchaseRequisitionLine_ID"), rs.getString("Name"));
				line.addElement(pp);
				line.add(rs.getBigDecimal("Qty"));       //  2-Qty
				line.add(rs.getString("UOMSymbol"));       //  3-UOM Symbol
				line.add(rs.getString("ISO_Code"));       //  4-ISO Code
				line.add(rs.getBigDecimal("PriceActual"));       //  5-Price Actual
				line.add(rs.getBigDecimal("LineNetAmt"));       //  6-Line Net Amount
				line.add(rs.getTimestamp("DateRequired"));       //  7-Date Required

				line.add(rs.getString("OrgValue"));       //  8-Org Value
				line.add(rs.getString("DocumentNo"));       //  9-Document No
				line.add(rs.getTimestamp("DateDoc"));       //  10-Date Doc
				line.add(rs.getString("Description"));       //  11-Description
				data.add(line);
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
//		miniTable.getModel().removeTableModelListener(this);
		
		Vector<String> columnName = new Vector<String>();
		columnName.add(Msg.getElement(Env.getCtx(), "Select"));
		columnName.add(Msg.getElement(Env.getCtx(), "M_Product_ID"));
		columnName.add(Msg.getElement(Env.getCtx(), "Qty"));
		columnName.add(Msg.getElement(Env.getCtx(), "C_UOM_ID"));
		columnName.add(Msg.getElement(Env.getCtx(), "C_Currency_ID"));
		columnName.add(Msg.getElement(Env.getCtx(), "PriceActual"));
		columnName.add(Msg.getElement(Env.getCtx(), "LineNetAmt"));
		columnName.add(Msg.getElement(Env.getCtx(), "DateRequired"));
		columnName.add(Msg.getElement(Env.getCtx(), "AD_Org_ID"));
		columnName.add(Msg.getElement(Env.getCtx(), "DocumentNo"));
		columnName.add(Msg.getElement(Env.getCtx(), "DateDoc"));
		columnName.add(Msg.getElement(Env.getCtx(), "Description"));
		
		DefaultTableModel model = new DefaultTableModel(data, columnName);
//		model.addTableModelListener(this);		
		miniTable.setModel(model);		
		miniTable.getSelectionModel().addListSelectionListener(this);
		
		int i = 0;
		miniTable.setColumnClass(i++, Boolean.class, false);
		miniTable.setColumnClass(i++, String.class, true);
		miniTable.setColumnClass(i++, BigDecimal.class, true);
		miniTable.setColumnClass(i++, String.class, true);
		miniTable.setColumnClass(i++, String.class, true);
		miniTable.setColumnClass(i++, BigDecimal.class, true);
		miniTable.setColumnClass(i++, BigDecimal.class, true);
		miniTable.setColumnClass(i++, Timestamp.class, true);
		miniTable.setColumnClass(i++, String.class, true);
		miniTable.setColumnClass(i++, String.class, true);
		miniTable.setColumnClass(i++, Timestamp.class, true);
		miniTable.setColumnClass(i++, String.class, true);

		miniTable.autoSize();
		miniTable.setRowSelectionAllowed(true);		
	}
	
	public void valueChanged(ListSelectionEvent e)
	{
		if(e.getValueIsAdjusting())
			return;
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int x = svTable.getSelectedRow();
		if(x>=0)
			cmd_populateVendorField(x);
		setCursor(Cursor.getDefaultCursor());
	}
	
	private int getColumnID(String tableName, String columnName)
	{
		int ID = 0;
		String sql = "SELECT c.AD_Column_ID FROM AD_Column c "
				+ "INNER JOIN AD_Table t ON c.AD_Table_ID = t.AD_Table_ID "
				+ "WHERE t.TableName = ? "
				+ "AND c.ColumnName = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, (Trx)null);
		ResultSet rs = null;
		try{
			pstmt.setString(1, tableName);
			pstmt.setString(2, columnName);
			rs = pstmt.executeQuery();
			if(rs.next())
				ID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ID;
	}
	

	public VGeneratePOFromRequisition(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public VGeneratePOFromRequisition(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public VGeneratePOFromRequisition(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public VGeneratePOFromRequisition() {
		// TODO Auto-generated constructor stub
	}

	public VGeneratePOFromRequisition(CompiereColor bc) {
		super(bc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if(e.getSource() == searchBtn)
			cmd_search();
		else if(e.getSource() == processBtn)
			cmd_process();
		else if(e.getSource() == searchVendorBtn)
			cmd_vendorSearch();
	}
	
	private void cmd_search()
	{
		log.config("");
		executeQuery();
	}	

	private void cmd_process()
	{
		log.config("");		
		
		// Check Document Control first (for PR)
		String docTypeClose = null;
		if(!GeneralEnhancementUtils.checkDocumentPeriodControl(MDocBaseType.DOCBASETYPE_PurchaseRequisition, new Timestamp(System.currentTimeMillis()), Env.getCtx().getAD_Client_ID(), (Trx)null))
			docTypeClose = ": Purchase Requisition (POR) ";
		if(docTypeClose!=null)
		{
			StringBuilder statusLine = new StringBuilder(Msg.getMsg(Env.getCtx(), "PeriodClosed"));
			statusLine.append(docTypeClose);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			statusLine.append(sdf.format(new Timestamp(System.currentTimeMillis())));
			
			statusBar.setStatusLine(statusLine.toString());
			statusBar.setStatusDB(0);
			svTable.dispose();
			return;
		}
		// PR
				
		int rows = miniTable.getRowCount();
		if(rows<0)
			return;
		
		// Check currency for each line. Every line must have same currency to proceed
		String currency = null;
		boolean diffCurrency = false;
		for(int i=0; i<rows; i++)
		{
			if(!(Boolean)miniTable.getValueAt(i, 0))
				continue;
			if(currency==null)
				currency = (String)miniTable.getValueAt(i, 4);
			else
			{
				if(!currency.equalsIgnoreCase((String)miniTable.getValueAt(i, 4)))
				{
					diffCurrency = true;
					break;
				}
			}
		}
		if(diffCurrency)
		{
			statusBar.setStatusLine("Selected lines must be in same currency");
			statusBar.setStatusDB(0);
			return;
		}
		
		Timestamp docDate = null;
		if((Timestamp)poDate.getValue()!=null)
			docDate = (Timestamp)poDate.getValue();
		else
			docDate = new Timestamp(System.currentTimeMillis());
		
		ArrayList<Integer> requisitionList = new ArrayList<>(); 		
		for(int i=0; i<rows; i++)
		{
			if(!(Boolean)miniTable.getValueAt(i, 0))
				continue;
			KeyNamePair pp = (KeyNamePair)miniTable.getValueAt(i, 1);
			int id = pp.getKey();
			MPurchaseRequisitionLine rl = new MPurchaseRequisitionLine(Env.getCtx(), id, (Trx)null);
			MPurchaseRequisition r = new MPurchaseRequisition(Env.getCtx(), rl.getXX_PurchaseRequisition_ID(), (Trx)null);
			int requisitionID = MPurchaseRequisitionLine.createRequisitionLine(rl, getPrice((Integer)vendorSearch.getValue(), rl.getM_Product_ID(), rl.getC_Charge_ID(), r.getC_Currency_ID(), rl.getPriceActual()), r.getAD_Org_ID(), (Integer)repSearch.getValue(), 
					docDate, (Timestamp)promisedDate.getValue(), r.getC_Currency_ID());
			
			boolean isExists = false;
			for(Integer reqID : requisitionList)
			{
				if(reqID.intValue()==requisitionID)
				{
					isExists = true;
					break;
				}
			}
			
			if(!isExists)
				requisitionList.add(requisitionID);
		}
		
		if(requisitionList.size()!=0)
		{
			StringBuilder poList = new StringBuilder("Updated Purchase Order(s) : ");
			// Complete Document
			int orderID = 0;
			for(Integer reqID : requisitionList)
			{
				MRequisition requisition = new MRequisition(Env.getCtx(), reqID, (Trx)null);
				DocumentEngine.processIt(requisition, X_M_Requisition.DOCACTION_Complete);
				requisition.save();				
				MRequisitionLine[] lines = MRequisition.getLines(requisition);
				int invNo = 0;
				for(MRequisitionLine rl : lines)
				{
					int orderLineID = MOrderLine.updateLines(rl.getCtx(), rl, ((Integer)vendorSearch.getValue()).intValue(), (Integer)repSearch.getValue(), (Timestamp)requiredDateFrom.getValue(), orderID, rl.get_Trx());					
					rl.setC_OrderLine_ID(orderLineID);
					if(rl.save())
					{
						MOrderLine ol = new MOrderLine(rl.getCtx(), orderLineID, rl.get_Trx());
						orderID = ol.getC_Order_ID();
						MOrder o = new MOrder(rl.getCtx(), orderID, rl.get_Trx());
						if(invNo!=0)
							poList.append(", ");
						poList.append(o.getDocumentNo());
						invNo++;
					}						
				}
			}
			statusBar.setStatusLine(poList.toString());
			statusBar.setStatusDB(0);			
		}
		
		executeQuery();
	}	
	
	private BigDecimal getPrice(int vendorID, int M_Product_ID, int C_Charge_ID, int C_Currency_ID, BigDecimal documentPrice)
	{
		BigDecimal price = documentPrice;
		StringBuilder sql = new StringBuilder();
		if(M_Product_ID!=0)
		{
			sql.append("SELECT PriceList AS Price "
					+ "FROM M_Product_PO "
					+ "WHERE IsActive = 'Y' "
					+ "AND C_BPartner_ID = ? "
					+ "AND M_Product_ID = ? "
					+ "AND C_Currency_ID = ? ");			
		}
		else if(C_Charge_ID!=0)
		{
			sql.append("SELECT ChargeAmt AS Price "
					+ "FROM C_Charge "
					+ "WHERE IsActive = 'Y' "
					+ "AND C_BPartner_ID = ? "
					+ "AND C_Charge_ID = ? ");

		}
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), (Trx) null);
		ResultSet rs = null;
		try
		{
			int index = 1;
			pstmt.setInt(index++, vendorID);
			if(M_Product_ID!=0)
			{
				pstmt.setInt(index++, M_Product_ID);
				pstmt.setInt(index++, C_Currency_ID);
			}
			else if(C_Charge_ID!=0)
				pstmt.setInt(index++, C_Charge_ID);

			rs = pstmt.executeQuery();
			if(rs.next())
			{
				BigDecimal defaultPrice = rs.getBigDecimal("Price");
				if(defaultPrice.signum()!=0)
					price = defaultPrice;
			}
		}
		catch (SQLException e)
		{
			e.getStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		return price;
	}
	
	private void cmd_vendorSearch()
	{
		log.config("");
		int i = miniTable.getSelectedRow();
		if(i<0)
			return;
		
		KeyNamePair pp = (KeyNamePair)miniTable.getValueAt(i, 1);
		MPurchaseRequisitionLine prl =  new MPurchaseRequisitionLine(Env.getCtx(), pp.getKey(), (Trx)null);
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		svTable.dispose();
		StringBuilder sql = new StringBuilder();
		int ID = 0;
		if(prl.getM_Product_ID()!=0)
		{
			sql.append("SELECT po.C_BPartner_ID, bp.Name, po.PriceList AS Price, po.Order_Min, po.Order_Pack, po.DeliveryTime_Promised "
					+ "FROM M_Product_PO po "
					+ "INNER JOIN C_BPartner bp ON po.C_BPartner_ID = bp.C_BPartner_ID "
					+ "INNER JOIN C_Currency c ON po.C_Currency_ID = c.C_Currency_ID "
					+ "WHERE po.IsActive = 'Y' "
					+ "AND po.M_Product_ID = ? "
					+ "AND c.ISO_Code = ? "
					+ "ORDER BY PriceList ");
			
			ID = prl.getM_Product_ID();
//			svTable.prepareTable(layoutForProduct, "", "", false, "");
		}
		else if(prl.getC_Charge_ID()!=0)
		{
			sql.append("SELECT c.C_BPartner_ID, bp.Name, c.ChargeAmt AS Price "
					+ "FROM C_Charge c "
					+ "INNER JOIN C_BPartner bp ON c.C_BPartner_ID = bp.C_BPartner_ID "
					+ "WHERE c.IsActive = 'Y' "
					+ "AND c.C_Charge_ID = ? ");
			
			ID = prl.getC_Charge_ID();
//			svTable.prepareTable(layoutForCharge, "", "", false, "");			
		}		
//		svTable.getModel().addTableModelListener(this);
		
		// Populate Data
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt= DB.prepareStatement(sql.toString(), (Trx) null);
			int index = 1;
			pstmt.setInt(index++, ID);
			if(prl.getM_Product_ID()!=0)
				pstmt.setString(index++, (String)miniTable.getValueAt(i, 4));
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(Boolean.valueOf(false));       //  0-Selection
				KeyNamePair pp2 = new KeyNamePair(rs.getInt("C_BPartner_ID"), rs.getString("Name"));
				line.add(pp2);
				line.add(rs.getBigDecimal("Price"));
				if(prl.getM_Product_ID()!=0)
				{
					line.add(rs.getBigDecimal("Order_Min"));
					line.add(rs.getBigDecimal("Order_Pack"));
					line.add(rs.getInt("DeliveryTime_Promised"));
				}
				data.add(line);
			}
		}
		catch (SQLException e)
		{
//			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		int dataNo = data.size();
		Vector<String> columnName = new Vector<String>();
		columnName.add(Msg.getElement(Env.getCtx(), "Select"));
		columnName.add(Msg.getElement(Env.getCtx(), "Vendor_ID"));
		columnName.add(Msg.getElement(Env.getCtx(), "Price"));
		if(prl.getM_Product_ID()!=0)
		{
			columnName.add(Msg.getElement(Env.getCtx(), "Order_Min"));
			columnName.add(Msg.getElement(Env.getCtx(), "Order_Pack"));
			columnName.add(Msg.getElement(Env.getCtx(), "DeliveryTime_Promised"));			
		}
		
		DefaultTableModel dtm = new DefaultTableModel(data, columnName);		
		svTable.setModel(dtm);		
		svTable.getSelectionModel().addListSelectionListener(this);

		int n = 0;
		svTable.setColumnClass(n++, Boolean.class, true);
		svTable.setColumnClass(n++, String.class, true);
		svTable.setColumnClass(n++, BigDecimal.class, true);
		if(prl.getM_Product_ID()!=0)
		{
			svTable.setColumnClass(n++, BigDecimal.class, true);
			svTable.setColumnClass(n++, BigDecimal.class, true);
			svTable.setColumnClass(n++, Integer.class, true);			
		}	
		svTable.setRowSelectionAllowed(true);
		svTable.autoSize();
		
		statusBar.setStatusLine(Msg.getElement(Env.getCtx(), "Vendor_ID") + "# " + dataNo); 
		statusBar.setStatusDB(0);
	}
	
	private void cmd_populateVendorField(int rowNo)
	{
		KeyNamePair pp = (KeyNamePair) svTable.getValueAt(rowNo, 1);
		vendorSearch.setValue(pp.getKey());
	}

}
