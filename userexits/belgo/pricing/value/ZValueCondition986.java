package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

/* Fórmula..: 986 
 * Criada em: 03/11/2011
 * Criador..: Cláudio L. Mártire
 * 
 * A fórmula 986 foi criada para  
 */		

public class ZValueCondition986 extends ValueFormulaAdapter {

	   public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
		        IPricingConditionUserExit pricingCondition) {
		   		    
		   BigDecimal Thousand = new BigDecimal("1000");
		   
		   String TAX_GROUP_BP_01 = "TAX_GROUP_BP_01";
		   String str_TAX_GROUP_BP_01 = null;
		   str_TAX_GROUP_BP_01 = pricingItem.getAttributeValue(TAX_GROUP_BP_01);
		   
		   BigDecimal xworki = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I).getValue();
		   BigDecimal xworkh = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_H).getValue();
	       BigDecimal xkwert = pricingCondition.getConditionValue().getValue();  
	       
	       String sCondTypeName = pricingCondition.getConditionTypeName();
	       
	       xkwert = Thousand.subtract(xworkh).divide(Thousand, BigDecimal.ROUND_HALF_UP).multiply(xkwert);
	       xkwert = xkwert.add(xworkh.divide(Thousand, BigDecimal.ROUND_HALF_UP).multiply(xworki));
	         
	       
	       if (xkwert.compareTo(PricingTransactiondataConstants.ZERO)!=0)
	    	   if (sCondTypeName.compareTo("YX11")==0 || sCondTypeName.compareTo("YX21")==0)
	    		   if (str_TAX_GROUP_BP_01.compareTo("2-ZONA-FRANCA")==0 || str_TAX_GROUP_BP_01.compareTo("9-OUTROS") == 0)
      			   // Retorna o valor da condição IBRX
	    			   xkwert = pricingItem.findPricingCondition(608,1).getConditionBase().getValue().setScale(5);

	       return xkwert;
	   }
}