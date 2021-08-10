# bluegreen Project

Test it on ocp：

```shell script
oc new-project testbg
oc apply -f src/main/resource/roles.yaml

./mvnw clean package -Dquarkus.container-image.build=true

oc get route
```

Access the route url with  /results/{namespace}/{bc}

```shell script
oc new-app --name=new httpd:2.4-el7~https://github.com/liuxiaoyu-git/Gold-Miner-Game.git#new
oc new-app --name=old httpd:2.4-el7~https://github.com/liuxiaoyu-git/Gold-Miner-Game.git#old
oc expose svc/new --name=rhgame
oc set route-backends rhgame new=50 old=50
//optional manual set
oc patch route/rhgame --patch '{"spec": {"alternateBackends": [{"kind": "Service","name": "new","weight": 0}], "to":{"kind": "Service","name": "new","weight": 100}}}'
```
Access the route url with /results/route/{namespace}/{routename}/{svcname}
