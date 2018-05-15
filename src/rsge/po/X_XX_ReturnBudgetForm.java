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
/** Generated Model for XX_ReturnBudgetForm
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ReturnBudgetForm extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ReturnBudgetForm_ID id
    @param trx transaction
    */
    public X_XX_ReturnBudgetForm (Ctx ctx, int XX_ReturnBudgetForm_ID, Trx trx)
    {
        super (ctx, XX_ReturnBudgetForm_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ReturnBudgetForm_ID == 0)
        {
            setIsShowM (false);	// N
            setIsShowMQ (false);	// N
            setLine (0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM XX_ReturnBudgetForm WHERE XX_CustomerBudgetForm_ID = @XX_CustomerBudgetForm_ID@
            setXX_CustomerBudgetForm_ID (0);
            setXX_ReturnBudgetForm_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ReturnBudgetForm (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27760213179602L;
    /** Last Updated Timestamp 2016-11-02 18:57:42.813 */
    public static final long updatedMS = 1478087862813L;
    /** AD_Table_ID=1000183 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ReturnBudgetForm");
        
    }
    ;
    
    /** TableName=XX_ReturnBudgetForm */
    public static final String Table_Name="XX_ReturnBudgetForm";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Activity.
    @param C_Activity_ID Business Activity */
    public void setC_Activity_ID (int C_Activity_ID)
    {
        if (C_Activity_ID <= 0) set_Value ("C_Activity_ID", null);
        else
        set_Value ("C_Activity_ID", Integer.valueOf(C_Activity_ID));
        
    }
    
    /** Get Activity.
    @return Business Activity */
    public int getC_Activity_ID() 
    {
        return get_ValueAsInt("C_Activity_ID");
        
    }
    
    /** Set Project.
    @param C_Project_ID Financial Project */
    public void setC_Project_ID (int C_Project_ID)
    {
        if (C_Project_ID <= 0) set_Value ("C_Project_ID", null);
        else
        set_Value ("C_Project_ID", Integer.valueOf(C_Project_ID));
        
    }
    
    /** Get Project.
    @return Financial Project */
    public int getC_Project_ID() 
    {
        return get_ValueAsInt("C_Project_ID");
        
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
    
    /** Set Show M.
    @param IsShowM Show Monthly only */
    public void setIsShowM (boolean IsShowM)
    {
        set_Value ("IsShowM", Boolean.valueOf(IsShowM));
        
    }
    
    /** Get Show M.
    @return Show Monthly only */
    public boolean isShowM() 
    {
        return get_ValueAsBoolean("IsShowM");
        
    }
    
    /** Set Show MQ.
    @param IsShowMQ Show Monthly - Quarter */
    public void setIsShowMQ (boolean IsShowMQ)
    {
        set_Value ("IsShowMQ", Boolean.valueOf(IsShowMQ));
        
    }
    
    /** Get Show MQ.
    @return Show Monthly - Quarter */
    public boolean isShowMQ() 
    {
        return get_ValueAsBoolean("IsShowMQ");
        
    }
    
    /** Set Line No.
    @param Line Unique line for this document */
    public void setLine (int Line)
    {
        set_Value ("Line", Integer.valueOf(Line));
        
    }
    
    /** Get Line No.
    @return Unique line for this document */
    public int getLine() 
    {
        return get_ValueAsInt("Line");
        
    }
    
    /** Set Product.
    @param M_Product_ID Product, Service, Item */
    public void setM_Product_ID (int M_Product_ID)
    {
        if (M_Product_ID <= 0) set_Value ("M_Product_ID", null);
        else
        set_Value ("M_Product_ID", Integer.valueOf(M_Product_ID));
        
    }
    
    /** Get Product.
    @return Product, Service, Item */
    public int getM_Product_ID() 
    {
        return get_ValueAsInt("M_Product_ID");
        
    }
    
    /** Set 1st Period.
    @param Period1 First period budget amount */
    public void setPeriod1 (java.math.BigDecimal Period1)
    {
        set_Value ("Period1", Period1);
        
    }
    
    /** Get 1st Period.
    @return First period budget amount */
    public java.math.BigDecimal getPeriod1() 
    {
        return get_ValueAsBigDecimal("Period1");
        
    }
    
    /** Set 10th Period.
    @param Period10 Tenth period budget amount */
    public void setPeriod10 (java.math.BigDecimal Period10)
    {
        set_Value ("Period10", Period10);
        
    }
    
    /** Get 10th Period.
    @return Tenth period budget amount */
    public java.math.BigDecimal getPeriod10() 
    {
        return get_ValueAsBigDecimal("Period10");
        
    }
    
    /** Set 11th Period.
    @param Period11 Eleventh period budget amount */
    public void setPeriod11 (java.math.BigDecimal Period11)
    {
        set_Value ("Period11", Period11);
        
    }
    
    /** Get 11th Period.
    @return Eleventh period budget amount */
    public java.math.BigDecimal getPeriod11() 
    {
        return get_ValueAsBigDecimal("Period11");
        
    }
    
    /** Set 12th Period.
    @param Period12 Twelveth period budget amount */
    public void setPeriod12 (java.math.BigDecimal Period12)
    {
        set_Value ("Period12", Period12);
        
    }
    
    /** Get 12th Period.
    @return Twelveth period budget amount */
    public java.math.BigDecimal getPeriod12() 
    {
        return get_ValueAsBigDecimal("Period12");
        
    }
    
    /** Set 2nd Period.
    @param Period2 Second period budget amount */
    public void setPeriod2 (java.math.BigDecimal Period2)
    {
        set_Value ("Period2", Period2);
        
    }
    
    /** Get 2nd Period.
    @return Second period budget amount */
    public java.math.BigDecimal getPeriod2() 
    {
        return get_ValueAsBigDecimal("Period2");
        
    }
    
    /** Set 3rd Period.
    @param Period3 Third period budget amount */
    public void setPeriod3 (java.math.BigDecimal Period3)
    {
        set_Value ("Period3", Period3);
        
    }
    
    /** Get 3rd Period.
    @return Third period budget amount */
    public java.math.BigDecimal getPeriod3() 
    {
        return get_ValueAsBigDecimal("Period3");
        
    }
    
    /** Set 4th Period.
    @param Period4 Fourth period budget amount */
    public void setPeriod4 (java.math.BigDecimal Period4)
    {
        set_Value ("Period4", Period4);
        
    }
    
    /** Get 4th Period.
    @return Fourth period budget amount */
    public java.math.BigDecimal getPeriod4() 
    {
        return get_ValueAsBigDecimal("Period4");
        
    }
    
    /** Set 5th Period.
    @param Period5 Fifth period budget amount */
    public void setPeriod5 (java.math.BigDecimal Period5)
    {
        set_Value ("Period5", Period5);
        
    }
    
    /** Get 5th Period.
    @return Fifth period budget amount */
    public java.math.BigDecimal getPeriod5() 
    {
        return get_ValueAsBigDecimal("Period5");
        
    }
    
    /** Set 6th Period.
    @param Period6 Sixth period budget amount */
    public void setPeriod6 (java.math.BigDecimal Period6)
    {
        set_Value ("Period6", Period6);
        
    }
    
    /** Get 6th Period.
    @return Sixth period budget amount */
    public java.math.BigDecimal getPeriod6() 
    {
        return get_ValueAsBigDecimal("Period6");
        
    }
    
    /** Set 7th Period.
    @param Period7 Seventh period budget amount */
    public void setPeriod7 (java.math.BigDecimal Period7)
    {
        set_Value ("Period7", Period7);
        
    }
    
    /** Get 7th Period.
    @return Seventh period budget amount */
    public java.math.BigDecimal getPeriod7() 
    {
        return get_ValueAsBigDecimal("Period7");
        
    }
    
    /** Set 8th Period.
    @param Period8 Eighth period budget amount */
    public void setPeriod8 (java.math.BigDecimal Period8)
    {
        set_Value ("Period8", Period8);
        
    }
    
    /** Get 8th Period.
    @return Eighth period budget amount */
    public java.math.BigDecimal getPeriod8() 
    {
        return get_ValueAsBigDecimal("Period8");
        
    }
    
    /** Set 9th Period.
    @param Period9 Ninth period budget amount */
    public void setPeriod9 (java.math.BigDecimal Period9)
    {
        set_Value ("Period9", Period9);
        
    }
    
    /** Get 9th Period.
    @return Ninth period budget amount */
    public java.math.BigDecimal getPeriod9() 
    {
        return get_ValueAsBigDecimal("Period9");
        
    }
    
    /** Set Product Name.
    @param ProductName Name of the Product */
    public void setProductName (String ProductName)
    {
        set_Value ("ProductName", ProductName);
        
    }
    
    /** Get Product Name.
    @return Name of the Product */
    public String getProductName() 
    {
        return (String)get_Value("ProductName");
        
    }
    
    /** Set Qty Period 1.
    @param QtyPeriod1 Qty in first period */
    public void setQtyPeriod1 (java.math.BigDecimal QtyPeriod1)
    {
        set_Value ("QtyPeriod1", QtyPeriod1);
        
    }
    
    /** Get Qty Period 1.
    @return Qty in first period */
    public java.math.BigDecimal getQtyPeriod1() 
    {
        return get_ValueAsBigDecimal("QtyPeriod1");
        
    }
    
    /** Set Qty Period 10.
    @param QtyPeriod10 Qty in tenth period */
    public void setQtyPeriod10 (java.math.BigDecimal QtyPeriod10)
    {
        set_Value ("QtyPeriod10", QtyPeriod10);
        
    }
    
    /** Get Qty Period 10.
    @return Qty in tenth period */
    public java.math.BigDecimal getQtyPeriod10() 
    {
        return get_ValueAsBigDecimal("QtyPeriod10");
        
    }
    
    /** Set Qty Period 11.
    @param QtyPeriod11 Qty in eleventh period */
    public void setQtyPeriod11 (java.math.BigDecimal QtyPeriod11)
    {
        set_Value ("QtyPeriod11", QtyPeriod11);
        
    }
    
    /** Get Qty Period 11.
    @return Qty in eleventh period */
    public java.math.BigDecimal getQtyPeriod11() 
    {
        return get_ValueAsBigDecimal("QtyPeriod11");
        
    }
    
    /** Set Qty Period 12.
    @param QtyPeriod12 Qty in twelveth period */
    public void setQtyPeriod12 (java.math.BigDecimal QtyPeriod12)
    {
        set_Value ("QtyPeriod12", QtyPeriod12);
        
    }
    
    /** Get Qty Period 12.
    @return Qty in twelveth period */
    public java.math.BigDecimal getQtyPeriod12() 
    {
        return get_ValueAsBigDecimal("QtyPeriod12");
        
    }
    
    /** Set Qty Period 2.
    @param QtyPeriod2 Qty in second period */
    public void setQtyPeriod2 (java.math.BigDecimal QtyPeriod2)
    {
        set_Value ("QtyPeriod2", QtyPeriod2);
        
    }
    
    /** Get Qty Period 2.
    @return Qty in second period */
    public java.math.BigDecimal getQtyPeriod2() 
    {
        return get_ValueAsBigDecimal("QtyPeriod2");
        
    }
    
    /** Set Qty Period 3.
    @param QtyPeriod3 Qty in third period */
    public void setQtyPeriod3 (java.math.BigDecimal QtyPeriod3)
    {
        set_Value ("QtyPeriod3", QtyPeriod3);
        
    }
    
    /** Get Qty Period 3.
    @return Qty in third period */
    public java.math.BigDecimal getQtyPeriod3() 
    {
        return get_ValueAsBigDecimal("QtyPeriod3");
        
    }
    
    /** Set Qty Period 4.
    @param QtyPeriod4 Qty in forth period */
    public void setQtyPeriod4 (java.math.BigDecimal QtyPeriod4)
    {
        set_Value ("QtyPeriod4", QtyPeriod4);
        
    }
    
    /** Get Qty Period 4.
    @return Qty in forth period */
    public java.math.BigDecimal getQtyPeriod4() 
    {
        return get_ValueAsBigDecimal("QtyPeriod4");
        
    }
    
    /** Set Qty Period 5.
    @param QtyPeriod5 Qty in fifth period */
    public void setQtyPeriod5 (java.math.BigDecimal QtyPeriod5)
    {
        set_Value ("QtyPeriod5", QtyPeriod5);
        
    }
    
    /** Get Qty Period 5.
    @return Qty in fifth period */
    public java.math.BigDecimal getQtyPeriod5() 
    {
        return get_ValueAsBigDecimal("QtyPeriod5");
        
    }
    
    /** Set Qty Period 6.
    @param QtyPeriod6 Qty in sixth period */
    public void setQtyPeriod6 (java.math.BigDecimal QtyPeriod6)
    {
        set_Value ("QtyPeriod6", QtyPeriod6);
        
    }
    
    /** Get Qty Period 6.
    @return Qty in sixth period */
    public java.math.BigDecimal getQtyPeriod6() 
    {
        return get_ValueAsBigDecimal("QtyPeriod6");
        
    }
    
    /** Set Qty Period 7.
    @param QtyPeriod7 Qty in seventh period */
    public void setQtyPeriod7 (java.math.BigDecimal QtyPeriod7)
    {
        set_Value ("QtyPeriod7", QtyPeriod7);
        
    }
    
    /** Get Qty Period 7.
    @return Qty in seventh period */
    public java.math.BigDecimal getQtyPeriod7() 
    {
        return get_ValueAsBigDecimal("QtyPeriod7");
        
    }
    
    /** Set Qty Period 8.
    @param QtyPeriod8 Qty in eight period */
    public void setQtyPeriod8 (java.math.BigDecimal QtyPeriod8)
    {
        set_Value ("QtyPeriod8", QtyPeriod8);
        
    }
    
    /** Get Qty Period 8.
    @return Qty in eight period */
    public java.math.BigDecimal getQtyPeriod8() 
    {
        return get_ValueAsBigDecimal("QtyPeriod8");
        
    }
    
    /** Set Qty Period 9.
    @param QtyPeriod9 Qty in ninth period */
    public void setQtyPeriod9 (java.math.BigDecimal QtyPeriod9)
    {
        set_Value ("QtyPeriod9", QtyPeriod9);
        
    }
    
    /** Get Qty Period 9.
    @return Qty in ninth period */
    public java.math.BigDecimal getQtyPeriod9() 
    {
        return get_ValueAsBigDecimal("QtyPeriod9");
        
    }
    
    /** Set Total Amount.
    @param TotalAmt Total Amount */
    public void setTotalAmt (java.math.BigDecimal TotalAmt)
    {
        set_Value ("TotalAmt", TotalAmt);
        
    }
    
    /** Get Total Amount.
    @return Total Amount */
    public java.math.BigDecimal getTotalAmt() 
    {
        return get_ValueAsBigDecimal("TotalAmt");
        
    }
    
    /** Set Total Quantity.
    @param TotalQty Total Quantity */
    public void setTotalQty (java.math.BigDecimal TotalQty)
    {
        set_Value ("TotalQty", TotalQty);
        
    }
    
    /** Get Total Quantity.
    @return Total Quantity */
    public java.math.BigDecimal getTotalQty() 
    {
        return get_ValueAsBigDecimal("TotalQty");
        
    }
    
    /** Set XX_CustomerBudgetForm_ID.
    @param XX_CustomerBudgetForm_ID XX_CustomerBudgetForm_ID */
    public void setXX_CustomerBudgetForm_ID (int XX_CustomerBudgetForm_ID)
    {
        if (XX_CustomerBudgetForm_ID < 1) throw new IllegalArgumentException ("XX_CustomerBudgetForm_ID is mandatory.");
        set_Value ("XX_CustomerBudgetForm_ID", Integer.valueOf(XX_CustomerBudgetForm_ID));
        
    }
    
    /** Get XX_CustomerBudgetForm_ID.
    @return XX_CustomerBudgetForm_ID */
    public int getXX_CustomerBudgetForm_ID() 
    {
        return get_ValueAsInt("XX_CustomerBudgetForm_ID");
        
    }
    
    /** Set Product Return.
    @param XX_ReturnBudgetForm_ID Product Return */
    public void setXX_ReturnBudgetForm_ID (int XX_ReturnBudgetForm_ID)
    {
        if (XX_ReturnBudgetForm_ID < 1) throw new IllegalArgumentException ("XX_ReturnBudgetForm_ID is mandatory.");
        set_ValueNoCheck ("XX_ReturnBudgetForm_ID", Integer.valueOf(XX_ReturnBudgetForm_ID));
        
    }
    
    /** Get Product Return.
    @return Product Return */
    public int getXX_ReturnBudgetForm_ID() 
    {
        return get_ValueAsInt("XX_ReturnBudgetForm_ID");
        
    }
    
    
}
