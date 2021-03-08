package threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 描述：     TODO
 */
public class ScheduledThreadPoolTest {

    public static void main(String[] args) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);
//        threadPool.schedule(new Task(), 5, TimeUnit.SECONDS); // 延迟5s执行Task任务
        threadPool.scheduleAtFixedRate(new Task(), 1, 3, TimeUnit.SECONDS); // 最初延迟1s执行Task，之后每隔3s执行Task
    }
}
