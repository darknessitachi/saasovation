package com.abigdreamer.infinity.ddd.port.adapter.messaging;

/**
 * 匹配的号码计数完毕
 *  
 * @author Darkness
 * @date 2014-12-17 下午9:41:08
 * @version V1.0
 * @since ark 1.0
 */
public class MatchedPhoneNumbersCounted extends PhoneNumberProcessEvent {

    private int matchedPhoneNumbers;

    public MatchedPhoneNumbersCounted(String aProcessId, int aMatchedPhoneNumbersCount) {
        super(aProcessId);

        this.matchedPhoneNumbers = aMatchedPhoneNumbersCount;
    }

    public int matchedPhoneNumbers() {
        return this.matchedPhoneNumbers;
    }
}
