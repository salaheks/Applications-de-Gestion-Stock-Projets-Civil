package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.Tache;
import ma.projet.classes.Projet;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeService implements IDao<Employe> {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public boolean create(Employe employe) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(employe);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(Employe employe) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(employe);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(Employe employe) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(employe);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Employe findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Employe.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Employe> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Employe> query = session.createQuery("FROM Employe", Employe.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<Tache> getTachesRealiseesByEmploye(int employeId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Tache> query = session.createQuery(
                "SELECT DISTINCT t FROM Tache t JOIN t.employeTaches et WHERE et.employe.id = :employeId AND et.dateFinReelle IS NOT NULL", 
                Tache.class
            );
            query.setParameter("employeId", employeId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<Projet> getProjetsGeresByEmploye(int employeId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Projet> query = session.createQuery(
                "SELECT DISTINCT p FROM Projet p JOIN p.taches t JOIN t.employeTaches et WHERE et.employe.id = :employeId", 
                Projet.class
            );
            query.setParameter("employeId", employeId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}