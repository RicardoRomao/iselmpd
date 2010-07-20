package trabalho.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import trabalho.propertiesViewer.PropertiesEditor;

import utils.Utils;

public class UpdateAction implements IGridViewAction {
    @SuppressWarnings("unchecked")
    private final GridViewModelAdapter model;

    @SuppressWarnings("unchecked")
    public UpdateAction(GridViewModelAdapter model) {
	this.model = model;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(final int[] arg) {
	final Object obj = model.getRowAt(arg[0]);
	PropertiesEditor pedit = new PropertiesEditor(obj);
	pedit.addUpdateListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent evt) {
		model.setValueAt(obj, arg[0], 0);
	    }
	});
	Utils.launchDialog(pedit, true, "Update");
    }

    @Override
    public String getName() {
	return "Update";
    }
}
