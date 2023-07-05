#!/bin/bash




echo " =========================================== "
echo " ======   STOPPING RABBITMQ   ============== "
echo " =========================================== "


kubectl delete statefulset rabbitmq
kubectl delete service rabbitmq
#cd rabbitmq && kubectl apply -f app-ingress.yml



sleep 0.5

echo " =========================================== "
echo " ======   STOPPING ELASTIC    ============== "
echo " =========================================== "


kubectl delete statefulset node
kubectl delete service elastic-service 
#kubectl delete ingress minimal-ingress

sleep 0.5


echo " =========================================== "
echo " ======   STOPPING WEBAPP     ============== "
echo " =========================================== "

kubectl delete statefulset webapp-statefulset
kubectl delete service webapp-service
kubectl delete ingress webapp-ingress


