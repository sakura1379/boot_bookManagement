package boot_bookmanage.java.controller;

import boot_bookmanage.java.BookManagementApplication;
import boot_bookmanage.java.FxmlView.MainFxmlView;
import boot_bookmanage.java.entities.Borrow;
import boot_bookmanage.java.service.BorrowService;
import boot_bookmanage.java.utils.ComponentUtil;
import boot_bookmanage.java.utils.ExcelExport;
import boot_bookmanage.java.utils.IconUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Zenglr
 * @program: boot_bookManagement
 * @packagename: boot_bookmanage.java.controller
 * @Description 借阅查询界面
 * TODO
 * @create 2020-12-06-6:46 下午
 */
@FXMLController
@Slf4j
public class BorrowController implements Initializable {

//    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");a

    @FXML
    private TableView<Borrow> borrowTable;

    @FXML
    private ComboBox readerComboBox;

    //布局文件中的输入文本框对象，用来输入搜索图书名称关键词
    @FXML
    private TextField bNameField;
    @FXML
    private Button returnButton,searchButton,downloadButton,addButton;

    //表格中的归还列
    private TableColumn<Borrow,Borrow> returnCol = new TableColumn<>("操作");

    //借阅模型数据集合，可以实时相应数据变化，无需刷新
    private ObservableList<Borrow> borrowData = FXCollections.observableArrayList();

    private ObservableList<String> readerData = FXCollections.observableArrayList();


    @Autowired
    BorrowService borrowService;
    @Autowired
    IconUtil iconUtil;

    private List<Borrow> borrowList = null;

    private List<String> readerList = null;

    private static final int MAX_THREADS = 8;
//    线程池配置
    private final Executor exec = Executors.newFixedThreadPool(MAX_THREADS, runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });

    //初始化方法，通过调用对图书表格和列表下拉框的两个封装方法，实现数据初始化
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iconUtil.setIcon(returnButton,"icon/return.png",35);
        iconUtil.setIcon(searchButton,"icon/search.png",30);
        iconUtil.setIcon(downloadButton,"icon/download.png",35);
        iconUtil.setIcon(addButton,"icon/addcell.png",35);
        initTable();
        initComBox();
    }
    //表格初始化方法
    private void initTable() {
        //水平方向不显示滚动条，表格的列宽会均匀分布
        borrowTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //1.调用查询所有图书的方法，
        borrowList = borrowService.getAllBorrows();
        //将实体集合作为参数，调用显示数据的方法，可以在界面的表格中显示图书模型集合的值
        showBorrowData(borrowList);


        //3.删除列的相关设置
        returnCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        returnCol.setCellFactory(param -> new TableCell<Borrow, Borrow>() {
//            private final Button returnButton = ComponentUtil.getButton("归还", "warning-theme");
        private final Button returnButton = ComponentUtil.getButton("sameDefault-theme");

            @Override
            protected void updateItem(Borrow borrow, boolean empty) {
                super.updateItem(borrow, empty);
                if (borrow == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(returnButton);
                iconUtil.setIcon(returnButton,"icon/close.png",35);
                //点击归还按钮，需要将这一行从表格移除，同时从底层数据库真正删除
                returnButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("确认对话框");
                    alert.setHeaderText("书名：" + borrow.getBname());
                    alert.setContentText("确定要归还这本书吗?");
                    Optional<ButtonType> result = alert.showAndWait();
                    //点击了确认按钮，执行删除操作，同时移除一行模型数据
                    if (result.get() == ButtonType.OK) {
                        borrowData.remove(borrow);
                        borrowService.deleteBorrow(borrow.getId());
                    }
                });
            }
        });
        //将除列加入图书表格
        borrowTable.getColumns().add(returnCol);
        //4.图书表格双击事件,双击弹出显示图书详情的界面
        borrowTable.setRowFactory(tv ->

        {
            TableRow<Borrow> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                //判断鼠标双击了一行
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    //获得该行的图书ID属性
                    int id = row.getItem().getId();
                    //根据id查询到图书的完整信息
                    Borrow borrow = borrowService.getBorrow(id);
                    //创建一个新的图书详情界面窗口
                    Stage bookInfoStage = new Stage();
                    bookInfoStage.setTitle("借阅详情界面");
                    //用VBox显示具体图书信息
                    VBox vBox = new VBox();
                    vBox.setSpacing(10);
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setPrefSize(600, 300);
                    vBox.setPadding(new Insets(10, 10, 10, 10));
                    Label nameLabel = new Label("读者名：" + borrow.getName());
                    nameLabel.getStyleClass().add("font-title");
                    Label bnameLabel = new Label("书名：" + borrow.getBname());
                    Label dateLabel = new Label("借阅时间:" + borrow.getBorrow_date());
                    vBox.getChildren().addAll(nameLabel, bnameLabel, dateLabel);
                    Scene scene = new Scene(vBox, 640, 380);
                    //因为是一个新的窗口，需要重新读入一下样式表，这个界面就可以使用style.css样式表中的样式了
                    scene.getStylesheets().add("/css/style.css");
                    bookInfoStage.setScene(scene);
                    bookInfoStage.show();
                }
            });
            return row;
        });
    }

    //下拉框初始化方法
    private void initComBox() {
        //1.到数据库查询所有的读者
        readerList = borrowService.getAllReaders();
        //2.将typeList集合加入typeData模型数据集合
        readerData.addAll(readerList);
        //3.将数据模型设置给下拉框
        readerComboBox.setItems(readerData);

        //4.下拉框选择事件监听，根据选择不同的类别，图书表格中会过滤出该类别的图书
        readerComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                    // System.out.println(newValue.getId() + "," + newValue.getTypeName());
                    //移除掉之前的数据
                    borrowTable.getItems().removeAll(borrowData);
                    //根据选中的类别查询该类别所有图书
                    borrowList = borrowService.getBorrowsByName(newValue.toString());
                    //重新显示数据
                    showBorrowData(borrowList);
                }
        );
    }

    private void showBorrowData(List<Borrow> borrowList) {
        borrowData.addAll(borrowList);
        borrowTable.setItems(borrowData);
    }

    //数据导出方法，采用hutool提供的工具类
    public void export() {
        ExcelExport.exportBorrow(borrowList);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示信息");
        alert.setHeaderText("借阅数据已导出!");
        alert.showAndWait();
    }

    //返回主界面
    public void logout(){
        BookManagementApplication.showView(MainFxmlView.class);
    }

    //根据关键词搜索方法
    public void search() {
        borrowTable.getItems().removeAll(borrowData);
        //获得输入的查询关键字
        String keywords = bNameField.getText().trim();
        borrowList = borrowService.getBorrowsLike(keywords);
        showBorrowData(borrowList);
    }

    //弹出新增借阅界面方法
    public void newBorrowStage() {
        Borrow borrow = new Borrow();
        //新建一个舞台
        Stage stage = new Stage();
        stage.setTitle("新增借阅界面");
        //创建一个垂直布局，用来放新增图书的各个组件
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        Label infoLabel = new Label("输入借阅信息：");
        infoLabel.setPrefHeight(50);
        infoLabel.setPrefWidth(580);
        infoLabel.setAlignment(Pos.CENTER);
        //给文本添加样式
        infoLabel.getStyleClass().addAll("gray-theme", "font-title");
        List<String> readerlist = borrowService.getAllReadersName();
        //将list中的数据加入observableList
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(readerlist);
        //创建类型下拉框
        ComboBox<String> RnameComboBox = new ComboBox<>();
        RnameComboBox.prefWidthProperty().bind(vBox.widthProperty());
        RnameComboBox.setPromptText("请选择读者名字");
        //给下拉框初始化值
        RnameComboBox.setItems(observableList);
        //下拉框选项改变事件
        RnameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                borrow.setName(newValue);
            }
        });
        List<String> booklist = borrowService.getAllBooksName();
        //将list中的数据加入observableList
        ObservableList<String> observableList1 = FXCollections.observableArrayList();
        observableList1.addAll(booklist);
        //创建类型下拉框
        ComboBox<String> BnameComboBox = new ComboBox<>();
        BnameComboBox.prefWidthProperty().bind(vBox.widthProperty());
        BnameComboBox.setPromptText("请选择图书名称");
        //给下拉框初始化值
        BnameComboBox.setItems(observableList1);
        //下拉框选项改变事件
        BnameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                borrow.setBname(newValue);
            }
        });

        //新增按钮
        FlowPane flowPane = new FlowPane();
        Button addBtn = new Button("新增");
        addBtn.setPrefWidth(120);
        addBtn.getStyleClass().addAll("gray-theme", "btn-radius");
        flowPane.getChildren().add(addBtn);
        flowPane.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(infoLabel, RnameComboBox, BnameComboBox, flowPane);
//        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 450, 200);
        scene.getStylesheets().add("/css/style.css");
        stage.getIcons().add(new Image("/img/logo.png"));
        stage.setScene(scene);
        stage.show();
        //点击新增按钮，将界面数据封装成一个Borrow对象，写入数据库
        addBtn.setOnAction(event -> {

            log.info(borrow.getName()+borrow.getBname());
//            exec.execute(new Runnable() {
//                @Override
//                public void run(){
//                    int id = borrowService.addBorrow(borrow);
//                    log.info(String.valueOf(id));
//                    borrow.setId(id);
//
//                }
//            });
            int id = borrowService.addBorrow(borrow);
            log.info(String.valueOf(id));
            borrow.setId(id);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date borrow_date = null;
            borrow_date = java.sql.Date.valueOf(df.format(new Date()));
            log.info(String.valueOf(borrow_date));
            borrow.setBorrow_date(borrow_date.toString());
            log.info(borrow.getName()+borrow.getBname()+borrow.getBorrow_date());
            borrowData.add(borrow);

            stage.close();
        });
    }
}

