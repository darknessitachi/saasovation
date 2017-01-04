package com.abigdreamer.infinity.ddd.port.adapter.messaging;

/**
 * 所有电话号码加载完毕
 *  
 * @author Darkness
 * @date 2014-12-17 下午9:39:05
 * @version V1.0
 * @since ark 1.0
 */
public class AllPhoneNumbersListed extends PhoneNumberProcessEvent {

    private String allPhoneNumbers;

    public AllPhoneNumbersListed(String aProcessId, String aPhoneNumbersArray) {
        super(aProcessId);

        this.allPhoneNumbers = aPhoneNumbersArray;
    }

    public String allPhoneNumbers() {
        return this.allPhoneNumbers;
    }
}
