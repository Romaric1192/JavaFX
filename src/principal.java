import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class principal extends Application{



    @Override
    public void start(Stage stage) throws Exception {
        // TODO Auto-generated method stub
        Parent parent = FXMLLoader.load(getClass().getResource("Appli.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle("GESTION DES ETUDIANTS");
        stage.setScene(scene);
        stage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
