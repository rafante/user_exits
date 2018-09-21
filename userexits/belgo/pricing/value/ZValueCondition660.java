package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;


public class ZValueCondition660 extends ValueFormulaAdapter {

    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

    	// First bring available values in ABAP name convention
	        BigDecimal xworki = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I).getValue();
	
	        BigDecimal temp_1 = new BigDecimal("0");
	        Double const_2 = new Double(2);
	        Double const_12 = new Double(12);
	
	        // Avoid taking the logarithm of Zero:
	        if (xworki.abs().compareTo(PricingTransactiondataConstants.ZERO) != 0)
	        {
	            temp_1 = new BigDecimal(Math.log(xworki.abs().doubleValue()) / Math.log(10));
	        }
	        else
	        {    
	            return PricingTransactiondataConstants.ZERO;
	        }
	
	        // Cut off and round up, the rough way:
	        Integer temp_2 = new Integer(temp_1.intValue() + 1);
	
	        // Minimum precision 10 ** 2:
	        Double temp_3 = new Double(Math.max(const_2.doubleValue(), temp_2.doubleValue()));

	        // Maximum precision 10 ** 12 to avoid shortdumps:
	        Double temp_4= new Double(Math.round(Math.min(const_12.doubleValue(), temp_3.doubleValue())));
	        BigDecimal DummyBase = new BigDecimal(Math.pow(10, temp_4.doubleValue()));
	
	        return DummyBase;  
    }
}