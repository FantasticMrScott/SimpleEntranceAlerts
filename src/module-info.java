module SimpleEntranceAlertsFx {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires java.desktop;
	
	opens main to javafx.graphics, javafx.fxml;
}
