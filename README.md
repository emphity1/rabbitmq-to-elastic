

                                ###############################################
                                ############# Appunti personali ###############
                                ###############################################


Buildare app maven e portarlo nella cartella docker-compose.
Buildare l'immagine del del Dockerfile che si prenderà lo jar creato precedentemente. Questo jar servirà poi per il pod su k8s.
Uploadare eventualmente l'immagine su dockerhub.

Startare minikube

Per fare apply di tutti file yml, si può utilizzare "launcher.sh" che sta nella cartella k8s, controllare i percorsi nello script prima di lanciarlo.






==================================================
==========Kubernetes==============
===========================================

===================  elastic-deployment.yml   ===================


metadata - specifica il nome del pod che sarà creato

replicas - numero di pod che si vogliono creare

servicename - il nome del servizio a cui si deve/ono agganciare i pod

initContainers - prima di farli partire avvia il commando neccessario per la memoria

selector matchLabels app: elastic - serve dopo per il collegamento col servizio

containers -
    ci sono le variabile per elastic affinche parta, come "node.name che prende il nome da "metadata.name"
    il "discovery.seed_hosts"
    il "cluster.initial_master_nodes"
    "jvm options"

sono tutti neccessari.



===================  elastic-service.yml    ===================

kind: Service - specifica che questo è un servizio

metadata name - è il nome del servizio

selector app: elastic - selezione con cosa comunica, cioè elastic, specificato nel deployment



===================  elastic-ingress.yml    ===================

kind: Ingress - specifica che questo è un ingress

annoations: specifica che tipo di ingress è: nginx etc...

paths:
    può avere 1 o piu path, funziona da reverse proxy, dove nel path
    gli puoi specificare un path, quando l'ip del nodo viene chiamato con quel dato "path" esso reindirizzerà verso il servizio specificato sotto in 
    "service", nel nostro casoo elastic-service, sulla porta 9200



============================================================================

Avviare: minikube start

Avviare deployment, servizio e ingress.

Port forwarding non serve

Per verificare lo stato dei nodi:

curl -X GET http://IP_NODO/_cluster/health

IP_NODO - cliccare su LENS a sx in alto su "Nodes"

(porta non serve)


Per eliminare i volumi:

kubectl get pods

kubectl delete pvc NOME_POD






