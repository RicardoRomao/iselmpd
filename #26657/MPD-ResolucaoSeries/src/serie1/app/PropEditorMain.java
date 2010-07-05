package serie1.app;

import serie1.propertiesViewer.PropertiesEditor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class PropEditorMain {

    private static final TestObject testObj = new TestObject();

    public static void main(String[] args){

        PropertiesEditor _propEdit =
                new PropertiesEditor<TestObject>(testObj);

        JFrame frame = new JFrame("Test PropertyViewer");
        frame.add(_propEdit);

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
