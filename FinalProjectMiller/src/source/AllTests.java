package source;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({RepairTaskTest.class, CarTest.class, RepairManagerTest.class})
public class AllTests {
}
