import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

// Main Class

public class Counter
{

  private int value;

  public Counter(int n)
  {
    this.value = n;
  }

  public int getAndDecrement()
  {
    int temp;
    synchronized(this)
    {
      temp = value;
      value = temp - 1;
    }
    return temp;
  }

  public int getAndIncrement()
  {
    int temp;
    synchronized(this)
    {
      temp = value;
      value = temp + 1;
    }
    return temp;
  }

  public void resetCounter()
  {
    value = 0;
  }
}
