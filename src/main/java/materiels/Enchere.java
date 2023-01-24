/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiels;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import connectivity.Connexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Client;

/**
 *
 * @author Mendrika
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Enchere {
    private String id;
    private String date;
    private double prix_depart;
    private String date_fin;
    private double prix_vente;
    private String name;
    private String description;
    private String catergorinom;
    private String etatnom;
    private String image;

    public Enchere(String id, String date, double prix_depart, String date_fin, double prix_vente, String name, String description, String catergorinom, String etatnom, String idProduit) {
        this.id = id;
        this.date = date;
        this.prix_depart = prix_depart;
        this.date_fin = date_fin;
        this.prix_vente = prix_vente;
        this.name = name;
        this.description = description;
        this.catergorinom = catergorinom;
        this.etatnom = etatnom;
        this.image = idProduit;
    }
    public Enchere() {
    }
    public void setId(String id) {
        this.id = id;
    }    
    public void setImage(String image) {
        this.image = image;
    }
    public static Enchere findById(int id) throws Exception{
        Enchere compte=null;
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        try {
            compte = new Enchere();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "SELECT*from Enchere where id="+id;
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                compte.setPrix_depart(rs.getDouble(3));
                compte.setDate_fin(rs.getString(4));
                compte.setPrix_vente(rs.getDouble(5));
            }
        } 
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         return compte;
    }
    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrix_depart(double prix_depart) {
        this.prix_depart = prix_depart;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public void setPrix_vente(double prix_vente) {
        this.prix_vente = prix_vente;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCatergorinom(String catergorinom) {
        this.catergorinom = catergorinom;
    }

    public void setEtatnom(String etatnom) {
        this.etatnom = etatnom;
    }

    public double getPrix_depart() {
        return prix_depart;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public double getPrix_vente() {
        return prix_vente;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCatergorinom() {
        return catergorinom;
    }

    public String getEtatnom() {
        return etatnom;
    }

    public String getImage() {
        return image;
    }


    
    
    
}
