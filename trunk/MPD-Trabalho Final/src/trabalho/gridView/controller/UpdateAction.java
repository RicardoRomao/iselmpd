package trabalho.gridView.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import trabalho.gridModel.GridViewModel;
import trabalho.propertiesViewer.PropertiesEditor;
import trabalho.unitOfWork.UnitOfWork;

import utils.Utils;

public class UpdateAction implements IGridViewAction {

    private final GridViewModel _model;

    public UpdateAction(GridViewModel model) {
        this._model = model;
    }

    @Override
    public void actionPerformed(final int[] arg) {
        if (arg.length == 0) return;
        PropertiesEditor pEdit;
        for (int i = 0; i < arg.length; i++) {
            final int idx = i;
            final Object obj = _model.get(arg[0]);
            pEdit = new PropertiesEditor(obj);
            pEdit.addUpdateListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    _model.set(idx, obj);
                    if (UnitOfWork.getCurrent() != null)
                        UnitOfWork.getCurrent().save();
                }
            });
            Utils.launchDialog(pEdit, true, "Update");
        }
    }

    @Override
    public String getName() {
        return "Update";
    }
}
