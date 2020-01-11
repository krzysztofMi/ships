package controller;

import javafx.application.Platform;

public class MenuController {

    BoardController boardController;

    public MenuController(BoardController boardController){
        this.boardController = boardController;
    }

    public void start(){
        boardController.cleanBoard();
        boardController.setMiss(0);
    }

    public void showAll(){
        boardController.showBoard();
    }

    public void exit(){
        Platform.exit();
    }
}
