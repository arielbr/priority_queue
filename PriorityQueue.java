package hw5;

import exceptions.EmptyException;

/**
 * Queue of ordered values.
 *
 * <p>Note that the interface does not include methods to obtain the minimum
 * or maximum explicitly, but of course we want to be able to create PQs
 * of either sort.</p>
 *
 * <p> Your implementations *must* provide two constructors, one that uses
 * the "natural" ordering of java.util.Comparable, such that best == max,
 * and another that accepts an explicit java.util.Comparator argument.</p>
 *
 * <p> It's a *very* good idea to take the time and *read* the documentation
 * for those interfaces *before* you start hacking!</p>
 *
 * @param <T> Element type.
 */
public interface PriorityQueue<T extends Comparable<T>> {
  /**
   * Insert a value.
   *
   * <p> A queue is not a set, so duplicate values <b>are</b> possible:
   * Inserting X three times means it has to be removed three times
   * before it's gone again. </p>
   *
   * @param t Value to insert.
   */
  void insert(T t);

  /**
   * Remove best value.
   *
   * <p>The best value is the largest value in the queue as determined
   * by the queue's comparator.</p>
   *
   * @throws EmptyException If queue is empty.
   */
  void remove() throws EmptyException;

  /**
   * Return best value.
   *
   * <p>The best value is the largest value in the queue as determined
   * by the queue's comparator.</p>
   *
   * @return best value in the queue.
   * @throws EmptyException If queue is empty.
   */
  T best() throws EmptyException;

  /**
   * Check if no elements present.
   *
   * @return True if queue is empty, false otherwise.
   */
  boolean empty();
}
