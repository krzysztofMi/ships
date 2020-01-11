package controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Data;
import model.Board;
import model.Cell;
import model.EnemyShips;
import model.SquareBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Data
public class BoardController {
    private Board boardModel;
    private List<Rectangle> rectangles;
    private EnemyShips enemy;
    private int miss;
    private final int RED_PART_NUMBER = 20;

    public BoardController(){
        boardModel = new SquareBoard();
        enemy = new EnemyShips();
        miss = 0;
        rectangles = new ArrayList<>();
        addEnemyShipsToBoard();
    }

    public void action(){
        addEnemyShipsToBoard();
        ListIterator iter = rectangles.listIterator();
        while (iter.hasNext()){
            Rectangle rec = (Rectangle) iter.next();
            rec.setOnMouseClicked(ev->{activate(rec);});
        }
    }

    public void cleanBoard(){
        for(Rectangle rec : rectangles) {
            rec.setFill(Color.WHITE);
        }
        boardModel.clean();

        enemy.replaceShip();
        addEnemyShipsToBoard();
    }

    private void addEnemyShipsToBoard(){
        enemy.getShips()
                .forEach(ship -> ship.getShipLocation()
                        .forEach(cell->{
                            boardModel.changeCellStateTo(true, cell.getX(), cell.getY());
                        }));
    }


    public void activate(Rectangle rec){
        Color color;
        try {
            if(rec.getFill() == Color.WHITE) miss++;
            Cell cell = changeCoordToCell(rec.getX(), rec.getY());
            color = boardModel.getCellState(cell) ? Color.RED : Color.GRAY;
            rec.setFill(color);
        }catch (Exception ex){
            return;
        }
    }

    private Cell changeCoordToCell(double x, double y){
        int xInt = (int) ((int) x/Board.cellWidth);
        int yInt = (int) ((int)y/Board.cellWidth);
        return new Cell(xInt, yInt);
    }

    public void showBoard(){
        for(int i = 0; i< Board.width; i++){
            for(int j = 0; j<Board.width; j++){
                Cell cell = boardModel.getCell(i, j);
                if(boardModel.getCellState(cell)){
                    rectangles.get(j*Board.width + i).setFill(Color.BLACK);
                }else{
                    rectangles.get(j*Board.width + i).setFill(Color.GRAY);
                }

            }
        }
    }

    public boolean checkIfEnd(){
        int counter = 0;
        for(Rectangle rec: rectangles){
            if(rec.getFill() == Color.RED) counter++;
        }
        return counter == RED_PART_NUMBER;
    }
}
