package ma.projet.test;

import ma.projet.config.SpringConfig;
import ma.projet.service.HommeService;
import ma.projet.service.FemmeService;
import ma.projet.service.MariageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SimpleTest {
    public static void main(String[] args) {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
            
            System.out.println("Context loaded successfully!");
            
            // Try to get the services
            System.out.println("Getting FemmeService...");
            FemmeService femmeService = context.getBean(FemmeService.class);
            System.out.println("FemmeService obtained: " + femmeService.getClass().getName());
            
            System.out.println("Getting MariageService...");
            MariageService mariageService = context.getBean(MariageService.class);
            System.out.println("MariageService obtained: " + mariageService.getClass().getName());
            
            System.out.println("Getting HommeService...");
            HommeService hommeService = context.getBean(HommeService.class);
            System.out.println("HommeService obtained: " + hommeService.getClass().getName());
            
            System.out.println("All services obtained successfully!");
            
            ((AnnotationConfigApplicationContext) context).close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}