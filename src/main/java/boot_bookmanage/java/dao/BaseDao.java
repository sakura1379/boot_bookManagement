package boot_bookmanage.java.dao;

import boot_bookmanage.java.service.impl.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zenglr
 * @ClassName
 * @Description 不再用
 * @create 2020-11-21-1:42 下午
 */
@Component
@Slf4j
public class BaseDao {
//    private Logger logger = LoggerFactory.getLogger(BaseDao.class);
    public static void update(Connection conn,String sql,Object...args){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            log.info(ps.toString());
            ps.execute();
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            jdbcUtils.closeResource(null,ps);
        }
    }

    public static <T> List<T> select(Class<T> clazz, Connection conn, String sql, Object... args) {
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<T> list=new ArrayList<T>();
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            log.info(ps.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                T t = clazz.newInstance();
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    Object columnVal = rs.getObject(i + 1);
                    String columName = rsmd.getColumnName(i + 1);
                    Field field = clazz.getDeclaredField(columName);
                    if (field != null) {
                        field.setAccessible(true);
                        field.set(t, columnVal);
                    }
                }
                list.add(t);
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException | InstantiationException e){
            e.printStackTrace();
        } finally {
            jdbcUtils.closeResource(null,ps,rs);
        }
        return list;
    }

//    public static <T> JSONArray findJSONArray(Class<T> clazz, Connection conn,String sql, Object... args)
//    {
//        PreparedStatement ps=null;
//        JSONArray ajson=null;
//        try {
//            ps=conn.prepareStatement(sql);
//            for(int i=0;i<args.length;i++)
//            {
//                ps.setObject(i+1,args[i]);
//            }
//            ResultSet resultSet = ps.executeQuery();
//            ajson=new JSONArray();
//            while(resultSet.next())
//            {
//                JSONObject jsonObject=new JSONObject();
//                ResultSetMetaData metaData=resultSet.getMetaData();
//                String columnName=null;
//                String columnValue=null;
//                for(int i=1;i<metaData.getColumnCount();i++) {
//                    columnName=metaData.getColumnLabel(i+1);
//                    try {
//                        columnValue=new String(resultSet.getBytes(columnName),"UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//
//                    jsonObject.put(columnName,columnValue);
//                }
//                ajson.put(jsonObject);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            jdbcUtils.closeResource(null,ps);
//        }
//        return ajson;
//    }
//
//    public static <T> JSONObject findJSONObject(Class<T> clazz, Connection conn,String sql, Object... args)
//    {
//        PreparedStatement ps=null;
//        JSONObject jsonObject=null;
//        try {
//            ps=conn.prepareStatement(sql);
//            for(int i=0;i<args.length;i++)
//            {
//                ps.setObject(i+1,args[i]);
//            }
//            ResultSet resultSet = ps.executeQuery();
//            if(resultSet.next())
//            {
//                jsonObject=new JSONObject();
//                ResultSetMetaData metaData=resultSet.getMetaData();
//                String columnName=null;
//                String columnValue=null;
//                for(int i=1;i<metaData.getColumnCount();i++) {
//                    columnName=metaData.getColumnLabel(i+1);
//                    try {
//                        columnValue=new String(resultSet.getBytes(columnName),"UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//
//                    jsonObject.put(columnName,columnValue);
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            jdbcUtils.closeResource(null,ps);
//        }
//        return jsonObject;
//    }

    public static <E> E getValue(Connection conn, String sql, Object...args)
    {
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            log.info(ps.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                return (E) rs.getObject(1);
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }finally {
            jdbcUtils.closeResource(null,ps,rs);
        }
        return null;
    }

}
