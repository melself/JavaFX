package com.eumll.animeshutdownguifix;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Anime Shutdown GUI");
        stage.getIcons().add(new Image("https://cdn-icons-png.flaticon.com/512/949/949551.png"));
        Text cc = new Text("ЧЧ");
        cc.setId("CC");
        Text mm = new Text("ММ");
        mm.setId("MM");
        Text ss = new Text("СС");
        ss.setId("SS");

        Text timeText = new Text();

        TextField chas = new TextField();
        TextField minuts = new TextField();
        TextField seconds = new TextField();

        chas.setText("0");
        minuts.setText("0");
        seconds.setText("0");

        chas.setPromptText("Часы");
        minuts.setPromptText("Минуты");
        seconds.setPromptText("Секунды");

        chas.setPrefWidth(80);
        minuts.setPrefWidth(80);
        seconds.setPrefWidth(80);

        timeText.setText("Жду приказа :3");
        timeText.setId("timeTEXT");
        Button button = new Button("Start");
        button.setId("start");
        button.setPrefWidth(60);

        Button button1 = new Button("Stop");
        button1.setId("start");
        button1.setPrefWidth(60);

        Timer timer = new Timer("Timer");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                button.setDisable(true);
                String textChas = chas.getText();
                int digChas = Integer.parseInt(textChas);
                String textMin = minuts.getText();
                int digMin = Integer.parseInt(textMin);
                String textSec = seconds.getText();
                int digSec = Integer.parseInt(textSec);

                TimerTask timerTask = new TimerTask() {
                    int sumSec = (digChas * 3600) + (digMin * 60) + digSec;
                    @Override
                    public void run() {
                        sumSec = sumSec - 1;
                        timeText.setText(sumSec + " секунд");
                        if(sumSec == 0){
                            OsCom();
                        }
                    }
                };
                timer.scheduleAtFixedRate(timerTask, 0, 1000 );
            }
        });

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                button.setDisable(false);
                timer.cancel();
                stage.close();
                Platform.runLater( () -> new main().start( new Stage() ) );
            }
        });


        Text n1 = new Text("");
        Text n12 = new Text("");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        Scene scene = new Scene(grid,600,400);
        scene.getStylesheets().add(getClass().getResource("mycss.css").toExternalForm());

        grid.add(cc, 0, 0);
        grid.add(mm, 1, 0);
        grid.add(ss, 2, 0);
        grid.add(chas, 0,1);
        grid.add(minuts, 1, 1);
        grid.add(seconds, 2, 1);
        grid.add(button, 3, 1);
        grid.add(button1,3,2);
        grid.add(timeText,1, 2);
        stage.setScene(scene);
        stage.show();
    }

    public void OsCom(){
        String shitDownComm = null;
        String OSN = System.getProperty("os.name");

        if (OSN.startsWith("Win")) {
            shitDownComm = "shutdown.exe -s -t 0";
        }
        else if (OSN.startsWith("Linux") || OSN.startsWith("Mac")) {
            shitDownComm= "shutdown -h now";
        }
        else {
            System.err.println("Shutdown unsupported operating system ...");
        }

        try {
            Runtime.getRuntime().exec(shitDownComm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    /*public void TimerGUI(){
        int tSumSec = (digChas * 3600) + (digMin * 60) + digSec;
        TimerTask countTimer = new TimerTask() {
            @Override
            public void run() {
                for(int i = tSumSec; i != 0; i--) {
                    long hour = i / 3600, min = i / 60 % 60, sec = i / 1 % 60;
                    String time = String.format("%02d:%02d:%02d", hour, min, sec);
                    timeText.setText(time);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Timer timerCountTimer = new Timer("Timer Counter");
        timerCountTimer.schedule(countTimer, tSumSec);
    }
*/
    public static void main(String[] args) {
        launch(args);
    }
}
