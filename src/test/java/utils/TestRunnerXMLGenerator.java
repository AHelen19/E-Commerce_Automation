package utils;

import org.reflections.Reflections;
import org.testng.IAlterSuiteListener;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import tests.TestBase;

import java.util.*;

public class TestRunnerXMLGenerator implements IAlterSuiteListener {

    @Override
    public void alter(List suites) {
        XmlSuite suite = (XmlSuite) suites.get(0);
        XmlTest xmlTest = new XmlTest(suite);
        xmlTest.setName("Tests");
        List<XmlClass> listTestClasses = new ArrayList<XmlClass>();

//       Map<String,String> testngParams = new HashMap<>();
//        testngParams.put("browser",TestConfig.getBrowser());
//        testngParams.put("env",TestConfig.getEnv());

        //Add any parameters that you want to set to the Test.
        //       xmlTest.setParameters(testngParams);

     /*   try {
            String groups = System.getenv("groups");
            if (!groups.equals("")) {
                if (!groups.contains(",")) {
                    groups = groups + ",";
                }
                List<String> groupList = Arrays.asList(groups.split(","));
                for (String group : groupList) {
                    xmlTest.addIncludedGroup(group);
                }
            }
        } catch (Exception e) {
        }
*/
        //Getting all Test Classes of Type TestBase
        Reflections reflections = new Reflections("tests");
        Set<Class<? extends TestBase>> classes = reflections.getSubTypesOf(TestBase.class);

        for (Class clazz : classes) {
            listTestClasses.add(new XmlClass(clazz.getCanonicalName()));
        }
        xmlTest.setXmlClasses(listTestClasses);
        suite.setParallel(XmlSuite.ParallelMode.CLASSES);
    }
}