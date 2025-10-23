package ma.projet;

import ma.projet.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class SpringContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testSpringContextLoads() {
        assertNotNull("Application context should be loaded", applicationContext);
        System.out.println("✅ Spring context loaded successfully!");
        
        // Vérifier que les beans principaux sont présents
        assertTrue("DataSource bean should exist", applicationContext.containsBean("dataSource"));
        assertTrue("SessionFactory bean should exist", applicationContext.containsBean("sessionFactory"));
        assertTrue("TransactionManager bean should exist", applicationContext.containsBean("transactionManager"));
        
        System.out.println("✅ All core Spring + Hibernate beans are configured!");
    }

    @Test
    public void testServiceBeans() {
        assertTrue("FemmeService bean should exist", applicationContext.containsBean("femmeService"));
        assertTrue("HommeService bean should exist", applicationContext.containsBean("hommeService"));
        assertTrue("MariageService bean should exist", applicationContext.containsBean("mariageService"));
        
        System.out.println("✅ All service beans are configured with Spring DI!");
    }
}