package src;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private String email;
    private ArrayList<Book> borrowedBookList = new ArrayList<>();

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBorrowedBookList() {
        return borrowedBookList;
    }

    public void addBorrowedBook(Book book) {
        if (book.isAvailable()) {
            borrowedBookList.add(book);
            book.setAvailable(false);
            System.out.println("Muon sach thanh cong");
        } else {
            System.out.println("Sach khong co san");
        }
    }

    public void removeBorrowedBook(String bookId) {
        for (Book book : borrowedBookList) {
            if (book.getId().equals(book.getId())) {
                borrowedBookList.remove(book);
                book.setAvailable(true);
                System.out.println("Tra sach thanh cong");
                return;
            }
        }
        System.out.println("Khong co sach de tra");
    }

    @Override
    public String toString() {
        return "user{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", borrowedBookList=" + borrowedBookList +
                '}';
    }
}
