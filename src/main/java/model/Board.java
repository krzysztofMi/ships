package model;

import java.util.List;
import java.util.Set;

public interface Board {
    public static final int width = 10;
    public static final double cellWidth = 50.0;

    void clean();
    void createBoard();
    Cell getCell(int x, int y);
    boolean getCellState(int x, int y);
    boolean getCellState(Cell cell);
    void changeCellStateTo(boolean state, int x, int y);
    void changeCellStateTo(boolean state, Cell cell);
    Set<Cell> getAllCell();
}
