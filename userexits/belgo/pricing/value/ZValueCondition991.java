package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition991 extends ValueFormulaAdapter {

	   public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
		        IPricingConditionUserExit pricingCondition) {
       
		   BigDecimal Thousand = new BigDecimal("1000");

		   //Limpa os valores das variáveis XWORKI e XWORKH
		   pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I,PricingTransactiondataConstants.ZERO);
		   pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_H,PricingTransactiondataConstants.ZERO);
		   		   
           //Preço de crédito do item (KOMP-CMPRE)
           BigDecimal komp_cmpre = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.CREDIT_PRICE).getValue();

           //Mover o ponto decimal para esquerda
           komp_cmpre = komp_cmpre.movePointLeft(2);

           //Quantidade (KOMP-MGAME)
           BigDecimal komp_mgame = new BigDecimal(pricingItem.getBaseQuantity().getValue().doubleValue());

           //Verificar se poderá ser feita divisão
           if (komp_mgame.compareTo(PricingTransactiondataConstants.ZERO) != 0)
           {

               //Retornar o valor com os devidos cálculos
               return ((komp_cmpre.multiply(Thousand.multiply(PricingTransactiondataConstants.HUNDRED))
               .divide(komp_mgame, BigDecimal.ROUND_HALF_UP))
               .divide(Thousand.multiply(PricingTransactiondataConstants.HUNDRED), BigDecimal.ROUND_HALF_UP));
           }
           else
           {
               return PricingTransactiondataConstants.ZERO;
           }		   
	  }	
}