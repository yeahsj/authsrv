package net.suntec.common;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import org.junit.Before;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TestQpid extends BaseTest {
	JmsTemplate qpidJmsTemplate;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		qpidJmsTemplate = (JmsTemplate) ctx.getBean("qpidJmsTemplate");
	}

	public void testSend() {
		qpidJmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(javax.jms.Session session)
					throws JMSException {
				MapMessage message = session.createMapMessage();
				message.setString("body", "hello world");
				message.setString("msg_id", "NT03");
				message.setString("marker", "accountsync");
				message.setString("device_info", "1");
				return message;
			}
		});
		logger.info("send success ");
	}
}
