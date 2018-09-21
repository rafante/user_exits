package userexits.belgo.pricing.value;

import java.math.BigDecimal;

import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

import userexits.belgo.pricing.utils.Utils;

public class ZValueCondition835 extends ValueFormulaAdapter {

	@Override
	public BigDecimal overwriteConditionValue(IPricingItemUserExit pricingItem,
			IPricingConditionUserExit pricingCondition) {
		// TODO Auto-generated method stub

		float dicm = Utils.GetItemPricingConditionRate(pricingItem, "DICM").floatValue();

		if (dicm == 0) {
			String zz_mat_ty = pricingItem.getAttributeValue("ZZ_MAT_TY").trim();
			String dis_channel = pricingItem.getAttributeValue("DIS_CHANNEL").trim();
			String priceProduct = pricingItem.getAttributeValue("PRICE_PRODUCT").trim();
			String product = pricingItem.getAttributeValue("PRODUCT").trim();

			float dicmMatRf;
			try {
				dicmMatRf = Float.parseFloat(pricingItem.getAttributeValue("ZZ_DICM_MAT_RF").trim());
			} catch (NumberFormatException e) {
				dicmMatRf = -1;
			}

			if (zz_mat_ty.equals("ZPRC") && priceProduct != null && !priceProduct.equals(product) && dicmMatRf != 0) {
				// float v_dicm = Utils.GetItemPricingConditionValue(pricingItem,
				// "DICM").floatValue();
				float v_icva = Utils.GetItemPricingConditionRate(pricingItem, "ICVA").floatValue();
				float v_ipva = Utils.GetItemPricingConditionRate(pricingItem, "IPVA").floatValue();
				float v_dcof = Utils.GetItemPricingConditionRate(pricingItem, "DCOF").floatValue();
				float v_bco1 = Utils.GetItemPricingConditionRate(pricingItem, "BCO1").floatValue();
				float v_dpis = Utils.GetItemPricingConditionRate(pricingItem, "DPIS").floatValue();
				float v_dipi = Utils.GetItemPricingConditionRate(pricingItem, "DIPI").floatValue();
				float v_bpi1 = Utils.GetItemPricingConditionRate(pricingItem, "BPI1").floatValue();
				float v_znfy = Utils.GetItemPricingConditionValue(pricingItem, "ZNFY").floatValue();
				float v_icbs = Utils.GetItemPricingConditionRate(pricingItem, "ICBS").floatValue();

				if (v_icbs != 0) {
					v_icva = v_icva * ( v_icbs / 100 );
				}

				if (v_dcof == 0) {
					v_bco1 = 0;
				}

				if (v_dpis == 0) {
					v_bpi1 = 0;
				}

				if (dis_channel.equals("40") && v_dipi == 100 && v_ipva != 0) {
					v_icva += (v_icva * v_ipva / 100);
				}

				BigDecimal value = pricingCondition.getConditionValue().getValue();

				if (pricingCondition.getConditionTypeName() != null
						&& pricingCondition.getConditionTypeName().equals("ZBCC")) {
					float v_zst = Utils.GetItemPricingConditionValue(pricingItem, "ZST").floatValue();
					value = value.subtract(new BigDecimal(v_zst));
				} else {
					float xkwert = v_znfy / (100 - (v_icva + v_bco1 + v_bpi1));
					xkwert *= 100;
					value = new BigDecimal(xkwert);
				}

				return value;
			}
		}

		return super.overwriteConditionValue(pricingItem, pricingCondition);
	}
}
