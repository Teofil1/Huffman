package tree;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.Map;

public class ControllerHuffmanTree{

    @FXML
    Canvas canvas;

    HuffmanTree huffmanTree = new HuffmanTree();

    public void showHuffmanTree(Map<String, Long> letters){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Node topNode = huffmanTree.buildTree(letters);
        double sizeNode = 40;
        int depthTree = huffmanTree.getDepth(topNode);
        double widthCanvas = sizeNode*(Math.pow(2,depthTree))*2+4*sizeNode;
        double heightCanvas = sizeNode*(depthTree+1)+sizeNode*Math.pow(2,depthTree)*2+4*sizeNode;
        canvas.setWidth(widthCanvas);
        canvas.setHeight(heightCanvas);
        huffmanTree.drawTree(topNode, gc, widthCanvas/2,sizeNode, depthTree, sizeNode);
    }
}
