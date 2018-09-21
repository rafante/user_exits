package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition669 extends ValueFormulaAdapter {
    
	public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

        // **************************************************
        // INÍCIO DA APLICAÇAO DA NOTA 609357 EM 24/04/2003
        // *************************************************
        // ADAPTADO PARA A NOVA VERSÃO DO IPC EM 16/10/2006 por CLÁUDIO L. MÁRTIRE  	
        String TAX_MAT_USAGE = "TAX_MAT_USAGE";
        boolean consumption;
            
        consumption = pricingItem.getAttributeValue(TAX_MAT_USAGE).equals("2");
        // **************************************************
        // FINAL DA APLICAÇAO DA NOTA 609357 EM 24/04/2003
        // *************************************************

        
	    if  ( consumption )
        {
	    	return pricingCondition.getConditionBase().getValue();
        }
        	else
            {   
/*        		BigDecimal fbetr = new BigDecimal("0");
        		BigDecimal fawrt = new BigDecimal("0"); 
        		BigDecimal fwert = new BigDecimal("0");

        		fbetr = pricingCondition.getConditionRate().getValue().setScale(15).divide(PricingTransactiondataConstants.HUNDRED,BigDecimal.ROUND_HALF_UP).setScale(15);
        		fawrt = pricingCondition.getConditionBase().getValue().setScale(15);
        		fwert = fawrt.setScale(15).multiply(PricingTransactiondataConstants.ONE.subtract(fbetr.setScale(15)).setScale(15));
        		
        		return fwert;*/

        		return (PricingTransactiondataConstants.ONE.subtract(pricingCondition.getConditionRate().getValue().setScale(15).divide(PricingTransactiondataConstants.HUNDRED,BigDecimal.ROUND_HALF_UP).setScale(15)).setScale(15)).setScale(15).multiply(pricingCondition.getConditionBase().getValue().setScale(15));
//    		return (PricingTransactiondataConstants.ONE.subtract(pricingCondition.getConditionRate().getValue())).multiply(pricingCondition.getConditionBase().getValue());
            }
    }

}