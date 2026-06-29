pipeline {
    agent any
    stages {
        stage ('PULL') {
            steps {
                git branch: 'main', url: 'https://github.com/jambhulkarcloudblitz-alt/eks-infra-terraform.git'
            }
        }
        stage ('TERRAFOM-PLAN') {
            steps {
                sh '''terraform init
                      terraform plan
                '''
            }
        }
        stages ('APPROVAL') {
            steps {
                timeout(30) {
                    input message : 'Approval is Required. Shall we proceed? , ok: approved'
                 }
                
            }
        }
        stage ('TERRAFORM-APPLLY') {
            steps {
                sh '''terraform apply --auto-approve'''
                
            }
        }
    }
}