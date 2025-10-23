package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employe_tache")
public class EmployeTache {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employe_id")
    private Employe employe;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tache_id")
    private Tache tache;
    
    @Column(name = "date_debut_reelle")
    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;
    
    @Column(name = "date_fin_reelle")
    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;
    
    public EmployeTache() {}
    
    public EmployeTache(Employe employe, Tache tache, Date dateDebutReelle, Date dateFinReelle) {
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Employe getEmploye() {
        return employe;
    }
    
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    
    public Tache getTache() {
        return tache;
    }
    
    public void setTache(Tache tache) {
        this.tache = tache;
    }
    
    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }
    
    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }
    
    public Date getDateFinReelle() {
        return dateFinReelle;
    }
    
    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }
}