package com.X_Tends.Teams.library_management.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadUtil {

    private static final String UPLOAD_DIR = "C:/Library backup/library-management/Gestion De Library X-Tends 1.0/uploads/";

    public static String saveFile(MultipartFile file) throws IOException {
        // Générer un nom de fichier unique
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        // Créer le chemin complet pour le répertoire d'upload
        Path uploadPath = Paths.get(UPLOAD_DIR);

        // Créer le répertoire s'il n'existe pas
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Chemin complet du fichier à enregistrer
        Path filePath = uploadPath.resolve(fileName);

        try (var inputStream = file.getInputStream()) {
            // Copier le fichier dans le chemin d'upload
            Files.copy(inputStream, filePath);
        } catch (IOException e) {
            throw new IOException("Impossible de sauvegarder le fichier : " + fileName, e);
        }

        // Retourner l'URL relative du fichier
        return "/uploads/" + fileName;
    }

    public static void saveFile(String uploadDir, String fileName, MultipartFile imageFile) {
    }
}
