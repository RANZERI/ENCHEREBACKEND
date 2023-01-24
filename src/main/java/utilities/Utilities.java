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
import materiel.Enchere;
import materiel.Etat;
import materiel.Produit;
import model.Statistique;

/**
 *
 * @author randretsa
 */
public class Utilities {
        public List<Etat> getAllEtat() throws Exception{
             Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList<Etat> list = null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "select*from etat";
            rs = stmt.executeQuery(sql);
        
            while(rs.next()){
                
                Etat c = new Etat();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                list.add(c);
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
            
        public List<Categorie> getAllCategorie() throws Exception{
             Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList<Categorie> list = null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "select*from categorie";
            rs = stmt.executeQuery(sql);
        
            while(rs.next()){
                
                Categorie c = new Categorie();
                c.setId(rs.getInt("id"));
                c.setType(rs.getString("type"));
                list.add(c);
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
        
        public Produit getProduit_byId(String id) throws Exception{
            Statement stmt = null;
            String sql = null;
            Connexion connexion = null;
            ResultSet rs = null;
            Produit p = null;
            try {
                p = new Produit();
                connexion = new Connexion();
                stmt = connexion.getConnect().createStatement();
                sql = "select*from produit where id="+id;
                rs = stmt.executeQuery(sql);

                while(rs.next()){
                        p.setId(rs.getInt("id"));
                        p.setName(rs.getString("name"));
                        p.setDescription(rs.getString("description"));
                        p.setEtatid(rs.getInt("etatid")+"");
                        p.setCategorieid(rs.getInt("categorieid")+"");
                }
            } catch (Exception e) {
                //TODO: handle exception
                throw e;
            }
            finally{
                rs.close();
                stmt.close();
                connexion.getConnect().close();
            }
            return p;
            
        }
        
        public List<Enchere> getNonSoldItem() throws Exception{
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList<Enchere> list = null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "select * from v_etat_enchere where idenchere not in (select enchereid from encherecloturer);";
            rs = stmt.executeQuery(sql);
        
            while(rs.next()){
                
                Enchere e = new Enchere();
                Produit p =new Produit();
                p.setId(rs.getInt("idproduit"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setCategorieid(rs.getString("type"));
                p.setEtatid(rs.getString("nom"));
              
                e.setDate(rs.getDate("date"));
                e.setDate_fin(rs.getString("date_fin"));
                e.setId(rs.getInt("idenchere"));
                e.setPrix_depart(rs.getDouble("prix_depart"));
                e.setPrix_vente(rs.getDouble("max"));
                e.setProduit(p);
                
                list.add(e);
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
       
        public List<Statistique> getStatistique() throws Exception{
                     Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList<Statistique> list = null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "select*from v_statistique";
            rs = stmt.executeQuery(sql);
        
            while(rs.next()){
                Statistique s = new Statistique();
                s.setCategorie(rs.getString("type"));
                s.setMontant(rs.getDouble("montant"));
                s.setTotal(rs.getDouble("total"));
                s.getPourcentageValue();
                list.add(s);
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
