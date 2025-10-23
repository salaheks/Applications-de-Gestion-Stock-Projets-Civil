# Applications-de-Gestion-Stock-Projets-Civil
Ce référentiel contient trois applications de gestion développées en Java avec Spring Framework et Hibernate. Chaque application suit l’architecture DAO-Service avec Spring pour la gestion des transactions et Hibernate pour la persistance des données.

🗂 Applications Incluses
1️⃣ Gestion de l'État Civil (gestion-etat-civil/)

Application pour gérer les registres d’état civil : naissances, mariages, décès.

Fonctionnalités :

Gestion des femmes, hommes et mariages

Recherche de la femme la plus âgée

Comptage des enfants par période

Recherche des femmes mariées plusieurs fois

2️⃣ Gestion de Projets (gestion-projets/)

Application pour gérer les projets, équipes et tâches.

Fonctionnalités :

Gestion des employés, projets et tâches

Association employé-tâche avec dates de réalisation

Suivi des tâches réalisées par employé

Affichage des projets gérés par employé

3️⃣ Gestion de Stock (gestion-stock/)

Application pour gérer les produits, catégories et commandes.

Fonctionnalités :

Gestion des produits et catégories

Gestion des commandes et lignes de commande

Recherche de produits par critères

Affichage des produits commandés entre deux dates

🛠 Technologies Utilisées

Java 8/11

Spring Framework 5.3.21

Hibernate 5.6.15.Final

Maven

MySQL 8.0.33

HikariCP 5.0.1 (pool de connexions)

JUnit 4.13.2

🏗️ Architecture

Couches DAO-Service avec Spring et Hibernate

Annotations Spring : @Service, @Transactional, @Autowired, @Configuration

Gestion déclarative des transactions

Pool de connexions optimisé (HikariCP)

Tests unitaires et Spring Test

⚡ Installation et Lancement
Prérequis

Java 8+

Maven 3.6+

MySQL 8.0+ (port 3307)

Compilation
cd gestion-etat-civil/  # ou gestion-projets/ ou gestion-stock/
mvn clean compile

Exécution des Tests
mvn test

Lancement de l’Application

Via Maven :

mvn exec:java -Dexec.mainClass="ma.projet.Application"


Via Java :

java -cp target/classes:$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q) ma.projet.Application

🧪 Tests

SpringContextTest : Vérifie le contexte Spring

SpringHibernateTest : Test de l’intégration Spring + Hibernate

Tests unitaires des services avec injection de dépendances

✅ Migration Hibernate → Spring + Hibernate

Avant (Hibernate seul) :

Session session = HibernateUtil.getSessionFactory().openSession();
Transaction tx = session.beginTransaction();
try {
    session.save(entity);
    tx.commit();
} catch (Exception e) {
    tx.rollback();
} finally {
    session.close();
}


Après (Spring + Hibernate) :

@Service
@Transactional
public class MyService {
    @Autowired
    private SessionFactory sessionFactory;

    public boolean create(Entity entity) {
        Session session = sessionFactory.getCurrentSession();
        session.save(entity);
        return true;
    }
}


Avantages :

Transactions déclaratives

Injection automatique

Code plus lisible et maintenable

Tests simplifiés

🤝 Contribution

Fork le projet

Créez une branche feature :

git checkout -b feature/nouvelle-fonctionnalité


Committez vos changements :

git commit -m "Ajout nouvelle fonctionnalité"


Push vers la branche :

git push origin feature/nouvelle-fonctionnalité


Ouvrez une Pull Request

Standards de code :

Suivre les conventions Java

Utiliser les annotations Spring

Ajouter des tests pour les nouvelles fonctionnalités

Documenter les nouvelles méthodes
