package boot_bookmanage.java.service;

import boot_bookmanage.java.entities.Reader;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-22-10:21 下午
 */
public interface ReaderService {
    /**
     * 查询所有读者信息
     * @return List<Reader>
     */
    List<Reader> getAllReaders();

    /**
     * 查询所有读者名字
     * @return
     */
    List<String> getAllReadersName();

    /**
     * 根据id删除读者
     * @param id
     */
    void deleteReader(int id);

    /**
     * 新增一个读者，返回自增主键
     * @param reader
     * @return
     */
    int addReader(Reader reader);

    /**
     * 根据身份角色统计读者数量
     * @param role
     * @return
     */
    int countByRole(String role);

    /**
     * 根据身份角色统计读者数量
     *
     * @param department
     * @return
     */
    int countByDepartment(String department);
}
