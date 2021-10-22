import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.mysql.jdbc.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class FormulaireController implements Initializable {
    
@FXML
private TextField txtid;
@FXML
private TextField txtnom;
@FXML
private TextField txtpren;
@FXML
private ComboBox selectfil;
@FXML
private ComboBox selectniv;
@FXML
private Label labniv;
private Label labfil;
private boolean update;

ObservableList<String> filieres = FXCollections.observableArrayList("Génie Informatique","Electronique","Télécommunication","Génie Industriel");

ObservableList<String> niveaux = FXCollections.observableArrayList("Licence 1","Licence 2","Licence 3","Master 1","Master 2");

@Override
public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub
    
    //ObservableList<String> filieres = FXCollections.observableArrayList("Génie Informatique","Electronique","Télécommunication","Génie Industriel");
       selectfil.setItems(filieres);
       
    //ObservableList<String> niveaux = FXCollections.observableArrayList("Licence 1","Licence 2","Licence 3","Master 1","Master 2");
       selectniv.setItems(niveaux);
}
/**
 * créer l'objet connect de la class ConnectDB
 * exécuter toutes les requetes sql en utilisant la fonction 
 * createStatement de la class Statement.
 * @param query
 */

public void executeQuery(String query){

    ConnectDB connect = new ConnectDB();
    Statement st;
    ResultSet rs;
    try{
        st = (Statement) connect.getConnect().createStatement();
        st.executeUpdate(query);
}catch(Exception e){
    e.printStackTrace();
}
}

Etudiants etudiants = new Etudiants();

@FXML
private void selectFiliere(MouseEvent event){
    String f = selectfil.getSelectionModel().getSelectedItem().toString();
    labfil.setText(f);
}

@FXML

private void selectNiveau(MouseEvent event){
    String n = selectniv.getSelectionModel().getSelectedItem().toString();
    labniv.setText(n);
}

@FXML
/**
 * la methode AjouterEtudiant 
 * permet de prendre en entrée toutes les informations
 * fournies grace au formulaire puis les inserrer dans la table étudiant
 * de la base de données ftic. */
private void AjouterEtudiant(MouseEvent event){
    String query = "INSERT INTO etudiant(nom,prenom,filiere,niveau) VALUES('" + txtnom.getText()+"', '" + txtpren.getText()+ "','"+selectfil.getValue()+"','"+selectniv.getValue()+"')";
    executeQuery(query);

    txtid.setText("");
    txtnom.setText("");
    txtpren.setText("");
    
    
}

public void setUpdate(boolean b) {
    this.update = b;
    
}

public void setTextField(int id, String nom, String prenom, String filiere, String niveau) {
    txtid.setText("" + id);
    txtnom.setText(nom);
    txtpren.setText(prenom);
    selectfil.setItems(filieres);
    selectniv.setItems(niveaux);
}


}
