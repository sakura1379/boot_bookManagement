package boot_bookmanage.java.service.impl;

import boot_bookmanage.java.dao.ReaderDao;
import boot_bookmanage.java.entities.Borrow;
import boot_bookmanage.java.entities.Reader;
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
 * @create 2020-11-23-7:34 下午
 */
@Service
@Slf4j
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    ReaderDao readerDao;

    @Override
    public List<Reader> getAllReaders() {
        List<Reader> readerList = readerDao.selectReaders();
        log.info("查询所有读者信息"+readerList.toString());
        return readerList;
    }

    @Override
    public List<String> getAllReadersName() {
        List<String> reader = new ArrayList<>();
        List<Reader> readerList = readerDao.getAllName();
        for(int i = 0; i < readerList.size(); i++){
            reader.add(i,readerList.get(i).getName());
        }
        log.info("获得所有读者名："+reader.toString());
        return reader;
    }

    @Override
    public void deleteReader(int id) {
        readerDao.deleteById(id);
    }

    @Override
    public int addReader(Reader reader) {
        int id =readerDao.insertReader(reader);
        log.info("新增读者返回id:"+id);
        return id;
    }

    @Override
    public int countByRole(String role) {
        int result = readerDao.countByRole(role);
        log.info("根据角色"+role+"统计读者信息:"+result);
        return result;
    }

    @Override
    public int countByDepartment(String department) {
        int result = readerDao.countByDepartment(department);
        log.info("根据部门"+department+"统计读者信息:"+result);
        return result;
    }
}

