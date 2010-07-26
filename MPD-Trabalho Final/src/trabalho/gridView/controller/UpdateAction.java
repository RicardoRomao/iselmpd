package trabalho.gridView.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import trabalho.gridModel.GridViewModelAdapter;
import trabalho.propertiesViewer.PropertiesEditor;
import trabalho.unitOfWork.UnitOfWork;

import utils.Utils;

public class UpdateAction implements IGridViewAction {

    private final GridViewModelAdapter model;

    public UpdateAction(GridViewModelAdapter model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(final int[] arg) {
        final Object obj = model.get(arg[0]);
        PropertiesEditor pedit = new PropertiesEditor(obj, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                model.setValueAt(obj, arg[0], 0);
                if (UnitOfWork.getCurrent() != null)
                    UnitOfWork.getCurrent().save();
            }
        });
        Utils.launchDialog(pedit, true, "Update");
    }

    @Override
    public String getName() {
        return "Update";
    }
}
