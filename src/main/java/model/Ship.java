package model;

import lombok.Builder;
import lombok.Data;
import model.enumerators.Orientation;

import java.util.*;

@Builder
@Data
public class Ship {
    private int length;
    private Orientation orientation;

    @Builder.Default
    private List<Cell> shipLocation = new ArrayList<>();

    public void addLocation(Cell cell){
        if(orientation == Orientation.VERTICAL) {
            int y = cell.getY();
            for (int i = 0; i<length; i++) {
                shipLocation.add(new Cell(cell.getX(), y));
                y++;
            }
        }else{
            int x = cell.getX();
            for(int i = 0; i<length; i++){
                shipLocation.add(new Cell(x, cell.getY()));
                x++;
            }
        }
    }

    public boolean checkIfCollision(Cell cell) {
        Cell shipProwLocation = shipLocation.get(0);
        Cell shipSternLocation = shipLocation.get(shipLocation.size() - 1);
        switch (orientation) {
            case VERTICAL:
                return checkIfVerticalCollision(cell, shipProwLocation, shipSternLocation);
            case HORIZONTAL:
                return checkIfHorizontalCollision(cell, shipProwLocation, shipSternLocation);
        }
        return true;
    }


    private boolean checkIfVerticalCollision(Cell cell, Cell shipProwLocation, Cell shipSternLocation){
        return (cell.getX() >= shipSternLocation.getX() - 1 &&
                cell.getX() <= shipSternLocation.getX() + 1 &&
                cell.getY() >= shipProwLocation.getY() - 1 &&
                cell.getY() <= shipSternLocation.getY() + 1);
    }

    private boolean checkIfHorizontalCollision(Cell cell, Cell shipProwLocation, Cell shipSternLocation){
        return (cell.getY() >= shipProwLocation.getY() - 1 &&
                cell.getY() <= shipProwLocation.getY() + 1 &&
                cell.getX() <= shipSternLocation.getX() + 1 &&
                cell.getX() >= shipProwLocation.getX() - 1);
    }

}
