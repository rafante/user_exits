package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition935 extends ValueFormulaAdapter {

    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {
    	 
        //Buscar o valor das variáveis TAX_DEPART_REG e REGION para a fórmula 935
        String TAX_DEPART_REG = "TAX_DEPART_REG";
        String REGION = "REGION";
        String str_Tax_Depart_Reg = null;
        String str_Region = null;
         
        //Buscar a região do centro
        str_Tax_Depart_Reg = pricingItem.getAttributeValue(TAX_DEPART_REG);
        if (str_Tax_Depart_Reg.equals("")){
         	// INACTIVE_DUE_TO_ERROR = X
          	pricingCondition.setInactive(PricingCustomizingConstants.InactiveFlag.INACTIVE_DUE_TO_ERROR);
        }

        //Buscar a região
        str_Region = pricingItem.getAttributeValue(REGION);
        if (str_Region.equals("")){
            	pricingCondition.setInactive(PricingCustomizingConstants.InactiveFlag.INACTIVE_DUE_TO_ERROR);
        }        
    	                 	
    	
        //Buscar o valor da condição da linha da pricing
        BigDecimal xkwert = pricingCondition.getConditionValue().getValue();

        // Se o valor for maior que zero retornar o valor da condição somado
        // com o valor da variável XWORKG, que possue o valor do frete com
        //ICMS                                                               

        //if (xkwert.compareTo(PricingTransactiondataConstants.ZERO) == 1 && str_Tax_Depart_Reg != "MG" && str_Region != "MG")
        if (xkwert.compareTo(PricingTransactiondataConstants.ZERO) == 1 && (!str_Tax_Depart_Reg.equals("MG")) && (!str_Region.equals("MG")))
        {
            BigDecimal xworkg = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_G).getValue();

            //Adicionar o valor do frete com ICMS ao valor da mercadoria
            xkwert = xkwert.add(xworkg);

            //Limpar variável de trabalho XWORKG
            pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_G, PricingTransactiondataConstants.ZERO);
        }
         
	     return xkwert;        
    }
}