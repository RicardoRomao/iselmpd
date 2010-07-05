package serie1.swingUtils;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
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
                dialog.setModal(modal);
                dialog.setTitle(title);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setVisible(true);
            }
        });

    }
}
