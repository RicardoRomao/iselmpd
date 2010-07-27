package utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.plaf.multi.MultiLabelUI;

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
    }

    public static void showError(String msg, String title) {
        JTextArea txt = new JTextArea(msg);
        txt.setOpaque(true);
        txt.setBackground(Color.WHITE);
        txt.setForeground(Color.RED);
        txt.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        txt.setEditable(false);
        utils.Utils.launchFrame(txt, title);
    }

}
