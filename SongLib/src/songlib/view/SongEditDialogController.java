//Tim Goetjen and Kinh Hoang
package songlib.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import songlib.app.SongLib;
import songlib.model.Song;

public class SongEditDialogController {
	@FXML
	private TextField nameField;
	@FXML
	private TextField artistField;
	@FXML
	private TextField albumField;
	@FXML
	private TextField yearField;

	private Stage dialogStage;
	private Song song;
	private boolean okClicked = false;
	private SongLib songLib;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setSongLib(SongLib songLib) {
		this.songLib = songLib;
	}

	// Sets fields in the edit dialog
	public void setSong(Song song) {
		this.song = song;

		nameField.setText(song.getName());
		artistField.setText(song.getArtist());
		albumField.setText(song.getAlbum());
		yearField.setText(song.getYear());
	}


	public boolean isOkClicked() {
		return okClicked;
	}

	// Called when the user hits OK
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			boolean duplicate = false;
			for(Song s : songLib.getSongData()){
				String n = s.getName();
				String a = s.getArtist();

				System.out.println(n + " "+ a);
				if(n.toLowerCase().equals(nameField.getText().toLowerCase()) &&
						(a.toLowerCase().equals(artistField.getText().toLowerCase()))){
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("Invalid Fields");
					alert.setHeaderText("Please correct invalid fields");
					alert.setContentText("Duplicate: Song already in list!\n");

					alert.showAndWait();
					duplicate = true;
					break;
				}
			}
			if(!duplicate){
				song.setName(nameField.getText());
				song.setArtist(artistField.getText());
				song.setAlbum(albumField.getText());
				song.setYear(yearField.getText());
				okClicked = true;
			}

			dialogStage.close();
		}
	}


	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 */
	private boolean isInputValid() {
		String errorMessage = "";
		if (nameField.getText() == null || nameField.getText().length() == 0) {
			errorMessage += "No valid name!\n"; 
		}
		if (artistField.getText() == null || artistField.getText().length() == 0) {
			errorMessage += "No valid artist!\n"; 
		}

		if (errorMessage.length() == 0) {
			return true;
		} 
		else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
}
