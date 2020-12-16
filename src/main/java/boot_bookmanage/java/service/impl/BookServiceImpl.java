package boot_bookmanage.java.service.impl;

import boot_bookmanage.java.dao.BookDao;
import boot_bookmanage.java.entities.Book;
import boot_bookmanage.java.entities.Reader;
import boot_bookmanage.java.service.BookService;
import boot_bookmanage.java.service.ReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-25-3:32 下午
 */
@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    BookDao bookDao;

    @Override
    public int addBook(Book book) {
        int val = 0;
        try {
            val = bookDao.insertBook(book);
        } catch (SQLException e) {
            log.error("新增图书出现异常");
        }
        log.info("增加图书返回id："+val);
        return val;
    }

    @Override
    public void deleteBook(int id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> bookList;
        bookList = bookDao.selectAllBooks();
        log.info("所有："+bookList.toString());
        return bookList;
    }

    @Override
    public List<String> getAllBooksName() {
        List<String> Book = new ArrayList<>();
        List<Book> bookList;
        bookList = bookDao.getAllBname();
        for(int i = 0; i < bookList.size(); i++){
            Book.add(i,bookList.get(i).getBname());
        }
        return Book;
    }

    @Override
    public Book getBook(int id) {
        Book book = new Book();
        book = bookDao.getBookById(id);
        log.info("根据id"+id+"查询出book:"+book.toString());
        return book;
    }

    @Override
    public List<Book> getBooksLike(String keywords) {
        List<Book> bookList;
        if(keywords != null){
            bookList = bookDao.selectBooksLike(keywords);
        }else {
            bookList = bookDao.selectAllBooks();
        }
        log.info("根据篇名"+keywords+"模糊查询"+bookList.toString());
        return bookList;
    }

    @Override
    public List<Book> getBooksByType(String type) {
        List<Book> bookList;
        if(type != null){
            bookList = bookDao.selectBooksByTypeId(type);
        }else {
            bookList = bookDao.selectAllBooks();
        }
        log.info("根据类别"+type+"查询："+bookList);
        return bookList;
    }

    @Override
    public int countByType(String type) {
        int result;
        result = bookDao.countByType(type);
        log.info("根据类别"+type+"统计图书信息:"+result);
        return result;
    }
}
