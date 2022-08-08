# Exercise - Add Dockerfile and test

## 1. Create Dockerfile

Add a Dockerfile to this project, put it in the root of the project (not in a sub-directory).

You can base it on a similar Dockerfile here:

https://bitbucket.org/fcallaly/trade-api/src/master/Dockerfile

You should change the base image to a newer java base image e.g. Java 11 - search on dockerhub for openjdk images

e.g. ```FROM openjdk:11-jre```

You will need to change the jar filename and path that is being copied into the Docker image

e.g. ```COPY target/northwind-0.0.1-SNAPSHOT.jar app.jar```

## 2. Test Dockerfile

Test the Dockerfile by building a new docker image: ( in the same directory as the Dockerfile)

**REMEMBER THE . ON THE END OF THE COMMAND BELOW!!**

```docker build -t shippers-demo:0.0.1 .```

To test that the image works we will run it with docker run. However, we need to pass in environment variables
to tell the image how to connect to a database. The ```-e``` flag in docker run will set env variables to the container.

```docker run -d --name shippers -e DB_USER=conygre -e DB_PASS=C0nygre-C0nygre -e DB_HOST=emeadocker65.conygre.com -p 8081:8080 shippers-demo:0.0.1```

You can follow the logs from your container to see if there are any errors with:
```docker logs -f shippers```

Verify that you see the image is running:
```docker ps```

Verify that the application swagger ui is on port 8081

```http://<YOUR_LINUX_HOSTNAME>:8081/swagger-ui/```

when you're done, stop the image:
```docker stop shippers```

## 3. (Optional) Tag and Push to our internal docker registry

There is a dockerregistry in the training network at dockerreg.conygre.com:5000

Tag your image with a name that indicates the registry to send it to:
```docker tag shippers-demo:0.0.1 dockerreg.conygre.com:5000/<yourname>-shippers-demo:0.0.1```

Now upload the image to dockerhub with a "push"
```docker push dockerreg.conygre.com:5000/<yourname>-shippers-demo:0.0.1```

Verify that you can see the image at ```http://dockerreg.conygre.com:8080```

## 4. (Optional) Tag and Push to dockerhub

Sign up to dockerhub (do not use a work email!)

Login with the docker command line to dockerhub (it will ask for your user/pass):
```docker login```

Tag your image for dockerhub:
```docker tag shippers-demo:0.0.1 your-dockerhub-id/shippers-demo:0.0.1```

Now upload the image to dockerhub with a "push"
```docker push your-dockerhub-id/shippers-demo:0.0.1```

Verify that you can see the image on dockerhub

## 5. (Optional) Try running on a different machine

You can now run your docker image from any machine that has docker installed.

E.g. Ask someone else in your room if they can run your image - or you can try running theirs.

**NOTE:** You'll need to decide which database you will connect to and set the database host as an env variable as shown below.

```
docker run -d -e DB_HOST=<WHAT DATABASE?> -e DB_USER=conygre -e DB_PASS=C0nygre-C0nygre dockerreg.conygre.com:5000/<yourname>-shippers-demo:0.0.1
```

You should now have your image (and so your java application) available to run from anywhere that can run docker images.


