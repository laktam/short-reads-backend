pipeline {
    agent any
    environment {
        MYSQL_ROOT_PASSWORD = 'root'
        MYSQL_DATABASE = 'short_reads'
        MYSQL_USER = 'root'
        MYSQL_PASSWORD = 'root'

        ROOT_UPLOAD_DIR = '/uploads'
        PROFILE_IMG_UPLOAD_DIR = '/uploads/static'
        POST_IMAGES_DIR = '/uploads/static/posts'

        DOCKER_NETWORK = 'short_reads_network'
    }
 
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/laktam/short-reads-backend.git'
            }
        } 
        stage('Create Network') {
            steps {
                script {
                    bat """
                        docker network create ${DOCKER_NETWORK} || echo "Network already exists"
                        exit 0
                        """
                }
            }
        }
        stage('Start MySQL') {
            steps {
                script {
                    try {
                        bat 'docker stop mysql_db'
                        bat 'docker rm mysql_db'
                    } catch (err) {
                        echo "Container not found or already removed"
                    }
                    bat """
                        docker run -d --name mysql_db \
                        --network=${DOCKER_NETWORK} \
                        -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} \
                        -e MYSQL_DATABASE=${MYSQL_DATABASE} \
                        -v ${WORKSPACE}/other/short-read.sql:/docker-entrypoint-initdb.d/init.sql \
                        -p 3306:3306 grugnog/mysql-5.1
                    """
                }
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
                    bat """
                        docker build --build-arg POST_IMAGES_DIR=${POST_IMAGES_DIR} \
                        --build-arg PROFILE_IMG_UPLOAD_DIR=${PROFILE_IMG_UPLOAD_DIR} -t shortreadsbackend:latest .
                    """
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
                    bat """
                    docker run -d --name shortreadsbackend -p 81:80 \
                    --network=${DOCKER_NETWORK} \
                    -e MYSQL_USER=${MYSQL_USER}  \
                    -e MYSQL_PASSWORD=${MYSQL_PASSWORD}  \
                    -e ROOT_UPLOAD_DIR=${ROOT_UPLOAD_DIR}  \
                    -e ROOT_UPLOAD_DIR=${ROOT_UPLOAD_DIR}  \
                    -e PROFILE_IMG_UPLOAD_DIR=${PROFILE_IMG_UPLOAD_DIR}  \
                    -e POST_IMAGES_DIR=${POST_IMAGES_DIR}  \
                    shortreadsbackend:latest
                    """
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
