package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.config.SpringConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestGestionProjets {
    
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            // Get SessionFactory directly to avoid proxy casting issues
            SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
            
            System.out.println(" Spring Context et SessionFactory initialisés");
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("             Test de création des données");
            System.out.println("=".repeat(50));
            
            // Create main transaction session
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            
            Employe employe1 = new Employe("Dupont", "Jean", "0123456789");
            Employe employe2 = new Employe("Martin", "Marie", "0987654321");
            session.save(employe1);
            session.save(employe2);
            System.out.println("Employés créés avec succès");
            
            Projet projet1 = new Projet("Gestion de stock", sdf.parse("14/01/2013"), sdf.parse("14/06/2013"));
            session.save(projet1);
            System.out.println("Projet créé avec succès");
            
            Tache tache1 = new Tache("Analyse", sdf.parse("01/02/2013"), sdf.parse("28/02/2013"), 1500.0, projet1);
            Tache tache2 = new Tache("Conception", sdf.parse("01/03/2013"), sdf.parse("31/03/2013"), 2000.0, projet1);
            Tache tache3 = new Tache("Développement", sdf.parse("01/04/2013"), sdf.parse("30/04/2013"), 800.0, projet1);
            
            session.save(tache1);
            session.save(tache2);
            session.save(tache3);
            System.out.println("Tâches créées avec succès");
            
            EmployeTache et1 = new EmployeTache(employe1, tache1, sdf.parse("10/02/2013"), sdf.parse("20/02/2013"));
            EmployeTache et2 = new EmployeTache(employe1, tache2, sdf.parse("10/03/2013"), sdf.parse("15/03/2013"));
            EmployeTache et3 = new EmployeTache(employe2, tache3, sdf.parse("10/04/2013"), sdf.parse("25/04/2013"));
            
            session.save(et1);
            session.save(et2);
            session.save(et3);
            System.out.println("Associations employé-tâche créées avec succès");
            
            // Commit the transaction
            session.getTransaction().commit();
            session.close();
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("        Test d'affichage des tâches d'un projet");
            System.out.println("=".repeat(50));
            Session querySession = sessionFactory.openSession();
            
            // Format date with day and month name
            SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd MMMM yyyy", java.util.Locale.FRANCE);
            StringBuilder output = new StringBuilder();
            output.append("\n Informations du projet:\n");
            output.append("-".repeat(50)).append("\n");
            output.append(String.format(" ID     : %d%n", projet1.getId()));
            output.append(String.format(" Nom    : %s%n", projet1.getNom()));
            output.append(String.format(" Début  : %s%n", fullDateFormat.format(projet1.getDateDebut())));
            output.append("\n Liste des tâches:\n");
            output.append("-".repeat(50)).append("\n");
            output.append(String.format("%-4s %-15s %-19s %s%n", "ID", "Nom", "Date Début", "Date Fin"));
            output.append("-".repeat(50)).append("\n");
            System.out.print(output.toString());
            
            List<EmployeTache> tachesRealisees = querySession.createQuery(
                "FROM EmployeTache et WHERE et.tache.projet.id = :projetId AND et.dateFinReelle IS NOT NULL", 
                EmployeTache.class)
                .setParameter("projetId", projet1.getId())
                .list();
            
            StringBuilder taskList = new StringBuilder();
            for (EmployeTache et : tachesRealisees) {
                taskList.append(String.format("%-4d %-15s %-19s %s%n",
                    et.getTache().getId(),
                    et.getTache().getNom(),
                    sdf.format(et.getDateDebutReelle()),
                    sdf.format(et.getDateFinReelle())));
            }
            System.out.print(taskList.toString());
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("      Test des tâches avec prix > 1000 DH");
            System.out.println("=".repeat(50));
            List<Tache> tachesCheres = querySession.createQuery(
                "FROM Tache t WHERE t.prix > 1000", Tache.class).list();
            if (tachesCheres != null && !tachesCheres.isEmpty()) {
                System.out.println("\n Tâches avec prix > 1000 DH:");
                System.out.println("-".repeat(40));
                System.out.printf("%-15s %s%n", "Nom", "Prix");
                System.out.println("-".repeat(40));
                for (Tache t : tachesCheres) {
                    System.out.printf("%-15s %.2f DH%n", t.getNom(), t.getPrix());
                }
            } else {
                System.out.println("Aucune tâche trouvée avec prix > 1000 DH");
            }
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("     Test des tâches réalisées entre deux dates");
            System.out.println("=".repeat(50));
            Date dateDebut = sdf.parse("01/02/2013");
            Date dateFin = sdf.parse("31/03/2013");
            List<Tache> tachesEntreDates = querySession.createQuery(
                "SELECT DISTINCT t FROM Tache t JOIN t.employeTaches et " +
                "WHERE et.dateFinReelle IS NOT NULL " +
                "AND et.dateFinReelle BETWEEN :dateDebut AND :dateFin", Tache.class)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .list();
            if (tachesEntreDates != null && !tachesEntreDates.isEmpty()) {
                System.out.println("\n Période: " + sdf.format(dateDebut) + " - " + sdf.format(dateFin));
                System.out.println("-".repeat(40));
                for (Tache t : tachesEntreDates) {
                    System.out.println("▪️ " + t.getNom());
                }
            } else {
                System.out.println("Aucune tâche trouvée dans cette période");
            }
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("    Test des tâches réalisées par un employé");
            System.out.println("=".repeat(50));
            List<Tache> tachesEmploye1 = querySession.createQuery(
                "SELECT DISTINCT t FROM Tache t JOIN t.employeTaches et " +
                "WHERE et.employe.id = :employeId AND et.dateFinReelle IS NOT NULL", Tache.class)
                .setParameter("employeId", employe1.getId())
                .list();
            if (tachesEmploye1 != null && !tachesEmploye1.isEmpty()) {
                System.out.println("\n Employé: " + employe1.getPrenom() + " " + employe1.getNom());
                System.out.println("-".repeat(40));
                System.out.println("Tâches réalisées:");
                for (Tache t : tachesEmploye1) {
                    System.out.println("▪️ " + t.getNom());
                }
            } else {
                System.out.println("Aucune tâche trouvée pour cet employé");
            }
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("     Test des projets gérés par un employé");
            System.out.println("=".repeat(50));
            List<Projet> projetsEmploye1 = querySession.createQuery(
                "SELECT DISTINCT p FROM Projet p JOIN p.taches t JOIN t.employeTaches et " +
                "WHERE et.employe.id = :employeId", Projet.class)
                .setParameter("employeId", employe1.getId())
                .list();
            if (projetsEmploye1 != null && !projetsEmploye1.isEmpty()) {
                System.out.println("\n👤 Employé: " + employe1.getPrenom() + " " + employe1.getNom());
                System.out.println("-".repeat(40));
                System.out.println("Projets gérés:");
                for (Projet p : projetsEmploye1) {
                    System.out.println(" " + p.getNom());
                }
            } else {
                System.out.println("Aucun projet trouvé pour cet employé");
            }
            
            querySession.close();
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("            Tests terminés avec succès");
            System.out.println("=".repeat(50));
            
        } catch (ParseException e) {
            System.err.println(" Erreur de format de date: " + e.getMessage());
        } catch (Exception e) {
            System.err.println(" Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}