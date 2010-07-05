package serie1.app;

import serie1.propertiesViewer.PropertiesViewer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;

public class PropViewerMain {

    private static final TestObject testObj = new TestObject();

    public static void main(String args[]) throws IOException {

        PropertiesViewer<TestObject> _propView =
                new PropertiesViewer<TestObject>(testObj);
        JFrame frame = new JFrame("Test PropertyViewer");
        frame.add(_propView);

//        _propView.addPropertySetter("prop1",
//                new GenericPropertySetter<TestObject>("prop1",Integer.class));
//
//        _propView.addPropertySetter("prop2",
//                new GenericPropertySetter<TestObject>("prop2",String.class));
//
//        _propView.addPropertySetter("prop3",
//                new GenericPropertySetter<TestObject>("prop3",Boolean.class));

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
