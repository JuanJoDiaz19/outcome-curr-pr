pipeline {
    agent any

    tools {
        jdk 'OpenJDK-17'
        maven 'maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/JuanJoDiaz19/outcome-curr.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Add Dokku Host Key') {
            steps {
                sh '''
                    mkdir -p ~/.ssh
                    ssh-keyscan -H helpme-god-juanjo.centralus.cloudapp.azure.com >> ~/.ssh/known_hosts
                '''
            }
        }

        stage('Code Coverage') {
            steps {
                jacoco execPattern: "**/target/jacoco.exec"
            }

        }

        stage('Deploy to Dokku') {
            steps {
                sshagent(['ssh-dokku']) {
                    sh "git remote remove dokku || true"
                    sh "git remote add dokku dokku@helpme-god-juanjo.centralus.cloudapp.azure.com:outcurr || true"
                    sh "git push dokku HEAD:main -f"
                }
            }
        }
    }

    // post {
    //     always {
    //         junit '**/target/surefire-reports/*.xml'
    //         jacoco execPattern: '**/target/*.exec'
    //         cleanWs() // Cleans up the workspace after build completion
    //     }
    // }
}