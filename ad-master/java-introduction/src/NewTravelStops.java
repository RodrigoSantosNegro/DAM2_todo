import java.util.ArrayList;

public class NewTravelStops {
    private ArrayList<String> stops = new ArrayList<>();

    public NewTravelStops() {
        this.stops.add("Kyoto");
        this.stops.add("Singapur");
        this.stops.add("Manila");
    }

    public void printStop(int index){
        System.out.println(this.stops.get(index));
    }

    public void changeStop(int index, String newValue){
        this.stops.set(index, newValue);
    }

    public void addStop(String newValue){
        this.stops.add(newValue);
    }

    public void showAllStops(){
        for (String s: stops) {
            System.out.println(s);
        }
    }
}
