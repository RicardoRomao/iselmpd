package trabalho.misc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import trabalho.propertiesUtils.PropertiesUtils;

@SuppressWarnings("serial")
public class TableModelAdapter<T> extends AbstractTableModel implements List<T> {

    private LinkedList<T> _lst = new LinkedList<T>();
    private Map<String, Method> properties = null;

    private synchronized void ensurePropertiesLoaded() {
        if (properties == null && _lst.size() > 0) {
            properties = PropertiesUtils.getVisibleProperties(_lst.getFirst(), new IPropertyFilter() {

                @Override
                public boolean accept(VisibleProperty prop) {
                    return prop.kind() == PropertyKind.Simple;
                }
            });
        }
    }

    @Override
    public int getColumnCount() {
        ensurePropertiesLoaded();
        return properties == null ? 0 : properties.size();
    }

    @Override
    public String getColumnName(int col) {
        ensurePropertiesLoaded();
        return (String) (properties == null ? "" : properties.keySet().toArray()[col]);
    }

    @Override
    public int getRowCount() {
        return _lst.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        ensurePropertiesLoaded();
        String property = getColumnName(col);
        Object obj = null;
        if (!property.equals("")) {
            try {
                obj = properties.get(property).invoke(_lst.get(row));
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        return obj;
    }

    @Override
    public boolean add(T obj) {
        return _lst.add(obj);
    }

    @Override
    public void add(int idx, T obj) {
        _lst.add(idx, obj);
    }

    @Override
    public boolean addAll(Collection<? extends T> lst) {
        return _lst.addAll(lst);
    }

    @Override
    public boolean addAll(int idx, Collection<? extends T> lst) {
        return _lst.addAll(idx, lst);
    }

    @Override
    public void clear() {
        _lst.clear();
    }

    @Override
    public boolean contains(Object obj) {
        return _lst.contains(obj);
    }

    @Override
    public boolean containsAll(Collection<?> lst) {
        return _lst.containsAll(lst);
    }

    @Override
    public T get(int idx) {
        return _lst.get(idx);
    }

    @Override
    public int indexOf(Object obj) {
        return _lst.indexOf(obj);
    }

    @Override
    public boolean isEmpty() {
        return _lst.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return _lst.iterator();
    }

    @Override
    public int lastIndexOf(Object obj) {
        return _lst.lastIndexOf(obj);
    }

    @Override
    public ListIterator<T> listIterator() {
        return _lst.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int idx) {
        return _lst.listIterator(idx);
    }

    @Override
    public boolean remove(Object obj) {
        return _lst.remove(obj);
    }

    @Override
    public T remove(int idx) {
        return _lst.remove(idx);
    }

    @Override
    public boolean removeAll(Collection<?> lst) {
        return _lst.removeAll(lst);
    }

    @Override
    public boolean retainAll(Collection<?> lst) {
        return _lst.retainAll(lst);
    }

    @Override
    public T set(int idx, T obj) {
        return _lst.set(idx, obj);
    }

    @Override
    public int size() {
        return _lst.size();
    }

    @Override
    public List<T> subList(int ini, int end) {
        return _lst.subList(ini, end);
    }

    @Override
    public Object[] toArray() {
        return _lst.toArray();
    }

    @SuppressWarnings("hiding")
    @Override
    public <T> T[] toArray(T[] lst) {
        return _lst.toArray(lst);
    }
}
