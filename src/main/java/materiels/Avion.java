/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiels;

import com.fasterxml.jackson.annotation.JsonFormat;
import connectivity.Connexion;
import java.io.File;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;

/**
 *
 * @author randretsa
 */
public class Avion {
    int id;
    String matricule;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Timestamp[] dates;
    double[] debut;
    double[] fin;
    String image;
    String marque;
    int annee;
    String description;
    public Avion(){
        
    }
    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp[] getDates() {
        return dates;
    }

    public double[] getDebut() {
        return debut;
    }

    public double[] getFin() {
        return fin;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDates(Timestamp[] dates) {
        this.dates = dates;
    }

    public void setDebut(double[] debut) {
        this.debut = debut;
    }

    public void setFin(double[] fin) {
        this.fin = fin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    @Override
    public String toString() {
        return "Vehicule{" + "id=" + id + ", matricule=" + matricule + '}';
    }
    
    public ArrayList findAll() throws Exception
    {
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        Avion v = null;
        ResultSet rs = null;
        ArrayList list = null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "SELECT*FROM avion";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                v = new Avion();
                v.setId(rs.getInt("id"));
                v.setMatricule(rs.getString("matricule"));
                v.setDescription(rs.getString("descriptions"));
                v.setMarque(rs.getString("marque"));
                v.setAnnee(rs.getInt("annee"));
                File p=new File("serveur/"+rs.getInt("id")+".jpg");
                byte[] pae = Files.readAllBytes(p.toPath());                   
                v.setImage((Base64.getEncoder().encodeToString(pae)));
                list.add(v);
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
    public Avion findById(int id) throws SQLException,ClassNotFoundException,InstantiationException,IllegalAccessException, Exception
    {
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        Avion v = null;
        ResultSet rs = null;
        try {
            v = new Avion();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.HOLD_CURSORS_OVER_COMMIT
            );
            sql = "SELECT*FROM v_avion where id = "+id;
            rs = stmt.executeQuery(sql);
            rs.last();
            System.out.println(rs.getRow()+"    00000000");
            dates=new Timestamp[rs.getRow()];
            debut=new double[rs.getRow()];
            fin=new double[rs.getRow()];
            rs.beforeFirst();
            int i=0;
            while(rs.next()){
                v.setId(rs.getInt("id"));
                v.setMatricule(rs.getString("matricule"));
                dates[i]=(rs.getTimestamp("dates"));
                debut[i]=(rs.getDouble("debut"));
                fin[i]=(rs.getDouble("fin"));
                v.setDescription(rs.getString("descriptions"));
                v.setMarque(rs.getString("marque"));
                v.setAnnee(rs.getInt("annee"));
                File p=new File("serveur/"+rs.getInt("id")+".jpg");
                byte[] pae = Files.readAllBytes(p.toPath());                   
                v.setImage((Base64.getEncoder().encodeToString(pae)));
                i++;
            }
            v.setDates(dates);
            v.setDebut(debut);
            v.setFin(fin);
        } catch (Exception e) {
            //TODO: handle exception
            throw e;
        }
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         return v;
    }    
    
  /*  public Avion save() throws SQLException,ClassNotFoundException,InstantiationException,IllegalAccessException
    {
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "INSERT INTO vehicule values(default,'"+this.getMatricule()+"')";
            stmt.execute(sql);
        } catch (Exception e) {
            //TODO: handle exception
            throw e;
        }
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         return this;
    }*/
}
