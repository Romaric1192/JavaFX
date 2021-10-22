

public class Etudiants {
   private String nom, prenom, filiere, niveau;
    private int id;
    

public Etudiants(){

}
public Etudiants(int id, String nom, String prenom, String filiere, String niveau){
    this.id = id;
    this.nom = nom;
    this.prenom = prenom;
    this.filiere = filiere;
    this.niveau = niveau;
}
public int getId(){
    return this.id;
}
public String getNom(){
    return this.nom;

}
public String getPrenom(){
    return this.prenom;
}
public String getFiliere(){
    return this.filiere;

}
public String getNiveau(){
    return this.niveau;
}

    
}
