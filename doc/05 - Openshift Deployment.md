# Exercise - Deploy shippers-demo:0.0.1 in Openshift

## 1. Check the image is in dockerhub

The shippers-demo:0.0.1 image has been pushed to dockerhub at:
https://hub.docker.com/repository/docker/callalyf/shippers-demo

Check that you see the image in dockerhub

## 2. Deploy in the openshift UI

1. Login to the openshift ui as admin/admin (https://<your_linux_hostname>:8443)

2. Create a new project in the openshift ui

3. In the new project click "Add to Project" -> "Deploy Image"

4. Enter the image name (AKA "tag") from dockerhub (callalyf/shippers-demo:0.0.1)

5. Set the appropriate env variables e.g. DB_HOST=emeadocker65.conygre.com, DB_USER=conygre, DB_PASS=C0nygre-C0nygre

6. As the container is being deployed explore the screens Application->Pods , Application->Deployments, Overview. Try to see what's happening.

## 3. Add a "route" to the app

Click Application->Services. Select the shippers-demo service. Click Actions->Create Route (top right).

Use default values for all the fields in the route, click 'Create. This is creating a path that we can access the application through.

Verify you can see the swagger-ui if you put /swagger-ui/ on the end of the route.

Verify you can save and retrieve from the database.


## 4. Explore

Take a look around e.g. explore the logs at Applications -> Pods . Select one and see logs.


## 5. (Optional) Repeat for a separate new project with the 'oc' command on the command line

Try to achieve the same with the 'oc' command e.g.
```
oc new-project .....
oc new-app ....
```
