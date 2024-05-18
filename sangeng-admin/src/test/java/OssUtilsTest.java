import com.sangeng.utils.OssUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = OssUtils.class)
public class OssUtilsTest {
    @Autowired
    private OssUtils ossUtils;

    @Test
    public void testOssUtils() {
        System.out.println(ossUtils.getAccessKey());
        System.out.println(ossUtils.getSecretKey());
        System.out.println(ossUtils.getBucket());
    }
}