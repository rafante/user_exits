package userexits.belgo.pricing.base;

import java.math.BigDecimal;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.BaseFormulaAdapter;
import com.sap.spe.base.logging.UserexitLogger;

public class ZBaseCondition899 extends BaseFormulaAdapter {

	@SuppressWarnings("unused")
	private static UserexitLogger userexitlogger = new UserexitLogger(ZBaseCondition899.class);

	public BigDecimal overwriteConditionBase(IPricingItemUserExit pricingItem,
			IPricingConditionUserExit pricingCondition) {

		String itm_usage = pricingItem.getAttributeValue("ITM_USAGE").trim();

		if (itm_usage.equals("08")) {

//			pricingCondition.setConditionRateValue(new BigDecimal(0));
			pricingCondition.setConditionRate(new BigDecimal(0),
		    pricingItem.getUserExitDocument().getDocumentCurrencyUnit());

		}
		
		return null;
	}
}