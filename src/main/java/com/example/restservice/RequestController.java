/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restservice;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import materiel.Enchere;
import materiel.Produit;
import model.Compte;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utilities.Recherche;
import utilities.Utilities;

@CrossOrigin
@RestController
public class RequestController  {
    
    
	@GetMapping("/caracteristiques")
	public HashMap getInsertProduitForm() throws Exception{
            
            HashMap<String,Object> map = new HashMap();
         
            Utilities utilities= new Utilities();
            
           map.put("etat",utilities.getAllEtat());
           map.put("categorie", utilities.getAllCategorie());
           
           return map;
	
        }
       
       @PostMapping("/caracteristiques") 
       public String insertProduit(@RequestBody Produit produit) throws Exception{
          
           produit.save();
           
            
           return "insertion reussi";
	}
       
       	@GetMapping("/toenchere/{id}")
	public Produit getProduitById(@PathVariable String id) throws Exception{
            
         
            Utilities utilities= new Utilities();
   
           return utilities.getProduit_byId(id);
	
        }
        
        @PostMapping("/toenchere") 
       public String createEnchere(@RequestBody Enchere enchere) throws Exception{
          
           enchere.save();
             
           return "mise en enchere réussi";
	}
       
        @GetMapping("/encheres")
	public HashMap getListEnchere() throws Exception{
            
            HashMap<String,Object> map = new HashMap();
         
            Utilities utilities= new Utilities();
            
            map.put("data",utilities.getNonSoldItem());
         
           return map;
	
        }
        
        @PostMapping("/comptes") 
       public String chargeCompte(@RequestBody Compte compte) throws Exception{
          
           compte.chargerCompte();
             
           return "compte recharger";
	}
       
        @GetMapping("/statistiques")
	public HashMap statistiques() throws Exception{
            
            HashMap<String,Object> map = new HashMap();
         
            Utilities utilities= new Utilities();
            
            map.put("data",utilities.getStatistique());
         
           return map;
	
        }
        
       @PostMapping("/recherche") 
       public HashMap recherche(@RequestBody Recherche recherche) throws Exception{
          
            HashMap<String,Object> map = new HashMap();
         
            Recherche r = new Recherche();
            
            map.put("data",r.rechercher(recherche.getName(), recherche.getType(), recherche.getEtat(), recherche.getDescription()));
         
           return map;
	}
        
    
}
