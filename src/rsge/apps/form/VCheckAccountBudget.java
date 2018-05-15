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
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.apps.form.VCharge;
import org.compiere.common.constants.DisplayTypeConstants;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.MElementValue;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.plaf.CompiereColor;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

import rsge.model.MAcctElementBudgetControl;
import rsge.model.MBudgetInfo;
import rsge.tools.BudgetCalculation;

public class VCheckAccountBudget extends CPanel implements FormPanel, ActionListener, ListSelectionListener{

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
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
	}	//	init
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(VCharge.class);
	//
	private CPanel 			mainPanel = new CPanel();
	private BorderLayout 	mainLayout = new BorderLayout();
	private CPanel			northPanel = new CPanel();
	private BorderLayout	northLayout = new BorderLayout();
	private CPanel			paramPanel = new CPanel();
	private GridBagLayout	paramLayout = new GridBagLayout();
	private CPanel 			centerPanel = new CPanel();	
	private BorderLayout 	cPanelLayout = new BorderLayout();
	private CPanel			resultPanel = new CPanel();
	private BorderLayout	resultLayout = new BorderLayout();
	private CPanel			summaryPanel = new CPanel();
	private GridBagLayout	summaryLayout = new GridBagLayout();

	private int				adOrgID = 0;
	private VLookup			orgSearch = null;
	private JLabel			orgSearchLabel = new JLabel(Msg.getElement(Env.getCtx(), "AD_Org_ID"));
	private VDate			checkDate = new VDate("Date1", true, false, true, DisplayTypeConstants.Date, "Date1"); 
	private JLabel			checkDateLabel = new JLabel(Msg.getElement(Env.getCtx(), "Date1"));

	private VLookup			accountSearch = null;
	private JLabel			accountSearchLabel = new JLabel(Msg.getElement(Env.getCtx(), "Account_ID"));
	
	private VLookup			activitySearch = null;
	private JLabel			activitySearchLabel = new JLabel(Msg.getElement(Env.getCtx(), "C_Activity_ID"));	

	private int				campaignID = 0;
	private VLookup			campaignSearch = null;
	private JLabel			campaignSearchLabel = new JLabel(Msg.getElement(Env.getCtx(), "C_Campaign_ID"));	

	private int				projectID = 0;
	private VLookup			projectSearch = null;
	private JLabel			projectSearchLabel = new JLabel(Msg.getElement(Env.getCtx(), "C_Project_ID"));	
	private VLookup			salesRegionSearch = null;
	private JLabel			salesRegionSearchLabel = new JLabel(Msg.getElement(Env.getCtx(), "C_SalesRegion_ID"));	
	
	private JLabel			budgetAmtLabel = new JLabel(Msg.getElement(Env.getCtx(), "BudgetAmt"));
	private JTextField		budgetAmt = new JTextField();
	private JLabel			reservedAmtLabel = new JLabel(Msg.getElement(Env.getCtx(), "ReservedAmt"));
	private JTextField		reservedAmt = new JTextField();
	private JLabel			unrealizedAmtLabel = new JLabel(Msg.getElement(Env.getCtx(), "UnrealizedAmt"));
	private JTextField		unrealizedAmt = new JTextField();
	private JLabel			realizedLabel = new JLabel(Msg.getElement(Env.getCtx(), "RealizedAmt"));
	private JTextField		realizedAmt = new JTextField();
	private JLabel			pendingAmtLabel = new JLabel(Msg.getElement(Env.getCtx(), "PendingAmt"));
	private JTextField		pendingAmt = new JTextField();
	private JLabel			totalAmtLabel = new JLabel(Msg.getElement(Env.getCtx(), "TotalAmt"));
	private JTextField		totalAmt = new JTextField();
	private JLabel			remainsAmtLabel = new JLabel(Msg.getElement(Env.getCtx(), "RemainingAmt"));
	private JTextField		remainsAmt = new JTextField();


	private JButton			checkButton = new JButton();
	
	private MiniTable miniTable = new MiniTable();
	private JScrollPane scrollPane = new JScrollPane();



	/**
	 *  Static Init
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		CompiereColor.setBackground(this);
		mainPanel.setLayout(mainLayout);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(northLayout);
		paramPanel.setLayout(paramLayout);
		northPanel.add(paramPanel, BorderLayout.NORTH);
		centerPanel.setLayout(cPanelLayout);
		northPanel.add(centerPanel, BorderLayout.CENTER);
		
		paramPanel.add(orgSearchLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		paramPanel.add(orgSearch, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		paramPanel.add(checkDateLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		paramPanel.add(checkDate, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		paramPanel.add(accountSearchLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		paramPanel.add(accountSearch, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		paramPanel.add(activitySearchLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		paramPanel.add(activitySearch, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		paramPanel.add(campaignSearchLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		paramPanel.add(campaignSearch, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		paramPanel.add(projectSearchLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		paramPanel.add(projectSearch, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		paramPanel.add(salesRegionSearchLabel, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		paramPanel.add(salesRegionSearch, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

		checkButton.setText("Check");
		paramPanel.add(checkButton, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 12), 0, 0));

		centerPanel.add(resultPanel, BorderLayout.NORTH);
		TitledBorder resultPanelTitle = new TitledBorder(Msg.getElement(Env.getCtx(), "Account_ID"));
		resultPanel.setBorder(resultPanelTitle);
		resultPanel.setLayout(resultLayout);
		resultPanel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getViewport().add(miniTable, null);
		scrollPane.setPreferredSize(new Dimension(450, 200));
		
		summaryPanel.setLayout(summaryLayout);
		northPanel.add(summaryPanel, BorderLayout.SOUTH);
		
		summaryPanel.add(budgetAmtLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		summaryPanel.add(budgetAmt, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		budgetAmt.setColumns(8);
		budgetAmt.setEditable(false);
		budgetAmt.setHorizontalAlignment(SwingConstants.RIGHT);
		
		summaryPanel.add(totalAmtLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		summaryPanel.add(totalAmt, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		totalAmt.setColumns(8);
		totalAmt.setEditable(false);
		totalAmt.setHorizontalAlignment(SwingConstants.RIGHT);

		summaryPanel.add(remainsAmtLabel, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		summaryPanel.add(remainsAmt, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		remainsAmt.setColumns(8);
		remainsAmt.setEditable(false);
		remainsAmt.setHorizontalAlignment(SwingConstants.RIGHT);

		
		summaryPanel.add(reservedAmtLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		summaryPanel.add(reservedAmt, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		reservedAmt.setColumns(8);
		reservedAmt.setEditable(false);
		reservedAmt.setHorizontalAlignment(SwingConstants.RIGHT);
		
		summaryPanel.add(unrealizedAmtLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		summaryPanel.add(unrealizedAmt, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		unrealizedAmt.setColumns(8);
		unrealizedAmt.setEditable(false);
		unrealizedAmt.setHorizontalAlignment(SwingConstants.RIGHT);

		summaryPanel.add(realizedLabel, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		summaryPanel.add(realizedAmt, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		realizedAmt.setColumns(8);
		realizedAmt.setEditable(false);
		realizedAmt.setHorizontalAlignment(SwingConstants.RIGHT);

		summaryPanel.add(pendingAmtLabel, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		summaryPanel.add(pendingAmt, new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		pendingAmt.setColumns(8);
		pendingAmt.setEditable(false);
		pendingAmt.setHorizontalAlignment(SwingConstants.RIGHT);
		

	}
	
	private void fillPicks(int WindowNo)
	{
		// Organization
		MLookup orgl = MLookupFactory.get(Env.getCtx(), WindowNo, getColumnID("AD_Org", "AD_Org_ID"), DisplayTypeConstants.TableDir);
		orgSearch = new VLookup("AD_Org_ID", true, false, true, orgl);
		
		// Account
		String accValidationCode = "C_ElementValue.IsActive = 'Y' AND C_ElementValue.IsSummary = 'N' ";
		MLookup acl = MLookupFactory.get(Env.getCtx(), WindowNo, getColumnID("C_ElementValue", "C_ElementValue_ID"), DisplayTypeConstants.Search);
		MLookupInfo accInfo = MLookupFactory.getLookupInfo(acl, getColumnID("C_ElementValue",  "C_ElementValue_ID"), Env.getLanguage(Env.getCtx()), "C_ElementValue_ID", 0, true, accValidationCode);
		acl.initialize(accInfo);
		accountSearch = new VLookup("C_ElementValue_ID", false, false, true, acl);
		
		// Activity
		String activityValCode = "C_Activity.IsActive = 'Y' ";
		MLookup activityl = MLookupFactory.get(Env.getCtx(), WindowNo, getColumnID("C_Activity", "C_Activity_ID"), DisplayTypeConstants.TableDir);
		MLookupInfo activityInfo = MLookupFactory.getLookupInfo(activityl, getColumnID("C_Activity", "C_Activity_ID"), Env.getLanguage(Env.getCtx()), "C_Activity_ID", 0, true, activityValCode);
		activityl.initialize(activityInfo);
		activitySearch = new VLookup("C_Activity_ID", false, false, true, activityl);
		
		// Campaign
		String campaignValCode = "C_Campaign.IsActive = 'Y' ";
		MLookup campaignl = MLookupFactory.get(Env.getCtx(), WindowNo, getColumnID("C_Campaign", "C_Campaign_ID"), DisplayTypeConstants.TableDir);
		MLookupInfo campaignInfo = MLookupFactory.getLookupInfo(campaignl, getColumnID("C_Campaign", "C_Campaign_ID"), Env.getLanguage(Env.getCtx()), "C_Campaign_ID", 0, true, campaignValCode);
		campaignl.initialize(campaignInfo);
		campaignSearch = new VLookup("C_Campaign_ID", false, false, true, campaignl);

		// Project
		String projectValCode = "C_Project.IsActive = 'Y' ";
		MLookup projectl = MLookupFactory.get(Env.getCtx(), WindowNo, getColumnID("C_Project", "C_Project_ID"), DisplayTypeConstants.TableDir);
		MLookupInfo projectInfo = MLookupFactory.getLookupInfo(projectl, getColumnID("C_Project", "C_Project_ID"), Env.getLanguage(Env.getCtx()), "C_Project_ID", 0, true, projectValCode);
		projectl.initialize(projectInfo);
		projectSearch = new VLookup("C_Project_ID", false, false, true, projectl);
		
		// SalesRegion
		String salesRegionValCode = "C_SalesRegion.IsActive = 'Y' ";
		MLookup salesRegionl = MLookupFactory.get(Env.getCtx(), WindowNo, getColumnID("C_SalesRegion", "C_SalesRegion_ID"), DisplayTypeConstants.TableDir);
		MLookupInfo salesRegionInfo = MLookupFactory.getLookupInfo(salesRegionl, getColumnID("C_SalesRegion", "C_SalesRegion_ID"), Env.getLanguage(Env.getCtx()), "C_SalesRegion_ID", 0, true, salesRegionValCode);
		salesRegionl.initialize(salesRegionInfo);
		salesRegionSearch = new VLookup("C_SalesRegion_ID", false, false, true, salesRegionl);
		
		checkButton.addActionListener(this);

	}
	
	private void cmd_search()
	{
		log.config("");
		checkBudget();;
	}	

	
	private void checkBudget()
	{
		resetSummary();
		log.info("");
		int AD_Client_ID = Env.getCtx().getAD_Client_ID();

		miniTable.dispose();
		
		// Get Start Date
		Timestamp startDate = getCalendarDate(Env.getCtx().getAD_Client_ID(), (Timestamp)checkDate.getValue(), true);
		
		adOrgID = (Integer)orgSearch.getValue();
		MBudgetInfo info = MBudgetInfo.get(Env.getCtx(), Env.getCtx().getAD_Client_ID(), (Trx)null);
		
		MAcctElementBudgetControl control = null;
		if(campaignSearch.getValue()!=null)
		{
			control = MAcctElementBudgetControl.get(info.getCtx(), info.getXX_BudgetInfo_ID(), MAcctElementBudgetControl.ELEMENTTYPE_Campaign, (Trx)null);
			campaignID = (Integer)campaignSearch.getValue();
		}
		if(projectSearch.getValue()!=null)
		{
			control = MAcctElementBudgetControl.get(info.getCtx(), info.getXX_BudgetInfo_ID(), MAcctElementBudgetControl.ELEMENTTYPE_Project, (Trx)null);
			projectID = (Integer)projectSearch.getValue();
		}

		if(control!=null)
		{	if(control.isAnyOrg())
				adOrgID=0;
			if(control.isAllYear())
			{
				Calendar cal = Calendar.getInstance();
				cal.set(1900, Calendar.JANUARY, 1);
				startDate = new Timestamp(cal.getTimeInMillis());
			}
		}
		// Get Account and Budget Amt
		Timestamp cDate = (Timestamp)checkDate.getValue();
		ArrayList<Integer> accountList = getAccountList(Env.getCtx(), AD_Client_ID, cDate, startDate);	
		if(accountList.size()==0)
		{
			if(cDate.before(info.getCutOffDate(cDate)))
			{
				Calendar cal = Calendar.getInstance();
				cal.setTime(cDate);
				cal.add(Calendar.MONTH, -1);
				cDate = new Timestamp(cal.getTimeInMillis());
				Timestamp newStartDate = getCalendarDate(Env.getCtx().getAD_Client_ID(), cDate, true);
				accountList = getAccountList(Env.getCtx(), AD_Client_ID, cDate, newStartDate);	
			}
		}
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		//  reset table
		int row = 0;
		miniTable.setRowCount(row);
		BigDecimal totalBudget = BigDecimal.ZERO;
		BigDecimal totalReserved = BigDecimal.ZERO;
		BigDecimal totalUnrealized = BigDecimal.ZERO;
		BigDecimal totalRealized = BigDecimal.ZERO;
		BigDecimal totalPending = BigDecimal.ZERO;
		BigDecimal totalAmt = BigDecimal.ZERO;
		BigDecimal totalRemains = BigDecimal.ZERO;
		
		for(Integer accountID : accountList)
		{
			MElementValue ev = new MElementValue(Env.getCtx(), accountID.intValue(), (Trx)null);
			BigDecimal budgetAmt = BigDecimal.ZERO;
			BigDecimal reservedAmt = BigDecimal.ZERO;
			BigDecimal unrealizedAmt = BigDecimal.ZERO;
			BigDecimal realizedAmt = BigDecimal.ZERO;
			BigDecimal pendingAmt = BigDecimal.ZERO;
			BigDecimal remainsAmt = BigDecimal.ZERO;
			BigDecimal usedAmt = BigDecimal.ZERO;
			
			int activity = 0;
			if(activitySearch!=null)
			{
				if((Integer)activitySearch.getValue()!=null)
					activity = (Integer)activitySearch.getValue();
			}
			
			int campaign = 0;
			if(campaignSearch!=null)
			{
				if((Integer)campaignSearch.getValue()!=null)
					campaign = (Integer)campaignSearch.getValue();
			}
			
			int project = 0;
			if(projectSearch!=null)
			{
				if((Integer)projectSearch.getValue()!=null)
					project = (Integer)projectSearch.getValue();
			}
			
			int salesRegion = 0;
			if(salesRegionSearch!=null)
			{
				if((Integer)salesRegionSearch.getValue()!=null)
					salesRegion = (Integer)salesRegionSearch.getValue();
			}
			
			BudgetCalculation bc = new BudgetCalculation(Env.getCtx(), adOrgID, cDate, cDate, null, null, false, accountID.intValue(), 
					activity, campaign, project, 0, 0, salesRegion, 0, 0, false, (Trx)null);			
			System.out.println("BC " + bc);
			if(bc!=null)
			{
				bc.calculateUsedAmt();
				budgetAmt = bc.getBudgetAmt();
				totalBudget = totalBudget.add(budgetAmt);
				if(bc.getReservedAmt()!=null)
				{
					reservedAmt = bc.getReservedAmt();
					totalReserved = totalReserved.add(reservedAmt);
				}
				if(bc.getUnrealizedAmt()!=null)
				{
					unrealizedAmt = bc.getUnrealizedAmt();
					totalUnrealized = totalUnrealized.add(unrealizedAmt);
				}
				if(bc.getRealizedAmt()!=null)
				{
					realizedAmt = bc.getRealizedAmt();
					totalRealized = totalRealized.add(totalRealized);
				}
				if(bc.getPendingAmt()!=null)
				{
					pendingAmt = bc.getPendingAmt();
					totalPending = totalPending.add(pendingAmt);
				}
				usedAmt = reservedAmt.add(unrealizedAmt).add(realizedAmt).add(pendingAmt);
				totalAmt = totalAmt.add(usedAmt);
				remainsAmt = budgetAmt.subtract(usedAmt);
				totalRemains = totalRemains.add(remainsAmt);
			}
			

			Vector<Object> line = new Vector<Object>();
//			line.add(Boolean.valueOf(false));       //  0-Selection
			
			KeyNamePair pp = new KeyNamePair(accountID.intValue(), ev.getValue());
			line.addElement(pp);
			line.add(ev.getName());       				//  2-Account Name
			line.add(budgetAmt);      				 	//  3-Budget Amount
			line.add(reservedAmt);       				//  4-Reserved Amount
			line.add(unrealizedAmt); 			      	//  5-Unrealized Amount
			line.add(realizedAmt);       				//  6-Realized Amount
			line.add(pendingAmt);				       	//  7-Pending Amount
			line.add(usedAmt);				       		//  8-Used Amount
			line.add(remainsAmt);				       	//  9-Remains Amount
			data.add(line);
		}
		
//		miniTable.getModel().removeTableModelListener(this);
		
		Vector<String> columnName = new Vector<String>();
		columnName.add(Msg.getElement(Env.getCtx(), "Account_ID"));
		columnName.add(Msg.getElement(Env.getCtx(), "Description"));
		columnName.add(Msg.getElement(Env.getCtx(), "BudgetAmt"));
		columnName.add(Msg.getElement(Env.getCtx(), "ReservedAmt"));
		columnName.add(Msg.getElement(Env.getCtx(), "UnrealizedAmt"));
		columnName.add(Msg.getElement(Env.getCtx(), "RealizedAmt"));
		columnName.add(Msg.getElement(Env.getCtx(), "PendingAmt"));
		columnName.add(Msg.getElement(Env.getCtx(), "TotalAmt"));
		columnName.add(Msg.getElement(Env.getCtx(), "RemainingAmt"));
		
		DefaultTableModel model = new DefaultTableModel(data, columnName);
//		model.addTableModelListener(this);		
		miniTable.setModel(model);		
		miniTable.getSelectionModel().addListSelectionListener(this);
		
		int i = 0;
		miniTable.setColumnClass(i++, String.class, true);
		miniTable.setColumnClass(i++, String.class, true);
		miniTable.setColumnClass(i++, BigDecimal.class, true);
		miniTable.setColumnClass(i++, BigDecimal.class, true);
		miniTable.setColumnClass(i++, BigDecimal.class, true);
		miniTable.setColumnClass(i++, BigDecimal.class, true);
		miniTable.setColumnClass(i++, BigDecimal.class, true);
		miniTable.setColumnClass(i++, BigDecimal.class, true);
		miniTable.setColumnClass(i++, BigDecimal.class, true);

		miniTable.autoSize();
		miniTable.setRowSelectionAllowed(true);		
		
		updateSummary(totalBudget, totalReserved, totalUnrealized, totalRealized, totalPending, totalAmt, totalRemains);
	}
	
	private boolean resetSummary()
	{
		budgetAmt.setText("0");
		reservedAmt.setText("0");
		unrealizedAmt.setText("0");
		realizedAmt.setText("0");
		pendingAmt.setText("0");
		totalAmt.setText("0");
		remainsAmt.setText("0");
		return true;
	}
	
	private boolean updateSummary(BigDecimal totalBudget, BigDecimal totalReserved, BigDecimal totalUnrealized,
			BigDecimal totalRealized, BigDecimal totalPending, BigDecimal usedAmt, BigDecimal totalRemains)
	{
		DecimalFormat format = DisplayType.getNumberFormat(DisplayTypeConstants.Number, Language.getLoginLanguage());
		budgetAmt.setText(format.format(totalBudget));
		reservedAmt.setText(format.format(totalReserved));
		unrealizedAmt.setText(format.format(totalUnrealized));
		realizedAmt.setText(format.format(totalRealized));
		pendingAmt.setText(format.format(totalPending));
		totalAmt.setText(format.format(usedAmt));
		remainsAmt.setText(format.format(totalRemains));
		return true;
	}

	
	private Timestamp getCalendarDate(int AD_Client_ID, Timestamp dateTrx, boolean isStartDate)
	{
		Timestamp calendarDate = null;
		StringBuilder sql = new StringBuilder("SELECT p.StartDate, p.EndDate FROM C_Period p "
				+ "INNER JOIN C_Year y ON p.C_Year_ID = y.C_Year_ID "
				+ "INNER JOIN XX_BudgetInfo bi ON y.C_Calendar_ID = bi.BudgetCalendar_ID "
				+ "WHERE p.PeriodType = 'S' "
				+ "AND bi.AD_Client_ID = ? "
				+ "AND y.C_Year_ID IN (SELECT C_Year_ID FROM C_Period "
				+ "WHERE ? BETWEEN StartDate AND EndDate) ");
		if(isStartDate)
			sql.append("ORDER BY p.StartDate ");
		else
			sql.append("ORDER BY p.EndDate DESC ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), (Trx)null);
		ResultSet rs = null;
		
		try{
			int index=1;
			pstmt.setInt(index++, AD_Client_ID);
			pstmt.setTimestamp(index++, dateTrx);		
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(isStartDate)
					calendarDate = rs.getTimestamp(1);
				else
					calendarDate = rs.getTimestamp(2);
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			// TODO: handle exception
		}		
		return calendarDate;
	}
	
	private ArrayList<Integer> getAccountList(Ctx ctx, int AD_Client_ID, Timestamp dateTrx, Timestamp startDate)
	{
		Timestamp endDate = getCalendarDate(AD_Client_ID, dateTrx, false);		
		
		ArrayList<Integer> accountList = new ArrayList<>();
		int accountID = 0;
		if(accountSearch!=null)
		{
			if((Integer)accountSearch.getValue()!=null)
				accountID = (Integer)accountSearch.getValue();
		}
		StringBuilder sql = new StringBuilder("SELECT C_ElementValue_ID FROM C_ElementValue WHERE C_ElementValue_ID IN ("
				+ "SELECT fa.Account_ID FROM Fact_Acct fa "
				+ "INNER JOIN C_ElementValue ev ON fa.Account_ID = ev.C_ElementValue_ID "
				+ "WHERE fa.PostingType = 'B' ");
		if(adOrgID!=0)
			sql.append("AND fa.AD_Org_ID = ? ");
		if(campaignID!=0)
			sql.append("AND fa.C_Campaign_ID = ? ");
		if(projectID!=0)
			sql.append("AND fa.C_Project_ID = ? ");
		sql.append("AND fa.DateAcct BETWEEN ? AND ? ");
		if(accountID!=0)
			sql.append("AND fa.Account_ID = ? ");
		sql.append("AND fa.Account_ID NOT IN ("
				+ "SELECT Account_ID FROM ("
				+ "SELECT vc.Account_ID FROM C_AcctSchema_GL ag "
				+ "INNER JOIN C_ValidCombination vc ON ag.SuspenseBalancing_Acct = vc.C_ValidCombination_ID "
				+ "UNION "
				+ "SELECT vc.Account_ID FROM C_AcctSchema_GL ag "
				+ "INNER JOIN C_ValidCombination vc ON ag.IntercompanyDueFrom_Acct = vc.C_ValidCombination_ID "
				+ "UNION "
				+ "SELECT vc.Account_ID FROM C_AcctSchema_GL ag "
				+ "INNER JOIN C_ValidCombination vc ON ag.IntercompanyDueTo_Acct = vc.C_ValidCombination_ID "
				+ ") "
				+ ") "
				+ ") ORDER BY Value ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), (Trx)null);
		ResultSet rs = null;
		
		try{
			int index=1;
			System.out.println(" ");
			System.out.println(sql);
			if(adOrgID!=0)
			{
				pstmt.setInt(index++, adOrgID);
				System.out.println(adOrgID);

			}
			if(campaignID!=0)
			{
				pstmt.setInt(index++, campaignID);
				System.out.println(campaignID);

			}
			if(projectID!=0)
			{
				pstmt.setInt(index++, projectID);
				System.out.println(projectID);

			}

			pstmt.setTimestamp(index++, startDate);
			System.out.println(startDate);
			pstmt.setTimestamp(index++, endDate);
			System.out.println(endDate);
			if(accountID!=0)
				pstmt.setInt(index++, accountID);
			rs = pstmt.executeQuery();
			while(rs.next())
				accountList.add(rs.getInt(1));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			// TODO: handle exception
		}		
		
		return accountList;
	}


	
	/**
	 *  Create Optional Organization Search Lookup
	 *  @param WindowNo window
	 *  @return VLookup
	 */
	public static VLookup createOrg(int WindowNo)
	{
		return null;
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if(e.getSource() == checkButton)
			cmd_search();
	}

	public VCheckAccountBudget(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public VCheckAccountBudget(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public VCheckAccountBudget(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public VCheckAccountBudget() {
		// TODO Auto-generated constructor stub
	}

	public VCheckAccountBudget(CompiereColor bc) {
		super(bc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
