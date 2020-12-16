package boot_bookmanage.java.controller;

import boot_bookmanage.java.BookManagementApplication;
import boot_bookmanage.java.FxmlView.*;
import boot_bookmanage.java.entities.Admin;
import boot_bookmanage.java.entities.Reader;
import boot_bookmanage.java.service.ReaderService;
import boot_bookmanage.java.utils.ComponentUtil;
import boot_bookmanage.java.utils.IconUtil;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-22-6:33 下午
 */
@FXMLController
public class ReaderController implements Initializable {

    @Autowired
    ReaderService readerService;
    @FXML
    private FlowPane readerPane;

    private List<Reader> readerList = new ArrayList<>();

    @FXML
    private StackPane mainContainer;
    @FXML
    private Label timeLabel;
    @FXML
    private ImageView adminAvatar;
    @FXML
    private Label adminName;
    @FXML
    private Button logoutButton,addButton;

    @Autowired
    PersonalController personalController;
    @Autowired
    IconUtil iconUtil;
    @Autowired
    AdminController adminController;

    private String account;

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
                iconUtil.setIcon(logoutButton,"icon/home.png",35);
                iconUtil.setIcon(addButton,"icon/add-account.png",35);
                adminAvatar.setClip(circle);
                //显示管理员姓名
                adminName.setText(account);
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
        readerList = readerService.getAllReaders();
        showReaders(readerList);
    }


    public void listDefault() throws Exception {
        mainContainer.getChildren().clear();
        AnchorPane anchorPane = new FXMLLoader(getClass().getResource("/fxml/default.fxml")).load();
        mainContainer.getChildren().add(anchorPane);
    }
    public void listBook() throws Exception {
        BookManagementApplication.showView(BookFxmlView.class);
    }

    public void listBorrow() throws Exception {
        BookManagementApplication.showView(BorrowFxmlView.class);
    }

    public void listBorrowAnalysis() throws Exception {
        BookManagementApplication.showView(BorrowAnalysisFxmlView.class);
    }

    public void listBookAnalysis() throws Exception {
        BookManagementApplication.showView(BookAnalysisFxmlView.class);
    }

    public void listAdmin() throws Exception {
        adminController.setAdmin(admin);
        BookManagementApplication.showView(AdminFxmlView.class);
    }

    public void listReader() throws Exception {
//        readerController.setAccount(account);
        BookManagementApplication.showView(ReaderFxmlView.class);
    }

    public void listReaderAnalysis() throws Exception {
        BookManagementApplication.showView(ReaderAnalysisFxmlView.class);
    }

    public void listPersonal() throws Exception {
        personalController.setAdmin(admin);
        BookManagementApplication.showView(PersonalFxmlView.class);
    }

    //退出系统
    public void logout() throws Exception {
        BookManagementApplication.showView(LoginFxmlView.class);
    }


    //通过循环遍历readerList集合，创建Hbox来显示每个读者信息
    private void showReaders(List<Reader> readerList) {
        //移除之前的记录
        readerPane.getChildren().clear();
        for (Reader reader : readerList) {
            HBox hBox = new HBox();
            hBox.setPrefSize(300, 240);
            hBox.getStyleClass().add("box");
            hBox.setSpacing(30);
            hBox.setPadding(new Insets(0,0,0,20));
            //左边垂直布局放身份
            VBox leftBox = new VBox();
            Label roleLabel = new Label(reader.getRole());
            leftBox.getChildren().addAll(roleLabel);
            //右边垂直布局放姓名、部门、邮箱、电话
            VBox rightBox = new VBox();
            rightBox.setSpacing(15);
            Label nameLabel = new Label(reader.getName());
            nameLabel.getStyleClass().add("font-title");
            Label departmentLabel = new Label(reader.getDepartment());
            Label emailLabel = new Label(reader.getEmail());
            Label mobileLabel = new Label(reader.getMobile());
            Button delBtn = ComponentUtil.getButton("删除","sameDefault-theme");
            iconUtil.setIcon(delBtn,"icon/close.png",35);
            rightBox.getChildren().addAll(nameLabel, departmentLabel,
                    emailLabel, mobileLabel,  delBtn);
            //左右两个垂直布局加入水平布局
            hBox.getChildren().addAll(leftBox, rightBox);
            //水平布局加入大的内容容器
            readerPane.getChildren().add(hBox);
            //删除按钮事件
            delBtn.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("确认对话框");
                alert.setContentText("确定要删除这行记录吗?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    int id = reader.getRid();
                    //删除掉这行记录
                    readerService.deleteReader(id);
                    //从流式面板移除当前这个人的布局
                    readerPane.getChildren().remove(hBox);
                }
            });
        }
    }

    //新增读者方法
    public void addReader() {
        //创建一个Reader对象
        Reader reader = new Reader();
        //新建一个舞台
        Stage stage = new Stage();
        stage.setTitle("新增读者界面");
        //创建一个垂直布局，用来放新增用户的各个组件
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        Label infoLabel = new Label("输入读者信息：");
        infoLabel.setPrefHeight(50);
        infoLabel.setPrefWidth(580);
        infoLabel.setAlignment(Pos.CENTER);
        //给文本添加样式
        infoLabel.getStyleClass().addAll("gray-theme", "font-title");
        TextField nameField = new TextField();
        nameField.setPromptText("请输入姓名");
        //输入框无焦点
        nameField.setFocusTraversable(false);
        HBox roleBox = new HBox();
        roleBox.setSpacing(20);
        ToggleGroup group = new ToggleGroup();
        RadioButton teacherButton = new RadioButton("老师");
        teacherButton.setToggleGroup(group);
        teacherButton.setSelected(true);
        teacherButton.setUserData("老师");
        RadioButton studentButton = new RadioButton("学生");
        studentButton.setToggleGroup(group);
        studentButton.setUserData("学生");
        roleBox.getChildren().addAll(teacherButton, studentButton);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                //给读者对象设置选中的角色
                System.out.println(group.getSelectedToggle().getUserData().toString());
                reader.setRole(group.getSelectedToggle().getUserData().toString());
            }
        });
        if(reader.getRole() == null){
            reader.setRole("老师");
        }
        //院系部门数组
        String[] departments = {"信息管理学院", "电气工程学院", "航空工程学院", "交通工程学院",
                "计算机与软件学院", "经济管理学院", "商务贸易学院"};
        //数组转为List
        List<String> list = Arrays.asList(departments);
        //将list中的数据加入observableList
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(list);
        //创建院系下拉框
        ComboBox<String> depComboBox = new ComboBox<>();
        depComboBox.setPromptText("请选择院系");
        //给下拉框初始化值
        depComboBox.setItems(observableList);
        //下拉框选项改变事件
        depComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //将选中的值设置给读者的部门属性
                reader.setDepartment(newValue);
            }
        });
        //邮箱输入框
        TextField emailField = new TextField();
        emailField.setPromptText("请输入邮箱");
        emailField.setFocusTraversable(false);
        //电话输入框
        TextField mobileField = new TextField();
        mobileField.setPromptText("请输入电话");
        mobileField.setFocusTraversable(false);
        //新增按钮
        FlowPane flowPane = new FlowPane();
        Button addBtn = new Button("新增");
        addBtn.setPrefWidth(120);
        addBtn.getStyleClass().addAll("gray-theme", "btn-radius");
        flowPane.getChildren().add(addBtn);
        flowPane.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(infoLabel, nameField, roleBox, depComboBox,
                emailField, mobileField, flowPane);
        Scene scene = new Scene(vBox, 450, 300);
        scene.getStylesheets().add("/css/style.css");
        stage.getIcons().add(new Image("/img/logo.png"));
        stage.setScene(scene);
        stage.show();
        //点击新增按钮，将界面数据封装成一个Reader对象，写入数据库
        addBtn.setOnAction(event -> {
            String nameString = nameField.getText().trim();
            String emailString = emailField.getText().trim();
            String mobileString = mobileField.getText().trim();
            reader.setName(nameString);
            reader.setEmail(emailString);
            reader.setMobile(mobileString);
            System.out.println(reader.getName() + reader.getRole() + reader.getMobile());
            readerService.addReader(reader);
            stage.close();
            //重新读取一下数据显示
            readerList = readerService.getAllReaders();
            showReaders(readerList);
        });
    }
}
