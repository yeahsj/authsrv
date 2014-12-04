package net.suntec.common;

import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.qpid.client.message.AMQPEncodedMapMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @项目名称: 企业应用开发框架
 * @功能描述: 接收Qpid消息
 * @当前版本： 1.0
 * @创建时间: 2014-11-14 下午12:13:45
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class MyMessageListener implements MessageListener {

	Logger logger = LoggerFactory.getLogger(MyMessageListener.class);

	@Override
	public void onMessage(Message msg) {
		if (msg instanceof AMQPEncodedMapMessage) {
			AMQPEncodedMapMessage m = (AMQPEncodedMapMessage) msg;
			Map map = m.getMap();
			Set<String> keys = map.keySet();
			for (String key : keys) {
				logger.info("key is " + key);
				logger.info("value is " + map.get(key));
			}
		} else {
			try {
				logger.info("type is " + msg.getJMSType());
				logger.info("message id is " + msg.getJMSMessageID());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
