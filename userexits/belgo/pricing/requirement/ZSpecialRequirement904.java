/* F�rmula..: 809 
 * Criada em: 25/11/2011
 * Criador..: Cl�udio L. M�rtire
 * 
 * A f�rmula 809 foi criada para retornar os impostos para os casos de venda para Zona Franca.
 * Esta necessidade � devido a negocia��o do pre�o pelos vendedores ser sem os impostos para as vendas ZF.
 */	
package userexits.belgo.pricing.requirement;

import com.sap.spe.condmgnt.customizing.IAccess;
import com.sap.spe.condmgnt.customizing.IStep;
import com.sap.spe.condmgnt.finding.userexit.IConditionFindingManagerUserExit;
import com.sap.spe.condmgnt.finding.userexit.RequirementAdapter;

public class ZSpecialRequirement904 extends RequirementAdapter {
	/**
	 * Purpose: This is an example of a pricing requirement. This requirement is
	 * met if the document's item category is relevant for pricing and no
	 * previous condition in the pricing procedure has set the condition
	 * exclusion flag. This requirement can be assigned to condition types in
	 * the pricing procedure to avoid unnecessary accesses to the database when
	 * an item is not relevant for pricing or a condition exclusion indicator
	 * has been set.
	 * 
	 * Example: A sales order is placed in the system. Some of the items in the
	 * order will be free to the customer and the customer service
	 * representative indicates this with the item category TANN. In the
	 * customizing, item category TANN has been configured as not relevant for
	 * pricing. Within the pricing procedure, the user assigns this requirement
	 * to all condition types. Using this requirement, the system does not
	 * access any pricing condition records for the free line item. In addition
	 * to offering free items, some of the prices for products in the sales
	 * order are defined as net prices. When a net price is found, no subsequent
	 * discounts or surcharges should be assigned to the item. This pricing
	 * requirement also ensures that further condition records are not accessed
	 * when a net price has already been found for the item (condition exclusion
	 * has been set).
	 */
	public boolean checkRequirement(IConditionFindingManagerUserExit item,
			IStep step, IAccess access) {
		String pricingIndicator = item.getAttributeValue("PRICING_INDICATOR");
		return pricingIndicator.equals("X");
	}
}
