package com.tc.vd;

import com.tc.vd.config.ResConstant;
import com.tc.vd.config.SysConfig;
import com.tc.vd.config.UserConfig;
import com.tc.vd.controller.ControlledStage;
import com.tc.vd.controller.MainController;
import com.tc.vd.controller.StageController;
import com.tc.vd.controller.UIResourceEnum;
import com.tc.vd.event.WindowsCloseEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class VdApplication extends Application {
    static {
        //设置log4j配置
        PropertyConfigurator.configureAndWatch(ResConstant.logConf);
//        try {
//            File resFile = FileResUtil.getResFile(ResConstant.addressBook);
//            PropertyConfigurator.configure(ResConstant.addressBook);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
    private StageController stageController = StageController.getInstance();
    private static Logger LOG = Logger.getLogger(VdApplication.class);

    /**
     * VD APP启动
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(final Stage primaryStage) throws Exception{
        ////国际化
        String language = System.getProperty("app.language");
        Locale local = null;
        if(null == language){
            local = Locale.getDefault();
        } else {
            Locale[] availableLocales = Locale.getAvailableLocales();
            for (Locale tlocal : availableLocales) {
                if (tlocal.getLanguage().equals(language)){
                    local = tlocal;
                    break;
                }
            }
        }
//        local = new Locale("zh", "CN");
//        local = new Locale("en", "US");
        if(null == local){
            //如果没有找到语言地区信息使用默认的vdLang.properties文件
            local = new Locale("");
        }
        ResourceBundle vdLang = ResourceBundle.getBundle("lang.vdLang", local);

        stageController.setVdLang(vdLang);
        stageController.setPrimaryStage(primaryStage);

        //加载用户偏好设置
        LOG.debug("加载用户偏好设置");
        UserConfig ucIns = UserConfig.getInstance();
        String useTimes = ucIns.getProperty("sys", "useTimes");
        if(null == useTimes || "".equals(useTimes) || (!useTimes.matches("\\d"))){
            useTimes = "1";
        } else {
            useTimes = String.valueOf(Integer.valueOf(useTimes) + 1);
            //ucIns.setProperty("sys", "useTimes", useTimes, true);//开发阶段注释
        }

        //加载主要场景
        URL stageRootUrl =  UIResourceEnum.PRIMARY_STAGE.getUiResourceUrl();
        FXMLLoader loader = new FXMLLoader(stageRootUrl, vdLang);
        Parent root = (Parent)loader.load();

        //设置舞台场景
        Scene scene= new Scene(root);
        primaryStage.setScene(scene);

        //设置控制器
        MainController mainController = loader.getController();
        ControlledStage controlledStage = (ControlledStage) mainController;
        controlledStage.setStageController(stageController, UIResourceEnum.PRIMARY_STAGE);//设置场景控制器
        mainController.init();//调用初始化方法

        primaryStage.initStyle(StageStyle.UNDECORATED);
        //收到关闭请求的时候怎么做
        primaryStage.setOnCloseRequest(new WindowsCloseEvent(primaryStage));

        //添加系统托盘图标
        if(SystemTray.isSupported()){ //如果支持系统托盘图标
            enableTray(primaryStage);
        }

        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            //载入系统环境变量
            new SysConfig();
            //设置皮肤
            String skin = System.getProperty("ui.skin");
            if(null != skin && !"".equals(skin)){
                System.setProperty("javafx.userAgentStylesheetUrl", skin);
            }
            //更改system输出
            String runMode = System.getProperty("app.runmode");
            if(!"dev".equals(runMode)){
                File outFile = new File(System.getProperty("system.out.filepath"));
                File errFile = new File(System.getProperty("system.err.filepath"));

                if(!outFile.exists()){
                    outFile.createNewFile();
                }
                if(!errFile.exists()){
                    errFile.createNewFile();
                }
                System.setOut(new PrintStream(new FileOutputStream(outFile)));
                System.setErr(new PrintStream(new FileOutputStream(errFile)));
            }
            //启动vd
            launch(args);
        } catch (Exception e){
            LOG.error("发生错误：", e);
            e.printStackTrace();
        }
    }

    private TrayIcon trayIcon;
    //右小角,最小化.
    private void enableTray(final Stage stage) {
        /**
         * 解决乱码问题
         * 1.http://cache.baiducontent.com/c?m=9d78d513d99b12eb0bfa950e1a16a0711824d3267ac0d16568d4e45f9214191c0521a1e4707e5713d2b56b1772b8392ffd863365410737b6ebdff94acacd923f5e883043000b873105a26eb8ba3532b051872de9bb5ea7e4ff68d5e890c4de274ed70c4e6d8087d61b514e9133a3033194face0e08180db9ee3760f45b7334cf7d1de35afae439680181&p=882a9546dc8b16fc57ef8c351e4e80&newp=c26a8e018adf12a05abd9b7e0e1c9e231610db2151d4da126b82c825d7331b001c3bbfb423231b04d4c37c6106aa4d58e9f0367735012ba3dda5c91d9fb4c57479&user=baidu&fm=sc&query=java%2Eawt%2EPopupMenu%BA%BA%D7%D6%C2%D2%C2%EB%CE%CA%CC%E2&qid=a0b6af4b000b803b&p1=5
         * 2.百度搜索：java.awt.PopupMenu汉字乱码问题
         *
         * 暂时解决办法：
         *      在虚拟机运行VM参数设置-Dfile.encoding=GBK
         */
        PopupMenu popupMenu = new PopupMenu();

        ResourceBundle vdLang = stageController.getVdLang();//获取国际化资源
        final String disStr = vdLang.getString("app.systemTray.display");
        final String minimizeStr = vdLang.getString("app.systemTray.minimize");
        final String exitStr = vdLang.getString("app.systemTray.exit");

        MenuItem openItem = new MenuItem(disStr);
        java.awt.MenuItem hideItem = new java.awt.MenuItem(minimizeStr);
        //hideItem.setFont(new Font("宋体",Font.ITALIC,18));
        java.awt.MenuItem quitItem = new java.awt.MenuItem(exitStr);

        ActionListener acl = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                java.awt.MenuItem item = (java.awt.MenuItem) e.getSource();
                Platform.setImplicitExit(false); //多次使用显示和隐藏设置false

                if (item.getLabel().equals(exitStr)) {//退出
                    SystemTray.getSystemTray().remove(trayIcon);
                    Platform.exit();
                    return;
                }
                if (item.getLabel().equals(disStr)) {//显示
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                            stage.setIconified(false);
                        }
                    });
                }
                if (item.getLabel().equals(minimizeStr)) {//最小化
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //stage.hide();
                            stage.setIconified(true);
                        }
                    });
                }
            }
        };

        //双击事件方法
        MouseListener sj = new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Platform.setImplicitExit(false); //多次使用显示和隐藏设置false
//                if (e.getClickCount() == 2) {
//                    if (stage.isShowing()) {
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                stage.hide();
//                            }
//                        });
//                    }else{
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                stage.show();
//                            }
//                        });
//                    }
//                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

            }
        };

        openItem.addActionListener(acl);
        quitItem.addActionListener(acl);
        hideItem.addActionListener(acl);

        popupMenu.add(openItem);
        popupMenu.add(hideItem);
        popupMenu.add(quitItem);

        try {
            SystemTray tray = SystemTray.getSystemTray();
            String icon = System.getProperty("res.css.skin.path") + File.separator + "images/VD16X16.png";
            File iconFile = new File(icon);
            String appTitle = System.getProperty("app.title");
            trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(iconFile.getAbsolutePath()), appTitle, popupMenu);
            trayIcon.setToolTip(appTitle);
            tray.add(trayIcon);
//            trayIcon.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(java.awt.event.MouseEvent e) {
//                    super.mouseClicked(e);
//                    if (!stage.isShowing()) {
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                Platform.setImplicitExit(false);
//                                stage.show();
//                            }
//                        });
//                    }else{
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                stage.hide();
//                                //Platform.exit();
//                            }
//                        });
//                    }
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
