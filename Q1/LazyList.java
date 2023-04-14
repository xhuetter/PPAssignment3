import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class LazyList
{
  Node head;

  public LazyList()
  {
    head = new Node(0);
    head.next = new Node(500001);
  }

  private class Node extends ReentrantLock
  {
    Integer item;
    int key;
    Node next;
    boolean marked = false;

    public Node(Integer item)
    {
      this.item = item;
      this.key = item.hashCode();
      this.next = null;
      this.marked = false;
    }
  }

  public boolean validate(Node pred, Node curr)
  {
    return !pred.marked && !curr.marked && pred.next == curr;
  }

  public boolean add(Integer item)
  {
    int key = item.hashCode();
    while (true)
    {
      Node pred = head;
      Node curr = head.next;
      while (curr.key < key)
      {
        pred = curr;
        curr = curr.next;
      }
      pred.lock();
      try
      {
        curr.lock();
        try
        {
          if (validate(pred, curr))
          {
            if (curr.key == key)
            {
              return false;
            }
            else
            {
              Node node = new Node(item);
              node.next = curr;
              pred.next = node;
              return true;
            }
          }
        }
        finally
        {
          curr.unlock();
        }
      }
      finally
      {
        pred.unlock();
      }
    }
  }

  public boolean remove(Integer item)
  {
    int key = item.hashCode();
    while (true)
    {
      Node pred = head;
      Node curr = head.next;
      while (curr.key < key)
      {
        pred = curr;
        curr = curr.next;
      }
      pred.lock();
      try
      {
        curr.lock();
        try
        {
          if (validate(pred, curr))
          {
            if (curr.key != key)
            {
              return false;
            }
            else
            {
              curr.marked = true;
              pred.next = curr.next;
              return true;
            }
          }
        }
        finally
        {
          curr.unlock();
        }
      }
      finally
      {
        pred.unlock();
      }
    }
  }

  public boolean contains(Integer item)
  {
    int key = item.hashCode();
    Node curr = head;
    while (curr.key < key)
    {
      curr = curr.next;
    }
    return curr.key == key && !curr.marked;
  }

}
