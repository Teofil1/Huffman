package tree;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class HuffmanTree {

   /* private int depth=0;
    private Tree topTree;*/

    /*public Tree buildTree(Map<String, Long> letters) {
        ArrayList<Tree> allTrees = new ArrayList();
        depth=0;
        ArrayList<Tree> nodesInCurrentLevel = new ArrayList();
        for (Map.Entry<String, Long> entry : letters.entrySet()) {
            nodesInCurrentLevel.add(new Tree(entry.getKey(), entry.getValue()));
        }
        while (nodesInCurrentLevel.size() > 1) {
            depth++;
            Collections.sort(nodesInCurrentLevel, new Comparator<Tree>() {
                @Override
                public int compare(Tree o1, Tree o2) {
                    return (int) (o1.getData() - o2.getData());
                }
            });
            ArrayList<Tree> nodesInNextLevel = new ArrayList();
            if (nodesInCurrentLevel.size() % 2 != 0) {
                nodesInNextLevel.add(nodesInCurrentLevel.remove(nodesInCurrentLevel.size() - 1));
            }
            for (int i = 0; i <= nodesInCurrentLevel.size() - 2; i += 2) {
                Tree leftChild = nodesInCurrentLevel.get(i);
                leftChild.setCode("0");
                Tree rightChild = nodesInCurrentLevel.get(i + 1);
                rightChild.setCode("1");
                nodesInNextLevel.add(new Tree(leftChild, rightChild, leftChild.getData() + rightChild.getData()));
            }
            allTrees.addAll(nodesInCurrentLevel);
            nodesInCurrentLevel = nodesInNextLevel;
        }
        allTrees.addAll(nodesInCurrentLevel);

       *//* for (Node node : allNodes) {
            System.out.println(node);
        }
*//*
        topTree = allTrees.get(allTrees.size()-1);
        buildCodesPaths(topTree);
        return topTree;
    }*/



    /*private void buildCodesPaths(Tree topTree){
        if(topTree.getPassed() == 0){
            topTree.setPassed(topTree.getPassed()+1);
            if(topTree.getLeftChild() != null) {
                topTree.getLeftChild().setCode(topTree.getCode()+"0");
                buildCodesPaths(topTree.getLeftChild());
            }
            else if(topTree.getParent() != null){
                buildCodesPaths(topTree.getParent());
            }
            else {
                zeroPassedFlags(topTree);
                return;
            }
        }
        else if(topTree.getPassed() == 1){
            topTree.setPassed(topTree.getPassed()+1);
            if(topTree.getRightChild() != null) {
                topTree.getRightChild().setCode(topTree.getCode()+"1");
                buildCodesPaths(topTree.getRightChild());
            }
            else {
                buildCodesPaths(topTree.getParent());
            }
        }
        else if(topTree.getPassed() == 2 ){
            if(topTree.getParent() != null) {
                buildCodesPaths(topTree.getParent());
            }
            else {
                zeroPassedFlags(topTree);
                return;
            }
        }

    }*/

    /*public void drawTree(Tree topTree, GraphicsContext gc, double x , double y, int depth, double sizeNode){
        gc.setFill(Color.GREEN);
        if(topTree.getPassed() == 0){
            topTree.setPassed(topTree.getPassed()+1);
            if(topTree.getLeftChild() != null) {
                y+=(Math.pow(2,depth)*sizeNode)/2;
                x-=(Math.pow(2,depth)*sizeNode)/2;
                depth--;
                drawTree(topTree.getLeftChild(), gc, x, y, depth, sizeNode);
            }
            else if(topTree.getParent() != null){
                depth++;
                double nextY= y-(Math.pow(2,depth)*sizeNode)/2;
                double nextX;
                if(topTree.getParent().getRightChild() == topTree) nextX=x-(Math.pow(2,depth)*sizeNode)/2;
                else nextX=x+(Math.pow(2,depth)*sizeNode)/2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,nextX+sizeNode/2, nextY+sizeNode/2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(topTree.getCode().substring(topTree.getCode().length() - 1),x+sizeNode/2,y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(topTree.getData()+": \""+ topTree.getLetter()+"\"",x+4,y+sizeNode/2);
                drawTree(topTree.getParent(), gc, nextX, nextY, depth, sizeNode);
            }
            else{
                gc.setStroke(Color.BROWN);
                gc.setLineWidth((2*sizeNode)/3);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,x+sizeNode/2, sizeNode*Math.pow(2,depth)*2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(1);
                gc.strokeText(topTree.getData()+": \""+ topTree.getLetter()+"\"",x+4,y+sizeNode/2);
                zeroPassedFlags(topTree);
                return;
            }
        }
        else if(topTree.getPassed() == 1){
            topTree.setPassed(topTree.getPassed()+1);
            if(topTree.getRightChild() != null) {
                y+=(Math.pow(2,depth)*sizeNode)/2;
                x+=(Math.pow(2,depth)*sizeNode)/2;
                depth--;
                drawTree(topTree.getRightChild(), gc, x, y, depth, sizeNode);
            }
            else {
                depth++;
                double nextY= y-(Math.pow(2,depth)*sizeNode)/2;
                double nextX;
                if(topTree.getParent().getRightChild() == topTree) nextX=x-(Math.pow(2,depth)*sizeNode)/2;
                else nextX=x+(Math.pow(2,depth)*sizeNode)/2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,nextX+sizeNode/2, nextY+sizeNode/2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(topTree.getCode().substring(topTree.getCode().length() - 1),x+sizeNode/2,y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(topTree.getData()),x+sizeNode/2-3,y+sizeNode/2+3);
                drawTree(topTree.getParent(), gc, nextX, nextY, depth, sizeNode);
            }
        }
        else if(topTree.getPassed() == 2 ){
            if(topTree.getParent() != null) {
                depth++;
                double nextY= y-(Math.pow(2,depth)*sizeNode)/2;
                double nextX;
                if(topTree.getParent().getRightChild() == topTree) nextX=x-(Math.pow(2,depth)*sizeNode)/2;
                else nextX=x+(Math.pow(2,depth)*sizeNode)/2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,nextX+sizeNode/2, nextY+sizeNode/2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(topTree.getCode().substring(topTree.getCode().length() - 1),x+sizeNode/2,y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(topTree.getData()),x+sizeNode/2-3,y+sizeNode/2+3);
                drawTree(topTree.getParent(), gc, nextX, nextY, depth, sizeNode);
            }
            else {
                gc.setStroke(Color.BROWN);
                gc.setLineWidth((2*sizeNode)/3);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,x+sizeNode/2, sizeNode*Math.pow(2,depth)*2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setLineWidth(1);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(topTree.getData()),x+sizeNode/2-3,y+sizeNode/2+3);
                zeroPassedFlags(topTree);
                return;
            }
        }

    }*/

    /*public void zeroPassedFlags(Tree topTree){
        if(topTree.getPassed() == 2){
            topTree.setPassed(topTree.getPassed()-1);
            if(topTree.getLeftChild() != null) zeroPassedFlags(topTree.getLeftChild());
            else zeroPassedFlags(topTree.getParent());
        }
        else if(topTree.getPassed() == 1){
            topTree.setPassed(topTree.getPassed()-1);
            if(topTree.getRightChild() != null) zeroPassedFlags(topTree.getRightChild());
            else if(topTree.getParent() != null) zeroPassedFlags(topTree.getParent());
            else return;
        }
        else if(topTree.getPassed() == 0 ){
            if(topTree.getParent() != null) zeroPassedFlags(topTree.getParent());
            else return;
        }
    }


    public int getDepth(Tree topTree) {
        return depth;
    }*/


}
