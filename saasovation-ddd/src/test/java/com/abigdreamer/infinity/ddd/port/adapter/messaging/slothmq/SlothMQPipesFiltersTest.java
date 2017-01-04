package com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import junit.framework.TestCase;

import com.abigdreamer.infinity.ddd.notification.Notification;
import com.abigdreamer.infinity.ddd.notification.NotificationReader;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.AllPhoneNumbersCounted;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.AllPhoneNumbersListed;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.ExchangeListener;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.MatchedPhoneNumbersCounted;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.MessageService;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.PhoneNumbersMatched;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.MessageService.MessageServerType;


/**
 * 消息管道过滤测试
 *  
 * @author Darkness
 * @date 2014-12-17 下午9:34:00
 * @version V1.0
 * @since ark 1.0
 */
public class SlothMQPipesFiltersTest extends TestCase {

    private ExchangeListener matchtedPhoneNumberCounter;
    private PhoneNumberExecutive phoneNumberExecutive;
    private ExchangeListener phoneNumberFinder;
    private ExchangeListener totalPhoneNumbersCounter;

    private static String[] phoneNumbers = new String[] {
        "303-555-1212   John",
        "212-555-1212   Joe",
        "718-555-1212   Zoe",
        "720-555-1212   Manny",
        "312-555-1212   Jerry",
        "303-555-9999   Sally"
    };

    public SlothMQPipesFiltersTest() {
        super();
    }

    public void testPhoneNumbersCounter() throws Exception {
        String processId = this.phoneNumberExecutive.start(phoneNumbers);

        Thread.sleep(1000L);

        PhoneNumberProcess process = this.phoneNumberExecutive.processOfId(processId);

        assertNotNull(process);
        assertEquals(2, process.matchedPhoneNumbers());
        assertEquals(6, process.totalPhoneNumbers());
    }

    @Override
    protected void setUp() throws Exception {
    	
    	MessageService.start(MessageServerType.Sloth);
    	
        phoneNumberExecutive = new PhoneNumberExecutive();
        phoneNumberFinder = new PhoneNumberFinder();
        matchtedPhoneNumberCounter = new MatchtedPhoneNumberCounter();
        totalPhoneNumbersCounter = new TotalPhoneNumbersCounter();

        MessageService.registerExchangeListener(this.phoneNumberExecutive);
        MessageService.registerExchangeListener(phoneNumberFinder);
        MessageService.registerExchangeListener(matchtedPhoneNumberCounter);
        MessageService.registerExchangeListener(totalPhoneNumbersCounter);

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {

    	MessageService.close();

        super.tearDown();
    }

    /**
     * 发布通知
     *  
     * @author Darkness
     * @date 2014-12-17 下午10:20:11
     * @version V1.0
     * @since ark 1.0
     */
    private void send(Notification aNotification) {
        MessageService.publish("PhoneNumberExchange", aNotification);
    }

    /**
     * 电话号码流程
     *  
     * @author Darkness
     * @date 2014-12-17 下午9:43:26
     * @version V1.0
     * @since ark 1.0
     */
    private class PhoneNumberProcess {

        private String id;// 流程id
        private int matchedPhoneNumbers;// 匹配的号码数
        private int totalPhoneNumbers;// 总号码数

        public PhoneNumberProcess() {
            super();

            this.id = UUID.randomUUID().toString().toUpperCase();
            this.matchedPhoneNumbers = -1;
            this.totalPhoneNumbers = -1;
        }

        public boolean isCompleted() {
            return this.matchedPhoneNumbers() >= 0 && this.totalPhoneNumbers() >= 0;
        }

        public String id() {
            return this.id;
        }

        public int matchedPhoneNumbers() {
            return this.matchedPhoneNumbers;
        }

        public void setMatchedPhoneNumbers(int aMatchedPhoneNumbersCount) {
            this.matchedPhoneNumbers = aMatchedPhoneNumbersCount;
        }

        public int totalPhoneNumbers() {
            return this.totalPhoneNumbers;
        }

        public void setTotalPhoneNumbers(int aTotalPhoneNumberCount) {
            this.totalPhoneNumbers = aTotalPhoneNumberCount;
        }
    }

    /**
     * 电话号码执行器
     *  
     * @author Darkness
     * @date 2014-12-17 下午9:42:45
     * @version V1.0
     * @since ark 1.0
     */
    private class PhoneNumberExecutive implements ExchangeListener {

        private Map<String, PhoneNumberProcess> processes;

        public PhoneNumberExecutive() {
            super();

            this.processes = new HashMap<String, PhoneNumberProcess>();
        }

        public PhoneNumberProcess processOfId(String aProcessId) {
            return this.processes.get(aProcessId);
        }

        public String start(String[] aPhoneNumbers) {
        		// 创建流程
            PhoneNumberProcess process = new PhoneNumberProcess();

            synchronized (this.processes) {
                this.processes.put(process.id(), process);
            }

            String allPhoneNumbers = "";

            for (String phoneNumber : aPhoneNumbers) {
                if (!allPhoneNumbers.isEmpty()) {
                    allPhoneNumbers = allPhoneNumbers + "\n";
                }

                allPhoneNumbers = allPhoneNumbers + phoneNumber;
            }

            Notification notification =
                    new Notification(
                            1L,
                            new AllPhoneNumbersListed(
                                    process.id(),
                                    allPhoneNumbers));

            send(notification);

            System.out.println("STARTED: " + process.id());

            return process.id();
        }

        @Override
        public String exchangeName() {
            return "PhoneNumberExchange";
        }

        @Override
        public void filteredDispatch(String aType, String aTextMessage) {

            NotificationReader reader = new NotificationReader(aTextMessage);

            String processId = reader.eventStringValue("processId");

            PhoneNumberProcess process = this.processes.get(processId);

            if (reader.typeName().contains("AllPhoneNumbersCounted")) {
                process.setTotalPhoneNumbers(reader.eventIntegerValue("totalPhoneNumbers"));
                System.out.println("AllPhoneNumbersCounted...");
            } else if (reader.typeName().contains("MatchedPhoneNumbersCounted")) {
                process.setMatchedPhoneNumbers(reader.eventIntegerValue("matchedPhoneNumbers"));
                System.out.println("MatchedPhoneNumbersCounted...");
            }

            if (process.isCompleted()) {
                System.out.println(
                        "Process: "
                        + process.id()
                        + ": "
                        + process.matchedPhoneNumbers()
                        + " of "
                        + process.totalPhoneNumbers()
                        + " phone numbers found.");
            }
        }

        @Override
        public String[] listensTo() {
            return new String[] {
                    "org.infinite.framework.common.port.adapter.messaging.AllPhoneNumbersCounted",
                    "org.infinite.framework.common.port.adapter.messaging.MatchedPhoneNumbersCounted",
            };
        }

        @Override
        public String name() {
            return this.getClass().getName();
        }
    }

    /**
     * 查找指定号码处理器
     *  
     * @author Darkness
     * @date 2014-12-17 下午9:59:06
     * @version V1.0
     * @since ark 1.0
     */
    private class PhoneNumberFinder implements ExchangeListener {

        public PhoneNumberFinder() {
            super();
        }

        @Override
        public String exchangeName() {
            return "PhoneNumberExchange";
        }

        @Override
        public void filteredDispatch(String aType, String aTextMessage) {
            System.out.println("AllPhoneNumbersListed (to match)...");

            NotificationReader reader = new NotificationReader(aTextMessage);

            String allPhoneNumbers = reader.eventStringValue("allPhoneNumbers");

            String[] allPhoneNumbersToSearch = allPhoneNumbers.split("\n");

            String foundPhoneNumbers = "";

            for (String phoneNumber : allPhoneNumbersToSearch) {
                if (phoneNumber.contains("303-")) {
                    if (!foundPhoneNumbers.isEmpty()) {
                        foundPhoneNumbers = foundPhoneNumbers + "\n";
                    }
                    foundPhoneNumbers = foundPhoneNumbers + phoneNumber;
                }
            }

            Notification notification =
                    new Notification(
                            1L,
                            new PhoneNumbersMatched(
                                    reader.eventStringValue("processId"),
                                    foundPhoneNumbers));

            send(notification);
        }

        @Override
        public String[] listensTo() {
            return new String[] { "org.infinite.framework.common.port.adapter.messaging.AllPhoneNumbersListed" };
        }

        @Override
        public String name() {
            return this.getClass().getName();
        }
    }

    /**
     * 匹配的号码计数器
     *  
     * @author Darkness
     * @date 2014-12-17 下午10:02:00
     * @version V1.0
     * @since ark 1.0
     */
    private class MatchtedPhoneNumberCounter implements ExchangeListener {

        public MatchtedPhoneNumberCounter() {
            super();
        }

        @Override
        public String exchangeName() {
            return "PhoneNumberExchange";
        }

        @Override
        public void filteredDispatch(String aType, String aTextMessage) {

            System.out.println("PhoneNumbersMatched (to count)...");

            NotificationReader reader = new NotificationReader(aTextMessage);

            String allMatchedPhoneNumbers = reader.eventStringValue("matchedPhoneNumbers");

            String[] allPhoneNumbersToCount = allMatchedPhoneNumbers.split("\n");

            Notification notification =
                    new Notification(
                            1L,
                            new MatchedPhoneNumbersCounted(
                                    reader.eventStringValue("processId"),
                                    allPhoneNumbersToCount.length));

            send(notification);
        }

        @Override
        public String[] listensTo() {
            return new String[] { "org.infinite.framework.common.port.adapter.messaging.PhoneNumbersMatched" };
        }

        @Override
        public String name() {
            return this.getClass().getName();
        }
    }

    /**
     * 所有号码计数器
     *  
     * @author Darkness
     * @date 2014-12-17 下午10:03:53
     * @version V1.0
     * @since ark 1.0
     */
    private class TotalPhoneNumbersCounter implements ExchangeListener {

        public TotalPhoneNumbersCounter() {
            super();
        }

        @Override
        public String exchangeName() {
            return "PhoneNumberExchange";
        }

        @Override
        public void filteredDispatch(String aType, String aTextMessage) {

            System.out.println("AllPhoneNumbersListed (to total)...");

            NotificationReader reader = new NotificationReader(aTextMessage);

            String allPhoneNumbers = reader.eventStringValue("allPhoneNumbers");

            String[] allPhoneNumbersToCount = allPhoneNumbers.split("\n");

            Notification notification =
                    new Notification(
                            1L,
                            new AllPhoneNumbersCounted(
                                    reader.eventStringValue("processId"),
                                    allPhoneNumbersToCount.length));

            send(notification);
        }

        @Override
        public String[] listensTo() {
            return new String[] { "org.infinite.framework.common.port.adapter.messaging.AllPhoneNumbersListed" };
        }

        @Override
        public String name() {
            return this.getClass().getName();
        }
    }
}
