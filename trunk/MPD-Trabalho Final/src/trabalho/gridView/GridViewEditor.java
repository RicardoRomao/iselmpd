package trabalho.gridView;

import trabalho.gridModel.GridViewModelAdapter;
import trabalho.gridView.controller.AddAction;
import trabalho.gridView.controller.RemoveAction;
import trabalho.gridView.controller.UpdateAction;

@SuppressWarnings("serial")
public class GridViewEditor extends GridView {
	public <V> GridViewEditor(GridViewModelAdapter<V> model) {
		super(model);
		addGridViewAction(new AddAction(model));
		addGridViewAction(new RemoveAction(model));
		addGridViewAction(new UpdateAction(model));
	}
}