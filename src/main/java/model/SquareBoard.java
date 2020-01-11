package model;


import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class SquareBoard implements Board {
    @Builder.Default
    public Map<Cell, Boolean> cells = new HashMap<>();


    public SquareBoard(){
        createBoard();
    }

    @Override
    public void createBoard() {
        for(int i = 0; i<width; i++){
            for(int j = 0; j<width; j++)
                cells.put(new Cell(i, j), false);
        }
    }

    @Override
    public Cell getCell(int x, int y) {
        return cells.keySet().stream()
                .filter(cell-> cell.getX()==x && cell.getY() == y)
                .findFirst().get();
    }

    @Override
    public void clean() {
        cells.clear();
        createBoard();
    }

    @Override
    public boolean getCellState(int x, int y){
        return cells.get(getCell(x, y));
    }

    @Override
    public boolean getCellState(Cell cell){
        return getCellState(cell.getX(), cell.getY());
    }

    @Override
    public void changeCellStateTo(boolean state, int x, int y){
        cells.replace(getCell(x, y), state);
    }

    @Override
    public void changeCellStateTo(boolean state, Cell cell){
        changeCellStateTo(state, cell.getX(), cell.getY());
    }

    @Override
    public Set<Cell> getAllCell(){
        return cells.keySet();
    }
}
