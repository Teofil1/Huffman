package tree;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;

import java.util.Map;
import java.util.stream.Collectors;

public class ControllerHuffmanTree{

    @FXML
    Canvas canvas;

    @FXML
    TextArea areaCharactersCode;

    public void showHuffmanTree(Map<String, Long> characters){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        HuffmanTree huffmanTree = HuffmanTree.buildHuffmanTree(characters);

        double sizeNode = 40;
        double widthCanvas = sizeNode*(Math.pow(2,huffmanTree.getDepth()))*2+4*sizeNode;
        double heightCanvas = sizeNode*(huffmanTree.getDepth()+1)+sizeNode*Math.pow(2,huffmanTree.getDepth())*2+4*sizeNode;
        canvas.setWidth(widthCanvas);
        canvas.setHeight(heightCanvas);

        huffmanTree.drawHuffmanTree(gc, widthCanvas/2,heightCanvas/2, sizeNode);
        Map<String, String> charactersCodes = huffmanTree.getCharactersCodes();

        String mapCharactersCodesAsString = charactersCodes.keySet().stream()
                .map(key -> key + " : " + charactersCodes.get(key))
                .collect(Collectors.joining("\n"));

        areaCharactersCode.setText(mapCharactersCodesAsString);

    }
}
