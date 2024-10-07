package com.X_Tends.Teams.library_management.service;

/*import com.X_Tends.Teams.library_management.model.Livre;
import com.X_Tends.Teams.library_management.repository.LivreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;



    // Get all Livres
    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    // Get all available Livres
    public List<Livre> getAvailableLivres() {
        return livreRepository.findByDisponible(true);
    }

    // Get a Livre by ID
    public Livre getLivreById(Long id) {
        return livreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livre non trouvé avec l'ID : " + id));
    }

    // Add a new Livre
    @Transactional
    public Livre addLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    // Update a Livre
    @Transactional
    public Livre updateLivre(Long id, Livre updatedLivre) {
        Livre livre = getLivreById(id);
        livre.setTitre(updatedLivre.getTitre());
        livre.setIsbn(updatedLivre.getIsbn());
        livre.setDatePublication(updatedLivre.getDatePublication());
        livre.setDisponible(updatedLivre.isDisponible());
        livre.setImageUrl(updatedLivre.getImageUrl());
        livre.setDescription(updatedLivre.getDescription());
        livre.setAuteur(updatedLivre.getAuteur());
        livre.setNom(updatedLivre.getNom());
        livre.setEmail(updatedLivre.getEmail());
        return livreRepository.save(livre);
    }

    // Delete a Livre by ID
    @Transactional
    public ResponseEntity<Void> deleteLivre(Long id) {
        try {
            livreRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livre non trouvé");
        }
    }

    // Get all borrowed Livres
    public List<Livre> getBorrowedLivres() {
        return livreRepository.findByDisponible(false);
    }

    // Save or update a Livre
    @Transactional
    public Livre saveOrUpdateLivre(Livre livre) {
        return livreRepository.save(livre);
    }
}
*/

import com.X_Tends.Teams.library_management.model.Livre;
import com.X_Tends.Teams.library_management.repository.LivreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;
    private final String uploadDir = "C:/Library backup/library-management/Gestion De Library X-Tends 1.0/uploads/";

    // Get all Livres
    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    // Get all available Livres
    public List<Livre> getAvailableLivres() {
        return livreRepository.findByDisponible(true);
    }

    // Get a Livre by ID
    public Livre getLivreById(Long id) {
        return livreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livre non trouvé avec l'ID : " + id));
    }

    // Add a new Livre
    @Transactional
    public Livre addLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    // Update a Livre
    @Transactional
    public Livre updateLivre(Long id, Livre updatedLivre) {
        Livre livre = getLivreById(id);
        livre.setTitre(updatedLivre.getTitre());
        livre.setIsbn(updatedLivre.getIsbn());
        livre.setDatePublication(updatedLivre.getDatePublication());
        livre.setDisponible(updatedLivre.isDisponible());
        livre.setImageUrl(updatedLivre.getImageUrl());
        livre.setDescription(updatedLivre.getDescription());
        livre.setAuteur(updatedLivre.getAuteur());
        livre.setNom(updatedLivre.getNom());
        livre.setEmail(updatedLivre.getEmail());
        return livreRepository.save(livre);
    }

    // Delete a Livre by ID
    @Transactional
    public void deleteLivre(Long id) {
        if (!livreRepository.existsById(id)) {
            throw new EntityNotFoundException("Livre non trouvé avec l'ID : " + id);
        }
        livreRepository.deleteById(id);
    }

    // Get all borrowed Livres
    public List<Livre> getBorrowedLivres() {
        return livreRepository.findByDisponible(false);
    }

    // Save or update a Livre
    @Transactional
    public Livre saveOrUpdateLivre(Livre livre) {
        return livreRepository.save(livre);
    }
}


