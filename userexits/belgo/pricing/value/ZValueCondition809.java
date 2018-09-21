/* Fórmula..: 809 
 * Criada em: 25/11/2011
 * Criador..: Cláudio L. Mártire
 * 
 * A fórmula 809 foi criada para retornar os impostos para os casos de venda para Zona Franca.
 * Esta necessidade é devido a negociação do preço pelos vendedores ser sem os impostos para as vendas ZF.
 */	
package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.conversion.ICurrencyValue;
import com.sap.spe.pricing.transactiondata.IPricingCondition;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;
 
	

public class ZValueCondition809 extends ValueFormulaAdapter {

	   public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
		        IPricingConditionUserExit pricingCondition) {
		      BigDecimal c_0   = new BigDecimal(0).setScale(3);
		   
		      ICurrencyValue DICM = pricingItem.findPricingCondition(200,1).getConditionRate();
			  ICurrencyValue ICVA = pricingItem.findPricingCondition(201,1).getConditionRate();
			  ICurrencyValue ICBS = pricingItem.findPricingCondition(202,1).getConditionRate();			  
		         
			  // ZONA FRANCA => Se for uma venda para zona franca 
			  String TAX_GROUP_BP_01 = pricingItem.getAttributeValue("TAX_GROUP_BP_01");
			  if (TAX_GROUP_BP_01.compareTo("2-ZONA-FRANCA")==0 || TAX_GROUP_BP_01.compareTo("9-OUTROS") == 0)
			  {
				  BigDecimal zfICMS   = new BigDecimal(0).setScale(20);  
				  BigDecimal zfCOFINS = new BigDecimal(0).setScale(20);  
				  BigDecimal zfPIS    = new BigDecimal(0).setScale(20);  
				  BigDecimal zfFATOR  = new BigDecimal(0).setScale(20);  
				  
	   		     //Recupera ICMS
				 IPricingCondition zfBXZF = pricingItem.findPricingCondition(617,1);
				 IPricingCondition zfDICM = pricingItem.findPricingCondition(200,1);
				 
	  		     if (zfBXZF != null)
	  		     	 if(zfBXZF.getConditionTypeName().compareTo("BXZF")==0)
	  		     		if(zfDICM.getConditionTypeName().compareTo("DICM")==0 && DICM.getValue().compareTo(c_0)!=0)
	  		     			zfICMS = ICVA.getValue().multiply(ICBS.getValue().divide(PricingTransactiondataConstants.HUNDRED,BigDecimal.ROUND_HALF_UP));
	 		     
	  		     //Recupera COFINS
				 IPricingCondition zfYFXC = pricingItem.findPricingCondition(684,1);
	  		     if(zfYFXC != null)
	  		     	 if((zfYFXC.getConditionTypeName().compareTo("YFXC")==0)
	  		     			 && (zfYFXC.getConditionRate().getValue().compareTo(c_0)!=0))
	  		     	 {
	 					IPricingCondition zfBCO1 = pricingItem.findPricingCondition(251,1);
	  		     		if(zfBCO1 != null)
	  		     			zfCOFINS = zfBCO1.getConditionRate().getValue(); 
	  		     	 }	 
	  		     		
	  		     //Recupera PIS
				 IPricingCondition zfYFXP = pricingItem.findPricingCondition(685,1);
	  		     if(zfYFXP != null)
	  		        if((zfYFXP.getConditionTypeName().compareTo("YFXP")==0)
		  		     			&& (zfYFXP.getConditionRate().getValue().compareTo(c_0)!=0))
		  		    {
	 					IPricingCondition zfBPI1 = pricingItem.findPricingCondition(256,1);
	  		     		if(zfBPI1 != null)
	  		     			zfPIS = zfBPI1.getConditionRate().getValue(); 
	  		     	 }	 
	  			  
	  		      zfFATOR =  PricingTransactiondataConstants.HUNDRED.subtract(zfICMS.add(zfCOFINS.add(zfPIS))).divide(PricingTransactiondataConstants.HUNDRED,BigDecimal.ROUND_HALF_UP);
	  		      return pricingCondition.getConditionValue().getValue().divide(zfFATOR, BigDecimal.ROUND_HALF_UP);
			  }
			  else 
				  return pricingCondition.getConditionValue().getValue();
			  
	   }
}