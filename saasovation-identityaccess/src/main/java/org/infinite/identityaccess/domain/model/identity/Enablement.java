package org.infinite.identityaccess.domain.model.identity;

import java.io.Serializable;
import java.util.Date;

import com.rapidark.framework.commons.lang.AssertionConcern;


/**
 * 可用状态
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:39:15
 * @version V1.0
 */
public final class Enablement extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean enabled;
    private Date startDate;
    private Date endDate;

    /**
     * 生成无期限可用状态
     *  
     * @author Darkness
     * @date 2014-11-26 下午9:53:55
     * @version V1.0
     * @since ark 1.0
     */
    public static Enablement indefiniteEnablement() {
        return new Enablement(true, null, null);
    }

    public Enablement(boolean anEnabled, Date aStartDate, Date anEndDate) {
        super();

        // 如果设置了起始或结束时间，那么两个时间必须都不为空，且起始时间小于结束时间
        if (aStartDate != null || anEndDate != null) {
            this.assertArgumentNotNull(aStartDate, "The start date must be provided.");
            this.assertArgumentNotNull(anEndDate, "The end date must be provided.");
            this.assertArgumentFalse(aStartDate.after(anEndDate), "Enablement start and/or end date is invalid.");
        }

        this.setEnabled(anEnabled);
        this.setEndDate(anEndDate);
        this.setStartDate(aStartDate);
    }

    public Enablement(Enablement anEnablement) {
        this(anEnablement.isEnabled(), anEnablement.startDate(), anEnablement.endDate());
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * 状态本身是否可用，状态处于可用并且未超期返回true
     *  
     * @author Darkness
     * @date 2014-11-26 下午10:00:13
     * @version V1.0
     * @since ark 1.0
     */
    public boolean isEnablementEnabled() {
        boolean enabled = false;

        if (this.isEnabled()) {
            if (!this.isTimeExpired()) {
                enabled = true;
            }
        }

        return enabled;
    }

    public Date endDate() {
        return this.endDate;
    }

    /**
     * 是否超期
     *  
     * @author Darkness
     * @date 2014-11-26 下午9:58:53
     * @version V1.0
     * @since ark 1.0
     */
    public boolean isTimeExpired() {
        boolean timeExpired = false;

        if (this.startDate() != null && this.endDate() != null) {
            Date now = new Date();
            if (now.before(this.startDate()) ||
                now.after(this.endDate())) {
                timeExpired = true;
            }
        }

        return timeExpired;
    }

    public Date startDate() {
        return this.startDate;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Enablement typedObject = (Enablement) anObject;
            equalObjects =
                this.isEnabled() == typedObject.isEnabled() &&
                ((this.startDate() == null && typedObject.startDate() == null) ||
                 (this.startDate() != null && this.startDate().equals(typedObject.startDate()))) &&
                ((this.endDate() == null && typedObject.endDate() == null) ||
                 (this.endDate() != null && this.endDate().equals(typedObject.endDate())));
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (19563 * 181)
            + (this.isEnabled() ? 1:0)
            + (this.startDate() == null ? 0:this.startDate().hashCode())
            + (this.endDate() == null ? 0:this.endDate().hashCode());

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Enablement [enabled=" + enabled + ", endDate=" + endDate + ", startDate=" + startDate + "]";
    }

    protected Enablement() {
        super();
    }

    private void setEnabled(boolean anEnabled) {
        this.enabled = anEnabled;
    }

    private void setEndDate(Date anEndDate) {
        this.endDate = anEndDate;
    }

    private void setStartDate(Date aStartDate) {
        this.startDate = aStartDate;
    }
}
