package boot_bookmanage.java.controller;

import boot_bookmanage.java.BookManagementApplication;
import boot_bookmanage.java.FxmlView.MainFxmlView;
import boot_bookmanage.java.entities.Admin;
import boot_bookmanage.java.service.AdminService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-21-3:52 下午
 */

@FXMLController
public class LoginController {
    @Autowired
    AdminService adminService;
    @Autowired
    MainController mainController;
    @FXML
    private TextField accountField;
    @FXML
    private PasswordField passwordField;


    public void login() throws Exception {
        String account = accountField.getText().trim();
        String password = passwordField.getText().trim();
        //调用登录功能
        if (adminService.login(account, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setContentText("登录成功!");
            alert.showAndWait();
            Admin admin = adminService.getAdminByAccount(account);
//            mainController.setAccount(account);
            mainController.setAdmin(admin);
            BookManagementApplication.showView(MainFxmlView.class);

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setContentText("账号或密码错误，登录失败!");
            alert.showAndWait();
        }
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("提示");
//            alert.setContentText("用户名："+account+"  密码："+password);
//            alert.showAndWait();
    }
}
