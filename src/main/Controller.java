package main;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import Model.Viewer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.FileChooser;

public class Controller implements Initializable {
	@FXML
	private VBox viewersLayout;
	@FXML
	private Button addViewerButton;
	@FXML
	private Button saveDataButton;
	@FXML
	private String streamer;
	@FXML
	private TextField streamerField;
	
	private List<Viewer> viewers = new ArrayList<Viewer>();
	
	ViewerItemController vic = new ViewerItemController();
	
	public int counter;
	
	private String headerWriter = "";
	
	private String header = "";
	
	private String footer = "";
	
	File htmlHeader = new File("Model/templateHeader.html");
	
	File htmlFooter = new File("Model/templateFooter.html");
	
	JavascriptBuilder scriptBuilder = new JavascriptBuilder();
	
	String[] caseStrings = new String[0];
	
	private Path pathToSaveFile;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		for(int i = 0; i < 10; i++) {
			Viewer viewer = new Viewer();
			viewers.add(i, viewer);
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/Model/viewer_item.fxml"));
			counter++;
			try {
				HBox hbox = fxmlLoader.load();
				viewersLayout.getChildren().add(hbox);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void addViewer(ActionEvent event) {
		Viewer viewer = new Viewer();
		viewers.add(viewer);
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/Model/viewer_item.fxml"));
		try {
			HBox hbox = fxmlLoader.load();
			viewersLayout.getChildren().add(hbox);
			counter++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveData(ActionEvent event) throws IOException {
		//open file chooser
		FileChooser saveFile = new FileChooser();
		saveFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("html", "*.html"));
		saveFile.setTitle("Save Your HTML File");
		File finalFile = saveFile.showSaveDialog(null);
		Path htmlPath = Paths.get(finalFile.getAbsolutePath());
		streamer = streamerField.getText();
		for(int i = 0; i < counter; i++) {
			ObservableList<Node> childsVB = viewersLayout.getChildren();
			HBox hb = (HBox)childsVB.get(i);
			ObservableList<Node> childsHB = hb.getChildren();
			TextField tf = (TextField)childsHB.get(1);
			TextField tf2 = (TextField)childsHB.get(2);
			Viewer thisViewer = viewers.get(i);
			thisViewer.setUserName(tf);
			thisViewer.setPathToField(tf2);
		}
		try {
			System.out.println(htmlHeader.getCanonicalPath());
			System.out.println(htmlFooter.getCanonicalPath());
			BufferedReader headerReader = new BufferedReader(new FileReader(htmlHeader));
			BufferedReader footerReader = new BufferedReader(new FileReader(htmlFooter));
			try {
				//write HTML header
				while((header = headerReader.readLine()) != null) {
					headerWriter += header + System.lineSeparator();
				}
				//write opening to switch
				headerWriter += scriptBuilder.switchOpen;
				//write switch case for each viewer
				for(Viewer viewer : viewers) {
					if(viewer.getUserName() != "" && viewer.getPathToField() != "") {
						headerWriter += scriptBuilder.switchBuilder(viewer.getUserName().toLowerCase());
					}
				}
				//close switch
				
				headerWriter += "\n\t\t\t}\n\t\t}";
				//write functions to only play audio once
				for(Viewer viewer : viewers) {
					if(viewer.getUserName() != "" && viewer.getPathToField() != "") {
						headerWriter += scriptBuilder.onceFunction(viewer.getUserName().toLowerCase());
					}
				}
				headerWriter += "\n";
				//create Howl items for each viewer
				for(int i = 0; i < viewers.size(); i++) {
					Viewer viewer = viewers.get(i);
					if (viewer.getUserName() != "" && viewer.getPathToField() != "") {
						Path pathToAudio = Paths.get(viewer.getPathToField());
						pathToSaveFile = htmlPath.relativize(pathToAudio).normalize();
						String htmlPath2 = pathToSaveFile.toString();
						htmlPath2 = htmlPath2.substring(1);
						headerWriter +=  (scriptBuilder.howlBuilder(viewer.getUserName().toLowerCase(), htmlPath2.replace('\\', '/')));
					}
				}
				headerWriter += "\n";
				while((footer = footerReader.readLine()) != null) {
					headerWriter += footer + System.lineSeparator();
				}
				// write Streamer's name along with HTML end
				headerWriter = headerWriter.replace("$streamer", streamer);
				try {
					if(!finalFile.exists()) {
						try {
							FileWriter myWriter = new FileWriter(finalFile);
							//BufferedWriter print = new BufferedWriter(outFile);
							finalFile.createNewFile();
							myWriter.write(headerWriter);
							//myWriter.flush();
							myWriter.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					headerReader.close();
					footerReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public void print() {
		for(Viewer viewer: viewers) {
			if(viewer.getPathToField() != "" && viewer.getUserName() != "") {
				//System.out.println("User: " + viewer.getUserName() + " - Path: " + viewer.getPathToField());
			}
		}
	}
}

