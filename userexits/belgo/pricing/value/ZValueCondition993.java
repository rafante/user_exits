package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition993 extends ValueFormulaAdapter {

	   public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
		        IPricingConditionUserExit pricingCondition) {

           // Essa parte do código existe somente no ambiente do CRM, não existe
           // nas fórmulas ABAP do R/3, é utilizado para guardar em atributos
           // privados os valores de BASE DE CONDIÇÃO (WA_ZEDI_KAWRT)
           // e também de UNIDADE DE PREÇO DA CONDIÇÃO (WA_ZEDI_KPEIN) da linha da
           // princing no momento em que a condição ZEDI é executada, que são
           // utilizados na fórmula 930
//           WA_ZEDI_KAWRT = prCondition.getConditionBase().getValue();
//           WA_ZEDI_KPEIN = prCondition.getPricingUnit().getValue();

           //Valor da condição do documento atual (KOMV-KWERT)
           BigDecimal xkwert = pricingCondition.getConditionValue().getValue();

           //Cópia da variável de trabalho xworki do R/3
           BigDecimal xworki = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I).getValue();
           //Limpar variável de trabalho XWORKI
           pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I,PricingTransactiondataConstants.ZERO);
           //Limpar variável de trabalho XWORKE
           pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_E,PricingTransactiondataConstants.ZERO);

           //Verificar o valor da condição atual
           if(xkwert.compareTo(PricingTransactiondataConstants.ZERO) == 0)
           {
               //Retornar o valor da variável xworki para o valor da condição
               return xworki;
           }
           else
           {
               if (xkwert.compareTo(PricingTransactiondataConstants.ZERO) == -1)
               {
                   //Retornar o valor da variável xworki para o valor da condição
                   return xkwert.multiply(PricingTransactiondataConstants.MINUS_ONE);
               }
               else
               {
                   //Retornar o valor da variável xworki para o valor da condição
                   return xkwert;
               }
           }
	   }
}
