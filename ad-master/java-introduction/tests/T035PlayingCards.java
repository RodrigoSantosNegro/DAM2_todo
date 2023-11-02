import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class T035PlayingCards {
    private static List<String> beforeJSON = new ArrayList<>();

    @BeforeClass
    public static void backupJSONAndClear() {
        String file = "assets\\cards.json";
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String newLine = null;
            do {
                newLine = bufferedReader.readLine();
                if (newLine != null) {
                    beforeJSON.add(newLine);
                }
            } while (newLine != null);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter1 = new FileWriter(file);
            BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
            bufferedWriter1.write("");
            bufferedWriter1.flush();
            fileWriter1.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // 1 Test Constructor raises exception
    @Test
    public void testConstructorException() throws FileNotFoundException {
        try {
            Class cls = Class.forName("CardDistributer");
            Object instance = cls.getDeclaredConstructor(short.class, short.class).newInstance((short) 0, (short) 0);
            fail("Se ha permitido instanciar la clase con 0, 0");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos al instanciar la clase");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("CardDistributer");
            Object instance = cls.getDeclaredConstructor(short.class, short.class).newInstance((short) 1, (short) 0);
            fail("Se ha permitido instanciar la clase con 1, 0");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos al instanciar la clase");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("CardDistributer");
            Object instance = cls.getDeclaredConstructor(short.class, short.class).newInstance((short) 0, (short) 1);
            fail("Se ha permitido instanciar la clase con 0, 1");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos al instanciar la clase");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("CardDistributer");
            Object instance = cls.getDeclaredConstructor(short.class, short.class).newInstance((short) -1, (short) 1);
            fail("Se ha permitido instanciar la clase con -1, 1");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos al instanciar la clase");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("CardDistributer");
            Object instance = cls.getDeclaredConstructor(short.class, short.class).newInstance((short) 1, (short) -1);
            fail("Se ha permitido instanciar la clase con 1, -1");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos al instanciar la clase");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("CardDistributer");
            Object instance = cls.getDeclaredConstructor(short.class, short.class).newInstance((short) (3+1), (short) 8);
            fail("Se ha permitido instanciar la clase numPlayers > permitido");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos al instanciar la clase");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("CardDistributer");
            Object instance = cls.getDeclaredConstructor(short.class, short.class).newInstance((short) 3, (short) (8+1));
            fail("Se ha permitido instanciar la clase con numCards > permitido");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos al instanciar la clase");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("CardDistributer");
            Object instance = cls.getDeclaredConstructor(short.class, short.class).newInstance((short) 3, (short) 8);            assertTrue(true);
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos al instanciar la clase");
        } catch (InvocationTargetException  e) {
            fail("No se ha permitido instanciar la clase con parámetros correctos");
        }
    }

    // 2 Test 3x3 case
    @Test
    public void test3x3() throws FileNotFoundException {
        short numPlayers = 3;
        short numCards = 3;
        testCase(numPlayers, numCards);
    }

    // 3 Test 1x1 case repeatedly
    @Test
    public void test1x1Repeatedly() throws FileNotFoundException {
        short numPlayers = 1;
        short numCards = 1;
        for (int i = 0; i < 20; i++) {
            testCase(numPlayers, numCards);
        }
    }

    // 4 Test max x max case
    @Test
    public void testMaxxMax() throws FileNotFoundException {
        short numPlayers = 3;
        short numCards = 8;
        testCase(numPlayers, numCards);
    }

    // 5 Test 1 x max case
    @Test
    public void test1xMax() throws FileNotFoundException {
        short numPlayers = 1;
        short numCards = 8;
        testCase(numPlayers, numCards);
    }

    // 6 Test max x 1 case
    @Test
    public void testMaxx1() throws FileNotFoundException {
        short numPlayers = 3;
        short numCards = 1;
        testCase(numPlayers, numCards);
    }

    @AfterClass
    public static void restoreJSON() {
        String file = "assets\\cards.json";
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String line: beforeJSON) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void testCase(short numPlayers, short numCards) throws FileNotFoundException {
        try {
            Class cls = Class.forName("CardDistributer");
            Object instance = cls.getDeclaredConstructor(short.class, short.class).newInstance(numPlayers, numCards);
            Method generateJSON = cls.getDeclaredMethod("storeJSON");
            generateJSON.invoke(instance);
        } catch (InvocationTargetException|ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos al instanciar la clase");
        }
        List<String> allCards = getCards();
        List<String> alreadySeenCards = new LinkedList<>();
        JSONTokener tokener = new JSONTokener(new FileReader("assets\\cards.json"));
        JSONObject object = new JSONObject(tokener);
        for (int i = 1; i < numPlayers+1; i++) {
            JSONArray hand = object.getJSONArray("player" + i);
            assertEquals(numCards, hand.length());
            for (int j = 0; j<hand.length(); j++) {
                String card = hand.getString(j);
                // Ensure card is not repeated
                assert(!alreadySeenCards.contains(card));
                // Ensure card is a valid string code
                assert(allCards.contains(card));
                alreadySeenCards.add(card);
            }
        }
    }

    private List<String> getCards() {
        List<String> cards = new ArrayList<>(40);
        cards.add("O1");
        cards.add("O2");
        cards.add("O3");
        cards.add("O4");
        cards.add("O5");
        cards.add("O6");
        cards.add("O7");
        cards.add("OS");
        cards.add("OC");
        cards.add("OR");
        cards.add("C1");
        cards.add("C2");
        cards.add("C3");
        cards.add("C4");
        cards.add("C5");
        cards.add("C6");
        cards.add("C7");
        cards.add("CS");
        cards.add("CC");
        cards.add("CR");
        cards.add("E1");
        cards.add("E2");
        cards.add("E3");
        cards.add("E4");
        cards.add("E5");
        cards.add("E6");
        cards.add("E7");
        cards.add("ES");
        cards.add("EC");
        cards.add("ER");
        cards.add("B1");
        cards.add("B2");
        cards.add("B3");
        cards.add("B4");
        cards.add("B5");
        cards.add("B6");
        cards.add("B7");
        cards.add("BS");
        cards.add("BC");
        cards.add("BR");
        return cards;
    }
}


