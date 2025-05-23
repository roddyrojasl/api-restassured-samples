pipeline {
    agent  {
        docker {
                    image 'maven:3.8.7-eclipse-temurin-17' // Imagen oficial de Maven con Java
                  }
              }

    stages {
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
