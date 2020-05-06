package hw5;

import exceptions.EmptyException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Priority queue implemented as a sorted ArrayList.
 *
 * <p>We use binary search to find the insertion point in O(log n) time, but
 * we need to spend O(n) time to "make room" for the insertion. We don't
 * treat the array as cyclic, so remove() also takes O(n) time...</p>
 *
 * <p>We use slot 0 of the ArrayList as a "sentinel"
 * of sorts for the internal find() calls.</p>
 *
 * @param <T> Element type.
 */
public class SortedArrayPriorityQueue<T extends Comparable<T>>
    implements PriorityQueue<T> {

  private ArrayList<T> data;
  private Comparator<T> cmp;

  /**
   * A sorted array using the "natural" ordering of T.
   */
  public SortedArrayPriorityQueue() {
    this(new DefaultComparator<>());
    // *** this(param) calls constructor
    // with params from the default constructor
  }

  /**
   * A sorted array using the given comparator for T.
   *
   * @param cmp Comparator to use.
   */
  public SortedArrayPriorityQueue(Comparator<T> cmp) {
    data = new ArrayList<T>();
    data.add(null);  // "sentinel" spot
    this.cmp = cmp;
  }

  // Value in the slot i "less" than value in the slot j?
  // Note that the comparator determines what we consider "less" here.
  private boolean less(int i, int j) {
    return cmp.compare(data.get(i), data.get(j)) < 0;
  }

  // Find position in data[] where t should live.
  private int find(T t) {
    data.set(0, t); // set sentinel
    // iterative binary search.
    int lower = 1;
    int upper = data.size() - 1;
    while (lower <= upper) {
      int m = (lower + upper) / 2;
      if (less(0, m)) {
        lower = m + 1;
      } else if (less(m, 0)) {
        upper = m - 1;
      } else {
        return m;
      }
    }
    return lower;
  }

  @Override
  public void insert(T t) {
    int p = find(t);
    data.add(p, t); // ArrayList takes care of shifting elements.
  }

  @Override
  public void remove() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    T t = data.remove(1); // ArrayList takes care of shifting elements.
    // It would have been a better implementation if "best" was placed
    //  at the end so removing it would not require shifting.
  }

  @Override
  public T best() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    return data.get(1); // because of sentinel at 0
  }

  @Override
  public boolean empty() {
    return data.size() == 1;  // because of sentinel at 0
  }

  // The default comparator uses the "natural" ordering.
  private static class DefaultComparator<T extends Comparable<? super T>>
      implements Comparator<T> {
    public int compare(T t1, T t2) {
      return t1.compareTo(t2);
    }
  }
}
