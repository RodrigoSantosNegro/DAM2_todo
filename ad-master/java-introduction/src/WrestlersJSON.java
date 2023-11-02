import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class WrestlersJSON {

    public void readWrestlers() throws FileNotFoundException {
        FileReader reader = new FileReader("assets\\wrestlers.json");
        JSONTokener tokener = new JSONTokener(reader);
        JSONArray jsonArray = new JSONArray(tokener);

        for (int i = 0; i < jsonArray.length(); i++) {
            String wrestlerName = jsonArray.getString(i);
            System.out.println(wrestlerName);
        }

    }
}
