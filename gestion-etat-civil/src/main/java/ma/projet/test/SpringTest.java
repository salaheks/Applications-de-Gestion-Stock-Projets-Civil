package ma.projet.test;

import ma.projet.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {
    public static void main(String[] args) {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
            
            System.out.println("Beans in context:");
            String[] beanNames = context.getBeanDefinitionNames();
            for (String beanName : beanNames) {
                System.out.println("- " + beanName + " : " + context.getBean(beanName).getClass().getName());
            }
            
            System.out.println("\nTrying to get services by name:");
            Object hommeService = context.getBean("hommeService");
            System.out.println("hommeService: " + hommeService.getClass().getName());
            
            Object femmeService = context.getBean("femmeService");
            System.out.println("femmeService: " + femmeService.getClass().getName());
            
            Object mariageService = context.getBean("mariageService");
            System.out.println("mariageService: " + mariageService.getClass().getName());
            
            ((AnnotationConfigApplicationContext) context).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}