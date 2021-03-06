package boot_bookmanage.java.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 颜色工具类，用来随机获取一种颜色
 */
@Component
public class ColorUtil {
    private static String[] colors = {
            "##99BBFF", "#9999FF", "#9F88FF", "#B088FF","#F0BBFF",
            "#D28EFF", "#D28EFF", "#77DDFF", "#CCBBFF", "#E8CCFF",
            "#FFFFBB", "#FFFFBB", "#EEFFBB", "#CCFF99", "#BBFFEE"
    };

    public static String getColor() {
        int bounds = colors.length;
        Random random = new Random();
        //获取数组范围长度内的一个随机索引值
        int index = random.nextInt(bounds);
        return colors[index];
    }
}
