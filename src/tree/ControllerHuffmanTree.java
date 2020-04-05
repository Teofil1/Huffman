package tree;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.Map;

public class ControllerHuffmanTree{

    @FXML
    Canvas canvas;
    /*Tree tree = new Tree();*/

    public void showHuffmanTree(Map<String, Long> letters){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Tree huffmanTree = Tree.buildHuffmanTree(letters);
        System.out.println(huffmanTree.getDepth());

        double sizeNode = 40;
        double widthCanvas = sizeNode*(Math.pow(2,huffmanTree.getDepth()))*2+4*sizeNode;
        double heightCanvas = sizeNode*(huffmanTree.getDepth()+1)+sizeNode*Math.pow(2,huffmanTree.getDepth())*2+4*sizeNode;
        canvas.setWidth(widthCanvas);
        canvas.setHeight(heightCanvas);

        huffmanTree.drawTree(gc, widthCanvas/2,sizeNode, sizeNode);
    }
}
