package boot_bookmanage.java.controller;

import boot_bookmanage.java.BookManagementApplication;
import boot_bookmanage.java.FxmlView.MainFxmlView;
import boot_bookmanage.java.service.BookService;
import boot_bookmanage.java.utils.IconUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Zenglr
 * @program: boot_bookManagement
 * @packagename: boot_bookmanage.java.controller
 * @Description 图书类型统计饼图界面
 * @create 2020-12-16-9:15 上午
 */
@FXMLController
@Slf4j
public class BookAnalysisController implements Initializable {
    @FXML
    private StackPane pieChartPane, barChartPane;
    @FXML
    private Button returnButton;

    @Autowired
    BookService bookService;
    @Autowired
    IconUtil iconUtil;

    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iconUtil.setIcon(returnButton,"icon/return.png",35);
        initPieChart();
        initBarChart();
    }

    private void initPieChart() {
        String[] type = {"文学", "科技", "经管", "传记"};
        for (int i = 0; i < type.length; i++) {
            int count = bookService.countByType(type[i]);
            pieChartData.add(new PieChart.Data(type[i], count));
        }
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("按图书类别统计饼图");
        pieChartPane.getChildren().add(chart);
    }

    private void initBarChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);
        bc.setTitle("根据类别统计柱形图");
        xAxis.setLabel("图书类别");
        yAxis.setLabel("图书数量");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2018年统计数据");
        String[] type = {"文学", "科技", "经管", "传记"};
        for (int i = 0; i < type.length; i++) {
            int count = bookService.countByType(type[i]);
            series1.getData().add(new XYChart.Data(type[i], count));
        }
        bc.getData().addAll(series1);
        barChartPane.getChildren().add(bc);
    }

    //返回主界面
    public void logout(){
        BookManagementApplication.showView(MainFxmlView.class);
    }
}
