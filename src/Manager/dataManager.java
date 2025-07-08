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
        System.out.println("Nhập id sách");
        String id = scanner.nextLine();
        System.out.println("Nhập tên sách");
        String title = scanner.nextLine();
        System.out.println("Nhập tác giả");
        String author = scanner.nextLine();
        System.out.println("Nhập loại sách");
        String category = scanner.nextLine();
        System.out.println("Nhập trạng thái sách");
        boolean available = scanner.nextLine().equals("true");
        Book book = new Book(id, title, author, category, available);
        bookList.add(book);
        System.out.println("Nhập sách thành công ");
    }

    public static void removeBook() {
        System.out.println("Nhập Id sách cần xóa");
        String id = scanner.nextLine();
        Book book = findBook(id);
        if (book == null) {
            System.out.println("Id sai hoặc sách không tồn tại ");
            return;
        }
        bookList.remove(book);
        System.out.println("Xoa thanh cong");

    }

    public static void updateBook() {
        System.out.println(" Nhập Id sách cần update");
        String id = scanner.nextLine();
        Book book = findBook(id);
        if (book == null) {
            System.out.println("Id sai hoặc sách không tồn tại  ");
            return;
        }


        System.out.println(" Tiêu đề mới : ");
        book.setTitle(scanner.nextLine());
        System.out.println(" Tác giả mới : ");
        book.setAuthor(scanner.nextLine());
        System.out.println(" Thể loại mới : ");
        book.setCategory(scanner.nextLine());

        System.out.println(" Update thành công");

    }

    public static void searchBook() {
        System.out.println(" Nhập tiêu đề hoặc tên tác giả : ");
        String title = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(title.toLowerCase())) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) System.out.println(" Không tìm thấy sách phù hợp!!");

    }

    public static void registerUser() {
        System.out.print("ID người dùng: ");
        String id = scanner.nextLine();
        System.out.print("Tên: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        userList.add(new User(id, name, email));
        System.out.println("Đăng ký thành công.");
    }

    public static void borrowBook() {
        System.out.println(" Nhập Id người dùng : ");
        String userId = scanner.nextLine();
        User user = findUser(userId);
        if (user == null) return;

        System.out.println(" Nhập Id sách muốn mượn : ");
        String bookId = scanner.nextLine();
        Book book = findBook(bookId);
        if (book == null) return;

        user.addBorrowedBook(book);
    }


    public static void returnedBook() {
        System.out.println("Id người dùng: ");
        String userId = scanner.nextLine();
        User user = findUser(userId);
        if (user == null) return;

        System.out.println("Id sách: ");
        String bookId = scanner.nextLine();
        user.removeBorrowedBook(bookId);
    }

    public static void displayBookList() {
        System.out.println("\nDANH SÁCH SÁCH:");
        for (Book book : bookList) {
            System.out.println(book);
        }

        System.out.println("\nDANH SÁCH USER:");
        for (User user : userList) {
            System.out.println(user);
        }
    }

    public static void showStatistics() {
        long borrowed = bookList.stream().filter(b -> !b.isAvailable()).count();
        System.out.println("\nThống kê");
        System.out.println("Tổng số sách: " + bookList.size());
        System.out.println(" Số sách đã được mượn: " + borrowed);
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
            System.out.println("Lỗi ghi file sách");
        }

        try (PrintWriter bw = new PrintWriter("file/user.txt")) {
            for (User u : userList) {
                bw.println(u.getId() + ";"
                        + u.getName() + ";"
                        + u.getEmail());
            }
        } catch (Exception e) {
            System.out.println("Lỗi ghi file user");
        }
        System.out.println(" Đã lưu dữ liệu ");
    }


    public static User findUser(String id) {
        for (User user : userList) {
            if (user.getId().equals(id)) return user;
        }
        System.out.println(" Không tìm thấy User !");
        return null;
    }

    public static Book findBook(String id) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) return book;
        }
        System.out.println(" Không tìm thây sách !");
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
            System.out.println("Không thể đọc file sách: " + e.getMessage());
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
            System.out.println(" Không thể đoc file user: " + e.getMessage());
        }

        System.out.println("Đã load dữ liệu từ file.");
    }


}
