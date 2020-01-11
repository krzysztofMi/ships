package tests;

import model.Board;
import model.Cell;
import model.SquareBoard;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardLogicTest {

    @Test
    public void checkingBoardContent(){
        SquareBoard board = new SquareBoard();
        for(int i = 0; i<10; i++){
            for(int j = 0; j<10; j++){
                assert(board.getCells().get(board.getCell(i, j)) == false);
            }
        }
    }

    @Test
    public void checkingCellNumber(){
        SquareBoard board = new SquareBoard();
        int i = 0;
        for(Cell cell: board.getCells().keySet()){
            i++;
        }
        assert(i == Board.width * Board.width);
    }

    @Test
    public void getCellTest(){
        Board board = new SquareBoard();
        Cell cell = board.getCell(0, 0);
        assert(cell.getX() == 0 && cell.getY() == 0);
        cell = board.getCell(5, 6);
        assert(cell.getX() == 5 && cell.getY() == 6);
        assertThrows(NoSuchElementException.class, ()->board.getCell(10, 10));
        assertThrows(NoSuchElementException.class, ()->board.getCell(5, 20));
        cell = board.getCell(7, 7);
        assert(cell.getX() == 7 && cell.getY() == 7);
        assert(!(cell.getX() == 3 && cell.getX() == 2));
    }

    @Test
    public void changeCellSateTest(){
        Board board = new SquareBoard();
        board.changeCellStateTo(true, 5, 5);
        assertTrue(board.getCellState(5, 5));
        board.changeCellStateTo(false, 5, 5);
        assertFalse(board.getCellState(5, 5 ));
    }

    @Test
    public void cleanTest(){
        Board board = new SquareBoard();

        board.changeCellStateTo(true, board.getCell(5, 5));
        board.clean();
        assertFalse(board.getCellState(5, 5));
        for(int i = 0; i<7; i++){
            for(int j = 0; j<7; j++){
                board.changeCellStateTo(true, i, j);
            }
        }
        board.clean();
        for(int i = 0; i<7; i++){
            for(int j = 0; j<7; j++){
                assertFalse(board.getCellState(i, j));
            }
        }
    }
}
