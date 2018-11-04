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
        stage('Compilation and Analysis') {
            parallel {
                stage("Compilation") {
                    steps {
                        sh 'mvn -B -DskipTests clean package'
                    }
                }
                stage("Checkstyle checking") {
                    steps {
                        sh "mvn checkstyle:checkstyle"
                        step([$class: 'CheckStylePublisher',
                          canRunOnFailed: true,
                          defaultEncoding: '',
                          healthy: '100',
                          pattern: '**/target/checkstyle-result.xml',
                          unHealthy: '90',
                          useStableBuildAsReference: true
                        ])
                    }
                }
            }
        }
        stage('QA') {
          steps {
            sh 'mvn scoverage:report'
            step([$class: 'ScoveragePublisher', reportDir: 'target/scoverage-data', reportFile: 'scoverage.coverage.xml'])
            //jacoco()
            withSonarQubeEnv('SonarQube') {
              // requires SonarQube Scanner for Maven 3.2+
              sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
            }
          }
        }
        stage('Packaging'){
            when {
                branch "master"
            }
            steps {
                script {
                    def server = Artifactory.server('artifactory')
                    def rtMaven = Artifactory.newMavenBuild()
                    rtMaven.resolver server: server, releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot'
                    rtMaven.deployer server: server, releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local'
                    rtMaven.tool = 'Maven3'
                    def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'install'
                    server.publishBuildInfo buildInfo
                }
            }
        }
        stage ('Production deployment') {
            when {
                branch 'master1'
             }
             steps {
                 input id: 'DeployToProd', message: 'Deploy to production system?', ok: 'Yes'
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

