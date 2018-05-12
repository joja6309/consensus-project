
import com.consensus.grid.GridGraphGeneratorImpl;
import org.junit.Test;
import com.consensus.grid.Main;

import java.util.Set;

public class ConsensusNodeTest {
    private static System.Logger consensusNodeTestLogger = System.getLogger("ConsensusNodeTes");

    @Test
    public void nodeTests(){
        GridGraphGeneratorImpl g = new GridGraphGeneratorImpl(){};
        consensusNodeTestLogger.log(System.Logger.Level.INFO, g.hashCode());


    }
}
