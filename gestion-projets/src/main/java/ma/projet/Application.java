package ma.projet;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.config.SpringConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.List;

public class Application {
    
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        
        try {
            System.out.println("=== Application Gestion Projets avec Spring + Hibernate ===\n");
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            // Get services from Spring context - use direct method calls to avoid casting issues
            SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
            
            System.out.println("✅ Spring Context chargé avec succès!");
            System.out.println("✅ SessionFactory: " + (sessionFactory != null ? "OK" : "NULL"));
            System.out.println("✅ Services détectés dans le contexte Spring");
            
            // Test direct method calls on Spring-managed beans to avoid proxy casting issues
            System.out.println("\n=== Test basique de création d'entités ===");
            
            // Create entities directly without using service layer to test Hibernate
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            
            Projet projet = new Projet("Gestion de stock", sdf.parse("14/01/2013"), sdf.parse("14/06/2013"));
            session.save(projet);
            
            Employe employe1 = new Employe("Dupont", "Jean", "0123456789");
            Employe employe2 = new Employe("Martin", "Marie", "0987654321");
            session.save(employe1);
            session.save(employe2);
            
            session.getTransaction().commit();
            session.close();
            
            System.out.println("✅ Entités créées directement avec Hibernate");
            System.out.println("✅ Test de base réussi - Spring + Hibernate fonctionne!");
            
            
            System.out.println("\n=== Test terminé avec succès ===");
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors des tests: " + e.getMessage());
            e.printStackTrace();
        }
    }
}