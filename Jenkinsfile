#!/usr/bin/env groovy

pipeline {
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    environment {
        FOOTBALL_API_KEY = credentials("football-api-key")
        PERF_ENV_URL = perf.sapesports.com
    }
    stages {
        stage('Build and Test') {
            steps {
                sh 'SAPESPORTS_FOOTBALLAPI_APIKEY=$FOOTBALL_API_KEY ./gradlew build'
            }
        }
        stage('Quality checks') {
            steps {
                sh './gradlew sonar'
            }
        }
        stage('Package') {
            steps {
                sh 'docker build -t sapesports:v${env.BUILD_NUMBER} .'
                sh 'docker push sapesports:v${env.BUILD_NUMBER}'
            }
        }
        stage('Performance test') {
            steps {
                sh './gatling/run_gatling.sh -b perf.sapesports.com'
            }
        }
        stage('Deploy dev') {
            steps {
                echo 'Placeholder for deployment'
                sh './scripts/deploy.sh'
            }
        }
    }
}