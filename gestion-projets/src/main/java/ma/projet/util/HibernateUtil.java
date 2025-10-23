package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    static {
        try {
            Configuration configuration = new Configuration();
            
            // Load properties from application.properties
            Properties properties = loadProperties();
            configuration.setProperties(properties);
            
            // Add annotated classes
            configuration.addAnnotatedClass(ma.projet.classes.Projet.class);
            configuration.addAnnotatedClass(ma.projet.classes.Tache.class);
            configuration.addAnnotatedClass(ma.projet.classes.Employe.class);
            configuration.addAnnotatedClass(ma.projet.classes.EmployeTache.class);
            
            sessionFactory = configuration.buildSessionFactory();
            
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de SessionFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }
    
    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = HibernateUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Fichier application.properties introuvable");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement des propriétés", e);
        }
        return properties;
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}