package hw5;

import exceptions.EmptyException;
import exceptions.PositionException;
import java.util.Iterator;

/**
 * Generic unbounded list abstraction.
 *
 * @param <T> Element type.
 */
public interface List<T> extends Iterable<T> {
  /**
   * Number of elements in list.
   *
   * @return Number of elements.
   */
  int length();

  /**
   * Check if no elements present.
   *
   * @return True if empty, false otherwise.
   */
  boolean empty();

  /**
   * Insert at front of list.
   *
   * @param t Element to insert.
   * @return Position created to hold element.
   */
  Position<T> insertFront(T t);

  /**
   * Insert at back of list.
   *
   * @param t Element to insert.
   * @return Position created to hold element.
   */
  Position<T> insertBack(T t);

  /**
   * Insert before a position.
   *
   * @param p Position to insert before.
   * @param t Element to insert.
   * @return Position created to hold element.
   * @throws PositionException If p is invalid for this list.
   */
  Position<T> insertBefore(Position<T> p, T t) throws PositionException;

  /**
   * Insert after a position.
   *
   * @param p Position to insert after.
   * @param t Element to insert.
   * @return Position created to hold element.
   * @throws PositionException If p is invalid for this list.
   */
  Position<T> insertAfter(Position<T> p, T t) throws PositionException;

  /**
   * Remove a position.
   *
   * @param p Position to remove.
   * @throws PositionException If p is invalid for this list.
   */
  void remove(Position<T> p) throws PositionException;

  /**
   * Remove from front of list.
   *
   * @throws EmptyException If list is empty.
   */
  void removeFront() throws EmptyException;

  /**
   * Remove from back of list.
   *
   * @throws EmptyException If list is empty.
   */
  void removeBack() throws EmptyException;

  /**
   * First position.
   *
   * @return First position.
   * @throws EmptyException If list is empty.
   */
  Position<T> front() throws EmptyException;

  /**
   * Last position.
   *
   * @return Last position.
   * @throws EmptyException If list is empty.
   */
  Position<T> back() throws EmptyException;

  /**
   * Check if <code>p</code> is the first position.
   *
   * @param p Position to examine.
   * @return True if p is the first position, false otherwise.
   * @throws PositionException If p is invalid for this list.
   */
  boolean first(Position<T> p) throws PositionException;

  /**
   * Check if <code>p</code> is the last position.
   *
   * @param p Position to examine.
   * @return True if p is the last position, false otherwise.
   * @throws PositionException If p is invalid for this list.
   */
  boolean last(Position<T> p) throws PositionException;

  /**
   * Next position.
   *
   * @param p Position to examine.
   * @return Position after p.
   * @throws PositionException If p is invalid, or the last position.
   */
  Position<T> next(Position<T> p) throws PositionException;

  /**
   * Previous position.
   *
   * @param p Position to examine.
   * @return Position before p.
   * @throws PositionException If p is invalid, or the first position.
   */
  Position<T> previous(Position<T> p) throws PositionException;

  /**
   * Returns an iterator going forward over list elements of type T.
   * The standard iterator() method creates one of these.
   *
   * @return Iterator going from front to back.
   */
  Iterator<T> forward();

  /**
   * Returns an iterator going backward over list elements of type T.
   *
   * @return Iterator going from back to front.
   */
  Iterator<T> backward();
}