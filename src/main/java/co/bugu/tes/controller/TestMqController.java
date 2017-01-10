package co.bugu.tes.controller;

import co.bugu.tes.global.MqConstant;
import co.bugu.tes.model.Parent;
import co.bugu.tes.service.IParentService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

/**
 * Created by daocers on 2016/10/30.
 */
@Controller
@RequestMapping("/mq")
public class TestMqController {

    @Autowired
    IParentService parentService;

    @RequestMapping("/send")
    @ResponseBody
    public String test(){
        try{
            String message = "goods";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(MqConstant.QUEUE_NAME, true, false, false, null);

            channel.confirmSelect();
            channel.basicPublish("", MqConstant.QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping("/comm")
    @ResponseBody
    public String common(){
        try{
            Random random = new Random();
            Parent parent = new Parent();
            parent.setAge(random.nextInt(30) + 1);
            parent.setName("test" + random.nextInt(1000));
            parent.setProp("propinfo" + random.nextBoolean() + random.nextInt(10));

            parentService.save(parent);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "common";

    }
}
