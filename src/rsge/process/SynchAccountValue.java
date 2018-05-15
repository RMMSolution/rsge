package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.compiere.model.MElementValue;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class SynchAccountValue extends SvrProcess {

	private int				maxLength = 0;
	
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null && element.getParameter_To() == null)
				;
			else if (name.equals("NumberOfDigit"))
				maxLength = element.getParameterAsInt();
		}

	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM C_ElementValue " +
				"WHERE AD_Client_ID = " + getAD_Client_ID();
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MElementValue ev = new MElementValue(getCtx(), rs, get_Trx());
				String value = ev.getValue();
				if(value.length()<maxLength)
				{
					int x = maxLength-value.length();
					StringBuilder a = new StringBuilder(value);
					for(int i = 0; i < x; i++)
					{
						a.append("0");
					}
					value = a.toString();
					ev.setValue(value);
					ev.save();
				}
			}			
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
