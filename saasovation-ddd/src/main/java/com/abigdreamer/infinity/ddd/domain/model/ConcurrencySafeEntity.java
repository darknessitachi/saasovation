package com.abigdreamer.infinity.ddd.domain.model;


/**
 * 带并发版本控制的的实体
 * 
 * @author Darkness
 * @date 2014-5-28 下午9:25:51
 * @version V1.0
 */
public class ConcurrencySafeEntity extends Entity {

    private static final long serialVersionUID = 1L;

    // 并发版本号
    private int concurrencyVersion;

    protected ConcurrencySafeEntity() {
        super();
    }

    public int concurrencyVersion() {
        return this.concurrencyVersion;
    }

    public void setConcurrencyVersion(int aVersion) {
        this.failWhenConcurrencyViolation(aVersion);
        this.concurrencyVersion = aVersion;
    }

    public void failWhenConcurrencyViolation(int aVersion) {
        if (aVersion != this.concurrencyVersion()) {
            throw new IllegalStateException(
                    "Concurrency Violation: Stale data detected. Entity was already modified.");
        }
    }
}
