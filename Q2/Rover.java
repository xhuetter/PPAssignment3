import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ConcurrentHashMap;

public class Rover
{


  ConcurrentSkipListSet<Integer> temps = new ConcurrentSkipListSet<Integer>();
  ConcurrentHashMap<Integer, Integer> highTempAtTime = new ConcurrentHashMap<Integer, Integer>();
  ConcurrentHashMap<Integer, Integer> lowTempAtTime = new ConcurrentHashMap<Integer, Integer>();

  public class Sensor implements Runnable
  {
    public void run()
    {
      int bottomMinute = 1;
      int currentMinute = 1;
      int temp;
      try
      {
        while(currentMinute < 61)
        {
          Thread.sleep(1000);
          temp = ThreadLocalRandom.current().nextInt(-100, 71);
          //System.out.println("Temp: " + temp);
          temps.add(temp);
          if (!highTempAtTime.containsKey(currentMinute))
          {
            highTempAtTime.put(currentMinute, temp);
          }
          else
          {
            if(highTempAtTime.get(currentMinute) < temp)
            {
              highTempAtTime.replace(currentMinute, temp);
            }
          }
          if (!lowTempAtTime.containsKey(currentMinute))
          {
            lowTempAtTime.put(currentMinute, temp);
          }
          else
          {
            if(lowTempAtTime.get(currentMinute) > temp)
            {
              lowTempAtTime.replace(currentMinute, temp);
            }
          }
          if (currentMinute - bottomMinute > 9)
          {
            bottomMinute += 1;
          }
          currentMinute += 1;
        }
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
    ArrayList<Thread> sensors = new ArrayList<Thread>();
    int currentMax, currentMin, interval = 0, currentDiff = 0;
    int highTemp, lowTemp;
    for(int i = 0; i < 8; i++)
    {
      Thread sensor
          = new Thread(new Sensor());
      sensors.add(sensor);

      sensor.start();
      // System.out.println("thread started");
    }

    for (int i = 0; i < 8; i++)
    {
      try{
        sensors.get(i).join();
      }
      catch (Exception e){
        System.out.println(e);
      }
    }

    Object[] array = temps.toArray();
    /*
    for (Object t : array)
    {
      System.out.println(t);
    }
    */

    System.out.println("Lowest Temps:");
    for (int i = 0; i < 5; i++)
    {
      System.out.println(array[i]);
    }

    int size = array.length;
    System.out.println("Highest Temps:");
    for (int i = size - 1; i > size - 6; i--)
    {
      System.out.println(array[i]);
    }

    for (int i = 1; i < 52; i++)
    {
      currentMax = -101;
      currentMin = 71;
      for (int j = i; j < i + 10; j++)
      {
        highTemp = highTempAtTime.get(j);
        if (highTemp > currentMax)
        {
          currentMax = highTemp;
        }
        lowTemp = lowTempAtTime.get(j);
        if (lowTemp < currentMin)
        {
          currentMin = lowTemp;
        }
      }
      // System.out.println("currentmax = " + currentMax);
      // System.out.println("currentmin = " + currentMin);
      if (currentMax - currentMin > currentDiff)
      {
        currentDiff = currentMax - currentMin;
        interval = i;
      }
    }

    System.out.println("Largest Difference from " + interval + " min to " + (interval + 9) + "min");
    // System.out.println(currentDiff);

  }

}
