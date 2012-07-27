package fr.perso.ppo.jms.consumer;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("jmsConsumer")
public class JmsConsumer implements MessageListener, InitializingBean { 

	protected static final String MESSAGE_COUNT = "messageCount";
	private static final Logger logger = LoggerFactory.getLogger(JmsConsumer.class);

    @Autowired
    private AtomicInteger counter = null;

    /**
     * Implementation of <code>MessageListener</code>.
     */
    
    public void onMessage(Message message) {
        
    	 logger.info("==================== INSIDE CONSUMER ==============START=============================================== ");
    	 try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			logger.error("error in consumer", e1);
		}
    	
    	try {   
            int messageCount = -1;
            if(message.propertyExists(MESSAGE_COUNT))
            	messageCount = message.getIntProperty(MESSAGE_COUNT);
            
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage)message;
                String msg = tm.getText();
                
                
                logger.info("Processed message '{}'.  value={}", msg, messageCount);
                
                counter.incrementAndGet();
            }
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        }
    	logger.info("==================== INSIDE CONSUMER ==============END=============================================== ");
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("////////////////    INIT //////////////////////////////");
		
	}
	

}
