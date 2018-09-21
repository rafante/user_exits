package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition664 extends ValueFormulaAdapter {

    //LINHA DA CONDIÇÃO ZMAX
//    private BigDecimal WA_ZMAX_KAWRT = new BigDecimal(0);
//    private BigDecimal WA_ZMAX_KPEIN = new BigDecimal(0);
	
    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

        //Atribuir os valores
//        WA_ZMAX_KAWRT = prCondition.getConditionBase().getValue();
//        WA_ZMAX_KPEIN = prCondition.getPricingUnit().getValue();

        //Retornar o valor da condição
        return pricingCondition.getConditionValue().getValue();

    }
	
}