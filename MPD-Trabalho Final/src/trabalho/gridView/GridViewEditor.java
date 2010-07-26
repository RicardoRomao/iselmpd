package trabalho.gridView;

import trabalho.gridModel.GridViewModelAdapter;
import trabalho.gridView.controller.AddAction;
import trabalho.gridView.controller.DetailsAction;
import trabalho.gridView.controller.RemoveAction;
import trabalho.gridView.controller.UpdateAction;

@SuppressWarnings("serial")
public class GridViewEditor extends GridView {
	public <V> GridViewEditor(GridViewModelAdapter<V> model, Class<?> klass) {
		super(model, klass);
		addGridViewAction(new AddAction(model,getGenericClass()));
		addGridViewAction(new RemoveAction(model));
		addGridViewAction(new UpdateAction(model));
		addGridViewAction(new DetailsAction(model));
	}
}