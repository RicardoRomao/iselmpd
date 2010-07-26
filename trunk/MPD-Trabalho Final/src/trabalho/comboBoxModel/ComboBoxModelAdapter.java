package trabalho.comboBoxModel;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

public class ComboBoxModelAdapter<T> implements ComboBoxModel, List<T> {

    private LinkedList<T> _elems = new LinkedList<T>();
    private int _selected;

    @SuppressWarnings("element-type-mismatch")
    public void setSelectedItem(Object anItem) {
        if (_elems.contains(anItem))
            _selected = _elems.indexOf(anItem);
        else
            _selected = 0;

    }

    public Object getSelectedItem() {
        return _elems.get(_selected);
    }

    public int getSize() {
        return _elems.size();
    }

    public Object getElementAt(int index) {
        return _elems.get(index);
    }

    public void addListDataListener(ListDataListener l) {
        //addListDataListener(l);
    }

    public void removeListDataListener(ListDataListener l) {
        //removeListDataListener(l);
    }

    public int size() {
        return _elems.size();
    }

    public boolean isEmpty() {
        return _elems.isEmpty();
    }

    @SuppressWarnings("element-type-mismatch")
    public boolean contains(Object o) {
        return _elems.contains(o);
    }

    public Iterator<T> iterator() {
        return _elems.iterator();
    }

    public Object[] toArray() {
        return _elems.toArray();
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean add(T e) {
        return _elems.add(e);
    }

    @SuppressWarnings("element-type-mismatch")
    public boolean remove(Object o) {
        return _elems.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return _elems.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        return _elems.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return _elems.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return _elems.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return _elems.retainAll(c);
    }

    public void clear() {
        _elems.clear();
    }

    public T get(int index) {
        return _elems.get(index);
    }

    public T set(int index, T element) {
        return _elems.set(index, element);
    }

    public void add(int index, T element) {
        _elems.add(index, element);
    }

    public T remove(int index) {
        return _elems.remove(index);
    }

    public int indexOf(Object o) {
        return _elems.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return _elems.lastIndexOf(o);
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
