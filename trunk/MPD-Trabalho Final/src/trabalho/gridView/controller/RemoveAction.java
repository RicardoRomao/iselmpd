package trabalho.gridView.controller;

import trabalho.gridModel.GridViewModel;
import trabalho.gridModel.GridViewModelAdapter;

public class RemoveAction implements IGridViewAction {

    @SuppressWarnings("unchecked")
    private final GridViewModelAdapter model;

    @SuppressWarnings("unchecked")
    public RemoveAction(GridViewModel model) {
        this.model = (GridViewModelAdapter) model;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(int[] arg) {
        for (int i = arg.length - 1; i >= 0; i--) {
            model.remove(model.get(arg[i]));
        }
    }

    @Override
    public String getName() {
        return "Remove";
    }
}
