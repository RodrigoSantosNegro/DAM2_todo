import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardDistributer {
    private int playerNum;
    private int cardsQuan;
    private List<String> cards = new ArrayList<>();;

    public CardDistributer(short playerNum, short cardsQuan) {
        if(playerNum<=0 || playerNum>3 || cardsQuan<=0 || cardsQuan>8) throw new RuntimeException();

        this.playerNum = playerNum;
        this.cardsQuan = cardsQuan;
        deckCards();
    }

    public void deckCards(){
        String[] suits = { "O", "C", "E", "B" };
        String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "S", "C", "R" };

        for(String s : suits){
            for (String n: numbers){
                cards.add(s + n);
            }
        }
    }

    public void storeJSON() throws FileNotFoundException {
        JSONObject jason = new JSONObject();
        int cardRand;
        for (int i = 1; i <= playerNum; i++) {
            JSONArray playerHand = new JSONArray(playerNum);
            for (int j = 1; j <= cardsQuan; j++) {
                cardRand = (int) (Math.random()*cards.size());
                playerHand.put(cards.get(cardRand));
                cards.remove(cardRand);
            }
            jason.put("player" + i, playerHand);
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter("assets\\cards.json");
            jason.write(writer, 2, 0);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
