pipeline {
    agent any

    parameters {
        string(
            name: 'BRANCH',
            defaultValue: 'main',
            description: 'Git branch to checkout and test'
        )
        booleanParam(
            name: 'RUN_ALL',
            defaultValue: true,
            description: '✓ Run all tests (overrides other selections)'
        )
        booleanParam(
            name: 'RUN_CRITICAL',
            defaultValue: false,
            description: '✓ Run critical tests'
        )
        booleanParam(
            name: 'RUN_NORMAL',
            defaultValue: false,
            description: '✓ Run normal tests'
        )
        booleanParam(
            name: 'RUN_API',
            defaultValue: false,
            description: '✓ Run API tests'
        )
        booleanParam(
            name: 'RUN_LOW',
            defaultValue: false,
            description: '✓ Run low priority tests'
        )
        booleanParam(
            name: 'RUN_BDD',
            defaultValue: false,
            description: '✓ Run BDD/Cucumber tests'
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

        stage('Setup Secrets') {
            steps {
                script {
                    echo "Creating secret.properties from Jenkins credentials..."
                    withCredentials([
                        string(credentialsId: 'saucedemo-username', variable: 'SAUCE_USER'),
                        string(credentialsId: 'saucedemo-password', variable: 'SAUCE_PASS')
                    ]) {
                        sh '''
                            echo "# SauceDemo Credentials" > secret.properties
                            echo "saucedemo.username=${SAUCE_USER}" >> secret.properties
                            echo "saucedemo.password=${SAUCE_PASS}" >> secret.properties
                            echo "✅ Secrets file created successfully"
                        '''
                    }
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
                    def severityGroups = []

                    if (params.RUN_ALL) {
                        severityGroups.add('all')
                        echo "Running ALL tests"
                    } else {
                        if (params.RUN_CRITICAL) severityGroups.add('critical')
                        if (params.RUN_NORMAL) severityGroups.add('normal')
                        if (params.RUN_API) severityGroups.add('api')
                        if (params.RUN_LOW) severityGroups.add('low')
                        if (params.RUN_BDD) severityGroups.add('bdd')
                    }

                    if (severityGroups.isEmpty()) {
                        error("No test groups selected! Please select at least one test group.")
                    }

                    def groupsParam = severityGroups.join(',')
                    echo "Running tests with groups: ${groupsParam}"
                    echo "Running in HEADLESS mode (always enabled in Jenkins)"
                    sh "./gradlew runBySeverity -Pseverity=${groupsParam} -Dselenide.headless=true"
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
            // Limpiar archivo de secrets desencriptado
            sh 'rm -f secret.properties'

            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'build/reports/tests/runBySeverity',
                reportFiles: 'index.html',
                reportName: 'TestNG Report'
            ])


            junit allowEmptyResults: true, testResults: 'build/test-results/runBySeverity/*.xml'

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
