
pipeline {
    agent any

    options {
        disableConcurrentBuilds()
        timeout(time: 15, unit: 'MINUTES')
    }

    environment {
        JAVA_HOME = '/Users/vinayraavi/Library/Java/JavaVirtualMachines/ms-21.0.13.0.2/Contents/Home'
        PATH = '/Users/vinayraavi/Library/Java/JavaVirtualMachines/ms-21.0.13.0.2/Contents/Home/bin:/opt/homebrew/bin:/usr/bin:/bin:/usr/sbin:/sbin'
    }

    stages {

        stage('Build Application') {
            steps {
                echo 'Building Employee Management API'
                sh 'mvn -B -DskipTests package'
            }
        }

        stage('Start API and Run Tests') {
            steps {
                sh '''
                    set -eu

                    APP_JAR=$(find target -maxdepth 1 -type f \
                      -name 'employee-management-api-*.jar' \
                      ! -name '*.original' | head -n 1)

                    if [ -z "$APP_JAR" ]; then
                        echo "Application JAR not found"
                        exit 1
                    fi

                    if nc -z 127.0.0.1 8080; then
                        echo "Port 8080 is already in use. Stop the existing API."
                        exit 1
                    fi

                    echo "Starting Spring Boot application"

                    java -jar "$APP_JAR" > app.log 2>&1 &
                    APP_PID=$!

                    cleanup() {
                        kill "$APP_PID" 2>/dev/null || true
                        wait "$APP_PID" 2>/dev/null || true
                    }

                    trap cleanup EXIT

                    READY=0
                    i=1

                    while [ "$i" -le 60 ]; do
                        if nc -z 127.0.0.1 8080; then
                            READY=1
                            break
                        fi

                        if ! kill -0 "$APP_PID" 2>/dev/null; then
                            echo "Application stopped unexpectedly"
                            cat app.log
                            exit 1
                        fi

                        sleep 2
                        i=$((i + 1))
                    done

                    if [ "$READY" -ne 1 ]; then
                        echo "Application did not start within 120 seconds"
                        cat app.log
                        exit 1
                    fi

                    echo "Application is running. Executing API tests."

                    mvn -B test
                '''
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true,
                  testResults: 'target/surefire-reports/TEST-*.xml'

            archiveArtifacts artifacts: 'app.log',
                             allowEmptyArchive: true
        }
    }
}
