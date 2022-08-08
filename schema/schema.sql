CREATE Database Northwind;

USE Northwind;

CREATE TABLE IF NOT EXISTS Shippers
(ShipperID INT NOT NULL AUTO_INCREMENT,
CompanyName VARCHAR(40) NOT NULL,
Phone VARCHAR(24) NULL,
PRIMARY KEY(ShipperID));

INSERT INTO Shippers (CompanyName, Phone)  VALUES('Speedy Expreb', '(503) 555-9831');
INSERT INTO Shippers (CompanyName, Phone)  VALUES('United Package', '(503) 555-3199');
INSERT INTO Shippers (CompanyName, Phone)  VALUES('Federal Shipping', '(503) 555-9931');
