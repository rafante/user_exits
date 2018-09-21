/* F�rmula..: 985 
 * Criada em: 23/01/2008
 * Criador..: Cl�udio L. M�rtire
 * 
 * A f�rmula 985 foi criada para acertar o c�lculo dos valores, pois o esquema de 
 * c�lculo baseado na RVABRA calcula alguns valores de forma diferente do esquema
 * baseado na RVJBRA, por isso foi adotado a utiliza��a de um FATOR de ajuste para
 * acertar estes valores. 
 * 
 * Os valores do FATOR de ajuste s�o atualizados no ECC atrav�s da transa��o ZSDXXX
 * toda altera��o/inclus�o/exclus�o de registros nesta transa��o deve ser realizada 
 * a mesma manuten��o nesta f�rmula obedecendo as regras de acordo com as codi��es de 
 * pre�o: DICM, ICVA, ICBS, DIPI, IPVA e IPBS  
 */
package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import userexits.belgo.pricing.utils.Utils;

import com.sap.spe.conversion.ICurrencyValue;
//import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.transactiondata.IPricingCondition;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition985 extends ValueFormulaAdapter {

	public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
			IPricingConditionUserExit pricingCondition) {

		BigDecimal conditionValue = new BigDecimal(0.0);

		// CONSTANTES
		/*
		 * DESATIVA��O VALOR AJUSTADO BigDecimal c_4 = new
		 * BigDecimal(4).setScale(3); BigDecimal c_5 = new
		 * BigDecimal(5).setScale(3); BigDecimal c_7 = new
		 * BigDecimal(7).setScale(3); BigDecimal c_10 = new
		 * BigDecimal(10).setScale(3); BigDecimal c_12 = new
		 * BigDecimal(12).setScale(3); BigDecimal c_13 = new
		 * BigDecimal(13).setScale(3); BigDecimal c_15 = new
		 * BigDecimal(15).setScale(3); BigDecimal c_17 = new
		 * BigDecimal(17).setScale(3); BigDecimal c_18 = new
		 * BigDecimal(18).setScale(3); BigDecimal c_19 = new
		 * BigDecimal(19).setScale(3); BigDecimal c_58 = new
		 * BigDecimal("58.82").setScale(3);
		 */
		
		BigDecimal c_0 = new BigDecimal(0).setScale(3); 
		BigDecimal c_100 = new BigDecimal(100).setScale(3);
		// DESATIVA��O VALOR AJUSTADO BigDecimal c_n100 = new
		// BigDecimal(-100).setScale(3);

		// DESATIVA��O VALOR AJUSTADO BigDecimal xworkd =
		// pricingItem.getSubtotal(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_D).getValue().setScale(20);
		// DESATIVA��O VALOR AJUSTADO BigDecimal ZFATOR = new
		// BigDecimal(1).setScale(20);
		// Canal de distribui��o
		String DIS_CHANNEL = pricingItem.getAttributeValue("DIS_CHANNEL");

		// Valor das condicoes
		ICurrencyValue DICM = pricingItem.findPricingCondition(200,1)
				.getConditionRate();
		ICurrencyValue ICVA = pricingItem.findPricingCondition(201, 1)
				.getConditionRate();
		ICurrencyValue ICBS = pricingItem.findPricingCondition(202, 1)
				.getConditionRate();
		/*
		 * DESATIVA��O VALOR AJUSTADO ICurrencyValue DIPI =
		 * pricingItem.findPricingCondition(240,1).getConditionRate();
		 * ICurrencyValue IPVA =
		 * pricingItem.findPricingCondition(241,1).getConditionRate();
		 * ICurrencyValue IPBS =
		 * pricingItem.findPricingCondition(242,1).getConditionRate();
		 * ICurrencyValue DCOF =
		 * pricingItem.findPricingCondition(250,1).getConditionRate();
		 * ICurrencyValue DPIS =
		 * pricingItem.findPricingCondition(255,1).getConditionRate();
		 */

		/*
		 * IN�?CIO - ZONA FRANCA
		 */
		// Retira os impostos para os casos de venda para Zona Franca, esta
		// necessidade � devido a
		// negocia��o do pre�o pelos vendedores ser sem os impostos para as
		// vendas ZF
		/* // *//* // */// */*//*/*//*/*//*/*//*/*//*/*//*/*//*/*//*/*//*/*//*/*//*/*//*/*//*/*//*/*//*/*//*/*//
		// ZONA FRANCA => Se for uma venda para zona franca
		String TAX_GROUP_BP_01 = pricingItem
				.getAttributeValue("TAX_GROUP_BP_01");
		if (TAX_GROUP_BP_01.compareTo("2-ZONA-FRANCA") == 0 || TAX_GROUP_BP_01.compareTo("9-OUTROS") == 0) {
			BigDecimal zfICMS = new BigDecimal(0).setScale(20);
			BigDecimal zfCOFINS = new BigDecimal(0).setScale(20);
			BigDecimal zfPIS = new BigDecimal(0).setScale(20);
			BigDecimal zfFATOR = new BigDecimal(0).setScale(20);
			String sCondTypeName = pricingCondition.getConditionTypeName();

			// Recupera ICMS
			IPricingCondition zfBXZF = pricingItem.findPricingCondition(617, 1);
			IPricingCondition zfDICM = pricingItem.findPricingCondition(200,1);
			 
			if (zfBXZF != null) 
  		    	if((zfBXZF.getConditionTypeName().compareTo("BXZF")==0) 
 		    			 || (DIS_CHANNEL.compareTo("40")==0 && sCondTypeName == null))//sCondTypeName.compareTo("ZBCI")!=0 )) //CONDI��O DIFERENTE DE ZBCI 
 		    		if(zfDICM.getConditionTypeName().compareTo("DICM")==0 && DICM.getValue().compareTo(c_0)!=0)
 					  zfICMS = ICVA.getValue().multiply(
							ICBS.getValue().divide(c_100,
									BigDecimal.ROUND_HALF_UP));
			
			// Recupera COFINS
			IPricingCondition zfYFXC = pricingItem.findPricingCondition(684, 1);
			if (zfYFXC != null)
 		     	 if((zfYFXC.getConditionTypeName().compareTo("YFXC")==0) 
  		     			 && (zfYFXC.getConditionRate().getValue().compareTo(c_0)!=0))
  		     	 {
					IPricingCondition zfBCO1 = pricingItem
							.findPricingCondition(251, 1);
					if (zfBCO1 != null)
						zfCOFINS = zfBCO1.getConditionRate().getValue();
				}

			// Recupera PIS
			IPricingCondition zfYFXP = pricingItem.findPricingCondition(685, 1);
			if (zfYFXP != null)
 		     	 if((zfYFXP.getConditionTypeName().compareTo("YFXP")==0)
  		     			 && (zfYFXP.getConditionRate().getValue().compareTo(c_0)!=0))
  		     	 {
					IPricingCondition zfBPI1 = pricingItem
							.findPricingCondition(256, 1);
					if (zfBPI1 != null)
						zfPIS = zfBPI1.getConditionRate().getValue();
				}

			zfFATOR = c_100.subtract(zfICMS.add(zfCOFINS.add(zfPIS))).divide(c_100, BigDecimal.ROUND_HALF_UP);

	  		if (DIS_CHANNEL.compareTo("40")==0 && sCondTypeName == null)//sCondTypeName.compareTo("ZBCI")!=0 )				
				conditionValue = (pricingCondition.getConditionValue()
						.getValue().multiply(zfFATOR)).divide(
						c_100.subtract(zfICMS).divide(c_100,
								BigDecimal.ROUND_HALF_UP),
						BigDecimal.ROUND_HALF_UP);
			else
				conditionValue = pricingCondition.getConditionValue()
						.getValue().multiply(zfFATOR);
		} else
			conditionValue = pricingCondition.getConditionValue().getValue();
		/*
		 * FIM - ZONA FRANCA
		 */

		/*
		 * Início - Alterações Diêgo Silva - 03.11.2014 - ICMS-ST Alíquota
		 * estado origem (C058293)
		 */
		// Verifica se a condição é diferente de ZBCI
		if (pricingCondition.getConditionTypeName() == null
				|| !pricingCondition.getConditionTypeName().equals("ZBCI")) {

			// Recupera a condição DCIM
			IPricingConditionUserExit dcimCondition = Utils
					.GetItemPricingCondition(pricingItem, "DICM");
			
			String Tab527 	   = new String("CUS527");
			String RebateTable = dcimCondition.getConditionTableName();
			
			if (RebateTable != null)
			{
							
				// Verifica se a condição "DCIM" buscou o acesso referente a
				// tabela 527
				if (RebateTable.equals(Tab527)) {
	
					// Determina o Destino
					String taxJurCodeDestino = pricingItem
							.getAttributeValue("TAXJURCODE");
					String destino = Utils
							.GetRegionFromTaxJurCode(taxJurCodeDestino);
	
					/*
					 * Recupera o Domicílio Fiscal (Se o caracter 3 de TAXJURCODE
					 * for vazio, Domicílio Fiscal será vazio, caso contrário, o
					 * Domicílio Fiscal será o primeiro caracter de 'TAXJURCODE'
					 */
					String domFiscalDestino = taxJurCodeDestino.substring(2, 3)
							.equals(" ") ? " " : taxJurCodeDestino.substring(0, 1);
	
					// Recupera o valor da condição DCIM
					BigDecimal dcimValue = dcimCondition.getConditionValue()
							.getValue();
	
					/*
					 * Verifica se o valor da condição DCIM é zero e se o destino é
					 * diferente de origem e se o Domicílio Fiscal é igual a Z
					 */
					if (dcimValue.compareTo(BigDecimal.ZERO) == 0
							&& !destino.equals(pricingItem
									.getAttributeValue("TAXJURCODE_FROM"))
							&& domFiscalDestino.equals("Z")) {
	
						// Recupera condições
						// • ICVA Montante (KBETR)
						// • ICBS Montante (KBETR)
						// • IPVA Montante (KBETR)
						// • BPI1 Montante (KBETR)
						// • BCO1 Montante (KBETR)
						// • ZNFY Valor (KWERT)
						// • DCOF Montante (KBETR)
						// • DIPI Montante (KBETR)
						// • DPIS Montante (KBETR)
						double icvaRate = Utils
								.GetItemPricingConditionRateAsDouble(pricingItem,
										"ICVA");
	
						double icbsRate = Utils
								.GetItemPricingConditionRateAsDouble(pricingItem,
										"ICBS");
	
						double ipvaRate = Utils
								.GetItemPricingConditionRateAsDouble(pricingItem,
										"IPVA");
	
						double bpi1Rate = Utils
								.GetItemPricingConditionRateAsDouble(pricingItem,
										"BPI1");
	
						double bc01Rate = Utils
								.GetItemPricingConditionRateAsDouble(pricingItem,
										"BCO1");
	
						double znfyValue = Utils
								.GetItemPricingConditionValueAsDouble(pricingItem,
										"ZNFY");
	
						double dcofRate = Utils
								.GetItemPricingConditionRateAsDouble(pricingItem,
										"DCOF");
	
						// double dipiRate = Utils
						// .GetItemPricingConditionRateAsDouble(pricingItem,
						// "DIPI");
	
						double dpisRate = Utils
								.GetItemPricingConditionRateAsDouble(pricingItem,
										"DPIS");
	
						// Se DPIS = 0,00
						// BPI1 =0,00.
						if (dpisRate == 0)
							bpi1Rate = 0;
	
						// Se DCOF = 0,00
						// BCO1 = 0,00.
						if (dcofRate == 0)
							bc01Rate = 0;
	
						// Se DIPI = 0.00
						// IPVA = 0,00.
						// if (dipiRate == 0)
						// ipvaRate = 0;
	
						// Calcular:
						// L_FATOR =
						// 1-((((ICVA*(ICBS/100)*(1+IPVA/100))+BPI1+BCO1))/100).
						// XKWERT = (ZNFY / L_FATOR).
						double fator = 1 - ((((icvaRate * (icbsRate / 100) * (1 + ipvaRate / 100))
								+ bpi1Rate + bc01Rate)) / 100);
	
						// XKWERT = (ZNFY / L_FATOR).
						double value = znfyValue / fator;
						BigDecimal xkwert = new BigDecimal(value);
						return xkwert;
					}
				}
			}
		}

		/*
		 * Fim - Alterações Diêgo Silva - 03.11.2014 - ICMS-ST Alíquota estado
		 * origem (C058293)
		 */
		// Verifica se é a condição ZP04
		
		if (conditionValue.compareTo(PricingTransactiondataConstants.ZERO)!=0) 
		{
			return conditionValue;
		}
		else
		{
			return PricingTransactiondataConstants.ZERO;
		}

		// Ir� executar somente para situa��es que N�O s�o de zona franca
		// Regra para aplica��o do FATOR de ajuste
		// DIPI=100; DICM=100 ou DICM=-100; IPBS=100; ICBS=100
		/*
		 * if(DIPI.getValue().compareTo(c_100)==0 &&
		 * (DICM.getValue().compareTo(c_100
		 * )==0||DICM.getValue().compareTo(c_n100)==0) &&
		 * IPBS.getValue().compareTo(c_100)==0 &&
		 * ICBS.getValue().compareTo(c_100)==0 ) { if
		 * (DIS_CHANNEL.compareTo("20")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } } else if
		 * (DIS_CHANNEL.compareTo("30")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_12)==0) //12 ZFATOR = new
		 * BigDecimal("1.00000000000000").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } } else if
		 * (DIS_CHANNEL.compareTo("40")==0) { // IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.00597942944794").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_12)==0) //12 ZFATOR = new
		 * BigDecimal("0.999355793505702").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.997935480081641").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.991895352245207").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.990288713910761").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.988640427922582").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.00589520211754").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_12)==0) //12 ZFATOR = new
		 * BigDecimal("0.999193636264575").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.997755535335153").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.991635509955656").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.990006458810405").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.988334671782517").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.00547214913412").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_12)==0) //12 ZFATOR = new
		 * BigDecimal("0.998376124643406").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.99684762275194").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.990319998438079").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.988576178601116").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.986783858086007").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.00504587551896").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_12)==0) //12 ZFATOR = new
		 * BigDecimal("0.997547232350017").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.995925832610136").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.988976688665937").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.987113433015072").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.98519534066559").setScale(20); } } } // DIPI=0;
		 * DICM=100 ou DICM=-100; IPBS=100; ICBS=100 else
		 * if(DIPI.getValue().compareTo(c_0)==0 &&
		 * (DICM.getValue().compareTo(c_100
		 * )==0||DICM.getValue().compareTo(c_n100)==0) &&
		 * IPBS.getValue().compareTo(c_100)==0 &&
		 * ICBS.getValue().compareTo(c_100)==0 ) { if
		 * (DIS_CHANNEL.compareTo("20")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.006315070831200").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408500").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624860").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367630").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.006315070831200").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408500").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624860").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367630").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.006315070831200").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408500").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624860").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367630").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.006315070831200").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408500").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624860").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367630").setScale(20); } } else if
		 * (DIS_CHANNEL.compareTo("30")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.006315070831200").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408500").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624860").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367630").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.006315070831200").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408500").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367630").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408500").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367630").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.006315070831200").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408500").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367630").setScale(20); } } else if
		 * (DIS_CHANNEL.compareTo("40")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.0063150708312").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.006315070831200").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_12)==0) //12 ZFATOR = new
		 * BigDecimal("1.000000000000000").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.990288713910760").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.006315070831200").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_12)==0) //12 ZFATOR = new
		 * BigDecimal("1.000000000000000").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.006315070831200").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_12)==0) //12 ZFATOR = new
		 * BigDecimal("1.000000000000000").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_0)==0) //ZERO ZFATOR = new
		 * BigDecimal("1.014095238095240").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_7)==0) //7 ZFATOR = new
		 * BigDecimal("1.006315070831200").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_12)==0) //12 ZFATOR = new
		 * BigDecimal("1.000000000000000").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_13)==0) //13 ZFATOR = new
		 * BigDecimal("0.998649881408502").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261430").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_18)==0) //18 ZFATOR = new
		 * BigDecimal("0.991405342624855").setScale(20); else
		 * if(ICVA.getValue().compareTo(c_19)==0) //19 ZFATOR = new
		 * BigDecimal("0.989849108367627").setScale(20); } }
		 * 
		 * } // DIPI=100; DICM=100 ou DICM=-100; IPBS=100; ICBS=58,82 else
		 * if(DIPI.getValue().compareTo(c_100)==0 &&
		 * (DICM.getValue().compareTo(c_100
		 * )==0||DICM.getValue().compareTo(c_n100)==0) &&
		 * IPBS.getValue().compareTo(c_100)==0 &&
		 * ICBS.getValue().compareTo(c_58)==0 ) { if
		 * (DIS_CHANNEL.compareTo("20")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } } else if
		 * (DIS_CHANNEL.compareTo("30")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.992924077261427").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } } else if
		 * (DIS_CHANNEL.compareTo("40")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.991895352245206").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.00196860867252").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.00131977884787").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.00066361761277").setScale(20); } } } // DIPI=0;
		 * DICM=100 ou DICM=-100; IPBS=100; ICBS=58,82 else
		 * if(DIPI.getValue().compareTo(c_0)==0 &&
		 * (DICM.getValue().compareTo(c_100
		 * )==0||DICM.getValue().compareTo(c_n100)==0) &&
		 * IPBS.getValue().compareTo(c_100)==0 &&
		 * ICBS.getValue().compareTo(c_58)==0 ) { if
		 * (DIS_CHANNEL.compareTo("20")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } } else if
		 * (DIS_CHANNEL.compareTo("30")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } } else if
		 * (DIS_CHANNEL.compareTo("40")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_4)==0) //4 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_5)==0) //5 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_10)==0) //10 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } else
		 * if(IPVA.getValue().compareTo(c_15)==0) //15 { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("1.0026102292769").setScale(20); } } } // DIPI=100;
		 * DICM=100 ou DICM=-100; IPBS=0; ICBS=100 ( INICIO 30.09.211) else
		 * if(DIPI.getValue().compareTo(c_100)==0 &&
		 * (DICM.getValue().compareTo(c_100
		 * )==0||DICM.getValue().compareTo(c_n100)==0) &&
		 * IPBS.getValue().compareTo(c_0)==0 &&
		 * ICBS.getValue().compareTo(c_100)==0 ) { if
		 * (DIS_CHANNEL.compareTo("20")==0 //CANAL DISTRIBUI��O ||
		 * DIS_CHANNEL.compareTo("30")==0) { //IPVA
		 * if(IPVA.getValue().compareTo(c_0)==0) //ZERO { // ICVA
		 * if(ICVA.getValue().compareTo(c_17)==0) //17 ZFATOR = new
		 * BigDecimal("0.99292407726143").setScale(20); } } //( FIM 30.09.211) }
		 * // DIPI=100; DICM=0; IPBS=100; ICBS=100 else
		 * if(DIPI.getValue().compareTo(c_100)==0 &&
		 * DICM.getValue().compareTo(c_0)==0 &&
		 * IPBS.getValue().compareTo(c_100)==0 &&
		 * ICBS.getValue().compareTo(c_100)==0 ) { if (
		 * (DIS_CHANNEL.compareTo("20")==0 //CANAL DISTRIBUI��O ||
		 * DIS_CHANNEL.compareTo("30")==0 || DIS_CHANNEL.compareTo("40")==0 ) &&
		 * (IPVA.getValue().compareTo(c_0)==0 //IPVA ||
		 * IPVA.getValue().compareTo(c_4)==0 ||
		 * IPVA.getValue().compareTo(c_5)==0 ||
		 * IPVA.getValue().compareTo(c_10)==0 ||
		 * IPVA.getValue().compareTo(c_15)==0) &&
		 * (ICVA.getValue().compareTo(c_7)==0 //ICVA ||
		 * ICVA.getValue().compareTo(c_12)==0 ||
		 * ICVA.getValue().compareTo(c_13)==0 ||
		 * ICVA.getValue().compareTo(c_17)==0 ||
		 * ICVA.getValue().compareTo(c_18)==0 ||
		 * ICVA.getValue().compareTo(c_19)==0) ) { ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); } } // DIPI=100; DICM=0;
		 * IPBS=100; ICBS=58,82 else if(DIPI.getValue().compareTo(c_100)==0 &&
		 * DICM.getValue().compareTo(c_0)==0 &&
		 * IPBS.getValue().compareTo(c_100)==0 &&
		 * ICBS.getValue().compareTo(c_58)==0 ) { if (
		 * (DIS_CHANNEL.compareTo("20")==0 //CANAL DISTRIBUI��O ||
		 * DIS_CHANNEL.compareTo("30")==0 || DIS_CHANNEL.compareTo("40")==0 ) &&
		 * (IPVA.getValue().compareTo(c_0)==0 //IPVA ||
		 * IPVA.getValue().compareTo(c_4)==0 ||
		 * IPVA.getValue().compareTo(c_5)==0 ||
		 * IPVA.getValue().compareTo(c_10)==0 ||
		 * IPVA.getValue().compareTo(c_15)==0) &&
		 * (ICVA.getValue().compareTo(c_17)==0) ) { ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); } } // DIPI=0; DICM=0;
		 * IPBS=100; ICBS=100 else if(DIPI.getValue().compareTo(c_0)==0 &&
		 * DICM.getValue().compareTo(c_0)==0 &&
		 * IPBS.getValue().compareTo(c_100)==0 &&
		 * ICBS.getValue().compareTo(c_100)==0 ) { if (
		 * (DIS_CHANNEL.compareTo("20")==0 //CANAL DISTRIBUI��O ||
		 * DIS_CHANNEL.compareTo("30")==0 || DIS_CHANNEL.compareTo("40")==0 ) &&
		 * (IPVA.getValue().compareTo(c_0)==0 //IPVA ||
		 * IPVA.getValue().compareTo(c_4)==0 ||
		 * IPVA.getValue().compareTo(c_5)==0 ||
		 * IPVA.getValue().compareTo(c_10)==0 ||
		 * IPVA.getValue().compareTo(c_15)==0) &&
		 * (ICVA.getValue().compareTo(c_0)==0 //ICVA ||
		 * ICVA.getValue().compareTo(c_7)==0 ||
		 * ICVA.getValue().compareTo(c_12)==0 ||
		 * ICVA.getValue().compareTo(c_13)==0 ||
		 * ICVA.getValue().compareTo(c_17)==0 ||
		 * ICVA.getValue().compareTo(c_18)==0 ||
		 * ICVA.getValue().compareTo(c_19)==0) ) { ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); } } // DIPI=0; DICM=0;
		 * IPBS=100; ICBS=58,82 else if(DIPI.getValue().compareTo(c_0)==0 &&
		 * DICM.getValue().compareTo(c_0)==0 &&
		 * IPBS.getValue().compareTo(c_100)==0 &&
		 * ICBS.getValue().compareTo(c_100)==0 ) { if (
		 * (DIS_CHANNEL.compareTo("20")==0 //CANAL DISTRIBUI��O ||
		 * DIS_CHANNEL.compareTo("30")==0 || DIS_CHANNEL.compareTo("40")==0 ) &&
		 * (IPVA.getValue().compareTo(c_0)==0 //IPVA ||
		 * IPVA.getValue().compareTo(c_5)==0 ||
		 * IPVA.getValue().compareTo(c_5)==0 ||
		 * IPVA.getValue().compareTo(c_10)==0 ||
		 * IPVA.getValue().compareTo(c_15)==0) &&
		 * (ICVA.getValue().compareTo(c_17)==0) ) { ZFATOR = new
		 * BigDecimal("1.01409523809524").setScale(20); } } if
		 * (DPIS.getValue().compareTo(c_0)!=0 &&
		 * DCOF.getValue().compareTo(c_0)!=0) { String COND =
		 * pricingCondition.getConditionTypeName(); if (COND != null ) if
		 * (COND.compareTo("ZBCI")==0) //N�O EXECUTA PARA A CONDI��O ZBCI ZFATOR
		 * = new BigDecimal(1).setScale(20);
		 * 
		 * return xworkd.multiply(ZFATOR); } else return xworkd;
		 */
	}
}