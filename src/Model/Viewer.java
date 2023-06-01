package Model;

import javafx.scene.control.TextField;

public class Viewer {
	
	private String userName;
	private String pathToField;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(TextField tf) {
		this.userName = tf.getText();
	}
	public String getPathToField() {
		return pathToField;
	}
	public void setPathToField(TextField tf2) {
		this.pathToField = tf2.getText();
	}
}

