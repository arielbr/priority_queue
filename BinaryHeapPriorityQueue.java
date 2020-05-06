package hw5;

import exceptions.EmptyException;
import java.util.Comparator;

/**
 * Priority queue implemented as a binary heap.
 *
 * <p> Use the ranked array representation of a binary heap!
 * Keep the arithmetic simple by sticking a null into slot 0 of the
 * ArrayList; wasting one reference is an okay price to pay for nicer
 * code.</p>
 *
 * @param <T> Element type.
 */
public class BinaryHeapPriorityQueue<T extends Comparable<T>>
    implements PriorityQueue<T> {

  private Comparator<T> cmp;
  private T[] arr;
  private int size;
  private int capacity;

  /**
   * A binary heap using the "natural" ordering of T.
   */
  public BinaryHeapPriorityQueue() {
    this(new DefaultComparator<>());
  }

  /**
   * A binary heap using the given comparator for T.
   *
   * @param cmp Comparator to use.
   */
  public BinaryHeapPriorityQueue(Comparator<T> cmp) {
    this.cmp = cmp;
    this.capacity = 10;
    this.arr = (T[]) new Comparable[capacity];
    this.size = 0;
  }

  /**
   * method to rearrange all elements in array by sequence.
   */
  public void swim() {
    for (int i = size / 2; i >= 1; i--) {
      // only one leaf
      if (2 * i + 1 > size) {
        if (cmp.compare(arr[2 * i], arr[i]) > 0) {
          swap(2 * i, i);
        }
      } else {
        int c = 2 * i;
        if (cmp.compare(arr[2 * i + 1], arr[2 * i]) > 0) {
          c++;
        }
        if (cmp.compare(arr[c], arr[i]) > 0) {
          swap(c, i);
        }
      }
    }

    /*
    for (int i = 1; i < size + 1; i++) {
      swimHelper(i);
    }
    */
  }

  private void swimHelper(int i) {
    int c = i / 2;
    if (c < 1) return;
    if (cmp.compare(arr[i], arr[c]) > 0) {
      swap(c, i);
      swimHelper(c);
    }
  }

  /**
   * helper method to swap to elements in an array.
   * @param i index of first element to be swapped.
   * @param j index of second element to be swapped.
   */
  private void swap(int i, int j) {
    T temp;
    temp = arr[j];
    arr[j] = arr[i];
    arr[i] = temp;
  }

  /**
   * to insert an element in the list.
   * @param t Value to insert.
   */
  @Override
  public void insert(T t) {
    if (size == 0) {
      arr[1] = t;
      size++;
      return;
    }
    if (size == capacity) {
      grow();
    }
    arr[++size] = t;
    swim();
  }

  /**
   * This would grow the size of the array when it is full.
   */
  private void grow() {
    capacity += 2;
    T[] temp = (T[]) new Object[capacity];
    for (int i = 0; i < size; i++) {
      temp[i] = arr[i];
    }
    arr = temp;
  }

  /**
   * remove the best element in the list.
   * @throws EmptyException thrown when list is empty.
   */
  @Override
  public void remove() throws EmptyException {
    if (size == 0) {
      throw new EmptyException();
    }
    arr[1] = arr[size--];
    swim();
  }

  /**
   * return the best value in the list.
   * @return the best value.
   * @throws EmptyException thrown when list is empty.
   */
  @Override
  public T best() throws EmptyException {
    if (size == 0) {
      throw new EmptyException();
    }
    return arr[1];
  }

  /**
   * check if list is empty.
   * @return boolean value of if list is empty.
   */
  @Override
  public boolean empty() {
    return size == 0;
  }

  /**
   * The default comparator class: uses the "natural" ordering.
   * @param <T> generic data type.
   */
  private static class DefaultComparator<T extends Comparable<? super T>>
      implements Comparator<T> {
    public int compare(T t1, T t2) {
      return t1.compareTo(t2);
    }
  }

}
