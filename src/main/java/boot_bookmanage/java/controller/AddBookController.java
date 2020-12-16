package boot_bookmanage.java.controller;

import boot_bookmanage.java.entities.Book;
import boot_bookmanage.java.service.BookService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Zenglr
 * @ClassName
 * @Description 不再使用 嵌入到BookController
 * @create 2020-11-25-3:40 下午
 */
//@FXMLController
//public class AddBookController{
//
//    private ObservableList<Book> bookData = FXCollections.observableArrayList();
//
//    public ObservableList<Book> getBookData() {
//        return bookData;
//    }
//
//    public void setBookData(ObservableList<Book> bookData) {
//        this.bookData = bookData;
//    }
//
//    @FXML
//    private TextField bookName, bookAuthor, bookPrice, bookType, bookStock;
//
//    @Autowired
//    BookService bookService;
//
//
//    public void addBook() {
//        String name = bookName.getText();
//        String author = bookAuthor.getText();
//        String price = bookPrice.getText();
//        String type = bookType.getText();
//        String stock = bookStock.getText();
//        System.out.println(stock);
//        Book book = new Book();
//        book.setBname(name);
//        book.setAuthor(author);
//        book.setType(type);
//        book.setPrice(Double.parseDouble(price));
//        book.setStock(Integer.parseInt(stock));
//        int id = bookService.addBook(book);
//        book.setBid(id);
//        this.getBookData().add(book);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("提示信息");
//        alert.setHeaderText("新增图书成功!");
//        alert.showAndWait();
//        Stage stage = (Stage) bookName.getScene().getWindow();
//        stage.close();
//    }
//}
