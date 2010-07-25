package utils;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Utils {

    public static void launchDialog(final JComponent cmp, final boolean modal, final String title) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                JDialog dialog = new JDialog();
                dialog.add(cmp, BorderLayout.CENTER);
                //
                // Dialog properties
                //
                dialog.setModal(true);
                //dialog.setModal(modal);
                dialog.setTitle(title);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    public static void launchFrame(final JComponent cmp, String title) {
        JFrame frame = new JFrame(title);
        frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(cmp);
        frame.setVisible(true);
        frame.pack();

//        frame.addWindowListener(new WindowAdapter() {
//
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
    }
}
