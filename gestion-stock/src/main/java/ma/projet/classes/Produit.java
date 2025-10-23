package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "produits")
@NamedQueries({
    @NamedQuery(
        name = "Produit.findByPrixSuperieur", 
        query = "SELECT p FROM Produit p WHERE p.prix > :prix"
    )
})
public class Produit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "reference", unique = true, nullable = false, length = 20)
    private String reference;
    
    @Column(name = "designation", nullable = false, length = 100)
    private String designation;
    
    @Column(name = "prix", nullable = false)
    private double prix;
    
    @Column(name = "quantite_stock")
    private int quantiteStock;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
    
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommande> lignesCommande;
    
    public Produit() {}
    
    public Produit(String reference, String designation, double prix, int quantiteStock, Categorie categorie) {
        this.reference = reference;
        this.designation = designation;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
        this.categorie = categorie;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    
    public int getQuantiteStock() { return quantiteStock; }
    public void setQuantiteStock(int quantiteStock) { this.quantiteStock = quantiteStock; }
    
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
    
    public List<LigneCommande> getLignesCommande() { return lignesCommande; }
    public void setLignesCommande(List<LigneCommande> lignesCommande) { this.lignesCommande = lignesCommande; }
    
    @Override
    public String toString() {
        return "Produit{id=" + id + ", reference='" + reference + "', designation='" + designation + 
               "', prix=" + prix + ", quantiteStock=" + quantiteStock + "}";
    }
}