package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import userexits.belgo.pricing.utils.Utils;

import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.IPricingCondition;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

/* Fórmula..: 987 
 * Criada em: 03/11/2011
 * Criador..: Cláudio L. Mártire
 * 
 * A fórmula 987 foi criada para  
 */		

public class ZValueCondition987 extends ValueFormulaAdapter {

	   public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
		        IPricingConditionUserExit pricingCondition) {
		   		   
		   BigDecimal Thousand = new BigDecimal("1000");
		   
		   String TAX_GROUP_BP_01 = "TAX_GROUP_BP_01";
		   String str_TAX_GROUP_BP_01 = null;
		   str_TAX_GROUP_BP_01 = pricingItem.getAttributeValue(TAX_GROUP_BP_01);
		   String DIS_CHANNEL = pricingItem.getAttributeValue("DIS_CHANNEL");
		   
		   BigDecimal xworki = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I).getValue();
		   BigDecimal xworkh = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_H).getValue();
	       BigDecimal xkwert = pricingCondition.getConditionValue().getValue();  

	       IPricingCondition zfYFXC = pricingItem.findPricingCondition(684,1);
	       IPricingCondition zfYFXP = pricingItem.findPricingCondition(685,1);
	       //if((zfYFXC.getConditionTypeName().compareTo("YFXC")==0) 
       
	       String sCondTypeName = pricingCondition.getConditionTypeName();
	       
	       xkwert = Thousand.subtract(xworkh).divide(Thousand, BigDecimal.ROUND_HALF_UP).multiply(xkwert);
	       xkwert = xkwert.add(xworkh.divide(Thousand, BigDecimal.ROUND_HALF_UP).multiply(xworki));
	       
	       if (Utils.GetItemPricingCondition(pricingItem, "DIZF")!=null || xkwert.compareTo(PricingTransactiondataConstants.ZERO)!=0)
	    	   if (sCondTypeName.compareTo("YX71")==0  || sCondTypeName.compareTo("YX81")==0)
	    		   if ( Utils.GetItemPricingCondition(pricingItem, "DIZF")!=null 
	    				   && (str_TAX_GROUP_BP_01.compareTo("2-ZONA-FRANCA")==0 || str_TAX_GROUP_BP_01.compareTo("9-OUTROS") == 0) 
	    				      && DIS_CHANNEL.compareTo("40")!=0 )
	    			   // Retorna o valor da condição IBRX
    			      // xkwert = pricingItem.findPricingCondition(608,1).getConditionBase().getValue().setScale(5);
	    			   
					   if (sCondTypeName.compareTo("YX71")==0) 
					   {
						   if((zfYFXC.getConditionTypeName().compareTo("YFXC")==0) 
								   && (zfYFXC.getConditionRate().getValue().compareTo(PricingTransactiondataConstants.ZERO)!=0))
	    			           xkwert = pricingItem.findPricingCondition(608,1).getConditionBase().getValue().setScale(5);
						   else  
							   xkwert = PricingTransactiondataConstants.ZERO;
					   } 
					   else if (sCondTypeName.compareTo("YX81")==0)
					   {
						   if((zfYFXP.getConditionTypeName().compareTo("YFXP")==0) 
								   && (zfYFXP.getConditionRate().getValue().compareTo(PricingTransactiondataConstants.ZERO)!=0))
	    			           xkwert = pricingItem.findPricingCondition(608,1).getConditionBase().getValue().setScale(5);
						   else
							   xkwert = PricingTransactiondataConstants.ZERO;
					   } 
		   
	       return xkwert;
	   }
}