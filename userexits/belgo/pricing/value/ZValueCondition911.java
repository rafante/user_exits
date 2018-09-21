package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.conversion.IAlternativeUnitFraction;
import com.sap.spe.conversion.ICurrencyUnit;
import com.sap.spe.conversion.ICurrencyValue;
import com.sap.spe.conversion.IPhysicalUnit;
import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.IPricingCondition;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition911 extends ValueFormulaAdapter {

    //Determinar o número de decimais utilizados no cálculo, como as
    //filiais da BELGO utilizam 5 casas decimais será utilizado a constante
    //C_NFDEC
    private static final int C_NFDEC = 5;
    private static final int C_VALUE_TWO = 2;
	
    public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
            IPricingConditionUserExit pricingCondition) {

	        //Declarações
	        String div = "";
	        int f = 0;
	        int posKumza_Kumne = 0;
	        int posUmvkz_Umvkn = 0;
	        BigDecimal Resultb = new BigDecimal("0");
	        BigDecimal Thousand = new BigDecimal("1000");
	        BigDecimal Retorno = new BigDecimal("0");
	
	        String sCondTypeName = pricingCondition.getConditionTypeName();
	        String DIS_CHANNEL = pricingItem.getAttributeValue("DIS_CHANNEL");
	        IPricingCondition cISTN = pricingItem.findPricingCondition(288,1);
	        ICurrencyValue vISTN = null; 
	        
	        if (sCondTypeName.compareTo("ZSBS")==0){
	        	Retorno = pricingCondition.getConditionValue().getValue();
	        	if (DIS_CHANNEL.compareTo("30")==0 || DIS_CHANNEL.compareTo("31")==0){
	    	        if (cISTN != null) 
	    	        { 
	    		        vISTN = pricingItem.findPricingCondition(288,1).getConditionRate();
	        		    if(cISTN.getConditionTypeName().compareTo("ISTN")==0 && vISTN.getValue().compareTo(PricingTransactiondataConstants.ZERO)>0)
	        		       Retorno = PricingTransactiondataConstants.ZERO;
	    	        }
	        	}
	        }
	        else {
		        //Declarar e recuperar o valor da variável XWORKL
		        BigDecimal xworkl = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_L).getValue();
		        //Limpar variável de trabalho XWORKL
		        pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_L,PricingTransactiondataConstants.ZERO);
		
	       		
		        //Setar o valor da base de condição
		        pricingCondition.setConditionBaseValue(xworkl);
		
		        //Retirar o ponto decimal de xworkl
		        Double Result_temp = new Double(xworkl.movePointRight(2).doubleValue());
		        BigDecimal Result = new BigDecimal(Result_temp.toString());
		
		        //Unidade de preço da condição
		        BigDecimal ykpein = pricingCondition.getPricingUnit().getValue();
		
		        //Realizar cálculo retirando a parte decimal
		        BigDecimal x_temp = new BigDecimal(Math.log(ykpein.abs().doubleValue())/ Math.log(10));
		        Integer x = new Integer(x_temp.intValue());
		
		        //Calcular o arredondamento do fator
		        f = C_NFDEC - C_VALUE_TWO - x.intValue();
		
		        if(f < 0)
		        {
		            f = Math.abs(f);
		            div = "X";
		        }
		
		        BigDecimal rf = new BigDecimal(Math.pow(10, f));
		
		        //Criar vetor de objetos com todas as unidades alternativas, fatores e divisores dos produtos
		        IAlternativeUnitFraction[] prFractions = pricingItem.getProduct().getAlternativeUnitConversionFractions();
		
		        //Todos as unidades do produto
		        IPhysicalUnit[] prUnits = pricingItem.getProduct().getAvailableQuantityUnits();
		
		        //Verificar as unidades
		        if (pricingItem.getProductQuantity().getUnitName().equals(pricingCondition.getPricingUnit().getUnitName()))
		        {
		            //Posição dos fatores
		            posKumza_Kumne = prUnits.length - 1;
		            posUmvkz_Umvkn = prUnits.length - 1;
		        }
		        else
		        {
		            //Posição dos fatores
		            posKumza_Kumne = prUnits.length - 2;
		            posUmvkz_Umvkn = prUnits.length - 1;
		        }
		
		        //Instanciar os dois ÚLTIMOS OBJETOS, que são referentes a unidade base/alternativa do produto
		        IAlternativeUnitFraction productUnitsFactors1 = (IAlternativeUnitFraction) prFractions[posKumza_Kumne];
		        IAlternativeUnitFraction productUnitsFactors2 = (IAlternativeUnitFraction) prFractions[posUmvkz_Umvkn];
		
		        //Campos utilizados para a conversão da condição
		        BigDecimal komp_kumza = new BigDecimal(Integer.toString(productUnitsFactors1.getFraction().getNumerator()));
		        BigDecimal komp_kumne = new BigDecimal(Integer.toString(productUnitsFactors1.getFraction().getDenominator()));
		
		        //Campos utilizados para a conversão da venda
		        BigDecimal komp_umvkz = new BigDecimal(Integer.toString(productUnitsFactors2.getFraction().getNumerator()));
		        BigDecimal komp_umvkn = new BigDecimal(Integer.toString(productUnitsFactors2.getFraction().getDenominator()));
		
		        //Desinstanciar objetos
		        productUnitsFactors1 = null;
		        productUnitsFactors2 = null;
		
		        //Converter o resultado de preço/unidade condição para preço unidade
		        //venda
		        if(div.length() == 0)
		        {
		            Result = rf.multiply(Result.multiply(komp_kumne.multiply(komp_umvkz)))
		            .divide(komp_kumza,BigDecimal.ROUND_HALF_UP).divide(komp_umvkn,BigDecimal.ROUND_HALF_UP)
		            .divide(ykpein,BigDecimal.ROUND_HALF_UP);
		        }
		        else
		        {
		            Result = Result.multiply(komp_kumne.multiply(komp_umvkz))
		            .divide(komp_kumza,BigDecimal.ROUND_HALF_UP).divide(komp_umvkn,BigDecimal.ROUND_HALF_UP)
		            .divide(ykpein,BigDecimal.ROUND_HALF_UP).divide(rf,BigDecimal.ROUND_HALF_UP);
		        }
		
		        //Colocar os dígitos de volta no Resultb
		        if(div.length() == 0)
		        {
		            Resultb = Result.multiply(komp_kumza.multiply(komp_umvkn.multiply(Thousand.multiply(ykpein))))
		            .divide(komp_kumne,BigDecimal.ROUND_HALF_UP).divide(komp_umvkz,BigDecimal.ROUND_HALF_UP).divide(rf,BigDecimal.ROUND_HALF_UP);
		
		            Result = Result.multiply(Thousand).divide(rf,BigDecimal.ROUND_HALF_UP);
		        }
		        else
		        {
		            Resultb = rf.multiply(Result.multiply(komp_kumza.multiply(komp_umvkn).multiply(Thousand).multiply(ykpein)))
		            .divide(komp_kumne,BigDecimal.ROUND_HALF_UP).divide(komp_umvkz,BigDecimal.ROUND_HALF_UP);
		
		            Result = rf.multiply(Result.multiply(Thousand));
		        }
		
		        //Unidade moeda
		        ICurrencyUnit documentCurrency = pricingItem.getUserExitDocument().getDocumentCurrencyUnit();
		
		        Resultb = new BigDecimal(Resultb.toString());
		        Resultb = Resultb.divide(Thousand, BigDecimal.ROUND_HALF_UP);
		
		        Resultb = Resultb.movePointLeft(2);
		
		        //Retornar os valores que foram calculados
		        //Montante ou percentagem da condição
		        pricingCondition.setConditionRate(Resultb,documentCurrency);
		
		        //Quantidade (KOMP-MGAME)
		        BigDecimal komp_mgame = pricingItem.getProductQuantity().getValue();
		
		        //Fator de divisão
		        BigDecimal facDivide = Thousand.multiply(PricingTransactiondataConstants.HUNDRED);
		
		        //Valor da Condição
		        Retorno = Result.multiply(komp_mgame).divide(facDivide, BigDecimal.ROUND_HALF_UP); 
		
		        //Os campos abaixo não foram alterados então não serão setados novamente
		        //komp_kumza, komp_kumne, xkomv_kmein, xkomv_kumza, xkomv_kumne, xkomv_kpein, xkomv_kawrt
	        }
	        return Retorno;
        }
}