/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiels;

import connectivity.Connexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author randretsa
 */
public class Assurance {
   
    Avion avion;
    String assurance;
    Date payement;
    Date expiration;
    double montant;

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

   
    public String getAssurance() {
        return assurance;
    }

    public void setAssurance(String assurance) {
        this.assurance = assurance;
    }

    public Date getPayement() {
        return payement;
    }

    public void setPayement(Date payement) {
        this.payement = payement;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    
    public ArrayList<Assurance> findAll(int remaining) throws SQLException,ClassNotFoundException,InstantiationException,IllegalAccessException
    {
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList list =null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "SELECT*,date_expiration - current_date remaining FROM assurance join avion on avion.id = assurance.idavion where date_expiration - current_date >="+remaining;
            rs = stmt.executeQuery(sql);
            while(rs.next()){

                Assurance k = new Assurance();
                k.setAssurance(rs.getString("assurance"));
                k.setPayement(rs.getDate("date_payement"));
                k.setExpiration(rs.getDate("date_expiration"));
                k.setMontant(rs.getDouble("montant"));
                
                Avion v = new Avion();
                v.setId(rs.getInt("idavion"));
                v.setMatricule(rs.getString("matricule"));
                
                k.setAvion(v);
                list.add(k);
            }
        } catch (Exception e) {
            //TODO: handle exception
            throw e;
        }
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         return list;
    }
    
}
