package com.eumll.animeshutdownguifix;

import javafx.application.Application;
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

        Button button = new Button("Start");
        button.setId("start");
        button.setPrefWidth(60);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
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
                };
                Timer timer = new Timer("Timer");

                String textChas = chas.getText();
                int digChas = Integer.parseInt(textChas);
                String textMin = minuts.getText();
                int digMin = Integer.parseInt(textMin);
                String textSec = seconds.getText();
                int digSec = Integer.parseInt(textSec);

                int Chas = 0;
                int Min = 0;
                int Sec = 0;

                if(digChas != 0){
                    Chas = digChas * 3600000;
                }
                else if(digMin != 0){
                    Min = digMin * 60000;
                }
                else if(digSec != 0){
                    Sec = digSec * 1000;
                }

                int sum = Chas + Min + Sec;
                timer.schedule(task, sum);
            }
        });


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
        grid.add(button, 1, 2);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
