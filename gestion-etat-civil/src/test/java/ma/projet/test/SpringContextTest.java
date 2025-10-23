package ma.projet.test;

import ma.projet.config.SpringConfig;
import ma.projet.service.HommeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class SpringContextTest {

    @Autowired
    private HommeService hommeService;

    @Test
    public void whenContextLoads_thenServiceIsNotNull() {
        assertNotNull(hommeService);
    }
}