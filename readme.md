# Kubernetes 

Orquestração de containers rodando microserviços.

## Microserviços

backend-servlet:

	mvn clean package jetty:run
	curl http://localhost:8080/api/backend?msg='Ola'
	
microservices-springboot:

	mvn clean package spring-boot:run -Dserver.port=8081 ou
	java -Dserver.port=8081 -jar target/microservices-springboot-0.0.1-SNAPSHOT.jar
	curl http://localhost:8081/api/msg 

##Container

backend-servlet

	backend-servlet.dockerfile
	sudo docker images
	Diretorio microservices-kubernetes
	sudo docker build -f backend-servlet/backend-servlet.dockerfile -t lincolnppires/backend-servlet .
	sudo docker network create --driver bridge rede-microservicos	
	sudo docker network ls
	sudo docker run -p 8080:8080 --name container-backend --network rede-microservicos lincolnppires/backend-servlet
	sudo docker ps 
	curl http://localhost:8080/api/backend?msg='Ola'
	http://localhost:8080/api/backend?msg=ola
	
	
microservices-springboot

	microservices-springboot.dockerfile
	sudo docker images	
	sudo docker build -f microservices-springboot/microservices-springboot.dockerfile -t lincolnppires/microservices-springboot . 
	sudo docker run -p 8081:8081 --name container-springboot --network rede-microservicos lincolnppires/microservices-springboot
	sudo docker network inspect rede-microservicos
	sudo docker inspect container-backend | grep IPAddress	
	curl http://localhost:8081/api/msg	
	docker exec -it container-springboot apt-get install -y curl && curl -X GET http://IPAdress:8080/api/backend?msg=ola

Parar todos os conteiners

	sudo docker stop $(sudo docker ps -q)

Remove todos os containers que estão parados
	
	sudo docker container prune

## Docker Compose 

	sudo curl -L "https://github.com/docker/compose/releases/download/1.22.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
	sudo chmod +x /usr/local/bin/docker-compose


	docker-compose.yml
	sudo docker-compose up
	sudo docker ps
	http://localhost:8080/api/backend?msg=ola
	http://localhost:8081/api/msg
	sudo docker-compose down	

	
## Minikube

	sudo apt install virtualbox
	curl -Lo minikube https://storage.googleapis.com/minikube/releases/v0.30.0/minikube-linux-amd64 && chmod +x minikube && sudo cp minikube /usr/local/bin/ && rm minikube

	
## kubectl:
	
	curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
	chmod +x ./kubectl
	sudo mv ./kubectl /usr/local/bin/kubectl
	
## Docker Hub

	sudo docker login
	sudo docker push lincolnppires/backend-servlet
	sudo docker push lincolnppires/microservices-springboot
	
## Pod
	
	pod-backend-servlet.yml
	minikube start
	kubectl get pods	
	minikube dashboard
	kubectl create -f pod-backend-servlet.yml 
	kubectl get pods
	kubectl delete pods pod-backend
	kubectl get pods

Pod foi removido permanentemente, ou seja, o Kubernetes ainda não fez o gerenciamento desse Pod que está abstraindo o container.

## Deployment

	deployment-backend-servlet.yml
	kubectl create -f deployment-backend-servlet.yml
	kubectl get pods
	kubectl delete pods NAME_POD		
	kubectl get pods
	
Abstraindo o objeto Pod dentro do objeto Deployment, adicionamos o estado desejado da aplicação para o Kubernetes gerenciar. 
Dessa forma, o Kubernetes sabe que sempre queremos ter um objeto Pod com o container rodando e tentará de todas as formas manter 
o estado do cluster igual a esse estado desejado de configuração.

## Escalando

	minikube dashboard
	kubectl get pods
	kubectl scale deployment backend-deployment --replicas=3	
	kubectl get pods
	minikube dashboard
	kubectl describe pods | grep IP

Pods estão em constante alterações -> por conta disso precisamos de um objeto mais estável...

## Serviços

	service-backend-servlet.yml
	kubectl create -f service-backend-servlet.yml 
	kubectl get services
	minikube service service-teste --url
	Ex: http://192.168.99.100:32494/api/backend?msg=ola
	--Verifique a variação dos ips!

## Pod / Deployment / Serviço:

	pod-microservices-springboot.yml
	deployment-microservices-springboot.yml
	service-microservices-springboot.yml

...

	
