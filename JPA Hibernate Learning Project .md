Exercise : JPA Hibernate Learning Project (a BookStoreApp)

# 1. Key features of this exercise

## 1. Entity Mapping

Two entities: Book and Publisher
Demonstrates different JPA annotations:

- @Entity
- @Table
- @Id
- @GeneratedValue
- @Column
- @ManyToOne
- @OneToMany

## 2. Relationship Mapping

Many-to-One relationship between Book and Publisher
Demonstrates how to set up and use relationships

## 3. CRUD Operations

Create: createPublisherAndBooks() method
Read: findBookById() method
Update: updateBook() method
Delete: deleteBook() method

## 4. Hibernate Configuration

Using an in-memory H2 database
Hibernate configuration file (hibernate.cfg.xml)
Utility class for session management

## 5. Query Demonstration

HQL (Hibernate Query Language) example in queryBooks()

## To set this up in Eclipse

1. Create a new Maven project
2. Copy the code into respective packages/files
3. Ensure Maven downloads the dependencies
4. Run the BookStoreApp class

## Exercises for you to try

1. Add more methods to query books
2. Implement error handling
3. Add more complex relationships
4. Create more sophisticated queries

# 2. Key information and structure of the JPA/Hibernate project

## Project Structure
The project is organized into several key packages and components:

### 1. Model Package (com.learnjpa.model)

Contains entity classes that represent database tables
Two main entities: Book and Publisher
Uses JPA annotations to define database mapping

### 2. Utility Package (com.learnjpa.util)

HibernateUtil class manages Hibernate SessionFactory
Provides a centralized way to create and access database sessions
Handles the initial setup and configuration of Hibernate

### 3. Main Package (com.learnjpa.main)

BookStoreApp contains the main application logic
Demonstrates various JPA/Hibernate operations

## Key JPA/Hibernate Annotations

### 1. @Entity

Marks a Java class as a persistent entity
Tells Hibernate to create a table based on this class


### 2. @Table

Allows customization of table details
Can specify table name, unique constraints, etc.


### 3. @Id

Marks the primary key of the entity
Unique identifier for each record


### 4. @GeneratedValue

Specifies how the primary key should be generated
IDENTITY strategy means the database will auto-increment the ID


### 5. @Column

Customizes column properties
Can set nullable, length, unique constraints


### 6. @ManyToOne and @OneToMany

Define relationships between entities
@ManyToOne: Many books can belong to one publisher
@OneToMany: One publisher can have many books

## Hibernate Configuration (hibernate.cfg.xml):

Defines database connection properties
Sets up dialect for specific database (H2 in this case)
Configures how Hibernate should interact with the database
hibernate.hbm2ddl.auto=create-drop means the database schema will be automatically created and dropped

## Main Application Methods:

### 1. createPublisherAndBooks():

Demonstrates creating and persisting entities
Shows how to set up relationships
Uses transaction management


### 2. findBookById():

Shows how to retrieve an entity by its primary key
Uses session.get() method


### 3. updateBook():

Demonstrates updating an existing entity
Shows the process of starting a transaction, modifying an object, and committing


### 4. deleteBook():

Shows how to remove an entity from the database
Demonstrates transaction management during deletion


### 5. queryBooks():

Uses HQL (Hibernate Query Language) to query entities
Shows how to filter results based on conditions
Demonstrates relationship-based querying

## Key Concepts Demonstrated:

- Object-Relational Mapping (ORM)
- Entity lifecycle management
- Transaction handling
- Relationship mapping
- Basic CRUD operations
- Query capabilities

## Dependencies:

- Hibernate Core: ORM framework
- H2 Database: In-memory database for easy testing