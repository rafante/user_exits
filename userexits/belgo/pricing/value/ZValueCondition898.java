package userexits.belgo.pricing.value;
/*
 * 
 * 
 * 
 */
import java.math.BigDecimal;

import userexits.belgo.pricing.utils.Utils;

import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition898 extends ValueFormulaAdapter {
    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {
        
    	String zzdecreto_difal = pricingItem.getAttributeValue("ZZDECRETO_DIFAL");
    	String taxJurCodeDestino = pricingItem.getAttributeValue("TAXJURCODE");
		String domFiscalDestino = taxJurCodeDestino.substring(2, 3)
				.equals(" ") ? " " : taxJurCodeDestino.substring(0, 1);
		
		if (   (pricingCondition.getConditionValue().getValue().compareTo(PricingTransactiondataConstants.ZERO) != 0)
		    && (pricingItem.getAttributeValue("DIS_CHANNEL").equals("40"))
		    && (domFiscalDestino.equals("X"))
			&& (!pricingItem.getAttributeValue("REGION").equals(pricingItem.getAttributeValue("TAX_DEPART_REG")))
			&& (!zzdecreto_difal.equals("")) )
		{
			float ibrxValue = Utils.GetItemPricingConditionValue(pricingItem, "IBRX").floatValue();
			float ipvaValue = Utils.GetItemPricingConditionRate(pricingItem, "IPVA").floatValue();
			float icvaValue = Utils.GetItemPricingConditionRate(pricingItem, "ICVA").floatValue();
			float isicValue = Utils.GetItemPricingConditionRate(pricingItem, "ISIC").floatValue();
			float istbValue = Utils.GetItemPricingConditionRate(pricingItem, "ISTB").floatValue();
			float ibx2Value = Utils.GetItemPricingConditionRate(pricingItem, "BX23").floatValue();
			
			if (zzdecreto_difal.equals("2"))  //2 - Reg.Cálculo BA		//(pricingItem.getAttributeValue("REGION").equals("BA"))
				istbValue = 100;
			else
			{
				if (istbValue == 0)
					istbValue = 100;
			}
			
			if (pricingCondition.getConditionTypeName().equals("ZX40"))
			{
				if (zzdecreto_difal.equals("1"))  //1 - Reg.Cálculo MG			//(pricingItem.getAttributeValue("REGION").equals("MG") )
					return new BigDecimal( ((((ibrxValue+(ibrxValue*ipvaValue/100)))-((ibrxValue+(ibrxValue*ipvaValue/100))*icvaValue/100))/(1-(isicValue/100))*(istbValue/100))  );

				else if (zzdecreto_difal.equals("2"))  //2 - Reg.Cálculo BA		//(pricingItem.getAttributeValue("REGION").equals("BA") )
					return new BigDecimal( ((((ibrxValue-(ibrxValue+(ibrxValue*ipvaValue/100))*icvaValue/100)+(ibrxValue*ipvaValue/100))/(1-(isicValue/100))*(istbValue/100)))  );

				else if (zzdecreto_difal.equals("3"))  //3 - Reg.Cálculo SE		
					return new BigDecimal( (ibrxValue+(ibrxValue*ipvaValue/100))/(1-((isicValue-icvaValue)/100)) *istbValue/100  );
				
				else if (zzdecreto_difal.equals("4"))  //4 - Reg.Cálculo GO		
					return new BigDecimal( (ibrxValue+ibx2Value)/(1-(isicValue/100)) *istbValue/100  );
			}
			else if (pricingCondition.getConditionTypeName().equals("ZX41"))
			{
				if (zzdecreto_difal.equals("1"))  //1 - Reg.Cálculo MG			//(pricingItem.getAttributeValue("REGION").equals("MG") )
					return new BigDecimal( ((((ibrxValue+(ibrxValue*ipvaValue/100)))-((ibrxValue+(ibrxValue*ipvaValue/100))*icvaValue/100))/(1-(isicValue/100))*(istbValue/100))*(isicValue/100)-((ibrxValue+(ibrxValue*ipvaValue/100))*icvaValue/100)  ); 

				else if (zzdecreto_difal.equals("2"))  //2 - Reg.Cálculo BA		//(pricingItem.getAttributeValue("REGION").equals("BA") )
					return new BigDecimal( (((((ibrxValue-(ibrxValue+(ibrxValue*ipvaValue/100))*icvaValue/100+(ibrxValue*ipvaValue/100)))/(1-(isicValue/100))*(istbValue/100)))*(isicValue-icvaValue)/100)  );
				
				else if (zzdecreto_difal.equals("3"))  //3 - Reg.Cálculo SE		
					return new BigDecimal( ((ibrxValue+(ibrxValue*ipvaValue/100))/(1-((isicValue-icvaValue)/100))*istbValue/100)*(isicValue-icvaValue)/100  );
				
				else if (zzdecreto_difal.equals("4"))  //4 - Reg.Cálculo GO		
					return new BigDecimal( (ibrxValue+ibx2Value)/(1-(isicValue/100))*(isicValue-icvaValue)/100  );
			}
		}
  
		return null;//pricingCondition.getConditionValue().getValue();
    }
}
