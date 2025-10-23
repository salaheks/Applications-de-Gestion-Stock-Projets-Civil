package ma.projet;

import ma.projet.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DebugBeans {
    
    public static void main(String[] args) {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
            
            System.out.println("=== All beans in Spring context ===");
            String[] beanNames = context.getBeanDefinitionNames();
            for (String beanName : beanNames) {
                System.out.println("Bean: " + beanName + " - Type: " + context.getBean(beanName).getClass().getName());
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}