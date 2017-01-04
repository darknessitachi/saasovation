package com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.abigdreamer.infinity.ddd.port.adapter.messaging.ExchangeListener;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.MessageService;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.MessageService.MessageServerType;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq.SlothExchangePublisher;

/**
 * 消息测试
 *  
 * @author Darkness
 * @date 2014-12-16 下午11:11:26
 * @version V1.0
 * @since ark 1.0
 */
public class SlothTest extends TestCase {

	private SlothExchangePublisher publisher;
	private TestExchangeListener testExchangeListener;

	public SlothTest() {
		super();
	}

	public void testPublishSubscribe() throws Exception {
		this.publisher.publish("my.test.type", "A tiny little message.");
		this.publisher.publish("my.test.type1", "A slightly bigger message.");
		this.publisher.publish("my.test.type2", "An even bigger message, still.");
		this.publisher.publish("my.test.type3", "An even bigger (bigger!) message, still.");

		Thread.sleep(1000L);

		assertEquals("my.test.type", testExchangeListener.receivedType());
		assertEquals("A tiny little message.", testExchangeListener.receivedMessage());
		assertEquals(4, TestExchangeListenerAgain.uniqueMessages().size());
	}

	@Override
	protected void setUp() throws Exception {
		
		MessageService.start(MessageServerType.Sloth);// 启动消息服务器
		
		this.testExchangeListener = new TestExchangeListener();

		MessageService.registerExchangeListener(this.testExchangeListener);
		MessageService.registerExchangeListener(new TestExchangeListenerAgain());
		MessageService.registerExchangeListener(new TestExchangeListenerAgain());
		MessageService.registerExchangeListener(new TestExchangeListenerAgain());

		this.publisher = new SlothExchangePublisher("TestExchange");

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		MessageService.close();
	}

	private static class TestExchangeListener implements ExchangeListener {

		private String receivedMessage;
		private String receivedType;

		TestExchangeListener() {
			super();
		}

		public String receivedMessage() {
			return this.receivedMessage;
		}

		public String receivedType() {
			return this.receivedType;
		}

		@Override
		public String exchangeName() {
			return "TestExchange";
		}

		@Override
		public void filteredDispatch(String aType, String aTextMessage) {
			this.receivedType = aType;
			this.receivedMessage = aTextMessage;
		}

		@Override
		public String[] listensTo() {
			return new String[] { "my.test.type" };
		}

		@Override
		public String name() {
			return this.getClass().getName();
		}
	}

	private static class TestExchangeListenerAgain implements ExchangeListener {

		private static int idCount = 0;
		private static Set<String> uniqueMessages = new HashSet<String>();

		private int id;

		public static Set<String> uniqueMessages() {
			return uniqueMessages;
		}

		TestExchangeListenerAgain() {
			super();

			this.id = ++idCount;
		}

		@Override
		public String exchangeName() {
			return "TestExchange";
		}

		@Override
		public void filteredDispatch(String aType, String aTextMessage) {
			synchronized (uniqueMessages) {
				uniqueMessages.add(aType + ":" + aTextMessage);
			}
		}

		@Override
		public String[] listensTo() {
			return null;	// all
		}

		@Override
		public String name() {
			return this.getClass().getName() + "#" + this.id;
		}
	}
}
