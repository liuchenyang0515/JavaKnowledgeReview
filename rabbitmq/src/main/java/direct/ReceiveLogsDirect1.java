package direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 描述：  接收3个等级的日志信息
 */
public class ReceiveLogsDirect1 {
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
        // 定义自己的队列和交换机进行绑定
        // 生成一个随机的临时的queue
        String queueName = channel.queueDeclare().getQueue();// 非持久且会删除队列
        // 一个交换机同时绑定3个queue
        channel.queueBind(queueName, EXCHANGE_NAME, "info");// 交换机和队列的绑定
        channel.queueBind(queueName, EXCHANGE_NAME, "warning");// 交换机和队列的绑定
        channel.queueBind(queueName, EXCHANGE_NAME, "error");// 交换机和队列的绑定

        // 进行消息接收
        System.out.println("开始接收消息");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println("收到消息：" + message);
            }
        };

        // 进行消息的消费
        channel.basicConsume(queueName, true, consumer);
    }
}
