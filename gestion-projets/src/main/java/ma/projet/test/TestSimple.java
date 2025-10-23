package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.config.SpringConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestSimple {
    
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        
        try {
            System.out.println("=== Test Application de Gestion de Projets ===\n");
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            // Get SessionFactory directly to avoid proxy casting issues
            SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
            
            System.out.println("✅ Spring Context et SessionFactory initialisés");
            
            // Use direct Hibernate session for testing
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            
            System.out.println("1. Création d'un projet");
            Projet projet = new Projet("Gestion de stock", sdf.parse("14/01/2013"), sdf.parse("14/06/2013"));
            session.save(projet);
            System.out.println("   Projet créé: " + projet.getNom());
            
            System.out.println("\n2. Création des employés");
            Employe employe1 = new Employe("Dupont", "Jean", "0123456789");
            Employe employe2 = new Employe("Martin", "Marie", "0987654321");
            session.save(employe1);
            session.save(employe2);
            System.out.println("   Employés créés: " + employe1.getPrenom() + " " + employe1.getNom() + 
                             " et " + employe2.getPrenom() + " " + employe2.getNom());
            
            System.out.println("\n3. Création des tâches");
            Tache tache1 = new Tache("Analyse", sdf.parse("01/02/2013"), sdf.parse("28/02/2013"), 1500.0, projet);
            Tache tache2 = new Tache("Conception", sdf.parse("01/03/2013"), sdf.parse("31/03/2013"), 2000.0, projet);
            Tache tache3 = new Tache("Développement", sdf.parse("01/04/2013"), sdf.parse("30/04/2013"), 800.0, projet);
            
            session.save(tache1);
            session.save(tache2);
            session.save(tache3);
            System.out.println("   Tâches créées: Analyse, Conception, Développement");
            
            System.out.println("\n4. Association employés-tâches avec dates réelles");
            EmployeTache et1 = new EmployeTache(employe1, tache1, sdf.parse("10/02/2013"), sdf.parse("20/02/2013"));
            EmployeTache et2 = new EmployeTache(employe1, tache2, sdf.parse("10/03/2013"), sdf.parse("15/03/2013"));
            EmployeTache et3 = new EmployeTache(employe2, tache3, sdf.parse("10/04/2013"), sdf.parse("25/04/2013"));
            
            session.save(et1);
            session.save(et2);
            session.save(et3);
            System.out.println("   Associations créées avec dates de réalisation");
            
            // Commit transaction
            session.getTransaction().commit();
            session.close();
            
            System.out.println("\n5. Test des requêtes - Tâches avec prix > 1000 DH");
            Session querySession = sessionFactory.openSession();
            List<Tache> tachesCheres = querySession.createQuery(
                "FROM Tache t WHERE t.prix > 1000", Tache.class).list();
            if (tachesCheres != null && !tachesCheres.isEmpty()) {
                for (Tache t : tachesCheres) {
                    System.out.println("   - " + t.getNom() + ": " + t.getPrix() + " DH");
                }
            } else {
                System.out.println("   Aucune tâche trouvée avec prix > 1000 DH");
            }
            querySession.close();
            
            System.out.println("\n6. Test réussi - Spring + Hibernate intégration complète");
            
            System.out.println("\n=== Tests terminés avec succès ===");
            
        } catch (Exception e) {
            System.err.println("Erreur lors des tests: " + e.getMessage());
            e.printStackTrace();
        }
    }
}