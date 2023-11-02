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

public class T036RadioFrequencies {
    private static List<String> beforeJSON = new ArrayList<>();

    @BeforeClass
    public static void backupJSONAndClear() {
        String file = "assets\\radios.json";
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

    // 1 Test Method raises exception
    @Test
    public void testConstructorException() {
        try {
            Class cls = Class.forName("RadioInfo");
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method method = cls.getDeclaredMethod("storeJSON", double.class, double.class);
            method.invoke(instance, (short) 0, (short) 0);
            fail("Se ha permitido invocar el método con min=0, max=0");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("RadioInfo");
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method method = cls.getDeclaredMethod("storeJSON", double.class, double.class);
            method.invoke(instance, (short) 10, (short) 0);
            fail("Se ha permitido invocar el método con min=10, max=0");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("RadioInfo");
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method method = cls.getDeclaredMethod("storeJSON", double.class, double.class);
            method.invoke(instance, (short) 0, (short) 10);
            fail("Se ha permitido invocar el método con min=0, max=10");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("RadioInfo");
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method method = cls.getDeclaredMethod("storeJSON", double.class, double.class);
            method.invoke(instance, (short) -1, (short) 0);
            fail("Se ha permitido invocar el método con min=-1, max=0");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("RadioInfo");
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method method = cls.getDeclaredMethod("storeJSON", double.class, double.class);
            method.invoke(instance, (short) 50, (short) 40);
            fail("Se ha permitido invocar el método con min>max");
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos");
        } catch (InvocationTargetException  e) {
            assertTrue(true);
        }
        try {
            Class cls = Class.forName("RadioInfo");
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method method = cls.getDeclaredMethod("storeJSON", double.class, double.class);
            method.invoke(instance, (short) 50, (short) 120);
            assertTrue(true);
        } catch (ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos");
        } catch (InvocationTargetException  e) {
            fail("No se ha permitido invocar el método con parámetros correctos");
        }
    }

    // 2 Test no results, low bound
    @Test
    public void testNoResults1() throws FileNotFoundException {
        double minF = 1;
        double maxF = 87;
        testCase(minF, maxF, 0);
    }

    // 3 Test no results, upper bound
    @Test
    public void testNoResults2() throws FileNotFoundException {
        double minF = 105;
        double maxF = 200;
        testCase(minF, maxF, 0);
    }

    // 4 Test all results
    @Test
    public void testAllResults() throws FileNotFoundException {
        double minF = 87.9;
        double maxF = 104.9;
        testCase(minF, maxF, 11);
    }

    // 5 Test some results (I)
    @Test
    public void testSomeResults1() throws FileNotFoundException {
        double minF = 88.5;
        double maxF = 102.6;
        testCase(minF, maxF, 6);
    }

    // 6 Test some results (II)
    @Test
    public void testSomeResults2() throws FileNotFoundException {
        double minF = 103.1;
        double maxF = 103.5;
        testCase(minF, maxF, 2);
    }

    @AfterClass
    public static void restoreJSON() {
        String file = "assets\\radios.json";
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

    private void testCase(double minFrequency, double maxFrequency, int expectedLength) throws FileNotFoundException {
        try {
            Class cls = Class.forName("RadioInfo");
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method method = cls.getDeclaredMethod("storeJSON", double.class, double.class);
            method.invoke(instance, minFrequency, maxFrequency);
        } catch (InvocationTargetException|ClassNotFoundException |NoSuchMethodException| IllegalAccessException | InstantiationException e) {
            fail("Problemas genéricos al instanciar la clase");
        }
        List<RadioStationInnerTestClass> allStations = allStations();
        JSONTokener tokener = new JSONTokener(new FileReader("assets\\radios.json"));
        JSONArray array = new JSONArray(tokener);
        assertEquals(expectedLength, array.length());
        for (int i = 0; i < array.length(); i++) {
            JSONObject someRadio = array.getJSONObject(i);
            String someRadioName = someRadio.getString("radioName");
            Double someRadioFrequency = someRadio.getDouble("radioFrequency");
            assert(nameExistsInList(someRadioName, allStations));
            assert(frequencyExistsInList(someRadioFrequency, allStations));
            assert(someRadioFrequency > minFrequency);
            assert(someRadioFrequency < maxFrequency);
        }
    }

    private boolean nameExistsInList(String name, List<RadioStationInnerTestClass> list) {
        for (RadioStationInnerTestClass r : list) {
            if (r.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean frequencyExistsInList(Double freq, List<RadioStationInnerTestClass> list) {
        for (RadioStationInnerTestClass r : list) {
            if (Math.abs(r.getFrequency() - freq) < 0.01) {
                return true;
            }
        }
        return false;
    }

    private List<RadioStationInnerTestClass> allStations() {
        List<RadioStationInnerTestClass> stations = new ArrayList<>(11);
        stations.add(new RadioStationInnerTestClass("Kiss FM", 88.0));
        stations.add(new RadioStationInnerTestClass("Cadena 100", 88.7));
        stations.add(new RadioStationInnerTestClass("Onda Cero", 89.2));
        stations.add(new RadioStationInnerTestClass("Los 40", 91.0));
        stations.add(new RadioStationInnerTestClass("COPE", 96.9));
        stations.add(new RadioStationInnerTestClass("Los 40 Classic", 97.6));
        stations.add(new RadioStationInnerTestClass("Hit", 100.7));
        stations.add(new RadioStationInnerTestClass("esRadio", 102.7));
        stations.add(new RadioStationInnerTestClass("Rock FM", 103.2));
        stations.add(new RadioStationInnerTestClass("Cuac FM", 103.4));
        stations.add(new RadioStationInnerTestClass("Radio Galega", 104.8));
        return stations;
    }

    class RadioStationInnerTestClass {
        private String name;
        private Double frequency;

        public RadioStationInnerTestClass(String name, Double frequency) {
            this.name = name;
            this.frequency = frequency;
        }

        public String getName() {
            return name;
        }

        public Double getFrequency() {
            return frequency;
        }
    }
}


