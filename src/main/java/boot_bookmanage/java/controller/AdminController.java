package boot_bookmanage.java.controller;

import boot_bookmanage.java.BookManagementApplication;
import boot_bookmanage.java.FxmlView.*;
import boot_bookmanage.java.entities.Admin;
import boot_bookmanage.java.entities.Reader;
import boot_bookmanage.java.service.AdminService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-26-10:20 上午
 */
@FXMLController
@Slf4j
public class AdminController implements Initializable {
    @FXML
    private ListView<Admin> adminListView;

    @Autowired
    AdminService adminService;

    private ObservableList<Admin> observableList = FXCollections.observableArrayList();

    private List<Admin> adminList = new ArrayList<>();

    @FXML
    private StackPane mainContainer;
    @FXML
    private Label timeLabel;
    @FXML
    private ImageView adminAvatar;
    @FXML
    private Label adminName;

    @Autowired
    PersonalController personalController;

    @Autowired
    ReaderController readerController;

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
        adminList = adminService.getAllAdmins();
        showAdmins(adminList);
    }

    private void showAdmins(List<Admin> adminList){
        adminList = adminService.getAllAdmins();
        observableList.setAll(adminList);
        adminListView.setItems(observableList);
        adminListView.setCellFactory(new Callback<ListView<Admin>, ListCell<Admin>>() {
            @Override
            public ListCell<Admin> call(ListView<Admin> param) {
                return new ListCell<Admin>() {
                    @Override
                    public void updateItem(Admin admin, boolean empty) {
                        super.updateItem(admin, empty);
                        if (admin != null && !empty) {
                            HBox container = new HBox();
                            container.setSpacing(20);
                            container.getStyleClass().add("box");
                            container.setMouseTransparent(true);
                            ImageView imageView = new ImageView("img/WechatIMG73.png");
                            imageView.setFitHeight(100);
                            imageView.setFitWidth(100);
                            Label accountLabel = new Label(admin.getAccount());
                            Button delBtn = new Button("删除");
                            delBtn.getStyleClass().add("warning-theme");
                            container.getChildren().addAll(imageView, accountLabel);
                            setGraphic(container);
                        }
                    }
                };
            }
        });
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
    }

    //退出系统
    public void logout() throws Exception {
        BookManagementApplication.showView(LoginFxmlView.class);
    }

    //新增管理员方法
    public void addAdmin() {
        //创建一个Reader对象
        Admin admin = new Admin();
        //新建一个舞台
        Stage stage = new Stage();
        stage.setTitle("新增管理员界面");
        //创建一个垂直布局，用来放新增用户的各个组件
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        Label infoLabel = new Label("输入读者信息：");
        infoLabel.setPrefHeight(50);
        infoLabel.setPrefWidth(580);
        infoLabel.setAlignment(Pos.CENTER);
        //给文本添加样式
        infoLabel.getStyleClass().addAll("green-theme", "font-title");
        TextField accountField = new TextField();
        accountField.setPromptText("请输入管理员姓名");
        //输入框无焦点
        accountField.setFocusTraversable(false);
        //邮箱输入框
        TextField passwordField = new TextField();
        passwordField.setPromptText("请输入密码");
        passwordField.setFocusTraversable(false);
        //新增按钮
        FlowPane flowPane = new FlowPane();
        Button addBtn = new Button("新增");
        addBtn.setPrefWidth(120);
        addBtn.getStyleClass().addAll("green-theme", "btn-radius");
        flowPane.getChildren().add(addBtn);
        flowPane.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(infoLabel, accountField, passwordField, flowPane);
        Scene scene = new Scene(vBox, 450, 200);
        scene.getStylesheets().add("/css/style.css");
        stage.getIcons().add(new Image("/img/logo.png"));
        stage.setScene(scene);
        stage.show();
        //点击新增按钮，将界面数据封装成一个Admin对象，写入数据库
        addBtn.setOnAction(event -> {
            String accountString = accountField.getText().trim();
            String passwordString = passwordField.getText().trim();
            admin.setAccount(accountString);
            admin.setPassword(passwordString);
            adminService.setAdmin(admin);
            System.out.println(admin.getAccount() + admin.getPassword());
            stage.close();
            //重新读取一下数据显示
            adminList = adminService.getAllAdmins();
            showAdmins(adminList);
        });
    }

}
