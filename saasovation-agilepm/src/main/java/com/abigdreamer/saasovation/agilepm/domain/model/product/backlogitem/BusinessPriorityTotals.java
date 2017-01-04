package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;

/**
 *  待定项成本和风险的总体描述
 * 
 * @author Darkness
 * @date 2014-5-10 下午9:16:51 
 * @version V1.0
 */
public class BusinessPriorityTotals extends ValueObject {

    private int totalBenefit;
    private int totalCost;
    private int totalPenalty;
    private int totalRisk;
    private int totalValue;

    public BusinessPriorityTotals(
            int aTotalBenefit,
            int aTotalPenalty,
            int aTotalValue,
            int aTotalCost,
            int aTotalRisk) {

        this();

        this.setTotalBenefit(aTotalBenefit);
        this.setTotalCost(aTotalCost);
        this.setTotalPenalty(aTotalPenalty);
        this.setTotalRisk(aTotalRisk);
        this.setTotalValue(aTotalValue);
    }

    public BusinessPriorityTotals(BusinessPriorityTotals aBusinessPriorityTotals) {
        this(aBusinessPriorityTotals.totalBenefit(),
             aBusinessPriorityTotals.totalPenalty(),
             aBusinessPriorityTotals.totalValue(),
             aBusinessPriorityTotals.totalCost(),
             aBusinessPriorityTotals.totalRisk());
    }

    public int totalBenefit() {
        return this.totalBenefit;
    }

    public int totalCost() {
        return this.totalCost;
    }

    public int totalPenalty() {
        return this.totalPenalty;
    }

    public int totalRisk() {
        return this.totalRisk;
    }

    public int totalValue() {
        return this.totalValue;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            BusinessPriorityTotals typedObject = (BusinessPriorityTotals) anObject;
            equalObjects =
                this.totalBenefit() == typedObject.totalBenefit() &&
                this.totalCost() == typedObject.totalCost() &&
                this.totalPenalty() == typedObject.totalPenalty() &&
                this.totalRisk() == typedObject.totalRisk() &&
                this.totalValue() == typedObject.totalValue();
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (47091 * 19)
            + this.totalBenefit()
            + this.totalCost()
            + this.totalPenalty()
            + this.totalRisk()
            + this.totalValue();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "BusinessPriorityTotals [totalBenefit=" + totalBenefit + ", totalCost=" + totalCost + ", totalPenalty="
                + totalPenalty + ", totalRisk=" + totalRisk + ", totalValue=" + totalValue + "]";
    }

    private BusinessPriorityTotals() {
        super();
    }

    private void setTotalBenefit(int aTotalBenefit) {
        this.totalBenefit = aTotalBenefit;
    }

    private void setTotalCost(int aTotalCost) {
        this.totalCost = aTotalCost;
    }

    private void setTotalPenalty(int aTotalPenalty) {
        this.totalPenalty = aTotalPenalty;
    }

    private void setTotalRisk(int aTotalRisk) {
        this.totalRisk = aTotalRisk;
    }

    private void setTotalValue(int aTotalValue) {
        this.totalValue = aTotalValue;
    }
}
