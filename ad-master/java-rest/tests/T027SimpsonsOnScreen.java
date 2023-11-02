import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class T027SimpsonsOnScreen {
    private Method stopServer = null;
    private Object currentServerInstance = null;

    @Test
    public void testClip1RandomTimestampIsCorrect() {
        int randomMilliseconds = (int) Math.floor(Math.random()*33000)+2000;
        testResultContainsAllSimpsons(1, randomMilliseconds);
    }

    @Test
    public void testClip2RandomTimestampIsCorrect() {
        int randomMilliseconds = (int) Math.floor(Math.random()*13000)+3000;
        testResultContainsAllSimpsons(2, randomMilliseconds);
    }

    @Test
    public void testClip3RandomTimestampIsCorrect() {
        int randomMilliseconds = (int) Math.floor(Math.random()*17000)+1000;
        testResultContainsAllSimpsons(3, randomMilliseconds);
    }

    @Test
    public void testClip4RandomTimestampIsCorrect() {
        int randomMilliseconds = (int) Math.floor(Math.random()*53000);
        testResultContainsAllSimpsons(4, randomMilliseconds);
    }

    @Test
    public void testClip5RandomTimestampIsCorrect() {
        int randomMilliseconds = (int) Math.floor(Math.random()*31000);
        testResultContainsAllSimpsons(5, randomMilliseconds);
    }

    @Test
    public void testEmptyResult() {
        testResultContainsAllSimpsons(1, 1000);
    }

    @Test
    public void testJudgeWhoHasNoSurnameNorDescription() {
        testResultContainsAllSimpsons(3, 11000);
    }

    @Test
    public void testNegativeQueryParam() {
        testResultContainsAllSimpsons(1, -1000);
    }

    @After
    public void teardownServer() {
        try {
            if ((stopServer != null) && (this.currentServerInstance != null)) {
                stopServer.invoke(this.currentServerInstance);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void testResultContainsAllSimpsons(int clipId, int milliseconds) {
        try {
            Class cls = Class.forName("SimpsonsREST");
            this.currentServerInstance = cls.getDeclaredConstructor().newInstance();
            this.stopServer = cls.getDeclaredMethod("stopServer");
            Method runServer = cls.getDeclaredMethod("runServer");
            runServer.invoke(this.currentServerInstance);
            List<TestCharacter> expected = selectCharacters(clipId, milliseconds);
            System.out.println("For clipId="+clipId+", milliseconds="+milliseconds+"; we expect the following characters:");
            for (TestCharacter c : expected) {
                System.out.println(c);
            }
            JSONArray result = getUrl("http://localhost:8126/clips/"+clipId+"/appearances?milliseconds="+milliseconds);
            assert(result != null);
            assertEquals(result.length(), expected.size());
            for (TestCharacter t : expected) {
                assert(existsInJSONArray(result, t));
            }
            this.stopServer.invoke(this.currentServerInstance);
        } catch (ClassNotFoundException e) {
            fail("La clase especificada no existe");
        } catch (NoSuchMethodException e) {
            fail("La clase especificada no contiene un metodo con el nombre indicado o con un constructor apropiado");
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            fail("Puede que el servidor de Main.java siga activo. Dale a STOP (recuadro rojo) en la barra superior, y re-lanza el test");
        }
    }

    private JSONArray getUrl(String url) {
        try {
            URL endpointURL = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) endpointURL.openConnection();
            String contentType = httpConnection.getHeaderField("Content-Type");
            assertTrue(contentType.contains("application/json"));
            InputStream is = httpConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            is.close();
            return new JSONArray(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            fail("JSON was malformed or couldn't connect to server (or other bad thing happened)");
        }
        return null;
    }

    private List<TestCharacter> selectCharacters(int clipId, int milliseconds) {
        String sql = """
                SELECT name, surname, description, imageUrl
                FROM TCharacters JOIN TAppearances ON TCharacters.id=TAppearances.characterId JOIN TClips ON TAppearances.clipId=TClips.id
                WHERE TClips.id =""";
        sql+=clipId;
        sql+=" AND TAppearances.startMilliseconds <= "+milliseconds+" AND TAppearances.endMilliseconds >= "+milliseconds;
        List<TestCharacter> allAppearances = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/sqlite3/simpsons.db");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                String name = result.getString(1);
                String surname = result.getString(2);
                String description = result.getString(3);
                String imageUrl = result.getString(4);
                TestCharacter c = new TestCharacter(name, surname, description, imageUrl);
                allAppearances.add(c);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allAppearances;
    }

    private boolean existsInJSONArray(JSONArray array, TestCharacter expected) {
        for (int i = 0; i < array.length(); i++) {
            TestCharacter current = new TestCharacter(array.getJSONObject(i));
            if (current.equals(expected)) {
                return true;
            }
        }
        return false;
    }

    class TestCharacter {
        private String name;
        private String surname;
        private String description;
        private String imageUrl;

        public TestCharacter(String name, String surname, String description, String imageUrl) {
            this.name = name;
            this.surname = surname;
            this.description = description;
            this.imageUrl = imageUrl;
        }

        TestCharacter(JSONObject o) {
            try {
                this.name = o.getString("firstName");
                this.imageUrl = o.getString("imageUrl");
                this.surname = o.getString("characterSurname");
                this.description = o.getString("description");
            } catch (JSONException e) {
                // Probably expected if it's the Simpsons judge (jueza) who has no surname or description in the database
                // Could mean other serialization errors
                System.out.println("A JSONException was caught. This can be expected or not. Please review your JSON keys");
                System.out.println(e.getMessage());
            }
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestCharacter that = (TestCharacter) o;
            return name.equals(that.name) && Objects.equals(surname, that.surname) && Objects.equals(description, that.description) && imageUrl.equals(that.imageUrl);
        }
    }
}

