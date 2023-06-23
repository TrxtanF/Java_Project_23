package application.javafx2.Interfaces;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import org.entity.Course;

@FunctionalInterface
public interface CellValueFactoryCreator<T> {
    ObservableValue<String> getValue(TableColumn.CellDataFeatures<T, String> celldata , String columnName);
}
