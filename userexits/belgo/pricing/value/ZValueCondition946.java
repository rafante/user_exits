package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import userexits.belgo.pricing.utils.Utils;

import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ZValueCondition946 extends ValueFormulaAdapter {

	public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
			IPricingConditionUserExit pricingCondition) {

		/*
		 * Início - Alterações Diêgo Silva - 03.11.2014 - ICMS-ST Alíquota
		 * estado origem (C058293)
		 */
		BigDecimal value = pricingCondition.getConditionValue().getValue();

		// Verifica se é a condição ZP04
		if (pricingCondition.getConditionTypeName() != null && pricingCondition.getConditionTypeName().equals("ZP04")) {

			// Recupera a condição DCIM
			IPricingConditionUserExit dcimCondition = Utils.GetItemPricingCondition(pricingItem, "DICM");

			// Verifica se a condição "DCIM" buscou o acesso referente a
			// tabela 527
			if (dcimCondition.getConditionTableName().equals("CUS527")) {

				// Determina o Destino
				String taxJurCodeDestino = pricingItem.getAttributeValue("TAXJURCODE");
				String destino = Utils.GetRegionFromTaxJurCode(taxJurCodeDestino);

				/*
				 * Recupera o Domicílio Fiscal (Se o caracter 3 de TAXJURCODE
				 * for vazio, Domicílio Fiscal será vazio, caso contrário, o
				 * Domicílio Fiscal será o primeiro caracter de 'TAXJURCODE'
				 */
				String domFiscalDestino = taxJurCodeDestino.substring(2, 3).equals(" ") ? " "
						: taxJurCodeDestino.substring(0, 1);

				// Recupera o valor da condição DCIM
				BigDecimal dcimValue = dcimCondition.getConditionValue().getValue();

				/*
				 * Verifica se o valor da condição DCIM é zero e se o destino
				 * é diferente de origem e se o Domicílio Fiscal é igual a Z
				 */
				if (dcimValue.compareTo(BigDecimal.ZERO) == 0
						&& !destino.equals(pricingItem.getAttributeValue("TAXJURCODE_FROM"))
						&& domFiscalDestino.equals("Z")) {

					// Recupera condições
					// • ICVA Montante (KBETR)
					// • ICBS Montante (KBETR)
					// • IBRX Valor (KWERT)
					// • BX23 Valor (KWERT)
					BigDecimal ibrxValue = Utils.GetItemPricingConditionValue(pricingItem, "IBRX");

					BigDecimal icbsValue = Utils.GetItemPricingConditionRate(pricingItem, "ICBS");

					BigDecimal bx23Value = Utils.GetItemPricingConditionValue(pricingItem, "BX23");

					BigDecimal icvaValue = Utils.GetItemPricingConditionRate(pricingItem, "ICVA");

					// Calcular:
					// XKWERT = (IBRX * (ICBS/100) + BX23) * (ICVA/100).
					value = (ibrxValue.multiply(icbsValue.divide(new BigDecimal(100.0))).add(bx23Value)
							.multiply(icvaValue.divide(new BigDecimal(100.0))));

					// Se o valor for maior que zero, multiplica por -1
					if (value.compareTo(PricingTransactiondataConstants.ZERO) > 0)
						value = value.multiply(PricingTransactiondataConstants.MINUS_ONE);
				}
			}

			BigDecimal bx9dValue = Utils.GetItemPricingConditionValue(pricingItem, "BX9D");
			BigDecimal bx9oValue = Utils.GetItemPricingConditionValue(pricingItem, "BX9O");
			BigDecimal bx9pValue = Utils.GetItemPricingConditionValue(pricingItem, "BX9P");

			value = value.add(bx9dValue.add(bx9oValue.add(bx9pValue)));
			
			float dicmMatRf;
			try{
			dicmMatRf = Float.parseFloat(pricingItem.getAttributeValue("ZZ_DICM_MAT_RF").trim());
			}catch (NumberFormatException e) {
				dicmMatRf = 0;
			}

			if (dicmMatRf == 100) {
				float dicmValue = Utils.GetItemPricingConditionRate(pricingItem, "DICM").floatValue();
				if (dicmValue == 0) {
					float v_icva = Utils.GetItemPricingConditionRate(pricingItem, "ICVA").floatValue();
					v_icva /= 100;
					float v_ibrx = Utils.GetItemPricingConditionValue(pricingItem, "IBRX").floatValue();
					float v_ipva = Utils.GetItemPricingConditionRate(pricingItem, "IPVA").floatValue();
					float v_dipi = Utils.GetItemPricingConditionRate(pricingItem, "DIPI").floatValue();
					float v_icbs = Utils.GetItemPricingConditionRate(pricingItem, "ICBS").floatValue();
					
					if(v_icbs != 0) {
						v_icva = v_icva * v_icbs / 100;
					}

					String dis_channel = pricingItem.getAttributeValue("DIS_CHANNEL").trim();
					float v_icva_v = v_icva;
					if (dis_channel.equals("40")) {
						if (v_dipi == 100) {
							if (v_ipva != 0) {
								v_icva_v += (v_icva_v * v_ipva / 100);
							}
						}
					}
					value = new BigDecimal(v_ibrx * v_icva_v * -1);
				}
			}
		}

		/*
		 * Fim - Alterações Diêgo Silva - 03.11.2014 - ICMS-ST Alíquota
		 * estado origem (C058293)
		 */

		// Retornar o valor da condição
		return value;
	}

}