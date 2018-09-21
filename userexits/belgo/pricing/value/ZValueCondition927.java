package userexits.belgo.pricing.value;


import java.math.BigDecimal;
import userexits.belgo.pricing.utils.Utils;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition927 extends ValueFormulaAdapter {
    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {
    	
    	if (pricingCondition.getConditionTypeName() != null
			&& pricingCondition.getConditionTypeName().equals("ZFRT")) {

    		BigDecimal zftrValue = Utils.GetItemPricingConditionValue(pricingItem, "ZFTR");
			
//    		Retornar o valor da condição	
    		if (zftrValue.compareTo(PricingTransactiondataConstants.ZERO) > 0)
				return zftrValue.multiply(PricingTransactiondataConstants.MINUS_ONE);
			else
				return PricingTransactiondataConstants.ZERO;	
    	}
    	else 
    		return pricingCondition.getConditionValue().getValue();	 
    }

	
}