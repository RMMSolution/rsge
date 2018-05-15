/******************************************************************************
 * Product: Compiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2008 Compiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us at *
 * Compiere, Inc., 3600 Bridge Parkway #102, Redwood City, CA 94065, USA      *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package rsge.po;

/** Generated Model - DO NOT CHANGE */
import java.sql.*;
import org.compiere.framework.*;
import org.compiere.util.*;
/** Generated Model for XX_DisbursementType
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DisbursementType extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DisbursementType_ID id
    @param trx transaction
    */
    public X_XX_DisbursementType (Ctx ctx, int XX_DisbursementType_ID, Trx trx)
    {
        super (ctx, XX_DisbursementType_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DisbursementType_ID == 0)
        {
            setName (null);
            setXX_DisbursementType_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DisbursementType (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27801779225540L;
    /** Last Updated Timestamp 2018-02-26 21:05:08.751 */
    public static final long updatedMS = 1519653908751L;
    /** AD_Table_ID=1000135 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DisbursementType");
        
    }
    ;
    
    /** TableName=XX_DisbursementType */
    public static final String Table_Name="XX_DisbursementType";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set AD Charge.
    @param AdvDisbursementCharge_ID Charge used in advance disbursement */
    public void setAdvDisbursementCharge_ID (int AdvDisbursementCharge_ID)
    {
        if (AdvDisbursementCharge_ID <= 0) set_Value ("AdvDisbursementCharge_ID", null);
        else
        set_Value ("AdvDisbursementCharge_ID", Integer.valueOf(AdvDisbursementCharge_ID));
        
    }
    
    /** Get AD Charge.
    @return Charge used in advance disbursement */
    public int getAdvDisbursementCharge_ID() 
    {
        return get_ValueAsInt("AdvDisbursementCharge_ID");
        
    }
    
    /** Set Charge.
    @param C_Charge_ID Additional document charges */
    public void setC_Charge_ID (int C_Charge_ID)
    {
        if (C_Charge_ID <= 0) set_Value ("C_Charge_ID", null);
        else
        set_Value ("C_Charge_ID", Integer.valueOf(C_Charge_ID));
        
    }
    
    /** Get Charge.
    @return Additional document charges */
    public int getC_Charge_ID() 
    {
        return get_ValueAsInt("C_Charge_ID");
        
    }
    
    /** Set Description.
    @param Description Optional short description of the record */
    public void setDescription (String Description)
    {
        set_Value ("Description", Description);
        
    }
    
    /** Get Description.
    @return Optional short description of the record */
    public String getDescription() 
    {
        return (String)get_Value("Description");
        
    }
    
    /** Set Name.
    @param Name Alphanumeric identifier of the entity */
    public void setName (String Name)
    {
        if (Name == null) throw new IllegalArgumentException ("Name is mandatory.");
        set_Value ("Name", Name);
        
    }
    
    /** Get Name.
    @return Alphanumeric identifier of the entity */
    public String getName() 
    {
        return (String)get_Value("Name");
        
    }
    
    /** Set Disbursement Type.
    @param XX_DisbursementType_ID Type of disbursement */
    public void setXX_DisbursementType_ID (int XX_DisbursementType_ID)
    {
        if (XX_DisbursementType_ID < 1) throw new IllegalArgumentException ("XX_DisbursementType_ID is mandatory.");
        set_ValueNoCheck ("XX_DisbursementType_ID", Integer.valueOf(XX_DisbursementType_ID));
        
    }
    
    /** Get Disbursement Type.
    @return Type of disbursement */
    public int getXX_DisbursementType_ID() 
    {
        return get_ValueAsInt("XX_DisbursementType_ID");
        
    }
    
    
}
