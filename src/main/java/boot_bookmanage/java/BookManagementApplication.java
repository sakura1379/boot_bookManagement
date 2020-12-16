package boot_bookmanage.java;

import boot_bookmanage.java.FxmlView.LoginFxmlView;
import boot_bookmanage.java.config.SplashScreenCustom;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class BookManagementApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
//        SpringApplication.run(BookManagementApplication.class, args);
//        run(BookManagementApplication.class,
//                Arrays.asList(new FxmlView[]{FxmlView.MODULE_DASHBOARD, FxmlView.MODULE_PROFILE, FxmlView.MAIN}),
//                FxmlView.MAIN, args);
        launch(BookManagementApplication.class, LoginFxmlView.class, new SplashScreenCustom(), args);

    }

}
