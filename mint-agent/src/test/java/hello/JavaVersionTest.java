package hello;


import java.util.Enumeration;
import java.util.Properties;



public class JavaVersionTest {

	public static void main(String[] args) throws Exception {

		Properties props = System.getProperties();
        Enumeration<Object> enumm = props.keys();
        while (enumm.hasMoreElements()) {
            String key = (String) enumm.nextElement();
            String value = (String) props.get(key);
            System.out.println("# " + key + " : " + value);
        }

	}
}
