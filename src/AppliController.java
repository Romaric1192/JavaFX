

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.awt.Desktop;


import com.mysql.jdbc.Statement;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;


public class AppliController implements Initializable{

    @FXML
    private TableColumn<Etudiants, String> editCol;
    @FXML
    private TableView<Etudiants> tb;
    @FXML
    private TableColumn<Etudiants, Integer> tbid;
    @FXML
    private TableColumn<Etudiants,String> tbnom;
    @FXML
    private TableColumn<Etudiants,String> tbpren;
    @FXML
    private TableColumn<Etudiants,String> tbfil;
    @FXML
    private TableColumn<Etudiants,String> tbniv;
    @FXML
    private Button btnaj;
    private Button btnsup;
    // private Button btnmod;
    private TextField txtid;
    private TextField txtnom;
    private TextField txtpr;
    private ComboBox<String> fileselect;
    private ComboBox<String> nivselect;

    ObservableList<String> fil = FXCollections.observableArrayList("Génie Informatique","Electronique","Télécommunication","Génie Industriel");

    ObservableList<String> niv = FXCollections.observableArrayList("Licence 1","Licence 2","Licence 3","Master 1","Master 2");

    Etudiants etudiants;

    @Override
    /**
     * mettre les url vers toutes les fonctions
     * pour la réutilisation
     */
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        // TODO Auto-generated method stub
        //fileselect.setItems(fil);
           
        //nivselect.setItems(niv);
        
        
        voirListe();
        
        
    }
    
    @FXML
    private void selectFiliere(MouseEvent event){
        String f = fileselect.getSelectionModel().getSelectedItem().toString();
        //labfil.setText(f);
    }
    
    @FXML
    
    private void selectNiveau(MouseEvent event){
        String n = nivselect.getSelectionModel().getSelectedItem().toString();
        //labniv.setText(n);
    }


    @FXML
    private void handleButtonAction(ActionEvent event) {

        /*System.out.println("You clicked me!");
        label.setText("Hello World!");*/
        voirListe();
         if (event.getSource()== btnsup){
        supprimerEtudiant();
         } 
        
    }
    

    public  ObservableList<Etudiants> getEtudiantsList(){
        ObservableList<Etudiants> EtudiantList =  FXCollections.observableArrayList();
     
        ConnectDB  connect = new ConnectDB();
        String query = "SELECT * FROM etudiant ORDER BY id asc";
        ResultSet rs;
        Statement st;
       
        
       try{
       st = (Statement) connect.getConnect().createStatement();
       rs = st.executeQuery(query);
       while(rs.next()){
            etudiants = new Etudiants(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("filiere"),
                    rs.getString("niveau"));
             EtudiantList.add(etudiants);
        }
       }catch(SQLException ex){}
        
        return EtudiantList;
        
    }

    /**
     * affichage de la table etudiant à l'interface graphique
     */
    ObservableList<Etudiants> list ;

    public void voirListe(){
        
        list = (ObservableList<Etudiants>) getEtudiantsList();
        tbid.setCellValueFactory(new PropertyValueFactory<Etudiants,Integer>("id"));
        tbnom.setCellValueFactory(new PropertyValueFactory<Etudiants,String>("nom"));
        tbpren.setCellValueFactory(new PropertyValueFactory<Etudiants,String>("prenom"));
        tbfil.setCellValueFactory(new PropertyValueFactory<Etudiants,String>("filiere"));
        tbniv.setCellValueFactory(new PropertyValueFactory<Etudiants,String>("niveau"));
        tb.setItems(list);
        
    }
    @FXML
    private void Actualiser(MouseEvent event) throws Exception{
        voirListe();
        sauvegarde(tb);
    }
    /**
     * supprimer un étudiant dans
     *  la base de données.
     * prendre en entrée l'id de l'etudiant
     * à la selection d'une ligne de la table
     * puis appliquer la requete sql correspondante.
     */
    @FXML
    public void supprimerEtudiant(){
    etudiants = tb.getSelectionModel().getSelectedItem();
    String query = "DELETE  FROM etudiant WHERE id = "+etudiants.getId()+"";
    executeQuery(query);
    voirListe();
    
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
        // ResultSet rs;
        try{
            st = (Statement) connect.getConnect().createStatement();
            st.executeUpdate(query);
    }catch(Exception e){
        e.printStackTrace();
    }}

    
    
    /**
     * afficher le formulaire d'inscription d'un étudiant
     * appeler le fichier Formulaire.fxml comme ressource
     * à exploiter.
     * @throws IOException
     * 
     */
@FXML

     private void getFormulaire(MouseEvent event) throws IOException{
         Stage stage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("Formulaire.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle("INSERTION/MODIFICATION");
        stage.setScene(scene);
        stage.show();
     }

     @FXML
     private void handleButtonAction2(MouseEvent event) {
          Etudiants etudiants = tb.getSelectionModel().getSelectedItem();
          txtid.setText(""+etudiants.getId());
          txtnom.setText(etudiants.getNom());
          txtpr.setText(etudiants.getPrenom());
          fileselect.setValue(etudiants.getFiliere());
          nivselect.setValue(etudiants.getNiveau());
          
          /*System.out.println("id " + etudiants.getId());
          System.out.println("Nom " + etudiants.getNom());*/
          
      }
  


@FXML
public void modifierEtudiant(MouseEvent event){
    String query = "UPDATE etudiant SET nom = '"+txtnom.getText()+"', prenom = '"+txtpr.getText()+"',filiere = '"+fileselect.getValue()+"', niveau = '"+nivselect.getValue()+"' "
    + "WHERE id = "+ txtid.getText()+"";
executeQuery(query);
//voirListe();

}

/**
 * sauvergarde de données dans un fichier 
 * excel
 * @param tb
 */
public void sauvegarde(TableView<Etudiants> tb){
    HSSFWorkbook hssfWorkbook=new HSSFWorkbook();
    HSSFSheet hssfSheet = hssfWorkbook.createSheet("Sheet1");
    HSSFRow firstRow = hssfSheet.createRow(0);
    HSSFRow hssfRow;

    //set titles of columns
    for (int i=0; i<tb.getColumns().size();i++){

        firstRow.createCell(i).setCellValue(tb.getColumns().get(i).getText());

    }

    for (int row=0; row<tb.getItems().size();row++){

        hssfRow= hssfSheet.createRow(row+1);

        for (int col=0; col<tb.getColumns().size(); col++){

            Object celValue = tb.getColumns().get(col).getCellObservableValue(row).getValue();

            try {
                if (celValue != null && Double.parseDouble(celValue.toString()) != 0.0) {
                    hssfRow.createCell(col).setCellValue(Double.parseDouble(celValue.toString()));
                }
            } catch (  NumberFormatException e ){

                hssfRow.createCell(col).setCellValue(celValue.toString());
            }

        }

    }

    //save excel file and close the workbook
    try {
        hssfWorkbook.write(new FileOutputStream("sauvegarde.xls"));
        hssfWorkbook.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

}
/**
 * sauvegarde de données dans un fichier csv
 * csv: comma saved value
 * @throws Exception
 */

public void writeExcel() throws Exception {
    String data = "This is the data in the output file";

    try {
      // Creates a FileWriter
      FileWriter file = new FileWriter("output.txt");

      // Creates a BufferedWriter
      BufferedWriter output = new BufferedWriter(file);

      // Writes the string to the file
      output.write(data);

      // Closes the writer
      output.close();
    }

    catch (Exception e) {
      e.getStackTrace();
    }
}

/**
 * exporter la javadoc comme aide en ligne
 * @param event
 */
@FXML
public void helpInline( MouseEvent event){

    String url = "G://POO//netbeans//Appli_Etudiant_FTIC//dist//javadoc/index.html";

    if(Desktop.isDesktopSupported()){
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }else{
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("xdg-open " + url);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

     }

