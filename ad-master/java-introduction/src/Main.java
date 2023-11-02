import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {
        TravelStops ts = new TravelStops();
        HomeCinemaPreferences prefs = new HomeCinemaPreferences();

        ts.printFirstStop();

        ts.changeStop(0, "A Coruña");

        ts.printFirstStop();

        prefs.saveExampleXML();

        // Leemos el XML, cambiamos el nombre y lo escribimos a JSON
        HomeCinemaPreferences prefs1 =
                new HomeCinemaPreferences(HomeCinemaPreferencesMode.MODE_XML);
        prefs1.setUsername("John Carter");
        prefs1.writeAsJSON();
        // Leemos las preferencias del JSON para verificar que son correctas
        HomeCinemaPreferences prefs2 =
                new HomeCinemaPreferences(HomeCinemaPreferencesMode.MODE_JSON);
        System.out.println("Username en .json es " + prefs2.getUsername());


    }
}