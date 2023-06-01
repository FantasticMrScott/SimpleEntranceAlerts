package main;


import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;


public class ViewerItemController {
    @FXML
    private Button deleteButton;

    @FXML
    private Button pathButton;

    @FXML
    private TextField pathField;

    @FXML
    private TextField usernameField;
    
    private String userName;
    private String path;
    
   public void setUsername(String userName) {
	   userName = usernameField.getText();
    }
   
   public void setPath(String path) {
	   path = pathField.getText();
   }
   
   public void setPathField(String newPath) {
	   pathField.setText(newPath);
   }
   
   public String getUsername() {
	   return userName;
   }
   
   public String getPath() {
	   return path;
   }
   
   
   public void findPath(ActionEvent event) {
	   FileChooser fileChooser = new FileChooser();
	   fileChooser.setTitle("Select Audio File");
	   fileChooser.getExtensionFilters().addAll(
			   new FileChooser.ExtensionFilter("All Audio Files", "*.mp3", "*.wav", "*.FLAC", "*.mp4"),
			   new FileChooser.ExtensionFilter("All Files", "*.*")
			   );
	   
	   File file = fileChooser.showOpenDialog(null); // select file
	   
	   if(file != null) {
		   String newPath = file.getAbsolutePath();
		   setPathField(newPath);
	   }
	   
   }
}
