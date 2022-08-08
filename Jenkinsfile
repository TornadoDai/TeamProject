def mysqlHost = 'returners-linux-17.conygre.com'
def dockerRegistry = 'dockerreg.conygre.com:5000'
def projectName = 'reference-northwind-app'
def version = "0.0.${currentBuild.number}"
def dockerImageTag = "${projectName}:${version}"

pipeline {
  agent any
 
  stages {
    stage('Test') {
      steps {
        sh 'chmod a+x mvnw'
        sh './mvnw clean test'
      }
    }

    stage('Build') {
      steps {
        sh './mvnw package'
      }
    }

    stage('Build Container') {
      steps {
        sh "docker build -t ${dockerImageTag} ."
      }
    }

    stage('Deploy Container To Openshift') {
      environment {
        OPENSHIFT_CREDS = credentials('openshiftCreds')
      }
      // This step does the following:
      // 1. login to openshift
      // 2. create a new project IF a project with projectName doesn't exist
      // 3. delete any old containers of our java app in the project
      // 4. start a new java app container in the project, setting env variables
      // 5. expose the java app to the outside world
      //
      steps {
        sh "oc login -u ${OPENSHIFT_CREDS_USR} -p ${OPENSHIFT_CREDS_PSW}"
        sh "oc project ${projectName} || oc new-project ${projectName}"
        sh "oc delete all --selector app=${projectName} || echo 'Unable to delete all previous openshift resources'"
        sh "oc new-app -l version=${version} -e DB_HOST=${mysqlHost} -e DB_USER=conygre -e DB_PASS=C0nygre-C0nygre ${dockerImageTag}"
        sh "oc expose svc/${projectName}"
      }
    }

    stage('Tag and Push') {
      steps {
        sh "docker tag ${dockerImageTag} ${dockerRegistry}/${dockerImageTag}"
    //    sh "docker push ${dockerRegistry}/${dockerImageTag}"
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      archiveArtifacts artifacts: 'target/site/jacoco/**/*'
      archiveArtifacts 'target/surefire-reports/**/*'
    }
  }
}

