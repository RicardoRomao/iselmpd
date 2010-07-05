package serie2.grupo2.testFw;

import java.awt.BorderLayout;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import serie2.grupo1.model.SimpleListViewModel;
import serie2.grupo1.view.ListView;

public class ListTestResultListener implements ITestResultListener {

    private final ListView _view;
    private final List<String> _model;
    private final Hashtable<String, Integer> _temps;

    public ListTestResultListener() {
        _temps = new Hashtable();
        _model = new SimpleListViewModel<String>();
        _view = new ListView((ListModel) _model);

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                JDialog dialog = new JDialog();
                dialog.add(_view, BorderLayout.CENTER);
                dialog.setModal(true);
                dialog.setTitle("TestResult results");
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    public void notifyTestStart(Test t) {
        _temps.put(t.getName(), _model.size());
        _model.add(t.getName() + " has started...");
    }

    public void notifyTestEnd(Test t) {
        String currMsg = _model.get(_temps.get(t.getName()));
        _model.set(_temps.get(t.getName()), currMsg + " ... ended!");
    }

    public void notifyTestFailure(TestFailure f) {
        String currMsg = _model.get(_temps.get(f.fFailedTest.getName()));
        _model.set(_temps.get(f.fFailedTest.getName()),
                currMsg + " " + f.fFailedTest.getName()
                + "\n\tFailed with the following message:\n"
                + f.fThrownException.getLocalizedMessage());
    }

    public void notifyTestError(TestFailure f) {
        String currMsg = _model.get(_temps.get(f.fFailedTest.getName()));
        _model.set(_temps.get(f.fFailedTest.getName()),
                currMsg + " " + f.fFailedTest.getName()
                + "\n\tResulted in an error with the following message:\n"
                + f.fThrownException.getLocalizedMessage());
    }
}
