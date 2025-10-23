package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Categorie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategorieService implements IDao<Categorie> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Categorie categorie) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(categorie);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Categorie categorie) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(categorie);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Categorie categorie) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(categorie);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Categorie findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Categorie.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categorie> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Categorie> query = session.createQuery("FROM Categorie", Categorie.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}