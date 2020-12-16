package boot_bookmanage.java.service.impl;

import boot_bookmanage.java.dao.BookDao;
import boot_bookmanage.java.dao.BorrowDao;
import boot_bookmanage.java.dao.ReaderDao;
import boot_bookmanage.java.entities.Book;
import boot_bookmanage.java.entities.Borrow;
import boot_bookmanage.java.entities.Reader;
import boot_bookmanage.java.service.BorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Zenglr
 * @program: boot_bookManagement
 * @packagename: boot_bookmanage.java.service.impl
 * @Description 借阅信息业务层实现
 * @create 2020-12-06-9:45 下午
 */
@Service
@Slf4j
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    BorrowDao borrowDao;
    @Autowired
    ReaderDao readerDao;
    @Autowired
    BookDao bookDao;

    @Override
    public int addBorrow(Borrow borrow){
        int val = 0;
        String rname = borrow.getName();
        String bname = borrow.getBname();
        int rid = 0;
        rid = readerDao.getRidByName(rname);
        log.info(String.valueOf(rid));
        int bid = bookDao.getBidByName(bname);
        log.info(String.valueOf(bid));
        val = borrowDao.insertBorrow(rid,bid);
        log.info(String.valueOf(val));
        bookDao.updateStock(bid);
        //        bookDao.updateStock(bid);
        return val;
    }

    @Override
    public void deleteBorrow(int id) {
        try {
            bookDao.returnStock(borrowDao.getBidById(id));
            borrowDao.deleteBorrowById(id);
        } catch (SQLException e) {
            log.error("归还图书出现异常");
        }
    }

    @Override
    public List<Borrow> getAllBorrows() {
        List<Borrow> borrowList;
        borrowList = borrowDao.selectAllBorrows();
        log.info("所有："+borrowList.toString());
        return borrowList;
    }

    @Override
    public List<Borrow> getBorrowsLike(String keywords) {
        List<Borrow> borrowList;
        if(keywords != null){
            borrowList = borrowDao.selectBorrowsLikeB(keywords);
        }else {
            borrowList = borrowDao.selectAllBorrows();
        }
        log.info("根据图书名"+keywords+"模糊查询"+borrowList.toString());
        return borrowList;
    }

    @Override
    public List<Borrow> getBorrowsByName(String rname) {
        List<Borrow> borrowList;
        if(rname != null){
            borrowList = borrowDao.selectBorrowsByRname(rname);
        }else {
            borrowList = borrowDao.selectAllBorrows();
        }
        log.info("根据读者名"+rname+"查询："+borrowList.toString());
        return borrowList;
    }

    @Override
    public Borrow getBorrow(int id) {
        Borrow borrow = borrowDao.getBorrowById(id);
        log.info("根据id"+id+"查询出借阅信息:"+borrow.toString());
        return borrow;
    }

    @Override
    public List<String> getAllReaders() {
        List<String> reader = new ArrayList<>();
        List<Borrow> borrowList = borrowDao.getAllRname();//获取到所有有借阅信息的reader
        for(int i = 0; i < borrowList.size(); i++){
            reader.add(i,borrowList.get(i).getName());
        }
        log.info("获取到所有有借阅信息的reader名字"+reader.toString());
        return reader;
    }

    @Override
    public List<String> getAllReadersName() {
        List<String> reader = new ArrayList<>();
        List<Reader> readerList = readerDao.getAllName();
        for(int i = 0; i < readerList.size(); i++){
            reader.add(i,readerList.get(i).getName());
        }
        log.info("获取到所有的reader名字"+reader.toString());
        return reader;
    }

    @Override
    public List<String> getAllBooksName() {
        List<String> book = new ArrayList<>();
        List<Book> bookList;
        bookList = bookDao.selectAllStockBook();
        for(int i = 0; i < bookList.size(); i++){
            book.add(i,bookList.get(i).getBname());
        }
        log.info("获取到所有的book名字"+book.toString());
        return book;
    }

    @Override
    public String getDateById(int id) {
        String date = borrowDao.getDateById(id);
        log.info(date);
        return date;
    }

    @Override
    public int countByReader(String rname) {
        int rid = readerDao.getRidByName(rname);
        log.info("根据读者名称"+rname+"获取到rid："+rid);
        int count = borrowDao.countByRid(rid);
        log.info("根据读者名称"+rname+"统计借阅数量"+count);
        return count;
    }

    @Override
    public int countByBook(String bname) {
        int bid = bookDao.getBidByName(bname);
        log.info("根据图书名称"+bname+"获取到bid："+bid);
        int count = borrowDao.countByBid(bid);
        log.info("根据图书名称"+bname+"统计借阅数量"+count);
        return count;
    }


}
