package topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 描述：topic模式交换机，发送消息
 */
public class EmitLogTopic {
    private static final String EXCHANGE_NAME = "topic_logs";

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
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String message = "Animal World";

        String[] routingKeys = new String[9];
        routingKeys[0] = "quick.orange.rabbit";
        routingKeys[1] = "lazy.orange.elephant";
        routingKeys[2] = "quick.orange.fox";
        routingKeys[3] = "lazy.brown.fox";
        routingKeys[4] = "lazy.pink.rabbit";// 符合多个条件也只打印一次，*.*.rabbit，lazy.#
        routingKeys[5] = "quick.brown.fox";
        routingKeys[6] = "orange";
        routingKeys[7] = "quick.orange.male.rabbit";
        routingKeys[8] = "lazy.orange.male.rabbit";

        for (int i = 0; i < routingKeys.length; ++i) {
            channel.basicPublish(EXCHANGE_NAME, routingKeys[i], null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送了：" + message + " routingKey:" + routingKeys[i]);
        }
        channel.close();
        connection.close();
    }
}
