
import com.xiaomi.bean.Usermsg;
import com.xiaomi.service.TestService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * @Author: xiaomi
 * @Description:
 * @Date:下午 5:14 2018/6/21 0021
 * @Email:a1205938863@gmail.com
 **/
public class ConfigTest  extends BaseJunit4Test {
    @Autowired
    private TestService testService;
    @Test
    public   void getResult(){
        System.out.println("123");
        List<Usermsg> info = testService.getInfo();
    }

}
