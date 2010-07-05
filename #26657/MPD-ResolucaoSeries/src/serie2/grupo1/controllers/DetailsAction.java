package serie2.grupo1.controllers;

import java.awt.Dialog.ModalExclusionType;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import serie1.propertiesViewer.PropertiesViewer;
import serie2.grupo1.model.Product;
import serie2.grupo1.view.IListViewAction;
import serie2.grupo1.view.SelectedItens;

public class DetailsAction implements IListViewAction {

    List _model;

    public DetailsAction(List model) {
        _model = model;
    }

    public String getName() {
        return "Details";
    }

    public void actionPerformed(SelectedItens arg) {
        for (final int idx : arg.indices) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    PropertiesViewer<Product> prodView = new PropertiesViewer(_model.get(idx));
                    JFrame frame = new JFrame();
                    frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
                    frame.add(prodView);
                    frame.setVisible(true);
                    frame.pack();
                }
            });
        }

    }
}
