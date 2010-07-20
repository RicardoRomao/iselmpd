package trabalho.misc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import annotations.VisibleProperty;
import annotations.VisibleProperty.PropertyKind;
import trabalho.propertiesUtils.PropertiesUtils;

public class GridViewModelAdapter<V> implements GridViewModel<V> {

    private Map<String, Method> properties = null;
    private List<V> rows;
    private List<TableModelListener> listeners;

    public GridViewModelAdapter() {
        listeners = new LinkedList<TableModelListener>();
        rows = new LinkedList<V>();
    }

    private synchronized void ensurePropertiesLoaded() {
        if (properties == null && rows.size() > 0) {
            properties = PropertiesUtils.getVisibleProperties(rows.get(0), new IPropertyFilter() {

                @Override
                public boolean accept(VisibleProperty prop) {
                    return prop.kind() == PropertyKind.Simple;
                }
            });
        }
    }

    private void fireChanged(TableModelEvent evt) {
        for (TableModelListener l : listeners) {
            l.tableChanged(evt);
        }
    }

    @Override
    public void add(V line) {
        rows.add(line);
        TableModelEvent evt = new TableModelEvent(this, rows.size() - 1, rows.size() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
        fireChanged(evt);
    }

    @Override
    public void remove(V line) {
        rows.remove(line);
        TableModelEvent evt = new TableModelEvent(this, rows.size() + 1, rows.size() + 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        fireChanged(evt);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return Object.class;
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
        return rows.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        ensurePropertiesLoaded();
        String property = getColumnName(col);
        Object obj = null;
        if (!property.equals("")) {
            try {
                obj = properties.get(property).invoke(rows.get(row));
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
        listeners.remove(l);
    }

    @Override
    public void setValueAt(Object obj, int row, int col) {
    }

    public V getRowAt(int row) {
        return rows.get(row);
    }
}
