pipeline {
    agent any

    tools {
        jdk 'OpenJDK-17'
        maven 'maven3'
    }

    // triggers {
    //     githubPullRequest()
    // }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/JuanJoDiaz19/outcome-curr-pr.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Smoke Tests') {
            steps {
                // Aquí puedes definir los comandos específicos para las pruebas de humo
                sh 'mvn verify -PsmokeTests'
            }
        }

        stage('Code Coverage') {
            steps {
                // Genera reporte de cobertura de código con JaCoCo
                jacoco execPattern: "**/target/jacoco.exec"
            }
        }

        stage('Docker Container Setup') {
            steps {
                // Configura la aplicación en un contenedor Docker para pruebas adicionales, si aplica
                sh '''
                    docker build -t outcurr-app .
                    docker run -d --name outcurr-container -p 9092:9092 outcurr-app
                '''
            }
        }
    }

    post {
        always {
            // Publica resultados de pruebas y limpia el workspace al finalizar
            junit '**/target/surefire-reports/*.xml'
            jacoco execPattern: '**/target/*.exec'
            cleanWs()
        }
    }
}
