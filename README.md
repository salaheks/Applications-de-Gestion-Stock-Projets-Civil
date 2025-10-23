# Applications-de-Gestion-Stock-Projets-Civil
📂 Aperçu du Référentiel

Ce référentiel contient trois applications de gestion développées en Java avec Spring Framework et Hibernate.

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

🛠️ Technologies Utilisées

Stack Principal :

Java 8/11

Spring Framework 5.3.21 (IoC, Transactions)

Hibernate 5.6.15.Final

Maven

MySQL 8.0.33

Dépendances Spring :

spring-context, spring-orm, spring-tx, spring-test

Autres outils :

HikariCP 5.0.1 (pool de connexions)

JUnit 4.13.2

MySQL Connector 8.0.33

🏗️ Architecture

Chaque application suit l’architecture couche DAO-Service :

├── classes/   # Entités JPA/Hibernate
├── dao/       # Interfaces DAO
├── service/   # Services métier (@Service + @Transactional)
├── config/    # Configuration Spring (@Configuration)
├── util/      # Utilitaires (ex: HibernateUtil)
└── test/      # Classes de tests


Configuration Spring :

Annotation-based (@Configuration, @ComponentScan)

Gestion déclarative des transactions (@EnableTransactionManagement)

Injection de dépendances automatique (@Autowired)

Configuration MySQL :

Port : 3307

Base de données :

gestion-etat-civil → gestion_etat_civil

gestion-projets → gestion_projets

gestion-stock → gestion_stock

HikariCP : pool optimisé pour les performances

Hibernate : hibernate.hbm2ddl.auto=create-drop (ou update pour prod)

⚡ Migration Hibernate → Spring + Hibernate

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
✅ Transactions déclaratives
✅ Injection automatique
✅ Pool HikariCP performant
✅ Tests simplifiés
✅ Code plus lisible et maintenable

🧪 Tests

SpringContextTest : Vérifie le contexte Spring

SpringHibernateTest : Test de l’intégration Spring + Hibernate

Tests unitaires des services

📌 Installation et Lancement

Prérequis :

Java 8+

Maven 3.6+

MySQL 8.0+ (port 3307)

Compilation :

cd gestion-etat-civil/  # ou gestion-projets/ ou gestion-stock/
mvn clean compile


Exécution des Tests :

mvn test


Lancement de l’Application :

Via Maven :

mvn exec:java -Dexec.mainClass="ma.projet.Application"


Via Java :

java -cp target/classes:$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q) ma.projet.Application

📝 Structure du Projet Exemple (gestion-etat-civil/)
gestion-etat-civil/
├── src/main/java/ma/projet/
│   ├── beans/        # Entités : Femme, Homme, Mariage
│   ├── service/      # Services Spring
│   ├── config/       # SpringConfig.java
│   └── Application.java
├── src/main/resources/
│   └── application.properties
└── pom.xml
