import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Collections;

public class Presents
{
  public Counter addCount;
  public Counter removeCount;
  public ArrayList<Integer> bag;
  // public ConcurrentLinkedDeque<Integer> chain;
  public LazyList chain;

  public Presents()
  {
    addCount = new Counter(500000);
    removeCount = new Counter(1);
    bag = new ArrayList<Integer>(500000);
    for (int i = 1; i <= 500000; i++)
    {
      bag.add(i);
    }
    Collections.shuffle(bag);
    chain = new LazyList();
  }

  public class Servant implements Runnable
  {
    public void run()
    {
      int currentPresent = addCount.getAndDecrement();
      try
      {
        while (currentPresent > 0)
        {
          if (chain.add(bag.get(currentPresent - 1)))
          {
            // System.out.println("Thank you written for " + bag.get(currentPresent));
            chain.remove(bag.get(currentPresent - 1));
          }
          // chain = Collections.sort(chain);
          currentPresent = addCount.getAndDecrement();
        }
        /*
        currentPresent = removeCount.getAndIncrement();
        while (chain.contains(500000))
        {
          chain.remove(currentPresent);
          if (currentPresent == 500000)
          {
            System.out.println("removing finished");
          }
          currentPresent = removeCount.getAndIncrement();
        }
        */
        return;
      }
      catch (Exception e)
      {
          // Throwing an exception
          System.out.println(e);
      }
    }
  }

  public void runTest()
  {
    long timeStart = System.currentTimeMillis();
    ArrayList<Thread> servants = new ArrayList<Thread>();
    for(int i = 0; i < 4; i++)
    {
      Thread servant
          = new Thread(new Servant());
      servants.add(servant);

      servant.start();
      // System.out.println("thread started");
    }

    for (int i = 0; i < 4; i++)
    {
      try{
        servants.get(i).join();
      }
      catch (Exception e){
        System.out.println(e);
      }
    }

    long timeEnd = System.currentTimeMillis() - timeStart;

    System.out.println("Thank you's written in " + timeEnd + "ms");
  }

}
