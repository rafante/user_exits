package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition662 extends ValueFormulaAdapter {

    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {
    	
	        BigDecimal xworkd = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_D).getValue();
	        BigDecimal xkwert = pricingCondition.getConditionValue().getValue();
	        BigDecimal result = xworkd.subtract(xkwert);
	
	        // Clear Subtotal D:
	        pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_D,PricingTransactiondataConstants.ZERO);
	
	        return (result.compareTo(PricingTransactiondataConstants.ZERO) < 0 ? PricingTransactiondataConstants.ZERO : result);
       }
}