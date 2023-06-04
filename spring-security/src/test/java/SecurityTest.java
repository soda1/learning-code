import com.soda.learn.security.SpringSecurityApplication;
import com.soda.learn.security.service.UserDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author soda
 * @date 2021/4/14
 */

@SpringBootTest(classes = SpringSecurityApplication.class)
@RunWith(SpringRunner.class)
public class SecurityTest {

    /**
     * 测试编码
     */
    @Autowired
    UserDetailService userDetailService;

    @Test
    public void encodeTest() {
        User user = (User) User.withDefaultPasswordEncoder()
                .username("libai")
                .password("123")
                .roles("admin")
                .build();
        System.out.println(user.getPassword());
        //匹配密码
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        System.out.println(bCryptPasswordEncoder.encode("123"));
        String encodePassword = user.getPassword().substring(8);
        System.out.println(bCryptPasswordEncoder.matches("123", encodePassword));

    }

    @Test
    public void userService() {
        UserDetails admin = userDetailService.loadUserByUsername("admin");
        System.out.println(admin);
    }
}
