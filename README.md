## Spring Boot with Redis 

### Install Docker

> Please install docker on your machine first

### Install kubectl

Look at below link for kubectl installation :
https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/

### Install Helm
> sudo snap install helm --classic

If you use a different OS than Ubuntu look at the link for install instruction :
https://helm.sh/docs/intro/install/

### Install Kind to make a local k8s cluster on your machine

Look at below link to install Kind :
> https://kind.sigs.k8s.io/docs/user/quick-start/#installation

When installation process is finished create a cluster : 
> kind create cluster

### Install Redis via Bitnami Helm charts
> helm repo add bitnami https://charts.bitnami.com/bitnami
> 
> helm repo update
> 
> helm install redis bitnami/redis-cluster

### Build docker image

> gradle clean
> 
> gradle bootJar
> 
> docker build -t springboot-with-redis:1.0 .

### Load your image into your local Kubernetes cluster

> kind load docker-image springboot-with-redis:1.0

If you have multiple clusters you must specify which cluster do you want to load the image:

> kind load docker-image springboot-with-redis:1.0 --name your-cluster-name

If you want to delete loaded images from Kind cluster do the followings:

> docker exec -it kind-control-plane bash
> 
> ctr -n=k8s.io images ls | grep springboot-with-redis | cut -f1 -d" " | xargs ctr -n=k8s.io images rm

For more info about loading your image into local k8s cluster look at here: https://bytegoblin.io/blog/how-to-run-locally-built-docker-images-in-kubernetes

### Deploy the app into kubernetes

> kubectl create deployment springboot-with-redis --image=springboot-with-redis:1.0 --dry-run=client -o=yaml > deployment.yaml
> 
> echo --- >> deployment.yaml
> 
> kubectl create service clusterip springboot-with-redis --tcp=8080:8080 --dry-run=client -o=yaml >> deployment.yaml
> 
> kubectl apply -f deployment.yaml
 
### Test deployed app

Run following command to access your app from inside kubernetes

> kubectl port-forward --namespace default svc/springboot-with-redis 8080:8080

Then send a request like this :

> POST http://localhost:8080/data?key=name&value=flag HTTP/2

You can run redis.http which is in test/resources directory.

For more investigation in your redis cluster :

1. Run a Redis&reg; pod that you can use as a client:
   kubectl run --namespace default redis-redis-cluster-client --rm --tty -i --restart='Never' \
   --env REDIS_PASSWORD=$REDIS_PASSWORD \
   --image docker.io/bitnami/redis-cluster:8.0.2-debian-12-r2 -- bash

2. Connect using the Redis&reg; CLI:

redis-cli -c -h redis-redis-cluster -a $REDIS_PASSWORD
