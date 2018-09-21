package userexits.belgo.pricing.value;


import java.math.BigDecimal;
import userexits.belgo.pricing.utils.Utils;
import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;



public class ZValueCondition820 extends ValueFormulaAdapter {
    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {
    	
    	//PVL
    	BigDecimal pvlValue = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_5).getValue().setScale(20);
    	//Frete
    	BigDecimal zftrValue = Utils.GetItemPricingConditionValue(pricingItem, "ZFTR");
    	//ZMK0
    	BigDecimal zmk0Value = Utils.GetItemPricingConditionValue(pricingItem, "ZMK0");
    	//ZEDI
    	BigDecimal zediValue = Utils.GetItemPricingConditionValue(pricingItem, "ZEDI");
    	
    	//ZRAG = ( (PVL + Frete) – ( (PVL + Frete) / (ZEDI / ZMK0) ) ) 
    	if(zmk0Value.compareTo(BigDecimal.ZERO) != 0 && zediValue.compareTo(BigDecimal.ZERO)!= 0){
        	BigDecimal exp1Value = pvlValue.add(zftrValue);      //(PVL + Frete)
        	BigDecimal exp2Value = zediValue.divide(zmk0Value, BigDecimal.ROUND_HALF_UP);  //(ZEDI / ZMK0)
        	BigDecimal exp3Value = exp1Value.divide(exp2Value, BigDecimal.ROUND_HALF_UP);  //( (PVL + Frete) / (ZEDI / ZMK0) )
        	BigDecimal zragValue = exp1Value.subtract(exp3Value);//( (PVL + Frete) – ( (PVL + Frete) / (ZEDI / ZMK0) ) )   
        	
        	return zragValue;	 
    	}
    	else 
    		return BigDecimal.ZERO;	
    
    }
}