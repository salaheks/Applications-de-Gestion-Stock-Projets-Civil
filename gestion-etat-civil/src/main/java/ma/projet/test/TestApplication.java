package ma.projet.test;

import ma.projet.beans.Homme;
import ma.projet.beans.Femme;
import ma.projet.beans.Mariage;
import ma.projet.service.HommeService;
import ma.projet.service.FemmeService;
import ma.projet.service.MariageService;
import ma.projet.config.SpringConfig;
import ma.projet.util.TableFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestApplication {
    
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        
        try {
            HommeService hommeService = context.getBean(HommeService.class);
            FemmeService femmeService = context.getBean(FemmeService.class);
            MariageService mariageService = context.getBean(MariageService.class);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            System.out.println("\n=== 1. CRÉATION DES FEMMES ===");
            
            // Créer 10 femmes avec des dates de naissance différentes
            Femme[] femmes = new Femme[10];
            femmes[0] = new Femme("RAMI", "Salima", "0661234567", "Casablanca", createDate(1970, 5, 15));    // 3ème plus âgée
            femmes[1] = new Femme("ALI", "Amal", "0662345678", "Rabat", createDate(1975, 8, 20));
            femmes[2] = new Femme("ALAOUI", "Wafa", "0663456789", "Fès", createDate(1980, 12, 10));
            femmes[3] = new Femme("ALAMI", "Karima", "0664567890", "Marrakech", createDate(1965, 3, 25));    // La plus âgée
            femmes[4] = new Femme("BENNANI", "Fatima", "0665678901", "Tanger", createDate(1982, 7, 8));
            femmes[5] = new Femme("CHRAIBI", "Aicha", "0666789012", "Agadir", createDate(1978, 11, 30));
            femmes[6] = new Femme("DRISSI", "Nadia", "0667890123", "Meknès", createDate(1985, 4, 18));
            femmes[7] = new Femme("EL FASSI", "Khadija", "0668901234", "Oujda", createDate(1968, 9, 5));     // 2ème plus âgée
            femmes[8] = new Femme("GHALI", "Zineb", "0669012345", "Tétouan", createDate(1988, 1, 12));
            femmes[9] = new Femme("HAJJI", "Samira", "0660123456", "Safi", createDate(1990, 6, 22));
            
            for (Femme femme : femmes) {
                femmeService.create(femme);
            }
            
            System.out.println("\n=== 2. CRÉATION DES HOMMES ===");
            
            // Créer 5 hommes
            Homme[] hommes = new Homme[5];
            hommes[0] = new Homme("SAFI", "SAID", "0671234567", "SAFI", createDate(1960, 12, 3));      // L'homme avec 4 mariages
            hommes[1] = new Homme("BENNANI", "Ahmed", "0672345678", "Rabat", createDate(1965, 4, 15));  // L'homme avec 3 mariages
            hommes[2] = new Homme("ALAMI", "Youssef", "0673456789", "Fès", createDate(1970, 8, 28));   // Un mariage
            hommes[3] = new Homme("CHRAIBI", "Omar", "0674567890", "Marrakech", createDate(1975, 1, 10)); // Un mariage
            hommes[4] = new Homme("DRISSI", "Khalid", "0675678901", "Tanger", createDate(1980, 5, 20)); // Un mariage
            
            for (Homme homme : hommes) {
                hommeService.create(homme);
            }
            
            System.out.println("\n=== 3. CRÉATION DES MARIAGES ===");
            System.out.println("Création des mariages pour SAFI SAID...");
            
            // Mariages en cours de SAFI SAID
            Mariage m2 = new Mariage(createDate(1990, 9, 3), null, 4, hommes[0], femmes[0]); // Salima RAMI
            mariageService.create(m2);
            
            Mariage m3 = new Mariage(createDate(1995, 9, 3), null, 2, hommes[0], femmes[1]); // Amal ALI
            mariageService.create(m3);
            
            Mariage m4 = new Mariage(createDate(2000, 11, 4), null, 3, hommes[0], femmes[2]); // Wafa ALAOUI - Correction de la date (11 au lieu de 12)
            mariageService.create(m4);
            
            // Mariage échoué de SAFI SAID
            Mariage m1 = new Mariage(createDate(1989, 9, 3), createDate(1990, 9, 3), 0, hommes[0], femmes[3]); // Karima ALAMI
            mariageService.create(m1);
            
            // Autres mariages pour tester les requêtes
            Mariage m5 = new Mariage(createDate(1985, 6, 15), null, 2, hommes[1], femmes[4]); // Ahmed - Fatima
            mariageService.create(m5);
            
            Mariage m6 = new Mariage(createDate(1990, 3, 20), createDate(1995, 3, 20), 1, hommes[1], femmes[5]); // Ahmed - Aicha (échec)
            mariageService.create(m6);
            
            Mariage m7 = new Mariage(createDate(1996, 7, 10), null, 3, hommes[1], femmes[6]); // Ahmed - Nadia
            mariageService.create(m7);
            
            // Femme mariée plusieurs fois
            Mariage m8 = new Mariage(createDate(1992, 4, 8), createDate(1997, 4, 8), 1, hommes[2], femmes[7]); // Youssef - Khadija
            mariageService.create(m8);
            
            Mariage m9 = new Mariage(createDate(1998, 10, 12), null, 2, hommes[3], femmes[7]); // Omar - Khadija (2ème mariage)
            mariageService.create(m9);
            
            Mariage m10 = new Mariage(createDate(2005, 2, 14), null, 1, hommes[4], femmes[8]); // Khalid - Zineb
            mariageService.create(m10);
            
            System.out.println("Données créées avec succès !");
            System.out.println();
            
            // Test 1: Afficher la liste des femmes
            System.out.println("\n=== TEST 1: LISTE DES FEMMES ===");
            System.out.println("Résultat attendu : Liste des 10 femmes avec leurs dates de naissance");
            List<Femme> listeFemmes = femmeService.findAll();
            
            String[] headersFemmes = {"ID", "Prénom", "Nom", "Téléphone", "Adresse", "Date de naissance"};
            List<String[]> rowsFemmes = new ArrayList<>();
            
            for (Femme f : listeFemmes) {
                rowsFemmes.add(new String[]{
                    String.valueOf(f.getId()),
                    f.getPrenom(),
                    f.getNom(),
                    f.getTelephone(),
                    f.getAdresse(),
                    sdf.format(f.getDateNaissance())
                });
            }
            
            TableFormatter.printTable(headersFemmes, rowsFemmes);
            System.out.println();
            
            // Test 2: Afficher la femme la plus âgée
            System.out.println("\n=== TEST 2: FEMME LA PLUS ÂGÉE ===");
            System.out.println("Résultat attendu : KARIMA ALAMI (née le 25/03/1965)");
            Femme femmeAgee = femmeService.findOldestWoman();
            if (femmeAgee != null) {
                String[] headersFemmeAgee = {"Prénom", "Nom", "Date de naissance"};
                List<String[]> rowsFemmeAgee = new ArrayList<>();
                rowsFemmeAgee.add(new String[]{
                    femmeAgee.getPrenom(),
                    femmeAgee.getNom(),
                    sdf.format(femmeAgee.getDateNaissance())
                });
                TableFormatter.printTable(headersFemmeAgee, rowsFemmeAgee);
            }
            System.out.println();
            
            // Test 3: Afficher les épouses d'un homme donné entre deux dates
            System.out.println("\n=== TEST 3: ÉPOUSES DE SAFI SAID ENTRE 1990-2000 ===");
            System.out.println("Résultat attendu : SALIMA RAMI et AMAL ALI");
            Date dateDebut = createDate(1990, 1, 1);
            Date dateFin = createDate(2000, 12, 31);
            List<Femme> epouses = hommeService.getEpousesBetweenDates(hommes[0].getId(), dateDebut, dateFin);
            
            String[] headersEpouses = {"Prénom", "Nom", "Téléphone", "Adresse"};
            List<String[]> rowsEpouses = new ArrayList<>();
            for (Femme epouse : epouses) {
                rowsEpouses.add(new String[]{
                    epouse.getPrenom(),
                    epouse.getNom(),
                    epouse.getTelephone(),
                    epouse.getAdresse()
                });
            }
            TableFormatter.printTable(headersEpouses, rowsEpouses);
            System.out.println();
            
            // Test 4: Afficher le nombre d'enfants d'une femme entre deux dates
            System.out.println("\n=== TEST 4: NOMBRE D'ENFANTS DE SALIMA RAMI ENTRE 1990-2000 ===");
            System.out.println("Résultat attendu : 4 enfants");
            Long nbrEnfants = femmeService.countChildrenBetweenDates(femmes[0].getId(), dateDebut, dateFin);
            System.out.println("Nombre d'enfants : " + nbrEnfants);
            System.out.println();
            
            // Test 5: Afficher les femmes mariées deux fois ou plus
            System.out.println("\n=== TEST 5: FEMMES MARIÉES DEUX FOIS OU PLUS ===");
            System.out.println("Résultat attendu : KHADIJA EL FASSI (mariée deux fois)");
            List<Femme> femmesMultiMariees = femmeService.findWomenMarriedTwiceOrMore();
            
            String[] headersMultiMariees = {"ID", "Prénom", "Nom", "Adresse", "Date de naissance"};
            List<String[]> rowsMultiMariees = new ArrayList<>();
            for (Femme f : femmesMultiMariees) {
                rowsMultiMariees.add(new String[]{
                    String.valueOf(f.getId()),
                    f.getPrenom(),
                    f.getNom(),
                    f.getAdresse(),
                    sdf.format(f.getDateNaissance())
                });
            }
            TableFormatter.printTable(headersMultiMariees, rowsMultiMariees);
            System.out.println();
            
            // Test 6: Afficher les hommes mariés à quatre femmes entre deux dates
            System.out.println("\n=== TEST 6: HOMMES MARIÉS À 4 FEMMES ENTRE 1990-2010 ===");
            System.out.println("Résultat attendu : 1 homme (SAFI SAID)");
            Date dateDebut2 = createDate(1990, 1, 1);
            Date dateFin2 = createDate(2010, 12, 31);
            Long nbrHommes4Femmes = hommeService.countMenWith4WivesBetweenDates(dateDebut2, dateFin2);
            System.out.println("Nombre d'hommes mariés à 4 femmes : " + nbrHommes4Femmes);
            System.out.println();
            
            // Test 7: Afficher les mariages d'un homme avec tous les détails
            System.out.println("\n=== TEST 7: DÉTAILS DES MARIAGES DE SAFI SAID ===");
            System.out.println("Résultat attendu : 3 mariages en cours et 1 mariage échoué");
            hommeService.displayMariageDetails(hommes[0].getId());
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((AnnotationConfigApplicationContext) context).close();
        }
    }
    
    private static Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal.getTime();
    }
}