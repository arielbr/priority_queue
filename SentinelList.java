package hw5;

import exceptions.EmptyException;
import exceptions.PositionException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic position-based linked list.
 *
 * <p>This is a sentinel-based doubly linked list.
 * It has dummy first and last nodes, "guarding" the beginning and end.
 * The data in those nodes are null, but their purpose is to eliminate
 * the need for special cases, particularly for the insert
 * and remove methods.</p>
 *
 * @param <T> Element type.
 */

public class SentinelList<T> implements List<T> {

  private Node<T> head;   // reference to the first node
  private Node<T> tail;   // reference to the last node

  /* ** LinkedList instance variables are declared here! ** */
  private int length;     // how many nodes in the list

  /**
   * Create an empty list.
   */
  public SentinelList() {
    // DONE: create sentinel head and tail nodes!
    head = new Node<>();
    tail = new Node<>();
    head.owner = this;
    tail.owner = this;
    head.next = tail;
    tail.prev = head;
    length = 0;
  }

  // Convert a position back into a node. Guards against null positions,
  // positions from other data structures, and positions that belong to
  // other LinkedList objects. That about covers it?
  private Node<T> convert(Position<T> p) throws PositionException {
    try {
      Node<T> n = (Node<T>) p;
      if (n.owner != this) {
        throw new PositionException();
      }
      return n;
    } catch (NullPointerException | ClassCastException e) {
      throw new PositionException();
    }
  }

  @Override
  public boolean empty() {
    return length == 0;
  }

  @Override
  public int length() {
    return length;
  }

  @Override
  public boolean first(Position<T> p) throws PositionException {
    Node<T> n = convert(p);
    return head.next == n;
  }

  @Override
  public boolean last(Position<T> p) throws PositionException {
    Node<T> n = convert(p);
    return tail.prev == n;
  }

  @Override
  public Position<T> front() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    return head.next;
  }

  @Override
  public Position<T> back() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    return tail.prev;
  }

  @Override
  public Position<T> next(Position<T> p) throws PositionException {
    if (last(p)) {
      throw new PositionException();
    }
    return convert(p).next;
  }

  @Override
  public Position<T> previous(Position<T> p) throws PositionException {
    if (first(p)) {
      throw new PositionException();
    }
    return convert(p).prev;
  }

  @Override
  public Position<T> insertFront(T t) {
    Node<T> n = new Node<T>();
    n.data = t;
    n.owner = this;

    Node<T> front = head.next;
    head.next = n;
    n.prev = head;
    n.next = front;
    front.prev = n;

    length += 1;
    return n;
  }

  @Override
  public Position<T> insertBack(T t) {
    Node<T> n = new Node<T>();
    n.data = t;
    n.owner = this;

    n.next = tail;
    n.prev = tail.prev;
    tail.prev = n;
    n.prev.next = n;

    length += 1;
    return n;
  }

  @Override
  public void removeFront() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }

    Node<T> h = head.next;
    Node<T> hh = h.next;

    head.next = hh;
    hh.prev = head;

    length -= 1;
    h.owner = null; // invalidate position
  }

  @Override
  public void removeBack() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    Node<T> t = tail.prev;
    Node<T> tt = t.prev;

    tail.prev = tt;
    tt.next = tail;

    length -= 1;
    t.owner = null; // invalidate position
  }

  @Override
  public void remove(Position<T> p) throws PositionException {
    Node<T> n = convert(p);

    Node<T> before = n.prev;
    Node<T> after = n.next;

    before.next = after;
    after.prev = before;

    length -= 1;
    n.owner = null; // invalidate position
  }

  @Override
  public Position<T> insertBefore(Position<T> p, T t)
      throws PositionException {
    Node<T> current = convert(p);
    Node<T> before = current.prev;

    Node<T> newBefore = new Node<T>();
    newBefore.data = t;
    newBefore.owner = this;


    newBefore.prev = before;
    before.next = newBefore;

    newBefore.next = current;
    current.prev = newBefore;

    length += 1;
    return newBefore;
  }

  @Override
  public Position<T> insertAfter(Position<T> p, T t)
      throws PositionException {
    Node<T> current = convert(p);
    Node<T> newAfter = new Node<T>();
    newAfter.data = t;
    newAfter.owner = this;

    newAfter.prev = current;
    current.next = newAfter;

    Node<T> after = current.next;
    newAfter.next = after;
    after.prev = newAfter;

    length += 1;
    return newAfter;
  }

  @Override
  public Iterator<T> forward() {
    return new ListIterator(true);
  }

  @Override
  public Iterator<T> backward() {
    return new ListIterator(false);
  }

  @Override
  public Iterator<T> iterator() {
    return forward();
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("[");
    for (Node<T> n = head.next; n != tail; n = n.next) {
      s.append(n.data);
      if (n.next != tail) {
        s.append(", ");
      }
    }
    s.append("]");
    return s.toString();
  }

  private static final class Node<T> implements Position<T> {
    // The usual doubly-linked list stuff.
    Node<T> next;   // reference to the Node after this
    Node<T> prev;   // reference to the Node before this
    T data;

    // List that created this node, to validate positions.
    List<T> owner;

    @Override
    public T get() {
      return data;
    }

    @Override
    public void put(T t) {
      data = t;
    }
  }

  /**
   * This iterator can be used to create either a forward
   * iterator, or a backwards one.
   */
  private final class ListIterator implements Iterator<T> {
    Node<T> current;
    boolean forward;

    ListIterator(boolean f) {
      forward = f;
      if (forward) {
        current = SentinelList.this.head.next;
      } else {
        current = SentinelList.this.tail.prev;
      }
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      T t = current.get();
      if (forward) { // move the . to point to the "next" node
        current = current.next;
      } else {
        current = current.prev;
      }
      return t;
    }

    @Override
    public boolean hasNext() {
      return current != null && current != head && current != tail;
    }
  }
}
