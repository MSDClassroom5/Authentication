node {
  
    stage ("Gradle Build - AuthApi") {
        sh 'gradle clean build'
    }
    
    stage ("Gradle Bootjar-Package - AuthApi") {
        sh 'gradle bootjar'
    }
    
    stage ("Containerize the app-docker build - AuthApi") {
        sh 'docker build -t project-auth:v1.0 .'
    }
    
    stage ("Inspect the docker image - AuthApi"){
        sh "docker images project-auth:v1.0"
        sh "docker inspect project-auth:v1.0"
    }
    
    stage ("Run Docker container instance - AuthApi"){
        sh "docker run -d --name project-auth -p 8081:8081 project-auth:v1.0"
     }
    
    stage('User Acceptance Test - AuthApi') {
	
	  def response= input message: 'Is this build good to go?',
	   parameters: [choice(choices: 'Yes\nNo', 
	   description: '', name: 'Pass')]
	
	  if(response=="Yes") {
	    stage('Deploy to Kubenetes cluster - AuthApi') {
	      sh "kubectl create deployment event-auth --image=project-auth:v1.0"
		//get the value of API_HOST from kubernetes services and set the env variable
	      sh "kubectl set env deployment/project-auth API_HOST=project-data:8080"
	      sh "kubectl expose deployment project-auth --type=LoadBalancer --port=8081"
	    }
	  }
    }

    stage("Production Deployment View"){
        sh "kubectl get deployments"
        sh "kubectl get pods"
        sh "kubectl get services"
    }
  
}
