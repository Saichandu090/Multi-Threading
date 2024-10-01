package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Scraper
{
    public static void main(String[] args)
    {

        try(ExecutorService service= Executors.newCachedThreadPool())
        {
            for(int i=0;i<15;i++)
            {
                service.execute(new Runnable()
                {
                    @Override
                    public void run() {
                        ScraperService.INSTANCE.scrape();
                    }
                });
            }
        }
    }
}

enum ScraperService
{
    INSTANCE;

    private final Semaphore semaphore=new Semaphore(3);

    public void scrape()
    {
        try
        {
            semaphore.acquire();
            invokeScrapeBot();
        }catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }finally {
            semaphore.release();
        }
    }

    private void invokeScrapeBot()
    {
        try
        {
            System.out.println("Scraping data.....");
            Thread.sleep(3000);
        }
        catch(InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}