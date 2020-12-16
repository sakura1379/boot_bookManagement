package boot_bookmanage.java.dao;

import boot_bookmanage.java.entities.Admin;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * TODO 使用jdbcTemplate
 * @create 2020-11-21-7:29 下午
 */
@Repository
@Slf4j
public class AdminDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSource dataSource;


    public List<Admin> selectAdmins() {
//        Connection conn = dataSource.getConnection();
        List<Admin> adminList;
        String sql = "select * from admin";
//        adminList = BaseDao.select(Admin.class,conn,sql);
        adminList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Admin.class));
        if(adminList!=null && adminList.size()>0){
            return adminList;
        }else{
            return null;
        }
    }


    public List<Admin> getAdminByAccountAndPw(String account, String password) {
//        Connection conn = dataSource.getConnection();
        String sql = "select * from admin where account = ? and password = ?";
        List<Admin> adminList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Admin.class),account,password);
        return adminList;
    }


    public List<Admin> getAdminByAccount(String account) {
//        Connection conn = dataSource.getConnection();
        String sql = "select * from admin where account = ?";
        List<Admin> adminList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Admin.class),account);
        return adminList;
    }

    public int setAdmin(Admin admin) {
//        Connection conn = dataSource.getConnection();
        String sql = "insert into admin(account,password) values (?,?)";
//        BaseDao.update(conn,sql,admin.getAccount(),admin.getPassword());
        return jdbcTemplate.update(sql,admin.getAccount(),admin.getPassword());
    }

    public void deleteByAccount(String account){
        String sql = "delete from admin where account = ?";
        jdbcTemplate.update(sql,account);
    }

    public void updateAdmin(Admin admin){
        String sql = "update admin set password = ? where account = ?";
        jdbcTemplate.update(sql,admin.getPassword(),admin.getAccount());
    }
}
