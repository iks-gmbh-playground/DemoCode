package de.test.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.test.SayHelloIntegrationTest;
import de.test.SmallTalkCommentIntegrationVariant1Test;
import de.test.SmallTalkCommentIntegrationVariant2Test;


// Executes integration tests on component level 

@RunWith(Suite.class)
@SuiteClasses({ SayHelloIntegrationTest.class, 
	            SmallTalkCommentIntegrationVariant1Test.class,
	            SmallTalkCommentIntegrationVariant2Test.class})
public class ComponentLevelTestSuite {

}
