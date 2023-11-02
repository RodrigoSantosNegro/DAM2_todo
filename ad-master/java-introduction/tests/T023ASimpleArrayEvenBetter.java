import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class T023ASimpleArrayEvenBetter {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream(), errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out, originalErr = System.err;

    @BeforeClass
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testPrintAllMethod() {
        try {
            Class cls = Class.forName("TravelStops");
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method printStop = cls.getDeclaredMethod("showAllStops");
            printStop.invoke(instance);
        } catch (ClassNotFoundException e) {
            fail("La clase especificada no existe");
        } catch (NoSuchMethodException e) {
            fail("La clase especificada no contiene un metodo con el nombre indicado o con un constructor apropiado");
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            fail("La clase especificada no puede ser instanciada");
        }
        assertTrue(outContent.toString().startsWith("Tokyo\r\nFrankfurt\r\n"));
    }

    @AfterClass
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}

