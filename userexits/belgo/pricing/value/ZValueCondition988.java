package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import userexits.belgo.pricing.utils.Utils;

import com.sap.spe.pricing.transactiondata.IPricingCondition;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

/* Fórmula..: 988 
 * Criada em: 03/11/2011
 * Criador..: Cláudio L. Mártire
 * 
 * A fórmula 988 foi criada para  
 */		

public class ZValueCondition988 extends ValueFormulaAdapter {

	   public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
		        IPricingConditionUserExit pricingCondition) {
		   		   
//		   BigDecimal Thousand = new BigDecimal("1000");
		   
		   String TAX_GROUP_BP_01 = "TAX_GROUP_BP_01";
		   String str_TAX_GROUP_BP_01 = null;
		   str_TAX_GROUP_BP_01 = pricingItem.getAttributeValue(TAX_GROUP_BP_01);		   
		   String DIS_CHANNEL = pricingItem.getAttributeValue("DIS_CHANNEL");
		   
//		   BigDecimal xworkh = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_H).getValue();
	       BigDecimal xkwert = pricingCondition.getConditionValue().getValue();  
		   
	       IPricingCondition zfYFXC = pricingItem.findPricingCondition(684,1);
	       IPricingCondition zfYFXP = pricingItem.findPricingCondition(685,1);
	       String sCondTypeName = pricingCondition.getConditionTypeName();	       
	       
	       if (xkwert.compareTo(PricingTransactiondataConstants.ZERO)!=0) 
			   if (  Utils.GetItemPricingCondition(pricingItem, "DIZF")!=null && (str_TAX_GROUP_BP_01.compareTo("2-ZONA-FRANCA")==0 || str_TAX_GROUP_BP_01.compareTo("9-OUTROS") == 0)
					   && DIS_CHANNEL.compareTo("40")!=0 ) 
			   {
				   xkwert = PricingTransactiondataConstants.ZERO; //Thousand.subtract(xworkh).divide(Thousand, BigDecimal.ROUND_HALF_UP).multiply(xkwert);
				   if (sCondTypeName.compareTo("YX70")==0) 
				   {					   
					   if((zfYFXC.getConditionTypeName().compareTo("YFXC")==0) 
							   && (zfYFXC.getConditionRate().getValue().compareTo(PricingTransactiondataConstants.ZERO)==0))
				           return pricingItem.findPricingCondition(608,1).getConditionBase().getValue().setScale(5);
				   }
				   else if (sCondTypeName.compareTo("YX80")==0)
				   {					   
					   if((zfYFXP.getConditionTypeName().compareTo("YFXP")==0) 
							   && (zfYFXP.getConditionRate().getValue().compareTo(PricingTransactiondataConstants.ZERO)==0))
						   return pricingItem.findPricingCondition(608,1).getConditionBase().getValue().setScale(5);
				   }
			   }			   
	       return xkwert;
	   }
}