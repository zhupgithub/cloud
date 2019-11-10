import com.zhupeng.EsApplication;
import com.zhupeng.entity.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsApplication.class)
public class EsDemoApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * @Description:创建索引，会根据Item类的@Document注解信息来创建
     * @Author: https://blog.csdn.net/chen_2890
     * @Date: 2018/9/29 0:51
     */
    @Test
    public void testCreateIndex() {
        elasticsearchTemplate.createIndex(Goods.class);
    }

    @Test
    public void testDeleteIndex(){
        elasticsearchTemplate.deleteIndex(Goods.class);
        elasticsearchTemplate.deleteIndex("item");
    }
}