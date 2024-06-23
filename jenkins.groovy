
pipeline{
    agent any
    tools {
        jdk 'jdk11'
        maven 'maven3'
    }
    stages {
        stage('Clean Workspace'){
            steps {
                cleanWs()
            }
        }
        stage('Checkout SCM'){
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Aj7Ay/amazon-eks-jenkins-terraform-aj7.git']])
            }
        }    
        stage('Maven Compile'){
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('sonarqube Analysis'){
            steps{
                script{
                    withSonarQubeEnv(credentialsId: 'Sonar-token'){
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }
        stage('quality gate'){
            steps{
                script{
                    waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
                }
            }
        }
        stage("OWASP Dependency Check"){
             steps{
                dependencyCheck additionalArguments:'--scan •/ --format HTML ', odcInstallation: 'DP-Check'
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }
        }
        stage('Build war file'){
            steps{
                sh 'mvn clean install package'
            }
        }
        stage('Build and push to docker hub'){
            steps{
                script{
                    withDockerRegistry(credentialsId: 'docker', toolName: 'docker'){
                        sh "docker build -t petclinic1 ."
                        sh "docker tag petclinic1 saipandu/pet-clinic123:latest" 
                        sh "docker push saipandu/pet-clinic123:latest"
    
                    }
                }
            }
        }
        stage('TRIVY'){
            steps{
                sh "trivy image saipandu/pet-clinic123:latest"
            }
        }
        stage('Deploy to container'){
            steps{
                sh 'docker run -d --name pet1 -p 8082:8080 saipandu/pet-clinic123:latest'
            }
        }
    }
}        

