//Tim Goetjen and Kinh Hoang
package songlib.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import songlib.app.SongLib;
import songlib.model.Song;

public class SongController {

	@FXML
	private TableView<Song> songTable;
	@FXML
	private TableColumn<Song, String> nameColumn;

	@FXML
	private Label nameLabel;
	@FXML
	private Label artistLabel;
	@FXML
	private Label albumLabel;
	@FXML
	private Label yearLabel;

	// Reference to the main application.
	private SongLib songLib;
	private Stage dialogStage;

	// Sets up the controller class with listeners and table fields
	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		showSongDetails(null);

		songTable.getSelectionModel().selectedItemProperty().addListener((observable, 
				oldValue, newValue) -> showSongDetails(newValue));
	}

	
	//Is called by the main application to give a reference back to itself.
	public void setSongLib(SongLib songLib) {
		this.songLib = songLib;

		// Add observable list data to the table
		songTable.setItems(songLib.getSongData());
		
		if(songLib.getSongData().size()!=0){
			songTable.getSelectionModel().select(0);
		}
	}

	// Shows song details in main app
	private void showSongDetails(Song song){
		if(song!=null){
			nameLabel.setText(song.getName());
			artistLabel.setText(song.getArtist());
			albumLabel.setText(song.getAlbum());
			yearLabel.setText(song.getYear());
		}
		else{
			nameLabel.setText("");
			artistLabel.setText("");
			albumLabel.setText("");
			yearLabel.setText("");
		}
	}

	// Deletes the song
	@FXML
	private void handleDeleteSong(){
		Song selectedSong = songTable.getSelectionModel().getSelectedItem();
		int selectedIndex = songTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			boolean okClicked = songLib.showSongDeleteDialog(selectedSong);
			if (okClicked)
				songTable.getSelectionModel().selectBelowCell();
				songTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(songLib.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Song Selected");
			alert.setContentText("Please select a song in the table.");

			alert.showAndWait();
		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	// Adds a new song after validating uniqueness
	@FXML
	private void handleNewSong() {
		Song tempSong = new Song();
		boolean okClicked = songLib.showSongEditDialog(tempSong);
		boolean duplicate = false;
		if (okClicked) {
			for(Song s : songLib.getSongData()){
				String n = s.getName();
				String a = s.getArtist();
				if(n.toLowerCase().equals(tempSong.getName().toLowerCase()) &&
						(a.toLowerCase().equals(tempSong.getArtist().toLowerCase()))){
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
				songLib.getSongData().add(tempSong);
				songTable.getSelectionModel().select(tempSong);
				FXCollections.sort(songLib.getSongData());
			}
		}
	}

	// Opens a dialog to edit the song
	@FXML
	private void handleEditSong() {
		Song selectedSong = songTable.getSelectionModel().getSelectedItem();
		if (selectedSong != null) {
			boolean okClicked = songLib.showSongEditDialog(selectedSong);
			if (okClicked) {
				showSongDetails(selectedSong);
				FXCollections.sort(songLib.getSongData());
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(songLib.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Song Selected");
			alert.setContentText("Please select a song in the table.");

			alert.showAndWait();
		}
	}
}

