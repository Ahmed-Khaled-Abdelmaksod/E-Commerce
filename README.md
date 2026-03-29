# E-Commerce Platform

A comprehensive Java-based e-commerce web application built with Jakarta EE, Hibernate, and MySQL. This platform enables customers to browse products, manage shopping carts, place orders, and allows administrators to manage the catalog.

## 📋 Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Database Configuration](#database-configuration)
- [Build & Run](#build--run)
- [Deployment](#deployment)
- [API Overview](#api-overview)
- [Contributors](#contributors)

## 🎯 Overview

This E-Commerce Platform is a full-featured web application designed to handle online retail operations. It provides a user-friendly interface for customers to shop online, manage their carts, and place orders, while offering administrators tools to manage products, categories, and customer orders.

## 🏗️ Architecture

The application follows a **Layered Architecture** pattern with clear separation of concerns:

```
┌─────────────────────────────────────────┐
│         UI Layer (JSP Views)            │
├─────────────────────────────────────────┤
│      Controllers (HTTP Handlers)        │
├─────────────────────────────────────────┤
│       Service Layer (Business Logic)    │
├─────────────────────────────────────────┤
│     DAO Layer (Data Persistence)        │
├─────────────────────────────────────────┤
│     Database (MySQL)                    │
└─────────────────────────────────────────┘
```

### Key Architectural Components:

- **Controllers**: Servlet-based HTTP request handlers for routing requests to appropriate services
- **Services**: Business logic layer for processing operations like orders, cart management, user authentication
- **DAO (Data Access Objects)**: Database interaction layer using Hibernate ORM
- **Entities**: JPA-mapped entity classes representing database tables
- **DTOs**: Data Transfer Objects for API communication
- **Filters**: Request filtering for security and preprocessing
- **Mappers**: Object mapping between entities and DTOs
- **Utilities**: Helper functions and common utilities

## 🛠️ Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Language** | Java | 23 |
| **Framework** | Jakarta EE | 6.0 |
| **ORM** | Hibernate | 6.6.1 |
| **Server** | Apache Tomcat | 10+ |
| **Database** | MySQL | 8.0+ |
| **Build Tool** | Maven | 3.6+ |
| **Connection Pool** | HikariCP | 5.1.0 |
| **JSON Processing** | Gson | 2.10.1 |
| **Templating** | JSP/JSTL | 3.0 |
| **Testing** | JUnit Jupiter | 5.11.0 |
| **Logging** | SLF4J | 1.7.36 |

## ✨ Features

### Customer Features
- **User Authentication**: Secure sign-up and sign-in with password protection
- **Product Browsing**: View all products with filtering by category
- **Product Search**: Search for products by name or category
- **Shopping Cart**: Add/remove items, update quantities, persistent cart management
- **Order Placement**: Checkout process with order confirmation
- **Order History**: View past orders and order details
- **User Profile**: Manage personal information and address details

### Admin Features
- **Product Management**: Add, edit, delete products
- **Category Management**: Organize products into categories
- **Order Management**: View and manage customer orders
- **Admin Dashboard**: Overview of sales and inventory
- **Admin Profile**: Manage admin account settings

### System Features
- **Session Management**: Secure session handling for user persistence
- **Error Handling**: Comprehensive error handling and user feedback
- **Data Validation**: Input validation on both client and server-side
- **Security Filters**: Request filtering for authentication and authorization

## 📁 Project Structure

```
e-commerce/
├── src/
│   ├── main/
│   │   ├── java/gov/iti/jets/ecommerce/
│   │   │   ├── beans/              # POJO beans for data binding
│   │   │   ├── config/             # Configuration classes (DataSource, Hibernate)
│   │   │   ├── context/            # Context management
│   │   │   ├── controllers/        # Servlet controllers (HTTP handlers)
│   │   │   ├── dao/                # Data Access Objects (Hibernate-based)
│   │   │   ├── DTO/                # Data Transfer Objects
│   │   │   ├── entity/             # JPA Entity classes
│   │   │   ├── enums/              # Enumeration classes
│   │   │   ├── filters/            # Request filters (auth, validation)
│   │   │   ├── listeners/          # Application lifecycle listeners
│   │   │   ├── mappers/            # Object mappers (Entity ↔ DTO)
│   │   │   ├── models/             # Domain models
│   │   │   ├── service/            # Service layer (business logic)
│   │   │   ├── utils/              # Utility classes and helpers
│   │   │   └── test/               # Test utilities
│   │   │
│   │   ├── resources/
│   │   │   ├── persistence.xml     # Hibernate configuration
│   │   │   ├── db.properties       # Database connection properties
│   │   │   ├── schema.sql          # Database schema
│   │   │   └── info.properties     # Application info
│   │   │
│   │   └── webapp/
│   │       ├── static/             # Static resources (CSS, JS, images)
│   │       │   ├── css/            # Stylesheets
│   │       │   ├── js/             # JavaScript files
│   │       │   └── images/         # Image assets
│   │       ├── views/              # JSP view files
│   │       │   ├── admin/          # Admin pages
│   │       │   └── checkout/       # Checkout pages
│   │       └── WEB-INF/
│   │           ├── web.xml         # Web application deployment descriptor
│   │           └── views/          # Protected JSP views
│   │
│   └── test/
│       ├── java/                   # Test classes
│       └── resources/              # Test resources
│
├── pom.xml                         # Maven configuration
├── mvnw & mvnw.cmd                # Maven Wrapper scripts
└── target/                         # Build output directory
```

## 📦 Prerequisites

- **Java Development Kit (JDK)** 23 or higher
- **Apache Maven** 3.6 or higher
- **Apache Tomcat** 10.0 or higher
- **MySQL Server** 8.0 or higher
- **Git** (for version control)

### Verification
```bash
java -version      # Should show Java 23+
mvn -version       # Should show Maven 3.6+
mysql --version    # Should show MySQL 8.0+
```

## 🔧 Installation & Setup

### Step 1: Clone or Download the Repository
```bash
git clone <repository-url>
cd e-commerce
```

### Step 2: Create Database
```bash
# Login to MySQL
mysql -u root -p

# Create database and import schema
CREATE DATABASE ecommerce_db;
USE ecommerce_db;
SOURCE Data-Base.sql;
```

### Step 3: Configure Database Connection
```bash
# Copy template to actual config
cp src/main/resources/db.properties.template src/main/resources/db.properties

# Edit db.properties with your credentials
```

**db.properties** configuration:
```properties
jdbcUrl=jdbc:mysql://localhost:3306/ecommerce_db
username=root
password=your_mysql_password
maximumPoolSize=10
minimumIdle=2
```

### Step 4: Build the Project
```bash
mvn clean install
```

This will:
- Compile the source code
- Run tests
- Package the application as a WAR file
- Output: `target/e-commerce-1.0-SNAPSHOT.war`

## 📊 Database Configuration

### Database Schema Overview

**Tables:**
- **Category**: Product categories
- **Product**: Product inventory with pricing
- **Customer**: User account information
- **Cart**: Shopping cart records
- **CartItem**: Items in carts
- **Orders**: Customer orders
- **OrderItems**: Items in orders

### Connection Pool Configuration (HikariCP)

The application uses HikariCP for connection pooling:
- Maximum Pool Size: 10 connections
- Minimum Idle Connections: 2
- Auto-configured in `config/DataSourceConfig.java`

## 🚀 Build & Run

### Development Environment

#### Using Maven:
```bash
# Compile and run tests
mvn clean compile test

# Package the application
mvn clean package

# Skip tests during build (if needed)
mvn clean package -DskipTests
```

#### Using IDE:
1. Import project as Maven project in IntelliJ IDEA or Eclipse
2. Configure Tomcat server in IDE
3. Deploy WAR artifact to server
4. Run/Debug from IDE

### Local Deployment with Tomcat

```bash
# Build the project
mvn clean package

# Copy WAR to Tomcat
cp target/e-commerce-1.0-SNAPSHOT.war $TOMCAT_HOME/webapps/

# Start Tomcat
$TOMCAT_HOME/bin/catalina.sh start  # Linux/Mac
# or
$TOMCAT_HOME/bin/catalina.bat start # Windows

# Access the application
# http://localhost:8080/e-commerce-1.0-SNAPSHOT
```

### Using Embedded Server (Development)

The Maven configuration supports running with embedded containers:
```bash
mvn compile
mvn test
```

## 📤 Deployment

### Production Deployment

#### Prerequisites
- Tomcat 10+ server installed and configured
- MySQL database server running
- Java 23 runtime environment

#### Steps

1. **Build Production Package**
   ```bash
   mvn clean package -DskipTests -Pproduction
   ```

2. **Configure for Production**
   - Update `db.properties` with production database credentials
   - Configure connection pool for expected load
   - Set appropriate logging levels

3. **Deploy to Tomcat**
   ```bash
   cp target/e-commerce-1.0-SNAPSHOT.war $TOMCAT_HOME/webapps/ecommerce.war
   ```

4. **Start Application**
   ```bash
   $TOMCAT_HOME/bin/startup.sh
   ```

5. **Verify Deployment**
   - Access: `http://your-server:8080/ecommerce`
   - Check Tomcat logs: `$TOMCAT_HOME/logs/catalina.out`




## 👥 Contributors

1- Ahmed Khaled Abdelmaksod
2- Mohamed Ashour
3- Mohamed Salah

> **Note**: Update this section with actual team member information

## 📝 Additional Resources

### Documentation
- [Jakarta EE Documentation](https://jakarta.ee/specifications/)
- [Hibernate ORM Guide](https://hibernate.org/orm/documentation/)
- [Maven Guide](https://maven.apache.org/guides/)
- [MySQL Reference Manual](https://dev.mysql.com/doc/refman/8.0/en/)


## 📄 License

This project is part of the ITI Java Enterprise track.

## ❓ Support & Issues

For issues, questions, or contributions:
1. Check existing documentation
2. Review similar issues in project history
3. Contact the project lead
4. Create detailed bug reports with steps to reproduce

---

**Last Updated**: March 29, 2026
**Version**: 1.0-SNAPSHOT