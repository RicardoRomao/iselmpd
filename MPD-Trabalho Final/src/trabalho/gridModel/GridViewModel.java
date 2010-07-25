package trabalho.gridModel;

import javax.swing.table.TableModel;

public interface GridViewModel<V> extends TableModel {
    public void add(V line);
    public void remove(V object);
}