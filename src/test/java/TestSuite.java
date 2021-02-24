import model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)

@Suite.SuiteClasses({
        TestAddOrder.class,
        TestAssigncountries.class,
        TestConnectedContinent.class,
        TestContinent.class,
        TestCountryDetails.class,
        TestEditMap.class,
        TestNeighbor.class,
        TestRemoveOrder.class,
        TestValidateMap.class,
        TestConnectedMap.class
})

/**
 *  It runs a collection of test cases.
 */
public class TestSuite {
    //Driver for All Test Cases
}


