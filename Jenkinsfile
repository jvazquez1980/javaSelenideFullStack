pipeline {
    agent any

    parameters {
        string(
            name: 'BRANCH',
            defaultValue: 'main',
            description: 'Git branch to checkout and test'
        )
        choice(
            name: 'SEVERITY_LEVEL',
            choices: ['all', 'blocker', 'critical', 'normal', 'minor', 'trivial'],
            description: 'Select test severity level to execute'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run tests in headless mode'
        )
    }

    environment {
        JAVA_HOME = '/opt/homebrew/Cellar/openjdk@11/11.0.29/libexec/openjdk.jdk/Contents/Home'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        GRADLE_OPTS = '-Dorg.gradle.daemon=false'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "Checking out branch: ${params.BRANCH}"
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/${params.BRANCH}"]],
                        userRemoteConfigs: scm.userRemoteConfigs
                    ])
                }
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean compileTestJava'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    echo "Running tests with severity level: ${params.SEVERITY_LEVEL}"
                    sh "./gradlew runBySeverity -Pseverity=${params.SEVERITY_LEVEL} -Dselenide.headless=${params.HEADLESS}"
                }
            }
            post {
                always {
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
