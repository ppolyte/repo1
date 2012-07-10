package fr.perso.ppo.spring;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/fr/perso/ppo/jms/jms-context-test.xml" })
public class SpringConfigurationTest extends AbstractJUnit4SpringContextTests {

        @Test
        public void dumpBeans() {
                String[] beanNames = this.applicationContext.getBeanDefinitionNames();
                Arrays.sort(beanNames);
                System.out
                                .println("********************************************************************");
                for (String beanName : beanNames) {
                        try {
                                Object bean = this.applicationContext.getBean(beanName);

                                System.out.println("----------------------");
                                System.out.println(beanName + " : " + bean + "      ");
                                System.out.println("   Aop proxy :" + AopUtils.isAopProxy(bean));
                                System.out
                                                .println("   Cglig proxy :" + AopUtils.isCglibProxy(bean));
                                System.out.println("  jdk dynamic proxy : "
                                                + AopUtils.isJdkDynamicProxy(bean));
                                try {

                                        if (bean instanceof Advised) {
                                                Advised advised = (Advised) bean;
                                                Object target = advised.getTargetSource().getTarget();
                                                System.out.println(" via Advised bean class " + bean.getClass());
                                                System.out.println(" via Advisedtarget class " + target.getClass());
                                        } else {
                                                System.out.println("  bean class " + bean.getClass());
                                        }
                                } catch (Exception e) {
                                        System.err.println(e);
                                }

                        } catch (BeanIsAbstractException e) {
                                System.out.println("---------getBean(beanName)--EXCEPTION-----------");
                                // tricky pour http-conduit : impossible d'invoquer getBean sur
                                // http-conduit
                                if (e.getMessage().indexOf("http-conduit") != -1) {
                                        System.out.println("**if** Erreur de http-conduit / beanName:"  + beanName);
                                } else {
                                        System.out.println("***else***" + beanName + " : abstract");
                                }
                                System.out.println(e);
                                System.out.println("---------getBean(beanName)--EXCEPTION-----------");

                        }
                }
                System.out
                                .println("********************************************************************");
        }
}
