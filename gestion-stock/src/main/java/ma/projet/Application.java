package ma.projet;

import ma.projet.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        
        System.out.println("Application Gestion Stock avec Spring + Hibernate démarrée avec succès!");
        System.out.println("Beans disponibles:");
        
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            if (beanName.contains("Service") || beanName.contains("sessionFactory") || beanName.contains("dataSource")) {
                System.out.println("- " + beanName);
            }
        }
        
        System.out.println("\n✅ Spring Context chargé avec succès!");
        System.out.println("✅ Hibernate SessionFactory configuré!");
        System.out.println("✅ Services injectés avec Spring DI!");
        System.out.println("✅ HikariCP pool de connexions configuré!");
        System.out.println("✅ Gestion des transactions Spring automatique!");
    }
}