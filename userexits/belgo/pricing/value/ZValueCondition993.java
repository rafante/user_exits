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

           // Essa parte do c�digo existe somente no ambiente do CRM, n�o existe
           // nas f�rmulas ABAP do R/3, � utilizado para guardar em atributos
           // privados os valores de BASE DE CONDI��O (WA_ZEDI_KAWRT)
           // e tamb�m de UNIDADE DE PRE�O DA CONDI��O (WA_ZEDI_KPEIN) da linha da
           // princing no momento em que a condi��o ZEDI � executada, que s�o
           // utilizados na f�rmula 930
//           WA_ZEDI_KAWRT = prCondition.getConditionBase().getValue();
//           WA_ZEDI_KPEIN = prCondition.getPricingUnit().getValue();

           //Valor da condi��o do documento atual (KOMV-KWERT)
           BigDecimal xkwert = pricingCondition.getConditionValue().getValue();

           //C�pia da vari�vel de trabalho xworki do R/3
           BigDecimal xworki = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I).getValue();
           //Limpar vari�vel de trabalho XWORKI
           pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I,PricingTransactiondataConstants.ZERO);
           //Limpar vari�vel de trabalho XWORKE
           pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_E,PricingTransactiondataConstants.ZERO);

           //Verificar o valor da condi��o atual
           if(xkwert.compareTo(PricingTransactiondataConstants.ZERO) == 0)
           {
               //Retornar o valor da vari�vel xworki para o valor da condi��o
               return xworki;
           }
           else
           {
               if (xkwert.compareTo(PricingTransactiondataConstants.ZERO) == -1)
               {
                   //Retornar o valor da vari�vel xworki para o valor da condi��o
                   return xkwert.multiply(PricingTransactiondataConstants.MINUS_ONE);
               }
               else
               {
                   //Retornar o valor da vari�vel xworki para o valor da condi��o
                   return xkwert;
               }
           }
	   }
}
