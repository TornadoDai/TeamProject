# Docker Exercise

Your goal in this exercise is to create a Dockerfile for a Java app and test the app running in a container.

Look back through the previous lab documents to refresh the details of how to achieve each step.

If your stuck on any step for more than a few minutes then ask an instructor for help rather than wasting unncessary time.

## Step 1) Clone the repo

The repository for the Java app we will use is at: ```https://bitbucket.org/fcallaly/lol-champions-jpa```

Clone this repository onto your linux machine:
```
git clone https://bitbucket.org/fcallaly/lol-champions-jpa.git
```

## Step 2) Compile the java app

We will use maven to compile the java application, the maven program is included in the repository as mvnw. Often you may have to make this file executable when you first clone a git repository.

```
cd lol-champions-jpa
chmod a+x mvnw
./mvnw package
```

By default, in a spring-boot application the maven package step will compile the code, run all tests and build a single jar. The jar can be found in the ```target/``` directory.


## Step 3) Write a Dockerfile

Write a Dockerfile to bundle the jar that was just created into a docker image. Look at the previous Dockerfile created for a java application for details of the contents of this file.

You can use the 'nano' text editor to write this new Dockerfile.


## Step 4) Build a new docker image

Build a new docker image with a docker build command. Look back at previous examples to remind yourself of the syntax of this command (hint: don't forget the .)

In the steps below, we will assume the new docker image is called (tagged): ```lol-docker-image:0.0.1```


## Step 5) Load the schema into MySQL on your linux machine

The MySQL installation on your linux machine has a different username/password:
username: conygre
password: C0nygre-C0nygre

Load the schema from the git repository into MySQL on your linux machine. This will create the schema and table required. The schema file is at schema/schema.sql

```
mysql -u root -p < schema/schema.sql

## You need to enter the MySQL password
```

## Step 6) Run the container connecting to the local MySQL database

Set the appropriate environment variables and run the newly build image. Take a look at the properties file in src/main/resources/application.properties to see what environment variables are available.

The env variables you will need to set will likely be similar to:

```
export DB_USER=conygre
export DB_PASS=C0nygre-C0nygre
export DB_HOST=<your_linux_hostname>
```

Now run the container, attaching it to an external port. This command is a guide - you may need to adjust it to your needs!
```
docker run -d -e DB_USER -e DB_PASS -e DB_HOST -p 8081:8080 lol-docker-image:0.0.1
```

## Step 7) Verify that the app works

Go to the swagger ui and verify you can save and retrieve records

```https://<your_linux_hostname>:8081/swagger-ui/```

## Step 8) Tag the image with a new name and push to dockerreg.conygre.com

Tag your image with a name that starts with ```dockerreg.conygre.com:5000```, this will mean we can "push" the image to that registry.

Note: use your name in the image so we can see who owns each one.

```
docker tag lol-docker-image:0.0.1 dockerreg.conygre.com:5000/frank-lol-docker-image:0.0.1

docker push dockerreg.conygre.com:5000/frank-lol-docker-image:0.0.1
```


Verify that your image is in the registry by checking the registry ui:
```http://dockerreg.conygre.com:8080```


## Step 9) (Extra!) Write a docker-compose yaml file

Create a docker-compose.yml file that will start a container from your image along with a MySQL container.

Look back at previous examples to see what should go in the compose file, this one will be very similar.



## Step 10) (Extra Extra!) Write a Jenkinsfile

For this to work, you will need to create your own version of the repository in bitbucket. You could either:
a) delete the .git directory here, create an EMPTY new bitbucket repo, go through the 'usual' ```git init```, ```git remote add origin ....``` steps.
b) fork the existing repo and clone your new fork

Ask your instructor if you are at all unclear on the above or which to choose.

When you have your OWN bitbucket repo checked out, now create a Jenkinsfile. Use the previouse northwind Jenkinsfile as a template, you should only have to change the variables at the top.

Commit the file to bitbucket.

Now on your Jenkins, create a new pipeline task, give it your bitbucket repo git url.

Run the task , monitor the jenkins output to see if anything goes wrong.

If the the jenkins job succeeds, verify you have a container running in openshift.
