package ma.projet.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "date_commande", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateCommande;
    
    @Column(name = "client", length = 100)
    private String client;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommande> lignesCommande;
    
    public Commande() {}
    
    public Commande(Date dateCommande, String client) {
        this.dateCommande = dateCommande;
        this.client = client;
        this.lignesCommande = new ArrayList<>();
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Date getDateCommande() { return dateCommande; }
    public void setDateCommande(Date dateCommande) { this.dateCommande = dateCommande; }
    
    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }
    
    public List<LigneCommande> getLignesCommande() { return lignesCommande; }
    public void setLignesCommande(List<LigneCommande> lignesCommande) { this.lignesCommande = lignesCommande; }
    
    @Override
    public String toString() {
        return "Commande{id=" + id + ", dateCommande=" + dateCommande + ", client='" + client + "'}";
    }
}