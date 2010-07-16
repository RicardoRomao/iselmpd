package trabalho.app;

import java.awt.BorderLayout;
import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import serie3.grupo1.domainObjects.Product;
import trabalho.misc.AddAction;
import trabalho.misc.GridViewModel;
import trabalho.misc.GridViewModelAdapter;
import trabalho.misc.RemoveAction;
import trabalho.view.GridView;
import trabalho.view.GridViewEditor;

public class Program {

    private static void launchDialog(final JComponent cmp, final boolean modal, final String title) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                JDialog dialog = new JDialog();
                dialog.add(cmp, BorderLayout.CENTER);
                //
                // Dialog properties
                //
                dialog.setModal(modal);
                dialog.setTitle(title);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        //TableModelAdapter<Product> products = new TableModelAdapter<Product>();
        GridViewModelAdapter<Product> products = new GridViewModelAdapter<Product>();
        fillProducts(products);
        //GridView view = new GridView(products);
        //view.addGridViewAction(new RemoveAction(products));
        //view.addGridViewAction(new AddAction(products));
	GridViewEditor view = new GridViewEditor(products);
        launchDialog(view, true, "Products");
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
