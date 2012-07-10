package fr.perso.ppo.jms.consumer;


import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Component("jmsConsumerTest2")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "jms-consumer-context-test2.xml" })
public class JmsConsumerTest2 implements  InitializingBean { 

	protected static final String MESSAGE_COUNT = "messageCount";
	private static final Logger logger = LoggerFactory.getLogger(JmsConsumer.class);

    @Autowired
    private AtomicInteger counter = null;

    @Autowired
    private JmsConsumer jmsConsumer;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("////////////////    INIT JmsConsumerTest//////////////////////////////");
		Assert.assertNotNull(jmsConsumer);
		
	}
	
	@Test
	public void should_conusme_messages() throws Exception {
		while(true) {
			
		}
	}

}
