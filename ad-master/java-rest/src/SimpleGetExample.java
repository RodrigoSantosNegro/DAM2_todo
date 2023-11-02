import org.restlet.Component;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


public class SimpleGetExample extends ServerResource {
    @Get
    public String toString(){
        return "Hola, mundo";
    }

}
