/**
 * 
 */
package rsge.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.compiere.model.MCurrency;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.model.MAssetGroup;

/**
 * @author FANNY R
 *
 */
public class CalculateFirstYearDepreciation {

	/** Ctx and Trx						*/
	Ctx 						ctx = null;
	Trx							trx = null;
	
	/** Depreciation Method				*/
	private String				depreciationMethod = null;
	/** Asset Life Year					*/
	private int					assetLifeYear = 0;
	/** Asset Life Month				*/
	private int					assetLifeMonth = 0;
	/** Asset Life						*/
	private BigDecimal			assetLife = BigDecimal.ZERO;
	
	/** Asset Cost						*/
	private BigDecimal			assetCost = BigDecimal.ZERO;
	
	
	/** Depreciation Rate 				*/
	private BigDecimal			depreciationRate = BigDecimal.ZERO;
	
	/** Asset First Year Depreciation	*/
	private BigDecimal			firstYearDepreciation = BigDecimal.ZERO;
	
	/**
	 * 
	 */
	public CalculateFirstYearDepreciation(Ctx ctx, BigDecimal assetCost, String depreciationMethod, int lifeYear, int lifeMonth, Trx trx) {
		// Initialize variable
		this.ctx = ctx;
		this.trx = trx;
		this.assetCost = assetCost;
		this.depreciationMethod = depreciationMethod;
		this.assetLifeYear = lifeYear;
		this.assetLifeMonth = lifeMonth;	
		
		BigDecimal fractionMonth = BigDecimal.valueOf(assetLifeMonth).divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_EVEN);
		assetLife = BigDecimal.valueOf(assetLifeYear).add(fractionMonth);
		
		firstYearDepreciation = calculateDepreciation();
		
	}
	
	private BigDecimal calculateDepreciation()
	{
		BigDecimal depreciationAmt = BigDecimal.ZERO;
		
//		if(depreciationMethod.equals(MAssetGroup.BUDGETDEPRECIATIONMETHOD_StraightLine))
		if(depreciationMethod.equalsIgnoreCase("SL"))
		{
			// Get Depreciation Rate (1/assetLife)
			depreciationRate = BigDecimal.ONE.divide(assetLife, 4, RoundingMode.HALF_EVEN);
		}
		else if(depreciationMethod.equalsIgnoreCase("DL"))
		{
			// Get Depreciation Rate (1/assetLife) * 2
			depreciationRate = (BigDecimal.ONE.divide(assetLife, 4, RoundingMode.HALF_EVEN)).multiply(BigDecimal.valueOf(2));
		}
		depreciationAmt = assetCost.multiply(depreciationRate);
		
		return depreciationAmt;
	}	

	
	public BigDecimal getDepreciationAmount()
	{
		// Verify depreciation amount scale = budget currency standard precision
		MCurrency currency = new MCurrency(ctx, BudgetUtils.getBudgetCurrencyID(ctx, ctx.getAD_Client_ID(), trx), trx);
		firstYearDepreciation = firstYearDepreciation.setScale(currency.getStdPrecision(), RoundingMode.HALF_UP);
		
		return firstYearDepreciation;
	}

}

