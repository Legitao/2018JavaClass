package assignment5;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

public class Main extends Application {
	private static String myPackage;
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
    
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane bpane = new BorderPane();
		GridPane gpane = new GridPane();		
		gpane.setStyle("-fx-border-color: green; -fx-border-width:2px");
		gpane.setPrefWidth(800);
		gpane.setPrefHeight(800);
		StackPane statsPane = new StackPane();
		statsPane.setPadding(new Insets(15, 15, 15, 30));
		statsPane.setAlignment(Pos.CENTER_LEFT);
		statsPane.setMinWidth(300);
		GridPane controlPane = new GridPane();
		setControlPanel(controlPane, gpane, statsPane);		
		bpane.setLeft(controlPane);
		bpane.setCenter(gpane);
		bpane.setRight(statsPane);
		Critter.displayWorld(gpane);
		
		
		Scene scene = new Scene(bpane);
		primaryStage.setTitle("Critter");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	void setControlPanel(GridPane controlPane, GridPane viewPane, StackPane statsPane) {
		controlPane.setStyle("-fx-background-color: gold");
	    controlPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		controlPane.setHgap(5.5);
		controlPane.setVgap(10);
		
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		ChoiceBox<String> stats_choiceBox = new ChoiceBox<>();

		
		stats_choiceBox.getItems().add("all");
		List<String> results = new ArrayList<String>();
		File[] files = new File("src/assignment5/").listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		results.remove("Critter.java");
		results.remove("Header.java");
		results.remove("Main.java");
		results.remove("Params.java");
		results.remove("InvalidCritterException.java");
		for(String s : results) {
			s = s.replace(".java", "");
			choiceBox.getItems().add(s);
			stats_choiceBox.getItems().add(s);
		}
		
		stats_choiceBox.setOnAction((ActionEvent e) -> {
			showStats(stats_choiceBox.getValue(), results, statsPane);
		});
		
		controlPane.add(new Label("Critter Name:"), 0, 0);
		controlPane.add(choiceBox, 1, 0);				
		controlPane.add(new Label("Number:"), 0, 1);
		TextField tfNum = new TextField();
		tfNum.setPrefColumnCount(4);
		controlPane.add(tfNum, 1, 1);
		Button btMake = new Button("make");
		controlPane.add(btMake, 0, 2);		
		btMake.setOnAction((ActionEvent e) -> {
			if (choiceBox.getValue() != null && tfNum.getText() != null && !tfNum.getText().isEmpty()) {
	            String name = choiceBox.getValue();
	            String num = tfNum.getText();
	            tfNum.clear();
	            for(int i = 0; i < Integer.valueOf(num); i++) {
	            	try {
						Critter.makeCritter(name);
					} catch (InvalidCritterException e1) {
						e1.printStackTrace();
					}
	            }
	            Critter.displayWorld(viewPane);
				showStats(stats_choiceBox.getValue(), results, statsPane);
	        } else {
	            System.out.println("You have not left a comment.");
	        }
		});
		controlPane.add(new Label("Set run steps:"), 0, 7);
		TextField tfRunTimes = new TextField();
		controlPane.add(tfRunTimes, 1, 7);
		Button btRun = new Button("run");
		controlPane.add(btRun, 0, 8);		
		btRun.setOnAction((ActionEvent e) -> {
			if (tfRunTimes.getText() != null && !tfRunTimes.getText().isEmpty()) {
	            String num = tfRunTimes.getText();
	            for(int i = 0; i < Integer.valueOf(num); i++) {
					Critter.worldTimeStep();;
	            }
	            showStats(stats_choiceBox.getValue(), results, statsPane);
				Critter.displayWorld(viewPane);
	        } else {
	            System.out.println("You have not left a comment.");
	        }
		});
		
		EventHandler<ActionEvent> eventHandler = e -> {
			Critter.worldTimeStep();
			Critter.displayWorld(viewPane);
			showStats(stats_choiceBox.getValue(), results, statsPane);
		};
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), eventHandler));
		animation.setCycleCount(Timeline.INDEFINITE);
		Button btAnimate = new Button("start animate");
		controlPane.add(btAnimate, 0, 13);
		btAnimate.setOnAction((ActionEvent e) -> {
			btMake.setDisable(true);
			btRun.setDisable(true);
			animation.play();
		});
		
		Button btStop = new Button("pause animate");
		controlPane.add(btStop, 0, 14);
		btStop.setOnAction((ActionEvent e) -> {
			btMake.setDisable(false);
			btRun.setDisable(false);
			animation.stop();
		});
		
		Button btQuit = new Button("quit");
		controlPane.add(btQuit, 0, 17);
		btQuit.setOnAction((ActionEvent e) -> {
			System.exit(0);
		});
		
		
		controlPane.add(new Label("stats Critter Name:"), 0, 20);
		controlPane.add(stats_choiceBox, 1, 20);
		
		controlPane.add(new Separator(), 0, 5);
		controlPane.add(new Separator(), 1, 5);
		controlPane.add(new Separator(), 0, 11);
		controlPane.add(new Separator(), 1, 11);
		controlPane.add(new Separator(), 0, 18);
		controlPane.add(new Separator(), 1, 18);
		
	}
	
	private void showStats(String name, List<String> results, StackPane statsPane) {
		if(name == null) {
			return;
		}
		Label stats = new Label();
	    stats.setWrapText(true);
	    stats.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 15; -fx-text-fill: darkred;");
		try {
			String runStatsResult = "";
			if(name.equals("all")) {
				for(String s : results) {
					s = s.replace(".java", "");
					java.util.List<Critter> critter_list = Critter.getInstances(s);
					Class <?> clazz = Class.forName(myPackage + "." + s);
					Critter tmp_critter = (Critter) clazz.newInstance();
					Method mm = clazz.getMethod("runStats", List.class); 
					runStatsResult += s + " stats: \n" + mm.invoke(tmp_critter, critter_list) + "\n";
				}
				stats.setText(runStatsResult);
			} else {
				java.util.List<Critter> critter_list = Critter.getInstances(name);
				Class <?> clazz = Class.forName(myPackage + "." + name);
				Critter tmp_critter = (Critter) clazz.newInstance();
				Method mm = clazz.getMethod("runStats", List.class);  
				runStatsResult += mm.invoke(tmp_critter, critter_list);
				stats.setText(name + " stats: \n" + runStatsResult + "\n");
			}
			statsPane.getChildren().clear();
		    statsPane.getChildren().add(stats);
		} catch (InvalidCritterException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
