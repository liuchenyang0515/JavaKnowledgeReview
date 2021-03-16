package direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 描述： direct类型的交换机，发送消息
 */
public class EmitLogDirect {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ地址,一定记得阿里云安全组要开15672和5672端口，少了5672端口会Connection timed out: connect
        factory.setHost("8.135.52.240");
        // 默认情况下，username和password的都是guest
        factory.setUsername("admin");
        factory.setPassword("password");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 定义交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String message = "info--Hello World!";
        // 交换机名字为EXCHANGE_NAME，类型direct，需要有routingKey，匹配全部队列
        channel.basicPublish(EXCHANGE_NAME, "info", null, message.getBytes(StandardCharsets.UTF_8));
        // 完成发送后，在控制台打印
        System.out.println("发送了消息，等级为info，消息内容：" + message);
        message = "warning--Hello World!";
        channel.basicPublish(EXCHANGE_NAME, "warning", null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送了消息，等级为warning，消息内容：" + message);
        message = "error--Hello World!";
        channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送了消息，等级为error，消息内容：" + message);

        channel.close();
        connection.close();
    }
}
