package boot_bookmanage.java.controller;

import boot_bookmanage.java.BookManagementApplication;
import boot_bookmanage.java.FxmlView.MainFxmlView;
import boot_bookmanage.java.entities.Borrow;
import boot_bookmanage.java.service.BookService;
import boot_bookmanage.java.service.BorrowService;
import boot_bookmanage.java.service.ReaderService;
import boot_bookmanage.java.utils.IconUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Zenglr
 * @program: boot_bookManagement
 * @packagename: boot_bookmanage.java.controller
 * @Description 借阅分析
 * @create 2020-12-16-10:47 上午
 */
@FXMLController
public class BorrowAnalysisController implements Initializable {
    @FXML
    private StackPane bookPieChart, readerPieChart;
    @FXML
    private Button returnButton;

    @Autowired
    BorrowService borrowService;
    @Autowired
    ReaderService readerService;
    @Autowired
    BookService bookService;
    @Autowired
    IconUtil iconUtil;

    private ObservableList<PieChart.Data> pieChartData1 = FXCollections.observableArrayList();

    private ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iconUtil.setIcon(returnButton,"icon/return.png",35);
        initBookPieChart();
        initReaderPieChart();
    }

    private void initReaderPieChart() {
        List<String> readers = readerService.getAllReadersName();
        for (String reader : readers) {
            int count = borrowService.countByReader(reader);
            pieChartData1.add(new PieChart.Data(reader, count));
        }
        final PieChart chart = new PieChart(pieChartData1);
        chart.setTitle("按读者统计饼图");
        readerPieChart.getChildren().add(chart);
    }

    private void initBookPieChart() {
        List<String> books = bookService.getAllBooksName();
        for (String book : books) {
            int count = borrowService.countByBook(book);
            pieChartData2.add(new PieChart.Data(book, count));
        }
        final PieChart chart = new PieChart(pieChartData2);
        chart.setTitle("按图书统计饼图");
        bookPieChart.getChildren().add(chart);
    }

    //返回主界面
    public void logout(){
        BookManagementApplication.showView(MainFxmlView.class);
    }
}

