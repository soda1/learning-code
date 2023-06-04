import com.soda.learn.SpringDemo01;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

@SpringBootTest
public class SpringDemo01Test {

    @Test
    public void test01() {
        //测试getResources
        try {
            Enumeration<URL> resources = SpringDemo01.class.getClassLoader().getResources("META-INF/spring.factories");
            while (resources.hasMoreElements()) {
                System.out.println(resources.nextElement());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
