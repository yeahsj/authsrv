package net.suntec.framework.util;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import net.suntec.framework.exception.ASQpidException;
import net.suntec.oauthsrv.dto.NcmsDTO;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年11月25日 上午11:28:24
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public final class NcmsUtil {
	JmsTemplate qpidJmsTemplate = null;

	public void setQpidJmsTemplate(JmsTemplate qpidJmsTemplate) {
		this.qpidJmsTemplate = qpidJmsTemplate;
	}

	public void send(final String deviceInfo) {
		qpidJmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(javax.jms.Session session) {
				MapMessage message;
				try {
					message = session.createMapMessage();
					message.setString("body", "accountsync");
					message.setString("msg_id", "NT03");
					message.setString("marker", "accountsync");
					message.setString("device_info", deviceInfo);
					return message;
				} catch (JMSException e) {
					throw new ASQpidException(e);
				}
			}
		});
	}

	public void send(final NcmsDTO ncmsDTO) {
		qpidJmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(javax.jms.Session session) {
				MapMessage message;
				try {
					message = session.createMapMessage();
					message.setString("body", ncmsDTO.getBody());
					message.setString("msg_id", "NT03");
					message.setString("marker", "accountsync");
					message.setString("device_info", ncmsDTO.getDeviceInfo());
					return message;
				} catch (JMSException e) {
					throw new ASQpidException(e);
				}
			}
		});
	}
}
