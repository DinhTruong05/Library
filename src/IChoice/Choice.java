package src.IChoice;

public interface Choice {
    int registerUser = 1;
    int addBook = 2;
    int removeBook = 3;
    int updateBook = 4;
    int searchBook = 5;
    int borrowBook = 6;
    int returnedBook = 7;
    int displayBookList = 8;
    int showStatistics = 9;
    int exportBorrowBookToFile = 10;
    int exportAvailableBooksToFile = 11;
    int exit = 0;

}
