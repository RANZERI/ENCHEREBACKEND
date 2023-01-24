/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiels;

import com.fasterxml.jackson.annotation.JsonFormat;
import connectivity.Connexion;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author randretsa
 */
public class Kilometrage {
    int idavion;
    @JsonFormat(pattern="yyyy-MM-dd")
    Date dates;
    double debut_km;
    double fin_km;

    public int getIdavion() {
        return idavion;
    }

    public void setIdavion(int idavion) {
        this.idavion = idavion;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public double getDebut_km() {
        return debut_km;
    }

    public void setDebut_km(double debut_km) {
        this.debut_km = debut_km;
    }

    public double getFin_km() {
        return fin_km;
    }

    public void setFin_km(double fin_km) {
        this.fin_km = fin_km;
    }
    
        public Kilometrage save() throws SQLException,ClassNotFoundException,InstantiationException,IllegalAccessException
    {
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "INSERT INTO kilometrage values("+this.getIdavion()+",'"+this.getDates()+"',"+this.getDebut_km()+","+this.getFin_km()+")";
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
    }
        
       public ArrayList<Kilometrage> findById(int id) throws SQLException,ClassNotFoundException,InstantiationException,IllegalAccessException
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
            sql = "SELECT*FROM kilometrage where id="+id;
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Kilometrage k = new Kilometrage();
                k.setIdavion(id);
                k.setDates(rs.getDate("dates"));
                k.setDebut_km(rs.getDouble("debut"));
                k.setFin_km(rs.getDouble("fin"));
                
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
