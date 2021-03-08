package threadpool;

public class EveryTaskOneThread {
    public static void main(String[] args) {
        /**
         * 当任务数量上升到1000+，这样内存开销太大，我们希望有固定数量的线程，来执行这1000个线程，这样就避免了反复创建并销毁线程所带来的开销问题
         * 此时需要线程池，能解决2个问题：
         * 1.反复创建线程开销大
         * 2.过多的线程会占用太多内存
         * 线程池好处：1.加快响应速度；2.合理利用CPU和内存 3.统一管理资源
         *
         * 线程池适合应用的场景
         * 1.服务器接收到大量请求时，使用线程池技术是非常合适的，它可以大大减少线程的创建和销毁次数，提高服务器的工作效率。
         * 2.实际在开发中，如果需要创建5个以上的线程，那么就可以使用线程池来管理
         */
        for (int i = 0; i < 10; ++i) {
            Thread thread = new Thread(new Task());
            thread.start();
        }
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("执行了任务");
        }
    }
}
