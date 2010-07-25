package trabalho.tableModel;

import annotations.VisibleProperty;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import trabalho.propertiesUtils.PropertiesUtils;
import trabalho.propertiesUtils.IPropertyFilter;
import trabalho.propertiesUtils.PropertyKind;

public class TableModelAdapter<T> extends AbstractTableModel implements List<T> {

    private final LinkedList<T> _elems = new LinkedList<T>();
    private Map<String, Method> _properties;

    public TableModelAdapter(Class<?> klass) {
//        _properties = PropertiesUtils.getVisibleProperties(klass, new IPropertyFilter(){
//            public boolean accept(VisibleProperty prop) {
//                return prop.kind() == PropertyKind.Simple;
//            }
//        });
    }

    private synchronized void ensurePropertiesLoaded() {
        if (_properties == null && _elems.size() > 0) {
            _properties = PropertiesUtils.getVisibleProperties(_elems.get(0), new IPropertyFilter() {
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
        return _properties == null ? 0 : _properties.size();
    }

    @Override
    public int getRowCount() { return _elems.size(); }

    @Override
    public String getColumnName(int col) {
        ensurePropertiesLoaded();
        return _properties == null ? null : (String) _properties.keySet().toArray()[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        ensurePropertiesLoaded();
        String property = getColumnName(col);
        Object obj = null;
        if (!property.equals("")) {
            try {
                obj = _properties.get(property).invoke(_elems.get(row));
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        return obj;
        //return _elems.get(row);
    }


    @Override
    public int size() {
        return _elems.size();
    }

    @Override
    public boolean add(T obj) {
        if (_elems.add(obj)) {
            fireTableRowsInserted(size()-1, size());
            return true;
        }
        return false;
    }

    @Override
    public void add(int idx, T obj) {
        _elems.add(idx, obj);
        fireTableRowsUpdated(idx, idx);
    }

    @Override
    public boolean addAll(Collection<? extends T> lst) {
        int oldSize = size();
        if (_elems.addAll(lst)) {
            fireTableRowsInserted(oldSize, size());
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int idx, Collection<? extends T> lst) {
        if (_elems.addAll(lst)) {
            fireTableRowsInserted(idx, size());
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        _elems.clear();
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public boolean contains(Object obj) {
        return _elems.contains(obj);
    }

    @Override
    public boolean containsAll(Collection<?> lst) {
        return _elems.containsAll(lst);
    }

    @Override
    public T get(int idx) {
        return _elems.get(idx);
    }

    @Override
    public int indexOf(Object obj) {
        return _elems.indexOf(obj);
    }

    @Override
    public boolean isEmpty() {
        return _elems.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return _elems.iterator();
    }

    @Override
    public int lastIndexOf(Object obj) {
        return _elems.lastIndexOf(obj);
    }

    @Override
    public ListIterator<T> listIterator() {
        return _elems.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int idx) {
        return _elems.listIterator(idx);
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public boolean remove(Object obj) {
        if (_elems.remove(obj)) {
            fireTableRowsDeleted(0, size());
            return true;
        }
        return false;
    }

    @Override
    public T remove(int idx) {
        T oldElem = _elems.remove(idx);
        fireTableRowsDeleted(idx, idx);
        return oldElem;
    }

    @Override
    public boolean removeAll(Collection<?> lst) {
        if (_elems.removeAll(lst)) {
            fireTableRowsDeleted(0, size());
            return true;
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> lst) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T set(int idx, T obj) {
        T oldElem = _elems.set(idx, obj);
        fireTableRowsUpdated(idx, idx);
        return oldElem;
    }

    @Override
    public List<T> subList(int ini, int end) {
        return _elems.subList(ini, end);
    }

    @Override
    public Object[] toArray() {
        return _elems.toArray();
    }

    @SuppressWarnings("hiding")
    @Override
    public <T> T[] toArray(T[] lst) {
        return _elems.toArray(lst);
    }
}
