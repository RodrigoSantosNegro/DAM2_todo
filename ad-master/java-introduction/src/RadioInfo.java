import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RadioInfo {
    private List<RadioStation> stations;

    public RadioInfo() {
        stations = new ArrayList<>();
        stations.add(new RadioStation("Kiss FM", 88.0));
        stations.add(new RadioStation("Cadena 100", 88.7));
        stations.add(new RadioStation("Onda Cero", 89.2));
        stations.add(new RadioStation("Los 40", 91.0));
        stations.add(new RadioStation("COPE", 96.9));
        stations.add(new RadioStation("Los 40 Classic", 97.6));
        stations.add(new RadioStation("Hit", 100.7));
        stations.add(new RadioStation("esRadio", 102.7));
        stations.add(new RadioStation("Rock FM", 103.2));
        stations.add(new RadioStation("Cuac FM", 103.4));
        stations.add(new RadioStation("Radio Galega", 104.8));
    }

    public void storeJSON(double minFrequency, double maxFrequency) {
        if (minFrequency <= 0 || minFrequency >= maxFrequency) {
            throw new RuntimeException();
        }

        JSONArray jasonArray = new JSONArray();
        for (RadioStation station : stations) {
            double frequency = station.getFrequency();
            if (frequency >= minFrequency && frequency <= maxFrequency) {
                JSONObject stationObject = new JSONObject();
                stationObject.put("radioName", station.getName());
                stationObject.put("radioFrequency", frequency);
                jasonArray.put(stationObject);
            }
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter("assets\\radios.json");
            jasonArray.write(writer, 2, 0);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}