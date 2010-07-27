package trabalho.gridView.controller;

import trabalho.domainObjects.DomainObject;
import trabalho.gridModel.GridViewModel;
import trabalho.unitOfWork.UnitOfWork;

public class RemoveAction<V extends DomainObject> implements IGridViewAction {

    private final GridViewModel<V> _model;

    public RemoveAction(GridViewModel<V> model) {
        this._model = model;
    }

    @Override
    public void actionPerformed(int[] arg) {
        for (int i = arg.length - 1; i >= 0; i--) {
            _model.get(arg[i]).remove();
            _model.remove(_model.get(arg[i]));
        }
        if (UnitOfWork.getCurrent() != null)
            UnitOfWork.getCurrent().save();
    }

    @Override
    public String getName() { return "Remove"; }
}
