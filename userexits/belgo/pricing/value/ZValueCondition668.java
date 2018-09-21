package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition668 extends ValueFormulaAdapter {

    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

	        // **************************************************
	        // INÍCIO DA APLICAÇAO DA NOTA 609357 EM 24/04/2003
	        // *************************************************
            // ADAPTADO PARA A NOVA VERSÃO DO IPC EM 16/10/2006 por CLÁUDIO L. MÁRTIRE  	
            boolean consumption;
	        String TAX_MAT_USAGE = "TAX_MAT_USAGE";

	        consumption = pricingItem.getAttributeValue(TAX_MAT_USAGE).equals("2");
	        // **************************************************
	        // FINAL DA APLICAÇAO DA NOTA 609357 EM 24/04/2003
	        // *************************************************
	        
	        
	        int kbetr = pricingCondition.getConditionRate().getValue().intValue();
	        BigDecimal xworkd = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_D).getValue();
	
	        // Clear Subtotal D:
	        pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_D,PricingTransactiondataConstants.ZERO);
	
	        switch (kbetr)
	        {
	            case 1:
	
	            case 3:
	                // Calculation Base is Invoice Value
	                return pricingCondition.getConditionBase().getValue();
	            case 2:
	                // Calculation Base is fixed price, if not consumption
	                if ( consumption )
	                {
	                    return pricingCondition.getConditionBase().getValue();
	                }
	                else
	                {
	                    return xworkd;
	                }
	            default:
	                // no Sub Trib Calculation
	                return PricingTransactiondataConstants.ZERO;
	        }
        }
}