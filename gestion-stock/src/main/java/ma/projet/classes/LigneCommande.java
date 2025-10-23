package ma.projet.classes;

import javax.persistence.*;

@Entity
@Table(name = "lignes_commande")
public class LigneCommande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "quantite", nullable = false)
    private int quantite;
    
    @Column(name = "prix_unitaire", nullable = false)
    private double prixUnitaire;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id")
    private Commande commande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id")
    private Produit produit;
    
    public LigneCommande() {}
    
    public LigneCommande(int quantite, double prixUnitaire, Commande commande, Produit produit) {
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.commande = commande;
        this.produit = produit;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    
    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    
    public double getMontantTotal() {
        return quantite * prixUnitaire;
    }
    
    @Override
    public String toString() {
        return "LigneCommande{id=" + id + ", quantite=" + quantite + 
               ", prixUnitaire=" + prixUnitaire + ", montantTotal=" + getMontantTotal() + "}";
    }
}