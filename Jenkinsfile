pipeline {
    agent any
    tools{
	maven 'Maven3'
    }
    stages {
        stage('Integration testing') {
                steps {
                    sh 'mvn test -DmembersOnlySuites=is.spark.tests.integration'
                    step([$class: 'JUnitResultArchiver', testResults:'**/target/surefire-reports/TEST-'+ '*IT.xml'])
                }
        }

        stage("Unit testing") {
            steps {
                sh 'mvn test -DmembersOnlySuites=is.spark.tests.unit'
                step([$class: 'JUnitResultArchiver', testResults:'**/target/surefire-reports/TEST-*UT.xml'])
            }
        }

    }
    post {
        success {
            slackSend (color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
        failure {
            slackSend (color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
    }
}

        //run to redeploy app (needs tinkering as taken from other project)
        /*
        stage("Staging") {
            steps {
                sh 'pid=\$(lsof -i:7070 -t); kill -TERM \$pid || kill -KILL \$pid'
                withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                    sh 'nohup mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=7070 &'
                }
            }
        }
        */

        //disabled testing stage as we are running unit and integration tests using profiles defined in pom
        /*
        stage('Testing') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        */

