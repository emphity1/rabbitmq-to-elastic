# rabbitmq-to-elastic
This is a microservice with a frontend, where you can write some data, this will be pushed into rabbitmq,then the receiver will read the data and post into the index of elasticsearch. 
Required a rabbitmq and elastic 7.17.x configured.

Backend service  is both Sender and Receiver.

Webapp sends your messagge from frontend with POST to backend. Backend configured with RabbitMQ sends message into the queue, the Receiver takes the message, and post into the index 
of elasticsearch.


1) check section of "myapp" of docker-compose.yml 
2) So check "context" it will take the Dockerfile from current dicretory "." if not specified. (Dockerfile need to be in same file with docker-compse.yml)
3) Start docker-compose
4) Open locahost:8080
5) Upload some json file

