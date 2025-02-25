# Building RESTful WS with Spring Boot 3
Banuprakash C

Full Stack Architect, Corporate Trainer

Co-founder & CTO: Lucida Technologies Pvt Ltd., 

Email: banuprakashc@yahoo.co.in

https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/REST

===================================

Softwares Required:
1)  openJDK 17
https://jdk.java.net/java-se-ri/17

2) IntelliJ Ultimate edition 
https://www.jetbrains.com/idea/download/?section=mac

OR

Eclipse for JEE  
	https://www.eclipse.org/downloads/packages/release/2022-09/r/eclipse-ide-enterprise-java-and-web-developers

3) MySQL  [ Prefer on Docker]

Install Docker Desktop

Docker steps:

```
a) docker pull mysql

For Windows:
b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -t -i local-mysql bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

```

Introduction to Spring and Spring Boot.
RESTful WS
JPA -> MySQL
Cache, AOP, Validation, testing
HATEOAS,
Async, Events, Reactive
Security, MS

Spring and Spring Boot:
Dependency Injection --> SOLID Design Principle

Spring --> provides a lightweight container for Dependency Injection [Inversion Of Control container] using which we can build enterprise application

Bean: object instantiated by container and or managed by the container

Manage --> take care of life-cycle / wiring

Traditional:
```
Controller
    Service service = new AdminService();

AdminService
    Repo repo = MySQLRepo();
```

IoC Container:
MySQLRepo object is given to AdminService;
AdminService object is given to Controller

Simple Application:
```
interface BookRepository {
    void addBook(Book book);
}

class BookRepositoryDbImpl implements BookRepository {
     public void addBook(Book book) {
        // SQL insert into ...
     }
}

class BookRepositoryMongoImpl implements BookRepository {
     public void addBook(Book book) {
        // db.collections.insert(book)
     }
}
// OCP Principle; Closed for Change, Open for Extension
class AppService {
    private BookRepository repo ; // loose coupling

    public void setRepo(BookRepository repo) {
        this.repo = repo;
    }

    public void insertBook(Book b) {
        this.repo.addBook(b);
    }
}
```

XML as Metadata:
beans.xml

```
    <beans>
        <bean    class="pkg.BookRepositoryDbImpl" />
        <bean id="bookMongo" class="pkg.BookRepositoryMongoImpl" />
        <bean id="service" class="pkg.AppService">
            <property name="repo" ref="bookMongo" />
        </bean>
    </beans>
```

property name="bookRepo"  ==> service.setRepo(bookMongo);

```
// creates a Spring container with metadata present in "beans.xml"
main() {
ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
ApplicationContext interface for Spring Container

AppService service = ctx.getBean("service", AppService.class);
    service.insert(new Book(...))
}
```

Annotations as Metadata instead of XML:
1) Spring has pre-defined annotations when applied at class level will creates instances of the class
@Component
@Repository
@Service
@Controller
@RestController
@Configuration
@ControllerAdvice

2) Autowired annotation for wiring
```
interface BookRepository {
    void addBook(Book book);
}

@Repository
class BookRepositoryDbImpl implements BookRepository {
     public void addBook(Book book) {
        // SQL insert into ...
     }
}

@Service
class AppService {
    @Autowired
    private BookRepository repo ; // loose coupling


    public void insertBook(Book b) {
        this.repo.addBook(b);
    }
}
```
@Repository uses sql-error-codes.xml

```
    try {


    } catch(SQLException ex) {
        if(ex.getErrorCode() == 1062) {
            throw new DuplicateKeyException(...)
        }
    }

```

Interllij --> New Project --> Spring Initilizer --> JDK 17, Java and Maven, setup artifactId and groupId


```
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
</dependency>
```

Spring Boot Framework in built on top of Spring Framework
Spring Boot 2.x version is built on top of Spring Framework 5.x
Spring Boot 3.x version is built on top of Spring Framework 6.x

Why Spring Boot?
* Highly opiniated framework
* Lots of configurations comes out of the box

Example:
If we want to use RDBMS, Spring Boot configures Database connection pool
If we want to build web application, configures Tomcat Web Container,
If we want ORM application, configures Hibernate out of the box
It's easier to dockerize the application


```

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

SpringApplication.run is similar to

ApplicationContext ctx = new AnnotationConfigApplicationContext();
ctx.setBasePackage("com.adobe.demo");
ctx.refresh();

// for example if we have a class with @Service in "com.example" package, it's not scanned


@SpringBootApplication is 3 in one:
1) @ComponentScan("com.adobe.demo")
2) @EnableAutoConfiguration --> creating built-in objects based on requirement, like database connection pool, Embedded Tomcat Container, ...
3) @Configuration

Object --> demoApplication
```

Error:
Field bookRepo in com.adobe.demo.service.AppService required a single bean, but 2 were found:
	- bookRepoDbImpl
	- bookRepoMongoImpl

Solution 1:
Making one as @Primary
make one of them as @Primary

Solution 2:
using @Qualifier

```
@Service
public class AppService {
    @Autowired
    @Qualifier("bookRepoDbImpl")
    private BookRepo bookRepo;
```

Solution 3:
based on Profile, only beans which are eligible for the profile will be created within the container

```
@Repository
@Profile("dev")
public class BookRepoMongoImpl implements BookRepo{

@Repository
@Profile("prod")
public class BookRepoDbImpl implements  BookRepo{

how to choose profile:
a) application.properties
spring.profiles.active=dev

b) Command Line Arguments: higher priority compared to application.properties
More Run/Debug -> Modify Run Confuration
Active Profiles: prod

```

Solution 4:
ConditionalOnMissingBean
```
@Repository
//@Profile("dev")
@ConditionalOnMissingBean( name = "bookRepoDbImpl")
public class BookRepoMongoImpl implements BookRepo{

```
https://www.tutorialspoint.com/spring/spring_architecture.htm
https://www.tutorialspoint.com/spring/spring_ioc_containers.htm

Factory Method and Building RESTful WS with JPA

================

Recap day1:
Dependency Injection, Inversion Of Controller container
Spring Framework vs Spring Boot
Metadata --> XML or Annotation

Spring Container can be accessed using ApplicationContext interface
BeanFactory is also an interface using which we can access Spring Container

BeanFactory:
1) Bean instantiation
2) Wiring

ApplicationContext is a super-set of Bean Factory
1) Bean instantiation
2) Wiring
3) AOP
4) Multiple context [ containers]

Annotations at class-level: @Component, @Repository, @Service, ... [7 annotations]
@Autowired can be used to wire dependencies [ wiring by type, @Primary, @Qualifier, @Profile, @ConditionalOnMissingBean, @ConditionalOnProperty]

==========

Day 2:
* Scope of bean
1) Singleton by default [ only one bean of a class is present inside the container]
2) Prototype
@Scope("prototype")
A seperate bean is wired for each dependency

```
@Service
public class AppService {
    @Autowired
    private BookRepo bookRepo;

@Service
public class AdminService {
    @Autowired
    private BookRepo bookRepo;



3) RequestScope: applicable only for web application and not standalone apps
@RequestScope
or
@Scope("request")

one bean per request

@Repository
@RequestScope
public class BookRepoDbImpl implements  BookRepo{

4) SessionScope: applicable only for web application and not standalone apps
@SessionScope

one bean per session
Session: Conversational state of a client

@Repository
@SessionScope
public class BookRepoDbImpl implements  BookRepo{

```

Factory Method 
The Factory Method pattern suggests that you replace direct object construction calls (using the new operator) with calls to a special factory method. 

* Object creation is complex
* we need objects of 3rd party classes
* Spring instantiates classes if it contains above mentioed 7 annotations : @Component, @Repository, @Service, ... [7 annotations]

Maven Central repository — com.mchange:c3p0:0.10.1

we get "ComboPooledDataSource" class to create a database connection pool.

ComboPooledDataSource doesn't contain above mentioed 7 annotations
DataSource: Pool of database connection

Solution:
```
@Configuration 
public class AppConfig {

    // factory method; returned object is managed by Spring Container
    @Bean("postgres")
    public DataSource getSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
        cpds.setUser("swaldman");
        cpds.setPassword("test-password");

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds; 
    }

    @Bean("mysql")
    public DataSource getSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "com.mysql.Driver" ); //loads the jdbc driver
        ...
        return cpds; 
    }
}

@Repository
public class BookRepoDbImpl implements  BookRepo {
    @Autowired
    @Qualifier("postgres")
    DataSource postgresds; // connection pool is wired

     @Autowired
    @Qualifier("mysql")
    DataSource mysqlds; // connection pool is wired
}

```

Docker Desktop

docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql

mysql: image --> application; mysql@8.2.3 or mysql@latest -==> using tags
local-mysql: name of the container --> running within the docker 
container runs on port 3306 -> exposed as 3306 to application outside of container

====================

Java Persistence API :JPA

ORM -> Object Relational Mapping

```
    @Entity
    @Table(name="books")
    public class Book {
        @Id
        private String isbn;

        @Column(name="title")
        private String bookTitle;

        @Column(name="amount")
        private double price;
    }

```

Once Mapping is done; ORM frameworks helps in DDL and DML operations; simplifies

 public void addProduct(Product product)
    em.persist(product);
 }

 ORM Frameworks: Hibernate, TopLink, KODO, JDO, OpenJPA, EclipseLink, ....
Hibernate --> JBOSS --> RedHat
TopLink --> Oracle
KODO --> BEA --> Oracle
JDO --> Sun MS --> Oracle
OpenJPA --> Apache

JPA: Specification for ORM 

Below code is not required in Spring Boot, required if we are using Spring Framework
```
@Configuration 
public class AppConfig {

    // factory method; returned object is managed by Spring Container
    @Bean
    public DataSource getSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
        cpds.setUser("swaldman");
        cpds.setPassword("test-password");

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds; 
    }

    @Bean
    public EntityManagerFactory emf(DataSource ds) {
        LocalContainerEntityManagerFactory emf = new LocalContainerEntityManagerFactory();
        emf.setDataSource(ds); // which db pool to be used
        emf.setJpaVendor(new HibernateJpaVendor()); //which ORM to use
        emf.setPackagesToScan("com.adobe.prj.entity"); // where are my entities?
        ..
        return emf;
    }
}

@Repository
public class BookRepoDbImpl implements  BookRepo {
    @PersistenceContext
    EntityManager em;

    public void addBook(Book b) {
        em.persist(b);
    }
}
```


New Application with the following depdencies:
1) MySQL
2) Lombok [ reduce boilerplate code]
3) JPA: Spring Data JPA --> gives Hibernate as ORM an HikariCP for DB Connection pool


Spring Data JPA creates database connection pool based on entiries present in 
"application.properites" or "application.yml" file

https://docs.spring.io/spring-boot/appendix/application-properties/index.html

1) spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

ORM should generate SQL compatabile with MySQL

2) spring.jpa.hibernate.ddl-auto=update

update --> map class to existing table, it table doesn't exist create a new one, if required alter the table

create --> drop table on application exit, create a new one for every run of application [ useful only for test env]

validate -> map to existing tables. Don't allow alter or creation. [ Bottom to top approach]

RESTFUL_DB?createDatabaseIfNotExist=true

create database RESTFUL_DB;

=======

With Spring Data JPA we need just an interface extend Repository interface, implmentation classes are generated by the Spring Data JPA,
meaning no need for @Repository class which contains all basic CRUD operations

We can also write custom methods.

```
public interface ProductRepository extends JpaRepository<Product, Integer> {
}

intenally a class for this interface is created by Spring Data JPA

JpaRepository<Product, Integer>
Product --> which entity to manage
Integer --> PK is a int type of data

Return type is Optional
 Optional<T> findById(ID id);
```

CommandLineRunner
Interface used to indicate that a bean should run when it is contained within a SpringApplication.
* Run as soon as the Spring container is created and intialized

```

 % docker exec -it local-mysql bash
bash-4.4# mysql -u root -p
Enter password: 

mysql> use RESTFUL_DB


Database changed
mysql> show tables;
+----------------------+
| Tables_in_RESTFUL_DB |
+----------------------+
| products             |
+----------------------+
1 row in set (0.00 sec)

mysql> select * from products;
+----+-----------+---------+----------+
| id | name      | price   | quantity |
+----+-----------+---------+----------+
|  1 | iPhone 16 |   98000 |      100 |
|  2 | Wacom     | 4500.99 |      100 |
+----+-----------+---------+----------+
2 rows in set (0.00 sec)

```

JPARepository CRUD operations for INSERT, DELETE has Transaction enabled.
When we write custom APIs for INSERT, DELETE or Update we need to explicitly enable Transactions

```
@Transactional
public Product updateProduct(int id, double price) {
        productRepository.updateProduct(id, price);
        return  getProductById(id);
}
```

Building RESTful WS

REST --> REpresentational State Transfer --> Architectural style for distibuted hypermedia systems.

Resource: Any info present on server can be a resource: database, file, image, printer

Representation: State of resource at a given point of time served in various formats like JSON / XML / CSV

Content negotiation: Asking for a suitable presentation by a client

Best Practices:
1) Use nouns to represent resources
2) Collection type of resources
 A collection resource is server-manged directory of resources [ like products]
 Clients may propose new resources to be added to a collection
 3) Store type of resources
 A Store is client-managed resource repositry.
 Like Cart
 Playlist
 https://spotify/users/banuprakash/playlists

4) Controller --> Actions / executable functions
Procedural concept
 https://spotify/users/{id}/playlists/play

5) Use hyphens (-) to improve readability, Avoid underscores
6) use lowercase in URIs
7) use query components for filter URI collection
https://server.com/products?category=mobile
https://server.com/products?page=1&size=25

8) use Path parameter to get a resource based on ID
https://server.com/products/4
 get a product whose id is 4

Resource Representation constits of:
1) data
2) metadata describing the data
3) hypermedia links [level 2 ]

=============

guiding principles of REST
1) Uniform Interface
2) client - server: Seperation of concerns, client and server code can evolve seperataly
3) Stateless: No Conversational state of client. client has to pass his/her info for every request. server doesn't hold any conversational state
4) Cacheable
5) Layered System

====================

RESTful , Validation, Exception handling,
JPA --> add more tables with Relationship

Day 2 Recap: JPA --> ORM --> JDBC --> Database
Repository --> JpaRepository / MongoRepository

```
Spring Data JPA generates @Repository class for the interfaces provided
interface EmployeeRepository extends JpaRepository<Employee, String> {
    // provides basic methods for CRUD operations
    // custom methods
    Projections or @Query [native Query or JP-QL]

    // any custom method we write for INSERT, DELETE and UPDATE needs to be @Transactional
}

```

Low level JDBC has two methods 
1) executeQuery(SELECT SQL) --> ResultSet
2) executeUpdate(INSERT, DELETE, UPDATE SQL) @Modifying -> int

==========

Day 3:
Building RESTful WS

```
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Web Server


Including web depenecies gives the application web capabilites to build traditional web application
or RESTful web application, Simply put it adds Spring MVC Module
1) Adds Tomcat as Servlet Container / Servlet engine / Web Container
Servlet engine: to run applications built using Java Technologies on web server
Servlet are server side java objects

Alternate to Tomcat we have Jetty, Netty, ...

2) Content negotiation handler for JSON is already available by adding Jackson library
Java <--> JSON 

Alternate to Jackson: Jettison, GSON, Moxy

If we need Java<---> XML explictly libraries has to be added and configured....

3) DispatcherServlet: servlet which works as FrontController, intercepts all requests coming from client

4) HandlerMapping: key/value pair to identify which class and method to invoke based on URL

=======

```

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private OrderService service;


    @GetMapping()
    public List<Product> getProducts() {
        return service.getProducts();
    }

    @PostMapping()
    public String addProduct(@RequestBody Product p) {
        return service.addProduct(p);
    }
}


````

POST http://server/api/products
Content-type: application/json
{
    "name": "A",
    "price": 630.121,
    "quantity": 300
}


@RequestBody is required to convert payload into Java Object, because to identify difference between Path paramter and Query Parameter


Note: Handler Mapping will scan only classes which have @RestController / @Controller for request mapping

@Controller is for traditional web applications which return Pages/presentation like HTML or PDF
@RestController is for returning representation of data in various format like JSON / XML / CSV ...


CRUD operations and REST 

GET - ReAD
POST -> CREATE a new record
PUT / PATCH --> UPDATE a record
PUT is for major update, almost all the fields based on ID. Send full Object as payload
PATCH is for partial update, one or two fields, can send using Request Param [ Query]
DELETE - DELETE --> avoid for collections, can be used for store [user managed]

POSTMAN download to test REST endpoints.

===========================

JSON PATCH

```
Complex nested data

public class Employee {
    int id;
    String title;
    List<String> skills = new ArrayList<>();
    Map<String, String> communication = new LinkedHashMap<>();
}

updating this Employee is too complex
May need to add skills, remove a skill, add skill in between

skills ==> [JAVA, AWS] ==> [JAVA, SPRING BOOT, AWS] 

communication
mobile: ...
email: ....


{
    "message": "Hello World",
    "from": "Unknown"
}

JSON PATCH payload is array of operations: add, move, remove, replace

[
    { "op": "replace", "path": "/message", "value": "Patching JSON is fun" },
    { "op": "add", "path": "/with", "value": "jsonpatch.me" },
    { "op": "remove", "path": "/from" }
]
```

Add few customers:
mysql> insert into customers values ('smitha@adobe.com', 'Smitha', 'Patil');


mysql> insert into customers values ('roger@adobe.com', 'Roger', 'Smith');


mysql> insert into customers values ('rita@adobe.com', 'Rita', 'Rao');

https://martinfowler.com/bliki/DomainDrivenDesign.html


@JoinColumn introduces FOREIGN KEY
1) FK will be in owning table if used with @ManyToOne and @OneToOne
2) FK will be in child side / other side with @OneToMany

=======

Order without Cascade:
Assume one order has 4 items
```
@OneToMany
@JoinColumn(name="order_fk")
private List<LineItem> items = new ArrayList<>();

To save we need:
orderRepo.save(order); 
itemRepo.save(i1);
itemRepo.save(i2);
itemRepo.save(i3);
itemRepo.save(i4);

To Delete we need:
orderRepo.delete(order); 
itemRepo.delete(i1);
itemRepo.delete(i2);
itemRepo.delete(i3);
itemRepo.delete(i4);

```

Order With Cascade:
Assume one order has 10 items
```
@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name="order_fk")
private List<LineItem> items = new ArrayList<>();

To save we need:
orderRepo.save(order);  // takes care of saving all items of order
To Delete we need:
orderRepo.delete(order); // takes care of deleting all items of order

```

EAGER and LAZY Loading of children.
```
1) By default One to many is LAZY loading; many To one is EAGER Fetching
@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name="order_fk")
private List<LineItem> items = new ArrayList<>();

orderRepo.findAll(); // select * from orders; items are not fetched

2) EAGER Fetching
 @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="order_fk")
    private List<LineItem> items = new ArrayList<>();

   orderRepo.findAll(); // select * from orders;  also items of orders are also fetched 
```

Genarated SQL:

```
 create table line_items (item_id integer not null auto_increment, amount float(53) not null, qty integer not null, product_fk integer, order_fk integer, primary key (item_id)) engine=InnoDB
create table orders (oid integer not null auto_increment, order_date datetime(6), total float(53) not null, customer_fk varchar(255), primary key (oid)) engine=InnoDB
alter table line_items add constraint FK7bcmyaf081a54pqagiuo2boo foreign key (product_fk) references products (id)
alter table line_items add constraint FKjvi2gypwgl46v67xa2bgqp0uj foreign key (order_fk) references orders (oid)
 alter table orders add constraint FKlctjwy900y7l1xmwulg4rkeb3 foreign key (customer_fk) references customers (email)
```

Validation of User input, Exception Handling

AOP: Aspect Oriented Programming
AOP is to eliminate Code tangling and Code scattering in application
Cross Cutting Concerns: aspect of code like logging, security, transaction, ... leads to code scatterning and code tangling

```
public void transferFunds(Account fromAcc, Account toAcc, double amt) {
    log.debug("begin Transaction");
    try {
    if(role.isAdmin()) {
        transaction.begin();
            // withdraw from fromACC
            log.debug("amount withdraw..");
            // deposit into toAcc
        transaction.commit();
     log.debug("Complete Transaction");
    }
    } catch(SQLException ex) {
        transaction.rollback();
         log.debug("Error in Transaction");
    }
}
```

Aspect: bit of code which generally leads to code tangling and scattering like logging, security, transaction
JoinPoint: place in your code where aspect can be weaved
In Spring methods and exceptions are eligible as JoinPoint
PointCut: selected JoinPoint
Advice: how aspect can be weaved like before, after, around, afterThrowing , afterReturning


Day 4:

Recap:
RESTful WS ProductController, OrderController
GET, POST, PATCH, JSON-PATCH, DELETE and PUT
PathVariable for Path Parameter /
RequestParam to handle Query parameters ?
@RequestBody is to convert payload to Java object
@ResponseBody is optional --> to convert retruned Java object to response body in various format based on Accept header.

Association Mapping :
@OneToMany, @ManyToOne, @JoinColumn, Cascade, EAGER vs LAZY FETCHING.

Day 4:
AOP: Aspect, JoinPoint [ where all aspects can be weaved, Spring limitation is we can have methods and exception, Dynamic Aspects are not possible], PointCut [ selected Joinpoint], Advice [ how we weave like before, after, afterThrowing, around, afterReturning]

https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/pointcuts.html

=====

Annotations: metadata.
1) Who uses it?
    a) COMPILER
    b) CLASSLOADER
    c) RUNTIME [ JRE ]
2) Where can I use it?
    a) FIELD
    b) METHOD
    c) TYPE [ class / interface / annotation]
    d) PARAMETER

==========

http://localhost:8080/api/products/2 
STATUS CODE -> 200

http://localhost:8080/api/products/234
SCODE --> 500 should have been 404 RESOURCE NOT FOUND

Expected: Product with Id: 234 doesn't exist!!!

400 series is client side errors
500 series is SERver side Error

If Exceptions are thrown from Controller / RestController a special type of Aspect called as ControllerAdvice [afterThrowing] gets invoked.

@ControllerAdvice  is internally @AfterThrowing advice but HttpServletRequest and HttpServletResponse aware

ResponseEntity: Response data + headers

Validation:
```
  <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
    private int id;

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value=10, message = "Price ${validatedValue} should be equal or more than {value}")
    private double price;

    @Min(value=1, message = "Quantity ${validatedValue} should be equal or more than {value}")
    private int quantity;

ProductController:
public Product addProduct(@RequestBody @Valid Product p) {


MethodArgumentNotValidException: 
Validation failed addProduct(com.adobe.orderapp.entity.Product) with 2 errors: 

[Field error  default message [Price 0.0 should be equal or more than 10]] 

[Field error default message [Quantity -100 should be equal or more than 1]] 

BindingResult is an interface which dictates how the object that stores the result of validation should store and retrieve the result of the validation
```

Unit Testing: Testing in Isolation.
Unit Testing Frameworks: JUnit, TestNG

Dependency:
Controller --> service --> Repository --> Database

To Test Service we need to mock Repository
To test Controller we need to mock Service.

MockAPI: Mockito, EasyMock, JMock, ...

spring-boot-starter-test by default includes:
1) Junit-jupeter as Unit testing framework
2) Mockito for mocking
3) Hamcrest for collection assertion
4) json-path to validate json
https://jsonpath.com/


@WebMvcTest loads only MVC relavent beans [link TestDispatcherServlet , HandlerMapping] within Spring Container, other beans like @Service,
@Component, @Respoistory ... will not be loaded within the spring container

MockMvc is made avaialble using which we can make API calls GET / POST ..

@WebMvcTest(ProductController.class) --> loads only ProductController into Container


=============================================
Pending Projections from RestController.

Caching

* Client Side Caching
* Server Side Caching
* Middle tier Caching


Client Side Caching:
* Cache-Control
* ETag

How can we generate ETag?
1) using hashCode() of entity
2) if JPA is used to map entity to database table we can use @Version column [ db column]
 @Version
 private int version;

 intially we will have the value of version in db as 0.
 any update we do, JPA increments it by 1
 Make sure mutations happens only thro JPA

 Also this will help in handling Concurrency.

Data Corruption:
```
 3 | LG AC        |  54000 |       100 

 Client 1: buys 5
READ
 3 | LG AC        |  54000 |       100 

    updates qty to 95

 client 2: buys 10

READ
 3 | LG AC        |  54000 |       100 

    updates qty to 90

final product can be 
 3 | LG AC        |  54000 |       90
 OR
3 | LG AC        |  54000 |       95

```

With Version:
```
3 | LG AC        |  54000 |       100  | 0

 Client 1: buys 5
READ
 3 | LG AC        |  54000 |       100 | 0

    updates qty to 95

    update products set quantity = 95, version = version + 1 where id = 3 and version = 0

client 2: buys 10

READ
 3 | LG AC        |  54000 |       100 |0

    updates qty to 90
     update products set quantity = 90, version = version + 1 where id = 3 and version = 0

final product can be 
 3 | LG AC        |  54000 |       90
 OR
3 | LG AC        |  54000 |       95
Only Tx is possible, other tx fails
But first commit wins, second commit is failed
```

GET http://localhost:8080/api/products/etag/2
Accept:application/json

Response Header:
HTTP/1.1 200 
ETag: "48005640"
Content-Type: application/json

===================================

Server Side Caching:
1) Database level using ORM
Ehcache ==> ORM will fetch from database and persist the data in files [generally]
SwarmCache Hibernate configuration

2) Web application level Cache [Spring container level]
```
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
 </dependency>
```

By default it gives ConcurrentMapCache [key / value] --> In memory Cache and CacheManager

Need to enable @EnableCaching --> any @Configuration file
```
@SpringBootApplication
@EnableCaching
public class OrderappApplication {
```
Cache the returned value:
  @Cacheable(value = "productCache", key = "#id")

Update the Cache:
 @CachePut(value = "productCache", key="#id")

Remove from Cache:
@CacheEvict(value = "productCache", key="#id")

Better evicting should be scheduled
@EnableScheduling
public class OrderappApplication {

    https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions

Configure Redis as CacheManager..

==
Redis as Cache Manager, HATEOAS, Spring Data Rest, RestTemplate, RestClient, Async,





==================


Day 4 Recap:
AOP: PointCut by expression or by Annotation, @Transactional, @ControllerAdvice: Any exceptions thrown from @Controller or @RestController @ControllerAdivce catches are writes custom data back to client.
EntityNotFoundException
validation: @Valid --> MethodArgumentNotValidException

UnitTesting by Mocking Service tier code.

Caching:ETag and ConcurrentMapCache [ @ConditionalOnMissingBean ]
@EnableCache, @Cacheable, @CachePut, @CacheEvict [Avoid this, use @EnableScheduling and @Scheduled]


Day 5:
Redis Container:
docker run -d --name=some-redis -p 6379:6379 redis

Redis:
```
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
            <version>3.4.0</version>
        </dependency>
```

By including above dependency RedisCacheManager will be available

class Product implements Serializable 

Redis Client: nodejs

$ npx redis-commander
http://127.0.0.1:8081/

=====================================================

Messages for i18n
```
messages.properties --> default file for messages [ could be success or error messages]
Product 
   @NotBlank(message = "{NotBlank.product.name}") // from properties file
//  @NotBlank(message = "Name is required")
    private String name;


POST http://localhost:8080/api/products
Accept:application/json
Content-Type:application/json
Accept-Language: fr
 messages_fr.propertes

Configure ReloadableResourceBundleMessageSource --> done in MessageConfig

```
https://martinfowler.com/articles/richardsonMaturityModel.html

HATEOAS [Level 3 RESTful]
HATEOAS stands for Hypermedia as the Engine of Application State. 
It's a principle that guides how clients and servers interact in a RESTful environment.

How does HATEOAS work?
* HATEOAS includes hypermedia links in API responses. 
* This allows clients to navigate and interact with resources without needing to know the API structure in advance. 
* HATEOAS provides information dynamically, enabling clients to find available actions and resources. 

Benefits of HATEOAS
* Increases discoverability of RESTful APIs [Else we need to expose REST API docs]
* Allows clients to interact with network applications without hard-coded knowledge of the API structure 

Good to have for applications like Patient-Doctor appointmint, StarBucks, Swiggy or any ecommerce sort of application.

```
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-hateoas</artifactId>
            <version>3.4.0</version>
        </dependency>
```
WebMvcLinkBuilder: Programatically adding links to the ResponseEntity
Alternativley use Spring-data-rest, which automates adding links

RepresentationModel can be CollectionModel [collection + links], EntityModel[ entity + links]
https://www.iana.org/

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL_FORMS)
Affordance

======================

Spring Data REST:
Spring Data REST is part of the umbrella Spring Data project [JPA ,Mongo] and makes it easy to build hypermedia-driven REST web services on top of Spring Data repositories [JpaRepository, MongoRepository]
Note: 
1) no need for explicilty configuring WebMvcLinkBuilder
2) By default it creates endpoints for Spring Data repositories [ no need for @RestController]

MicroService for Product CRUD --> Spring Data REST
MicroService for Ordering --> Spring REST web

Spring Data REST and Web can't co-exist
Spring Data REST uses @BasePathAwareController in case if we need explict endpoints,
can't use @RestController

New Project with mysql, jpa, rest repositories, lombok

Create entity and JpaRepository, no need for Service or Controller tier code

http://localhost:8080/products
http://localhost:8080/products?page=0&size=2
http://localhost:8080/products/search/findByQuantity?quantity=100
http://localhost:8080/products/search/getByRange?l=10000&h=500000

spring.data.rest.base-path=/api
http://localhost:8080/api/products

Suppose if we need customization use @BasePathAwareController
=========

Async operations in Spring Boot

Rest Clients: Consuming REST Api in Java based applications:
1) RestTemplate
2) WebClient
3) RestClient [ Spring boot 3+ ]

Assume we are building a application for Discharing a Patient:

// synchronous code --> Blocking sequential execution

```
    public String dischargePatient(String patientId, String patientName) {
        billingProcess.processBill();
        medicalRecordsService.updatePatientHistory(); // discharge summary
        houseKeepingService.cleanAndAssign();
        notificationService.notifyPatients();
        // s1
        // s2
        return "Patient is discharge ...";
    }

```

Solution: Use Events

Project with "web" dependency

@EnableAsync on any @Configuration class

@Async --> execute the method by using Thread from Thread-pool

Declarative HTTP client using HttpExchange

HttpExchange can be used with RestTemplate / RestClient or WebClient


Apache Benchmark:

ab -c 10 -n 100 http://localhost:8080/api/posts

===================

Recap:

HATEOAS, WebMvcLinkBuilder, Spring Data REST on top of Data Repositories like MongoRepository/ JpaRepostiory, @BasePathAwareController.

Asynchronous programming: @EnableAsync, @Async, ApplicationEvent, @EventListener 

=================

Day 6:

Monitoring:
* Notify that the system is at fault
* Focuses on the collection of data
* Discovering faults

Spring Boot Actuator is a module that provides production-ready features to monitor and manage appliciton.
It provides endpoints and metrics for monitoring

```
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
 </dependency>

 application.properties

management.endpoint.health.show-details=always
#management.endpoints.web.exposure.include=health,metrics,info,prometheus
management.endpoints.web.exposure.exclude=
management.endpoints.web.exposure.include=*
management.metrics.distribution.percentiles-histogram.http.server.requests=true

http://localhost:8080/actuator
http://localhost:8080/actuator/metrics
http://localhost:8080/actuator/metrics/http.server.requests
http://localhost:8080/actuator/metrics/jvm.threads.live

```

https://prometheus.io/
Prometheus is an open-source systems monitoring and alerting toolkit 
Prometheus collects and stores its metrics as time series data, i.e. metrics information is stored with the timestamp at which it was recorded, alongside optional key-value pairs called labels.

scrapes data from endpoint and stores it as time series data

Grafana : Generate Dashboard
``
  <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-prometheus</artifactId>
            <scope>runtime</scope>
        </dependency>
Docker Compose:
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-docker-compose</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

    looks for compose.yml file in your application and runs docker compose up

http://localhost:9090/
http_server_requests_seconds_count
```

Spring Docs --> Documentation of RESTful Endpoints
RAML or OpenAPI

```
  <!-- OpenAPI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
               <version>2.5.0</version>
        </dependency>

http://localhost:8080/v3/api-docs
http://localhost:8080/swagger-ui/index.html

```

Reactive Programming:
Paradigm concerned with data streams and propagation of change

HTTP Protocol --> PULL Protocol

Subscription

request(n) / cancel()

onNext(data)


5. OnComplete() / OnError()

======

Spring Boot reactive module uses Netty instead of Tomcat as web container.
Netty server is event based, Tomcat and Jetty are Thread based container

Tomcat and Jetty are Thread based container are good for CPU intense operations

Netty or and event based Web containers are good for stream base non-blocking IO operations
Example: Netflix or any OTT platform. Admin Dashboards for KPI , Monitoring dashbaords
https://dzone.com/articles/spring-webflux-eventloop-vs-thread-per-request-mod

Spring Boot Reactive module provides 2 types of publishers Flux [0...n] / Mono [0..1]

Scenario 1: Cold Publisher
```
    // method to stream a movie

    String<String> getMovie(String name) {
        return Stream.of("Scene 1", "Scene 2" , "Scene 3" ...)
    } 

    // each scene will play for 2 seconds
    Flux<String> netFlix = Flux.fromStream(() -> getMovie(name))
        .delayElements(Duration.ofSeconds(2));

    // User 1 watching
    netFlix.subscribe(scene -> System.out.println("User 1 Watching " + scene));

    // user 2 joins later, but still gets all the scene --> Cold Publisher
       netFlix.subscribe(scene -> System.out.println("User 1 Watching " + scene));

```
Scenario 2: Hot Publisher
```
  Flux<String> movieTheatre = Flux.fromStream(() -> getMovie(name))
        .delayElements(Duration.ofSeconds(2)).share(); // starts publishing even without and subscribers


    // User 1 watching
    movieTheatre.subscribe(scene -> System.out.println("User 1 Watching " + scene));

    // user 2 joins later,  looses prev scene
       movieTheatre.subscribe(scene -> System.out.println("User 1 Watching " + scene));
```

For Reactive Spring boot application use "webflux" [ Netty] instead of "web" module [Tomcat]
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

time blocking code: 10032 ms
time non-blocking code: 1 ms

Reactive database:
R2DBC stands for Reactive Relational Database Connectivity, a specification to integrate SQL databases using reactive drivers.

spring.data.r2dbc.repositories.enabled=true
spring.r2dbc.url=r2dbc:h2:mem://./testdb

automatically spring data executes these files
resources/schema.sql --> DDL 
resources/data.sql --> DML

instead of JpaRepository --> R2dbcRepository [ ReactiveCrudRepository ]
public interface UserRepository extends  R2dbcRepository<UserEntity, String> {

===================

Some Databases like no-SQL MongoDB supports tailable cursor

Tailable Cursors
By default, MongoDB automatically closes a cursor when the client exhausts all results in the cursor. 
However, for capped collections you can use a tailable cursor that remains open after the client exhausts the results in the initial cursor. 

========

New Spring boot application: lombok, reactive web , reactive mongoDB

docker run --name some-mongo -p 27017:27017 -d mongodb/mongodb-community-server:latest

docker exec -it some-mongo
# mongosh

default is "test" database
test> db.createCollection("movies", {capped:true, size:1234567, max:100})

With Web FLux we can have functional Routes instead of @Controller or @RestController

```
  return RouterFunctions.route()
                .GET("/router/customers", handler::loadCustomers)
                .GET("/router/customers/stream", handler::loadCustomerStream)
                .GET("/router/customer/{input}", handler::findCustomers)
                .POST("/router/customer", handler::saveCustomer)
                .build();
```

Security and Intro to MicroService

=========

Day 7

Recap:
* @EnableCaching
* @EnableScheduling
* @EnableAsync --> Thread pool to be used with @Async placed on the method to specify that the method has to execute in a seperate thread.
ApplicationEvent @EventListener, future & CompletableFuture

Reactive Programming: based on stream and event emitting
Flux and Mono are pre-defined publishers webflux module --> Netty Web Server instead of Tomcat Web Server
Cold Publisher, Hot Publisher [share()]
R2DBC [Reactive] should be used instead of JDBC [blocking code]
ReactiveMongoRepository --> Capped Collection supports TailableCursor

Flux.zip to combine Publisher [ interval]

Instead of @RestController we can use functional Routes.

-----------------------------

Projections
OrderRepository: findAll() gives Order, Customer and LineItem data, Product

Order --> Customer is ManyToOne --> Default is  EAGER fetching
Order --> LineItem is OneToMany --> default is LAZY, but we made it as EAGER
LINEITEM --> Product is ManyToOne --> default is EAGER

I need data for report :

oid | order_date | first_name | email | total

Solution: write SQL or JP-QL to Join the tables.

```
   
//    @Query(value = "select o.oid,o.order_date, c.first_name, " +
//            "c.email, o.total from orders o left outer join on customers c " +
//            "where orders.customer_fk = c.email", nativeQuery = true)
//    @Query("select o.oid, o.orderDate, c.firstName, c.email from Order o join o.customer c")
//    List<Object[]> getReport();

    @Query("select new com.adobe.orderapp.dto.ReportDTO(o.oid, o.orderDate, c.firstName, c.email) from Order o join o.customer c")
    List<ReportDTO> getReport();
```

Spring Security

Authentication and Authorization

Project with Web and Security

```
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
 </dependency>
```

By including above depenendecy 
1) we made all resources protected.
2) gives you login and logout pages
3) creates one user with username ="user" and generated password
Using generated security password: 07b743c0-ce4a-4a7c-a756-8e8071e65fda
http://localhost:8080/admin
http://localhost:8080/hello

http://localhost:8080/logout


DAO Security
```
Connection Pool
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.32</version>
        </dependency>
```

https://docs.spring.io/spring-security/reference/servlet/appendix/database-schema.html

https://bcrypt-generator.com/

=========

RESTful has to be Stateless.

Using Tokens for Authorization.
https://jwt.io/

HEADER.
PAYLOAD. --> contains claims like principle, IAT, EXP, ISS, AUTHORITES
SIGNATURE --> HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
your-256-bit-secret
)

eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c


Adding Security using JWT to orderapp:
1) dependencies:
for security and JWT:

2) User <---> ROLE entities many to many relationship [ JPA for Security]
3) UserDao
4) UserDetailsServiceImpl
5) application.properties
jwt.secret=TopSecretValuetobeUsedForMyApplicationusingSpringBootBoom

6) JwtService to generate a token and validate the token
7) AuthenticationService [ service to register and login]

http://server.com/resource
Authorization: Bearer <<token>>
8) SecurityConfig
9) JwtAuthenticationFilter


eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbm5hQGFkb2JlLmNvbSIsImlhdCI6MTczNzk1OTYwOCwiZXhwIjoxNzM3OTYxMDQ4LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiLCJST0xFX0FETUlOIl0sInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaXNzIjoiaHR0cHM6Ly9hdXRzZXJ2ZXIuYWRvYmUuY29tIn0.J29G82e5tjv30n9pRlfagZPeFmsro4gcHtXs7a7FgrM
