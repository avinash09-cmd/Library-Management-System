import java.io.*;
import java.util.*;

// Book class
class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("✅ Book borrowed successfully.");
        } else {
            System.out.println("❌ Sorry! Book is not available.");
        }
    }

    public void returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            System.out.println("✅ Book returned successfully.");
        } else {
            System.out.println("⚠️ This book was not borrowed.");
        }
    }

    @Override
    public String toString() {
        return id + " | " + title + " | " + author + " | " + (isAvailable ? "Available" : "Issued");
    }
}

// Library class
class Library {
    private List<Book> books;
    private final String fileName = "library.dat";

    public Library() {
        books = new ArrayList<>();
        loadData();
    }

    public void addBook(Book book) {
        books.add(book);
        saveData();
    }

    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("📚 No books in the library.");
        } else {
            System.out.println("\n--- Book List ---");
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    public void borrowBook(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                b.borrowBook();
                saveData();
                return;
            }
        }
        System.out.println("❌ Book not found.");
    }

    public void returnBook(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                b.returnBook();
                saveData();
                return;
            }
        }
        System.out.println("❌ Book not found.");
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.out.println("⚠️ Error saving data.");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            books = (List<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            books = new ArrayList<>();
        }
    }
}

// Main class
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        int choice;

        do {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    library.addBook(new Book(id, title, author));
                }
                case 2 -> library.viewBooks();
                case 3 -> {
                    System.out.print("Enter Book ID to borrow: ");
                    int bid = sc.nextInt();
                    library.borrowBook(bid);
                }
                case 4 -> {
                    System.out.print("Enter Book ID to return: ");
                    int rid = sc.nextInt();
                    library.returnBook(rid);
                }
                case 5 -> System.out.println("👋 Exiting...");
                default -> System.out.println("⚠️ Invalid choice!");
            }
        } while (choice != 5);

        sc.close();
    }
}
