//Tim Goetjen and Kinh Hoang
package songlib.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public SongController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		songTable.sort();
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		showSongDetails(null);

		songTable.getSelectionModel().selectedItemProperty().addListener((observable, 
				oldValue, newValue) -> showSongDetails(newValue));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setSongLib(SongLib songLib) {
		this.songLib = songLib;

		// Add observable list data to the table
		songTable.setItems(songLib.getSongData());
	}

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

	@FXML
	private void handleDeleteSong(){
		Song selectedSong = songTable.getSelectionModel().getSelectedItem();
		int selectedIndex = songTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			boolean okClicked = songLib.showSongDeleteDialog(selectedSong);
			if (okClicked)
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

	@FXML
	private void handleNewSong() {
		Song tempSong = new Song();
		boolean okClicked = songLib.showSongEditDialog(tempSong);
		if (okClicked) {
			songLib.getSongData().add(tempSong);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditSong() {
		Song selectedSong = songTable.getSelectionModel().getSelectedItem();
		if (selectedSong != null) {
			boolean okClicked = songLib.showSongEditDialog(selectedSong);
			if (okClicked) {
				showSongDetails(selectedSong);
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

