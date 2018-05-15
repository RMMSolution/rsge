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


/** FinancialSummaryType AD_Reference_ID=1002624 */
public enum X_Ref__Financial_Summary_Type 
{
    /** Current Asset = CA */
    CURRENT_ASSET("CA"),
    /** Current Liability = CL */
    CURRENT_LIABILITY("CL"),
    /** COGM = CM */
    COGM("CM"),
    /** COGS = CS */
    COGS("CS"),
    /** EAT = EA */
    EAT("EA"),
    /** EBIT = EB */
    EBIT("EB"),
    /** EBITDA = ED */
    EBITDA("ED"),
    /** Gross Profit = GP */
    GROSS_PROFIT("GP"),
    /** Operating Profit = OP */
    OPERATING_PROFIT("OP"),
    /** Total Asset = TA */
    TOTAL_ASSET("TA"),
    /** Total Expense = TE */
    TOTAL_EXPENSE("TE"),
    /** Total Liability = TL */
    TOTAL_LIABILITY("TL"),
    /** Total Equity = TO */
    TOTAL_EQUITY("TO"),
    /** Total Revenue = TR */
    TOTAL_REVENUE("TR");
    
    public static final int AD_Reference_ID=1002624;
    private final String value;
    private X_Ref__Financial_Summary_Type(String value)
    {
         this.value = value;
         
    }
    public String getValue() 
    {
         return this.value;
         
    }
    public static boolean isValid(String test) 
    {
         if( test == null ) return true;
         for( X_Ref__Financial_Summary_Type v : X_Ref__Financial_Summary_Type.values())
        {
             if( v.getValue().equals(test)) return true;
             
        }
        return false;
        
    }
    
}
