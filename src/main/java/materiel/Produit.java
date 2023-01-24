/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiel;

import connectivity.Connexion;
import java.sql.Statement;

/**
 *
 * @author randretsa
 */
public class Produit {
    int id;
    String name;
    String description;
    String categorieid;
    String etatid;
    
    public void save() throws Exception{
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "insert into Produit(name,description,Categorieid,Etatid) values('"+this.name+"','"+this.description+"',"+this.categorieid+","+this.etatid+")";
            stmt.execute(sql);
        } catch (Exception e) {
            //TODO: handle exception
            throw e;
        }
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorieid() {
        return categorieid;
    }

    public void setCategorieid(String categorieid) {
        this.categorieid = categorieid;
    }

    public String getEtatid() {
        return etatid;
    }

    public void setEtatid(String etatid) {
        this.etatid = etatid;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", name=" + name + ", description=" + description + ", categorieid=" + categorieid + ", etatid=" + etatid + '}';
    }
    
    
    
}
