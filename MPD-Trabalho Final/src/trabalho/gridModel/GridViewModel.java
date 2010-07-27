package trabalho.gridModel;

import javax.swing.table.TableModel;

public interface GridViewModel<V> extends TableModel {
    public void add(V line);
    public void remove(V object);
    public V get(int index);
    public void set(int index, V object);
}