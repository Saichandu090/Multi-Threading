package multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock
{
    private static final Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {

        Thread one=new Thread(()->{
            try {
                get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread two=new Thread(DeadLock::hey);

        one.start();
        two.start();
    }

    public static void get() throws InterruptedException
    {
        synchronized (LOCK)
        {
            System.out.println("Hi from Method one..");
            LOCK.wait();
            System.out.println("Hi back from Method one if Notified by Method two");
        }
    }

    public static void hey()
    {
        synchronized (LOCK)
        {
            System.out.println("Hi from Method two...");
        }
    }
}
