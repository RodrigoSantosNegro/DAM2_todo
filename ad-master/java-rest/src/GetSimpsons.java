import org.json.JSONArray;
import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;

public class GetSimpsons extends ServerResource {

    @Get
    public StringRepresentation getEndpointResponse(){
        JSONArray finalResult = new JSONArray();
        SimpsonsConnector connector = new SimpsonsConnector();
        int milliseconds = Integer.parseInt(getQueryValue("milliseconds"));
        int clipId = Integer.parseInt(getAttribute("clipID"));

        ArrayList<Character> simpsonsArray = connector.identifySimpsons(milliseconds, clipId);
        for(Character c : simpsonsArray){
            finalResult.put(c.toJSONObject());
        }

        connector.closeConnection();
        String jsonString = finalResult.toString();
        StringRepresentation representation = new StringRepresentation(jsonString);
        representation.setMediaType(MediaType.APPLICATION_JSON);
        return representation;
    }
}
