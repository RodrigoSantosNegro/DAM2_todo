import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class JSONGetNewCompany extends ServerResource {
    @Get
    public StringRepresentation getEndpointResponse(){
        GrossProfit profit = new GrossProfit(
                2019,
                5004573,
                "PLN"
        );
        Company newCompany = new Company(
                "Netflux",
                "Pepe Depura",
                profit
        );
        String jsonString = newCompany.toJSONObject().toString();
        StringRepresentation representation = new StringRepresentation(jsonString);
        representation.setMediaType(MediaType.APPLICATION_JSON);
        return representation;
    }
}
