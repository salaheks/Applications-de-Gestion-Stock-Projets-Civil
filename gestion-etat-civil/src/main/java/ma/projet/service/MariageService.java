package ma.projet.service;

import ma.projet.beans.Mariage;
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
public class MariageService implements IDao<Mariage> {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public boolean create(Mariage mariage) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(mariage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(Mariage mariage) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(mariage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(Mariage mariage) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(mariage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Mariage findById(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Mariage.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Mariage> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Mariage> query = session.createQuery("FROM Mariage", Mariage.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}