# Real-World-CICD

# Deployed Pet Clinic Java Application with Jenkins & Docker


# Pipeline Overview

1)Setup AWS EC2 Instance
  * Launch an AWS T2 Large Instance with Ubuntu.
  * Open inbound ports for HTTP and HTTPS in the Security Group.
2)Install Jenkins, Docker, and Trivy
  * Install Jenkins and start it on port 8080.
  * Install Docker and add the current user to the docker group.
  * Install Trivy for vulnerability scanning.
3)Configure Jenkins Plugins
  * Install necessary plugins like JDK, Maven, SonarQube Scanner, OWASP Dependency Check.
4)Create Jenkins Pipeline
  * Define a Jenkins pipeline using declarative syntax.
  * Configure tools (jdk and maven) in the pipeline.
  * Checkout the Git repository containing the Pet Clinic application.
  * Compile the application using Maven.
  * Perform SonarQube analysis on the codebase.
  * Implement a quality gate to ensure code quality standards are met.
  * Perform OWASP Dependency Check to identify vulnerabilities.
5)Build and Dockerize the Application
  * Build the application and create a Docker image.
  * Push the Docker image to Docker Hub.
6)Deploy Using Docker
  * Perform a security scan on the Docker image using Trivy.
  * Deploy the Docker image as a container on port 8082.
7)Clean Up
  * Terminate the AWS EC2 instance once the deployment is completed.
