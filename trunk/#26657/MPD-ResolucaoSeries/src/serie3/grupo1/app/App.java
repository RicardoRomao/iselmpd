package serie3.grupo1.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import serie1.propertiesViewer.PropertiesViewer;
import serie3.grupo1.dataMappers.registry.MapperRegistry;
import serie3.grupo1.dataMappers.OrderDetailsMapper;
import serie3.grupo1.dataMappers.OrderMapper;
import serie3.grupo1.domainObjects.Order;
import serie3.grupo1.domainObjects.OrderDetails;
import serie3.grupo1.domainObjects.primaryKeys.PkOrderDetails;

public class App {

    public static void main(String[] args){
        viewOrderDetails();
    }


    public static void viewOrderDetails() {

        OrderDetailsMapper orderDetMap = (OrderDetailsMapper)
                MapperRegistry.current().get(OrderDetails.class);

        OrderDetails o = orderDetMap.getById(new PkOrderDetails(11077,10));

        PropertiesViewer<OrderDetails> _propView =
                new PropertiesViewer<OrderDetails>(o);
        JFrame frame = new JFrame("Order Details");
        frame.add(_propView);
        frame.setVisible(true);
        frame.pack();

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void viewOrder() {

        OrderMapper orderMap = (OrderMapper)
                MapperRegistry.current().get(Order.class);

        Order o = orderMap.getById(11077);

        PropertiesViewer<Order> _propView =
                new PropertiesViewer<Order>(o);
        JFrame frame = new JFrame("Order Details");
        frame.add(_propView);
        frame.setVisible(true);
        frame.pack();

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


}
