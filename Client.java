package org.tempestdesign.sendclient;

//import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
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
	
	static Session sesh;
	static Properties prop = new Properties();
	
	static Button btnLOG, btnSEND;
	static Label lblUN, lblPW, lblTO, lblSUB, lblTEXT, lblHEAD, lblHOST;
	static Scene log, edit, sent;
	static Text UNfail;
	static TextField tUN, tto, tsub, thead;
	static TextArea ttext;
	static PasswordField tPW;
	static ComboBox<String> cmbHOST, cmbTYPE;
	public static String UN, PW, host, port, mto, mhead, msub, cTEXT, cTYPE;
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
		layEDIT.setPadding(new Insets(10, 15, 20, 30));
		
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
		//
		ObservableList<String> typeOps = FXCollections.observableArrayList(
				"text/plain",
				"text/html"
				);
		cmbTYPE = new ComboBox<String>(typeOps);
		lblTO = new Label("To: ");
		lblHEAD = new Label("Heading: ");
		lblSUB = new Label("Message subject: ");
		lblTEXT = new Label("Body");
		tto = new TextField();
		thead = new TextField();
		tsub = new TextField();
		ttext = new TextArea();
		tto.setPrefWidth(250);
		thead.setPrefWidth(250);
		tsub.setPrefWidth(250);
		ttext.setPrefSize(380, 510);
		ttext.setWrapText(true);
		layEDIT.add(lblTO, 0, 1);
		layEDIT.add(lblHEAD, 0, 2);
		layEDIT.add(lblSUB, 0, 3);
		layEDIT.add(lblTEXT, 1, 4);
		layEDIT.add(tto, 1, 1);
		layEDIT.add(thead, 1, 2);
		layEDIT.add(tsub, 1, 3);
		layEDIT.add(ttext, 0, 5);
		layEDIT.add(cmbTYPE, 0, 0);
		cmbTYPE.setValue("text/plain");
		//
//	##	//	##	//
	//	##	//	##	//
		//	##	//	##	//
		//
		btnSEND = new Button();
		btnSEND.setText("Send eMail");
		btnSEND.setOnAction(e -> {
			cTYPE = cmbTYPE.getValue();
			mto = tto.getText();
			mhead = thead.getText();
			msub = tsub.getText();
			cTEXT = ttext.getText();
			Mail(mto, msub, cTEXT);
		});
		layEDIT.add(btnSEND, 1, 5);
		
		
		
		
		edit = new Scene(layEDIT, 640, 710);
		
		log = new Scene(layLOG, 480, 210);
		win.setX(150);
		win.setY(200);
		win.setScene(log);
		win.show();
		
	}
	
	public static void auth() {
		boolean auth = chk(UN, PW);
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
	
	public static boolean chk(String UN, String PW) /*throws AuthenticationFailedException /*AddressException*/ {
		
		prop.put("mail.smtp.auth", "true");
		if(host.equals("smtp.gmail.com") || host.equals("smtp.mail.yahoo.com")){
			prop.put("mail.smtp.starttls.enable", "true");
		}
		prop.put("mail.smtp.host", host/*[0]*/);
		prop.put("mail.smtp.port", port);
		if(host.equals("smtp.mail.yahoo.com")) { prop.put("mail.smtp.ssl.enable", "true"); }
		
		boolean check = true;
		//
		try {
			InternetAddress e = new InternetAddress(UN);
			e.validate();
		} catch (AddressException e) {
			e.getStackTrace();
			check = false;
		}
		
		if(check) {
			sesh = Session.getInstance(prop,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(UN, PW);
					}
				});
		}
		
		return check;
		
		
		
		
	}
	
	public static void Mail(String to, String sub, String cont) /*throws IOException*/ {
		
		try {
			
			System.out.println("\n \n>> ?" + mto);
			System.out.println("\n \n>> ?" + to);
			MimeMessage m = new MimeMessage(sesh);
			m.setFrom(new InternetAddress(UN));
			m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			m.setSubject(sub);
			m.setSentDate(new Date());
			m.setContent(cont, cTYPE);
			m.setHeader("EMAIL HEAD", mhead);
			System.out.println("\n \n \n \n >> ??????? " + m.getContentType());
			System.out.println("\n \n \n \n >> ??????? " + m.getDataHandler());
			System.out.println("\n \n \n \n >> ??????? " + m.getSubject());
			System.out.println("\n \n \n \n >> ??????? " + m.getRecipients(Message.RecipientType.TO).toString());
			System.out.println("\n \n \n \n >> ??????? " + m.getAllRecipients().toString());
			//System.out.println("\n \n \n \n >> ??????? " + m.getContent().toString());
			//System.out.println("\n \n \n \n >> ??????? " + m.getInputStream());
			
			Transport t;
			if(host.equals("smtp.mail.yahoo.com"))
				t = sesh.getTransport("smtps");
			else 
				t = sesh.getTransport("smtp");
			 //
			System.out.println(">> ? smtp(s) ---> ## " + t.getURLName() + " \n>> ?");
			
			Transport.send(m);

			
			

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} // end try/catch //
		
		// });
			
		}
	
	
	
	
	
	
	
	
	
	
}


















