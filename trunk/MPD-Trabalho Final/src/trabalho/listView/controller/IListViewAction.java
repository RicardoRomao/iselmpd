package trabalho.listView.controller;

import trabalho.listView.SelectedItens;

public interface IListViewAction{
  String getName();
  void actionPerformed(SelectedItens arg);
}
