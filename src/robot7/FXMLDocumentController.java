/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * マルチバイト文字はキーを送る事ができないのでクリップボード経由となる（未作成）
 * 全角文字をチェックするサンプル共通関数です。（下のサイトを参考に自動判定して分岐）
 * https://blog.java-reference.com/common-check-zenkaku/
 */
package robot7;

import FXMLRobotUty.FXMLRobotGrandPanelController;
import actor.Keyboard;
import actor.Mouse;
import actor.SendControlKey;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author kyokuto
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mouseClickPoint.FXMLGrandPanelController;

public class FXMLDocumentController implements Initializable {

    //@FXML
    //private FXMLDocumentController FXMLDocumentController;
    /**
     * @return the tFPointX
     */
    public TextField gettFPointX() {
        return tFPointX;
    }

    /**
     * @return the tFPointY
     */
    public TextField gettFPointY() {
        return tFPointY;
    }

    /**
     * @param tFPointX the tFPointX to set
     */
    public void settFPointX(TextField tFPointX) {
        this.tFPointX = tFPointX;
    }

    /**
     * @param tFPointY the tFPointY to set
     */
    public void settFPointY(TextField tFPointY) {
        this.tFPointY = tFPointY;
    }

    //private File text;
    @FXML
    private Label lbType;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MenuItem mIfileOpen;

    @FXML
    private MenuItem closeItem;

    @FXML
    private MenuItem mIgetPoint;

    @FXML
    private TableView<Member> tVdata;

    @FXML
    private TableColumn tClineNumber;

    @FXML
    private TableColumn tCpassFlag;

    @FXML
    private TableColumn tCtext;

    @FXML
    private TextField tFPointX;
    @FXML
    private TextField tFPointY;

    @FXML
    private CheckBox chbEnter;

    @FXML
    private Button btnSendToNext;

    @FXML
    private FXMLRobotGrandPanelController FXMLRobotGrandPanelController;

    private ObservableList<Member> data;

    private int lastMousePointX;
    private int lastMousePointY;

    private Path path;

    /*
    public void setSendToNextShow(boolean flg){
            btnSendToNext.setVisible(flg);
    }
     */
    private void btnClicEvent() {
        this.btnSendToNext.setOnMouseClicked(e -> lastMousePointX = (int) Math.floor(e.getX()));
        this.btnSendToNext.setOnMouseClicked(e -> lastMousePointY = (int) Math.floor(e.getY()));
    }

    @FXML
    void handleAutoToRunAction(ActionEvent event) {
        try {
            while (handleSendToNextAction(event)) {
                Thread.sleep(1000);
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    boolean handleSendToNextAction(ActionEvent event) throws IOException {

        //btnSendToNext.setVisible(false);
        System.out.println("SendToNext");
        int i = tVdata.getSelectionModel().getSelectedIndex();
        System.out.println("Selectded now " + i);
        Mouse.mouseClick(Integer.parseInt(this.gettFPointX().getText()),
                Integer.parseInt(this.gettFPointY().getText()));
// SendControlKey.testSendKey();
// System.exit(0);
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/FXMLRobotUty/FXMLRobotGrandPanel.fxml"));
        Parent root;
        try {

            String typingLetter;
            if (chbEnter.isSelected()) { // with enter. 
                typingLetter = tVdata.getSelectionModel().getSelectedItem().getText()
                        .concat("\n");
            } else { // no enter. 
                typingLetter = tVdata.getSelectionModel().getSelectedItem().getText();
            }

            root = fxmlLoader.load();

            this.FXMLRobotGrandPanelController = fxmlLoader.getController();
            Scene scene = new Scene(root);

            scene.setFill(null);
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.setFullScreen(true);
            //stage.setFullScreenExitHint("実行中");
            stage.setAlwaysOnTop(true);
            //stage.show();
            if (typingLetter.substring(0, 1).equals("@")) {
                if (typingLetter.substring(1, 2).equals("'")) { // Comment.
                    MsgBox.plain(typingLetter);
                } else if (typingLetter.substring(1, 2).equals(",")) { // MouseClick
                    ;
                    //MsgBox.plain("Click "+typingLetter.substring(2));
                    String point[] = (typingLetter.substring(2)).split(",");
                    MsgBox.plain("Click " + point[0] + " " + point[1]);
                    actor.Mouse.mouseClick(
                            Integer.parseInt(point[0]),
                            Integer.parseInt(point[1])
                    );
                } else if (typingLetter.substring(1, 2).equals("]")) { // 終端標識
                    data.get(i).setPassFlag("Typed");
                    //tVdata.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                    tVdata.getSelectionModel().select(++i);
                    tVdata.refresh();
                    tVdata.scrollTo(i);
                    return false;
                } else {
                    // Function type.
                    SendControlKey.sendControlKey(typingLetter.substring(1, 2));
                }
            } else { // Will type.
                System.out.println("これからタイプ" + typingLetter);
                btnSendToNext.setDisable(true);
                btnSendToNext.setVisible(false);
                ((Stage) this.anchorPane.getScene().getWindow()).setResizable(false);
                System.out.println("行タイプ結果"
                        + FXMLRobotGrandPanelController.doTyping(typingLetter));
                ((Stage) this.anchorPane.getScene().getWindow()).setResizable(true);
                btnSendToNext.setVisible(true);
                btnSendToNext.setDisable(false);
                btnSendToNext.requestFocus();
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        data.get(i).setPassFlag("Typed");
        //tVdata.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tVdata.getSelectionModel().select(++i);
        tVdata.refresh();
        tVdata.scrollTo(i);
        if (i == tVdata.getItems().size()) {
            MsgBox.info("停止標識なく最後の行を処理しました。");
            return false;
        }
        //btnSendToNext.setVisible(true);

        // ↓連続入力の時に具合が悪いのでコメントアウトした。
        // Mouse.mouseMove(this.lastMousePointX, this.lastMousePointY);
        return true;
    }

    @FXML
    void handleFileOpenAction(ActionEvent event) {
        System.out.println("Open");
        path = choiceFile();
        readText(path);
        tVdata.getSelectionModel().select(0); // init select first low.
    }

    @FXML
    Path choiceFile() {
        System.out.println("File Choice");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("FileChooser");
        if (path != null) {
            fileChooser.setInitialDirectory(path.getParent().toFile());
        }
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("All file", "*.*", "*"));
        File text = fileChooser.showOpenDialog(null);
        System.out.println(String.valueOf(text));
        return Paths.get(text.getPath());
    }

    @FXML
    void readText(Path path) {
        try {
            // List<String> lines = Files.lines(path).collect(Collectors.toList());
            List<String> lines = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.toList());
            if (lines.get(0).startsWith("\uFEFF")) {
                lines.set(0, lines.get(0).substring(1));
            }

//data.addAll(new Member(0,"FILE",path.getFileName().toString()));
            for (int i = 0; i < lines.size(); i++) {
                data.addAll(new Member(i + 1, "N/T", lines.get(i)));
            }
            /*
            if (lines.stream().anyMatch(pipe -> pipe.indexOf("|") > 0)) {
                System.out.println("Inc |");
            }
            lines.forEach(l -> {
                if (Stream.of(l).anyMatch(p -> p.equals("_"))) {
                    System.out.println("Inc _");
                };
                System.out.println(l);

            });
             */
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void getMouseClickPoint(ActionEvent event) {
        try {
            //((Stage) this.anchorPane.getScene().getWindow()).hide(); Able but ...
            //FXMLGrandPanelController.setFXMLDocumentController(this);

            Parent root = FXMLLoader.load(getClass().getResource("/mouseClickPoint/FXMLGrandPanel.fxml"));
            //root.setStyle("-fx-background-color: transparent");
            Scene scene = new Scene(root);
            scene.setFill(null);
            Stage stage = new Stage(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("流し込むウインドーのハンドル部をクリック");
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
            //((Stage) this.anchorPane.getScene().getWindow()).show();
            //FXMLGrandPanelController.setFXMLDocumentController(this);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void showPoint(ActionEvent event) {
        try {
            //((Stage) this.anchorPane.getScene().getWindow()).hide(); Able but ...
            //FXMLGrandPanelController.setFXMLDocumentController(this);

            Parent root = FXMLLoader.load(getClass().getResource("/showPointWindow/FXMLShowGrandPanel.fxml"));
            //root.setStyle("-fx-background-color: transparent");
            Scene scene = new Scene(root);
            scene.setFill(null);
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("座標を取得する箇所をクリック");
            stage.setAlwaysOnTop(false);
            stage.showAndWait();
            //((Stage) this.anchorPane.getScene().getWindow()).show();
            //FXMLGrandPanelController.setFXMLDocumentController(this);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void clearItems(ActionEvent event) {
        //error
        //this.tVdata.itemsProperty().getItems().removeAll(); 
        data.clear();
        tVdata.refresh();
    }

    @FXML
    void systemClose(ActionEvent event) {
        //this.anchorPane.getScene().getWindow().getScene();
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
        tVdata.itemsProperty().setValue(data);
        tVdata.setItems(data);

        this.tClineNumber.setCellValueFactory(new PropertyValueFactory<Member, Integer>("lineNumber"));
        this.tCpassFlag.setCellValueFactory(new PropertyValueFactory<Member, String>("passFlag"));
        this.tCtext.setCellValueFactory((new PropertyValueFactory<Member, String>("text")));

        this.btnClicEvent();

        this.btnSendToNext.setOnMouseClicked((MouseEvent e) -> {
            FXMLDocumentController.this.lastMousePointX = (int) Math.floor(e.getScreenX());
            FXMLDocumentController.this.lastMousePointY = (int) Math.floor(e.getScreenY());
        });
        /*
        tVdata.selectionModelProperty().addListener((Observable observable) -> {
            if (tVdata.getSelectionModel() != null) { // これ必要
                if (tVdata.getSelectionModel().getSelectedIndex() == 0) {
                    tVdata.getSelectionModel().select(1); // init select first low.
                }
            }
        });
         */
    }
    /*
    public void showAgain() {
        ((Stage) this.anchorPane.getScene().getWindow()).setIconified(false);
    }
     */
}
