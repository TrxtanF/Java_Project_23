package application.javafx2.Interfaces;

import javafx.scene.control.TableCell;

@FunctionalInterface
public interface CellFactoryCreator<S, T> {
    TableCell<S, T> createCell(String buttonName);
}