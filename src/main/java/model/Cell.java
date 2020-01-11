package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Random;

@Data
@AllArgsConstructor
@ToString
public class Cell {
    private int x;
    private int y;

    public static Cell giveRandomCell(int width){
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * width);
        return new Cell(x, y);
    }
}
