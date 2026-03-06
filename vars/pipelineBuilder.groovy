def call(String appType) {

    pipeline {

        agent any

        stages {

            stage('Checkout') {
                steps {
                    git url: 'https://github.com/dkasha14/jenkins-shared-library.git', branch: 'master'
                }
            }

            stage('Build') {
                steps {
                    script {

                        if (appType == "python") {
                            sh "pip3 install -r requirements.txt || true"
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