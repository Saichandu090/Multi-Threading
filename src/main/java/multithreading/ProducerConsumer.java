package multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ProducerConsumer
{
    public static void main(String[] args) {

        Worker work=new Worker(5,0);

        Thread producer=new Thread(()->{
            try {
                work.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer=new Thread(()->{
            try {
                work.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producer.start();
        consumer.start();
    }
}

class Worker
{
    private final Object lock = new Object();
    private final Integer top;
    private final Integer bottom;
    private int sequence = 0;
    private Queue<Integer> container;

    Worker(int top,int bottom)
    {
        this.top=top;
        this.bottom=bottom;
        this.container =new PriorityQueue<>();
    }

    public void produce() throws InterruptedException
    {
        synchronized (lock)
        {
            while(true)
            {
                if (container.size() == top) {
                    System.out.println("Container is full,waiting for the items to be removed!!");
                    lock.wait();
                } else {
                    System.out.println(sequence + " Adding to the Container!!");
                    container.add(sequence++);
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consume() throws InterruptedException
    {
        synchronized (lock)
        {
            while(true)
            {
                if (container.size() == bottom) {
                    System.out.println("Container is Empty,Waiting for things to get added!!");
                    lock.wait();
                } else {
                    System.out.println(container.poll() + " removed from the Container!!");
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }
}
