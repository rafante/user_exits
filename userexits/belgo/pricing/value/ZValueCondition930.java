package userexits.belgo.pricing.value;

import java.math.BigDecimal;

//import com.sap.spe.base.util.event.ClearAllStatusEvent;
//import com.sap.spe.base.util.event.ErrorStatusEvent;
import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
//import com.sap.spe.pricing.transactiondata.IPricingItem;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition930 extends ValueFormulaAdapter {

    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

    /*
	        // Recuperar os valores dos montantes das condi��es armazenados
	        // nas vari�veis de trabalho
	        BigDecimal WA_ZEDI_KBETR = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_D).getValue();
	
	        //Verificar se a linha da condi��o ZEDI possue valor informado
	        if( WA_ZEDI_KBETR.compareTo(PricingTransactiondataConstants.ZERO) == 1)
	        {
	            BigDecimal WA_ZMIN_KBETR = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I).getValue();
	            BigDecimal WA_ZMAX_KBETR = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_F).getValue();
	*/
	            //Limpar vari�vel de trabalho XWORKD
	            pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_D,PricingTransactiondataConstants.ZERO);
	            //Limpar vari�vel de trabalho XWORKI
	            pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_I,PricingTransactiondataConstants.ZERO);
	            //Limpar vari�vel de trabalho XWORKF
	            pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_F,PricingTransactiondataConstants.ZERO);
	/*
	            //Realizar as convers�es
	            BigDecimal VC_ZEDI = WA_ZEDI_KBETR;
	            BigDecimal VC_ZMIN = WA_ZMIN_KBETR;
	            BigDecimal VC_ZMAX = WA_ZMAX_KBETR;
	
	            //Verificar se o valor est� abaixo do valor negociado
	            if (VC_ZEDI.compareTo(VC_ZMIN) == -1)
	            {
	                //Informar que o pre�o est� abaixo do limite m�nimo
	
	                // cast IPricingItemUserExit to IPricingItem
	                IPricingItem castedPrItemMin = (IPricingItem) pricingItem;
	
	                // remove old message
	                castedPrItemMin.clearStatusMessage(new ClearAllStatusEvent(this,"UserExitContext"));
	
	                // set new message
	               castedPrItemMin.setStatusMessage(new ErrorStatusEvent(this,"Pre�o fora do limite m�nimo estabelecido.","UserExitContext"));
	            }
	
	            if (VC_ZEDI.compareTo(VC_ZMAX) == 1)
	            {
	                //Informar que o pre�o est� acima do limite m�ximo
	
	                // cast IPricingItemUserExit to IPricingItem
	                IPricingItem castedPrItemMax = (IPricingItem) pricingItem;
	
	                // remove old message
	                castedPrItemMax.clearStatusMessage(new ClearAllStatusEvent(this,"UserExitContext"));
	
	                // set new message
	               castedPrItemMax.setStatusMessage(new ErrorStatusEvent(this,"Pre�o fora do limite m�ximo estabelecido.","UserExitContext"));
	            }
	        }
            //Retornar o valor da condi��o
            return WA_ZEDI_KBETR;
     */
	        return null;
    }
}