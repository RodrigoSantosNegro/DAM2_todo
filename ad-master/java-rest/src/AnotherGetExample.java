import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AnotherGetExample extends ServerResource {
    @Get
    public String toString(){
        return ("To whom it may concern");
    }
}
