package serie2.grupo1.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;

/**
 * @uml.dependency   supplier="view.SelectedItens"
 */
public class ListView extends JPanel {

    private JList lstItens;
    private JPanel south;

    public ListView(ListModel model) {
        setLayout(new BorderLayout());
        south = new JPanel();
        add(south, BorderLayout.SOUTH);
        //
        // center
        //
        lstItens = new JList(model);
        JScrollPane pane = new JScrollPane(lstItens);
        pane.setBorder(new TitledBorder("Itens:"));
        add(pane, BorderLayout.CENTER);
    }

    public void addListViewAction(final IListViewAction action) {
        JButton bt = new JButton(action.getName());
        south.add(bt);
        bt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(new SelectedItens(
                        lstItens.getSelectedValues(),
                        lstItens.getSelectedIndices()));
            }
        });
    }
}
