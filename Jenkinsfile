pipeline {
    agent any

    environment {
        PATH = "/usr/local/bin:/usr/bin:/bin:${env.PATH}"
        GRADLE_OPTS = '-Dorg.gradle.daemon=false'
    }

    stages {
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t selenide-tests .'
            }
        }

        stage('Run Tests in Docker') {
            steps {
                sh 'docker run --rm selenide-tests'
            }
            post {
                always {
                    sh 'docker run --rm -v $(pwd)/build:/app/build selenide-tests ./gradlew allureReport --no-daemon || true'
                    allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
                }
            }
        }
    }

    post {
        always {
            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'build/reports/tests/test',
                reportFiles: 'index.html',
                reportName: 'TestNG Report'
            ])

            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'

            cleanWs()
        }
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}
