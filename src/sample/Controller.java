package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tree.ControllerHuffmanTree;

import java.io.IOException;

public class Controller  {

    @FXML
    TextArea inputText;

    @FXML
    public void showSceneWithTree(ActionEvent event) {
        Stage treeStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = (Pane) loader.load(getClass().getResource("/tree/tree.fxml").openStream());
            ControllerHuffmanTree controllerHuffmanTree = (ControllerHuffmanTree) loader.getController();
            controllerHuffmanTree.showHuffmanTree(inputText.getText());
            Scene scene = new Scene(root);
            treeStage.setScene(scene);
            treeStage.setTitle("Huffman");
            treeStage.setMinHeight(365);
            treeStage.setMinWidth(610);
            treeStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
