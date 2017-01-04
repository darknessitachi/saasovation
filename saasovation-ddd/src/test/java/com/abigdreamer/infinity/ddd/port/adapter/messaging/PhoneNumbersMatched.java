package com.abigdreamer.infinity.ddd.port.adapter.messaging;

/**
 * 号码匹配完毕
 *  
 * @author Darkness
 * @date 2014-12-17 下午9:42:11
 * @version V1.0
 * @since ark 1.0
 */
public class PhoneNumbersMatched extends PhoneNumberProcessEvent {

    private String matchedPhoneNumbers;// 匹配的号码

    public PhoneNumbersMatched(String aProcessId, String aMatchedPhoneNumbers) {
        super(aProcessId);

        this.matchedPhoneNumbers = aMatchedPhoneNumbers;
    }

    public String matchedPhoneNumbers() {
        return this.matchedPhoneNumbers;
    }
}
