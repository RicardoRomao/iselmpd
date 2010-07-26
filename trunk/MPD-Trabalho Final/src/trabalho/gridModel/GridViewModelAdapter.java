package trabalho.gridModel;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import annotations.VisibleProperty;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import trabalho.propertiesUtils.PropertiesUtils;
import trabalho.propertiesUtils.IPropertyFilter;
import trabalho.propertiesUtils.PropertyKind;

public class GridViewModelAdapter<V> implements GridViewModel<V> {

    private Map<String, Method> _properties = null;
    private final List<V> _elems;
    private final List<TableModelListener> _listeners;
    public List<V> listed = new LinkedList<V>();

    public GridViewModelAdapter() { this(null); }

    public GridViewModelAdapter(Collection<V> col) {
        _listeners = new LinkedList<TableModelListener>();
        _elems = new LinkedList<V>();
        if (col != null)
            _elems.addAll(col);
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

    private void fireChanged(TableModelEvent evt) {
        for (TableModelListener l : _listeners) {
            l.tableChanged(evt);
        }
    }

    @Override
    public void add(V line) {
        _elems.add(line);
        TableModelEvent evt = new TableModelEvent(this, _elems.size() - 1, _elems.size() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
        fireChanged(evt);
    }

    @Override
    public void remove(V line) {
        _elems.remove(line);
        TableModelEvent evt = new TableModelEvent(this, _elems.size() + 1, _elems.size() + 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        fireChanged(evt);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        _listeners.add(l);
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return Object.class;
    }

    @Override
    public int getColumnCount() {
        ensurePropertiesLoaded();
        return _properties == null ? 0 : _properties.size();
    }

    @Override
    public String getColumnName(int col) {
        ensurePropertiesLoaded();
        return _properties == null ? null : (String)(_properties.keySet().toArray()[col]);
    }

    @Override
    public int getRowCount() {
        return _elems.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        ensurePropertiesLoaded();
        String property = getColumnName(col);
        Object obj = null;
        if (property != null) {
            try {
                obj = _properties.get(property).invoke(_elems.get(row));
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        return obj;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        _listeners.remove(l);
    }

    @Override
    public void setValueAt(Object obj, int row, int col) {
        _elems.set(row, (V)obj);
        TableModelEvent evt = new TableModelEvent(this, row);
        fireChanged(evt);

    }

    public V get(int index){
        return _elems.get(index);
    }

    public int indexOf(V e) { return _elems.indexOf(e); }

}
