import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class T031TakingTheStoredXML {
    private static List<String> beforeXML = new ArrayList<>();
    private static String randomName;
    private static boolean randomBoolean;

    @BeforeClass
    public static void setTestXML() {
        int num = (int) Math.floor(Math.random()*100);
        randomName = "RandomName" + num;
        randomBoolean = randomBoolean();

        String file = "assets\\cinemaPrefs.xml";
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String newLine = null;
            do {
                newLine = bufferedReader.readLine();
                if (newLine != null) {
                    beforeXML.add(newLine);
                }
            } while (newLine != null);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter1 = new FileWriter(file);
            BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
            bufferedWriter1.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
            bufferedWriter1.newLine();
            bufferedWriter1.write("<Preferences>");
            bufferedWriter1.newLine();
            bufferedWriter1.write("<Username>"+ randomName +"</Username>");
            bufferedWriter1.newLine();
            if (randomBoolean) {
                bufferedWriter1.write("<PrefersDarkMode>true</PrefersDarkMode>");
            } else {
                bufferedWriter1.write("<PrefersDarkMode>false</PrefersDarkMode>");
            }
            bufferedWriter1.newLine();
            bufferedWriter1.write("</Preferences>");
            bufferedWriter1.newLine();
            bufferedWriter1.flush();
            fileWriter1.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testHomeCinemaPreferencesRetrievesUsername() {
        String actualName = "";
        try {
            Class cls = Class.forName("HomeCinemaPreferences");
            Object instance = cls.getDeclaredConstructor(boolean.class).newInstance(true);
            Method usernameGetter = cls.getDeclaredMethod("getUsername");
            actualName = (String) usernameGetter.invoke(instance);
        } catch (ClassNotFoundException e) {
            fail("La clase especificada no existe");
        } catch (NoSuchMethodException e) {
            fail("La clase especificada no contiene un metodo con el nombre indicado o con un constructor apropiado");
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            fail("La clase especificada no puede ser instanciada");
        }
        assertEquals(randomName, actualName);
    }

    @Test
    public void testHomeCinemaPreferencesRetrievesBoolean() {
        boolean actualDarkmode = false;
        try {
            Class cls = Class.forName("HomeCinemaPreferences");
            Object instance = cls.getDeclaredConstructor(boolean.class).newInstance(true);
            Method dGetter = cls.getDeclaredMethod("isDarkModePreferred");
            actualDarkmode = (boolean) dGetter.invoke(instance);
        } catch (ClassNotFoundException e) {
            fail("La clase especificada no existe");
        } catch (NoSuchMethodException e) {
            fail("La clase especificada no contiene un metodo con el nombre indicado o con un constructor apropiado");
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            fail("La clase especificada no puede ser instanciada");
        }
        assertEquals(randomBoolean, actualDarkmode);
    }

    @Test
    public void testHomeCinemaPreferencesDeprecatedConstructor() {
        try {
            Class cls = Class.forName("HomeCinemaPreferences");
            Constructor c = cls.getDeclaredConstructor();
            Annotation[] annotations = c.getDeclaredAnnotations();
            assert(annotations.length > 0);
            assert(annotations[0].annotationType().equals(Deprecated.class));
        } catch (ClassNotFoundException e) {
            fail("La clase especificada no existe");
        } catch (NoSuchMethodException e) {
            fail("La clase especificada no contiene un metodo con el nombre indicado o con un constructor apropiado");
        }
    }

    @AfterClass
    public static void restoreXML() {
        String file ="assets\\cinemaPrefs.xml";
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String line: beforeXML) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush(); // Guardamos el fichero a disco
            fileWriter.close(); // Y lo cerramos
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean randomBoolean() {
        return randomNumberBetween(0, 2) == 0;
    }

    private static int randomNumberBetween(int min, int max) {
        if (max <= min) {
            return 0;
        }
        Random r = new Random();
        int result = r.nextInt(max-min) + min;
        return result;
    }
}
