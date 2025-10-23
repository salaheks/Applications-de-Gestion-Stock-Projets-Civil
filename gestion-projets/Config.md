
## Project Overview

This is a Spring + Hibernate project management application that tracks time spent on projects and calculates global costs. It uses Spring IoC/DI with annotation-based configuration, Hibernate ORM with JPA annotations, and MySQL database with HikariCP connection pooling.

## Common Commands

### Build and Compilation
```bash
mvn compile                    # Compile the project
mvn clean compile             # Clean and compile
```

### Running the Application
```bash
mvn exec:java -Dexec.mainClass="ma.projet.Application"           # Run main application
mvn exec:java -Dexec.mainClass="ma.projet.test.TestSimple"      # Run simple tests
mvn exec:java -Dexec.mainClass="ma.projet.test.TestGestionProjets" # Run comprehensive tests
mvn exec:java -Dexec.mainClass="ma.projet.DebugBeans"           # Debug Spring beans
```

### Testing
```bash
mvn test                      # Run JUnit tests
mvn test -Dtest=AppTest       # Run specific test class
```

## Architecture and Configuration

### Spring Configuration
- **Location**: `ma.projet.config.SpringConfig`
- **Type**: Java-based configuration with `@Configuration`
- **Component Scanning**: `@ComponentScan(basePackages = "ma.projet")`
- **Transaction Management**: `@EnableTransactionManagement` with HibernateTransactionManager
- **Properties Source**: `application.properties` for database configuration

### Database Configuration
- **Database**: MySQL running on port 3307
- **Connection Pool**: HikariCP with optimized settings
- **Schema Management**: `hibernate.hbm2ddl.auto=update`
- **Configuration File**: `src/main/resources/application.properties`

### Entity Relationships
```
Projet (1) ←--→ (N) Tache (N) ←--→ (N) Employe
                      ↑
                EmployeTache (junction table with real dates)
```

### Service Layer Architecture
All services use Spring's `@Service` annotation and `@Transactional` for transaction management:
- **ProjetService**: Project management with specialized display methods
- **TacheService**: Task management with named queries for business logic
- **EmployeService**: Employee management with project association queries
- **EmployeTacheService**: Manages employee-task assignments with actual dates

### Key Implementation Details

#### Spring Bean Resolution
Services are proxied by Spring for transaction management. When accessing beans programmatically:
```java
// Correct way to get proxied service beans
ProjetService projetService = (ProjetService) context.getBean("projetService");
```

#### SessionFactory Injection
Services use `@Autowired SessionFactory sessionFactory` to get current session:
```java
Session session = sessionFactory.getCurrentSession();
```

#### Transaction Boundaries
All service methods are `@Transactional`. Read-only operations should use `@Transactional(readOnly = true)`.

### Database Schema
- **Entities**: Projet, Tache, Employe, EmployeTache (all in `ma.projet.classes`)
- **Primary Keys**: Auto-generated using `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- **Relationships**: JPA annotations with proper fetch strategies
- **Date Handling**: `@Temporal(TemporalType.DATE)` for date fields

### Business Logic Patterns
- **Named Queries**: Used in TacheService for complex business queries (e.g., tasks > 1000 DH)
- **Custom Display Logic**: ProjetService.afficherTachesRealisees() formats output specifically
- **Date Range Queries**: Support for filtering tasks by realization dates
- **Employee-Project Association**: Complex queries joining through EmployeTache

### Testing Strategy
- **Unit Tests**: Basic JUnit tests in `src/test/java/com/example/AppTest.java`
- **Integration Tests**: Application-level tests in `ma.projet.test` package
- **Spring Context Tests**: Use actual Spring ApplicationContext for testing services

### Troubleshooting Common Issues
- **SessionFactory Null**: Ensure services are retrieved from Spring context, not instantiated directly
- **Transaction Issues**: Verify `@Transactional` annotations and proper transaction manager configuration
- **Connection Issues**: Check MySQL is running on port 3307 and credentials in application.properties
- **Bean Not Found**: Use bean names (lowercase) when accessing services programmatically

### Development Workflow
1. Modify entity classes in `ma.projet.classes` for schema changes
2. Update service classes in `ma.projet.service` for business logic
3. Test changes using Application.java or test classes
4. Database schema updates automatically via `hibernate.hbm2ddl.auto=update`
