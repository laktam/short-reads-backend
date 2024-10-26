pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/laktam/short-reads-backend'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
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
                    sh 'docker stop shortreadsbackend || true'
                    sh 'docker rm shortreadsbackend || true'
                    sh 'docker run -d --name shortreadsbackend -p 8080:8080 shortreadsbackend:latest'
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
