package userexits.belgo.pricing.base;


import java.math.BigDecimal;
import userexits.belgo.pricing.utils.Utils;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit; 	
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit; 
import com.sap.spe.pricing.transactiondata.userexit.BaseFormulaAdapter;


public class ZBaseCondition897 extends BaseFormulaAdapter {

	    public BigDecimal overwriteConditionBase(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

    	double ZPRFValue = Utils.GetItemPricingConditionValueAsDouble(pricingItem, "ZPRF");

    	if(ZPRFValue != 0)
    		pricingCondition.setConditionRateValue(PricingTransactiondataConstants.HUNDRED);
	    
    	return null;
    	
    }	
}