CREATE TABLE Shippers
(ShipperID INT NOT NULL AUTO_INCREMENT,
 CompanyName VARCHAR(40) NOT NULL,
 Phone VARCHAR(24) NULL,
 PRIMARY KEY(ShipperID));

INSERT INTO Shippers (ShipperID, CompanyName, Phone)  VALUES (1, 'Speedy Expreb', '(503) 555-9831');
INSERT INTO Shippers (ShipperID, CompanyName, Phone)  VALUES (2, 'United Package', '(503) 555-3199');
INSERT INTO Shippers (ShipperID, CompanyName, Phone)  VALUES (3, 'Federal Shipping', '(503) 555-9931');