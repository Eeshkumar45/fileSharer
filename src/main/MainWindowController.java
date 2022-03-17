package main;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainWindowController {
    @FXML private Pane titlePane;
    @FXML private ImageView btnMinimize, btnClose;
    @FXML private Label lblResult;

    private double x, y;
    private double num1 = 0;
    private String operator = "+";

    public void init(Stage stage) {
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });

        btnClose.setOnMouseClicked(mouseEvent -> stage.close());
        btnMinimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));
    }

    BackgroundFill background_fill = new BackgroundFill(Color.PINK,
            CornerRadii.EMPTY, Insets.EMPTY);
    Background background = new Background(background_fill);
    Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));


    @FXML private Pane sendBtn;
    @FXML private Pane reciveBtn;
    @FXML private Pane mainFrame;
    @FXML private Pane senderPane;
    @FXML private Pane reciverPane;
    @FXML private Pane commonPane;




    public void sendBtnClicked(MouseEvent mouseEvent) {
        System.out.println("send Button Clicked");
        mainFrame.setVisible(false);
        senderPane.setVisible(true);
        commonPane.setVisible(true);

    }

    public void reciveBtnClicked(MouseEvent mouseEvent) {
        System.out.println("Recive Button Clicked");
        mainFrame.setVisible(false);
        reciverPane.setVisible(true);
        commonPane.setVisible(true);
    }

    public void mouseOntoReciveBtn(MouseEvent mouseEvent) {
        //reciveBtn.setBackground(background);
        //reciveBtn.setBorder(border);
        reciveBtn.setStyle("-fx-background-color: black");
    }

    public void mouseOutofReciveBtn(MouseEvent mouseEvent) {
        reciveBtn.setStyle("opacity:1");
    }

    public void mouseOntoSendBtn(MouseEvent mouseEvent) {
        sendBtn.setStyle("-fx-background-color: black");
    }

    public void mouseOutofSendBtn(MouseEvent mouseEvent) {
        sendBtn.setStyle("opacity:1");
    }

    public void filechooserBtnClicked(MouseEvent mouseEvent) {
    }

    public void goBackBtnClicked(MouseEvent mouseEvent) {
        System.out.println("go back");
        mainFrame.setVisible(true);
        senderPane.setVisible(false);
        reciverPane.setVisible(false);
        commonPane.setVisible(false);

    }
}
