package trabalho.misc;

import trabalho.domainObjects.Product;

public class AddAction implements IGridViewAction {
    @SuppressWarnings("unchecked")
    private final GridViewModel model;

    @SuppressWarnings("unchecked")
    public AddAction(GridViewModel model) {
        this.model = model;
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
