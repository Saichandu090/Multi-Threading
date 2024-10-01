package multithreading;

public class LockWithCustomObjects
{
    private static int count1=0;
    private static int count2=0;

    private static final Object lock1=new Object();
    private static final Object lock2=new Object();
    public static void main(String[] args) {

        Thread one=new Thread(()->{
           for(int i=0;i<1000;i++)
           {
               increment1();
           }
        });

        Thread two=new Thread(()->{
            for(int i=0;i<1000;i++)
            {
                increment2();
            }
        });

        one.start();
        two.start();

        try{
            one.join();
            two.join();
        }
        catch(InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        System.out.println(count1+" ----- "+count2);
    }

    public static void increment1()
    {
        synchronized (lock1) {
            count1++;
        }
    }

    public static void increment2()
    {
        synchronized (lock2) {
            count2++;
        }
    }
}
