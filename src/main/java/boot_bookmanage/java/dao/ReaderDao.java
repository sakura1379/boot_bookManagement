package boot_bookmanage.java.dao;

import boot_bookmanage.java.entities.Borrow;
import boot_bookmanage.java.entities.Reader;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
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
 * @create 2020-11-23-7:38 下午
 */
@Repository
@Slf4j
public class ReaderDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSource dataSource;

    /**
     * 查询所有读者信息
     *
     * @return List<Reader>
     */
    public List<Reader> selectReaders() {
//        Connection conn = dataSource.getConnection();
        String sql = "select * from reader";
        List<Reader> readerList;
        readerList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Reader.class));
//        readerList = BaseDao.select(Reader.class,conn,sql);
        return readerList;
    }
    /**
     * 根据id删除实体
     *
     * @param id
     * @return
     */
    public void deleteById(int id) {
//        Connection conn = dataSource.getConnection();
        String sql = "delete from reader where rid = ?";
        jdbcTemplate.update(sql);
//        BaseDao.update(conn,sql,id);
    }

    /**
     * 新增一个读者
     *
     * @param reader
     * @return
     */
    public int insertReader(Reader reader) {
//        Connection conn = dataSource.getConnection();
        String sql = "insert into reader(name,role,department,email,mobile) values (?,?,?,?,?)";
        int id = jdbcTemplate.update(sql,reader.getName(),reader.getRole(),reader.getDepartment(),reader.getEmail(),reader.getMobile());
        return id;
//        BaseDao.update(conn,sql,args);
    }

    /**
     * 根据身份角色统计读者数量
     *
     * @param role
     * @return
     */
    public int countByRole(String role) {
//        Connection conn = dataSource.getConnection();
        String sql = "select count(*) from reader where role = ?";
        Integer val = jdbcTemplate.queryForObject(sql,Integer.class,role);
        if(val != null){
            return val;
        }else {
            return 0;
        }
    }

    /**
     * 根据院系统计读者数量
     *
     * @param department
     * @return
     */
    public int countByDepartment(String department) {
//        Connection conn = dataSource.getConnection();
        String sql = "select count(*) from reader where department = ?";
//        long val;
//        val = BaseDao.getValue(conn,sql,department);
        Integer val = jdbcTemplate.queryForObject(sql,Integer.class,department);
        if(val != null){
            return val;
        }else {
            return 0;
        }
    }

    /**
     * 统计读者总数
     *
     * @return
     */
    public int countReaders() {
//        Connection conn = dataSource.getConnection();
        String sql = "select count(*) from reader";
        Integer val = jdbcTemplate.queryForObject(sql,Integer.class);
//        val = BaseDao.getValue(conn,sql);
        if(val != null){
            return val;
        }else {
            return 0;
        }
    }

    /**
     * 根据读者名字返回读者id
     * @param rname
     * @return
     */
    public int getRidByName(String rname) {
//        Connection conn = dataSource.getConnection();
        String sql = "select rid from reader where name = ?";
        Integer val;
//        val = BaseDao.getValue(conn,sql,rname);
        val = jdbcTemplate.queryForObject(sql,Integer.class,rname);
        return val;
    }

//    /**
//     * 根据rid获取到所有读者姓名
//     * @param rid
//     * @return
//     * @throws SQLException
//     */
//   public List<String> getAllNameByRid(List<String> rid) throws SQLException {
//       Connection conn = dataSource.getConnection();
//       String sql = "select name from reader where rid = ?";
//       List<String> nameList;
//       nameList = BaseDao.getValue(conn,sql,rid);
//       return nameList;
//   }

    /**
     * 获取到所有的读者姓名
     * @return
     */
    public List<Reader> getAllName() {
//        Connection conn = dataSource.getConnection();
        String sql = "select name from reader";
        List<Reader> nameList;
//        nameList = BaseDao.select(Reader.class,conn,sql);
        nameList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Reader.class));
        return nameList;
    }

}
