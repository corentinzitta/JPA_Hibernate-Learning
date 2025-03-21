/* 
    Project Structure:
    1. Create a new Maven project in Eclipse
    2. Add these dependencies to pom.xml:

    ```xml
    <dependencies>
        <!-- Hibernate Core -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>5.6.15.Final</version>
        </dependency>
        
        <!-- H2 Database (for easy testing) -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>2.1.214</version>
        </dependency>
    </dependencies>
    ```
*/

// Entity Class: Book
package com.learnjpa.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "author", nullable = false, length = 100)
    private String author;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "price")
    private Double price;

    // Many-to-One relationship with Publisher
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    // Constructors
    public Book() {}

    public Book(String title, String author, LocalDate publicationDate, Double price) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.price = price;
    }

    // Getters and Setters
    // (Generate these in Eclipse using Source > Generate Getters and Setters)
}

// Entity Class: Publisher
package com.learnjpa.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // One-to-Many relationship with Book
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;

    // Constructors
    public Publisher() {}

    public Publisher(String name) {
        this.name = name;
    }

    // Getters and Setters
    // (Generate these in Eclipse)
}

// Hibernate Utility Class
package com.learnjpa.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
/* 
    // Hibernate Configuration File (src/main/resources/hibernate.cfg.xml)
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
    <hibernate-configuration>
        <session-factory>
            <!-- JDBC Database connection settings -->
            <property name="connection.driver_class">org.h2.Driver</property>
            <property name="connection.url">jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1</property>
            <property name="connection.username">sa</property>
            <property name="connection.password"></property>

            <!-- Hibernate settings -->
            <property name="hibernate.dialect">org.hibernate.dialect.H2Dialect</property>
            <property name="hibernate.show_sql">true</property>
            <property name="hibernate.hbm2ddl.auto">create-drop</property>

            <!-- Mapping classes -->
            <mapping class="com.learnjpa.model.Book"/>
            <mapping class="com.learnjpa.model.Publisher"/>
        </session-factory>
    </hibernate-configuration>
*/

// Main Application to Demonstrate JPA Functionalities
package com.learnjpa.main;

import com.learnjpa.model.Book;
import com.learnjpa.model.Publisher;
import com.learnjpa.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class BookStoreApp {
    public static void main(String[] args) {
        // Create and persist entities
        createPublisherAndBooks();

        // Demonstrate different JPA operations
        findBookById();
        updateBook();
        deleteBook();
        queryBooks();

        // Cleanup
        HibernateUtil.shutdown();
    }

    private static void createPublisherAndBooks() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Create Publisher
            Publisher publisher = new Publisher("Tech Books Inc.");
            session.save(publisher);

            // Create Books
            Book book1 = new Book("Java for Beginners", "John Smith", 
                LocalDate.of(2022, 1, 15), 39.99);
            book1.setPublisher(publisher);
            
            Book book2 = new Book("Advanced Hibernate", "Emily Johnson", 
                LocalDate.of(2021, 6, 20), 49.99);
            book2.setPublisher(publisher);

            session.save(book1);
            session.save(book2);

            transaction.commit();
            System.out.println("Books and Publisher created successfully.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    private static void findBookById() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Book book = session.get(Book.class, 1L);
            if (book != null) {
                System.out.println("Found Book: " + book.getTitle() + 
                    " by " + book.getAuthor());
            }
        }
    }

    private static void updateBook() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Book book = session.get(Book.class, 1L);
            if (book != null) {
                book.setPrice(44.99); // Update price
                session.update(book);
            }

            transaction.commit();
            System.out.println("Book updated successfully.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    private static void deleteBook() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Book book = session.get(Book.class, 2L);
            if (book != null) {
                session.delete(book);
            }

            transaction.commit();
            System.out.println("Book deleted successfully.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    private static void queryBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL Query to find books by publisher
            Query<Book> query = session.createQuery(
                "FROM Book b WHERE b.publisher.name = :publisherName", Book.class);
            query.setParameter("publisherName", "Tech Books Inc.");
            
            List<Book> books = query.getResultList();
            
            System.out.println("Books from Tech Books Inc.:");
            books.forEach(book -> System.out.println(
                book.getTitle() + " - $" + book.getPrice()));
        }
    }
}