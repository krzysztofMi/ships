package tests;



import model.Cell;
import model.Ship;
import model.enumerators.Orientation;
import org.junit.jupiter.api.Test;


import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ShipTest {


    @Test
    public void addShipVerticalTest(){
        Ship ship = Ship.builder().length(4).orientation(Orientation.VERTICAL).build();
        ship.addLocation(new Cell(3,3));
        int i = 3;
        for(Cell location : ship.getShipLocation()){
            assertTrue(location.getX() == 3);
            assertTrue(location.getY() == i++);
        }
        assertTrue(7 - i == 4); //Ship is length of 4
    }

    @Test
    public void addShipHorizontalTest(){
        Ship ship = Ship.builder().length(4).orientation(Orientation.HORIZONTAL).build();
        ship.addLocation(new Cell(3,3));
        int i = 3;
        for(Cell location : ship.getShipLocation()){
            assertTrue(location.getX() == i++);
            assertTrue(location.getY() == 3);
        }
        assertTrue(7 - i == 4); //Ship is length of 4
    }


    @Test
    public void checkIfCollisionTest(){
        Ship ship = Ship.builder().length(4).orientation(Orientation.VERTICAL).build();
        ship.addLocation(new Cell(5, 5));
        for(int i = 4; i<7; i++){
            for(int j = 4; j<10; j++){
                assertTrue(ship.checkIfCollision(new Cell(i, j)),
                        "One of field is not in sight but should be (vertical)!");
            }
        }

        for(int i = 0; i<100; i++){
            int x = ThreadLocalRandom.current().nextInt(0, 11);
            int y = ThreadLocalRandom.current().nextInt(0, 11);
            if(x >= 4 && x <= 6 && y >= 4 && y <= 9 )
                assertTrue(ship.checkIfCollision(new Cell(x, y)), "$x, $y is not in sight!");
            else{
                assertFalse(ship.checkIfCollision(new Cell(x, y)), "$x, y is in sight!");
            }
        }

        ship = Ship.builder().length(4).orientation(Orientation.HORIZONTAL).build();
        ship.addLocation(new Cell(5, 5));
        for(int i = 4; i<6; i++){
            for(int j = 4; j<9; j++){
                assertTrue(ship.checkIfCollision(new Cell(j, i)),
                        "One of field is not in sight but should be (vertical)!");
            }
        }

        for(int i = 0; i<100; i++){
            int x = ThreadLocalRandom.current().nextInt(0, 10);
            int y = ThreadLocalRandom.current().nextInt(0, 10);
            if(x>=4 && x<=9 && y>=4 && y<=6 )
                assertTrue(ship.checkIfCollision(new Cell(x, y)), "$x, $y is not in sight!");
            else{
                assertFalse(ship.checkIfCollision(new Cell(x, y)), "$x, $y is in sight!");
            }
        }
    }
}
