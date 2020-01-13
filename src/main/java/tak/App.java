package tak;

import controller.BoardController;
import controller.MenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Board;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.function.Function;

/**
 * JavaFX App
 */
public class App extends Application {

    private BoardController boardController;
    private MenuController menuController;
    private int recPosX = 0;
    private int recPosY = 0;

    private boolean firstKeyboardUse = true;
    @Override
    public void start(Stage stage){
        initializeController();
        HBox root = new HBox();
        List<Rectangle> rectangles = setBoardContent();
        addAlert();
        root.setFocusTraversable(true);
        root.setOnKeyPressed(e->{
            if(firstKeyboardUse){
                changeRectangleStroke(rectangles, recPosX, recPosY , Color.BLUE);
                firstKeyboardUse = false;
                return;
            }
            switch (e.getCode()){
                case TAB:
                    menuController.start();
                    break;
                case ESCAPE:
                    menuController.exit();
                    break;
                case RIGHT:
                    changeRectangleStroke(rectangles, recPosX, recPosY, Color.BLACK);
                    if(recPosX < 450) recPosX += 50;
                    changeRectangleStroke(rectangles, recPosX, recPosY, Color.BLUE);
                    break;
                case LEFT:
                    changeRectangleStroke(rectangles, recPosX, recPosY, Color.BLACK);
                    if(recPosX > 0) recPosX -= 50;
                    changeRectangleStroke(rectangles, recPosX, recPosY, Color.BLUE);
                    break;
                case UP:
                    changeRectangleStroke(rectangles, recPosX, recPosY, Color.BLACK);
                    if(recPosY > 0) recPosY -= 50;
                    changeRectangleStroke(rectangles, recPosX, recPosY, Color.BLUE);
                    break;
                case DOWN:
                    changeRectangleStroke(rectangles, recPosX, recPosY, Color.BLACK);
                    if(recPosY < 450) recPosY += 50;
                    changeRectangleStroke(rectangles, recPosX, recPosY, Color.BLUE);
                    break;
                case ENTER:
                    for(int i = 0; i<rectangles.size(); i++) {
                        Rectangle rectangle = rectangles.get(i);
                        if((int) rectangle.getX() == recPosX && (int) rectangle.getY() == recPosY ) {
                            boardController.activate(rectangle);
                        }
                    }
            }
        });
        root.setOnMouseMoved(e->{
            changeRectangleStroke(rectangles, recPosX, recPosY, Color.BLACK);
            firstKeyboardUse = true;
        });
        Pane leftPane = new Pane();
        leftPane.getChildren().addAll(rectangles);
        root.getChildren().add(leftPane);
        root.getChildren().add(setMenuContent());
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(root.getOnKeyPressed());
        stage.setTitle("Ships");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


    private void changeRectangleStroke(List<Rectangle> rectangles, int x, int y, Color color){
        for(int i = 0; i<rectangles.size(); i++) {
            Rectangle rec = rectangles.get(i);
            if ((int) rec.getX() == x && (int) rec.getY() == y) {
                if (color == Color.BLACK) {
                    rec.setStrokeWidth(1.0);
                    rec.setStrokeType(StrokeType.CENTERED);
                    rec.setStroke(color);
                } else {
                    rec.setStrokeWidth(2.0);
                    rec.setStrokeType(StrokeType.INSIDE);
                    rec.setStroke(color);
                }
            }
        }
    }

    private void initializeController(){
        boardController = new BoardController();
        menuController = new MenuController(boardController);
    }

    private List<Rectangle> setBoardContent(){
        Pane leftPane = new Pane();
        boardController.getBoardModel().getAllCell().forEach(cell -> {
            Rectangle rectangle = new Rectangle();
            rectangle.setX(cell.getX() * Board.cellWidth);
            rectangle.setY(cell.getY() * Board.cellWidth);
            rectangle.setHeight(Board.cellWidth);
            rectangle.setWidth(Board.cellWidth);
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Color.BLACK);
            boardController.getRectangles().add(rectangle);
        });
        boardController.action();
        return boardController.getRectangles();
    }

    private VBox setMenuContent(){
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(20, 0, 0, 0));
        vBox.setMinWidth(100.0);
        vBox.setStyle("-fx-background-color: #9016CE");
        Button start = prepareButton("start", e->menuController.start());
        start.setFocusTraversable(false);
        start.setStyle("-fx-font-size: 25px; -fx-min-width: 80px; -fx-background-color: white");
        Button exit = prepareButton("exit", e->menuController.exit());
        exit.setFocusTraversable(false);
        exit.setStyle("-fx-font-size: 25px; -fx-min-width: 80px; -fx-background-color: white");
        exit.setMinWidth(30);
        vBox.getChildren().addAll(start, exit);
        return vBox;
    }
    private Timeline timer;
    private void addAlert(){
        timer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(boardController.checkIfEnd()){
                    alert();
                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

    }

    private Stage alertStage;

    private void alert(){
        alertStage = new Stage();
        alertStage.initStyle(StageStyle.UNDECORATED);
        Pane root = new Pane();
        alertStage.setTitle("END");
        Button start = prepareButton("Start", e->{
            menuController.start();
            alertStage.close();
            timer.play();
        });
        start.setFocusTraversable(false);
        Button exit = prepareButton("Exit", e->{
            menuController.exit();
            alertStage.close();
            timer.play();
        });
        exit.setFocusTraversable(false);
        Text text = new Text("Koniec gry!");
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 60px");
        Text text1 = new Text("Liczba strzałów: " + boardController.getMiss());
        text1.setFill(Color.WHITE);
        text1.setStyle("-fx-font-size: 30px");
        setLayoutPosition(text, 0, 50);
        setLayoutPosition(text1, 25, 100);
        setLayoutPosition(start, 50, 125);
        setLayoutPosition(exit, 200, 125);
        start.setStyle("-fx-text-fill : white; -fx-background-color: #A16F6F; -fx-font-size: 20px");
        exit.setStyle("-fx-text-fill : white; -fx-background-color: #A16F6F; -fx-font-size: 20px");
        root.setStyle("-fx-background-color: #000000");
        root.setOnKeyPressed(e->{
            System.out.println(e.getCode());
            switch (e.getCode()){
                case ENTER:
                    if(exit.isDefaultButton()){
                        exit.fire();
                    }
                    if(start.isDefaultButton()){
                        start.fire();
                    }
                    break;
                case TAB:
                    if(exit.isDefaultButton()){
                        start.setDefaultButton(true);
                        exit.setDefaultButton(false);
                    }else{
                        exit.setDefaultButton(true);
                        start.setDefaultButton(false);
                    }
                    break;
                case LEFT:
                    start.setDefaultButton(true);
                    exit.setDefaultButton(false);
                    break;
                case RIGHT:
                    exit.setDefaultButton(true);
                    start.setDefaultButton(false);
            }
            if(exit.isDefaultButton()){
                exit.setStyle("-fx-text-fill : white; -fx-background-color: #A16F6F; -fx-font-size: 20px; -fx-border-color: blue");
                start.setStyle("-fx-text-fill : white; -fx-background-color: #A16F6F; -fx-font-size: 20px; -fx-border-color: black");
            }else{
                exit.setStyle("-fx-text-fill : white; -fx-background-color: #A16F6F; -fx-font-size: 20px; -fx-border-color: black");
                start.setStyle("-fx-text-fill : white; -fx-background-color: #A16F6F; -fx-font-size: 20px; -fx-border-color: blue");
            }
        });
        root.getChildren().addAll(text, text1, start, exit);
        Scene scene = new Scene(root, 300, 200);
        scene.setOnKeyPressed(root.getOnKeyPressed());
        alertStage.setScene(scene);
        timer.stop();
        alertStage.show();
    }

    private Button prepareButton(String name, EventHandler event){
        Button button = new Button(name);
        button.setOnAction(event);
        return button;
    }

    private void setLayoutPosition(Node node, double x, double y){
        node.setLayoutX(x);
        node.setLayoutY(y);
    }

    public static void main(String[] args) {
        launch();
    }

}