# rabbitmq-to-elastic
This is a microservice with a frontend, where you can write some data, this will be pushed into rabbitmq,then the receiver will read the data and post into the index of elasticsearch. 
Required a rabbitmq and elastic 7.17.x configured.

What i did:
- pulled a docker image  with elastic 7.17.10
- pulled a docker image with rabbitmq

Webapp sends your messagge from frontend with POST to backend. Backend configured with RabbitMQ sends message into the queue, the Receiver takes the message, and post into the index 
of elasticsearch.
