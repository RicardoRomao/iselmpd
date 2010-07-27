package trabalho.gridView.controller;

import exceptions.GridViewControllerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import trabalho.domainObjects.DomainObject;
import trabalho.gridModel.GridViewModel;
import trabalho.propertiesViewer.PropertiesCreator;
import trabalho.unitOfWork.UnitOfWork;
import utils.Utils;

public class AddAction implements IGridViewAction {

    private final GridViewModel _model;
    private final Class<? extends DomainObject> _class;

    public AddAction(GridViewModel model, Class<? extends DomainObject> klass) {
        _model = model;
        _class = klass;
    }

    @Override
    public void actionPerformed(final int[] arg) {
        try {
            final Object obj = _class.newInstance();

            PropertiesCreator pAdd = new PropertiesCreator(obj);
            pAdd.addUpdateListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    _model.add(obj);
                    if (UnitOfWork.getCurrent() != null)
                        UnitOfWork.getCurrent().save();
                    }
            });

            Utils.launchDialog(pAdd, true, "Add");
            
        } catch (InstantiationException ex) {
            ex.printStackTrace();
            throw new GridViewControllerException();
        } catch (IllegalAccessException ex) {
            throw new GridViewControllerException();
        } catch (IllegalArgumentException ex) {
            throw new GridViewControllerException();
        } catch (SecurityException ex) {
            throw new GridViewControllerException();
        }

    }

    @Override
    public String getName() { return "Add"; }
}
