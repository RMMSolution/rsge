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


/** BudgetCutOffMonth AD_Reference_ID=1000402 */
public enum X_Ref__Budget_Month 
{
    /** Jan = 00 */
    JAN("00"),
    /** February = 01 */
    FEBRUARY("01"),
    /** March = 02 */
    MARCH("02"),
    /** April = 03 */
    APRIL("03"),
    /** May = 04 */
    MAY("04"),
    /** June = 05 */
    JUNE("05"),
    /** July = 06 */
    JULY("06"),
    /** August = 07 */
    AUGUST("07"),
    /** September = 08 */
    SEPTEMBER("08"),
    /** October = 09 */
    OCTOBER("09"),
    /** November = 10 */
    NOVEMBER("10"),
    /** December = 11 */
    DECEMBER("11");
    
    public static final int AD_Reference_ID=1000402;
    private final String value;
    private X_Ref__Budget_Month(String value)
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
         for( X_Ref__Budget_Month v : X_Ref__Budget_Month.values())
        {
             if( v.getValue().equals(test)) return true;
             
        }
        return false;
        
    }
    
}
