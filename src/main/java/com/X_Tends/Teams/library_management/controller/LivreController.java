package com.X_Tends.Teams.library_management.controller;

/*import com.X_Tends.Teams.library_management.util.FileUploadUtil;
import com.X_Tends.Teams.library_management.model.Livre;
import com.X_Tends.Teams.library_management.repository.LivreRepository;
import com.X_Tends.Teams.library_management.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
@RequestMapping("/api/livres")
public class LivreController {

    @Autowired
    private LivreService livreService;
    @Autowired
    private LivreRepository livreRepository;



    // Récupérer les livres disponibles
    @GetMapping("/disponibles")
    public List<Livre> getAvailableLivres() {
        return livreService.getAvailableLivres();
    }

    // Récupérer un livre par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable("id") Long id) {
        try {
            Livre livre = livreService.getLivreById(id);
            return ResponseEntity.ok(livre);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Ajouter un nouveau livre
    @PostMapping
    public ResponseEntity<Livre> addLivre(@RequestBody Livre livre) {
        Livre nouveauLivre = livreService.addLivre(livre);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauLivre);
    }

    // Mettre à jour un livre par ID
    @PutMapping("/{id}")
    public ResponseEntity<Livre> updateLivre(@PathVariable("id") Long id, @RequestBody Livre updatedLivre) {
        try {
            Livre livre = livreService.updateLivre(id, updatedLivre);
            return ResponseEntity.ok(livre);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Supprimer un livre par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivre(@PathVariable("id") Long id) {
        try {
            livreService.deleteLivre(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Emprunter un livre
    @PostMapping("/emprunter/{id}")
    public ResponseEntity<String> emprunterLivre(@PathVariable Long id) {
        try {
            Livre livre = livreService.getLivreById(id);
            if (!livre.isDisponible()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le livre n'est pas disponible.");
            }

            // Mettre à jour la disponibilité
            livre.setDisponible(false);
            livre.setBorrowDate(LocalDate.now());
            livre.setReturnDate(LocalDate.now().plusDays(14));

            livreService.updateLivre(id, livre);
            return ResponseEntity.ok("Livre emprunté avec succès.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé.");
        }
    }

    // Afficher les livres empruntés
    @GetMapping("/emprunts")
    public List<Livre> getBorrowedLivres() {
        return livreService.getBorrowedLivres();
    }
   @PostMapping("/retourner/{id}")
   public ResponseEntity<String> retournerLivre(@PathVariable Long id) {
       try {
           Livre livre = livreService.getLivreById(id);
           livre.setDisponible(true);
           livreService.updateLivre(id, livre);
           return ResponseEntity.ok("Livre retourné avec succès.");
       } catch (EntityNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé.");
       }
   }


    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Livre> addLivre(
            @RequestPart("titre") String titre,
            @RequestPart("isbn") String isbn,
            @RequestPart("auteur") String auteur,
            @RequestPart("disponible") Boolean disponible,
            @RequestPart("description") String description,
            @RequestParam("imageFile") MultipartFile imageFile) {

        try {
            // Sauvegarder l'image dans un répertoire local
            String fileName = imageFile.getOriginalFilename();
            String uploadDir = "uploads/images/";
            FileUploadUtil.saveFile(uploadDir, fileName, imageFile);

            // Créer et enregistrer le livre
            Livre livre = new Livre();
            livre.setTitre(titre);
            livre.setIsbn(isbn);
            livre.setAuteur(auteur);
            livre.setDisponible(disponible);
            livre.setDescription(description);
            livre.setImageUrl(uploadDir + fileName);  // Enregistrer le chemin de l'image

            Livre savedLivre = livreRepository.save(livre);

            return ResponseEntity.ok(savedLivre);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllLivres() {
        return ResponseEntity.ok(livreRepository.findAll());
    }
}

/*import com.X_Tends.Teams.library_management.util.FileUploadUtil;
import com.X_Tends.Teams.library_management.model.Livre;
import com.X_Tends.Teams.library_management.repository.LivreRepository;
import com.X_Tends.Teams.library_management.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
@RequestMapping("/api/livres")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private LivreRepository livreRepository;

    // Récupérer les livres disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Livre>> getAvailableLivres() {
        List<Livre> livres = livreService.getAvailableLivres();
        return ResponseEntity.ok(livres);
    }

    // Récupérer un livre par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable("id") Long id) {
        try {
            Livre livre = livreService.getLivreById(id);
            return ResponseEntity.ok(livre);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Ajouter un nouveau livre avec upload d'image
    @PostMapping
    public ResponseEntity<String> addLivre(
            @RequestParam("titre") String titre,
            @RequestParam("isbn") String isbn,
            @RequestParam("auteur") String auteur,
            @RequestParam("disponible") Boolean disponible,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Livre nouveauLivre = new Livre();
            nouveauLivre.setTitre(titre);
            nouveauLivre.setIsbn(isbn);
            nouveauLivre.setAuteur(auteur);
            nouveauLivre.setDisponible(disponible);
            nouveauLivre.setDescription(description);

            // Traitement de l'image
            if (image != null && !image.isEmpty()) {
                String imageUrl = FileUploadUtil.saveFile(image);
                nouveauLivre.setImageUrl(imageUrl);
            }

            livreService.addLivre(nouveauLivre);
            return ResponseEntity.status(HttpStatus.CREATED).body("Livre ajouté avec succès");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload de l'image");
        }
    }

    // Mettre à jour un livre par ID avec upload d'image
    @PutMapping("/{id}")
    public ResponseEntity<String> updateLivre(
            @PathVariable Long id,
            @RequestParam("titre") String titre,
            @RequestParam("isbn") String isbn,
            @RequestParam("auteur") String auteur,
            @RequestParam("disponible") Boolean disponible,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Livre livre = livreService.getLivreById(id);
            livre.setTitre(titre);
            livre.setIsbn(isbn);
            livre.setAuteur(auteur);
            livre.setDisponible(disponible);
            livre.setDescription(description);

            // Traitement de l'image
            if (image != null && !image.isEmpty()) {
                String imageUrl = FileUploadUtil.saveFile(image);
                livre.setImageUrl(imageUrl);
            }

            livreService.updateLivre(id, livre);
            return ResponseEntity.ok("Livre mis à jour avec succès");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload de l'image");
        }
    }

    // Supprimer un livre par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLivre(@PathVariable Long id) {
        try {
            livreService.deleteLivre(id);
            return ResponseEntity.ok("Livre supprimé avec succès");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé");
        }
    }

    // Emprunter un livre
    @PostMapping("/emprunter/{id}")
    public ResponseEntity<String> emprunterLivre(@PathVariable Long id) {
        try {
            Livre livre = livreService.getLivreById(id);
            if (!livre.isDisponible()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le livre n'est pas disponible.");
            }

            // Mettre à jour la disponibilité
            livre.setDisponible(false);
            livre.setBorrowDate(LocalDate.now());
            livre.setReturnDate(LocalDate.now().plusDays(14));

            livreService.updateLivre(id, livre);
            return ResponseEntity.ok("Livre emprunté avec succès.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé.");
        }
    }

    // Afficher les livres empruntés
    @GetMapping("/emprunts")
    public ResponseEntity<List<Livre>> getBorrowedLivres() {
        List<Livre> livres = livreService.getBorrowedLivres();
        return ResponseEntity.ok(livres);
    }

    // Retourner un livre
    @PostMapping("/retourner/{id}")
    public ResponseEntity<String> retournerLivre(@PathVariable Long id) {
        try {
            Livre livre = livreService.getLivreById(id);
            livre.setDisponible(true);
            livreService.updateLivre(id, livre);
            return ResponseEntity.ok("Livre retourné avec succès.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé.");
        }
    }
}*/


import com.X_Tends.Teams.library_management.model.Livre;
import com.X_Tends.Teams.library_management.repository.LivreRepository;
import com.X_Tends.Teams.library_management.service.LivreService;
import com.X_Tends.Teams.library_management.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
@RequestMapping("/api/livres")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private LivreRepository livreRepository;

    private final String uploadDir = "C:/Library backup/library-management/Gestion De Library X-Tends 1.0/uploads/";

    // Récupérer tous les livres
    @GetMapping
    public ResponseEntity<List<Livre>> getAllLivres() {
        List<Livre> livres = livreService.getAllLivres();
        return ResponseEntity.ok(livres);
    }

    // Récupérer un livre par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable("id") Long id) {
        try {
            Livre livre = livreService.getLivreById(id);
            return ResponseEntity.ok(livre);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Ajouter un nouveau livre avec upload d'image
    @PostMapping
    public ResponseEntity<String> addLivre(
            @RequestParam("titre") String titre,
            @RequestParam("isbn") String isbn,
            @RequestParam("auteur") String auteur,
            @RequestParam("disponible") Boolean disponible,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Livre nouveauLivre = new Livre();
            nouveauLivre.setTitre(titre);
            nouveauLivre.setIsbn(isbn);
            nouveauLivre.setAuteur(auteur);
            nouveauLivre.setDisponible(disponible);
            nouveauLivre.setDescription(description);

            // Traitement de l'image
            if (image != null && !image.isEmpty()) {
                String imageUrl = FileUploadUtil.saveFile(image);
                nouveauLivre.setImageUrl(imageUrl);
            }

            livreService.addLivre(nouveauLivre);
            return ResponseEntity.status(HttpStatus.CREATED).body("Livre ajouté avec succès");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload de l'image");
        }
    }

    // Mettre à jour un livre par ID avec upload d'image
    @PutMapping("/{id}")
    public ResponseEntity<String> updateLivre(
            @PathVariable Long id,
            @RequestParam("titre") String titre,
            @RequestParam("isbn") String isbn,
            @RequestParam("auteur") String auteur,
            @RequestParam("disponible") Boolean disponible,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Livre livre = livreService.getLivreById(id);
            livre.setTitre(titre);
            livre.setIsbn(isbn);
            livre.setAuteur(auteur);
            livre.setDisponible(disponible);
            livre.setDescription(description);

            // Traitement de l'image
            if (image != null && !image.isEmpty()) {
                String imageUrl = FileUploadUtil.saveFile(image);
                livre.setImageUrl(imageUrl);
            }

            livreService.updateLivre(id, livre);
            return ResponseEntity.ok("Livre mis à jour avec succès");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload de l'image");
        }
    }

    // Supprimer un livre par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLivre(@PathVariable Long id) {
        try {
            livreService.deleteLivre(id);
            return ResponseEntity.ok("Livre supprimé avec succès");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé");
        }
    }

    // Emprunter un livre
    @PostMapping("/emprunter/{id}")
    public ResponseEntity<String> emprunterLivre(@PathVariable Long id) {
        try {
            Livre livre = livreService.getLivreById(id);
            if (!livre.isDisponible()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le livre n'est pas disponible.");
            }

            // Mettre à jour la disponibilité
            livre.setDisponible(false);
            livre.setBorrowDate(LocalDate.now());
            livre.setReturnDate(LocalDate.now().plusDays(14));

            livreService.updateLivre(id, livre);
            return ResponseEntity.ok("Livre emprunté avec succès.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé.");
        }
    }

    // Afficher les livres empruntés
    @GetMapping("/emprunts")
    public ResponseEntity<List<Livre>> getBorrowedLivres() {
        List<Livre> livres = livreService.getBorrowedLivres();
        return ResponseEntity.ok(livres);
    }

    // Retourner un livre
    @PostMapping("/retourner/{id}")
    public ResponseEntity<String> retournerLivre(@PathVariable Long id) {
        try {
            Livre livre = livreService.getLivreById(id);
            livre.setDisponible(true);
            livreService.updateLivre(id, livre);
            return ResponseEntity.ok("Livre retourné avec succès.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé.");
        }
    }

    // Récupérer les livres disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Livre>> getAvailableLivres() {
        List<Livre> livres = livreService.getAvailableLivres();
        return ResponseEntity.ok(livres);
    }

    // Récupérer une image par son nom de fichier
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
