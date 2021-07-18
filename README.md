# bluegreen Project

Test it on ocp：

```shell script
oc new-project testbg
oc apply -f src/resource/roles.yaml

./mvnw clean package -Dquarkus.container-image.build=true

oc get route
```

Access the route url with  /{namespace}/{bc}



