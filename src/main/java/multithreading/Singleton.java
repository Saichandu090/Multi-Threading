package multithreading;

public class Singleton
{
    private static Singleton obj;

    private Singleton()
    {

    }

    public static Singleton getInstance()
    {
        if(obj==null)
        {
            synchronized (Singleton.class)
            {
                obj=new Singleton();
            }
        }
        return obj;
    }
}

class Check
{
    public static void main(String[] args) {

        Singleton s=Singleton.getInstance();
        Singleton s1=Singleton.getInstance();
        System.out.println(s+" -> "+s1);
    }
}
