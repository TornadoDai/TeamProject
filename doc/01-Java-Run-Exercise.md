# Exercise - Build And Run With Commandline

## 1. Clone the repository to Your Linux Machine

On your linux machine clone the repo:
```git clone https://bitbucket.org/fcallaly/northwind-shippers-jpa.git```

cd into the cloned directory
```cd northwind-shippers-jpa```

## 2. Test & build the java code

If the maven wrapper is not executable then make it so: ```chmod a+x mvnw```

Use maven to build a jar - note this will also run all of the unit tests in the code
```./mvnw clean package```

**Note:** Particularly for App Dev people, some of the unit tests in this project interact with a database (they are really integration tests).
In order to run these tests on any machine, they use an "in-memory" database. This is enabled with 3 things:

1. The h2 dependency is included in pom.xml with test scope
2. @AutoConfigureTestDatabase is included on the relevant tests classes
3. Some properties are set for testing by adding the file src/test/resources/application.properties

You will need to add the above (or similar) to your own Spring Boot projects to get them to build correctly on a CI system.

## 3. Load the schema & tables into the database

There is a MySQL database on your linux machine. It has a different username and password to the one on your windows machine. On the linux machine, the mysql credentials are:
```
username: conygre
password: C0nygre-C0nygre
```

There is a schema file in schema/schema.sql. We will load the contents of that file into the database on the linux machine:
```
# log in to mysql
mysql -u conygre -p < schema/schema.sql

# enter the MySQL password
```

**Note:** JPA/Java could alternatively create the table(s) automatically when the program runs because the ddl-auto property is set to 'update' in application.properties.

## 4. Run the jar with the correct properties set

To run the jar so that it uses the mysql database on your linux machine, you will need to set some properties e.g. - use port 8081, set the mysql user/password

Note in this case we're setting the properties as env variables in the shell. There is an alternative method which is to set them  in the java command "-D...."..

e.g.
```
export SERVER_PORT=8081
export DB_USER=conygre
export DB_PASS=C0nygre-C0nygre
java -jar target/northwind-0.0.1-SNAPSHOT.jar
```

Verify the program is working by going to the swagger ui at YOUR LINUX HOSTNAME on port 8081 from any web browser:
e.g. ```http://YOUR_LINUX_HOSTNAME:8081/swagger-ui/```


What happens if you add a new database record through the POST method on the swagger ui and then restart the app?
Is your new record still there? Why/Why Not?

How would you get your app to save/retrieve records to the mysql database installed on the machine: emeadocker65.conygre.com
Try this out, what shipper records do you now see in swagger-ui ?


