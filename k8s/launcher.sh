#!/bin/bash



path="/home/dima/Desktop/esercitazione/k8s/k8s-test"


echo " =========================================== "
echo " ======   STARTING RABBITMQ   ============== "
echo " =========================================== "


cd "$path"/rabbitmq && kubectl apply -f rabbit-deployment.yml
cd "$path"/rabbitmq && kubectl apply -f rabbit-service.yml



sleep 0.5

echo " =========================================== "
echo " ======   STARTING ELASTIC    ============== "
echo " =========================================== "


cd "$path"/elastic && kubectl apply -f elastic-deployment.yml
cd "$path"/elastic && kubectl apply -f elastic-service.yml

sleep 0.5


echo " =========================================== "
echo " ======   STARTING WEBAPP     ============== "
echo " =========================================== "

cd "$path"/app && kubectl apply -f app-deployment.yml
cd "$path"/app && kubectl apply -f app-service.yml
cd "$path"/app && kubectl apply -f app-ingress.yml
