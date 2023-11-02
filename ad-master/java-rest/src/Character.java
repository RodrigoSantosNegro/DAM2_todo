import org.json.JSONObject;

import java.util.ArrayList;

public class Character {

    private String firstName;
    private String characterSurname;
    private String description;
    private String imageUrl;

    public Character(String firstName, String characterSurname, String description, String imageUrl){
        this.firstName = firstName;
        this.characterSurname = characterSurname;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public JSONObject toJSONObject(){
        JSONObject object = new JSONObject();
        object.put("firstName", this.firstName);
        object.put("characterSurname", this.characterSurname);
        object.put("description", this.description);
        object.put("imageUrl", this.imageUrl);

        return object;
    }
}
