# Running Multiple Containers

## 1. Testing Mysql In a Container

Before running muliptle containers, let's first run a mysql database in a container by itself.

When we run the mysql database container, we will want it to have some tables.

So, we will get the mysql container install the shippers table into the database when it starts up.

1. Take a look at the schema for the shippers table

The schema is stored in the repository in a file at schema/schema.sql. A relational database application will need a sql file that can install the required tables and data. We would not use the JPA ddl-auto feature in production.

Take a look at the contents of this file - the mysql database will run this file when it starts up.
```cat /home/grads/northwind-shippers-jpa/schema/schema.sql```

2. Run a MYSQL database in a container

In this command we're letting the container access the directory that contains the schema file. However from inside the container, this same directory is at /docker-entrypoint-initdb.d.


```
docker run -d --name mysql -v /home/grads/northwind-shippers-jpa/schema:/docker-entrypoint-initdb.d  -e MYSQL_ROOT_PASSWORD=c0nygre mysql
```

You should check the database container is running with ```docker ps```, then get a cmd line INSIDE the container and run the mysql client to look around.

```
docker exec -it mysql /bin/bash
mysql -u root -pc0nygre
use Northwind;
show tables;
select * from Shippers;
exit
exit
```

We've proved we can start a database in a container, now stop and remove the container
```
docker stop mysql
docker rm mysql
```

## 2. Two Containers on a "docker network"

1. Create a docker network called "mysqlnet"
```
docker network create mysqlnet
```

2. Run the mysql container again, but put it on the network with the --network flag
```
docker run -d --name mysql --network mysqlnet -v /home/grads/northwind-shippers-jpa/schema:/docker-entrypoint-initdb.d  -e MYSQL_ROOT_PASSWORD=c0nygre mysql
```

3. Run your java app container, putting it on the network and telling it to use the Database Host called "mysql" (because that's the NAME of the container we created)
```
docker run --name javaapp --network mysqlnet -e DB_HOST=mysql -p 8081:8080 shippers-demo:0.0.1
```


## 3. Multiple Containers with docker-compose

docker-compose is a tool that lets us handle multipe containers as a single unit.

With docker-compose the containers, and how to run them, is all defined in yaml. The convention is for this file to be called docker-compose.yml

We could do all of the previous steps using docker-compose. This provides a clear definition of all of the containers we want to create and how they are linked.

Create a file called docker-compose.yml and paste the below text into it:
```
version: '3.0'

services:

  mysql:
    image: mysql:latest
    volumes:
      - /home/grads/northwind-shippers-jpa/schema:/docker-entrypoint-initdb.d
    environment:
      MYSQL_ROOT_PASSWORD: c0nygre
    networks:
      - mysqlnet

  javaapp:
    image: shippers-demo:0.0.1
    environment:
      DB_HOST: mysql
    ports:
      - 8081:8080
    networks:
      - mysqlnet

networks:
  mysqlnet:
```

We can use docker-compose to deal with the two containers as a single 'unit'. docker-compose will look for a docker-compose.yml file in the current directory (or above).

* ```docker-compose up``` : run all containers in the docker-compose.yml file in the current directory
* ```docker-compose up -d``` : same as above, but this time detach your console (use docker-compose logs to see the logs).
* ```docker-compose down``` : stop all containers and delete the container (not the image)
* ```docker-compose stop <service_name>``` : stop an individual container from the file
* ```docker-compose start <service_name>``` : start an individual container from the file
* ```docker-compose logs <service_name>``` : see the output from an individual service

See https://docs.docker.com/compose/reference/ for more commands
