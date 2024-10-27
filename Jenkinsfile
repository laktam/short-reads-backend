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
                    bat 'docker build -t shortreadsbackend:latest .'
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    try {
                        bat 'docker stop shortreadsbackend'
                        bat 'docker rm shortreadsbackend'
                    } catch (err) {
                        echo "Container not found or already removed"
                    }
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
