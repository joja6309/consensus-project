import com.consensus.histogram.App;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class HistogramTest {

    @Test
    public void firstTest() {
        assert(true);

    }
    @Test
    public void testAppHasAGreeting() {
        assert(true);

        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
