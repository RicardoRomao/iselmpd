package serie2.grupo1.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.AbstractListModel;

public class SortedListModel<T> extends AbstractListModel implements List<T> {

    private final List<T> _list;
    private final Comparator<T> _comparator;

    public static SortedListModel getInstance(Comparator cmp) {
        return new SortedListModel(cmp);
    }

    public static <E extends Comparable<E>> SortedListModel getInstance() {
        return new SortedListModel(new Comparator<E>() {

            public int compare(E o1, E o2) {
                return o1.compareTo(o2);
            }
        });
    }

    private SortedListModel(Comparator cmp) {
        _list = new LinkedList();
        _comparator = cmp;
    }

    public int getSize() {
        return _list.size();
    }

    public T getElementAt(int index) {
        return _list.get(index);
    }

    public int size() {
        return _list.size();
    }

    public boolean isEmpty() {
        return _list.isEmpty();
    }

    public boolean contains(Object o) {
        return _list.contains(o);
    }

    public Iterator<T> iterator() {
        return _list.iterator();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean add(T e) {

        int pos = -1;

        if (_list.size() == 0) {
            _list.add(e);
        } else {
            for (int i = 0; i < _list.size(); i++) {
                if (_comparator.compare(_list.get(i), e) >= 0) {
                    pos = i;
                    _list.add(pos, e);
                    break;
                }
            }
            if (pos == -1) {
                _list.add(e);
            }
        }
        super.fireContentsChanged(this, 0, _list.size());
        return true;
    }

    public boolean remove(Object o) {
        return _list.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        int currSize = size();
        _list.clear();
        super.fireIntervalRemoved(this, 0, size());
    }

    public T get(int index) {
        return _list.get(index);
    }

    public T set(int index, T element) {
        T temp = this.remove(index);
        super.fireContentsChanged(temp, 0, size());
        add(element);
        return temp;
    }

    public void add(int index, T element) {
        add(element);
    }

    public T remove(int index) {
        T temp = _list.remove(index);
        super.fireIntervalRemoved(this, index, index);
        return temp;
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
