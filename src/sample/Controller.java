package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tree.ControllerHuffmanTree;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Controller  {

    @FXML
    TextArea inputText;

    @FXML
    TextArea areaCharactersStatistic;

    @FXML
    TextField entropyField;

    @FXML
    Button buttonShowTree;

    @FXML
    public void showCharactersStatistic(ActionEvent event) {
        String text = inputText.getText();
        if(!text.equals("")){
            showCharactersStatistics(text);
            showEntropy(text);
            buttonShowTree.setDisable(false);
        }
        else{
            buttonShowTree.setDisable(true);
        }

    }

    @FXML
    public void showSceneWithTree(ActionEvent event) {
        Stage treeStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = (Pane) loader.load(getClass().getResource("/tree/tree.fxml").openStream());
            ControllerHuffmanTree controllerHuffmanTree = (ControllerHuffmanTree) loader.getController();
            controllerHuffmanTree.showHuffmanTree(getTextAsSortedMapCharactersFrequent(inputText.getText()));
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

    public Map<String, Long> getTextAsSortedMapCharactersFrequent(String text){
        Map<String, Long> frequentCharacters = Arrays.stream(
                text.split("")).collect(
                Collectors.groupingBy(c -> c, Collectors.counting()));

        Map<String, Long> sortedFrequentChars = frequentCharacters
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));

        return sortedFrequentChars;
    }

    public void showCharactersStatistics(String text) {
        Integer textLenght = text.length();
        Map<String, Long> mapCharactersFrequent = getTextAsSortedMapCharactersFrequent(text);

        String mapAsString = mapCharactersFrequent.keySet().stream()
                .map(key -> key + " : " + mapCharactersFrequent.get(key) + "  "
                        + String.format("%.2f", ((double)mapCharactersFrequent.get(key)/textLenght)*100) + "%")
                .collect(Collectors.joining("\n"));
        areaCharactersStatistic.setText(mapAsString);
    }

    public void showEntropy(String text) {
        entropyField.setText(String.format("%.4f", calculateEntropy(text)));
    }

    public Double calculateEntropy(String text) {
        Integer textLenght = text.length();
        Map<String, Long> mapCharactersFrequent = getTextAsSortedMapCharactersFrequent(text);
        Double entropy=0.0;
        for (Map.Entry<String, Long> entry : mapCharactersFrequent.entrySet()) {
            double p = (double)entry.getValue()/textLenght;
            entropy += entry.getValue()*(p*log2(1/p));
        }
        return entropy;
    }

    public static double log2(double d) {
        return Math.log(d)/Math.log(2.0);
    }

}
