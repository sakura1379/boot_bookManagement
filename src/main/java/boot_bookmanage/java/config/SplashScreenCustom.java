package boot_bookmanage.java.config;

import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Parent;

/**
 * @author Zenglr
 * @program: boot_bookManagement
 * @packagename: boot_bookmanage.java.config
 * @Description 自定义加载动画
 * @create 2020-12-07-8:21 下午
 */
public class SplashScreenCustom extends SplashScreen {
    @Override
    public Parent getParent() {
        return super.getParent();
    }

    @Override
    public boolean visible() {
        return super.visible();
    }

    @Override
    public String getImagePath() {
        return "/img/load.gif";
    }
}
