package rsge.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.model.MPeriod;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env.QueryParams;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_ClosingPeriod;

public class MClosingPeriod extends X_XX_ClosingPeriod implements DocAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MClosingPeriod.class);

	public MClosingPeriod(Ctx ctx, int XX_ClosingPeriod_ID, Trx trx) {
		super(ctx, XX_ClosingPeriod_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MClosingPeriod(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		if(getC_Period_ID()>0){
			MPeriod mPeriod = new MPeriod(getCtx(), getC_Period_ID(), get_Trx());
			setDateAcct(mPeriod.getEndDate());
		}
		
		if(cek()){
			log.saveError("Error", Msg.getMsg(getCtx(), "Period Exists"));
			return false;
		}
		return true;
	}
	
	private boolean cek(){
		boolean cek = false;
		String sql = "SELECT XX_ClosingPeriod_ID FROM XX_ClosingPeriod " +
				"WHERE XX_ClosingPeriod_ID NOT IN (?) AND C_Period_ID = ? ";
		PreparedStatement ps = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			ps.setInt(1, getXX_ClosingPeriod_ID());
			ps.setInt(2, getC_Period_ID());
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0)
					cek = true;
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cek;
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		// TODO Auto-generated method stub
		return DocActionConstants.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true; 
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		return DocActionConstants.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
log.info("voidIt - " + toString());
		
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		log.info("closeIt - " + toString());
		return true; 
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		return DocumentEngine.processIt(this, DocActionConstants.ACTION_Void);
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		log.info("reverseAccrualIt - " + toString());
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		log.info("reActivateIt - " + toString());
		return DocumentEngine.processIt(this, DocActionConstants.ACTION_Reverse_Correct);
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		return sb.toString();
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF(temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null; 
	}
	
	public File createPDF (File file)
	{
		return null;
	}
	String m_processMsg;

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return BigDecimal.ZERO;
	}

	@Override
	public void setProcessMsg(String processMsg) {
		// TODO Auto-generated method stub
		m_processMsg = processMsg;
	}

	@Override
	public Timestamp getDocumentDate() {
		// TODO Auto-generated method stub
		return super.getDateDoc();
	}

	@Override
	public String getDocBaseType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryParams getLineOrgsQueryInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
