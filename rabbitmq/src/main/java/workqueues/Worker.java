package workqueues;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker {
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
        // 队列重启保留
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        System.out.println("开始接收消息");
        channel.basicQos(2); // 希望处理的消息数量是1，在这1个消息处理完之前是不会接受下一个任务的
        // 第二个参数autoAck自动确认传false
        channel.basicConsume(TASK_QUEUE_NAME, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("接收到了消息：" + message);
                try {
                    doWork(message);
                } finally {
                    System.out.println("消息处理完成");
                    // 第一个参数是模版固定写法，第二个参数是否多个消息同时确认
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        });

    }

    private static void doWork(String task) {
        // 为了后续消息区分做准备
        char[] chars = task.toCharArray();
        for (char ch : chars) {
            if (ch == '.') { // 有.的消息就多处理一会
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
