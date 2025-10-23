package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Femme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HommeService implements IDao<Homme> {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public boolean create(Homme homme) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(homme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(Homme homme) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(homme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(Homme homme) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(homme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Homme findById(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Homme.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Homme> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Homme> query = session.createQuery("FROM Homme", Homme.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<Femme> getEpousesBetweenDates(Long hommeId, Date dateDebut, Date dateFin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "SELECT m.femme FROM Mariage m WHERE m.homme.id = :hommeId " +
                        "AND m.dateDebut BETWEEN :dateDebut AND :dateFin";
            Query<Femme> query = session.createQuery(hql, Femme.class);
            query.setParameter("hommeId", hommeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public Long countMenWith4WivesBetweenDates(Date dateDebut, Date dateFin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            
            // Using Criteria API
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Mariage> mariage = cq.from(Mariage.class);
            
            // Conditions
            Predicate datePredicate = cb.between(mariage.get("dateDebut"), dateDebut, dateFin);
            Predicate activeMarriage = cb.isNull(mariage.get("dateFin"));
            
            // Group by homme and count
            cq.groupBy(mariage.get("homme"));
            cq.having(cb.equal(cb.count(mariage), 4L));
            cq.select(cb.count(mariage.get("homme")));
            cq.where(cb.and(datePredicate, activeMarriage));
            
            return session.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
    
    @Transactional(readOnly = true)
    public void displayMariageDetails(Long hommeId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Homme homme = session.get(Homme.class, hommeId);
            if (homme == null) {
                System.out.println("Homme non trouvé avec l'ID: " + hommeId);
                return;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            // Get all marriages for this man
            String hql = "FROM Mariage m WHERE m.homme.id = :hommeId ORDER BY m.dateDebut";
            Query<Mariage> query = session.createQuery(hql, Mariage.class);
            query.setParameter("hommeId", hommeId);
            List<Mariage> mariages = query.list();
            
            System.out.println("DÉTAILS DES MARIAGES DE " + homme.getNom().toUpperCase() + " " + homme.getPrenom().toUpperCase());
            
            // Mariages en cours
            List<String[]> rowsEnCours = new ArrayList<>();
            for (Mariage m : mariages) {
                if (m.getDateFin() == null) {
                    rowsEnCours.add(new String[]{
                        m.getFemme().getPrenom().toUpperCase(),
                        m.getFemme().getNom().toUpperCase(),
                        sdf.format(m.getDateDebut()),
                        String.valueOf(m.getNbrEnfants())
                    });
                }
            }
            
            if (!rowsEnCours.isEmpty()) {
                System.out.println("\nMARIAGES EN COURS :");
                String[] headersEnCours = {"Prénom", "Nom", "Date début", "Nbr enfants"};
                ma.projet.util.TableFormatter.printTable(headersEnCours, rowsEnCours);
            }
            
            // Mariages échoués
            List<String[]> rowsEchoues = new ArrayList<>();
            for (Mariage m : mariages) {
                if (m.getDateFin() != null) {
                    rowsEchoues.add(new String[]{
                        m.getFemme().getPrenom().toUpperCase(),
                        m.getFemme().getNom().toUpperCase(),
                        sdf.format(m.getDateDebut()),
                        sdf.format(m.getDateFin()),
                        String.valueOf(m.getNbrEnfants())
                    });
                }
            }
            
            if (!rowsEchoues.isEmpty()) {
                System.out.println("\nMARIAGES ÉCHOUÉS :");
                String[] headersEchoues = {"Prénom", "Nom", "Date début", "Date fin", "Nbr enfants"};
                ma.projet.util.TableFormatter.printTable(headersEchoues, rowsEchoues);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}