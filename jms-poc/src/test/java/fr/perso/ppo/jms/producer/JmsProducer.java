package fr.perso.ppo.jms.producer;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "jms-context-test.xml" })
public class JmsProducer implements InitializingBean{

	private static final Logger logger = LoggerFactory
			.getLogger(JmsProducer.class);

	protected static final String MESSAGE_COUNT = "messageCount";

	@Autowired
	private JmsTemplate template = null;
	private int messageCount = 1000000;
	@Autowired
	private Destination destination;
	@Autowired
	private Destination destination2;

	/**
	 * Generates JMS messages
	 */
	// @PostConstruct
	@Test
	public void generateMessages() throws JMSException {
		
		for (int i = 0; i < messageCount; i++) {
			final int index = i;
			final String text = "Message number is " + i + ".";

			// logger.info("before Sending message: " + text);
			try {
				 try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						logger.error("error in producer", e1);
					}
				 
				template.send(destination,new MessageCreator() {
					public Message createMessage(Session session)
							throws JMSException {
						TextMessage message = session.createTextMessage(text);
						message.setIntProperty(MESSAGE_COUNT, index);
						
						//message.setJMSTimestamp(10000);
						//message.setJMSExpiration(5000);
						//message.setBooleanProperty("droppable", true);
						//message.setJMSRedelivered(false);

						logger.info("Sending message: " + text);

						return message;
					}
				});
				if(i<1000)
				template.send(destination2,new MessageCreator() {
					public Message createMessage(Session session)
							throws JMSException {
						TextMessage message = session.createTextMessage(text);
						message.setIntProperty(MESSAGE_COUNT, index);
						
						//message.setJMSTimestamp(10000);
						//message.setJMSExpiration(5000);
						//message.setBooleanProperty("droppable", true);
						//message.setJMSRedelivered(false);

						logger.info("Sending message: " + text);

						return message;
					}
				});
				
			} catch (Exception e) {
				/*
				 * org.springframework.jms.UncategorizedJmsException: Uncategorized exception occured during JMS processing; nested exception is javax.jms.JMSException: org.apache.activemq.transport.RequestTimedOutIOException
				 */
				logger.error("================================================================================");
				logger.error("Error in sending message", e);
				logger.error("================================================================================");
				System.exit(1);
			}

		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		/*logger.warn("---1----template time to live {}", this.template.getTimeToLive());
		//template.setTimeToLive(20000);
		logger.warn("---2----template time to live {}", this.template.getTimeToLive());
		
		logger.warn("---3----template delivery mode {}", this.template.getDeliveryMode());
		template.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		logger.warn("---4----template delivery mode {}", this.template.getDeliveryMode());
		*/
			
	}
}