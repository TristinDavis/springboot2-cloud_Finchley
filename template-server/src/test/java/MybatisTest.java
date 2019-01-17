//import com.aha.tech.Application;
//import com.aha.tech.model.entity.ReadEntity;
//import com.aha.tech.model.entity.UserEntity;
//import com.aha.tech.repository.dao.example.UserMapperExample;
//import com.aha.tech.repository.dao.read.ReadMapper;
//import com.aha.tech.repository.dao.readwrite.UserMapper;
//import com.github.pagehelper.PageHelper;
//import org.apache.commons.lang3.RandomUtils;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//
///**
// * @Author: luweihong
// * @Date: 2018/7/27
// */
//@RunWith(SpringRunner.class)
//@ActiveProfiles("dev")
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class MybatisTest {
//
//    private Logger LOGGER = LoggerFactory.getLogger(MybatisTest.class);
//
//    @Resource
//    private UserMapper userMapper;
//
//    @Resource
//    private ReadMapper readMapper;
//
//    @Before
//    public void readMe() {
//        LOGGER.info("运行本用例,需要保证test.sql脚本运行成功,并且检查2个数据源是否配置正确");
//        LOGGER.info("application-${profile}.properties中的 readdb.enable = on 记得要配置");
//    }
//
//    @Test
//    public void crud() {
//        Long id = RandomUtils.nextLong();
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUserId(id);
//        userEntity.setName("monkey");
//        userEntity.setCreatedAt(new Date());
//        userEntity.setUpdatedAt(new Date());
//
//        LOGGER.debug("开始插入 userEntity : {} ", userEntity);
//        int insetRow = userMapper.insert(userEntity);
//        Assert.assertTrue("插入失败", insetRow > 0);
//
//        UserEntity deleteEntity = new UserEntity();
//        deleteEntity.setUserId(id);
//        int deleteRow = userMapper.delete(deleteEntity);
//        Assert.assertTrue("删除失败", deleteRow > 0);
//
//        UserEntity updateEntity = new UserEntity();
//        updateEntity.setUserId(3L);
//        updateEntity.setName("xxxxxxxxx");
//        int updateRow = userMapper.updateByPrimaryKeySelective(updateEntity);
//        Assert.assertTrue("修改失败", updateRow > 0);
//
//    }
//
//    @Test
//    public void deleteByPrimaryKey() {
//        int row = userMapper.deleteByPrimaryKey(2);
//        Assert.assertTrue("删除失败", row > 0);
//    }
//
//    @Test
//    public void update() {
//        UserEntity updateEntity = new UserEntity();
//        updateEntity.setUserId(3L);
//        updateEntity.setName("dddd");
//        updateEntity.setUpdatedAt(new Date(1514779200000L));
//        int updateRow = userMapper.updateByPrimaryKey(updateEntity);
//        Assert.assertTrue("修改失败", updateRow > 0);
//    }
//
//    /**
//     * findOne
//     */
//    @Test
//    public void findOne() {
//        Long userId = 1L;
//        UserEntity userEntity = userMapper.selectByPrimaryKey(userId);
//        LOGGER.debug("user : {}", userEntity);
//        Assert.assertTrue("未查询到数据", userEntity == null);
//    }
//
//    /**
//     * 分页测试
//     */
//    @Test
//    public void page() {
//        LOGGER.debug("运行前提是库里的,t_user行数超过10,用例将会使用分页插件,不需要在sql中指定limit等方法");
//        // 一页10条
//        Integer limit = 10;
//        // 从第几条开始取,第一条的offset = 0
//        Integer offset = 0;
//        PageHelper.offsetPage(offset, limit);
//        // 声明page分页,声明后下一条sql将按照此逻辑分页,取数成功后失效
//        PageHelper.offsetPage(offset, limit);
//        List<UserEntity> userEntityList = userMapper.selectAll();
//        Assert.assertTrue("page plugin error,may be not really error !", userEntityList.size() == limit);
//
//
//        PageHelper.offsetPage(1, limit);
//        List<ReadEntity> readEntityList = readMapper.selectAll();
//        Assert.assertTrue("page plugin error,may be not really error !", readEntityList.size() == limit);
//        LOGGER.debug("page : {} ", readEntityList);
//    }
//
//    @Test
//    public void dataSourceTest() {
//        LOGGER.debug("多数据源测试,在一个线程中操作2个datasource");
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUserId(RandomUtils.nextLong());
//        userEntity.setName("读写库的资源");
//        int readWriteRow = userMapper.insert(userEntity);
//        Assert.assertTrue("read write db insert failure", readWriteRow > 0);
//
//
//        ReadEntity readEntity = new ReadEntity();
//        readEntity.setUserId(RandomUtils.nextLong());
//        readEntity.setAge(RandomUtils.nextInt());
//        int readRow = readMapper.insert(readEntity);
//        Assert.assertTrue("read db insert failure", readRow > 0);
//    }
//
//
//    @Test
//    @Transactional(value = "readwriteTransactionManager")
//    public void readWriteTransaction() {
//        LOGGER.debug("指定读写库的transactional");
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUserId(RandomUtils.nextLong());
//        userEntity.setName("插入一条记录,然后让其异常");
//        int readWriteRow = userMapper.insert(userEntity);
//        Assert.assertTrue("read write db insert failure", readWriteRow > 0);
//        Object o = null;
//        o.toString();
//    }
//
//    @Test
//    @Transactional(value = "readTransactionManager")
//    public void readTransaction() {
//        LOGGER.debug("指定读库的transactional");
//        ReadEntity readEntity = new ReadEntity();
//        readEntity.setAge(16);
//        readEntity.setUserId(RandomUtils.nextLong());
//        int readRow = readMapper.insert(readEntity);
//        Assert.assertTrue("read write db insert failure", readRow > 0);
//        Object o = null;
//        o.toString();
//    }
//
//    @Resource
//    private UserMapperExample userMapperExample;
//
//    @Test
//    public void useExample() {
//        LOGGER.info("单表的crud 尽可能不去写 mapper.xml,因为麻烦");
//        Long userId = 1L;
//        LOGGER.info("定义userId,需要先在数据库插入一条数据");
//
//        UserEntity userEntity = userMapperExample.findByUserId(userId);
//        Assert.assertTrue("not found", userEntity != null);
//        LOGGER.debug("user userEntity : {}", userEntity);
//
//        UserEntity afterEntity = new UserEntity();
//        afterEntity.setName("我是火车王 wuwuwuwuwuwuwu");
//        afterEntity.setUpdatedAt(new Date());
//
//        int row = userMapperExample.updateUserById(userId, afterEntity);
//        Assert.assertTrue("updated failure", row > 0);
//    }
//
//    /**
//     *
//     */
//    @Test
//    public void batchOperation() {
//        List<UserEntity> userEntityList = userMapper.selectAll();
//        userEntityList.stream().forEach(entity -> entity.setName(String.valueOf(RandomUtils.nextInt(0, 100))));
//        userMapper.batchUpdate(userEntityList);
//    }
//
//}
