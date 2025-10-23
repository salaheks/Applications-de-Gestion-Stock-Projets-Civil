package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import ma.projet.beans.Homme;
import ma.projet.beans.Femme;
import ma.projet.beans.Mariage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    static {
        try {
            Configuration configuration = new Configuration();
            
            Properties properties = new Properties();
            InputStream inputStream = HibernateUtil.class.getClassLoader()
                    .getResourceAsStream("application.properties");
            
            if (inputStream != null) {
                properties.load(inputStream);
                inputStream.close();
            }
            
            configuration.setProperty(Environment.DRIVER, properties.getProperty("hibernate.connection.driver_class"));
            configuration.setProperty(Environment.URL, properties.getProperty("hibernate.connection.url"));
            configuration.setProperty(Environment.USER, properties.getProperty("hibernate.connection.username"));
            configuration.setProperty(Environment.PASS, properties.getProperty("hibernate.connection.password"));
            configuration.setProperty(Environment.DIALECT, properties.getProperty("hibernate.dialect"));
            configuration.setProperty(Environment.HBM2DDL_AUTO, properties.getProperty("hibernate.hbm2ddl.auto"));
            configuration.setProperty(Environment.SHOW_SQL, properties.getProperty("hibernate.show_sql"));
            configuration.setProperty(Environment.FORMAT_SQL, properties.getProperty("hibernate.format_sql"));
            
            configuration.addAnnotatedClass(Homme.class);
            configuration.addAnnotatedClass(Femme.class);
            configuration.addAnnotatedClass(Mariage.class);
            
            sessionFactory = configuration.buildSessionFactory();
            
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des propriétés: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation de Hibernate: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}