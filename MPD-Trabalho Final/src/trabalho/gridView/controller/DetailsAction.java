package trabalho.gridView.controller;

import trabalho.gridModel.GridViewModelAdapter;
import trabalho.propertiesViewer.PropertiesViewer;
import utils.Utils;

public class DetailsAction implements IGridViewAction {

    private final GridViewModelAdapter _model;

    public DetailsAction(GridViewModelAdapter model) {
        _model = model;
    }

    public String getName() {
        return "Details";
    }

    public void actionPerformed(int[] arg) {
        final Object obj = _model.get(arg[0]);
        PropertiesViewer pView = new PropertiesViewer(obj);
        Utils.launchDialog(pView, true, "Details");
    }

}
