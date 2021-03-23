import model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)

@Suite.SuiteClasses({
        TestAddOrder.class,
        TestAssigncountries.class,
        TestAssignReinforcements.class,
        TestConnectedContinent.class,
        TestContinent.class,
        TestCountryDetails.class,
        TestEditMap.class,
        TestLoadMap.class,
        TestNeighbor.class,
        TestRemoveOrder.class,
        TestValidateMap.class,
        TestConnectedMap.class,
        TestOrderCapability.class,
        TestDeploy.class,
        TestBlockade.class,
        TestNeutralTerritory.class,
        TestAirlift.class,
        TestBomb.class,
        TestAdjacent.class,
        TestNotNeighbour.class
})

/**
 *  It runs a collection of test cases.
 */
public class TestSuite {
    //Driver for All Test Cases
}


