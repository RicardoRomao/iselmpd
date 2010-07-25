package trabalho.gridView.controller;

import trabalho.domainObjects.Product;
import trabalho.gridModel.GridViewModel;
import trabalho.gridModel.GridViewModelAdapter;

public class AddAction implements IGridViewAction {
    @SuppressWarnings("unchecked")
    private final GridViewModelAdapter model;

    @SuppressWarnings("unchecked")
    public AddAction(GridViewModel model) {
        this.model = (GridViewModelAdapter) model;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(int[] arg) {
        model.add(new Product(1, null, "PF", null, null, 1.25, 2));
    }

    @Override
    public String getName() {
        return "Add";
    }
}
