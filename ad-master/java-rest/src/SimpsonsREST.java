import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.routing.VirtualHost;

import java.util.ArrayList;

public class SimpsonsREST {
    private Component component;

    public void runServer(){
        try {
            this.component = new Component();
            this.component.getServers().add(Protocol.HTTP, 8126);
            VirtualHost host = this.component.getDefaultHost();
            host.attach("/clips/{clipID}/appearances", GetSimpsons.class);

            this.component.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stopServer() throws Exception {
        if (this.component != null) {
            this.component.stop();
        }
    }
}
