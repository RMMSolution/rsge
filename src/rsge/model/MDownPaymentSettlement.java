package rsge.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.compiere.model.X_C_AllocationHdr;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.compiere.util.Env.QueryParams;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_DownPaymentSettlement;

public class MDownPaymentSettlement extends X_XX_DownPaymentSettlement implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MDownPaymentSettlement.class);


	public MDownPaymentSettlement(Ctx ctx, int XX_DownPaymentSettlement_ID, Trx trx) {
		super(ctx, XX_DownPaymentSettlement_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MDownPaymentSettlement(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		
		if(isManual())
		{
			if(getC_BPartner_ID()!=0)
			{
				getDPRemains();
				System.out.println(dpRemains);
				setRemainingAmt(dpRemains);
				if(getC_Invoice_ID()!=0)
				{
					MInvoice inv = new MInvoice(getCtx(), getC_Invoice_ID(), get_Trx());				
					if(getC_BPartner_ID()!=inv.getC_BPartner_ID())
					{
						setC_Invoice_ID(0);
						setInvoiceAmt(BigDecimal.ZERO);
						setOpenAmt(BigDecimal.ZERO);					
					}
					else
					{
						setC_Currency_ID(inv.getC_Currency_ID());
						setInvoiceAmt(inv.getGrandTotal());
						setOpenAmt(getInvoiceOpenAmt());					
					}
				}
				
			}			
			if(getAppliedAmt().compareTo(dpRemains)>0)
				setAppliedAmt(dpRemains);
		}
		else
		{
			if(getC_Invoice_ID()!=0)
			{
				MInvoice inv = new MInvoice(getCtx(), getC_Invoice_ID(), get_Trx());				
				setC_BPartner_ID(inv.getC_BPartner_ID());
				setC_Currency_ID(inv.getC_Currency_ID());	
				setIsSOTrx(inv.isSOTrx());
			}
			
		}
		return true;
	}
	
	private BigDecimal getInvoiceOpenAmt()
	{
		BigDecimal openAmt = BigDecimal.ZERO;
		String sql = "SELECT InvoiceOpen(C_Invoice_ID, 0) FROM C_Invoice "
				+ "WHERE C_Invoice_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getC_Invoice_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				openAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return openAmt;
	}
	
	private BigDecimal 					dpRemains = BigDecimal.ZERO;
	private Map<Integer, BigDecimal>	dpList = new HashMap<>();
	
	private void getDPRemains()
	{
		dpRemains = BigDecimal.ZERO;
		String sql = "SELECT XX_DownPayment_ID, (CASE WHEN AllocatedAmt<TotalDPAmount THEN PayAmt-TotalDPAmount ELSE PayAmt-AllocatedAmt END) AS RemainingAmt FROM XX_DownPayment "
				+ "WHERE PayAmt > TotalDPAmount "
				+ "AND PayAmt > AllocatedAmt "
				+ "AND C_BPartner_ID = ?  "
				+ "ORDER BY DateTrx ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getC_BPartner_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				dpList.put(rs.getInt(1), rs.getBigDecimal(2));
				dpRemains = dpRemains.add(rs.getBigDecimal(2));
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public boolean approveIt() {
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	//	approveIt

	@Override
	public boolean closeIt() {
		log.info("closeIt - " + toString());
		return true;
	}	//	closeIt

	@Override
	public String completeIt() {
		System.out.println("Complete It Down Payment Settlement");
		if(isManual())
		{
			BigDecimal allocatedAmt = getAppliedAmt();
			for(Map.Entry<Integer, BigDecimal> entry : dpList.entrySet())
			{
				MDownPayment dp = new MDownPayment(getCtx(), entry.getKey().intValue(), get_Trx());
				MDownPaymentOrderAlloc dpAlloc = new MDownPaymentOrderAlloc(getCtx(), 0, get_Trx());
				dpAlloc.setAD_Org_ID(dp.getAD_Org_ID());
				dpAlloc.setXX_DownPayment_ID(entry.getKey().intValue());
				dpAlloc.setXX_DownPaymentSettlement_ID(getXX_DownPaymentSettlement_ID());
				dpAlloc.setC_Invoice_ID(getC_Invoice_ID());
				BigDecimal appliedAmt = allocatedAmt;
				if(allocatedAmt.compareTo(entry.getValue())>0)
					appliedAmt = entry.getValue();
				dpAlloc.setAllocatedAmt(appliedAmt);
				dpAlloc.save();
				allocatedAmt = allocatedAmt.subtract(appliedAmt);
				if(allocatedAmt.signum()==0)
					break;
			}
			
			// Create Allocation
			MInvoice inv = new MInvoice(getCtx(), getC_Invoice_ID(), get_Trx());
			MAllocationHdr ah = new MAllocationHdr(getCtx(), 0, get_Trx());
			ah.setClientOrg(inv);
			ah.setDateAcct(inv.getDateAcct());
			ah.setDateTrx(inv.getDateInvoiced());
			ah.setC_Currency_ID(inv.getC_Currency_ID());
			ah.save();
			
			// Generate Allocation Line
			MAllocationLine al = new MAllocationLine(ah);
			al.setDateTrx(inv.getDateInvoiced());
			al.setC_Invoice_ID(getC_Invoice_ID());
			al.setC_BPartner_ID(inv.getC_BPartner_ID());
			if(inv.isSOTrx())
				al.setAmount(getAppliedAmt());
			else
				al.setAmount(getAppliedAmt().negate());
			if(al.save())
			{
				DocumentEngine.processIt(ah, X_C_AllocationHdr.DOCACTION_Complete);
				ah.save();
			}

		}
		return DocActionConstants.STATUS_Completed;
	}

	
	@Override
	public File createPDF() {
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	public File createPDF (File file)
	{
		return null;
	}
	
	@Override
	public BigDecimal getApprovalAmt() {		
		return BigDecimal.ZERO;
	}

	@Override
	public String getDocBaseType() {
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		return 0;
	}

	@Override
	public Timestamp getDocumentDate() {
		return super.getDateDoc();
	}

	@Override
	public String getDocumentInfo() {
		return "";
	}	//	getDocumentInfo

	@Override
	public QueryParams getLineOrgsQueryInfo() {
		return null;
	}

	String m_processMsg;

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		return sb.toString();
	}	//	getSummary

	@Override
	public boolean invalidateIt() {
		log.info(toString());
		return true;
	}	//	invalidateIt

	@Override
	public String prepareIt() {
		System.out.println("START PrepareIt");
		if(isManual())
		{
			if(getAppliedAmt().signum()==0)
			{
				System.out.println("Should be here");
				m_processMsg = "Applied amount cannot be 0";
				return DocActionConstants.STATUS_Invalid;
			}
			if(getAppliedAmt().compareTo(getRemainingAmt())>0)
			{
				m_processMsg = "Applied amount cannot be bigger than remaining amount";
				return DocActionConstants.STATUS_Invalid;
			}	
		}
		return DocActionConstants.STATUS_InProgress;
	}	//	prepareIt
	
	@Override
	public boolean reActivateIt() {
		log.info("reActivateIt - " + toString());
		return DocumentEngine.processIt(this, DocActionConstants.ACTION_Reverse_Correct);
	}	//	reActivateIt

	@Override
	public boolean rejectIt() {
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}//	rejectIt

	@Override
	public boolean reverseAccrualIt() {
		log.info("reverseAccrualIt - " + toString());
		return false;
	}//	reverseAccrualIt

	@Override
	public boolean reverseCorrectIt() {
		log.info("reverseCorrectIt - " + toString());
		return false;
	}//	reverseCorrectionIt

	@Override
	public void setProcessMsg(String processMsg) {
		m_processMsg = processMsg;
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}//	unlockIt

	@Override
	public boolean voidIt() {
		log.info("voidIt - " + toString());
		
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return false;
	}//	voidIt

}
