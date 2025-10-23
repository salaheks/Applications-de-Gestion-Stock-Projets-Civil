package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Produit;
import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommande;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

@Service
@Transactional
public class ProduitService implements IDao<Produit> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Produit produit) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(produit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Produit produit) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(produit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Produit produit) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(produit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Produit findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (Produit) session.get(Produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM Produit");
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Produit> findByCategorie(Categorie categorie) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM Produit p WHERE p.categorie = :categorie");
            query.setParameter("categorie", categorie);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Produit> findProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "SELECT DISTINCT lc.produit FROM LigneCommande lc " +
                        "WHERE lc.commande.dateCommande BETWEEN :dateDebut AND :dateFin";
            Query query = session.createQuery(hql);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<LigneCommande> findProduitsParCommande(Commande commande) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM LigneCommande lc WHERE lc.commande = :commande");
            query.setParameter("commande", commande);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public void afficherProduitsCommande(int commandeId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            
            Commande commande = (Commande) session.get(Commande.class, commandeId);
            if (commande != null) {
                System.out.println("Commande : " + commande.getId() + "     Date : " + commande.getDateCommande());
                System.out.println("Liste des produits :");
                System.out.println("Référence\tPrix\t\tQuantité");
                
                List<LigneCommande> lignes = findProduitsParCommande(commande);
                for (LigneCommande ligne : lignes) {
                    System.out.println(ligne.getProduit().getReference() + "\t\t" + 
                                     ligne.getPrixUnitaire() + " DH\t" + ligne.getQuantite());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public List<Produit> findProduitsPrixSuperieur(double prix) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.getNamedQuery("Produit.findByPrixSuperieur");
            query.setParameter("prix", prix);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}