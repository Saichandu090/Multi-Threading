package multithreading;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock
{
    private static final Lock lock1=new ReentrantLock();
    private static final Lock lock2=new ReentrantLock();

    public static void worker1()
    {
        lock1.lock();
        System.out.println("Worker one acquired Lock1");
        try
        {
            Thread.sleep(200);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        lock2.lock();
        System.out.println("Worker one acquired Lock2");
        lock1.unlock();
        lock2.unlock();
    }

    public static void worker2()
    {
        lock2.lock();
        System.out.println("Worker two acquired Lock2");
        try
        {
            Thread.sleep(200);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        lock1.lock();
        System.out.println("Worker two acquired Lock1");
        lock1.unlock();
        lock2.unlock();
    }

    public static void main(String[] args) {

        DeadLock deadLock=new DeadLock();

        new Thread(DeadLock::worker1,"Worker1").start();
        new Thread(DeadLock::worker2,"Worker2").start();

        new Thread(()->{

            ThreadMXBean mxBean= ManagementFactory.getThreadMXBean();

            while(true)
            {
                long[] threadIds = mxBean.findDeadlockedThreads();
                if (threadIds!=null)
                {
                    System.out.println("DeadLock Detected");
                    ThreadInfo[] threadInfo=mxBean.getThreadInfo(threadIds);
                    for (long threadId : threadIds)
                    {
                        System.out.println("Thread with " + threadId + " is in deadlock");
                    }
                    break;
                }
                else
                    try
                    {
                        Thread.sleep(5000);
                    }
                    catch(InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }
            }
        }).start();
    }
}
