package tree;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class HuffmanTree {

    private int depth=0;
    private ArrayList<Node> allNodes = new ArrayList();
    private Node topNode;

    public Node buildTree(Map<String, Long> letters) {
        depth=0;
        ArrayList<Node> nodesInCurrentLevel = new ArrayList();
        for (Map.Entry<String, Long> entry : letters.entrySet()) {
            nodesInCurrentLevel.add(new Node(entry.getKey(), entry.getValue()));
        }
        while (nodesInCurrentLevel.size() > 1) {
            depth++;
            Collections.sort(nodesInCurrentLevel, new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return (int) (o1.getData() - o2.getData());
                }
            });
            ArrayList<Node> nodesInNextLevel = new ArrayList();
            if (nodesInCurrentLevel.size() % 2 != 0) {
                nodesInNextLevel.add(nodesInCurrentLevel.remove(nodesInCurrentLevel.size() - 1));
            }
            for (int i = 0; i <= nodesInCurrentLevel.size() - 2; i += 2) {
                Node leftChild = nodesInCurrentLevel.get(i);
                leftChild.setCode("0");
                Node rightChild = nodesInCurrentLevel.get(i + 1);
                rightChild.setCode("1");
                nodesInNextLevel.add(new Node(leftChild, rightChild, leftChild.getData() + rightChild.getData()));
            }
            allNodes.addAll(nodesInCurrentLevel);
            nodesInCurrentLevel = nodesInNextLevel;
        }
        allNodes.addAll(nodesInCurrentLevel);

       /* for (Node node : allNodes) {
            System.out.println(node);
        }
*/
        topNode = allNodes.get(allNodes.size()-1);
        buildCodesPaths(topNode);
        return topNode;
    }



    private void buildCodesPaths(Node topNode){
        if(topNode.getPassed() == 0){
            topNode.setPassed(topNode.getPassed()+1);
            if(topNode.getLeftChild() != null) {
                topNode.getLeftChild().setCode(topNode.getCode()+"0");
                buildCodesPaths(topNode.getLeftChild());
            }
            else if(topNode.getParent() != null){
                buildCodesPaths(topNode.getParent());
            }
            else {
                zeroPassedFlags(topNode);
                return;
            }
        }
        else if(topNode.getPassed() == 1){
            topNode.setPassed(topNode.getPassed()+1);
            if(topNode.getRightChild() != null) {
                topNode.getRightChild().setCode(topNode.getCode()+"1");
                buildCodesPaths(topNode.getRightChild());
            }
            else {
                buildCodesPaths(topNode.getParent());
            }
        }
        else if(topNode.getPassed() == 2 ){
            if(topNode.getParent() != null) {
                buildCodesPaths(topNode.getParent());
            }
            else {
                zeroPassedFlags(topNode);
                return;
            }
        }

    }

    public void drawTree(Node topNode, GraphicsContext gc, double x , double y, int depth, double sizeNode){
        gc.setFill(Color.GREEN);
        if(topNode.getPassed() == 0){
            topNode.setPassed(topNode.getPassed()+1);
            if(topNode.getLeftChild() != null) {
                y+=(Math.pow(2,depth)*sizeNode)/2;
                x-=(Math.pow(2,depth)*sizeNode)/2;
                depth--;
                drawTree(topNode.getLeftChild(), gc, x, y, depth, sizeNode);
            }
            else if(topNode.getParent() != null){
                depth++;
                double nextY= y-(Math.pow(2,depth)*sizeNode)/2;
                double nextX;
                if(topNode.getParent().getRightChild() == topNode) nextX=x-(Math.pow(2,depth)*sizeNode)/2;
                else nextX=x+(Math.pow(2,depth)*sizeNode)/2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,nextX+sizeNode/2, nextY+sizeNode/2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(topNode.getCode().substring(topNode.getCode().length() - 1),x+sizeNode/2,y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(topNode.getData()+": \""+topNode.getLetter()+"\"",x+4,y+sizeNode/2);
                drawTree(topNode.getParent(), gc, nextX, nextY, depth, sizeNode);
            }
            else{
                gc.setStroke(Color.BROWN);
                gc.setLineWidth((2*sizeNode)/3);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,x+sizeNode/2, sizeNode*Math.pow(2,depth)*2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(1);
                gc.strokeText(topNode.getData()+": \""+topNode.getLetter()+"\"",x+4,y+sizeNode/2);
                zeroPassedFlags(topNode);
                return;
            }
        }
        else if(topNode.getPassed() == 1){
            topNode.setPassed(topNode.getPassed()+1);
            if(topNode.getRightChild() != null) {
                y+=(Math.pow(2,depth)*sizeNode)/2;
                x+=(Math.pow(2,depth)*sizeNode)/2;
                depth--;
                drawTree(topNode.getRightChild(), gc, x, y, depth, sizeNode);
            }
            else {
                depth++;
                double nextY= y-(Math.pow(2,depth)*sizeNode)/2;
                double nextX;
                if(topNode.getParent().getRightChild() == topNode) nextX=x-(Math.pow(2,depth)*sizeNode)/2;
                else nextX=x+(Math.pow(2,depth)*sizeNode)/2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,nextX+sizeNode/2, nextY+sizeNode/2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(topNode.getCode().substring(topNode.getCode().length() - 1),x+sizeNode/2,y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(topNode.getData()),x+sizeNode/2-3,y+sizeNode/2+3);
                drawTree(topNode.getParent(), gc, nextX, nextY, depth, sizeNode);
            }
        }
        else if(topNode.getPassed() == 2 ){
            if(topNode.getParent() != null) {
                depth++;
                double nextY= y-(Math.pow(2,depth)*sizeNode)/2;
                double nextX;
                if(topNode.getParent().getRightChild() == topNode) nextX=x-(Math.pow(2,depth)*sizeNode)/2;
                else nextX=x+(Math.pow(2,depth)*sizeNode)/2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,nextX+sizeNode/2, nextY+sizeNode/2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(topNode.getCode().substring(topNode.getCode().length() - 1),x+sizeNode/2,y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(topNode.getData()),x+sizeNode/2-3,y+sizeNode/2+3);
                drawTree(topNode.getParent(), gc, nextX, nextY, depth, sizeNode);
            }
            else {
                gc.setStroke(Color.BROWN);
                gc.setLineWidth((2*sizeNode)/3);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,x+sizeNode/2, sizeNode*Math.pow(2,depth)*2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setLineWidth(1);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(topNode.getData()),x+sizeNode/2-3,y+sizeNode/2+3);
                zeroPassedFlags(topNode);
                return;
            }
        }

    }

    public void zeroPassedFlags(Node topNode){
        if(topNode.getPassed() == 2){
            topNode.setPassed(topNode.getPassed()-1);
            if(topNode.getLeftChild() != null) zeroPassedFlags(topNode.getLeftChild());
            else zeroPassedFlags(topNode.getParent());
        }
        else if(topNode.getPassed() == 1){
            topNode.setPassed(topNode.getPassed()-1);
            if(topNode.getRightChild() != null) zeroPassedFlags(topNode.getRightChild());
            else if(topNode.getParent() != null) zeroPassedFlags(topNode.getParent());
            else return;
        }
        else if(topNode.getPassed() == 0 ){
            if(topNode.getParent() != null) zeroPassedFlags(topNode.getParent());
            else return;
        }
    }


    public int getDepth(Node topNode) {
        return depth;
    }


}
