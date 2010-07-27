package trabalho.gridView;

import trabalho.gridModel.GridViewModel;
import trabalho.gridView.controller.AddAction;
import trabalho.gridView.controller.DetailsAction;
import trabalho.gridView.controller.RemoveAction;
import trabalho.gridView.controller.UpdateAction;

@SuppressWarnings("serial")
public class GridViewEditor extends GridView {
	public <V> GridViewEditor(GridViewModel<V> model, Class<V> klass) {
		super(model);
		addGridViewAction(new AddAction(model,klass));
		addGridViewAction(new RemoveAction(model));
		addGridViewAction(new UpdateAction(model));
		addGridViewAction(new DetailsAction(model));
	}
}