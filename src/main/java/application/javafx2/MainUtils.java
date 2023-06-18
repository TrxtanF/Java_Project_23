package application.javafx2;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.entity.Course;

public class MainUtils {

    public static <S, T> void createButtonColumn(String[] buttonNames, TableView<S> tableView, CellFactoryCreator<S, T> cellFactoryCreator) {
        TableColumn<S, T>[] buttonColumns = new TableColumn[buttonNames.length];

        for (int i = 0; i < buttonColumns.length; i++) {
            final int index = i;
            buttonColumns[i] = new TableColumn<>(buttonNames[i]);
            buttonColumns[i].setCellFactory(param -> cellFactoryCreator.createCell(buttonNames[index]));
            tableView.getColumns().add(buttonColumns[i]);
        }
    }

    public static<T> void createColumn (String[] columnNames, TableView tableView, CellValueFactoryCreator<T> valueCreator ){
        TableColumn<T, String>[] columns = new TableColumn[columnNames.length];

        for (int i = 0; i < columns.length; i++) {
            final int index = i;
            columns[i] = new TableColumn<>(columnNames[i]);

            columns[i].setCellValueFactory(celldata -> valueCreator.getValue(celldata, columnNames[index]));
            tableView.getColumns().add(columns[i]);
        }

    }
}
