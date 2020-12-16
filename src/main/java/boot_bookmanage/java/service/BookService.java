package boot_bookmanage.java.service;

import boot_bookmanage.java.entities.Book;

import java.util.List;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-25-2:59 下午
 */
public interface BookService {
    /**
     * 新增图书
     * @param book
     * @return
     */
    int addBook(Book book);

    /**
     * 根据id删除图书
     * @param id
     * @return
     */
    void deleteBook(int id);

    /**
     * 更新图书信息
     * @param book
     * @return
     */
    void updateBook(Book book);


    /**
     * 查询所有图书
     * @return List<Book>
     */
    List<Book> getAllBooks();

    /**
     * 查询所有图书名
     * @return
     */
    List<String> getAllBooksName();


    /**
     * 根据id查询图书信息
     *
     * @param id
     * @return Book
     */
    Book getBook(int id);

    /**
     * 根据书名关键词模糊查询图书
     *
     * @param keywords
     * @return List<Book>
     */
    List<Book> getBooksLike(String keywords);

    /**
     * 根据图书类别查询图书
     *
     * @param type
     * @return List<Book>
     */
    List<Book> getBooksByType(String type);

    /**
     * 根据图书类别统计图书数量
     *
     * @param type
     * @return
     */
    int countByType(String type);
}
