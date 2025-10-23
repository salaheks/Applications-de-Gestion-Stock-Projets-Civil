package ma.projet.service;

import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TacheService implements IDao<Tache> {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public boolean create(Tache tache) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(tache);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(Tache tache) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(tache);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(Tache tache) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(tache);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Tache findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Tache.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Tache> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM Tache");
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<Tache> getTachesAvecPrixSuperieurA1000() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.getNamedQuery("Tache.findByPrixSuperieurA1000");
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<Tache> getTachesRealiseesEntreDates(Date dateDebut, Date dateFin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.getNamedQuery("Tache.findTachesRealiseesEntreDates");
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}