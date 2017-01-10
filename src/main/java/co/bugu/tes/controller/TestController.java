package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.exception.DbBatchException;
import co.bugu.framework.core.util.ApplicationContextUtil;
import co.bugu.framework.core.util.BuguProperties;
import co.bugu.framework.core.util.BuguPropertiesUtil;
import co.bugu.framework.core.util.DatabaseUtil;
import co.bugu.tes.model.Child;
import co.bugu.tes.model.Parent;
import co.bugu.tes.service.IChildService;
import co.bugu.tes.service.IParentService;
import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by daocers on 2016/9/20.
 */
@RequestMapping("/test")
@RestController
public class TestController {
    @Autowired
    IParentService parentService;
    @Autowired
    IChildService childService;

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/gen")
    public String test() throws IOException {
        Random random = new Random();
        int parentId = random.nextInt(1000000);
        BuguProperties properties = BuguPropertiesUtil.load("conf/system.properties");
        Integer count = properties.getInt("count");
        List<Parent> parents = new ArrayList<>();
        for(int i = 0; i < count; i++){
            Parent parent = new Parent();
            parent.setAge(i);
            parent.setName("name" + i);
            parent.setProp("prop" + i);
            parent.setProp1("prop1 " + i);
            parent.setProp2("prop2 " + i);
            parent.setProp3("prop3 " + i);
            parent.setProp12("prop12 " + i);
            parents.add(parent);
        }
        long begin = System.currentTimeMillis();
        for(Parent parent: parents){
            parentService.save(parent);
        }
        long end = System.currentTimeMillis();

        logger.info("随机插入100万条数据耗时： {} 毫秒", end - begin);

        List<Child> children = new ArrayList<>();
        for(int i = 0; i < 2 * count; i++){
            parentId = random.nextInt(1000000);
            Child child = new Child();
            child.setName("child-name " + i);
            child.setA("a " + i);
            child.setB("b " + i);
            child.setC("c " + i);
            child.setD("d " + i);
            child.setParentId(parentId);
            children.add(child);
        }
        begin = System.currentTimeMillis();
        for(Child child: children){
            childService.save(child);
        }
        end = System.currentTimeMillis();
        logger.info("随机插入200万条数据耗时： {} 毫秒", end - begin);
        return "ok";
    }

    @RequestMapping("/get")
    public String get(Integer showcount, Integer page) throws Exception {
        Long begin = System.currentTimeMillis();
        PageInfo<Parent> pageInfo = new PageInfo<>(showcount, page);
        pageInfo = parentService.listByObject(null, pageInfo);
        List<Parent> list = pageInfo.getData();
        for(Parent parent : list){
            Integer parentId = parent.getId();
            Child child = new Child();
            child.setParentId(parentId);
            List<Child> children = childService.findAllByObject(child);
            parent.setChildren(children);
        }
//        logger.info("获取到parent数量： {}", list.size());
        long end = System.currentTimeMillis();
        logger.info("单表逐表查询，随机查询数据 {} 条，耗时： {} 毫秒", new Object[]{list.size(), end - begin});
        return "ok";
    }

    @RequestMapping("/list")
    public String list(Integer showcount, Integer page) throws Exception {
        Long begin = System.currentTimeMillis();
        PageInfo<Parent> pageInfo = new PageInfo<>(showcount, page);
        pageInfo = parentService.listByObject(null, pageInfo);
        List<Parent> list = pageInfo.getData();
        long end = System.currentTimeMillis();
        logger.info("单表逐表查询，随机查询数据 {} 条，耗时： {} 毫秒", new Object[]{list.size(), end - begin});
        return "ok";
    }

    @RequestMapping("/batch")
    public String batchAdd(){
        DataSource dataSource = ApplicationContextUtil.getBean("dataSource", DruidDataSource.class);
        String sql = "insert into parent (name, age, prop1, prop, prop2, prop3, prop12) values ";
        for(int j = 0; j < 2000; j++){
            List<String[]> list = new ArrayList<>();
            for(int i = 0; i < 3000; i++){
                String name = "name 12" + i;
                Integer age = i;
                String prop1 = "prop1 fsd " + i;
                String prop = "prop1 fsdfsdfsd " + i;
                String prop2 = "prop1 我们fsd " + i;
                String prop3 = "prop3第三个sd " + i;
                String prop12 = "prop3第12个sd " + i;
                String[] data = new String[]{name, age + "", prop1, prop, prop2, prop3, prop12};
                list.add(data);
            }
            try {
                long begin = System.currentTimeMillis();
                DatabaseUtil.batchInsert(dataSource, sql, list);
                long end = System.currentTimeMillis();
                logger.info("批量插入数据10000条，耗时： {}毫秒", end - begin);
            } catch (DbBatchException e) {
                logger.error("批量添加失败", e);
            }
        }

        return "ok";
    }


}
