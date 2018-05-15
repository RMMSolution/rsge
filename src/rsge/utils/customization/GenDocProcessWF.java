/**
 * 
 */
package rsge.utils.customization;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWFNodeNext;
import org.compiere.wf.MWorkflow;

/**
 * GenDocProcessWF.java
 *
 * @author FANNY
 * @date Dec 27, 2011
 * 
 */
public class GenDocProcessWF extends SvrProcess {

	/** Initiate Variable						*/
	/** AD_Workflow_ID							*/
	private int					AD_Workflow_ID = 0;
	/** Workflow Name							*/
	private String 				WFName = null;
	/** AD_Table_ID								*/
	private int					AD_Table_ID = 0;
	/** Publish Status							*/
	private String				publishStatus = null;
	/** Version									*/
	private int					Version = 0;
	/** Author									*/
	private String				Author = null;
	/** AccessLvl									*/
	private String				AccessLvl = null;
	/** Entity Type								*/
	private String				EntityType = null;
	/** Start Node ID							*/
	private int 				StartNode_ID = 0;
	
	/**
	 * 
	 */
	public GenDocProcessWF() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter params[] = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();
			if(name.equals("Name"))
				WFName = (String) param.getParameter();
			if(name.equals("AD_Table_ID"))
				AD_Table_ID = param.getParameterAsInt();
			if(name.equals("PublishStatus"))
				publishStatus = (String) param.getParameter();
			if(name.equals("Version"))
				Version = param.getParameterAsInt();
			if(name.equals("Author"))
				Author = (String) param.getParameter();
			if(name.equals("AccessLevel"))
				AccessLvl = (String) param.getParameter();
			
		}
		
		/** Get Entity type of Table			*/
		String sql = "SELECT Entitytype from AD_Table " +
				"WHERE AD_Table_ID = " + AD_Table_ID;
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
				EntityType = rs.getString(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// Validate if workflow for certain AD_Table is exists. If yes, cancel process
		String sql = "SELECT 1 FROM AD_Workflow " +
				"WHERE AD_Table_ID = " + AD_Table_ID;
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				// Cancel Process
				return Msg.getMsg(getCtx(), "Table WF Exists");
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		GenerateWorkflow();
		return Msg.getMsg(getCtx(), "Workflow Created");
	}

	private void GenerateWorkflow()
	{
		//Generate Header
		MWorkflow wf = new MWorkflow(getCtx(), 0, get_Trx());
		
		wf.setValue(WFName);
		wf.setName(WFName);
		wf.setAD_Table_ID(AD_Table_ID);
		wf.setDescription("(Standard " + WFName + ")");
		wf.setWorkflowType("P");
		wf.setAccessLevel(AccessLvl);
		wf.setAuthor(Author);
		wf.setPublishStatus(publishStatus);
		wf.setEntityType(EntityType);
		wf.setVersion(Version);
		wf.isValid();
		if(wf.save())
		{
			AD_Workflow_ID = wf.getAD_Workflow_ID();
		}
		// End Generate Header
		
		CreateWFNode();
		
		//Update Start Node
		wf.setAD_WF_Node_ID(StartNode_ID);
		wf.save();
	}
	
	private void CreateWFNode()
	{
		int PrepareNode_ID;
		int CompleteNode_ID;
		int AutoNode_ID;
		
		/** Create Start Node	*/
		StartNode_ID = CreateNode("S");
		/** Create Doc Prepare Node	*/
		PrepareNode_ID = CreateNode("P");
		/** Create Doc Auto Node	*/
		AutoNode_ID = CreateNode("A");
		/** Create Doc Complete Node	*/
		CompleteNode_ID = CreateNode("C");		
		
		/** Create Transition between Node			*/
		/** From Start Node to Doc Prepare			*/
		CreateTransition(StartNode_ID, PrepareNode_ID, 10, "(Standard Approval)", true);
		/** From Start Node to Doc Auto				*/
		CreateTransition(StartNode_ID, AutoNode_ID, 100, "(Standard Transition)", false);
		/** From Prepare Node to Doc Complete				*/
		CreateTransition(PrepareNode_ID, CompleteNode_ID, 100, "(Standard Transition)", false);
	}
	
	private int CreateNode(String NodeType)
	{
		int AD_WF_Node_ID = 0;
		String Name = null;
		String Description = "(Standard Node)";
		String Action = null;
		String DocAction = null;
		
		if(NodeType.equalsIgnoreCase("A"))
		{
			Name = "(DocAuto)";
			Action = "D";
			DocAction = "--";
		}
		if(NodeType.equalsIgnoreCase("C"))
		{
			Name = "(DocComplete)";
			Action = "D";
			DocAction = "CO";
		}
		if(NodeType.equalsIgnoreCase("P"))
		{
			Name = "(DocPrepare)";
			Action = "D";
			DocAction = "PR";
		}
		if(NodeType.equalsIgnoreCase("S"))
		{
			Name = "(Start)";
			Action = "Z";
		}
		MWFNode node = new MWFNode(getCtx(), 0, get_Trx());
		
		node.setAD_Workflow_ID(AD_Workflow_ID);
		node.setValue(Name);
		node.setName(Name);
		node.setEntityType(EntityType);
		node.setDescription(Description);
		node.setJoinElement("X");
		node.setSplitElement("X");
		node.setAction(Action);
		if(DocAction != null)
			node.setDocAction(DocAction);
		
		if(node.save())
		{
			AD_WF_Node_ID = node.getAD_WF_Node_ID();
		}
		return AD_WF_Node_ID;
	}
	
	private void CreateTransition(int StartNode_ID, int ToNode_ID, int sequence, String Description, boolean StdUserWF)
	{
		MWFNodeNext nn = new MWFNodeNext(getCtx(), 0, get_Trx());
		nn.setAD_WF_Node_ID(StartNode_ID);
		nn.setAD_WF_Next_ID(ToNode_ID);
		nn.setSeqNo(sequence);
		nn.setDescription(Description);
		nn.setIsStdUserWorkflow(StdUserWF);
		nn.setEntityType(EntityType);
		nn.save();		
	}
}
