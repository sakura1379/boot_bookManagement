package boot_bookmanage.java.controller;

import boot_bookmanage.java.BookManagementApplication;
import boot_bookmanage.java.FxmlView.MainFxmlView;
import boot_bookmanage.java.service.ReaderService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Zenglr
 * @program: boot_bookManagement
 * @packagename: boot_bookmanage.java.controller
 * @Description 读者分析界面
 * @create 2020-12-09-10:00 下午
 */
@FXMLController
public class ReaderAnalysisController implements Initializable{
    @FXML
    private StackPane departmentPieChart, rolePieChart;

    private String[] departments = {"信息管理学院", "电气工程学院", "航空工程学院", "交通工程学院",
            "计算机与软件学院", "经济管理学院", "商务贸易学院"};
    private String[] roles = {"老师", "学生"};

    @Autowired
    ReaderService readerService;

    private ObservableList<PieChart.Data> pieChartData1 = FXCollections.observableArrayList();

    private ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDepartmentPieChart();
        initRolePieChart();
    }

    private void initDepartmentPieChart() {
        for (String department : departments) {
            int count = readerService.countByDepartment(department);
            pieChartData1.add(new PieChart.Data(department, count));
        }
        final PieChart chart = new PieChart(pieChartData1);
        chart.setTitle("按院系统计饼图");
        departmentPieChart.getChildren().add(chart);
    }

    private void initRolePieChart() {
        for (String role : roles) {
            int count = readerService.countByRole(role);
            pieChartData2.add(new PieChart.Data(role, count));
        }
        final PieChart chart = new PieChart(pieChartData2);
        chart.setTitle("按角色统计饼图");
        rolePieChart.getChildren().add(chart);
    }

    //返回主界面
    public void logout(){
        BookManagementApplication.showView(MainFxmlView.class);
    }
}
