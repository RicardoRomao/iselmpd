package trabalho.gridView.controller;

import trabalho.gridModel.GridViewModel;
import trabalho.propertiesViewer.PropertiesViewer;
import utils.Utils;

public class DetailsAction implements IGridViewAction {

    private final GridViewModel _model;

    public DetailsAction(GridViewModel model) {
        _model = model;
    }

    public String getName() { return "Details"; }

    public void actionPerformed(int[] arg) {
        if (arg.length == 0) return;
        PropertiesViewer pView;
        for (int i = 0; i<arg.length; i++) {
            final Object obj = _model.get(arg[i]);
            pView = new PropertiesViewer(obj);
            Utils.launchDialog(pView, true, "Details");
        }
    }

}
