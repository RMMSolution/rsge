package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.ImportBPartnerModel;

public class ValidateImportBPartner extends SvrProcess {

	ArrayList<ImportBPartnerModel> list = new ArrayList<ImportBPartnerModel>();
	private static final String STD_CLIENT_CHECK = " AND AD_Client_ID=? " ;
	private int				p_AD_Client_ID = 0;

	
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM I_BPartner WHERE I_IsImported = 'N' ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ImportBPartnerModel imp = new ImportBPartnerModel(getCtx(), rs, get_Trx());
				list.add(imp);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		p_AD_Client_ID = getAD_Client_ID();
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		for(ImportBPartnerModel imp : list)
		{
			// Check Name
			String name = imp.getName();
			if(name.length()>60)
			{
				imp.setName(name.substring(0, 60));
				imp.setName2(name.substring(60));
				imp.setDescription(name);
			}
			
			// Check Address1
			String address = imp.getAddress1();
			if(address.length()>60)
			{
				imp.setAddress1(address.substring(0, 60));
				imp.setAddress2(address.substring(60));
			}
			
			// Check City
			String city = imp.getCity();
			if(city.length()>60)
			{
				imp.setCity(city.substring(0, 60));
			}

			// Check Contact Name
			String contact = imp.getContactName();
			if(contact!=null)
			{
				if(contact.length()>60)
				{
					imp.setContactName(contact.substring(0, 60));
				}				
			}
			imp.save();		
		
		}
		//	Set Country
		String sql = "UPDATE I_BPartner "
				+ "SET AD_Client_ID = COALESCE (AD_Client_ID,?),"
				+ " AD_Org_ID = COALESCE (AD_Org_ID, 0),"
				+ " IsActive = COALESCE (IsActive, 'Y'),"
				+ " Created = COALESCE (Created, SysDate),"
				+ " CreatedBy = COALESCE (CreatedBy, 0),"
				+ " Updated = COALESCE (Updated, SysDate),"
				+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
				+ " I_ErrorMsg = NULL,"
				+ " I_IsImported = 'N' "
				+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL";
		int	no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
			log.fine("Reset=" + no);
			
			//
			sql = "UPDATE I_BPartner i "
				+ "SET C_BP_Group_ID=(SELECT C_BP_Group_ID FROM C_BP_Group g"
				+ " WHERE i.GroupValue=g.Value AND g.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE C_BP_Group_ID IS NULL"
				+ " AND I_IsImported<>'Y'" 
				+ STD_CLIENT_CHECK;
			no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
			log.fine("Set Group=" + no);
			//
			String ts = DB.isPostgreSQL()?"COALESCE(I_ErrorMsg,'')":"I_ErrorMsg";  //java bug, it could not be used directly
			sql = "UPDATE I_BPartner "
				+ "SET I_IsImported='E', I_ErrorMsg="+ts +"||'ERR=Invalid Group, ' "
				+ "WHERE C_BP_Group_ID IS NULL"
				+ " AND I_IsImported<>'Y'" 
				+ STD_CLIENT_CHECK;
			no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
			log.config("Invalid Group=" + no);

		//
		sql = "UPDATE I_BPartner i "
			+ "SET C_Country_ID=(SELECT C_Country_ID FROM C_Country c"
			+ " WHERE i.CountryCode=c.CountryCode AND c.IsSummary='N' AND c.AD_Client_ID IN (0, i.AD_Client_ID)) "
			+ "WHERE C_Country_ID IS NULL"
			+ " AND I_IsImported<>'Y'" 
			+ STD_CLIENT_CHECK;
		no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
		log.fine("Set Country=" + no);
		//
		ts = DB.isPostgreSQL()?"COALESCE(I_ErrorMsg,'')":"I_ErrorMsg";  //java bug, it could not be used directly
		sql = "UPDATE I_BPartner "
			+ "SET I_IsImported='E', I_ErrorMsg="+ts +"||'ERR=Invalid Country, ' "
			+ "WHERE C_Country_ID IS NULL AND (City IS NOT NULL OR Address1 IS NOT NULL)"
			+ " AND I_IsImported<>'Y'" 
			+ STD_CLIENT_CHECK;
		no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
		log.config("Invalid Country=" + no);

		//	Set Region
		sql = "UPDATE I_BPartner i "
			+ "Set RegionName=(SELECT Name FROM C_Region r"
			+ " WHERE r.IsDefault='Y' AND r.C_Country_ID=i.C_Country_ID"
			+ " AND r.AD_Client_ID IN (0, i.AD_Client_ID)) " 
			+ "WHERE RegionName IS NULL AND C_Region_ID IS NULL"
			+ " AND I_IsImported<>'Y'" 
			+ STD_CLIENT_CHECK;
		no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
		log.fine("Set Region Default=" + no);
		//
		sql = "UPDATE I_BPartner i "
			+ "Set C_Region_ID=(SELECT C_Region_ID FROM C_Region r"
			+ " WHERE r.Name=i.RegionName AND r.C_Country_ID=i.C_Country_ID"
			+ " AND r.AD_Client_ID IN (0, i.AD_Client_ID)) "
			+ "WHERE C_Region_ID IS NULL"
			+ " AND I_IsImported<>'Y'" 
			+ STD_CLIENT_CHECK;
		no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
		log.fine("Set Region=" + no);
		//
		sql = "UPDATE I_BPartner i "
			+ "SET I_IsImported='E', I_ErrorMsg="+ts +"||'ERR=Invalid Region, ' "
			+ "WHERE C_Region_ID IS NULL "
			+ " AND EXISTS (SELECT * FROM C_Country c"
			+ " WHERE c.C_Country_ID=i.C_Country_ID AND c.HasRegion='Y')"
			+ " AND I_IsImported<>'Y'" 
			+ STD_CLIENT_CHECK;
		no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
		log.config("Invalid Region=" + no);
		
		//	Set City
		//
		sql = "UPDATE I_BPartner i "
			+ "Set C_City_ID=(SELECT c.C_City_ID FROM C_City c"
			+ " WHERE c.Name=i.City AND c.C_Country_ID=i.C_Country_ID"
			+ " AND c.C_Region_ID = i.C_Region_ID "
			+ " AND c.AD_Client_ID IN (0, i.AD_Client_ID)) "
			+ "WHERE C_City_ID IS NULL"
			+ " AND I_IsImported<>'Y'" 
			+ STD_CLIENT_CHECK;
		no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
		log.fine("Set City=" + no);
		//
		sql = "UPDATE I_BPartner i "
			+ "SET I_IsImported='E', I_ErrorMsg="+ts +"||'ERR=Invalid City, ' "
			+ "WHERE C_City_ID IS NULL "
			+ " AND I_IsImported<>'Y'" 
			+ STD_CLIENT_CHECK;
		no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
		log.config("Invalid City=" + no);

		//	Set District
		sql = "UPDATE I_BPartner i "
			+ "Set XX_District_ID=(SELECT d.XX_District_ID FROM XX_District d "
			+ " WHERE d.Name=i.DistrictName AND d.C_City_ID=i.C_City_ID"
			+ " AND d.AD_Client_ID IN (0, i.AD_Client_ID)) "
			+ "WHERE XX_District_ID IS NULL"
			+ " AND I_IsImported<>'Y'" 
			+ STD_CLIENT_CHECK;
		no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
		log.fine("Set District=" + no);
		//
		sql = "UPDATE I_BPartner i "
			+ "SET I_IsImported='E', I_ErrorMsg="+ts +"||'ERR=Invalid District, ' "
			+ "WHERE XX_District_ID IS NULL "
			+ " AND I_IsImported<>'Y'" 
			+ STD_CLIENT_CHECK;
		no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
		log.config("Invalid District=" + no);

		//	Set Sub District
		sql = "UPDATE I_BPartner i "
			+ "Set XX_SubDistrict_ID=(SELECT sd.XX_SubDistrict_ID FROM XX_SubDistrict sd "
			+ " WHERE sd.Name=i.SubDistrictName AND sd.XX_District_ID=i.XX_District_ID"
			+ " AND sd.AD_Client_ID IN (0, i.AD_Client_ID)) "
			+ "WHERE XX_SubDistrict_ID IS NULL"
			+ " AND I_IsImported<>'Y'" 
			+ STD_CLIENT_CHECK;
		no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
		log.fine("Set Sub District=" + no);
		//
		sql = "UPDATE I_BPartner i "
			+ "SET I_IsImported='E', I_ErrorMsg="+ts +"||'ERR=Invalid Sub District, ' "
			+ "WHERE XX_SubDistrict_ID IS NULL AND SubDistrictName IS NOT NULL "
			+ " AND I_IsImported<>'Y'" 
			+ STD_CLIENT_CHECK;
		no = DB.executeUpdate(get_Trx(), sql, new Object[] {p_AD_Client_ID});
		log.config("Invalid Sub District=" + no);

		return "Validation Complete";
	}

}
