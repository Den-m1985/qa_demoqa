## Шаг 1. Установить Docker и Docker Compose

https://www.jenkins.io/doc/book/installing/docker/

```shell
docker network create jenkins
```

```shell
docker run \
  --name jenkins-docker \
  --rm \
  --detach \
  --privileged \
  --network jenkins \
  --network-alias docker \
  --env DOCKER_TLS_CERTDIR=/certs \
  --volume jenkins-docker-certs:/certs/client \
  --volume jenkins-data:/var/jenkins_home \
  --publish 2376:2376 \
  docker:dind \
  --storage-driver overlay2
```
cat > Dockerfile
```text
FROM jenkins/jenkins:2.479.1-jdk17
USER root
RUN apt-get update && apt-get install -y lsb-release ca-certificates curl && \
    install -m 0755 -d /etc/apt/keyrings && \
    curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc && \
    chmod a+r /etc/apt/keyrings/docker.asc && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] \
    https://download.docker.com/linux/debian $(. /etc/os-release && echo \"$VERSION_CODENAME\") stable" \
    | tee /etc/apt/sources.list.d/docker.list > /dev/null && \
    apt-get update && apt-get install -y docker-ce-cli && \
    apt-get clean && rm -rf /var/lib/apt/lists/*
    
# Chrome dependency Instalation
RUN apt-get update && apt-get install -y \
    fonts-liberation \
    libasound2 \
    libatk-bridge2.0-0 \
    libatk1.0-0 \
    libatspi2.0-0 \
    libcups2 \
    libdbus-1-3 \
    libdrm2 \
    libgbm1 \
    libgtk-3-0 \
#    libgtk-4-1 \
    libnspr4 \
    libnss3 \
    libwayland-client0 \
    libxcomposite1 \
    libxdamage1 \
    libxfixes3 \
    libxkbcommon0 \
    libxrandr2 \
    xdg-utils \
    libu2f-udev \
    libvulkan1
# Chrome instalation 
RUN curl -LO  https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt-get install -y ./google-chrome-stable_current_amd64.deb
RUN rm google-chrome-stable_current_amd64.deb
# Check chrome version
RUN echo "Chrome: " && google-chrome --version

USER jenkins
RUN jenkins-plugin-cli --plugins "blueocean"
RUN jenkins-plugin-cli --plugins "docker-workflow"
RUN jenkins-plugin-cli --plugins "json-path-api"
```
```shell
docker build -t myjenkins-blueocean:2.479.1-1 .
```
```shell
docker run \
  --name jenkins-blueocean \
  --restart=on-failure \
  --detach \
  --network jenkins \
  --env DOCKER_HOST=tcp://docker:2376 \
  --env DOCKER_CERT_PATH=/certs/client \
  --env DOCKER_TLS_VERIFY=1 \
  --publish 8080:8080 \
  --publish 50000:50000 \
  --volume jenkins-data:/var/jenkins_home \
  --volume jenkins-docker-certs:/certs/client:ro \
  myjenkins-blueocean:2.479.1-1
```



## Пример из яндекс практикума:
## Шаг 1.
```shell
sudo apt update  
sudo apt install docker.io docker-compose -y  
sudo systemctl enable --now docker
```

## Шаг 2. Создать docker-compose.yml
cat > docker-compose.yml
```text

version: '3.8'  
services:  
jenkins:  
image: jenkins/jenkins:lts  
container_name: jenkins  
ports:  
- "8080:8080"  
- "50000:50000"  
volumes:  
- jenkins_home:/var/jenkins_home  
restart: unless-stopped

volumes:  
jenkins_home:
```

## Шаг 3. Запустить
```shell
docker-compose up -d
```

## Шаг 4. Получить пароль администратора
```shell
docker exec -it jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```
В терминале увидим пароль администратора, копируем.

## Настройка
Переходим по адресу:
```text
http://<ip>:8080/
```
Разблокируйте Jenkins: 
Вводим пароль администратора.
Установите плагины: Выберите опцию "Install suggested plugins" для стандартного набора.
Создайте пользователя: После установки плагинов создайте учётную запись администратора для входа в систему.
Запустите Jenkins: После создания пользователя вы увидите сообщение о готовности Jenkins к работе. 


### Пример
```text
pipeline {
    agent any
    
    tools {
        gradle 'Default'
        jdk 'JDK17'
    }
        
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                url: 'https://github.com/Den-m1985/qa_demoqa'
            }
        }
    
        stage('Check') {
            steps {
                sh 'ls -la'
                sh 'pwd'
            }
        }
        
        stage('Test') {
            steps {
                echo 'Тестирование...'
                sh './gradlew clean uiTest -Denv=remote'
            }
        }
    }
    post {
        always {
            allure report: 'allure-report', results: 'build/allure-results'
        }
    }
}
```
