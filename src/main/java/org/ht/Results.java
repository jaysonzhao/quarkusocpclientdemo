package org.ht;

import io.fabric8.openshift.api.model.Build;
import io.fabric8.openshift.api.model.BuildRequestBuilder;
import io.fabric8.openshift.client.OpenShiftClient;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/results")
public class Results {

    @Inject
    private OpenShiftClient openshiftClient;

    @GET
    @Path("/{namespace}/{bcname}")
    @Produces(MediaType.TEXT_PLAIN)
    public String changeBG(@PathParam("namespace") String namespace, @PathParam("bcname") String bcname) {
        Build build = openshiftClient.buildConfigs().inNamespace(namespace).withName(bcname).instantiate(new BuildRequestBuilder()
        .withNewMetadata().withName(bcname).endMetadata()
        .build());
        return build.getMetadata().getName();
    }

    @GET
    @Path("/status/{namespace}/{buildname}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getBuildStatus(@PathParam("namespace") String namespace, @PathParam("buildname") String buildname) {
        
        return openshiftClient.builds().inNamespace(namespace).withName(buildname).get().getStatus().toString();
    }

    @GET
    @Path("/route/{namespace}/{routename}/{svcname}")
    @Produces(MediaType.TEXT_PLAIN)
    public String chageRoute(@PathParam("namespace") String namespace, @PathParam("routename") String routename, @PathParam("svcname") String svcname) {
        
        try
           {
              String othername = svcname.equals("new") ? "old" : "new";
              openshiftClient.routes().inNamespace(namespace).withName(routename).patch("{\"spec\": {\"alternateBackends\": [{\"kind\": \"Service\",\"name\": \""+othername+"\",\"weight\": 0}], \"to\":{\"kind\": \"Service\",\"name\": \""+svcname+"\",\"weight\": 100}}}\"");
           }
           catch(Exception e){
               e.printStackTrace();
               return "Fail";
           }

        return "Done";
    }

    @GET
    @Path("/route/{namespace}/{routename}")
    @Produces(MediaType.TEXT_PLAIN)
    public String iniRoute(@PathParam("namespace") String namespace, @PathParam("routename") String routename) {
        
        try
           {
              
              openshiftClient.routes().inNamespace(namespace).withName(routename).patch("{\"spec\": {\"alternateBackends\": [{\"kind\": \"Service\",\"name\": \"new\",\"weight\": 50}], \"to\":{\"kind\": \"Service\",\"name\": \"old\",\"weight\": 50}}}\"");
           }
           catch(Exception e){
               e.printStackTrace();
               return "Fail";
           }

        return "Done";
    }
}