package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "homme")
@NamedQueries({
    @NamedQuery(name = "Homme.findMenWith4Wives", 
                query = "SELECT h FROM Homme h WHERE h.id IN (SELECT m.homme.id FROM Mariage m WHERE m.dateFin IS NULL GROUP BY m.homme.id HAVING COUNT(m.homme.id) = 4)")
})
public class Homme {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom", nullable = false)
    private String nom;
    
    @Column(name = "prenom", nullable = false)
    private String prenom;
    
    @Column(name = "telephone")
    private String telephone;
    
    @Column(name = "adresse")
    private String adresse;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_naissance", nullable = false)
    private Date dateNaissance;
    
    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages;
    
    public Homme() {}
    
    public Homme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public Date getDateNaissance() {
        return dateNaissance;
    }
    
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
    public List<Mariage> getMariages() {
        return mariages;
    }
    
    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
    
    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}