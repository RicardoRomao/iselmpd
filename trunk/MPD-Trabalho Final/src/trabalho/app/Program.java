package trabalho.app;

import java.util.Collection;
import java.util.HashSet;

import trabalho.jdbc.JdbcConnector;
import trabalho.jdbc.JdbcDataSource;
import trabalho.dataMappers.ProductMapper;
import trabalho.domainObjects.Product;
import trabalho.unitOfWork.UnitOfWork;
import trabalho.misc.GridViewModel;
import trabalho.misc.GridViewModelAdapter;
import trabalho.view.GridViewEditor;
import utils.Utils;

public class Program {

    public static void main(String[] args) {
        UnitOfWork uow = new UnitOfWork(new JdbcConnector(JdbcDataSource.getDataSource()));

        //TableModelAdapter<Product> products = new TableModelAdapter<Product>();

        GridViewModelAdapter<Product> products = new GridViewModelAdapter<Product>();
        Collection<Product> pCol = new HashSet<Product>();

        //fillProducts(products);
        ProductMapper pMapper = (ProductMapper)uow.getMapper(Product.class);
        pMapper.loadAllInto(pCol);
        for (Product p : pCol)
            products.add(p);

        //GridView view = new GridView(products);
        //view.addGridViewAction(new RemoveAction(products));
        //view.addGridViewAction(new AddAction(products));
        GridViewEditor view = new GridViewEditor(products);
        Utils.launchDialog(view, true, "Products");
    }

    @SuppressWarnings("unused")
    private static void fillProducts(Collection<Product> itens) {
        itens.add(new Product(1, null, "PA", null, null, 2.5, 2));
        itens.add(new Product(2, null, "PB", null, null, 1.5, 1));
        itens.add(new Product(3, null, "PC", null, null, 1.0, 5));
        itens.add(new Product(4, null, "PD", null, null, 4.2, 8));
        itens.add(new Product(5, null, "PE", null, null, 12.25, 1));
    }

    private static void fillProducts(GridViewModel<Product> itens) {
        itens.add(new Product(1, null, "PA", null, null, 2.5, 2));
        itens.add(new Product(2, null, "PB", null, null, 1.5, 1));
        itens.add(new Product(3, null, "PC", null, null, 1.0, 5));
        itens.add(new Product(4, null, "PD", null, null, 4.2, 8));
        itens.add(new Product(5, null, "PE", null, null, 12.25, 1));
    }
}
