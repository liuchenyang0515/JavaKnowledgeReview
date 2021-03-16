package workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 描述：任务有所耗时，多个任务
 */
public class NewTask {
    private final static String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ地址,一定记得阿里云安全组要开15672和5672端口，少了5672端口会Connection timed out: connect
        factory.setHost("8.135.52.240");
        factory.setUsername("admin");
        factory.setPassword("password");
        // 建立连接
        Connection connection = factory.newConnection();
        // 获得信道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        for (int i = 0; i < 10; ++i) {
            String message;
            if ((i & 1) == 0) {
                message = i + "...";
            } else {
                message = String.valueOf(i);
            }
            channel.basicPublish("", TASK_QUEUE_NAME, null,
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送了消息：" + message);
        }
        channel.close();
        connection.close();
    }
}
