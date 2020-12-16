package boot_bookmanage.java.controller;

import boot_bookmanage.java.BookManagementApplication;
import boot_bookmanage.java.FxmlView.*;
import boot_bookmanage.java.entities.Admin;
import boot_bookmanage.java.entities.Reader;
import boot_bookmanage.java.utils.IconUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-21-8:38 下午
 */

@FXMLController
public class MainController implements Initializable {
    @FXML
    private StackPane mainContainer;
    @FXML
    private Label timeLabel;
    @FXML
    private ImageView adminAvatar;
    @FXML
    private Label adminName;
    @FXML
    private Button logoutButton;

    @Autowired
    ReaderController readerController;

    @Autowired
    AdminController adminController;

    @Autowired
    PersonalController personalController;

    @Autowired
    IconUtil iconUtil;

//    private String account;
//
//    public void setAccount(String account) {
//        this.account = account;
//    }
//    private String homeIcon  = getClass().getResource("src/main/resources/icon/home.png").toString();

    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public void initialize(URL location, ResourceBundle resources) {
        //开启一个UI线程 ,将登录界面传过来的管理员信息显示在主界面的右上角
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Image image = new Image("img/WechatIMG73.png");
                adminAvatar.setImage(image);
                //将头像显示为圆形
                Circle circle = new Circle();
                circle.setCenterX(20.0);
                circle.setCenterY(20.0);
                circle.setRadius(20.0);
                adminAvatar.setClip(circle);
                //显示管理员姓名
                adminName.setText(admin.getAccount());
                iconUtil.setIcon(logoutButton,"icon/home.png",35);
            }
        });
        //启一个线程，用来同步获取系统时间
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //获取系统当前时间
                    LocalDateTime now = LocalDateTime.now();
                    //格式化时间
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
                    String timeString = dateTimeFormatter.format(now);
                    //启一个UI线程
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //将格式化后的日期时间显示在标签上
                            timeLabel.setText(timeString);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.err.println("中断异常");
                    }
                }
            }
        }).start();

        try {
            AnchorPane anchorPane = new FXMLLoader(getClass().getResource("/fxml/default.fxml")).load();
            mainContainer.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void listDefault() throws Exception {
        mainContainer.getChildren().clear();
        AnchorPane anchorPane = new FXMLLoader(getClass().getResource("/fxml/default.fxml")).load();
        mainContainer.getChildren().add(anchorPane);
    }
    public void listBook() throws Exception {
        BookManagementApplication.showView(BookFxmlView.class);
    }

    public void listBookAnalysis() throws Exception {
        BookManagementApplication.showView(BookAnalysisFxmlView.class);
    }

    public void listAdmin() throws Exception {
        adminController.setAdmin(admin);
        BookManagementApplication.showView(AdminFxmlView.class);
    }

    public void listReader() throws Exception {
        readerController.setAdmin(admin);
        BookManagementApplication.showView(ReaderFxmlView.class);
    }

    public void listReaderAnalysis() throws Exception {
        BookManagementApplication.showView(ReaderAnalysisFxmlView.class);
    }

    public void listBorrow() throws Exception {
        BookManagementApplication.showView(BorrowFxmlView.class);
    }

    public void listBorrowAnalysis() throws Exception {
        BookManagementApplication.showView(BorrowAnalysisFxmlView.class);
    }

    public void listPersonal() throws Exception {
        personalController.setAdmin(admin);
        BookManagementApplication.showView(PersonalFxmlView.class);
//        PersonalController personalController = fxmlLoader.getController();

    }

    //退出系统
    public void logout() throws Exception {
        BookManagementApplication.showView(LoginFxmlView.class);
    }

}
