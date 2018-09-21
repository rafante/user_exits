package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition894 extends ValueFormulaAdapter {
    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {
        
    	String zz_zst = pricingItem.getAttributeValue("ZZ_ZST").trim();   
		
		if ( zz_zst.equals("")  ) 
		{
			return pricingCondition.getConditionValue().getValue();
		}
			else
		{
			pricingCondition.setConditionRateValue(PricingTransactiondataConstants.ZERO);	  
		    return PricingTransactiondataConstants.ZERO;
		}
  
    } 	
}