package trabalho.app.mainView;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import trabalho.dataMappers.IDataMapper;
import trabalho.dataMappers.registry.MapperRegistry;
import trabalho.domainObjects.DomainObject;
import trabalho.gridModel.GridViewModel;
import trabalho.gridModel.GridViewModelAdapter;
import trabalho.gridView.GridViewEditor;
import trabalho.tableModel.TableModelAdapter;
import utils.Utils;

public class MainView extends JComponent{

    public MainView() {
        
        Collection<Class<? extends DomainObject>> domainObjects =
                MapperRegistry.current().getAllDomainObjectClasses();
        setLayout(new GridLayout(domainObjects.size()+5,1));
        IDataMapper mapper;
        add(getLabel(".: ISEL - SV0910 :."));
        add(getLabel(".: Trabalho Final de MPD (#26657, #31768 e #31923) :."));
        add(getLabel(""));
        for(Class klass : domainObjects) {
            mapper = MapperRegistry.current().get(klass);
            add(getDomainObjectSelector(klass.getSimpleName(), klass, mapper));
        }
        add(getLabel(""));
        add(getLabel(".: DEETC - Departamento de Engenharia Informática e de Computadores :."));
    }

    private JButton getDomainObjectSelector(String title, final Class<?> klass, final IDataMapper mapper) {
        
        JButton btn = new JButton(title);
        btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                TableModelAdapter tModelAdapter = new TableModelAdapter();
                mapper.loadAllInto(tModelAdapter);
                GridViewModel gModelAdapter =
                        new GridViewModelAdapter(tModelAdapter);
                GridViewEditor gView = new GridViewEditor(gModelAdapter,klass);
                Utils.launchDialog(gView, true, klass.getSimpleName());
            }
        });
        return btn;
    }

    private JLabel getLabel(String title) {
        JLabel lbl = new JLabel(title,JLabel.CENTER);
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        lbl.setOpaque(true);
        lbl.setBackground(Color.DARK_GRAY);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        return lbl;
    }

}
