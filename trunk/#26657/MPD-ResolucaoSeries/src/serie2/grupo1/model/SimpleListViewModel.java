package serie2.grupo1.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.AbstractListModel;

/**
 * @author mcarvalho
 *
 * @param <E>
 */
@SuppressWarnings("serial")
public class SimpleListViewModel<E> extends AbstractListModel implements List<E>{
  /** 
   * @uml.property name="elems"
   * @uml.associationEnd aggregation="shared"
   */

  private LinkedList<E> elems = new LinkedList<E>();

  @Override
  public boolean add(E e) {
    elems.add(e);
    fireIntervalAdded(this, elems.size()-1, elems.size()-1);
    return true;
  }

  @Override
  public void add(int index, E element) {
    elems.add(index, element);
    fireIntervalAdded(this, index, index);
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    for (E e : c) {
      this.add(e);
    }
    return true;
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void clear() {
    int size = elems.size();
    elems.clear();
    fireIntervalAdded(this, 0, size-1);
  }

  @Override
  public boolean contains(Object o) {
    return elems.contains(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return elems.containsAll(c);
  }

  @Override
  public E get(int index) {
    return elems.get(index);
  }

  @Override
  public int indexOf(Object o) {
    return elems.indexOf(o);
  }

  @Override
  public boolean isEmpty() {
    return elems.isEmpty();
  }

  @Override
  public Iterator<E> iterator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int lastIndexOf(Object o) {
    return elems.lastIndexOf(o);
  }

  @Override
  public ListIterator<E> listIterator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean remove(Object o) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public E remove(int index) {
    E e = elems.remove(index);
    fireIntervalRemoved(this, index, index);
    return e;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public E set(int index, E element) {
    E oldElem = remove(index);
    add(index,element);
    return oldElem;
  }

  @Override
  public int size() {
    return elems.size();
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object[] toArray() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T[] toArray(T[] a) {
    // TODO Auto-generated method stub
    return null;
  }
  /**
   * @see javax.swing.ListModel#getElementAt(int)
   */
  @Override
  public Object getElementAt(int index) {
    return this.get(index);
  }

  /**
   * @see javax.swing.ListModel#getSize()
   */
  @Override
  public int getSize() {
    // TODO Auto-generated method stub
    return this.size();
  }
}
