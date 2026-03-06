def call(String appType) {

    pipeline {

        agent any

        stages {

            stage('Checkout') {
                steps {
                    checkout scm
                }
            }

            stage('Build') {
                steps {
                    script {

                        if (appType == "python") {
                            sh "pip install -r requirements.txt"
                        }

                        if (appType == "java") {
                            sh "mvn clean package"
                        }

                        if (appType == "node") {
                            sh "npm install"
                        }

                    }
                }
            }

            stage('Test') {
                steps {
                    echo "Running tests..."
                }
            }

            stage('Deploy') {
                steps {
                    echo "Deploy stage..."
                }
            }

        }
    }
}