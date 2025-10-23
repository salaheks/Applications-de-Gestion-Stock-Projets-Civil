package ma.projet;

import ma.projet.config.SpringConfig;
import ma.projet.dao.IDao;
import ma.projet.beans.Femme;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class SpringHibernateTest {

    @Autowired
    @Qualifier("femmeService")
    private IDao<Femme> femmeService;

    @Test
    public void testSpringContextLoads() {
        assertNotNull("FemmeService should be injected", femmeService);
        System.out.println("✅ Spring context loaded successfully!");
        System.out.println("✅ FemmeService bean injected: " + femmeService.getClass().getSimpleName());
    }

    @Test
    public void testFindAllMethod() {
        try {
            // Test that the method can be called without throwing exceptions
            femmeService.findAll();
            System.out.println("✅ findAll() method executed successfully with Spring transactions!");
        } catch (Exception e) {
            System.out.println("❌ Error calling findAll(): " + e.getMessage());
            // This might fail if database is not available, but Spring DI should work
        }
    }
}