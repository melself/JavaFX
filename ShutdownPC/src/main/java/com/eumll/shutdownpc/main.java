package com.eumll.shutdownpc;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class main extends Application {
    private double xOffset;
    private double yOffset;
    private static final String PATH = "C:/ShutdownPC/config/config.txt";
    @Override
    public void start(Stage stage) throws IOException {

        File theDir = new File("C:/ShutdownPC/config");
        if (!theDir.exists()){
            theDir.mkdirs();
            try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(PATH)))){
                writer.println("Day.fxml");
            }
            catch (IOException e){
                System.err.println(e.getMessage());
            }
            String content = Files.lines(Paths.get(PATH)).reduce("", String::concat);

            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("mainView" + content));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = stage.getX() - event.getScreenX();
                    yOffset = stage.getY() - event.getScreenY();
                }
            });
            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() + xOffset);
                    stage.setY(event.getScreenY() + yOffset);
                }
            });
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Shutdown PC");
            stage.setScene(scene);
            stage.show();
        }
        else {
            String content = Files.lines(Paths.get(PATH)).reduce("", String::concat);
            System.out.println(content);

            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("mainView" + content));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = stage.getX() - event.getScreenX();
                    yOffset = stage.getY() - event.getScreenY();
                }
            });
            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() + xOffset);
                    stage.setY(event.getScreenY() + yOffset);
                }
            });
            stage.getIcons().add(new Image("https://cdn-icons-png.flaticon.com/512/352/352163.png"));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Shutdown PC");
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}