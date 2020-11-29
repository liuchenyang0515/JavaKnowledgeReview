package com.imooc.sleep;

class MyThread implements Runnable{

	@Override
	public void run() {
		for(int i=1;i<=30;i++){
			System.out.println(Thread.currentThread().getName()+"执行第"+i+"次！");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
}
public class SleepDemo {

	public static void main(String[] args) {
		MyThread mt=new MyThread();
		Thread t=new Thread(mt);
		t.start();
		Thread t1=new Thread(mt);
		t1.start();
	}

}
