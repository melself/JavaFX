package com.eumll.shutdownpc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class mainController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String path;
    private static final String PATH = "C:/ShutdownPC/config/config.txt";

    @FXML
    protected void closeApp() {
        System.exit(1);
    }

    @FXML
    public void switchToSceneMain(ActionEvent event) throws Exception{
        String content = Files.lines(Paths.get(PATH)).reduce("", String::concat);

        Parent root = FXMLLoader.load(getClass().getResource("mainView" + content));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToSceneSettings(ActionEvent event) throws Exception{
        String content = Files.lines(Paths.get(PATH)).reduce("", String::concat);

        Parent root = FXMLLoader.load(getClass().getResource("settingsTheme" + content));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToSceneTimer(ActionEvent event) throws Exception{
        String content = Files.lines(Paths.get(PATH)).reduce("", String::concat);

        Parent root = FXMLLoader.load(getClass().getResource("TimerTheme" + content));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToSceneSetTime(ActionEvent event) throws Exception{
        String content = Files.lines(Paths.get(PATH)).reduce("", String::concat);

        Parent root = FXMLLoader.load(getClass().getResource("SetTimeTheme" + content));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private RadioButton dayBtn, nightBtn, blueBtn;
    public void changeTheme(ActionEvent event) throws IOException {
        if(dayBtn.isSelected()){
            try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(PATH)))){
                writer.println("Day.fxml");
            }
        catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        else if(nightBtn.isSelected()){
            try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(PATH)))){
                writer.println("Night.fxml");
            }
            catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        else if(blueBtn.isSelected()){
            try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(PATH)))){
                writer.println("Blue.fxml");
            }
        catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
    }

    @FXML
    private Button startBtn, stopBtn;
    @FXML
    private TextField chas, minuts, seconds;
    @FXML
    private Label timeText;

    public void timerShutdown(){
        Timer timer = new Timer("Timer");

        String textChas = chas.getText();
        int digChas = Integer.parseInt(textChas);
        String textMin = minuts.getText();
        int digMin = Integer.parseInt(textMin);
        String textSec = seconds.getText();
        int digSec = Integer.parseInt(textSec);

        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                startBtn.setDisable(true);

                TimerTask timerTask = new TimerTask() {
                    int sumSec = (digChas * 3600) + (digMin * 60) + digSec;
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            sumSec = sumSec - 1;
                            long hour = sumSec / 3600, min = sumSec / 60 % 60, sec = sumSec / 1 % 60;
                            String timeToString = String.format("%02d:%02d:%02d", hour, min, sec);;
                            timeText.setText(timeToString);
                            if(sumSec == 0){
                                timeText.setText("Выключаюсь");
                                String shitDownComm = null;
                                String OSN = System.getProperty("os.name");

                                if (OSN.startsWith("Win")) {
                                    shitDownComm = "shutdown.exe -s -t 0";
                                }
                                else if (OSN.startsWith("Linux") || OSN.startsWith("Mac")) {
                                    shitDownComm= "shutdown -h now";
                                }
                                else {
                                    timeText.setText("Error");
                                }

                                try {
                                    Runtime.getRuntime().exec(shitDownComm);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                System.exit(0);
                                timer.cancel();
                            }
                        });
                    }
                };
                timer.scheduleAtFixedRate(timerTask, 0, 1000);
            }
        });

        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startBtn.setDisable(false);
                timeText.setText("Ожидание");
                timer.cancel();
            }
        });
    }

    @FXML
    private TextField chas1, minuts1;
    @FXML
    private Button startBtn1, stopBtn1;
    @FXML
    private Label timeText1;

    public void setTimeShutdown(){
        String textChas = chas1.getText();
        String textMin = minuts1.getText();

        Timer timer = new Timer();

        startBtn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startBtn1.setDisable(true);
                timeText1.setText("В процессе");
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                        Date date = new Date();
                        String data1 = dateFormat.format(date);

                        String inputData = new String(textChas + ":" + textMin);

                        if(inputData.equals(data1)){
                            String shitDownComm = null;
                            String OSN = System.getProperty("os.name");

                            if (OSN.startsWith("Win")) {
                                shitDownComm = "shutdown.exe -s -t 0";
                            }
                            else if (OSN.startsWith("Linux") || OSN.startsWith("Mac")) {
                                shitDownComm= "shutdown -h now";
                            }
                            else {
                                timeText.setText("Error");
                            }

                            try {
                                Runtime.getRuntime().exec(shitDownComm);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.exit(0);

                            timer.cancel();
                        }
                    }
                };
                timer.scheduleAtFixedRate(timerTask, 0, 1000);
            }
        });

        stopBtn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startBtn1.setDisable(false);
                timeText1.setText("Запланируйте время");
                timer.cancel();
            }
        });
    }


}
