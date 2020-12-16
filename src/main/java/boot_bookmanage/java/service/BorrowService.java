package boot_bookmanage.java.service;

import boot_bookmanage.java.entities.Book;
import boot_bookmanage.java.entities.Borrow;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author Zenglr
 * @program: boot_bookManagement
 * @packagename: boot_bookmanage.java.service
 * @Description 借阅业务层接口
 * @create 2020-12-06-9:41 下午
 */
public interface BorrowService {
    /**
     * 新增借阅
     * @param borrow
     * @return 是否成功
     */
    int addBorrow(Borrow borrow);

    /**
     * 根据id归还图书
     * @param id
     * @return
     */
    void deleteBorrow(int id);

    /**
     * 获取所有借阅信息
     * @return List<Borrow>
     */
    List<Borrow> getAllBorrows();

    /**
     * 根据书名关键词模糊查询借阅信息
     *
     * @param keywords
     * @return List<Borrow>
     */
    List<Borrow> getBorrowsLike(String keywords);

    /**
     * 根据读者姓名查询借阅信息
     *
     * @param rname
     * @return List<Borrow>
     */
    List<Borrow> getBorrowsByName(String rname);

    /**
     * 根据id查询借阅信息
     * @param id
     * @return
     */
    Borrow getBorrow(int id);

    /**
     * 获取到所有已借阅图书的读者名字
     * @return
     */
    List<String> getAllReaders();

    /**
     * 获取到所有读者名字
     * @return
     */
    List<String> getAllReadersName();

    /**
     * 获取到所有图书名字
     * @return
     */
    List<String> getAllBooksName();

    /**
     * 根据id获取到日期
     * @return
     */
    String getDateById(int id);

    /**
     * 根据读者名字统计借阅数量
     * @param rname
     * @return
     */
    int countByReader(String rname);

    /**
     * 根据图书名字统计借阅数量
     * @param bname
     * @return
     */
    int countByBook(String bname);
}
