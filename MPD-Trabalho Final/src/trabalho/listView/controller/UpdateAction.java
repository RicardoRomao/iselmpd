package trabalho.listView.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.SwingUtilities;
import trabalho.domainObjects.Product;
import trabalho.listView.SelectedItens;
import trabalho.propertiesViewer.PropertiesEditor;
import trabalho.unitOfWork.UnitOfWork;
import utils.Utils;

public class UpdateAction implements IListViewAction{

    private final List _model;

    public String getName() {
        return "Update";
    }

    public UpdateAction(List model) { _model = model; }

    public void actionPerformed(SelectedItens arg) {
        for (final int idx : arg.indices) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    PropertiesEditor<Product> prodEdit = new PropertiesEditor(_model.get(idx));
                    prodEdit.addUpdateListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                if (UnitOfWork.getCurrent() != null)
                                    UnitOfWork.getCurrent().save();
                            }
                        }
                    );

                    prodEdit.addUpdateListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e) {
                            _model.set(idx,_model.get(idx));
                        }
                    });

                    Utils.launchFrame(prodEdit, null);
                }
            });
        }
    }

}
