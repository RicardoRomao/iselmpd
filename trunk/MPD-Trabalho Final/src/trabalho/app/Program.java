package trabalho.app;

import trabalho.dataMappers.ProductMapper;
import trabalho.dataMappers.registry.MapperRegistry;
import trabalho.domainObjects.Product;
import trabalho.jdbc.JdbcConnector;
import trabalho.jdbc.JdbcDataSource;
import trabalho.listModel.SimpleListViewModel;
import trabalho.listView.ListView;
import trabalho.unitOfWork.UnitOfWork;
import trabalho.gridModel.GridViewModelAdapter;
import trabalho.gridView.GridViewEditor;
import trabalho.tableModel.TableModelAdapter;
import utils.Utils;

public class Program {

    public static void main(String[] args) {

        UnitOfWork uow = new UnitOfWork(new JdbcConnector(JdbcDataSource.getDataSource()));
        UnitOfWork.setCurrent(uow);
        
        ProductMapper pMapper = 
                (ProductMapper) MapperRegistry.current().get(Product.class);

        SimpleListViewModel listModel = new SimpleListViewModel();
        pMapper.loadAllInto(listModel);

        ListView lView = new ListView(listModel);
        //lView.addListViewAction(new UpdateAction(listModel));

        TableModelAdapter gridModel = new TableModelAdapter(Product.class);
        pMapper.loadAllInto(gridModel);

        GridViewModelAdapter gridModelA = new GridViewModelAdapter(gridModel);

        GridViewEditor gView = new GridViewEditor(gridModelA);
        //gView.addGridViewAction(new UpdateAction(gridModel));
        
        Utils.launchDialog(gView, true, "Product List");
        //Utils.launchDialog(lView, true, "Product List");
    }
}
