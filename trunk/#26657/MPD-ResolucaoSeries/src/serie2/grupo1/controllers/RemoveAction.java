package serie2.grupo1.controllers;

import java.util.List;

import serie2.grupo1.view.IListViewAction;
import serie2.grupo1.view.SelectedItens;

public class RemoveAction implements IListViewAction{
  /** 
   * @uml.property name="model"
   * @uml.associationEnd aggregation="shared"
   */
  List model;
  public RemoveAction(List model){
    this.model = model;
  }
  public String getName() {
    return "Remove";
  }
  public void actionPerformed(SelectedItens arg) {
    for(int i = arg.indices.length-1; i>=0; i--)
      model.remove(arg.indices[i]);
  }
}