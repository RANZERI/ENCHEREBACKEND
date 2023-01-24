/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import connectivity.Connexion;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import materiel.Categorie;
import materiel.Produit;

/**
 *
 * @author randretsa
 */
public class Recherche {
   
       
    String[] name;
        String[] type;
        String[] etat;
        String description;
        
    public String formatSql(String[] name,String [] type,String[] etat,String description){
        
        String sql = "SELECT * FROM v_recherche where 1=1 "; 
        
        if(!"".equals(description)){
            sql = sql + "and description like '%"+description+"%'";
        }
        if(name!=null){
           
            sql = sql + " and (";
            int i =0;
            for(String s : name){

                if(i==0) sql = sql + " name like '%"+s+"%'";
                else sql = sql +" OR name like '%"+s+"%'";
                i++;
            }
            sql = sql +")";
        }
        
        
        if(type!=null){
           
            int i=0;
            sql = sql + " and (";
            for(String s : type){
                if(i==0) sql = sql +" type like '%"+s+"%'";
                else sql = sql +" OR type like '%"+s+"%'";
                i++;
            }
            sql = sql +")";
        }
        if(etat!=null){
           
            sql = sql + " and (";
            int i=0;
            for(String s : etat){
                if(i==0) sql = sql +" nom like '%"+s+"%'";
                else sql = sql +" OR nom like '%"+s+"%'";
                i++;
            }
            sql = sql +")";
        }
        System.out.println(sql+"    xxxx");
        return sql;
    }
    
    public List<Produit> rechercher(String[] name,String [] type,String[] etat,String description) throws Exception{
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList<Produit> list = null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = formatSql(name, type, etat, description);
            rs = stmt.executeQuery(sql);
        
            while(rs.next()){
                
                Produit p = new Produit();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setCategorieid(rs.getString("type"));
                p.setEtatid(rs.getString("nom"));
            
                list.add(p);
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

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String[] getEtat() {
        return etat;
    }

    public void setEtat(String[] etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
