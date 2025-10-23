package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommande;
import ma.projet.classes.Produit;
import ma.projet.config.SpringConfig;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestGestionStock {

    public static void main(String[] args) {

        
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        try {

            CategorieService categorieService = context.getBean(CategorieService.class);
            ProduitService produitService = context.getBean(ProduitService.class);
            CommandeService commandeService = context.getBean(CommandeService.class);
            LigneCommandeService ligneCommandeService = context.getBean(LigneCommandeService.class);

            System.out.println("=== Test de création des catégories ===");

            Categorie catOrdinateurs = new Categorie("Ordinateurs", "Ordinateurs de bureau et portables");
            Categorie catPeriph = new Categorie("Périphériques", "Souris, claviers, écrans");
            Categorie catLogiciels = new Categorie("Logiciels", "Logiciels et applications");

            categorieService.create(catOrdinateurs);
            categorieService.create(catPeriph);
            categorieService.create(catLogiciels);

            System.out.println("Catégories créées avec succès!");

            System.out.println("\n=== Test de création des produits ===");

            Produit p1 = new Produit("ES12", "Ordinateur portable Dell", 120, 10, catOrdinateurs);
            Produit p2 = new Produit("ZR85", "Souris optique", 100, 25, catPeriph);
            Produit p3 = new Produit("EE85", "Écran LCD 24 pouces", 200, 5, catPeriph);
            Produit p4 = new Produit("AB45", "Clavier mécanique", 80, 15, catPeriph);
            Produit p5 = new Produit("CD67", "Logiciel antivirus", 150, 20, catLogiciels);

            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);
            produitService.create(p4);
            produitService.create(p5);

            System.out.println("Produits créés avec succès!");

            System.out.println("\n=== Test d'affichage des produits par catégorie ===");

            List<Produit> produitsOrdinateurs = produitService.findByCategorie(catOrdinateurs);
            System.out.println("Produits de la catégorie Ordinateurs :");
            for (Produit p : produitsOrdinateurs) {
                System.out.println("- " + p.getReference() + " : " + p.getDesignation() + " (" + p.getPrix() + " DH)");
            }

            List<Produit> produitsPeriph = produitService.findByCategorie(catPeriph);
            System.out.println("\nProduits de la catégorie Périphériques :");
            for (Produit p : produitsPeriph) {
                System.out.println("- " + p.getReference() + " : " + p.getDesignation() + " (" + p.getPrix() + " DH)");
            }

            System.out.println("\n=== Test de création des commandes ===");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdf.parse("14/03/2013");
            Date date2 = sdf.parse("15/03/2013");
            Date date3 = sdf.parse("16/03/2013");

            Commande cmd1 = new Commande(date1, "Client A");
            Commande cmd2 = new Commande(date2, "Client B");
            Commande cmd3 = new Commande(date3, "Client C");

            commandeService.create(cmd1);
            commandeService.create(cmd2);
            commandeService.create(cmd3);

            System.out.println("Commandes créées avec succès!");

            System.out.println("\n=== Test de création des lignes de commande ===");

            LigneCommande lc1 = new LigneCommande(7, 120, cmd1, p1);
            LigneCommande lc2 = new LigneCommande(14, 100, cmd1, p2);
            LigneCommande lc3 = new LigneCommande(5, 200, cmd1, p3);
            cmd1.getLignesCommande().add(lc1);
            cmd1.getLignesCommande().add(lc2);
            cmd1.getLignesCommande().add(lc3);

            LigneCommande lc4 = new LigneCommande(3, 150, cmd2, p5);
            LigneCommande lc5 = new LigneCommande(2, 80, cmd2, p4);
            cmd2.getLignesCommande().add(lc4);
            cmd2.getLignesCommande().add(lc5);

            ligneCommandeService.create(lc1);
            ligneCommandeService.create(lc2);
            ligneCommandeService.create(lc3);
            ligneCommandeService.create(lc4);
            ligneCommandeService.create(lc5);

            System.out.println("Lignes de commande créées avec succès!");

            System.out.println("\n=== Test d'affichage des produits d'une commande ===");

            System.out.println("Commande : " + cmd1.getId() + "     Date : " + sdf.format(cmd1.getDateCommande()));
            System.out.println("Liste des produits :");
            System.out.println("+-----------+----------+-----------+");
            System.out.println("| Référence |   Prix   | Quantité |");
            System.out.println("+-----------+----------+-----------+");
            List<LigneCommande> lignesCommande = cmd1.getLignesCommande();
            if (lignesCommande != null && !lignesCommande.isEmpty()) {
                for (LigneCommande lc : lignesCommande) {
                    System.out.printf("| %-9s | %6.2f DH | %8d |%n",
                            lc.getProduit().getReference(),
                            lc.getProduit().getPrix(),
                            lc.getQuantite());
                }
            } else {
                System.out.println("| Aucun produit dans cette commande |");
            }
            System.out.println("+-----------+--------+-----------+");

            System.out.println("\n=== Test d'affichage des produits commandés entre deux dates ===");

            Date dateDebut = sdf.parse("13/03/2013");
            Date dateFin = sdf.parse("15/03/2013");

            List<Produit> produitsCommandes = produitService.findProduitsCommandesEntreDates(dateDebut, dateFin);
            System.out.println("Produits commandés entre le " + sdf.format(dateDebut) + " et le " + sdf.format(dateFin) + " :");
            System.out.println("+-----------+---------------------------+----------+--------------+");
            System.out.println("| Référence |        Désignation       |   Prix   | Qté en Stock |");
            System.out.println("+-----------+---------------------------+----------+--------------+");
            if (!produitsCommandes.isEmpty()) {
                for (Produit p : produitsCommandes) {
                    System.out.printf("| %-9s | %-25s | %6.2f DH | %11d |%n",
                            p.getReference(),
                            p.getDesignation(),
                            p.getPrix(),
                            p.getQuantiteStock());
                }
            } else {
                System.out.println("|                 Aucun produit trouvé                          |");
            }
            System.out.println("+-----------+---------------------------+----------+--------------+");

            System.out.println("\n=== Test d'affichage des produits dont le prix > 100 DH (requête nommée) ===");

            List<Produit> produitsChers = produitService.findProduitsPrixSuperieur(100);
            System.out.println("Produits avec prix > 100 DH :");
            System.out.println("+-----------+---------------------------+----------+--------------+");
            System.out.println("| Référence |        Désignation       |   Prix   | Qté en Stock |");
            System.out.println("+-----------+---------------------------+----------+--------------+");
            if (!produitsChers.isEmpty()) {
                for (Produit p : produitsChers) {
                    System.out.printf("| %-9s | %-25s | %6.2f DH | %11d |%n",
                            p.getReference(),
                            p.getDesignation(),
                            p.getPrix(),
                            p.getQuantiteStock());
                }
            } else {
                System.out.println("|                 Aucun produit trouvé                          |");
            }
            System.out.println("+-----------+---------------------------+----------+--------------+");

            System.out.println("\n=== Tests terminés avec succès! ===");

        } catch (Exception e) {
            System.err.println("Erreur lors des tests : " + e.getMessage());
            e.printStackTrace();
        } finally {
            ((AnnotationConfigApplicationContext) context).close();
        }
    }
}