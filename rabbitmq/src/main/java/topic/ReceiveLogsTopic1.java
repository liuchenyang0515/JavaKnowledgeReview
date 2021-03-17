package topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 描述：特定路由键
 */
public class ReceiveLogsTopic1 {
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
        // 定义自己的队列和交换机进行绑定
        // 生成一个随机的临时的queue
        String queueName = channel.queueDeclare().getQueue();// 非持久且会删除队列
        // 定义routingKey
        String routingKey = "*.orange.*";
        // 一个交换机通过routingKey绑定队列
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);// 交换机和队列的绑定

        // 进行消息接收
        System.out.println("开始接收消息");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println("收到消息：" + message + " routingKey:" + envelope.getRoutingKey());
            }
        };

        // 进行消息的消费
        channel.basicConsume(queueName, true, consumer);
    }
}
