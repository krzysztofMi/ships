package model;

import lombok.Builder;
import lombok.Data;
import model.enumerators.Orientation;

import java.util.LinkedList;
import java.util.List;

@Data
public class EnemyShips {
    private List<Ship> ships;
    private final int FOUR_FLAG_SHIP_NUMBER = 1;
    private final int THREE_FLAG_SHIP_NUMBER = 2;
    private final int TWO_FLAG_SHIP_NUMBER = 3;
    private final int ONE_FLAG_SHIP_NUMBER = 4;


    public EnemyShips(){
        ships = new LinkedList<>();
        setShips();
    }

    public void setShips(){
        for(int i = 0; i<ONE_FLAG_SHIP_NUMBER; i++){
            addShip(1);
        }
        for(int i = 0; i<TWO_FLAG_SHIP_NUMBER; i++){
            addShip(2);
        }
        for(int i = 0; i<THREE_FLAG_SHIP_NUMBER; i++){
            addShip(3);
        }
        for(int i = 0; i<FOUR_FLAG_SHIP_NUMBER; i++){
            addShip(4);
        }
    }

    private void addShip(int length){
        Cell cell;
        Orientation orientation;
        int i = 0;
        while (true) {
            cell = Cell.giveRandomCell(Board.width-1);
            orientation = Orientation.getRandomOrientation();
            int limit = Board.width - length;
            if (orientation == Orientation.VERTICAL && cell.getY() < limit &&
                    !checkIfCollision(cell, length, orientation)) break;
            if (orientation == Orientation.HORIZONTAL && cell.getX() < limit &&
                    !checkIfCollision(cell, length, orientation)) break;
            if(i++==1000){
                replaceShip();
                return;
            }
        }
        Ship ship = Ship.builder().orientation(orientation).length(length).build();
        ship.addLocation(cell);
        ships.add(ship);
    }

    public boolean checkIfCollision(Cell location, int length, Orientation orientation){
        Ship tmpShip = Ship.builder().length(length).orientation(orientation).build();
        tmpShip.addLocation(location);
        for(Ship ship : ships){
            for(Cell tmpLocation : tmpShip.getShipLocation()){
                if(ship.checkIfCollision(tmpLocation)) return true;
            }
        }
        return false;
    }

    public void replaceShip(){
        ships.clear();
        setShips();
    }
}
