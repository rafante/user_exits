package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition672 extends ValueFormulaAdapter {

    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

	        // return minimum price if surcharge type is 3
	        int kbetr = pricingCondition.getConditionRate().getValue().intValue();
	
	        switch (kbetr)
	        {
	            case 3:
	                return pricingCondition.getConditionBase().getValue();
	            default:
	                return PricingTransactiondataConstants.ZERO;
	        }
        }	
}