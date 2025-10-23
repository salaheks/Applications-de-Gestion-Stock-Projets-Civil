
## Project Overview

This is a Spring-based Java stock management application that demonstrates integration between Spring Framework, Hibernate ORM, and MySQL. The application manages computer products inventory with categories, products, orders, and order lines. It has been migrated from pure Hibernate to a Spring + Hibernate architecture.

## Build and Development Commands

```bash
# Build and run
mvn clean compile                                               # Clean and compile the project
mvn exec:java -Dexec.mainClass="ma.projet.test.TestGestionStock" # Run the main test application
mvn exec:java -Dexec.mainClass="ma.projet.test.SpringTestDebug"  # Debug Spring bean loading

# Testing and validation
mvn test                                                        # Run unit tests
mvn package                                                     # Package the application
```

## Architecture and Key Components

### Spring Configuration Architecture
- **SpringConfig**: Java-based configuration class using `@Configuration`
- **Component Scanning**: Scans `ma.projet` package for Spring-managed components
- **Data Source**: HikariCP connection pool configured for MySQL
- **Session Management**: Uses `LocalSessionFactoryBean` and `SessionFactory` beans
- **Transaction Management**: Declarative transactions with `@Transactional`

### Service Layer Pattern
All services implement the generic `IDao<T>` interface and are Spring-managed beans:

- **CategorieService**: Product category management
- **ProduitService**: Product management with specialized queries
- **CommandeService**: Order management
- **LigneCommandeService**: Order line management

Services use `@Autowired SessionFactory` for database access and `@Transactional` for transaction management.

### Entity Relationships
- **Categorie** ↔ **Produit**: One-to-many bidirectional relationship
- **Commande** ↔ **LigneCommande**: One-to-many relationship
- **Produit** ↔ **LigneCommande**: Many-to-one relationship

### Configuration Sources
- `src/main/resources/application.properties`: Database connection and Hibernate settings
- `ma.projet.config.SpringConfig`: Spring bean configuration
- Database: MySQL on localhost:3307, schema `gestion_stock`

## Critical Development Notes

### Spring Bean Injection
- Services must be obtained through Spring's `ApplicationContext.getBean()`
- **Never instantiate services with `new`** - this bypasses Spring's dependency injection
- Example correct usage:
  ```java
  ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
  CategorieService service = context.getBean(CategorieService.class);
  ```

### SessionFactory Configuration
- The project uses a two-bean approach: `sessionFactoryBean()` returns `LocalSessionFactoryBean`, `sessionFactory()` returns the actual `SessionFactory`
- Services inject the `SessionFactory` bean, not the `LocalSessionFactoryBean`
- Session access via `sessionFactory.getCurrentSession()` within `@Transactional` methods

### Database Requirements
1. MySQL server running on port 3307
2. Database `gestion_stock` must exist
3. Credentials: root/root (configurable in application.properties)
4. Tables are auto-created via `hibernate.hbm2ddl.auto=update`

### Testing and Debugging
- `TestGestionStock`: Comprehensive test demonstrating all functionality
- `SpringTestDebug`: Utility to inspect loaded Spring beans
- Always clean and recompile after service layer changes: `mvn clean compile`

### Common Issues
- **NullPointerException in services**: Usually indicates services were instantiated with `new` instead of Spring injection
- **NoSuchBeanDefinitionException**: Check that services have `@Service` annotation and are in scanned packages
- **Session errors**: Ensure methods are marked `@Transactional` and SessionFactory is properly injected
