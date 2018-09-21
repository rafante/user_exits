package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import userexits.belgo.pricing.utils.Utils;

import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition933 extends ValueFormulaAdapter {
	
	public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
			IPricingConditionUserExit pricingCondition) {
		
		//Cópia da variável de trabalho xworki do R/3
		BigDecimal xworki = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I ).getValue();
		//Cópia da variável de trabalho xworkf do R/3
		BigDecimal xworkf = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_F).getValue();
		// Valor da condição do documento atual (KOMV-KWERT)
		BigDecimal xkwert = pricingCondition.getConditionValue().getValue();                                                                             
		
		pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I, PricingTransactiondataConstants.ZERO);
		pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_F, PricingTransactiondataConstants.ZERO);
		
		if (pricingCondition.getConditionTypeName().equals("ZNF1"))
		{                                     
			
			if(xworkf.compareTo(xworki) > 0 && xworkf.compareTo(PricingTransactiondataConstants.ZERO) != 0) 
			{
				return xworkf;
			}
			else if(xworki.compareTo(PricingTransactiondataConstants.ZERO) != 0)
			{
				if(xworkf.compareTo(PricingTransactiondataConstants.ZERO) != 0)
				{
					return  xworki;
				}
				else
				{
					return PricingTransactiondataConstants.ZERO;
				}
			}
			else if(xworki.compareTo(PricingTransactiondataConstants.ZERO) == 0)
			{
				return xworkf;
			}
			
		}
		if (pricingCondition.getConditionTypeName().equals("ZNF2"))
		{      
			if(xworkf.compareTo(xworki) >= 0) 
			{
				return xworkf;
			}
			else if(xworki.compareTo(PricingTransactiondataConstants.ZERO) != 0)
			{
				
				float zprfValue = Utils.GetItemPricingConditionValue(pricingItem, "ZPRF").floatValue() * -1;
				float zvndValue = Utils.GetItemPricingConditionValue(pricingItem, "ZVND").floatValue() * -1;
				
				if(xworkf.compareTo(PricingTransactiondataConstants.ZERO) != 0)
				{      
					if(zvndValue != 0)
					{
						return xworki;
					}
					else 
					{
						double v_calc = xworki.doubleValue() - xworkf.doubleValue();
						
						if(zprfValue > v_calc && v_calc != 0)
						{
							return xworkf;
						}
						else 
						{
							double v_return = xworki.doubleValue() - zprfValue;
							BigDecimal xkwert2 = new BigDecimal(v_return);
							return xkwert2;
						}
					}
					
				}
				else
				{
					return xworki;
				}
			}
			
		}
		
		if (pricingCondition.getConditionTypeName().equals("ZNF3") || pricingCondition.getConditionTypeName().equals("ZNFZ"))
		{
			if(xworki.compareTo(PricingTransactiondataConstants.ZERO) != 0)
			{
				return xworki;
			}
			else
			{
				return xworkf;
			}
		}else if(pricingCondition.getConditionTypeName().equals("ZVPR")) {
			
			float v_ztgt = Utils.GetItemPricingConditionValue(pricingItem, "ZTGT").floatValue();
			float v_zvnd_kwert = Utils.GetItemPricingConditionValue(pricingItem, "ZVND").floatValue() * -1;
			float v_zvnd_kbetr = Utils.GetItemPricingConditionRate(pricingItem, "ZVND").floatValue();
			float v_znpf = Utils.GetItemPricingConditionValue(pricingItem, "ZNPF").floatValue();
			if(v_znpf != 0) {
				v_znpf *= -1;
				if(v_zvnd_kwert != 0) {
					float v_zvndx = ((v_ztgt * v_zvnd_kbetr) * -1 ) / 10;
					float newValue = xkwert.floatValue();
					if(v_zvndx > v_zvnd_kwert) {
						newValue = v_ztgt - v_zvndx;
					}else {
						newValue = v_ztgt - v_zvnd_kwert;
					}
					xkwert = new BigDecimal(newValue);
				}
			}
		}
		
				
		//Limpar variável de trabalho XWORKI
		/*pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I,PricingTransactiondataConstants.ZERO);
		 //Limpar variável de trabalho XWORKF
		  pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_F,PricingTransactiondataConstants.ZERO);
		  
		  //Verificar se será retornado para o valor da mercadoria o campo
		   //xworki ou xworkf
		    if(xworki.compareTo(PricingTransactiondataConstants.ZERO) != 0)
		    {
		    return xworki;
		    }
		    else
		    {
		    return xworkf;
		    }*/
		return xkwert;
	}
	
}