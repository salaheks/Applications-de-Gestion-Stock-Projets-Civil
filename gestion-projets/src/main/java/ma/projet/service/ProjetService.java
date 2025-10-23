package ma.projet.service;

import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional
public class ProjetService implements IDao<Projet> {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public boolean create(Projet projet) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(projet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(Projet projet) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(projet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(Projet projet) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(projet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Projet findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Projet.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Projet> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM Projet");
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<Tache> getTachesPlanifiees(int projetId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM Tache t WHERE t.projet.id = :projetId");
            query.setParameter("projetId", projetId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public void afficherTachesRealisees(int projetId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Projet projet = session.get(Projet.class, projetId);
            if (projet != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println("Projet : " + projet.getId() + "\tNom : " + projet.getNom() + 
                                 "\tDate début : " + sdf.format(projet.getDateDebut()));
                System.out.println("Liste des tâches:");
                System.out.println("Num\tNom\t\t\tDate Début Réelle\tDate Fin Réelle");
                
                Query query = session.createQuery(
                    "SELECT DISTINCT t, et FROM Tache t JOIN t.employeTaches et WHERE t.projet.id = :projetId AND et.dateDebutReelle IS NOT NULL"
                );
                query.setParameter("projetId", projetId);
                
                List<Object[]> results = query.list();
                for (Object[] result : results) {
                    Tache tache = (Tache) result[0];
                    EmployeTache et = (EmployeTache) result[1];
                    
                    String dateDebutStr = et.getDateDebutReelle() != null ? sdf.format(et.getDateDebutReelle()) : "Non commencée";
                    String dateFinStr = et.getDateFinReelle() != null ? sdf.format(et.getDateFinReelle()) : "En cours";
                    
                    System.out.println(tache.getId() + "\t" + tache.getNom() + "\t\t" + 
                                     dateDebutStr + "\t\t" + dateFinStr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}