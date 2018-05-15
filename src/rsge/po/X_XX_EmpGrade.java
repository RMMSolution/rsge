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
/** Generated Model for XX_EmpGrade
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_EmpGrade extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_EmpGrade_ID id
    @param trx transaction
    */
    public X_XX_EmpGrade (Ctx ctx, int XX_EmpGrade_ID, Trx trx)
    {
        super (ctx, XX_EmpGrade_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_EmpGrade_ID == 0)
        {
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_EmpGrade (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27668836728789L;
    /** Last Updated Timestamp 2013-12-11 04:36:52.0 */
    public static final long updatedMS = 1386711412000L;
    /** AD_Table_ID=1000718 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_EmpGrade");
        
    }
    ;
    
    /** TableName=XX_EmpGrade */
    public static final String Table_Name="XX_EmpGrade";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    
}
