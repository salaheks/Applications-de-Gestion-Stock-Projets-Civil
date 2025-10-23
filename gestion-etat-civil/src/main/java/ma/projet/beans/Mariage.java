package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mariage")
public class Mariage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut", nullable = false)
    private Date dateDebut;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;
    
    @Column(name = "nbr_enfants", nullable = false)
    private int nbrEnfants = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homme_id", nullable = false)
    private Homme homme;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "femme_id", nullable = false)
    private Femme femme;
    
    public Mariage() {}
    
    public Mariage(Date dateDebut, Date dateFin, int nbrEnfants, Homme homme, Femme femme) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfants = nbrEnfants;
        this.homme = homme;
        this.femme = femme;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public Date getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    
    public int getNbrEnfants() {
        return nbrEnfants;
    }
    
    public void setNbrEnfants(int nbrEnfants) {
        this.nbrEnfants = nbrEnfants;
    }
    
    public Homme getHomme() {
        return homme;
    }
    
    public void setHomme(Homme homme) {
        this.homme = homme;
    }
    
    public Femme getFemme() {
        return femme;
    }
    
    public void setFemme(Femme femme) {
        this.femme = femme;
    }
    
    public boolean isActive() {
        return dateFin == null;
    }
    
    @Override
    public String toString() {
        return "Mariage{" +
                "dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbrEnfants=" + nbrEnfants +
                ", homme=" + homme +
                ", femme=" + femme +
                '}';
    }
}