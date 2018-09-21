package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition605 extends ValueFormulaAdapter {
    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

		/*
		 * Recupera o Domicílio Fiscal (Se o caracter 3 de TAXJURCODE
		 * for vazio, Domicílio Fiscal será vazio, caso contrário, o
		 * Domicílio Fiscal será o primeiro caracter de 'TAXJURCODE'
		 */
		String taxJurCodeDestino = pricingItem.getAttributeValue("TAXJURCODE");
		String domFiscalDestino = taxJurCodeDestino.substring(2, 3)
				.equals(" ") ? " " : taxJurCodeDestino.substring(0, 1);

		if (domFiscalDestino.equals("Z"))
			pricingCondition.setConditionRateValue(PricingTransactiondataConstants.HUNDRED);
		else 		
			pricingCondition.setConditionRateValue(PricingTransactiondataConstants.ZERO);
		
		return pricingCondition.getConditionValue().getValue();
    }
}