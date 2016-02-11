//Tim Goetjen and Kinh Hoang
package songlib.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import songlib.model.Song;
import songlib.view.SongController;
import songlib.view.SongDeleteDialogController;
import songlib.view.SongEditDialogController;

public class SongLib extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<Song> songData = FXCollections.observableArrayList();

	public SongLib(){
		load_data("src/songlib/model/datafile.txt");
		songData.add(new Song("Lose Yourself","Eminem","Curtain Call", "2005"));
		songData.add(new Song("All I Do Is Win", "DJ Khaled", "All I Do Is Win", "2012"));
	}
	public ObservableList<Song> getSongData(){
		return songData;
	}
	
	private void load_data(String pathname){ // Attempts to load a program datafile if one exists
		File datafile = new File(pathname);
		if(!datafile.exists()){
			System.out.println("No Datafile exists continuing to program");
			return;
		}
		String filePath = datafile.getAbsolutePath();
		Path path = Paths.get(filePath);
		
	    try {
	    	System.out.println("Datafile Found: attempting to load data"); 
	        List<String> lines = Files.readAllLines(path);
	        for (String line : lines) {
	        	String[] values = line.split("[,]");// Delineate by commas
	        	if(values.length != 4){ 
	        		System.out.println("bad input line");
	        		continue;
	        	}
	        	for(int i = 0; i<values.length; i++){ // clean up whitespace
	        		 values[i] = values[i].trim();
	        	}
	        	if(values[0].compareTo("") == 0 || values[1].compareTo("") == 0){ 
	        		System.out.println("Song or Title missing: Continuing to next line");
	        		continue;
	        	}
	        	songData.add(new Song(values[0], values[1],values[2], values[3]));
	        }
	    } catch (IOException e) {
	        System.out.println(e);
	    }
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("My Song Library");		
		this.primaryStage.setResizable(false);
		
		initRootLayout();
		
		showSongView();
	}
	
	@Override
	public void stop(){
	    System.out.println("Gotta save mang");
	    // Delete file 
	    // Save file
	}

	public void showSongView() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(SongLib.class.getResource("/songlib/view/SongView.fxml"));
			AnchorPane songView = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(songView);
			
			SongController controller = loader.getController();
			controller.setSongLib(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(SongLib.class.getResource("/songlib/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean showSongEditDialog(Song song) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(SongLib.class.getResource("/songlib/view/SongEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Song");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        SongEditDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setSong(song);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean showSongDeleteDialog(Song song) {
	    try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(SongLib.class.getResource("/songlib/view/SongDeleteDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Delete Song");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        SongDeleteDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);

	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public static void main(String[] args) {
		launch(new String[0]);
	}
	public Stage getPrimaryStage() {
		return primaryStage;
	}

}