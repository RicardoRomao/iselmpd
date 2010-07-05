package serie2.grupo1.app;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import serie2.grupo1.model.Product;
import serie2.grupo1.view.ListView;
import serie2.grupo1.controllers.DetailsAction;
import serie2.grupo1.controllers.RemoveAction;
import serie2.grupo1.controllers.UpdateAction;
import serie2.grupo1.model.SortedListModel;

public class Program {
    
  private static void launchDialog(final JComponent cmp, final boolean modal, final String title){
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
  
  /**
   * Populate the destination itens Collection with the created Product instances.  
   * @param fileName Source file name.
   */
  public static void loadItensFromFile(String fileName, Collection<Product> itens){
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      String line = null;
      while((line = reader.readLine()) != null){
        itens.add(Product.valueOf(line));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }catch(NumberFormatException e){
      e.printStackTrace();
    }
  }
  
  /**
   * Main function
   */
  public static void main(String[] args) {
    //SimpleListViewModel<Product> prods = new SimpleListViewModel<Product>();

    SortedListModel<Product> prods = SortedListModel.getInstance(
            new Comparator<Product>(){

            public int compare(Product o1, Product o2) {
                return (int)(o1.getPrice()-o2.getPrice());
            }
    });
    loadItensFromFile("Inventario02.txt", prods);
    ListView view = new ListView(prods);
    launchDialog(view, true, "Products");
    view.addListViewAction(new RemoveAction(prods));
    view.addListViewAction(new DetailsAction(prods));
    view.addListViewAction(new UpdateAction(prods));
  }
}