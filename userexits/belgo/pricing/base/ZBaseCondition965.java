package userexits.belgo.pricing.base;

import java.math.BigDecimal;
import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.userexit.BaseFormulaAdapter;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;

public class ZBaseCondition965 extends BaseFormulaAdapter {

    public BigDecimal overwriteConditionBase(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {
    	
         if (pricingCondition.getConditionControl() == PricingCustomizingConstants.Control.VALUE_FIXED_FOR_BILLED_ITEMS)
         {
        	 pricingCondition.setConditionControl(PricingCustomizingConstants.Control.ADJUST_FOR_QUANTITY_VARIANCE);     	 
         } 
         //return null;
         
         //Código da fórmula 2 - 11/09/2012
         BigDecimal xkwert = pricingItem.getNetValue().getValue();
         return xkwert;
        }
    
}