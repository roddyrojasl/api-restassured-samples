pipeline {
    agent  {
        docker {
                    image 'maven:3.8.7-eclipse-temurin-17' // Imagen oficial de Maven con Java
                  }
              }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/roddyrojasl/api-restassured-samples.git'
            }
        }
        stage('Install') {
            steps {
                sh 'mvn install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}
