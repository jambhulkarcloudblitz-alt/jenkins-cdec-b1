pipeline{
    agent any
    stages {
        stage ('PULL') {
            steps {
                git branch: 'test', url: 'https://github.com/jambhulkarcloudblitz-alt/CDEC-studentapp.git'
            }
        }
        stage ('FRONTEND-DOCKER-BUILD') {
            steps {
                sh '''cd frontend
                    docker build . -t shubhamjambhulkar07/easy-frontend:latest'''
            }
        }
        stage ('Backend-DOCKER-BUILD') {
            steps {
                sh '''cd backend
                        docker build . -t shubhamjambhulkar07/easy-backend:latest'''
            }
        }
        stage ('Docker-Push') {
            steps {
                sh '''docker push shubhamjambhulkar07/easy-backend:latest
                      docker push shubhamjambhulkar07/easy-frontend:latest'''
            }
        }
         stage ('Docker-Remove') {
            steps {
                sh '''docker rmi -f shubhamjambhulkar07/easy-backend:latest
                      docker rmi -f shubhamjambhulkar07/easy-frontend:latest'''
            }
        }
        stage ('DEPLOY') {
            steps {
                sh 'kubectl apply -f simple-deploy/'
            }
        }
    }
}