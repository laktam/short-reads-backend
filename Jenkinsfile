pipeline {
    agent any
 
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/laktam/short-reads-backend.git'
            }
        } 
        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("shortreadsbackend:latest")
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    bat 'docker stop shortreadsbackend || true'
                    bat 'docker rm shortreadsbackend || true'
                    bat 'docker run -d --name shortreadsbackend -p 8080:8080 shortreadsbackend:latest'
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed.'
        }
    }
}
