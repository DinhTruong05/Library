package src.Manager;


import src.Book;
import src.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class dataManager {
    public static List<Book> bookList = new ArrayList<>();
    public static List<User> userList = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void addBook() {
        System.out.println("Input ID book");
        String id = scanner.nextLine();
        System.out.println("Input title");
        String title = scanner.nextLine();
        System.out.println("Input author");
        String author = scanner.nextLine();
        System.out.println("Input category");
        String category = scanner.nextLine();
        System.out.println("Input available");
        boolean available = scanner.nextLine().equals("true");
        Book book = new Book(id, title, author, category, available);
        bookList.add(book);
        System.out.println("Input Done ");
    }

    public static void removeBook() {
        System.out.println("Input Id book to remove");
        String id = scanner.nextLine();
        Book book = findBook(id);
        if (book == null) {
            System.out.println("Wrong Id or book not found ");
            return;
        }
        bookList.remove(book);
        System.out.println("Remove Done");

    }

    public static void updateBook() {
        System.out.println("Input Id book to update");
        String id = scanner.nextLine();
        Book book = findBook(id);
        if (book == null) {
            System.out.println("Wrong Id or book not found  ");
            return;
        }


        System.out.println(" New title : ");
        book.setTitle(scanner.nextLine());
        System.out.println(" New author : ");
        book.setAuthor(scanner.nextLine());
        System.out.println(" New category : ");
        book.setCategory(scanner.nextLine());

        System.out.println(" Update done!");

    }

    public static void searchBook() {
        System.out.println(" Input title or author : ");
        String title = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(title.toLowerCase())) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) System.out.println(" Not found !!");

    }

    public static void registerUser() {
        System.out.print("User Id: ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        userList.add(new User(id, name, email));
        System.out.println("Register user done.");
    }

    public static void borrowBook() {
        System.out.println(" Input ID User : ");
        String userId = scanner.nextLine();
        User user = findUser(userId);
        if (user == null) return;

        System.out.println(" Input ID Book to borrow : ");
        String bookId = scanner.nextLine();
        Book book = findBook(bookId);
        if (book == null) return;

        user.addBorrowedBook(book);
    }


    public static void returnedBook() {
        System.out.println("ID User: ");
        String userId = scanner.nextLine();
        User user = findUser(userId);
        if (user == null) return;

        System.out.println("ID Book to return: ");
        String bookId = scanner.nextLine();
        user.removeBorrowedBook(bookId);
    }

    public static void displayBookList() {
        System.out.println("\nLIST BOOK:");
        for (Book book : bookList) {
            System.out.println(book);
        }

        System.out.println("\nLIST USER:");
        for (User user : userList) {
            System.out.println(user);
        }
    }

    public static void showStatistics() {
        long borrowed = bookList.stream().filter(b -> !b.isAvailable()).count();
        System.out.println("\nThống kê");
        System.out.println("Total book: " + bookList.size());
        System.out.println(" Total book borrowed: " + borrowed);
    }

    public static void saveToFile() {
        try (PrintWriter bw = new PrintWriter("file/book.txt")) {
            for (Book b : bookList) {
                bw.println(b.getId() + ";"
                        + b.getTitle() + ";"
                        + b.getAuthor() + ";"
                        + b.getCategory() + ";"
                        + b.isAvailable());
            }
        } catch (Exception e) {
            System.out.println("Error input file book");
        }

        try (PrintWriter bw = new PrintWriter("file/user.txt")) {
            for (User u : userList) {
                bw.println(u.getId() + ";"
                        + u.getName() + ";"
                        + u.getEmail());
            }
        } catch (Exception e) {
            System.out.println("Error input file user");
        }
        System.out.println(" Export file done ");
    }


    public static User findUser(String id) {
        for (User user : userList) {
            if (user.getId().equals(id)) return user;
        }
        System.out.println(" Not found User !");
        return null;
    }

    public static Book findBook(String id) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) return book;
        }
        System.out.println(" Not found Book !");
        return null;
    }

    public static void loadFromFile() {
        try(BufferedReader br = new BufferedReader(new FileReader("file/book.txt"))) {
            String line;
            while ((line = br.readLine())!= null){
                String[] data = line.split(";");
                if (data.length== 5) {
                    String id = data[0];
                    String title = data[1];
                    String author = data[2];
                    String category = data[3];
                    boolean available = Boolean.parseBoolean(data[4]);
                    bookList.add(new Book(id, title, author, category, available));
                }
            }

        }catch (Exception e){
            System.out.println("Can't read file book : " + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader("file/user.txt"))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] data = line.split(";");
                if (data.length== 3) {
                    String id = data[0];
                    String name = data[1];
                    String email = data[2];
                    userList.add(new User(id, name, email));
                }
            }

        }catch (Exception e){
            System.out.println(" Can't read file user: " + e.getMessage());
        }

        System.out.println("Loading from file is done.");
    }


}
