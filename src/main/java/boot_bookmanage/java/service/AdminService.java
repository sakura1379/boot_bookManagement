package boot_bookmanage.java.service;

import boot_bookmanage.java.entities.Admin;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-21-7:25 下午
 */
public interface AdminService {
    /**
     * 查询所有管理员
     * @return List<Admin>
     */
    List<Admin> getAllAdmins();


    /**
     * 管理员登录
     * @param account
     * @param password
     * @return boolean
     */
    boolean login(String account,String password) throws SQLException;

    /**
     * 根据账号查询管理员信息
     * @param account
     * @return amdin
     */
    Admin getAdminByAccount(String account) throws SQLException;

    /**
     * 新增管理员
     * @param admin
     */
    void setAdmin(Admin admin);

    /**
     * 根据账号删除管理员
     * @param account
     */
    void deleteAdmin(String account);

    /**
     * 更改密码
     * @param admin
     */
    void updateAdmin(Admin admin);
}
