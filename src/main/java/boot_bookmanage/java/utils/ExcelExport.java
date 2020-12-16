package boot_bookmanage.java.utils;

import boot_bookmanage.java.entities.Book;
import boot_bookmanage.java.entities.Borrow;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.util.List;

public class ExcelExport {
    public static void exportBook(List<Book> bookList) {
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("/Users/sskura/Downloads/test.xlsx");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(5, "图书信息表");
        // 一次性写出内容，使用默认样式
        writer.write(bookList);
        // 关闭writer，释放内存
        writer.close();
    }
    public static void exportBorrow(List<Borrow> borrowList) {
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("/Users/sskura/Downloads/test1.xlsx");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(4, "图书借阅表");
        // 一次性写出内容，使用默认样式
        writer.write(borrowList);
        // 关闭writer，释放内存
        writer.close();
    }
}
