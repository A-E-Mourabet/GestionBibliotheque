pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven'
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/A-E-Mourabet/GestionBibliotheque.git'
            }
        }
        stage('Build') {
            steps {
                bat '%MAVEN_HOME%\\bin\\mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                bat '%MAVEN_HOME%\\bin\\mvn test'
            }
        }
        stage('Quality Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat '%MAVEN_HOME%\\bin\\mvn sonar:sonar'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Déploiement simulé réussi'
            }
        }
    }
    post {
        success {
            emailext to: 'elmourabet17@gmail.com',
                subject: 'Build Success',
                body: 'Le build a été complété avec succès.'
        }
        failure {
            emailext to: 'elmourabet17@gmail.com',
                subject: 'Build Failed',
                body: 'Le build a échoué.'
        }
    }
}
