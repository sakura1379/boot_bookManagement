package boot_bookmanage.java.service.impl;

import boot_bookmanage.java.dao.AdminDao;
import boot_bookmanage.java.entities.Admin;
import boot_bookmanage.java.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-21-7:25 下午
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
//    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    AdminDao adminDao;
//    @Autowired
//    AdminMapper adminMapper;


    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> adminList;
        adminList = adminDao.selectAdmins();
        log.info("查询所有管理员:"+adminList.toString());
        return adminList;
    }


    @Override
    public boolean login(String account, String password) {
        boolean flag = false;
        List<Admin> admin;
        admin = adminDao.getAdminByAccountAndPw(account,password);
        log.info(admin.toString());
//        admin = adminMapper.getAdminByAccoutAndPw(account,password);
        if (admin.size()>0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public Admin getAdminByAccount(String account) {
        List<Admin> admin = adminDao.getAdminByAccount(account);
        log.info("根据管理员名字："+admin.get(0).toString());
        return admin.get(0);
    }

    @Override
    public void setAdmin(Admin admin) {
        adminDao.setAdmin(admin);
        log.info(admin.toString());
    }

    @Override
    public void deleteAdmin(String account){
        adminDao.deleteByAccount(account);
    }

    @Override
    public void updateAdmin(Admin admin) {
        adminDao.updateAdmin(admin);
    }

}
