package tree;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class HuffmanTree {
    private HuffmanTree leftChild, rightChild, parent;
    private String character = "";
    private String code = "";
    private long data;
    private int passed = 0;
    private int depth = 0;

    private HuffmanTree(String character, long data) {
        this.character = character;
        this.data = data;
    }

    private HuffmanTree(HuffmanTree leftChild, HuffmanTree rightChild, long data, int depth) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.data = data;
        this.depth = depth;
        this.leftChild.parent = this;
        this.rightChild.parent = this;
    }

    static public HuffmanTree buildHuffmanTree(Map<String, Long> characters) {
        ArrayList<HuffmanTree> allHuffmanTrees = new ArrayList();
        int depth = 0;
        ArrayList<HuffmanTree> nodesInCurrentLevel = new ArrayList();
        for (Map.Entry<String, Long> entry : characters.entrySet()) {
            nodesInCurrentLevel.add(new HuffmanTree(entry.getKey(), entry.getValue()));
        }
        while (nodesInCurrentLevel.size() > 1) {
            depth++;
            Collections.sort(nodesInCurrentLevel, new Comparator<HuffmanTree>() {
                @Override
                public int compare(HuffmanTree o1, HuffmanTree o2) {
                    return (int) (o1.data - o2.data);
                }
            });
            ArrayList<HuffmanTree> nodesInNextLevel = new ArrayList();
            if (nodesInCurrentLevel.size() % 2 != 0) {
                nodesInCurrentLevel.get(nodesInCurrentLevel.size() - 1).deepen();
                nodesInNextLevel.add(nodesInCurrentLevel.remove(nodesInCurrentLevel.size() - 1));
            }
            for (int i = 0; i <= nodesInCurrentLevel.size() - 2; i += 2) {
                HuffmanTree leftChild = nodesInCurrentLevel.get(i);
                leftChild.code = "0";
                HuffmanTree rightChild = nodesInCurrentLevel.get(i + 1);
                rightChild.code = "1";
                nodesInNextLevel.add(new HuffmanTree(leftChild, rightChild, leftChild.data + rightChild.data, depth));
            }
            allHuffmanTrees.addAll(nodesInCurrentLevel);
            nodesInCurrentLevel = nodesInNextLevel;
        }
        allHuffmanTrees.addAll(nodesInCurrentLevel);

        HuffmanTree topHuffmanTree = allHuffmanTrees.get(allHuffmanTrees.size() - 1);
        topHuffmanTree.buildCodesPaths();
        /*for (HuffmanTree node : allHuffmanTrees) {
            System.out.println(node);
        }*/
        return topHuffmanTree;
    }


    public void drawHuffmanTree(GraphicsContext gc, double x, double y, double sizeNode) {
        gc.setFill(Color.GREEN);
        if (passed == 0) {
            passed++;
            if (leftChild != null) {
                y += (Math.pow(2, depth) * sizeNode) / 2;
                x -= (Math.pow(2, depth) * sizeNode) / 2;
                leftChild.drawHuffmanTree(gc, x, y, sizeNode);
            } else if (parent != null) {
                double nextY = y - (Math.pow(2, depth + 1) * sizeNode) / 2;
                double nextX;
                if (parent.rightChild == this) nextX = x - (Math.pow(2, depth + 1) * sizeNode) / 2;
                else nextX = x + (Math.pow(2, depth + 1) * sizeNode) / 2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x + sizeNode / 2, y + sizeNode / 2, nextX + sizeNode / 2, nextY + sizeNode / 2);
                gc.fillOval(x, y, sizeNode, sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(code.substring(code.length() - 1), x + sizeNode / 2, y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(data + ": \"" + character + "\"", x + 4, y + sizeNode / 2);
                parent.drawHuffmanTree(gc, nextX, nextY, sizeNode);
            } else {
                gc.setStroke(Color.BROWN);
                gc.setLineWidth((2 * sizeNode) / 3);
                gc.strokeLine(x + sizeNode / 2, y + sizeNode / 2, x + sizeNode / 2, sizeNode * Math.pow(2, depth) * 2);
                gc.fillOval(x, y, sizeNode, sizeNode);
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(1);
                gc.strokeText(data + ": \"" + character + "\"", x + 4, y + sizeNode / 2);
                zeroPassedFlags();
                return;
            }
        } else if (passed == 1) {
            passed++;
            if (rightChild != null) {
                y += (Math.pow(2, depth) * sizeNode) / 2;
                x += (Math.pow(2, depth) * sizeNode) / 2;
                rightChild.drawHuffmanTree(gc, x, y, sizeNode);
            } else {
                double nextY = y - (Math.pow(2, depth + 1) * sizeNode) / 2;
                double nextX;
                if (parent.rightChild == this) nextX = x - (Math.pow(2, depth + 1) * sizeNode) / 2;
                else nextX = x + (Math.pow(2, depth + 1) * sizeNode) / 2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x + sizeNode / 2, y + sizeNode / 2, nextX + sizeNode / 2, nextY + sizeNode / 2);
                gc.fillOval(x, y, sizeNode, sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(code.substring(code.length() - 1), x + sizeNode / 2, y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(data), x + sizeNode / 2 - 3, y + sizeNode / 2 + 3);
                parent.drawHuffmanTree(gc, nextX, nextY, sizeNode);
            }
        } else if (passed == 2) {
            if (parent != null) {
                double nextY = y - (Math.pow(2, depth + 1) * sizeNode) / 2;
                double nextX;
                if (parent.rightChild == this) nextX = x - (Math.pow(2, depth + 1) * sizeNode) / 2;
                else nextX = x + (Math.pow(2, depth + 1) * sizeNode) / 2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x + sizeNode / 2, y + sizeNode / 2, nextX + sizeNode / 2, nextY + sizeNode / 2);
                gc.fillOval(x, y, sizeNode, sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(code.substring(code.length() - 1), x + sizeNode / 2, y);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(data), x + sizeNode / 2 - 3, y + sizeNode / 2 + 3);
                parent.drawHuffmanTree(gc, nextX, nextY, sizeNode);
            } else {
                gc.setStroke(Color.BROWN);
                gc.setLineWidth((2 * sizeNode) / 3);
                gc.strokeLine(x + sizeNode / 2, y + sizeNode / 2, x + sizeNode / 2, sizeNode * Math.pow(2, depth) * 2);
                gc.fillOval(x, y, sizeNode, sizeNode);
                gc.setLineWidth(1);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(data), x + sizeNode / 2 - 3, y + sizeNode / 2 + 3);
                zeroPassedFlags();
                return;
            }
        }

    }

    private void deepen() {
        if (passed == 0) {
            passed++;
            if (leftChild != null) {
                leftChild.depth++;
                leftChild.deepen();
            } else if (parent != null) {
                parent.deepen();
            } else {
                depth++;
                zeroPassedFlags();
                return;
            }
        } else if (passed == 1) {
            passed++;
            if (rightChild != null) {
                rightChild.depth++;
                rightChild.deepen();
            } else {
                parent.deepen();
            }
        } else if (passed == 2) {
            if (parent != null) {
                parent.deepen();
            } else {
                depth++;
                zeroPassedFlags();
                return;
            }
        }

    }

    public Map<String, String> getCharactersCodes() {
        Map<String, String> charactersCodes = new HashMap<>();
        recursiveSearchCharactersCodes(charactersCodes);
        return charactersCodes;
    }

    private void recursiveSearchCharactersCodes(Map<String, String> charactersCodes){
        if (passed == 0) {
            passed++;
            if (leftChild != null) {
                leftChild.recursiveSearchCharactersCodes(charactersCodes);
            } else if (parent != null) {
                charactersCodes.put(character,code);
                parent.recursiveSearchCharactersCodes(charactersCodes);
            } else {
                charactersCodes.put(character,code);
                zeroPassedFlags();
                return;
            }
        } else if (passed == 1) {
            passed++;
            if (rightChild != null) {
                rightChild.recursiveSearchCharactersCodes(charactersCodes);
            } else {
                charactersCodes.put(character,code);
                parent.recursiveSearchCharactersCodes(charactersCodes);
            }
        } else if (passed == 2) {
            if (parent != null) {
                parent.recursiveSearchCharactersCodes(charactersCodes);
            } else {
                zeroPassedFlags();
                return;
            }
        }
    }



    private void buildCodesPaths() {
        if (passed == 0) {
            passed++;
            if (leftChild != null) {
                leftChild.code = code + "0";
                leftChild.buildCodesPaths();
            } else if (parent != null) {
                parent.buildCodesPaths();
            } else {
                zeroPassedFlags();
                return;
            }
        } else if (passed == 1) {
            passed++;
            if (rightChild != null) {
                rightChild.code = code + "1";
                rightChild.buildCodesPaths();
            } else {
                parent.buildCodesPaths();
            }
        } else if (passed == 2) {
            if (parent != null) {
                parent.buildCodesPaths();
            } else {
                zeroPassedFlags();
                return;
            }
        }

    }

    private void zeroPassedFlags() {
        if (passed == 2) {
            passed--;
            if (leftChild != null) leftChild.zeroPassedFlags();
            else parent.zeroPassedFlags();
        } else if (passed == 1) {
            passed--;
            if (rightChild != null) rightChild.zeroPassedFlags();
            else if (parent != null) parent.zeroPassedFlags();
            else return;
        } else if (passed == 0) {
            if (parent != null) parent.zeroPassedFlags();
            else return;
        }
    }


    public int getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "data=" + data +
                ", depth=" + depth +
                ", character=" + character +
                ", " + code +
                '}';
    }
}
