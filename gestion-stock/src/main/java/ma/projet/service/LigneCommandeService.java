package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.LigneCommande;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LigneCommandeService implements IDao<LigneCommande> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(LigneCommande ligneCommande) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(ligneCommande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(LigneCommande ligneCommande) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(ligneCommande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(LigneCommande ligneCommande) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(ligneCommande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LigneCommande findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (LigneCommande) session.get(LigneCommande.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<LigneCommande> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM LigneCommande");
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}