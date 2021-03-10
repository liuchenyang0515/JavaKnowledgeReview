package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述：     演示newFixedThreadPool出错的情况
 */
public class FixedThreadPoolOOM {

    private static ExecutorService executorService = Executors.newFixedThreadPool(4);
    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(new SubThread());
        }
    }



}

class SubThread implements Runnable {

    /**
     * VM options设置为-Xmx8m -Xms8m，这样更快达到OOM
     * Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
     * 	at java.util.concurrent.LinkedBlockingQueue.offer(LinkedBlockingQueue.java:416)
     * 	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1371)
     * 	at threadpool.FixedThreadPoolOOM.main(FixedThreadPoolOOM.java:14)
     */
    @Override
    public void run() {
        try {
            Thread.sleep(1000000000);// 一直睡眠，塞满队列，体会OOM
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
