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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import static org.junit.Assert.*;

public class T030SaveAsXML {

    @BeforeClass
    public static void clearXMLs() {
        String file1 = "assets\\example.xml";
        String file2 = "assets\\cinemaPrefs.xml";
        try {
            FileWriter fileWriter1 = new FileWriter(file1);
            BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
            bufferedWriter1.write("");
            bufferedWriter1.flush();
            fileWriter1.close();
            FileWriter fileWriter2 = new FileWriter(file2);
            BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
            bufferedWriter2.write("");
            bufferedWriter2.flush();
            fileWriter2.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testHomeCinemaPreferencesWriteExample() {
        try {
            Class cls = Class.forName("HomeCinemaPreferences");
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method saveToFile = cls.getDeclaredMethod("saveExampleXML");
            saveToFile.invoke(instance);
        } catch (ClassNotFoundException e) {
            fail("La clase especificada no existe");
        } catch (NoSuchMethodException e) {
            fail("La clase especificada no contiene un metodo con el nombre indicado o con un constructor apropiado");
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            fail("La clase especificada no puede ser instanciada");
        }
        assertXMLContainsLevel0NodeWithValue("assets\\example.xml", "Student");
        assertXMLContainsLevel1NodeWithTextContent("assets\\example.xml", "Name", "Pepe Depura");
        assertXMLContainsLevel1NodeWithTextContent("assets\\example.xml", "IsLearning", "true");
    }

    @Test
    public void testHomeCinemaPreferencesStoresUsername() {
        int num = (int) Math.floor(Math.random()*100);
        String randomName = "RandomName" + num;
        try {
            Class cls = Class.forName("HomeCinemaPreferences");
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method usernameSetter = cls.getDeclaredMethod("setUsername", String.class);
            Method saveToFile = cls.getDeclaredMethod("writeAsXML");
            usernameSetter.invoke(instance, randomName);
            saveToFile.invoke(instance);
        } catch (ClassNotFoundException e) {
            fail("La clase especificada no existe");
        } catch (NoSuchMethodException e) {
            fail("La clase especificada no contiene un metodo con el nombre indicado o con un constructor apropiado");
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            fail("La clase especificada no puede ser instanciada");
        }
        assertXMLContainsLevel0NodeWithValue("assets\\cinemaPrefs.xml", "Preferences");
        assertXMLContainsLevel1NodeWithTextContent("assets\\cinemaPrefs.xml", "Username", randomName);
    }

    @Test
    public void testHomeCinemaPreferencesStoresDarkMode() {
        boolean darkMode = randomBoolean();
        String darkModeString;
        if (darkMode) {
            darkModeString = "true";
        } else {
            darkModeString = "false";
        }
        try {
            Class cls = Class.forName("HomeCinemaPreferences");
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method darkModeSetter = cls.getDeclaredMethod("setDarkModePreferred", boolean.class);
            Method saveToFile = cls.getDeclaredMethod("writeAsXML");
            darkModeSetter.invoke(instance, darkMode);
            saveToFile.invoke(instance);
        } catch (ClassNotFoundException e) {
            fail("La clase especificada no existe");
        } catch (NoSuchMethodException e) {
            fail("La clase especificada no contiene un metodo con el nombre indicado o con un constructor apropiado");
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            fail("La clase especificada no puede ser instanciada");
        }
        assertXMLContainsLevel0NodeWithValue("assets\\cinemaPrefs.xml", "Preferences");
        assertXMLContainsLevel1NodeWithTextContent("assets\\cinemaPrefs.xml", "PrefersDarkMode", darkModeString);
    }

    private void assertXMLContainsLevel0NodeWithValue(String xmlFile, String nodeName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xmlDocument = builder.parse(xmlFile);
            Element rootElement = xmlDocument.getDocumentElement();
            assertEquals(nodeName, rootElement.getNodeName());
        } catch (IOException | SAXException | ParserConfigurationException e) {
            fail("There was an exception");
        }
    }

    private void assertXMLContainsLevel1NodeWithTextContent(String xmlFile, String nodeName, String content) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xmlDocument = builder.parse(xmlFile);

            Element rootElement = xmlDocument.getDocumentElement();
            NodeList childNodes = rootElement.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node child = childNodes.item(i);
                if (child.getNodeName().equals(nodeName)) {
                    assertEquals(content, child.getTextContent());
                    return;
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            fail("There was an exception");
        }
        fail("The searched node " + nodeName + " was not found");
    }

    private boolean randomBoolean() {
        return randomNumberBetween(0, 2) == 0;
    }

    private int randomNumberBetween(int min, int max) {
        if (max <= min) {
            return 0;
        }
        Random r = new Random();
        int result = r.nextInt(max-min) + min;
        return result;
    }
}

