/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package showPointWindow;

import mouseClickPoint.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import robot7.FXMLDocumentController;
import robot7.MsgBox;
import robot7.Robot7;

/**
 * FXML Controller class
 *
 * @author kyokuto
 */
public class FXMLShowGrandPanelController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Pane grandPane;

    //@FXML
    //private FXMLDocumentController FXMLDocumentController;
    private void showClickPos() {
        grandPane.setOnMouseClicked(
                e -> {
                    
                      anchorPane.getScene().getWindow().hide();
                    MsgBox.info(
                "X= "+String.valueOf((int)e.getX())+" Y= "+String.valueOf((int)e.getY()));
                  
                });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //sendClickPos();
        anchorPane.setOpacity(0.6);
        showClickPos();
//this.anchorPane.setStyle("-fx-background-radius: 10;-fx-background-color: rgba(0,0,0,0.3);");
        /*
                    FXMLDocumentController.gettFPointX()
                           .setText(String.valueOf(e.getScreenX()));
                    FXMLDocumentController.gettFPointY()
                            .setText(String.valueOf(e.getScreenY()));
         */
        // anchorPane.getScene().getWindow().hide();
    }

}
