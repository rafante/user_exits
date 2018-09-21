package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition670 extends ValueFormulaAdapter {

    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

        BigDecimal xworkd = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_D).getValue();
        BigDecimal xkwert = pricingCondition.getConditionValue().getValue();

        // Clear Subtotal D:
        pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_D,PricingTransactiondataConstants.ZERO);
        return (xworkd.compareTo(xkwert) > 0 ? xworkd : xkwert);
        }
}