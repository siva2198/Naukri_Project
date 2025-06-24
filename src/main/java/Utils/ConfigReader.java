package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Author: Sivaraman M
 * User:Sivaraman M
 */
public class ConfigReader {
    private static Properties prop;
    public static void initProperties(){
        prop = new Properties();
        try{
            FileInputStream fis = new FileInputStream("config.properties");
            prop.load(fis);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static String get(String key){
        return prop.getProperty(key);
    }

}
