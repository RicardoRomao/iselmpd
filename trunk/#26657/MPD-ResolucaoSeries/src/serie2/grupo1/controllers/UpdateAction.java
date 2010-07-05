package serie2.grupo1.controllers;

import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import serie1.propertiesViewer.PropertiesEditor;
import serie2.grupo1.model.Product;
import serie2.grupo1.view.IListViewAction;
import serie2.grupo1.view.SelectedItens;

public class UpdateAction implements IListViewAction{

    List _model;

    public UpdateAction(List model){
        _model = model;
    }

    public String getName() {
        return "Update";
    }

    public void actionPerformed(SelectedItens arg) {
        for (final int idx : arg.indices) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    PropertiesEditor<Product> prodEdit = new PropertiesEditor(_model.get(idx));

                    prodEdit.addUpdateListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e) {
                            _model.set(idx,_model.get(idx));
                        }
                    });

                    JFrame frame = new JFrame();
                    frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
                    frame.add(prodEdit);
                    frame.setVisible(true);
                    frame.pack();
                }
            });
        }
    }

}
