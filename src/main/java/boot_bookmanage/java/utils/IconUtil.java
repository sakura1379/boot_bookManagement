package boot_bookmanage.java.utils;

import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;

/**
 * @author Zenglr
 * @program: boot_bookManagement
 * @packagename: boot_bookmanage.java.utils
 * @Description 按钮自定义
 * @create 2020-12-16-2:25 下午
 */
@Component
public class IconUtil {
    /**
     * 为按钮获取图标
     * @param button
     * @param path
     * @param size
     */
    public void setIcon(Button button, String path, int size){
        Image icon = new Image(path);
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(size);
        imageView.setFitHeight((int)(size * icon.getHeight() / icon.getWidth()));
        button.setGraphic(imageView);
        //设置图标点击时发亮
        ColorAdjust colorAdjust = new ColorAdjust();
        button.setOnMousePressed(event ->  {
            colorAdjust.setBrightness(0.5);
            button.setEffect(colorAdjust);
        });
        button.setOnMouseReleased(event -> {
            colorAdjust.setBrightness(0);
            button.setEffect(colorAdjust);
        });
    }
}
