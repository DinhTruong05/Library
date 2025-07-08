import src.Manager.dataManager;

import java.util.Scanner;

import static src.IChoice.Choice.*;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        dataManager.loadFromFile();
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

            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == exit) {
                System.out.println(" Đang lưu dữ liệu");
                dataManager.saveToFile();
                System.out.println(" Bye");

                break;
            }
            switch (choice) {
                case registerUser:
                    dataManager.registerUser();
                    break;
                case addBook:
                    dataManager.addBook();
                    break;
                case removeBook:
                    dataManager.removeBook();
                    break;
                case updateBook:
                    dataManager.updateBook();
                    break;
                case searchBook:
                    dataManager.searchBook();
                    break;
                case borrowBook:
                    dataManager.borrowBook();
                    break;
                case returnedBook:
                    dataManager.returnedBook();
                    break;
                case displayBookList:
                    dataManager.displayBookList();
                    break;
                case showStatistics:
                    dataManager.showStatistics();
                    break;

            }
        }
    }
}
