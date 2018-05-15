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
/** Generated Model for XX_PurchaseRequisitionLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_PurchaseRequisitionLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_PurchaseRequisitionLine_ID id
    @param trx transaction
    */
    public X_XX_PurchaseRequisitionLine (Ctx ctx, int XX_PurchaseRequisitionLine_ID, Trx trx)
    {
        super (ctx, XX_PurchaseRequisitionLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_PurchaseRequisitionLine_ID == 0)
        {
            setIsCalculateBudget (false);	// N
            setIsChargedToAccount (false);	// N
            setIsCheckBudgetQty (false);	// N
            setIsLocked (false);	// N
            setIsOverBudget (false);	// N
            setIsQtyOverBudget (false);	// N
            setIsRecheckBudget (false);	// N
            setLine (0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM XX_PurchaseRequisitionLine WHERE XX_PurchaseRequisition_ID = @XX_PurchaseRequisition_ID@
            setXX_PurchaseRequisition_ID (0);
            setXX_PurchaseRequisitionLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_PurchaseRequisitionLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27803962395192L;
    /** Last Updated Timestamp 2018-03-24 03:31:18.403 */
    public static final long updatedMS = 1521837078403L;
    /** AD_Table_ID=1000175 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_PurchaseRequisitionLine");
        
    }
    ;
    
    /** TableName=XX_PurchaseRequisitionLine */
    public static final String Table_Name="XX_PurchaseRequisitionLine";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Account.
    @param Account_ID Account used */
    public void setAccount_ID (int Account_ID)
    {
        if (Account_ID <= 0) set_Value ("Account_ID", null);
        else
        set_Value ("Account_ID", Integer.valueOf(Account_ID));
        
    }
    
    /** Get Account.
    @return Account used */
    public int getAccount_ID() 
    {
        return get_ValueAsInt("Account_ID");
        
    }
    
    /** Set Amount.
    @param Amount Amount in a defined currency */
    public void setAmount (java.math.BigDecimal Amount)
    {
        set_Value ("Amount", Amount);
        
    }
    
    /** Get Amount.
    @return Amount in a defined currency */
    public java.math.BigDecimal getAmount() 
    {
        return get_ValueAsBigDecimal("Amount");
        
    }
    
    /** Set Budget Charge.
    @param BudgetCharge_ID Budget Charge */
    public void setBudgetCharge_ID (int BudgetCharge_ID)
    {
        if (BudgetCharge_ID <= 0) set_Value ("BudgetCharge_ID", null);
        else
        set_Value ("BudgetCharge_ID", Integer.valueOf(BudgetCharge_ID));
        
    }
    
    /** Get Budget Charge.
    @return Budget Charge */
    public int getBudgetCharge_ID() 
    {
        return get_ValueAsInt("BudgetCharge_ID");
        
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
    
    /** Set Order Line.
    @param C_OrderLine_ID Order Line */
    public void setC_OrderLine_ID (int C_OrderLine_ID)
    {
        if (C_OrderLine_ID <= 0) set_Value ("C_OrderLine_ID", null);
        else
        set_Value ("C_OrderLine_ID", Integer.valueOf(C_OrderLine_ID));
        
    }
    
    /** Get Order Line.
    @return Order Line */
    public int getC_OrderLine_ID() 
    {
        return get_ValueAsInt("C_OrderLine_ID");
        
    }
    
    /** Set UOM.
    @param C_UOM_ID Unit of Measure */
    public void setC_UOM_ID (int C_UOM_ID)
    {
        if (C_UOM_ID <= 0) set_Value ("C_UOM_ID", null);
        else
        set_Value ("C_UOM_ID", Integer.valueOf(C_UOM_ID));
        
    }
    
    /** Get UOM.
    @return Unit of Measure */
    public int getC_UOM_ID() 
    {
        return get_ValueAsInt("C_UOM_ID");
        
    }
    
    /** Set Converted Amount.
    @param ConvertedAmt Converted Amount */
    public void setConvertedAmt (java.math.BigDecimal ConvertedAmt)
    {
        set_Value ("ConvertedAmt", ConvertedAmt);
        
    }
    
    /** Get Converted Amount.
    @return Converted Amount */
    public java.math.BigDecimal getConvertedAmt() 
    {
        return get_ValueAsBigDecimal("ConvertedAmt");
        
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
    
    /** Set Calculate Budget.
    @param IsCalculateBudget Calculate budget for this entity */
    public void setIsCalculateBudget (boolean IsCalculateBudget)
    {
        set_Value ("IsCalculateBudget", Boolean.valueOf(IsCalculateBudget));
        
    }
    
    /** Get Calculate Budget.
    @return Calculate budget for this entity */
    public boolean isCalculateBudget() 
    {
        return get_ValueAsBoolean("IsCalculateBudget");
        
    }
    
    /** Set Charge to Account.
    @param IsChargedToAccount Charge to Account */
    public void setIsChargedToAccount (boolean IsChargedToAccount)
    {
        set_Value ("IsChargedToAccount", Boolean.valueOf(IsChargedToAccount));
        
    }
    
    /** Get Charge to Account.
    @return Charge to Account */
    public boolean isChargedToAccount() 
    {
        return get_ValueAsBoolean("IsChargedToAccount");
        
    }
    
    /** Set Organization Qty.
    @param IsCheckBudgetQty This rule will check all quantity of product in an organization */
    public void setIsCheckBudgetQty (boolean IsCheckBudgetQty)
    {
        set_Value ("IsCheckBudgetQty", Boolean.valueOf(IsCheckBudgetQty));
        
    }
    
    /** Get Organization Qty.
    @return This rule will check all quantity of product in an organization */
    public boolean isCheckBudgetQty() 
    {
        return get_ValueAsBoolean("IsCheckBudgetQty");
        
    }
    
    /** Set Lock.
    @param IsLocked Lock */
    public void setIsLocked (boolean IsLocked)
    {
        set_Value ("IsLocked", Boolean.valueOf(IsLocked));
        
    }
    
    /** Get Lock.
    @return Lock */
    public boolean isLocked() 
    {
        return get_ValueAsBoolean("IsLocked");
        
    }
    
    /** Set Over Budget.
    @param IsOverBudget This record is over budget */
    public void setIsOverBudget (boolean IsOverBudget)
    {
        set_Value ("IsOverBudget", Boolean.valueOf(IsOverBudget));
        
    }
    
    /** Get Over Budget.
    @return This record is over budget */
    public boolean isOverBudget() 
    {
        return get_ValueAsBoolean("IsOverBudget");
        
    }
    
    /** Set Quantity Over Budget.
    @param IsQtyOverBudget Quantity over budget */
    public void setIsQtyOverBudget (boolean IsQtyOverBudget)
    {
        set_Value ("IsQtyOverBudget", Boolean.valueOf(IsQtyOverBudget));
        
    }
    
    /** Get Quantity Over Budget.
    @return Quantity over budget */
    public boolean isQtyOverBudget() 
    {
        return get_ValueAsBoolean("IsQtyOverBudget");
        
    }
    
    /** Set Recheck Budget.
    @param IsRecheckBudget Recheck budget */
    public void setIsRecheckBudget (boolean IsRecheckBudget)
    {
        set_Value ("IsRecheckBudget", Boolean.valueOf(IsRecheckBudget));
        
    }
    
    /** Get Recheck Budget.
    @return Recheck budget */
    public boolean isRecheckBudget() 
    {
        return get_ValueAsBoolean("IsRecheckBudget");
        
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
    
    /** Set Line Amount.
    @param LineNetAmt Line Extended Amount (Quantity * Actual Price) without Freight and Charges */
    public void setLineNetAmt (java.math.BigDecimal LineNetAmt)
    {
        set_Value ("LineNetAmt", LineNetAmt);
        
    }
    
    /** Get Line Amount.
    @return Line Extended Amount (Quantity * Actual Price) without Freight and Charges */
    public java.math.BigDecimal getLineNetAmt() 
    {
        return get_ValueAsBigDecimal("LineNetAmt");
        
    }
    
    /** Set Attribute Set Instance.
    @param M_AttributeSetInstance_ID Product Attribute Set Instance */
    public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
    {
        if (M_AttributeSetInstance_ID <= 0) set_Value ("M_AttributeSetInstance_ID", null);
        else
        set_Value ("M_AttributeSetInstance_ID", Integer.valueOf(M_AttributeSetInstance_ID));
        
    }
    
    /** Get Attribute Set Instance.
    @return Product Attribute Set Instance */
    public int getM_AttributeSetInstance_ID() 
    {
        return get_ValueAsInt("M_AttributeSetInstance_ID");
        
    }
    
    /** Set Product Category.
    @param M_Product_Category_ID Category of a Product */
    public void setM_Product_Category_ID (int M_Product_Category_ID)
    {
        if (M_Product_Category_ID <= 0) set_Value ("M_Product_Category_ID", null);
        else
        set_Value ("M_Product_Category_ID", Integer.valueOf(M_Product_Category_ID));
        
    }
    
    /** Get Product Category.
    @return Category of a Product */
    public int getM_Product_Category_ID() 
    {
        return get_ValueAsInt("M_Product_Category_ID");
        
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
    
    /** Set Requisition Line.
    @param M_RequisitionLine_ID Material Requisition Line */
    public void setM_RequisitionLine_ID (int M_RequisitionLine_ID)
    {
        if (M_RequisitionLine_ID <= 0) set_Value ("M_RequisitionLine_ID", null);
        else
        set_Value ("M_RequisitionLine_ID", Integer.valueOf(M_RequisitionLine_ID));
        
    }
    
    /** Get Requisition Line.
    @return Material Requisition Line */
    public int getM_RequisitionLine_ID() 
    {
        return get_ValueAsInt("M_RequisitionLine_ID");
        
    }
    
    /** Set Over Budget Amount.
    @param OverBudgetAmt Amount exceed budget allocated */
    public void setOverBudgetAmt (java.math.BigDecimal OverBudgetAmt)
    {
        set_Value ("OverBudgetAmt", OverBudgetAmt);
        
    }
    
    /** Get Over Budget Amount.
    @return Amount exceed budget allocated */
    public java.math.BigDecimal getOverBudgetAmt() 
    {
        return get_ValueAsBigDecimal("OverBudgetAmt");
        
    }
    
    /** Set Over Budget Quantity.
    @param OverBudgetQty Quantity exceed budget allocated */
    public void setOverBudgetQty (java.math.BigDecimal OverBudgetQty)
    {
        set_Value ("OverBudgetQty", OverBudgetQty);
        
    }
    
    /** Get Over Budget Quantity.
    @return Quantity exceed budget allocated */
    public java.math.BigDecimal getOverBudgetQty() 
    {
        return get_ValueAsBigDecimal("OverBudgetQty");
        
    }
    
    /** Set Period Budget.
    @param PeriodBudgetAmt Period budget amount */
    public void setPeriodBudgetAmt (java.math.BigDecimal PeriodBudgetAmt)
    {
        set_Value ("PeriodBudgetAmt", PeriodBudgetAmt);
        
    }
    
    /** Get Period Budget.
    @return Period budget amount */
    public java.math.BigDecimal getPeriodBudgetAmt() 
    {
        return get_ValueAsBigDecimal("PeriodBudgetAmt");
        
    }
    
    /** Set Unit Price.
    @param PriceActual Actual Price */
    public void setPriceActual (java.math.BigDecimal PriceActual)
    {
        set_Value ("PriceActual", PriceActual);
        
    }
    
    /** Get Unit Price.
    @return Actual Price */
    public java.math.BigDecimal getPriceActual() 
    {
        return get_ValueAsBigDecimal("PriceActual");
        
    }
    
    /** Set Quantity.
    @param Qty Quantity */
    public void setQty (java.math.BigDecimal Qty)
    {
        set_Value ("Qty", Qty);
        
    }
    
    /** Get Quantity.
    @return Quantity */
    public java.math.BigDecimal getQty() 
    {
        return get_ValueAsBigDecimal("Qty");
        
    }
    
    /** Set Quantity Delivered.
    @param QtyDelivered Quantity Delivered */
    public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
    {
        set_Value ("QtyDelivered", QtyDelivered);
        
    }
    
    /** Get Quantity Delivered.
    @return Quantity Delivered */
    public java.math.BigDecimal getQtyDelivered() 
    {
        return get_ValueAsBigDecimal("QtyDelivered");
        
    }
    
    /** Set Quantity Ordered.
    @param QtyOrdered Ordered Quantity */
    public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
    {
        set_Value ("QtyOrdered", QtyOrdered);
        
    }
    
    /** Get Quantity Ordered.
    @return Ordered Quantity */
    public java.math.BigDecimal getQtyOrdered() 
    {
        return get_ValueAsBigDecimal("QtyOrdered");
        
    }
    
    /** Set Remaining Budget.
    @param RemainingBudget Remaining budget amount for selected account */
    public void setRemainingBudget (java.math.BigDecimal RemainingBudget)
    {
        set_Value ("RemainingBudget", RemainingBudget);
        
    }
    
    /** Get Remaining Budget.
    @return Remaining budget amount for selected account */
    public java.math.BigDecimal getRemainingBudget() 
    {
        return get_ValueAsBigDecimal("RemainingBudget");
        
    }
    
    /** Set Remaining Qty.
    @param RemainingBudgetQty Remaning Quantity */
    public void setRemainingBudgetQty (java.math.BigDecimal RemainingBudgetQty)
    {
        set_Value ("RemainingBudgetQty", RemainingBudgetQty);
        
    }
    
    /** Get Remaining Qty.
    @return Remaning Quantity */
    public java.math.BigDecimal getRemainingBudgetQty() 
    {
        return get_ValueAsBigDecimal("RemainingBudgetQty");
        
    }
    
    /** Set Unrealized amount.
    @param UnrealizedAmt Amount which has not been realized yet */
    public void setUnrealizedAmt (java.math.BigDecimal UnrealizedAmt)
    {
        set_Value ("UnrealizedAmt", UnrealizedAmt);
        
    }
    
    /** Get Unrealized amount.
    @return Amount which has not been realized yet */
    public java.math.BigDecimal getUnrealizedAmt() 
    {
        return get_ValueAsBigDecimal("UnrealizedAmt");
        
    }
    
    /** Set Requisition.
    @param XX_PurchaseRequisition_ID User Requisition */
    public void setXX_PurchaseRequisition_ID (int XX_PurchaseRequisition_ID)
    {
        if (XX_PurchaseRequisition_ID < 1) throw new IllegalArgumentException ("XX_PurchaseRequisition_ID is mandatory.");
        set_Value ("XX_PurchaseRequisition_ID", Integer.valueOf(XX_PurchaseRequisition_ID));
        
    }
    
    /** Get Requisition.
    @return User Requisition */
    public int getXX_PurchaseRequisition_ID() 
    {
        return get_ValueAsInt("XX_PurchaseRequisition_ID");
        
    }
    
    /** Set Requisition Line.
    @param XX_PurchaseRequisitionLine_ID Requisition Line */
    public void setXX_PurchaseRequisitionLine_ID (int XX_PurchaseRequisitionLine_ID)
    {
        if (XX_PurchaseRequisitionLine_ID < 1) throw new IllegalArgumentException ("XX_PurchaseRequisitionLine_ID is mandatory.");
        set_ValueNoCheck ("XX_PurchaseRequisitionLine_ID", Integer.valueOf(XX_PurchaseRequisitionLine_ID));
        
    }
    
    /** Get Requisition Line.
    @return Requisition Line */
    public int getXX_PurchaseRequisitionLine_ID() 
    {
        return get_ValueAsInt("XX_PurchaseRequisitionLine_ID");
        
    }
    
    
}
