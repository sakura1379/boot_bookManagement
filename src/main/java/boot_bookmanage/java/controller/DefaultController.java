package boot_bookmanage.java.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-22-8:38 下午
 */
@FXMLController
public class DefaultController implements Initializable {
    @FXML
    private ImageView bookImg;
//    @FXML
//    private Label typeCount, bookCount, readerCount, adminCount;
//    private AnalysisService analysisService = ServiceFactory.getAnalysisServiceInstance();

    //轮播图资源数组
    String[] imgPath = {"book1.jpg", "book2.jpg", "book3.jpg", "book4.jpg", "book5.jpg"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //获取后台的统计数据，显示在相应区域
//        typeCount.setText("类别" + analysisService.getTypesCount() + "种");
//        bookCount.setText("图书" + analysisService.getBooksCount() + "册");
//        readerCount.setText("读者" + analysisService.getReadersCount() + "人");
//        adminCount.setText("管理员" + analysisService.getAdminsCount() + "个");

        //新建一个线程，用来轮播
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //循环读取图片数组
                    for (int i = 0; i < imgPath.length; i++) {
                        //用每个资源创建一个图片对象
                        Image image = new Image("/img/" + imgPath[i]);
                        //开启一个UI线程
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //将创建的Image对象设置给ImageView
                                bookImg.setImage(image);
                            }
                        });
                        try {
                            //休眠2秒
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //轮播到最后一张图的时候，回到第一张重新播放
                        if (i == imgPath.length - 1) {
                            i = 0;
                        }
                    }
                }
            }
        }).start();
    }
}
