import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class JSONGetAnotherExample extends ServerResource {
    @Get
    public StringRepresentation getEndpointResponse(){
        JSONObject json = new JSONObject();
        json.put("characterPopularity", "12");
        json.put("characterDescription", "Homer J. Simpson es un hombre de muchas agallas, pero con un punto débil: Las rosquillas");
        String jsonString = json.toString();
        StringRepresentation representation = new StringRepresentation(jsonString);
        representation.setMediaType(MediaType.APPLICATION_JSON);
        return representation;
    }
}
