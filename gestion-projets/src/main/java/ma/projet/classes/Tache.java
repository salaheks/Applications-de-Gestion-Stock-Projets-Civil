package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tache")
@NamedQueries({
    @NamedQuery(
        name = "Tache.findByPrixSuperieurA1000",
        query = "SELECT t FROM Tache t WHERE t.prix > 1000"
    ),
    @NamedQuery(
        name = "Tache.findTachesRealiseesEntreDates",
        query = "SELECT DISTINCT t FROM Tache t JOIN t.employeTaches et WHERE et.dateDebutReelle >= :dateDebut AND et.dateFinReelle <= :dateFin"
    )
})
public class Tache {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "nom", nullable = false)
    private String nom;
    
    @Column(name = "date_debut_prevue")
    @Temporal(TemporalType.DATE)
    private Date dateDebutPrevue;
    
    @Column(name = "date_fin_prevue")
    @Temporal(TemporalType.DATE)
    private Date dateFinPrevue;
    
    @Column(name = "prix")
    private double prix;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id")
    private Projet projet;
    
    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeTache> employeTaches;
    
    public Tache() {}
    
    public Tache(String nom, Date dateDebutPrevue, Date dateFinPrevue, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebutPrevue = dateDebutPrevue;
        this.dateFinPrevue = dateFinPrevue;
        this.prix = prix;
        this.projet = projet;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Date getDateDebutPrevue() {
        return dateDebutPrevue;
    }
    
    public void setDateDebutPrevue(Date dateDebutPrevue) {
        this.dateDebutPrevue = dateDebutPrevue;
    }
    
    public Date getDateFinPrevue() {
        return dateFinPrevue;
    }
    
    public void setDateFinPrevue(Date dateFinPrevue) {
        this.dateFinPrevue = dateFinPrevue;
    }
    
    public double getPrix() {
        return prix;
    }
    
    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    public Projet getProjet() {
        return projet;
    }
    
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    
    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }
    
    public void setEmployeTaches(List<EmployeTache> employeTaches) {
        this.employeTaches = employeTaches;
    }
}