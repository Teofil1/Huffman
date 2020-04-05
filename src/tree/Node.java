package tree;

public class Node{
    private Node leftChild, rightChild, parent;
    private String letter="";
    private String code="";
    private long data;
    private int passed = 0;


    public Node(String letter, long data) {
        this.letter = letter;
        this.data = data;
    }

    public Node(Node leftChild, Node rightChild, long data) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.data = data;
        this.leftChild.setParent(this);
        this.rightChild.setParent(this);
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
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


    @Override
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
    }
}
