package ma.projet;

import ma.projet.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        
        System.out.println("Application Spring avec Hibernate démarrée avec succès!");
        System.out.println("Beans disponibles:");
        
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            if (beanName.contains("Service") || beanName.contains("sessionFactory") || beanName.contains("dataSource")) {
                System.out.println("- " + beanName);
            }
        }
    }
}