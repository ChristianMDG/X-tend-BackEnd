document.getElementById('addBookForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const nomUtilisateur = document.getElementById('nomUtilisateur').value;
    const emailUtilisateur = document.getElementById('emailUtilisateur').value;

    const bookData = {

        nomUtilisateur: nomUtilisateur,
        emailUtilisateur: emailUtilisateur
    };

    try {
        const response = await fetch('http://localhost:8080/api/livres', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(bookData),
        });

        if (response.ok) {
            alert('Livre ajouté avec succès!');
            document.getElementById('addBookForm').reset(); // Réinitialiser le formulaire
        } else {
            alert('Erreur lors de l\'ajout du livre');
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de l\'ajout du livre');
    }
});
