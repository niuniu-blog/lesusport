import com.haichuang.lesusport.dao.product.ProductDao;
import com.haichuang.lesusport.pojo.Test;
import com.haichuang.lesusport.pojo.product.Product;
import com.haichuang.lesusport.service.test.TestService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ZhengHeTest {
    @Autowired
    private TestService testService;
    @Autowired
    private ProductDao productDao;

    @org.junit.Test
    public void saveTest() {
        Test test = new Test();
        test.setName("aaa");
        testService.saveTest(test);
    }

    @org.junit.Test
    public void getProduct() {
        Product product = productDao.selectByPrimaryKey(15L);
        System.out.println(product);
    }
}
