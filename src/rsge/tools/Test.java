package rsge.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Test {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<Integer, BigDecimal> clrg = new HashMap<Integer, BigDecimal>();
		clrg.put(100, BigDecimal.valueOf(1000));
		clrg.put(200, BigDecimal.valueOf(1500));
		
		BigDecimal a = BigDecimal.valueOf(1250);
		Integer k = 100;
		for (Map.Entry<Integer, BigDecimal> entry : clrg.entrySet()) {
			if(k.equals(entry.getKey()))
			{
				BigDecimal amt = entry.getValue();
				amt = amt.subtract(a);
				clrg.put(k, amt);
			}
		}
		printMap(clrg);

	}
	
	public static void printMap(Map<Integer, BigDecimal> map) {
		for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() 
                                      + " Amount : " + entry.getValue());
		}
	}
	

}
