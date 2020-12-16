package boot_bookmanage.java.dao;

import boot_bookmanage.java.entities.Admin;
import boot_bookmanage.java.entities.Book;
import boot_bookmanage.java.entities.Reader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * TODO 使用jdbcTemplate
 * @create 2020-11-25-3:01 下午
 */
@Repository
@Slf4j
public class BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSource dataSource;

    /**
     * 新增图书，返回自增主键
     *
     * @param book
     * @return
     * @throws SQLException
     */
    public int insertBook(Book book) throws SQLException {
//        Connection conn = dataSource.getConnection();
        String sql = "insert into book(bname,author,type,price,stock) values (?,?,?,?,?)";
//        BaseDao.update(conn,sql,args);
        int val = jdbcTemplate.update(sql,book.getBname(),book.getAuthor(),book.getType(),book.getPrice(),book.getStock());
//        if(val != null){
//            return val;
//        }else {
//            return 0;
//        }
        return val;
    }

    /**
     * 根据id删除图书
     *
     * @param id
     * @return
     */
    public void deleteBookById(int id) {
//        Connection conn = dataSource.getConnection();
        String sql = "delete from book where bid = ?";
        jdbcTemplate.update(sql,id);
//        BaseDao.update(conn,sql,id);
    }

    /**
     * 更新图书信息,更新价格
     *
     * @param book
     * @return
     */
    public void updateBook(Book book) {
//        Connection conn = dataSource.getConnection();
        String sql = "update book set price = ? where bid = ?";
        jdbcTemplate.update(sql,book.getPrice(),book.getBid());
//        BaseDao.update(conn,sql,args);
    }

    /**
     * 根据bid归还图书
     * @param bid
     */
    public void returnStock(int bid) {
//        Connection conn = dataSource.getConnection();
        String sql = "update book set stock = stock+1 where bid = ?";
        jdbcTemplate.update(sql,bid);
//        BaseDao.update(conn,sql,bid);
    }

    /**
     * 根据bid借阅图书
     * @param bid
     */
    public void updateStock(int bid) {
//        Connection conn = dataSource.getConnection();
        String sql = "update book set stock = stock-1 where bid = ?";
        jdbcTemplate.update(sql,bid);
//        BaseDao.update(conn,sql,bid);
    }

    /**
     * 查询所有图书
     * @return
     */
    public List<Book> selectAllBooks() {
//        Connection conn = dataSource.getConnection();
        String sql = "select * from book";
        List<Book> bookList;
        bookList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Book.class));
//        bookList = BaseDao.select(Book.class,conn,sql);
        return bookList;
    }

    /**
     * 查询所有库存大于0的图书，用于借阅
     * @return
     */
    public List<Book> selectAllStockBook() {
//        Connection conn = dataSource.getConnection();
        String sql = "select * from book where stock > 0";
        List<Book> bookList;
        bookList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Book.class));
//        bookList = BaseDao.select(Book.class,conn,sql);
        return bookList;
    }


    /**
     * 根据id查询图书信息
     *
     * @param id
     * @return
     */
    public Book getBookById(long id) {
//        Connection conn = dataSource.getConnection();
        String sql = "select * from book where bid = ?";
//        List<Book> bookList = new ArrayList<>();
//        bookList = BaseDao.select(Book.class,conn,sql,id);
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Book.class),id);
    }

    /**
     * 根据图书名字返回图书id
     * @param bname
     * @return
     */
    public int getBidByName(String bname) {
//        Connection conn = dataSource.getConnection();
        String sql = "select bid from book where bname = ?";
        Integer val;
//        val = BaseDao.getValue(conn,sql,bname);
        val = jdbcTemplate.queryForObject(sql,Integer.class,bname);
        return val;
    }

    /**
     * 根据书名关键词模糊查询图书
     *
     * @param keywords
     * @return
     */
    public List<Book> selectBooksLike(String keywords) {
//        Connection conn = dataSource.getConnection();
        String sql = "select * from book where bname like ?";
        String keyword = "%" + keywords + "%";
        List<Book> bookList;
        bookList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Book.class),keyword);
//        bookList = BaseDao.select(Book.class,conn,sql,keyword);
        return bookList;
    }

    /**
     * 根据图书类别查询图书
     *
     * @param type
     * @return
     */
    public List<Book> selectBooksByTypeId(String type) {
//        Connection conn = dataSource.getConnection();
        String sql = "select * from book where type = ?";
        List<Book> bookList;
        bookList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Book.class),type);
//        bookList = BaseDao.select(Book.class,conn,sql,type);
        return bookList;
    }

    /**
     * 根据图书类别统计图书数量
     *
     * @param type
     * @return
     */
    public int countByType(String type) {
//        Connection conn = dataSource.getConnection();
        String sql = "select count(*) from book where type = ?";
        Integer val = jdbcTemplate.queryForObject(sql,Integer.class,type);
        if(val != null){
            return val;
        }else {
            return 0;
        }
    }

    /**
     * 统计图书总数
     *
     * @return
     */
    public int countBooks() {
//        Connection conn = dataSource.getConnection();
        String sql = "select count(*) from book";
        Integer val = jdbcTemplate.queryForObject(sql,Integer.class);
        if(val != null){
            return val;
        }else {
            return 0;
        }
    }

    /**
     * 获取到所有的图书名称
     * @return
     */
    public List<Book> getAllBname() {
//        Connection conn = dataSource.getConnection();
        String sql = "select bname from book";
        List<Book> nameList;
        nameList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Book.class));
//        nameList = BaseDao.select(Book.class,conn,sql);
        return nameList;
    }
}
