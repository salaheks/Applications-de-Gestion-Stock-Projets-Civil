package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeTacheService implements IDao<EmployeTache> {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public boolean create(EmployeTache employeTache) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(employeTache);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(EmployeTache employeTache) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(employeTache);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(EmployeTache employeTache) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(employeTache);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public EmployeTache findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(EmployeTache.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EmployeTache> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM EmployeTache");
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<EmployeTache> findByEmployeId(int employeId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM EmployeTache et WHERE et.employe.id = :employeId");
            query.setParameter("employeId", employeId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<EmployeTache> findByTacheId(int tacheId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM EmployeTache et WHERE et.tache.id = :tacheId");
            query.setParameter("tacheId", tacheId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}