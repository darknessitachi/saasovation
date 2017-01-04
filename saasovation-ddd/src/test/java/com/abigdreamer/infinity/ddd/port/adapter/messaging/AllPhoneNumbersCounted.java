package com.abigdreamer.infinity.ddd.port.adapter.messaging;

/**
 * 所有电话号码计数完毕
 *  
 * @author Darkness
 * @date 2014-12-17 下午9:38:13
 * @version V1.0
 * @since ark 1.0
 */
public class AllPhoneNumbersCounted extends PhoneNumberProcessEvent {

    private int totalPhoneNumbers;

    public AllPhoneNumbersCounted(String aProcessId, int aTotalPhoneNumbersCount) {
        super(aProcessId);

        this.totalPhoneNumbers = aTotalPhoneNumbersCount;
    }

    public int totalPhoneNumbers() {
        return this.totalPhoneNumbers;
    }
}
