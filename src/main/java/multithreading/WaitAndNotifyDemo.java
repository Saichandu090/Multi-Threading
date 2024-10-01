package multithreading;

public class WaitAndNotifyDemo
{
    private static final Object LOCK=new Object();

    public static void main(String[] args) {

        Thread one=new Thread(()->{
            try {
                one();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread two=new Thread(()->{
            try {
                two();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        one.start();
        two.start();
    }

    public static void one() throws InterruptedException
    {
        synchronized (LOCK)
        {
            System.out.println("Hi from the method one....");
            LOCK.wait();
            System.out.println("Hi again from the Method One...");
        }
    }

    public static void two() throws InterruptedException
    {
        synchronized (LOCK)
        {
            System.out.println("Hi from the Method two");
            LOCK.notify();  //If not notified the other thread will be in waiting condition only which is that this two() has DeadLock.
            System.out.println("Hi back from method two even after notifying..");
        }
    }
}

//wait is used for interThread Communication and synchronization.
//sleep is used for pausing the thread execution for the specific time.
