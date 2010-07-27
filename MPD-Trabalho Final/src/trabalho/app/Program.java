package trabalho.app;

import trabalho.app.mainView.MainView;
import trabalho.jdbc.JdbcConnector;
import trabalho.jdbc.JdbcDataSource;
import trabalho.unitOfWork.UnitOfWork;
import utils.Utils;

public class Program {

    public static void main(String[] args) {

        UnitOfWork uow = new UnitOfWork(new JdbcConnector(JdbcDataSource.getDataSource()));
        UnitOfWork.setCurrent(uow);
        Utils.launchFrame(new MainView(),".: MPD Trabalho 3 :.");
        
    }
}