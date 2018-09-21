package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.base.util.event.ClearAllStatusEvent;
import com.sap.spe.pricing.transactiondata.IPricingItem;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition665 extends ValueFormulaAdapter {

    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

        //Limpar Status Viewer
        IPricingItem castedPrItem = (IPricingItem) pricingItem;
        castedPrItem.clearStatusMessage(new ClearAllStatusEvent(this,"UserExitContext"));

        //Recuperar o valor da codição
        BigDecimal xkwert = pricingCondition.getConditionValue().getValue();

        //Retornar valor
        return xkwert.multiply(PricingTransactiondataConstants.MINUS_ONE);

    }

}