CREATE TABLE Category (
    CategoryID INT PRIMARY KEY,
    Name VARCHAR(255),
    Description VARCHAR(255)
);

CREATE TABLE Product (
    ProductID INT PRIMARY KEY,
    Name VARCHAR(255),
    Description VARCHAR(255),
    Price FLOAT,
    Brand VARCHAR(255),
    Quantity INT,
    CategoryID INT,
    FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID)
);

CREATE TABLE Customer (
    CustomerID INT PRIMARY KEY,
    Name VARCHAR(255),
    Email VARCHAR(255),
    Password VARCHAR(255),
    PhoneNo VARCHAR(20),
    DOB DATE,
    HouseNo VARCHAR(50),
    Street VARCHAR(255),
    Area VARCHAR(255),
    City VARCHAR(100),
    Country VARCHAR(100)
);

CREATE TABLE Cart (
    CartID INT PRIMARY KEY,
    CustomerID INT,
    CreatedAt DATE,
    UpdatedAt DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)
);

CREATE TABLE CartItem (
    CartItemID INT PRIMARY KEY,
    CartID INT,
    ProductID INT,
    Quantity INT,
    FOREIGN KEY (CartID) REFERENCES Cart(CartID),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);

CREATE TABLE Orders (
    OrderID INT PRIMARY KEY,
    CustomerID INT,
    OrderDate DATE,
    TotalAmount FLOAT,
    Status VARCHAR(50),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)
);

CREATE TABLE OrderItem (
    OrderItemID INT PRIMARY KEY,
    OrderID INT,
    ProductID INT,
    Quantity INT,
    Price FLOAT,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);

CREATE TABLE Payment (
    PaymentID INT PRIMARY KEY,
    OrderID INT,
    PaymentDate DATE,
    PaymentMethod VARCHAR(50),
    Amount FLOAT,
    Status VARCHAR(50),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
);