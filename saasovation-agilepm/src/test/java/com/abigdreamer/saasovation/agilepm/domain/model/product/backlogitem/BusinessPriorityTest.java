package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.text.NumberFormat;


import com.abigdreamer.saasovation.agilepm.domain.model.DomainTest;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BusinessPriority;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BusinessPriorityRatings;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BusinessPriorityTotals;


/**
 * 业务价值测试
 * 
 * @author Darkness
 * @date 2014-5-29 下午9:06:47
 * @version V1.0
 */
public class BusinessPriorityTest extends DomainTest {

	public BusinessPriorityTest() {
		super();
	}

	public void testCostPercentageCalculation() throws Exception {

		BusinessPriority businessPriority = new BusinessPriority(new BusinessPriorityRatings(2, 4, 1, 1));

		BusinessPriority businessPriorityCopy = new BusinessPriority(businessPriority);

		assertEquals(businessPriority, businessPriorityCopy);

		BusinessPriorityTotals totals = new BusinessPriorityTotals(53, 49, 53 + 49, 37, 33);

		float cost = businessPriority.costPercentage(totals);

		assertEquals("2.7", this.oneDecimal().format(cost));

		assertEquals(businessPriorityCopy, businessPriority);
	}

	public void testPriorityCalculation() throws Exception {

		BusinessPriority businessPriority = new BusinessPriority(new BusinessPriorityRatings(2, 4, 1, 1));

		BusinessPriority businessPriorityCopy = new BusinessPriority(businessPriority);

		assertEquals(businessPriority, businessPriorityCopy);

		BusinessPriorityTotals totals = new BusinessPriorityTotals(53, 49, 53 + 49, 37, 33);

		float calculatedPriority = businessPriority.priority(totals);

		assertEquals("1.03", this.twoDecimals().format(calculatedPriority));

		assertEquals(businessPriorityCopy, businessPriority);
	}

	public void testTotalValueCalculation() throws Exception {

		BusinessPriority businessPriority = new BusinessPriority(new BusinessPriorityRatings(2, 4, 1, 1));

		BusinessPriority businessPriorityCopy = new BusinessPriority(businessPriority);

		assertEquals(businessPriority, businessPriorityCopy);

		float totalValue = businessPriority.totalValue();

		assertEquals("6.0", this.oneDecimal().format(totalValue));

		assertEquals(businessPriorityCopy, businessPriority);
	}

	public void testValuePercentageCalculation() throws Exception {

		BusinessPriority businessPriority = new BusinessPriority(new BusinessPriorityRatings(2, 4, 1, 1));

		BusinessPriority businessPriorityCopy = new BusinessPriority(businessPriority);

		assertEquals(businessPriority, businessPriorityCopy);

		BusinessPriorityTotals totals = new BusinessPriorityTotals(53, 49, 53 + 49, 37, 33);

		float valuePercentage = businessPriority.valuePercentage(totals);

		assertEquals("5.9", this.oneDecimal().format(valuePercentage));

		assertEquals(businessPriorityCopy, businessPriority);
	}

	private NumberFormat oneDecimal() {
		return this.decimal(1);
	}

	private NumberFormat twoDecimals() {
		return this.decimal(2);
	}

	private NumberFormat decimal(int aNumberOfDecimals) {
		NumberFormat fmt = NumberFormat.getInstance();
		fmt.setMinimumFractionDigits(aNumberOfDecimals);
		fmt.setMaximumFractionDigits(aNumberOfDecimals);
		return fmt;
	}
}
