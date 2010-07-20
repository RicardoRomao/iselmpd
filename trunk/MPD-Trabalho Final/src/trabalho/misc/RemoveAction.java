package trabalho.misc;

public class RemoveAction implements IGridViewAction {

    @SuppressWarnings("unchecked")
    private final GridViewModelAdapter model;

    @SuppressWarnings("unchecked")
    public RemoveAction(GridViewModelAdapter model) {
        this.model = model;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(int[] arg) {
        for (int i = arg.length - 1; i >= 0; i--) {
            model.remove(model.getRowAt(arg[i]));
        }
    }

    @Override
    public String getName() {
        return "Remove";
    }
}
