package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class TestConfig {

    private static Properties prop;
    private static FileInputStream fs;
    private static String browser = System.getenv("browser");
    private static String env = System.getenv("env");
    private static Properties properties;

     public static String getBrowser(){
        return browser;
    }

    public static String getEnv(){
        return env;
    }

    public static void loadConfig() {
        String fileInfo = "";
        //check the operating system of the user and based on that try to locate the properties files
        if (System.getProperty("os.name").toLowerCase().contains("win")
                || System.getProperty("os.name").toLowerCase().contains("mac")
                || System.getProperty("os.name").toLowerCase().contains("linux")) {
            fileInfo = System.getProperty("user.dir")
                    + File.separator + "src" + File.separator + "test" + File.separator +
                    "resources" + File.separator + "property" + File.separator + "configuration.properties";
        }
        try {
            //Creating object of file input stream
            fs = new FileInputStream(new File(fileInfo));
            //The Properties class provides methods to get data from the properties file and store data into the properties file
            prop = new Properties();
            //loading the data from input steam object and able to get data as key value pair
            prop.load(fs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        if (prop.containsKey(key)) {
            return prop.getProperty(key);
        }
        return null;
    }
}
