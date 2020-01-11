package tests;

import lombok.val;
import model.Board;
import model.Cell;
import model.EnemyShips;
import model.Ship;
import model.enumerators.Orientation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyShipLogicTest {

    @Test
    public void randomCoordTest(){
        int range = 10;
        for(int i = 0; i<100; i++) {
            Cell random = Cell.giveRandomCell(range);
            assertTrue(random.getX() < range);
            assertTrue(random.getX() >= 0);
            assertTrue(random.getY() < range);
            assertTrue(random.getY() >= 0);
        }
    }

    @Test
    public void randomOrientationTest(){
        int veriticalQuantity = 0;
        int horizontalQuantity = 0;
        for(int i = 0; i<100; i++){
            Orientation randomOrientation = Orientation.getRandomOrientation();
            if(randomOrientation == Orientation.HORIZONTAL){
                horizontalQuantity++;
            }else{
                veriticalQuantity++;
            }
        }
        assertTrue(veriticalQuantity>0 && horizontalQuantity>0, "Not a random!");
    }

    @Test
    public void addShipTest(){
        for(int i = 0; i<20; i++) {
            EnemyShips enemy = new EnemyShips();
            assertFalse(enemy.getShips().isEmpty(), "Ships not added!");
            for(int j = 1; j<5 ;j++){
                int length = 5 - j;
                List<Ship> ships = enemy.getShips().stream().filter(ship -> ship.getLength() == length).collect(Collectors.toList());
                assertEquals(j, ships.size(), "Ship with lenght $lenght should be $j");
            }
        }
    }

    @Test
    public void shipsBoardBoundTest(){
        for(int i = 1; i<20; i++) {
            EnemyShips enemy = new EnemyShips();
            for (Ship ship : enemy.getShips()) {
                Cell coord = ship.getShipLocation().get(0);
                if(ship.getOrientation() == Orientation.VERTICAL) {
                    assertTrue( coord.getX() < Board.width - ship.getLength(),
                            "${ship.orientation} ship beyond board");
                    assertTrue(coord.getY() < Board.width,
                            "${ship.orientation} ship beyond board");
                }else{
                    assertTrue(coord.getY() < Board.width - ship.getLength(),
                            "${ship.orientation} ship beyond board");
                    assertTrue(coord.getX() < Board.width,
                            "${ship.orientation} ship beyond board");
                }
            }
        }
    }
}
