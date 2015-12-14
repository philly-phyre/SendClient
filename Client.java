package org.tempestdesign.sendclient;

//import javax.mail.*;
//import javax.mail.internet.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//import javafx.stage.WindowEvent;

public class Client extends Application  /*implements EventHandler<ActionEvent>*/ {
	
	Button btnLOG, btnSEND;
	Label lblUN, lblPW, lblTO, lblSUB, lblTEXT, lblHEAD, lblHOST;
	Scene log, edit, sent, fail;
	TextField tUN, tPW, tto, tsub, ttext, thead;
	ComboBox<String> cmbHOST;
	public static String UN, PW, host, port, mto, msub, cTEXT, cTYPE;
	
	public static void main(String args[]) {
		launch(args);
	}

	//@SuppressWarnings("unused")
	@Override
	public void start(Stage win) throws Exception {
		
		win.setOnCloseRequest(e -> Platform.exit());
		win.setTitle("SendMail \u0020\u0020 | | \u0020\u0020 TDS");
		//
		GridPane layLOG = new GridPane();
		layLOG.setAlignment(Pos.CENTER);
		layLOG.setHgap(30);
		layLOG.setVgap(15);
		layLOG.setPadding(new Insets(20, 20, 20, 20));
		//
		ObservableList<String> hostOps = 
				FXCollections.observableArrayList(
					"smtp.gmail.com",
					"smtp.mail.yahoo.com"
				);
		cmbHOST = new ComboBox<String>(hostOps);
		cmbHOST.setValue(" ");
		layLOG.add(cmbHOST, 1, 0);
		//
		lblHOST = new Label("SMTP Server: ");
		layLOG.add(lblHOST, 0, 0);
		lblUN = new Label("Username/email: ");
		layLOG.add(lblUN, 0, 1);
		lblPW = new Label("Password: ");
		layLOG.add(lblPW, 0, 2);
		//
		tUN = new TextField();
		tPW = new TextField();
		layLOG.add(tUN, 1, 1);
		layLOG.add(tPW, 1, 2);
		Text tfail = new Text("Cannot authenticate");
		//
		btnLOG = new Button();
		btnLOG.setText("Verify");
		btnLOG.setOnAction(e -> {
			UN = tUN.getText();
			PW = tPW.getText();
			host = cmbHOST.getValue();
			if (host.equals("smtp.gmail.com"))
				port = "587";
			else if (host.equals("smtp.mail.yahoo.com"))
				port = "465";
			boolean auth = Send.auth(UN, PW);
			if(!auth) {
				System.out.print("Not auth");
				layLOG.add(tfail, 3, 2);
			} else if (auth) {
			System.out.print("Auth");
			win.setScene(edit);
			}
		});
		layLOG.add(btnLOG, 1, 3);
//	##	//	##	//
	//	##	//	##	//
		//	##	//	##	//
		btnSEND = new Button();
		btnSEND.setText("SendMail!");
		btnSEND.setOnAction(e -> Send.send());
		
		
		
		
		
		
		
		//lay.getChildren().addAll(lblUN, txtUN, lblPW, txtPW, hostS, btnLOG);
		
		log = new Scene(layLOG, 420, 210);
		win.setX(150);
		win.setY(200);
		win.setScene(log);
		win.show();
		
	}
	
	
	
	
	
	
	
	
	
	
}


















