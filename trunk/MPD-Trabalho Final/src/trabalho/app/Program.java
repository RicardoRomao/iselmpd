package trabalho.app;

import java.util.Calendar;
import java.util.Locale;
import trabalho.dataMappers.OrderMapper;
import trabalho.dataMappers.ProductMapper;
import trabalho.dataMappers.registry.MapperRegistry;
import trabalho.domainObjects.Order;
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
        
        OrderMapper oMapper =
                (OrderMapper) MapperRegistry.current().get(Order.class);
        ProductMapper pMapper =
                (ProductMapper) MapperRegistry.current().get(Product.class);

        SimpleListViewModel listModel = new SimpleListViewModel();
        oMapper.loadAllInto(listModel);

        ListView lView = new ListView(listModel);

        TableModelAdapter gridModel = new TableModelAdapter(Order.class);
        oMapper.loadAllInto(gridModel);

        GridViewModelAdapter gridModelA = new GridViewModelAdapter(gridModel);

        GridViewEditor gView = new GridViewEditor(gridModelA,Order.class);
        Utils.launchDialog(gView, true, "Order List");
    }
}
