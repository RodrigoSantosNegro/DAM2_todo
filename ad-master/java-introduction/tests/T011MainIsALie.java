import org.junit.Test;
import static org.junit.Assert.*;

public class T011MainIsALie {

    @Test
    public void testClassExists() {
        try {
            Class cls = Class.forName("IUnderstand");
        } catch (ClassNotFoundException e) {
            fail("La clase especificada no existe");
        }
        assertTrue(true);
    }
}

