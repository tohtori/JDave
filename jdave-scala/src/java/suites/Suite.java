package suites;

import jdave.junit4.JDaveGroupRunner;
import jdave.junit4.SpecDirs;
import jdave.runner.Groups;

import org.junit.runner.RunWith;

@RunWith(JDaveGroupRunner.class)
@Groups(include=Groups.ALL)
@SpecDirs("target/classes")
public class Suite {

}
