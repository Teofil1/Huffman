package tree;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class ControllerHuffmanTree {

    @FXML
    Canvas canvas;

    @FXML
    TextArea areaCharactersCode;

    @FXML
    TextArea areaEncodedText;

    @FXML
    TextArea areaCharactersStatistic;

    @FXML
    TextField entropyField;

    @FXML
    TextField averageWordLengthField;

    public void showHuffmanTree(String text) {
        Map<String, Long> characters = getTextAsSortedMapCharactersFrequent(text);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        HuffmanTree huffmanTree = HuffmanTree.buildHuffmanTree(characters);

        double sizeNode = 30;
        double widthCanvas = sizeNode * (Math.pow(2, huffmanTree.getDepth())) + sizeNode * (huffmanTree.getDepth() + 1) / 2;
        double heightCanvas = sizeNode * Math.pow(2, huffmanTree.getDepth() - 1) + 300;
        if (widthCanvas < 100) widthCanvas = 200;
        /*if(widthCanvas > 4096 || heightCanvas > 4096){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Sorry, your screen resolution doesn't allow you to display this tree");
            alert.showAndWait();
        } else{*/
            canvas.setWidth(widthCanvas);
            canvas.setHeight(heightCanvas);
            huffmanTree.drawHuffmanTree(gc, widthCanvas / 2, heightCanvas - 200, sizeNode);
        //}

        showCharactersStatistics(text);
        showCharactersCodes(huffmanTree);
        showEntropy(text);
        showAverageWordLength(text, huffmanTree);
        showEncodedText(text, huffmanTree);
    }



    public String textToHuffmansEncode(String text, Map charactersCodes) {
        String encodedText = "";
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++)
            encodedText += charactersCodes.get(String.valueOf(chars[i]));
        return encodedText;
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

    public void showEncodedText(String text, HuffmanTree huffmanTree) {
        Map<String, String> charactersCodes = huffmanTree.getCharactersCodes();
        areaEncodedText.setText(textToHuffmansEncode(text, charactersCodes));
    }

    public void showCharactersCodes(HuffmanTree huffmanTree) {
        Map<String, String> charactersCodes = huffmanTree.getCharactersCodes();
        String mapCharactersCodesAsString = charactersCodes.keySet().stream()
                .map(key -> "\""+key + "\" : " + charactersCodes.get(key))
                .collect(Collectors.joining("\n"));

        areaCharactersCode.setText(mapCharactersCodesAsString);
    }

    public void showCharactersStatistics(String text) {
        Integer textLenght = text.length();
        Map<String, Long> mapCharactersFrequent = getTextAsSortedMapCharactersFrequent(text);

        String mapAsString = mapCharactersFrequent.keySet().stream()
                .map(key -> "\""+key + "\" : " + mapCharactersFrequent.get(key) + "  "
                        + String.format("%.2f", ((double)mapCharactersFrequent.get(key)/textLenght)*100) + "%")
                .collect(Collectors.joining("\n"));
        areaCharactersStatistic.setText(mapAsString);
    }

    public void showEntropy(String text) {
        entropyField.setText(String.format("%.4f", calculateEntropy(text)));
    }

    public void showAverageWordLength(String text, HuffmanTree huffmanTree) {
        averageWordLengthField.setText(String.format("%.4f", calculateAverageWordLength(text, huffmanTree)));
    }

    public Double calculateAverageWordLength(String text, HuffmanTree huffmanTree) {
        Double averageWordLength=0.0;
        Map<String, Long> mapCharactersFrequent = getTextAsSortedMapCharactersFrequent(text);
        Map<String, String> charactersCodes = huffmanTree.getCharactersCodes();
        for (Map.Entry<String, String> entry : charactersCodes.entrySet()){
            double p = (double) mapCharactersFrequent.get(entry.getKey())/text.length();
            averageWordLength += entry.getValue().length()*p;
        }
        return averageWordLength;
    }

    public static double log2(double d) {
        return Math.log(d)/Math.log(2.0);
    }

    public Double calculateEntropy(String text) {
        Integer textLenght = text.length();
        Map<String, Long> mapCharactersFrequent = getTextAsSortedMapCharactersFrequent(text);
        Double entropy=0.0;
        for (Map.Entry<String, Long> entry : mapCharactersFrequent.entrySet()) {
            double p = (double)entry.getValue()/textLenght;
            entropy += p*log2(1/p);
        }
        return entropy;
    }


}
