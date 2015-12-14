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
	
	static Button btnLOG, btnSEND;
	static Label lblUN, lblPW, lblTO, lblSUB, lblTEXT, lblHEAD, lblHOST;
	static Scene log, edit, sent;
	static Text UNfail;
	static TextField tUN, tto, tsub, ttext, thead;
	static PasswordField tPW;
	static ComboBox<String> cmbHOST;
	public static String UN, PW, host, port, mto, msub, cTEXT, cTYPE;
	static GridPane layLOG, laySENT, layEDIT;
	static Stage win;
	
	public static void main(String args[]) {
		launch(args);
	}

	//@SuppressWarnings("unused")
	@Override
	public void start(Stage primaryStage) throws Exception {
		win = primaryStage;
		
		win.setOnCloseRequest(e -> Platform.exit());
		win.setTitle("SendMail \u0020\u0020 | | \u0020\u0020 TDS");
		//
		layLOG = new GridPane();
		layLOG.setAlignment(Pos.CENTER);
		layLOG.setHgap(30);
		layLOG.setVgap(15);
		layLOG.setPadding(new Insets(20, 10, 20, 10));
		//
		layEDIT = new GridPane();
		layEDIT.setAlignment(Pos.CENTER_LEFT);
		layEDIT.setHgap(10);
		layEDIT.setVgap(25);
		layEDIT.setPadding(new Insets(10, 15, 20, 100));
		
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
		tPW = new PasswordField();
		layLOG.add(tUN, 1, 1);
		layLOG.add(tPW, 1, 2);
		UNfail = new Text("Cannot authenticate");
		//
		btnLOG = new Button();
		btnLOG.setText("Verify");
		btnLOG.setOnAction(e -> {
			UN = tUN.getText();
			PW = tPW.getText();
			host = cmbHOST.getValue();
			if (host.equals("smtp.gmail.com")) {
				port = "587";
			} else if (host.equals("smtp.mail.yahoo.com")) {
				port = "465";
			} else {
				port = "80";
			}
			if(layLOG.getChildren().contains(UNfail)) {
				System.out.print("y");
				layLOG.getChildren().remove(UNfail);
			}
			auth();
		});
		layLOG.add(btnLOG, 1, 3);
//	##	//	##	//
	//	##	//	##	//
		//	##	//	##	//
		btnSEND = new Button();
		btnSEND.setText("Send eMail");
		btnSEND.setOnAction(e -> Send.send());
		layEDIT.add(btnSEND, 1, 5);
		
		
		
		
		edit = new Scene(layEDIT, 480, 710);
		
		log = new Scene(layLOG, 480, 210);
		win.setX(150);
		win.setY(200);
		win.setScene(log);
		win.show();
		
	}
	
	public static void auth() {
		boolean auth = Send.auth(UN, PW);
		if(!auth) {
			System.out.print("Not auth");
			layLOG.add(UNfail, 3, 1);
			tUN.clear();
			tPW.clear();
		} else if (auth) {
			System.out.print("Auth");
			win.setScene(edit);
		} else {
			System.out.print("Not auth");
			layLOG.add(UNfail, 3, 1);
			cmbHOST.setValue(" ");
			tUN.clear();
			tPW.clear();
		}
	}
	
	
	
	
	
	
	
	
	
	
}


















