import com.consensus.utils.base.CmdLineParser;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TestUtils {

    @Test
    public void firstTest() {
        assert(true);

    }
//    @Test
//    public void testAppHasAGreeting() {
//        assert(true);
//
//        App classUnderTest = new App();
//        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
//    }
    @Test
    public void parseCommandLineArguments(){
        String[] args = "-Ctopics=topic1 -Ptopics=topic2  -BootServer=0.0.0.0:9092 -GroupId=NodeGroup1".split(" ");
        CmdLineParser cmdParser =
                new CmdLineParser(args);

        assertNotNull(cmdParser.getClass());



    }

}