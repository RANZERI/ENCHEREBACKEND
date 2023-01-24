/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connectivity.Connexion;
import gestion.Convertisseur;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import materiels.Categorie;
import materiels.Enchere;
import materiels.Etat;
import materiels.Produit;
import materiels.Transaction;
import model.Client;

/**
 *
 * @author Mendrika
 */
public class ProduitDao {
    public static List<Produit> getProduitBy(int id)throws  Exception{
        Statement stmt=null;
        Timestamp time=null;
        String ssql="select * from v_produit where id2="+id;
        Connection con=new Connexion().getConnect();
        stmt=con.createStatement();
        System.out.println(ssql);
        List<Produit> n=new ArrayList<Produit>();
        ResultSet fin=stmt.executeQuery(ssql);
        Produit x=null;
        while(fin.next()){
            Categorie cat=new Categorie();
            cat.setId(fin.getInt(4));
            cat.setImage(fin.getString(5));
            x=new Produit(fin.getInt(1),fin.getString(2),fin.getString(3),cat,new Etat(fin.getInt(6),fin.getString(7)));
            for(int i=0;i<6;i++){
                File p=new File("MEDIA/"+i+"/"+x.getId()+".png");
                if(p.exists()){            
                    byte[] pae = Files.readAllBytes(p.toPath());                   
                    x.setImage((Base64.getEncoder().encodeToString(pae)));
                    break;
                }
            }
            n.add(x);
        }
        return n;
    }

    public static Enchere getEnchere(int id)throws  Exception{
        Statement stmt=null;
        Timestamp time=null;
        String ssql="select * from v_enchere where id="+id;
        Connection con=new Connexion().getConnect();
        stmt=con.createStatement();
        ResultSet fin=stmt.executeQuery(ssql);
        Enchere x=null;
        while(fin.next()){
            x=new Enchere(fin.getString(1),fin.getString(2),fin.getDouble(3),fin.getString(4),fin.getDouble(5),fin.getString(6),fin.getString(7),fin.getString(8),fin.getString(9),fin.getString(10));
        }
        if(Transaction.get_last((int) x.getPrix_vente())!=null){
            double current=Transaction.get_last((int) x.getPrix_vente()).getMontant();
            x.setPrix_depart(current);
        }
        if(x!=null){
            for(int i=0;i<6;i++){
                File p=new File("MEDIA/"+i+"/"+x.getImage()+".png");
                if(p.exists()){            
                    byte[] pae = Files.readAllBytes(p.toPath());                   
                    x.setImage((Base64.getEncoder().encodeToString(pae)));
                }
            }
        }
        System.out.println(ssql+"   "+x);
        return x;
    }
    
}
