package boot_bookmanage.java.utils;

import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component
public class ComponentUtil {

    //根据传入的文字和主题返回一个按钮
    public static Button getButton(String text, String theme) {
        Button button = new Button(text);
        button.getStyleClass().add(theme);
        return button;
    }

    public static Button getButton(String theme) {
        Button button = new Button();
        button.getStyleClass().add(theme);
        return button;
    }
}
