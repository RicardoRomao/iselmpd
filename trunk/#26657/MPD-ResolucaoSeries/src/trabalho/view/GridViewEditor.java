package trabalho.view;

import trabalho.misc.AddAction;
import trabalho.misc.GridViewModel;
import trabalho.misc.GridViewModelAdapter;
import trabalho.misc.RemoveAction;
import trabalho.misc.UpdateAction;

@SuppressWarnings("serial")
public class GridViewEditor extends GridView {
	public <V> GridViewEditor(GridViewModel<V> model) {
		super(model);
		addGridViewAction(new AddAction(model));
		addGridViewAction(new RemoveAction((GridViewModelAdapter<V>)model));
		addGridViewAction(new UpdateAction((GridViewModelAdapter<V>)model));
	}
}