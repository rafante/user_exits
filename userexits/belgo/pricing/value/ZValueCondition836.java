package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition836 extends ValueFormulaAdapter {

	public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
			IPricingConditionUserExit pricingCondition) {

		float value = pricingCondition.getConditionValue().getValue().floatValue();
		float subtI = pricingItem.getSubtotalAsBigDecimal('I').floatValue();
		float subtF = pricingItem.getSubtotalAsBigDecimal('F').floatValue();

		if (subtI != 0f) {
			value = subtI;
		} else {
			value = subtF;
		}
		
		pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I, PricingTransactiondataConstants.ZERO);
		pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_F, PricingTransactiondataConstants.ZERO);
		
		return new BigDecimal(value);
	}
}
