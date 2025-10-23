package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categorie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produit> produits;
    
    public Categorie() {}
    
    public Categorie(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public List<Produit> getProduits() { return produits; }
    public void setProduits(List<Produit> produits) { this.produits = produits; }
    
    @Override
    public String toString() {
        return "Categorie{id=" + id + ", libelle='" + libelle + "', description='" + description + "'}";
    }
}