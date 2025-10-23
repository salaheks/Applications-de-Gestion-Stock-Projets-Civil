package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FemmeService implements IDao<Femme> {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public boolean create(Femme femme) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(femme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(Femme femme) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(femme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(Femme femme) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(femme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Femme findById(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Femme.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Femme> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Femme> query = session.createQuery("FROM Femme", Femme.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public Femme findOldestWoman() {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "FROM Femme f ORDER BY f.dateNaissance ASC";
            Query<Femme> query = session.createQuery(hql, Femme.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public Long countChildrenBetweenDates(Long femmeId, Date dateDebut, Date dateFin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createNativeQuery("SELECT SUM(m.nbr_enfants) FROM mariage m WHERE m.femme_id = ? AND m.date_debut BETWEEN ? AND ?");
            query.setParameter(1, femmeId);
            query.setParameter(2, dateDebut);
            query.setParameter(3, dateFin);
            Object result = query.uniqueResult();
            return result != null ? ((Number) result).longValue() : 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
    
    @Transactional(readOnly = true)
    public List<Femme> findWomenMarriedTwiceOrMore() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Femme> query = session.createNamedQuery("Femme.findWomenMarriedTwiceOrMore", Femme.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}