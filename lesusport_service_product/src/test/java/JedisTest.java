import redis.clients.jedis.Jedis;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JedisTest {
//    @Autowired
//    private Jedis jedis;

    @org.junit.Test
    public void getTest() {
//        String pid = jedis.get("pid");
        Jedis jedis = new Jedis("192.168.200.128", 6379);
        System.out.println(jedis.incr("pid"));
    }
}
