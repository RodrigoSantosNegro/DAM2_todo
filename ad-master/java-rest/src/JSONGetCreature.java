import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class JSONGetCreature extends ServerResource {
    @Get
    public StringRepresentation getEndpointResponse(){
        Magic newMagic = new Magic(
                42,
                "Se dice que su aliento ígneo alcanza la temperatura del Sol"
        );
        Creature newFilm = new Creature(
                "Dragón negro de ojos rojos",
                334,
                true,
                newMagic
        );

        String jsonString = newFilm.toJSONObject().toString();
        StringRepresentation representation = new StringRepresentation(jsonString);
        representation.setMediaType(MediaType.APPLICATION_JSON);
        return representation;
    }
}
