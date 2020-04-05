package tree;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class Tree {
    private Tree leftChild, rightChild, parent;
    private String letter="";
    private String code="";
    private long data;
    private int passed = 0;
    private int depth=0;

    private Tree(String letter, long data) {
        this.letter = letter;
        this.data = data;
    }

    private Tree(Tree leftChild, Tree rightChild, long data, int depth) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.data = data;
        this.depth = depth;
        this.leftChild.setParent(this);
        this.rightChild.setParent(this);
    }

    static public Tree buildHuffmanTree(Map<String, Long> letters) {
        ArrayList<Tree> allTrees = new ArrayList();
        int depth=0;
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
                nodesInNextLevel.add(new Tree(leftChild, rightChild, leftChild.getData() + rightChild.getData(), depth));
            }
            allTrees.addAll(nodesInCurrentLevel);
            nodesInCurrentLevel = nodesInNextLevel;
        }
        allTrees.addAll(nodesInCurrentLevel);

        Tree topTree = allTrees.get(allTrees.size()-1);
        topTree.buildCodesPaths();
        return topTree;
    }

    public void drawTree(GraphicsContext gc, double x , double y, double sizeNode){
        gc.setFill(Color.GREEN);
        if(passed == 0){
            passed++;
            if(getLeftChild() != null) {
                y+=(Math.pow(2,depth)*sizeNode)/2;
                x-=(Math.pow(2,depth)*sizeNode)/2;
//                depth--;
                getLeftChild().drawTree(gc, x, y, sizeNode);
            }
            else if(getParent() != null){
//                depth++;
                double nextY= y-(Math.pow(2,depth+1)*sizeNode)/2;
                double nextX;
                if(getParent().getRightChild() == this) nextX=x-(Math.pow(2,depth+1)*sizeNode)/2;
                else nextX=x+(Math.pow(2,depth+1)*sizeNode)/2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,nextX+sizeNode/2, nextY+sizeNode/2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(getCode().substring(getCode().length() - 1),x+sizeNode/2,y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(getData()+": \""+ getLetter()+"\"",x+4,y+sizeNode/2);
                getParent().drawTree(gc, nextX, nextY, sizeNode);
            }
            else{
                gc.setStroke(Color.BROWN);
                gc.setLineWidth((2*sizeNode)/3);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,x+sizeNode/2, sizeNode*Math.pow(2,depth)*2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(1);
                gc.strokeText(getData()+": \""+ getLetter()+"\"",x+4,y+sizeNode/2);
                zeroPassedFlags();
                return;
            }
        }
        else if(passed == 1){
            passed++;
            if(getRightChild() != null) {
                y+=(Math.pow(2,depth)*sizeNode)/2;
                x+=(Math.pow(2,depth)*sizeNode)/2;
//                depth--;
                getRightChild().drawTree(gc, x, y, sizeNode);
            }
            else {
//                depth++;
                double nextY= y-(Math.pow(2,depth+1)*sizeNode)/2;
                double nextX;
                if(getParent().getRightChild() == this) nextX=x-(Math.pow(2,depth+1)*sizeNode)/2;
                else nextX=x+(Math.pow(2,depth+1)*sizeNode)/2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,nextX+sizeNode/2, nextY+sizeNode/2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(getCode().substring(getCode().length() - 1),x+sizeNode/2,y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(getData()),x+sizeNode/2-3,y+sizeNode/2+3);
                getParent().drawTree(gc, nextX, nextY, sizeNode);
            }
        }
        else if(passed == 2 ){
            if(getParent() != null) {
               // depth++;
                double nextY= y-(Math.pow(2,depth+1)*sizeNode)/2;
                double nextX;
                if(getParent().getRightChild() == this) nextX=x-(Math.pow(2,depth+1)*sizeNode)/2;
                else nextX=x+(Math.pow(2,depth+1)*sizeNode)/2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,nextX+sizeNode/2, nextY+sizeNode/2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(getCode().substring(getCode().length() - 1),x+sizeNode/2,y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(getData()),x+sizeNode/2-3,y+sizeNode/2+3);
                getParent().drawTree(gc, nextX, nextY, sizeNode);
            }
            else {
                gc.setStroke(Color.BROWN);
                gc.setLineWidth((2*sizeNode)/3);
                gc.strokeLine(x+sizeNode/2,y+sizeNode/2,x+sizeNode/2, sizeNode*Math.pow(2,depth)*2);
                gc.fillOval(x, y, sizeNode,sizeNode);
                gc.setLineWidth(1);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(getData()),x+sizeNode/2-3,y+sizeNode/2+3);
                zeroPassedFlags();
                return;
            }
        }

    }

    private void buildCodesPaths(){
        if(getPassed() == 0){
           setPassed(getPassed()+1);
            if(getLeftChild() != null) {
                getLeftChild().setCode(getCode()+"0");
                getLeftChild().buildCodesPaths();
            }
            else if(getParent() != null){
                getParent().buildCodesPaths();
            }
            else {
                zeroPassedFlags();
                return;
            }
        }
        else if(getPassed() == 1){
            setPassed(getPassed()+1);
            if(getRightChild() != null) {
                getRightChild().setCode(getCode()+"1");
                getRightChild().buildCodesPaths();
            }
            else {
                getParent().buildCodesPaths();
            }
        }
        else if(getPassed() == 2 ){
            if(getParent() != null) {
                getParent().buildCodesPaths();
            }
            else {
                zeroPassedFlags();
                return;
            }
        }

    }

    public void zeroPassedFlags(){
        if(getPassed() == 2){
            setPassed(getPassed()-1);
            if(getLeftChild() != null) getLeftChild().zeroPassedFlags();
            else getParent().zeroPassedFlags();
        }
        else if(getPassed() == 1){
            setPassed(getPassed()-1);
            if(getRightChild() != null) getRightChild().zeroPassedFlags();
            else if(getParent() != null) getParent().zeroPassedFlags();
            else return;
        }
        else if(getPassed() == 0 ){
            if(getParent() != null) getParent().zeroPassedFlags();
            else return;
        }
    }



    public Tree getParent() {
        return parent;
    }

    public void setParent(Tree parent) {
        this.parent = parent;
    }

    public Tree getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Tree leftChild) {
        this.leftChild = leftChild;
    }

    public Tree getRightChild() {
        return rightChild;
    }

    public void setRightChild(Tree rightChild) {
        this.rightChild = rightChild;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    /*@Override
    public String toString() {
        if(parent == null)
            return "Node{" +
                "leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                ", parent=null" +
                ", letter='" + letter + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                ", passed=" + passed +
                '}';
        else
            return "Node{" +
                    "leftChild=" + leftChild +
                    ", rightChild=" + rightChild +
                    ", parent=" + parent.getData() +
                    ", letter='" + letter + '\'' +
                    ", code='" + code + '\'' +
                    ", data=" + data +
                    ", passed=" + passed +
                    '}';
    }*/
}
