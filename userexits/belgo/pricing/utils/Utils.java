package userexits.belgo.pricing.utils;

import java.math.BigDecimal;

import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;

public class Utils {

	/**
	 * Diêgo Silva - 04.11.2014 Recupera uma condição de item específica
	 * 
	 * @param pricingItem
	 * @param condition
	 * @return
	 */
	public static IPricingConditionUserExit GetItemPricingCondition(
			IPricingItemUserExit pricingItem, String condition) {

		// Recupera todas as condições do item
		IPricingConditionUserExit[] pricingConditions = pricingItem
				.getUserExitConditions();

		// Varre todas as condições do item
		for (int i = 0; i < pricingConditions.length; i++) {

			// Verifica se o nome da condição do item atual é o mesmo do que
			// está se tentando localizar
			if (pricingConditions[i].getConditionTypeName() != null
					&& pricingConditions[i].getConditionTypeName().trim()
							.equalsIgnoreCase(condition)) {
				// Caso encontrado, retorna a condição de item
				return pricingConditions[i];
			}
		}

		return null;
	}

	/**
	 * Diêgo Silva - 04.11.2014 Recupera o valor de uma condição de item
	 * específica
	 * 
	 * @param pricingItem
	 * @param condition
	 * @return
	 */
	public static BigDecimal GetItemPricingConditionValue(
			IPricingItemUserExit pricingItem, String condition) {

		// Recupera a condição do item
		IPricingConditionUserExit pricingConditionUserExit = GetItemPricingCondition(
				pricingItem, condition);

		// Caso encontrado, retorna seu valor. Caso contrário, zero
		return pricingConditionUserExit != null ? pricingConditionUserExit
				.getConditionValue().getValue() : BigDecimal.ZERO;
	}

	/**
	 * Diêgo Silva - 04.11.2014 Recupera o montante (%) de uma condição de item
	 * específica
	 * 
	 * @param pricingItem
	 * @param condition
	 * @return
	 */
	public static BigDecimal GetItemPricingConditionRate(
			IPricingItemUserExit pricingItem, String condition) {

		// Recupera a condição do item
		IPricingConditionUserExit pricingConditionUserExit = GetItemPricingCondition(
				pricingItem, condition);

		// Caso encontrado, retorna seu valor. Caso contrário, zero
		return pricingConditionUserExit != null ? pricingConditionUserExit
				.getConditionRate().getValue() : BigDecimal.ZERO;
	}

	/**
	 * Diêgo Silva - 04.11.2014 Recupera o valor de uma condição de item
	 * específica, retornando o tipo double
	 * 
	 * @param pricingItem
	 * @param condition
	 * @return
	 */
	public static double GetItemPricingConditionValueAsDouble(
			IPricingItemUserExit pricingItem, String condition) {
		return Double.parseDouble(GetItemPricingConditionValue(pricingItem,
				condition).toString());
	}

	/**
	 * Diêgo Silva - 04.11.2014 Recupera o montante (%) de uma condição de item
	 * específica, retornando o tipo double
	 * 
	 * @param pricingItem
	 * @param condition
	 * @return
	 */
	public static double GetItemPricingConditionRateAsDouble(
			IPricingItemUserExit pricingItem, String condition) {
		return Double.parseDouble(GetItemPricingConditionRate(pricingItem,
				condition).toString());
	}

	/**
	 * Recupera a região de acordo com o TAXJURCODE
	 * 
	 * @param taxJurCode
	 * @return
	 */
	public static String GetRegionFromTaxJurCode(String taxJurCode) {
		return taxJurCode.substring(2, 3).equals(" ") ? taxJurCode.substring(0,
				2) : taxJurCode.substring(1, 3);
	}
}
