package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Commande;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommandeService implements IDao<Commande> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Commande commande) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(commande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Commande commande) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(commande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Commande commande) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(commande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Commande findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (Commande) session.get(Commande.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commande> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM Commande");
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}