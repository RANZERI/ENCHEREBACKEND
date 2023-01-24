package com.example.restservice;

import dao.LoginDao;
import dao.ProduitDao;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import materiels.Categorie;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.websocket.server.PathParam;
import materiels.Assurance;
import materiels.Compte;
import materiels.Enchere;
import materiels.Kilometrage;
import materiels.Produit;
import materiels.Transaction;
import model.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
public class CategorieController  {

	@GetMapping("/categors")
	public ResponseEntity<Object> getCategories() throws Exception{            
            return new ResponseEntity<>(Categorie.getListCategorie(), HttpStatus.OK);
	} 
	@GetMapping("/categorie/{id}")
	public ResponseEntity<Object> getProduit(@PathVariable int id) throws Exception{            
            return new ResponseEntity<>(ProduitDao.getProduitBy(id), HttpStatus.OK);
	} 
        @GetMapping("/compte/lister")
	public ResponseEntity<Object> getListe() throws Exception{            
            return new ResponseEntity<>(Compte.get_transaction_non_valider(), HttpStatus.OK);
	} 
        @PostMapping(value = "/compte/recharger")
	public ResponseEntity<Object> recharger(@RequestBody Compte id) { 
            try{
                Client cp=Client.FindByToken(id.getClient().getToken());
                id.setClient(cp);
                id.save();
                return new ResponseEntity<>("Inserer avec succes", HttpStatus.OK);
            }
            catch(Exception aaa){
                aaa.printStackTrace();
                return new ResponseEntity<>("{\"message\": \""+aaa.getMessage()+"\"}", HttpStatus.OK);
            }
	} 
	@PutMapping("/compte/valider/{id}")
	public ResponseEntity<Object> valider(@PathVariable String id) {            
            try{
                Compte.update("true", id);
            }
            catch(Exception aaa){
                aaa.printStackTrace();
                return new ResponseEntity<>("{\"message\": \""+aaa.getMessage()+"\"}", HttpStatus.OK);
            }
            return new ResponseEntity<>("Valider avec succes", HttpStatus.OK);
	} 
	@PostMapping("/transaction/encherir")
	public ResponseEntity<Object> miser(@RequestBody Transaction transact) throws Exception{            
            try{

                transact.save();
            }
            catch(Exception aaa){
                aaa.printStackTrace();
                return new ResponseEntity<>("{\"message\": \""+aaa.getMessage()+"\"}", HttpStatus.OK);
            }
            return new ResponseEntity<>("Miser avec succes", HttpStatus.OK);
	} 
            @RequestMapping(value = "/login",method=RequestMethod.GET)
    public ResponseEntity<Object> get_name(@RequestParam(name="email")String mail,@RequestParam(name="password")String password) {
        Client ee=null;
        try{
            ee=LoginDao.update_first(mail, password);
            if(ee!=null){
                return new ResponseEntity<>(ee, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("{\"message\": \"utilisateur inexistant\" }", HttpStatus.OK);
            }
        }
        catch(Exception aaa){
            aaa.printStackTrace();
            return new ResponseEntity<>("{\"message\": \""+aaa.getMessage()+"\"}", HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/logout",method=RequestMethod.GET)
    public ResponseEntity<Object> logout(@RequestParam(name="token")String mail) {
        Client ee=null;
        try{
             LoginDao.update(mail,null,null);
        }
        catch(Exception aaa){
            aaa.printStackTrace();
            return new ResponseEntity<>("{\"message\": \""+aaa.getMessage()+"\"}", HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"message\": \"Logout succes\"}", HttpStatus.OK);
    }
    @RequestMapping(value = "/inscrire",method=RequestMethod.POST)
    public ResponseEntity<Object> logout(@RequestBody Client mail) {
        try{
            mail.inscrire();
        return new ResponseEntity<>("{\"message\": \"Inscription succes\"}", HttpStatus.OK);
        }
        catch(Exception aaa){
            aaa.printStackTrace();
            return new ResponseEntity<>("{\"message\": \""+aaa.getMessage()+"\"}", HttpStatus.OK);
        }
    }
    private void saveUploadedFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            File myObj = new File("MEDIA/1/"+file.getOriginalFilename());
            myObj.createNewFile();
            Path path = Paths.get(myObj.toURI());
            Files.write(path, bytes);
            InputStream input = new FileInputStream(myObj);
            BufferedImage originalImage = ImageIO.read(input);
            Image newResizedImage = originalImage.getScaledInstance(300, 100, Image.SCALE_SMOOTH);
            String s = myObj.getAbsolutePath();
            String fileExtension = s.substring(s.lastIndexOf(".") + 1);
            ImageIO.write(convertToBufferedImage(newResizedImage),
                    fileExtension, myObj);
        }
    }
    public static BufferedImage convertToBufferedImage(Image img) {;

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bi = new BufferedImage(
                img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();

        return bi;
    }
        @RequestMapping(value = "/api/render/file",method=RequestMethod.GET)
        public ResponseEntity<Object> multiUploadFileModel() throws IOException{
                File b =new File("serveur/");
                File[] tab=b.listFiles();
                byte[][] pae=new byte[tab.length][];
                List<String> encodedString=new ArrayList<>();
                for(int i=0;i<tab.length;i++){
                    pae[i] = Files.readAllBytes(tab[i].toPath());   
                    encodedString.add(Base64.getEncoder().encodeToString(pae[i]));

                }
            return new ResponseEntity<>(encodedString, HttpStatus.OK);
        }        
    @RequestMapping(value = "/api/upload/file",method=RequestMethod.POST)
    public ResponseEntity<Object> multiUploadFileModel(@RequestParam("File") MultipartFile file) {
            // Save as you want as per requiremens0
            
                System.out.println(file.getOriginalFilename()+"    cccccc     ");
            try{
                if(file!=null){
                    saveUploadedFile(file);        
                }
            }
            catch(IOException ae){
                ae.printStackTrace();
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);                
            }
        return new ResponseEntity<>("Successfully uploaded!", HttpStatus.OK);
    }   
   	@GetMapping("/enchere/{id}")
	public Enchere getEnchere(@PathVariable int id) throws Exception{
            Enchere pdd=ProduitDao.getEnchere(id);
            return pdd;
	}
   
}