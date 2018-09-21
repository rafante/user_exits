package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition663 extends ValueFormulaAdapter {
//	  private BigDecimal WA_ZMIN_KAWRT = new BigDecimal(0);
//    private BigDecimal WA_ZMIN_KPEIN = new BigDecimal(0);

	
    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

	        //Atribuir os valores
//	        WA_ZMIN_KAWRT = pricingCondition.getConditionBase().getValue();
//	        WA_ZMIN_KPEIN = pricingCondition.getPricingUnit().getValue();
	
	        //Retornar o valor da condição
	        return pricingCondition.getConditionValue().getValue();
        }
}