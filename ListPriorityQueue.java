package hw5;

import exceptions.EmptyException;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Priority queue implemented using our (unordered) abstract List,
 * specifically with a SentinelList implementation.
 *
 * @param <T> Element type.
 */
public class ListPriorityQueue<T extends Comparable<T>>
    implements PriorityQueue<T> {

  private List<T> list;
  private Comparator<T> cmp;

  /**
   * An unordered List PQ using the "natural" ordering of T.
   */
  public ListPriorityQueue() {
    this(new DefaultComparator<>());
  }

  /**
   * An unordered List PQ using the given comparator for T.
   *
   * @param cmp Comparator to use.
   */
  public ListPriorityQueue(Comparator<T> cmp) {
    list = new SentinelList<>();
    this.cmp = cmp;
  }

  /**
   * insert value into list.
   * @param t Value to insert.
   */
  @Override
  public void insert(T t) {
    list.insertFront(t);
  }

  /**
   * remove the best element from list.
   * @throws EmptyException thrown when list is empty.
   */
  @Override
  public void remove() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    Position<T> maxPos = findBest();//iterate(list.front(), list.front());
    list.remove(maxPos);
  }

  public Position<T> findBest() {
    Position<T> cur = list.front();
    Position<T> bestPos = list.front();
    T best = list.front().get();
    T temp;
    while (!list.last(cur)) {
      cur = list.next(cur);
      temp = cur.get();
      if (cmp.compare(temp, best) > 0) {
        bestPos = cur;
      }
    }
    return bestPos;
  }

  /**
   * to return the value of the best element.
   * @return the value of the best element.
   * @throws EmptyException thrown when list is empty.
   */
  @Override
  public T best() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    // iterate through list and find the min
    T min = list.front().get();
    T cur;
    Iterator<T> it = list.iterator();
    while (it.hasNext()) {
      cur = it.next();
      if (cur != null && cmp.compare(cur, min) > 0) {
        min = cur;
      }
    }
    return min;
  }

  /**
   * check if list is empty.
   * @return boolean value of whether list is empty.
   */
  @Override
  public boolean empty() {
    return list.length() == 0;
  }

  /**
   * Default comparator: uses the "natural" ordering.
   * @param <T> generic data type.
   */
  private static class DefaultComparator<T extends Comparable<? super T>>
      implements Comparator<T> {
    public int compare(T t1, T t2) {
      return t1.compareTo(t2);
    }
  }
}
