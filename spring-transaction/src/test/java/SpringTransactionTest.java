import com.soda.learn.SpringTransactionApplication;
import com.soda.learn.service.MyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = SpringTransactionApplication.class)
@RunWith(SpringRunner.class)
public class SpringTransactionTest {

    @Autowired
    MyService service;

    @Test
    public void test01() {
        service.insertUser("dddd", "dao");
    }


    @Test
    public void test02() {
        service.invokeInsertUser();
    }
}
