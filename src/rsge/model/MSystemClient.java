package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_SystemClient;

public class MSystemClient extends X_XX_SystemClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MSystemClient(Ctx ctx, int XX_SystemClient_ID, Trx trx) {
		super(ctx, XX_SystemClient_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MSystemClient(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static MSystemClient update(Ctx ctx, int AD_Client_ID, String name, Trx trx)
	{
		MSystemClient si = null;
		String sql = "SELECT * FROM XX_SystemClient " +
				"WHERE Client_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Client_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				si = new MSystemClient(ctx, rs, trx);
				if(!si.getName().equals(name))
				{
					si.setName(name);
					si.save();
				}
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(si==null)
		{
			si = new MSystemClient(ctx, 0, trx);
			si.setClient_ID(AD_Client_ID);
			si.setName(name);
			si.save();
		}
		return si;
	}
	
	public static int getClientID(int XX_SystemClient_ID, Trx trx)
	{
		int clientID = 0;
		String sql = "SELECT Client_ID FROM XX_SystemClient " +
				"WHERE XX_SystemClient_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, XX_SystemClient_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				clientID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return clientID;
	}

}
