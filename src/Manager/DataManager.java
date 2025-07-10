package src.Manager;


import src.Book;
import src.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static src.IChoice.Choice.*;

public class DataManager {
    public static List<Book> bookList = new ArrayList<>();
    public static List<User> userList = new ArrayList<>();

    private static final Scanner scanner = new Scanner(System.in);

    public static void addBook() {
        System.out.println("Input ID book");
        String id = scanner.nextLine();
        System.out.println("Input title");
        String title = scanner.nextLine();
        System.out.println("Input author");
        String author = scanner.nextLine();
        System.out.println("Input category");
        String category = scanner.nextLine();
        System.out.println("Is the book available? (true/false)");
        boolean available = Boolean.parseBoolean(scanner.nextLine().trim().toLowerCase());
        Book book = new Book(id, title, author, category, available);
        bookList.add(book);
        DataManager.saveToFile();
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
        DataManager.saveToFile();
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

        DataManager.saveToFile();
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
        DataManager.saveToFile();
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
        if (book == null || !book.isAvailable()) {
            System.out.println("Book is not available.");
            return;
        }

        user.addBorrowedBook(book);
        book.setAvailable(false);
        saveToFile();
        System.out.println("Borrow book done.");
    }


    public static void returnedBook() {
        System.out.println("ID User: ");
        String userId = scanner.nextLine();
        User user = findUser(userId);
        if (user == null) return;

        System.out.println("ID Book to return: ");
        String bookId = scanner.nextLine();
        user.removeBorrowedBook(bookId);

        Book book = findBook(bookId);

        if (book != null) book.setAvailable(true);
        saveToFile();
        System.out.println("Return book done.");
    }



    public static void displayBookList() {

        System.out.println("\nLIST BOOK:");
        for (Book book : bookList) {
            System.out.println(book);
        }

        System.out.println("\nLIST USER:");
        for (User user : userList) {
            System.out.println(user);
            List<Book> borrowedBookList = user.getBorrowedBookList();
            if (!borrowedBookList.isEmpty()) {
                System.out.println("Borrowed books:");
                for (Book book : borrowedBookList) {
                    System.out.println("    - " + book);
                }
            }
        }
    }

    public static void showStatistics() {
        long borrowed = bookList.stream().filter(b -> !b.isAvailable()).count();
        System.out.println("\nThống kê");
        System.out.println("Total book: " + bookList.size());
        System.out.println(" Total book borrowed: " + borrowed);
    }

    public static void exportBorrowBookToFile() {
        try (PrintWriter bw = new PrintWriter("file/borrowBook.txt")) {
            for (User user : userList) {
                List<Book> borrowedBookList = user.getBorrowedBookList();
                if (borrowedBookList.isEmpty()) {
                    System.out.println("No book borrowed " );
                    continue;
                }
                for (Book book : borrowedBookList) {
                    bw.println(user.getId() + ";" + user.getName() + ";"
                            + book.getId() + ";" + book.getTitle());
                    }
            }
            System.out.println("Borrowed book details exported to file/borrowBook.txt");
        } catch (Exception e) {
            System.out.println("Error input file borrowBook" + e.getMessage());
        }
    }

    public static void exportAvailableBooksToFile() {
        try (PrintWriter writer = new PrintWriter("file/available_books.txt")) {
            for (Book book : bookList) {
                if (book.isAvailable()) {
                    writer.println(book.getId() + ";" + book.getTitle() + ";" + book.getAuthor() + ";" + book.getCategory());
                }
            }
            System.out.println("Available books exported to file/available_books.txt");
        } catch (Exception e) {
            System.out.println("Error exporting available books: " + e.getMessage());
        }
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
        try (BufferedReader br = new BufferedReader(new FileReader("file/borrowed.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 2) {
                    User user = findUser(data[0]);
                    Book book = findBook(data[1]);
                    if (user != null && book != null) {
                        user.addBorrowedBook(book);
                        book.setAvailable(false);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Can't read file borrowed: " + e.getMessage());
        }

        System.out.println("Loading from file is done.");


    }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        DataManager.loadFromFile();
        while (true) {
            System.out.println(" Chào mừng đến với thư viện sách online !! ");
            System.out.println(" Vui lòng chọn chức năng mốn sử dụng: ");
            System.out.println(" 1. Đăng ký tài khoản ");
            System.out.println(" 2. Thêm sách ");
            System.out.println(" 3. Xóa sách ");
            System.out.println(" 4. Sửa sách ");
            System.out.println(" 5. Tìm sách ");
            System.out.println(" 6. Mượn sách ");
            System.out.println(" 7. Trả sách ");
            System.out.println(" 8. Danh sách người dùng và sách ");
            System.out.println(" 9. Tổng số sách và số sách đã cho mượn ");
            System.out.println("10. Xuất danh sách sách đang được mượn ra file");
            System.out.println("11. Xuất danh sách sách chưa mượn ra file");


            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == exit) {
                System.out.println(" Đang lưu dữ liệu");
                DataManager.saveToFile();
                System.out.println(" Bye");

                break;
            }
            switch (choice) {
                case registerUser:
                    DataManager.registerUser();
                    break;
                case addBook:
                    DataManager.addBook();
//                    dataManager.saveToFile();
                    break;
                case removeBook:
                    DataManager.removeBook();
//                    dataManager.saveToFile();
                    break;
                case updateBook:
                    DataManager.updateBook();
//                    DataManager.saveToFile();
                    break;
                case searchBook:
                    DataManager.searchBook();
                    break;
                case borrowBook:
                    DataManager.borrowBook();
                    DataManager.saveToFile();
                    break;
                case returnedBook:
                    DataManager.returnedBook();
                    DataManager.saveToFile();
                    break;
                case displayBookList:
                    DataManager.displayBookList();
                    break;
                case showStatistics:
                    DataManager.showStatistics();
                    break;
                case exportBorrowBookToFile:
                    DataManager.exportBorrowBookToFile();
                    break;
                case exportAvailableBooksToFile:
                    DataManager.exportAvailableBooksToFile();
                    break;

            }
        }
    }
}
