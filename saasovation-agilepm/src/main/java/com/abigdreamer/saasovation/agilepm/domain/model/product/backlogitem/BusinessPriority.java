package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;

/**
 *  待定项的业务优先级，用于衡量待定项的业务价值
 * 
 * @author Darkness
 * @date 2014-5-8 下午8:27:15 
 * @version V1.0
 */
public class BusinessPriority extends ValueObject {

    private BusinessPriorityRatings ratings;

    public BusinessPriority(BusinessPriorityRatings aBusinessPriorityRatings) {
        this();

        this.setRatings(aBusinessPriorityRatings);
    }

    public BusinessPriority(BusinessPriority aBusinessPriority) {
        this(new BusinessPriorityRatings(aBusinessPriority.ratings()));
    }

    public float costPercentage(BusinessPriorityTotals aTotals) {
        return (float) 100 * this.ratings().cost() / aTotals.totalCost();
    }

    public float priority(BusinessPriorityTotals aTotals) {
        float costAndRisk = this.costPercentage(aTotals) + this.riskPercentage(aTotals);

        return this.valuePercentage(aTotals) / costAndRisk;
    }

    public float riskPercentage(BusinessPriorityTotals aTotals) {
        return (float) 100 * this.ratings().risk() / aTotals.totalRisk();
    }

    public float totalValue() {
        return this.ratings().benefit() + this.ratings().penalty();
    }

    public float valuePercentage(BusinessPriorityTotals aTotals) {
        return (float) 100 * this.totalValue() / aTotals.totalValue();
    }

    public BusinessPriorityRatings ratings() {
        return this.ratings;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            BusinessPriority typedObject = (BusinessPriority) anObject;
            equalObjects = this.ratings().equals(typedObject.ratings());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (15681 * 13)
            + this.ratings().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "BusinessPriority [ratings=" + ratings + "]";
    }

    private BusinessPriority() {
        super();
    }

    private void setRatings(BusinessPriorityRatings aRatings) {
        this.assertArgumentNotNull(aRatings, "The ratings must be provided.");

        this.ratings = aRatings;
    }
}
