package webJunit.fast;

import com.jd.jsf.gd.config.spring.ProviderBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by shaohongsen on 2018/6/25.
 */
public class FastContext extends ClassPathXmlApplicationContext {

    public FastContext() throws BeansException {
        super();
    }

    public FastContext(String... configLocations) throws BeansException {
        super(configLocations);
    }


    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        ApplicationContext context = new FastContext("spring-config.xml");
        long time2 = System.currentTimeMillis();
        System.out.println((time2 - time1) / 1000);
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        Iterator<String> beanNamesIterator = beanFactory.getBeanNamesIterator();
        beanFactory.ignoreDependencyInterface(ProviderBean.class);
        while (beanNamesIterator.hasNext()) {
            String beanName = beanNamesIterator.next();
            try {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                //如果是jsf的consumer,需要添加lazy属性
                if (beanDefinition.getBeanClassName().contains("ConsumerBean")) {
                    MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
                    propertyValues.add("lazy", true);
                }
                //普通的bean直接lazy-init
                beanDefinition.setLazyInit(true);
            } catch (NoSuchBeanDefinitionException e) {
                System.out.println(beanName + ": NoSuchBeanDefinitionException");
            }
        }
    }

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
        // Create a new XmlBeanDefinitionReader for the given BeanFactory.
        XmlBeanDefinitionReader beanDefinitionReader = new LocalXmlBeanDefinitionReader(beanFactory);

        // Configure the bean definition reader with this context's
        // resource loading environment.
        beanDefinitionReader.setEnvironment(this.getEnvironment());
        beanDefinitionReader.setResourceLoader(this);
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));

        // Allow a subclass to provide custom initialization of the reader,
        // then proceed with actually loading the bean definitions.
        initBeanDefinitionReader(beanDefinitionReader);
        loadBeanDefinitions(beanDefinitionReader);
    }

    private static class LocalXmlBeanDefinitionReader extends XmlBeanDefinitionReader {
        public LocalXmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
            super(registry);
        }

        @Override
        public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
            String path = ((ClassPathResource) encodedResource.getResource()).getPath();
            if (path.contains("shop-jsf-provider.xml")) {
                return 0;
            }
            return super.loadBeanDefinitions(encodedResource);
        }
    }

}
