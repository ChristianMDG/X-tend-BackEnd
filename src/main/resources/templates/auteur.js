const apiUrl = 'http://localhost:8080/api/books';

// Fonction pour récupérer et afficher la liste des livres disponibles
async function fetchBooks() {
    const response = await fetch(apiUrl + '/disponibles');
    const books = await response.json();
    const bookList = document.getElementById('book-list');
    bookList.innerHTML = '';

    books.forEach(book => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <strong>${book.title}</strong> par ${book.author} - ${book.genre}
            <br>
            <img src="${book.imageUrl}" alt="${book.title}" style="width: 100px;">
            <p>${book.description}</p>
            <button onclick="borrowBook(${book.id})">Emprunter</button>
        `;
        bookList.appendChild(listItem);
    });
}

// Fonction pour récupérer et afficher les emprunts de l'utilisateur
async function fetchUserLoans() {
    const response = await fetch('http://localhost:8080/api/users/emprunts');
    const loans = await response.json();
    const loansList = document.getElementById('user-loans-list');
    loansList.innerHTML = '';

    loans.forEach(loan => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <strong>${loan.book.title}</strong> - ${loan.borrowedDate}
            <button onclick="returnBook(${loan.book.id})">Retourner</button>
        `;
        loansList.appendChild(listItem);
    });
}

// Fonction pour emprunter un livre
async function borrowBook(bookId) {
    const response = await fetch(`http://localhost:8080/api/books/emprunter/${bookId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (response.ok) {
        alert("Livre emprunté avec succès !");
        fetchBooks(); // Mettre à jour la liste des livres disponibles
        fetchUserLoans(); // Mettre à jour la liste des emprunts de l'utilisateur
    } else {
        alert("Erreur lors de l'emprunt du livre.");
    }
}

// Fonction pour retourner un livre
async function returnBook(bookId) {
    const response = await fetch(`http://localhost:8080/api/books/retourner/${bookId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (response.ok) {
        alert("Livre retourné avec succès !");
        fetchUserLoans(); // Mettre à jour la liste des emprunts de l'utilisateur
        fetchBooks(); // Mettre à jour la liste des livres disponibles
    } else {
        alert("Erreur lors du retour du livre.");
    }
}

// Événements
document.addEventListener('DOMContentLoaded', () => {
    fetchBooks();
    fetchUserLoans();
});