package trabalho.gridView;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

import trabalho.gridView.controller.IGridViewAction;

@SuppressWarnings("serial")
public class GridView extends JPanel {

    private final Class<?> _genericClass;
    
    public GridView(TableModel model, Class<?> klass) {
        _genericClass = klass;
        setLayout(new BorderLayout());
        south = new JPanel();
        add(south, BorderLayout.SOUTH);
        tblItens = new JTable(model);
        JScrollPane pane = new JScrollPane(tblItens);
        pane.setBorder(new TitledBorder("Itens:"));
        add(pane, BorderLayout.CENTER);
    }

    public void addGridViewAction(final IGridViewAction action) {
        JButton bt = new JButton(action.getName());
        south.add(bt);
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(tblItens.getSelectedRows());
            }
        });
    }

    public Class<?> getGenericClass() { return _genericClass; }

    private JTable tblItens;
    private JPanel south;
}
