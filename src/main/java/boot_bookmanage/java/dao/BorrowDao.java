package boot_bookmanage.java.dao;

import boot_bookmanage.java.entities.Book;
import boot_bookmanage.java.entities.Borrow;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.record.BOFRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sun.jvm.hotspot.utilities.Interval;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Zenglr
 * @program: boot_bookManagement
 * @packagename: boot_bookmanage.java.dao
 * @Description 借阅的数据库实现
 * @create 2020-12-06-7:32 下午
 */
@Repository
@Slf4j
public class BorrowDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSource dataSource;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 根据rid、bid插入新的借阅信息到借阅表里
     * @param rid
     * @param bid
     * @return id
     * TODO 原则上不允许读者借阅两本一样的书，所以可以通过bidrid同时查找唯一的一个id，但未设置限制借阅
     */
    public int insertBorrow(int rid, int bid) {
//        Connection conn = dataSource.getConnection();
        String sql = "insert into borrow(bid,rid,borrow_date) values (?,?,?)";
        jdbcTemplate.update(sql,bid,rid,df.format(new Date()));
//        BaseDao.update(conn,sql,bid,rid,df.format(new Date()));
        String sql_select = "select id from borrow where rid = ? and bid = ?";
        Integer val;
        val = jdbcTemplate.queryForObject(sql_select,Integer.class,rid,bid);
//        val = BaseDao.getValue(conn,sql_select,rid,bid);
        log.info(String.valueOf(val));
        if(val != null){
            return val;
        }else {
            return 0;
        }
    }

    /**
     * 根据id归还图书
     *
     * @param id
     * @return
     */
    public void deleteBorrowById(int id) throws SQLException {
//        Connection conn = dataSource.getConnection();
        String sql = "delete from borrow where id = ?";
        jdbcTemplate.update(sql,id);
//        BaseDao.update(conn,sql,id);
    }

    /**
     * 根据id获取到bid
     * @param id
     * @return bid
     */
    public int getBidById(int id) {
//        Connection conn = dataSource.getConnection();
        String sql = "select bid from borrow where id = ?";
        Integer val = jdbcTemplate.queryForObject(sql,Integer.class,id);
        if(val != null){
            return val;
        }else {
            return 0;
        }
    }

    /**
     * 查询所有借阅信息
     *
     * @return
     */
    public List<Borrow> selectAllBorrows() {
//        Connection conn = dataSource.getConnection();
        String sql = "select br.id,b.bname,r.name,br.borrow_date from borrow br,reader r,book b where br.bid = b.bid and br.rid = r.rid";
        List<Borrow> borrowList;
        borrowList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Borrow.class));
//        borrowList = BaseDao.select(Borrow.class,conn,sql);
        return borrowList;
    }

    /**
     * 根据id查询借阅信息
     *
     * @param id
     * @return
     */
    public Borrow getBorrowById(long id) {
//        Connection conn = dataSource.getConnection();
        String sql = "select br.id,b.bname,r.name,br.borrow_date from borrow br,reader r,book b where br.bid = b.bid and br.rid = r.rid and br.id = ?";
        Borrow borrow = jdbcTemplate.queryForObject(sql,Borrow.class,id);
//        borrowList = BaseDao.select(Borrow.class,conn,sql,id);
        return borrow;
    }

    /**
     * 根据读者名关键词查询图书
     *
     * @param rnameKeywords
     * @return
     */
    public List<Borrow> selectBorrowsByRname(String rnameKeywords) {
//        Connection conn = dataSource.getConnection();
        String sql = "select br.id,b.bname,r.name,br.borrow_date from borrow br,reader r,book b where br.bid = b.bid and br.rid = r.rid and name = ?";
        List<Borrow> borrowList;
        borrowList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Borrow.class),rnameKeywords);
//        borrowList = BaseDao.select(Borrow.class,conn,sql,rnameKeywords);
        return borrowList;
    }

    /**
     * 根据图书名关键词模糊查询图书
     *
     * @param bnameKeywords
     * @return
     */
    public List<Borrow> selectBorrowsLikeB(String bnameKeywords) {
//        Connection conn = dataSource.getConnection();
        String sql = "select br.id,b.bname,r.name,br.borrow_date from borrow br,reader r,book b where br.bid = b.bid and br.rid = r.rid and b.bname like ?";
        String keyword = "%" + bnameKeywords + "%";
        List<Borrow> borrowList;
        borrowList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Borrow.class),keyword);
//        borrowList = BaseDao.select(Borrow.class,conn,sql,keyword);
        return borrowList;
    }

    /**
     * 获取到所有有借阅信息的reader
     * @return
     */
    public List<Borrow> getAllRname() {
//        Connection conn = dataSource.getConnection();
        String sql = "select distinct name from borrow,reader where borrow.rid = reader.rid;";
        List<Borrow> nameList;
        nameList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Borrow.class));
//        nameList = BaseDao.select(Borrow.class,conn,sql);
        return nameList;
    }

    /**
     * 根据id获取到日期
     * @param id
     * @return
     */
    public String getDateById(int id) {
//        Connection conn = dataSource.getConnection();
        String sql = "select borrow_date from borrow where id = ?";
        String borrow_date = jdbcTemplate.queryForObject(sql,String.class,id);
//        borrow_date = BaseDao.getValue(conn,sql,id);
        return borrow_date;
    }

    /**
     * 根据rid获取到借阅数量
     * @param rid
     * @return
     */
    public int countByRid(int rid){
        String sql = "select count(*) from borrow where rid = ?";
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,rid);
        if(count != null){
            return count;
        }else {
            return 0;
        }
    }

    /**
     * 根据bid获取到借阅数量
     * @param bid
     * @return
     */
    public int countByBid(int bid){
        String sql = "select count(*) from borrow where bid = ?";
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,bid);
        if(count != null){
            return count;
        }else {
            return 0;
        }
    }
}
