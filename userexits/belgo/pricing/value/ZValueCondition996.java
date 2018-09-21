package userexits.belgo.pricing.value;

import java.math.BigDecimal;
import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition996 extends ValueFormulaAdapter {

	   public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
		        IPricingConditionUserExit pricingCondition) {
		  
//		  Recupera o prazo m�dio da condi��o
//		  ***//***//***//***  
		   //Prazo m�dio da condi��o e valor da condi��o para a f�rmula 996
		    int PRAZO_MEDIO_INT = 0;

/*	       Executar fun��o RFC no R/3 para calcular o prazo m�dio da condi��o nas tabelas T052/T052s
           calculaPrazoMedio(komk_zterm);*/
		   
		  String ZZPRAZO_MEDIO = pricingItem.getAttributeValue("ZZPRAZO_MEDIO");
          if (!ZZPRAZO_MEDIO.equals("")){
       	   PRAZO_MEDIO_INT = Integer.parseInt(ZZPRAZO_MEDIO);
          }
          //Condi��o de pagamento para a ordem
          String komk_zterm = pricingItem.getAttributeValue("PMNTTRMS");
          String VVALTG = pricingItem.getAttributeValue("ZZVALTG");

          String VVALDT = "00000000";
          String VVERDAT = "00000000";

          //Converter para inteiro os calores
          int VVALDT_INT = Integer.parseInt(VVALDT);
          int VVALTG_INT = 0;

          //Verificar se o valor est� nulo
          if (!VVALTG.equals(""))
          {
               VVALTG_INT = Integer.parseInt(VVALTG);
          }

          int VVERDAT_INT = Integer.parseInt(VVERDAT);
          int PRAZO_PRORR_INT = 0;
          int PRAZO_PVL_INT = 0;

          //C�lculo da prorroga��o
          if(VVALDT_INT != 0)
          {
              //Realizar c�lculo
              PRAZO_PRORR_INT = VVALDT_INT - VVERDAT_INT;
          }
          else
          {
              if(VVALTG_INT != 0)
              {
                  PRAZO_PRORR_INT = VVALTG_INT;
              }
          }
          //Verificar o valor da prororroga��o
          if (PRAZO_PRORR_INT < 0)
          {
              PRAZO_PVL_INT = PRAZO_MEDIO_INT;
          }
          else
          {
              PRAZO_PVL_INT = PRAZO_MEDIO_INT + PRAZO_PRORR_INT;
          }
//		  ***//***//***//***  

          
          
//        Chamar RFC para calcular a taxa de juros
//          calculaTaxaJuros(xworkg, xworkh, PRAZO_PVL_INT);
          BigDecimal taxa_juros = new BigDecimal("0");
          BigDecimal expoe      = new BigDecimal("0");
          BigDecimal taxa_ordem = new BigDecimal("0");
          BigDecimal taxa_redordem = new BigDecimal("0");
          BigDecimal xkwert     = new BigDecimal("0");
          BigDecimal pow = new BigDecimal("0");

          BigDecimal PRAZO_PVL_BIG = new BigDecimal(Integer.toString(PRAZO_PVL_INT)).add(new BigDecimal("0.000000000000000001")); 
//          PRAZO_PVL_BIG.add(new BigDecimal(0.000000000000000001));
          
          //Recuperar o valor das vari�veis de trabalho XWORKH e XWORKG
           BigDecimal xworkh = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_H).getValue();
           BigDecimal xworkg = pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_G).getValue();
          // Previne para erro de divis�o por ZERO
           if(xworkh.compareTo(PricingTransactiondataConstants.ZERO)!=0)
             taxa_juros = PricingTransactiondataConstants.ONE.setScale(40).add(xworkg.setScale(40).divide(xworkh.setScale(40),BigDecimal.ROUND_HALF_UP)).setScale(40);
           else
        	 taxa_juros = PricingTransactiondataConstants.ZERO;
           
           expoe = PRAZO_PVL_BIG.divide(new BigDecimal("30"),25,BigDecimal.ROUND_HALF_UP);
           pow = new BigDecimal(Math.pow( taxa_juros.setScale(80).doubleValue(), expoe.setScale(25).doubleValue())).setScale(80);
           taxa_ordem = PricingTransactiondataConstants.ONE.setScale(80).divide(pow.setScale(pow.scale()),BigDecimal.ROUND_HALF_UP).setScale(80);
           
           taxa_redordem = PricingTransactiondataConstants.ONE.setScale(80).subtract(taxa_ordem.setScale(80)).setScale(80);
           xkwert = xworkh.setScale(40,BigDecimal.ROUND_HALF_UP).multiply(taxa_redordem.setScale(40,BigDecimal.ROUND_HALF_UP)).setScale(40,BigDecimal.ROUND_HALF_UP);
           xkwert = xkwert.setScale(40,BigDecimal.ROUND_HALF_UP).multiply(PricingTransactiondataConstants.MINUS_ONE.setScale(40,BigDecimal.ROUND_HALF_UP)).setScale(40,BigDecimal.ROUND_HALF_UP);
           
          
           //Verificar a condi��o de pagamento
           if(komk_zterm.equals("C072"))
           {
               //Setar valor para KZWI3
        	   pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_3,PricingTransactiondataConstants.ZERO);
               //Setar valor para KZWI2
        	   pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_2,new BigDecimal(Integer.toString(PRAZO_PVL_INT)));

               //Retonar o valor para KWERT
               return PricingTransactiondataConstants.ZERO;
           }
           else 
           {
               //Setar valor para KZWI3
        	   pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_3,xkwert);
               //Setar valor para KZWI2
        	   pricingItem.setSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_2,new BigDecimal(Integer.toString(PRAZO_PVL_INT)));

               //Retonar o valor para KWERT
        	   return xkwert.setScale(40);
           } 

     }
}


