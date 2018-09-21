package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition925 extends ValueFormulaAdapter {
    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

/* DEMANDA C594936 - Opera��o Interestadual realizada com Constutoras
 * DATA: 26/01/2016 	AUTOR: CLAUDIO LUCIANO MARTIRE  
 * F�rmula para verificar se a opera��o � interestadual para consumidor n�o contribuinte 
 * nos Estados informados em tabela de par�metros, se atender a estas verifica��es ser� 
 * considerada a base 100%, desconsiderando a parametriza��o da opera��o dentro do estad
 * nos grupos de exce��es din�micas cadastrados na tabela de par�metros.
 * 
 * ZBMCRMXXX-GRUOP => pricingItem.getAttributeValue("ZZTAXGRUOP")   
 * KOMP-MTUSE  => pricingItem.getAttributeValue("TAX_MAT_USAGE"), para o contexto do CRM sera utilizado o atributo DIS_CHANNEL, uma vez que n�o existe valor para o TAX_MAT_USAGE que o mesmo e derivado do codigo de imposto 
 * KOMP-WKREG  => pricingItem.getAttributeValue("TAX_DEPART_REG") 
 * KOMK-REGIO  => pricingItem.getAttributeValue("REGION") 
 * KOMK-TXJCD  => pricingItem.getAttributeValue("TAXJURCODE") 
 * XKOMV-KSCHL => pricingCondition.getConditionTypeName()
 * XKOMV-KBETR => pricingCondition.getConditionRate() 
 * XKOMV-KOLNR => pricingCondition.getAccess().getCounter() 
*/
    	
    	String zzTaxGruop = pricingItem.getAttributeValue("ZZTAXGRUOP");
    	String taxJurCodeDestino = pricingItem.getAttributeValue("TAXJURCODE");
		
    	String domFiscalDestino = taxJurCodeDestino.substring(2, 3)
				.equals(" ") ? " " : taxJurCodeDestino.substring(0, 1);

		if ( (pricingCondition.getConditionTypeName() != null && pricingCondition.getConditionTypeName().equals("ISIB")) 
		&& (!pricingItem.getAttributeValue("REGION").equals(pricingItem.getAttributeValue("TAX_DEPART_REG")))
		&& (pricingItem.getAttributeValue("DIS_CHANNEL").equals("40")) //(pricingItem.getAttributeValue("TAX_MAT_USAGE").equals("2"))
		&& (domFiscalDestino.equals("Z")) 
		&& (pricingCondition.getConditionRate().getValue().compareTo(PricingTransactiondataConstants.HUNDRED) < 0)
		&& (zzTaxGruop.length() > 0) )
		{
			String [] sGrpImposto =  new String[1];
			Integer iCounter = new Integer(pricingCondition.getAccess().getCounter()); //Recupera o Grupo de imposto
			
			if (zzTaxGruop.contains("-")) 
				sGrpImposto = zzTaxGruop.split("-"); 
			else
				sGrpImposto[0] = zzTaxGruop;
						
			for(int i=0; i<sGrpImposto.length; i++)
				if (iCounter.toString().equals(sGrpImposto[i])){
					pricingCondition.setConditionRateValue(PricingTransactiondataConstants.HUNDRED);
					return new BigDecimal("10");
				}
		}
		
		return pricingCondition.getConditionValue().getValue();
    }
}