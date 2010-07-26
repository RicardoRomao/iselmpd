package trabalho.gridView.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabalho.gridModel.GridViewModelAdapter;
import trabalho.propertiesViewer.PropertiesCreator;
import trabalho.unitOfWork.UnitOfWork;
import utils.Utils;

public class AddAction implements IGridViewAction {

    private final GridViewModelAdapter _model;
    private final Class<?> _class;

    public AddAction(GridViewModelAdapter model, Class<?> klass) {
        _model = model;
        _class = klass;
    }

    @Override
    public void actionPerformed(final int[] arg) {
        try {
            final Object obj = _class.newInstance();
            _model.add(obj);

            PropertiesCreator pAdd = new PropertiesCreator(obj, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    _model.setValueAt(obj, _model.indexOf(obj), 0);
                    if (UnitOfWork.getCurrent() != null)
                        UnitOfWork.getCurrent().save();
                    }
            });
            Utils.launchDialog(pAdd, true, "Add");

        } catch (InstantiationException ex) {
            ex.printStackTrace();
            Logger.getLogger(AddAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AddAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(AddAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(AddAction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getName() {
        return "Add";

    }
}
