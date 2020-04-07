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
        ArrayList<HuffmanTree> allHuffmanSubTrees = new ArrayList();
        int level = 0;
        ArrayList<HuffmanTree> SubTreesInCurrentLevel = new ArrayList();
        for (Map.Entry<String, Long> entry : characters.entrySet()) {
            SubTreesInCurrentLevel.add(new HuffmanTree(entry.getKey(), entry.getValue()));
        }

        while (SubTreesInCurrentLevel.size() > 1) {


            Collections.sort(SubTreesInCurrentLevel, new Comparator<HuffmanTree>() {
                @Override
                public int compare(HuffmanTree o1, HuffmanTree o2) {
                    return (int) (o1.data - o2.data);
                }
            });
            ArrayList<HuffmanTree> SubTreesInNextLevel = new ArrayList();
            if (SubTreesInCurrentLevel.size() > 2) {
                for (int i=0; i < SubTreesInCurrentLevel.size(); i++) {
                    for(int j=2; j < i; j++){
                        if (SubTreesInCurrentLevel.get(i).data > SubTreesInCurrentLevel.get(j - 1).data + SubTreesInCurrentLevel.get(j - 2).data) {
                            SubTreesInCurrentLevel.get(i).deepen();
                            SubTreesInNextLevel.add(SubTreesInCurrentLevel.remove(i));
                            i--;
                            break;
                        }
                    }
                }
            }
            if (SubTreesInCurrentLevel.size() % 2 != 0) {
                SubTreesInCurrentLevel.get(SubTreesInCurrentLevel.size() - 1).deepen();
                SubTreesInNextLevel.add(SubTreesInCurrentLevel.remove(SubTreesInCurrentLevel.size() - 1));
            }

            for (int i = 0; i <= SubTreesInCurrentLevel.size() - 2; i += 2) {
                HuffmanTree leftChild = SubTreesInCurrentLevel.get(i);
                leftChild.code = "0";
                HuffmanTree rightChild = SubTreesInCurrentLevel.get(i + 1);
                rightChild.code = "1";
                SubTreesInNextLevel.add(new HuffmanTree(leftChild, rightChild, leftChild.data + rightChild.data, level+1));
            }
            /*System.out.println("LEVEL: "+level);
            for (HuffmanTree node : SubTreesInCurrentLevel) {
                System.out.println(node.data+" level: "+node.depth);
            }*/
            allHuffmanSubTrees.addAll(SubTreesInCurrentLevel);
            SubTreesInCurrentLevel = SubTreesInNextLevel;
            level++;
        }
        allHuffmanSubTrees.addAll(SubTreesInCurrentLevel);

        HuffmanTree topHuffmanTree = allHuffmanSubTrees.get(allHuffmanSubTrees.size() - 1);
        topHuffmanTree.buildCodesPaths();
        for (HuffmanTree tree : allHuffmanSubTrees) {
            System.out.println(tree.data+" level: "+tree.depth);
        }
        return topHuffmanTree;
    }


    public void drawHuffmanTree(GraphicsContext gc, double x, double y, double sizeNode) {
        gc.setFill(Color.GREEN);
        if (passed == 0) {
            passed++;
            if (leftChild != null) {
                y -= (Math.pow(2, depth) * sizeNode) / 2;
                x -= (Math.pow(2, depth) * sizeNode) / 2;
                leftChild.drawHuffmanTree(gc, x, y, sizeNode);
            } else if (parent != null) {
                double nextY = y + (Math.pow(2, depth + 1) * sizeNode) / 2;
                double nextX;
                if (parent.rightChild == this) nextX = x - (Math.pow(2, depth + 1) * sizeNode) / 2;
                else nextX = x + (Math.pow(2, depth + 1) * sizeNode) / 2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x + sizeNode / 2, y + sizeNode / 2, nextX + sizeNode / 2, nextY + sizeNode / 2);
                gc.fillOval(x, y, sizeNode, sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(code.substring(code.length() - 1), x + sizeNode / 2, y + sizeNode + 15);
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
                y -= (Math.pow(2, depth) * sizeNode) / 2;
                x += (Math.pow(2, depth) * sizeNode) / 2;
                rightChild.drawHuffmanTree(gc, x, y, sizeNode);
            } else {
                double nextY = y + (Math.pow(2, depth + 1) * sizeNode) / 2;
                double nextX;
                if (parent.rightChild == this) nextX = x - (Math.pow(2, depth + 1) * sizeNode) / 2;
                else nextX = x + (Math.pow(2, depth + 1) * sizeNode) / 2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x + sizeNode / 2, y + sizeNode / 2, nextX + sizeNode / 2, nextY + sizeNode / 2);
                gc.fillOval(x, y, sizeNode, sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(code.substring(code.length() - 1), x + sizeNode / 2, y + sizeNode + 15);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(data), x + sizeNode / 2 - 3, y + sizeNode / 2 + 3);
                parent.drawHuffmanTree(gc, nextX, nextY, sizeNode);
            }
        } else if (passed == 2) {
            if (parent != null) {
                double nextY = y + (Math.pow(2, depth + 1) * sizeNode) / 2;
                double nextX;
                if (parent.rightChild == this) nextX = x - (Math.pow(2, depth + 1) * sizeNode) / 2;
                else nextX = x + (Math.pow(2, depth + 1) * sizeNode) / 2;
                gc.setStroke(Color.BROWN);
                gc.setLineWidth(5);
                gc.strokeLine(x + sizeNode / 2, y + sizeNode / 2, nextX + sizeNode / 2, nextY + sizeNode / 2);
                gc.fillOval(x, y, sizeNode, sizeNode);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeText(code.substring(code.length() - 1), x + sizeNode / 2, y + sizeNode + 15);
                gc.setStroke(Color.WHITE);
                gc.strokeText(String.valueOf(data), x + sizeNode / 2 - 3, y + sizeNode / 2 + 3);
                parent.drawHuffmanTree(gc, nextX, nextY, sizeNode);
            } else {
                gc.setStroke(Color.BROWN);
                gc.setLineWidth((2 * sizeNode) / 3);
                gc.strokeLine(x + sizeNode / 2, y + sizeNode / 2, x + sizeNode / 2, y + 100);
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

    private void recursiveSearchCharactersCodes(Map<String, String> charactersCodes) {
        if (passed == 0) {
            passed++;
            if (leftChild != null) {
                leftChild.recursiveSearchCharactersCodes(charactersCodes);
            } else if (parent != null) {
                charactersCodes.put(character, code);
                parent.recursiveSearchCharactersCodes(charactersCodes);
            } else {
                charactersCodes.put(character, code);
                zeroPassedFlags();
                return;
            }
        } else if (passed == 1) {
            passed++;
            if (rightChild != null) {
                rightChild.recursiveSearchCharactersCodes(charactersCodes);
            } else {
                charactersCodes.put(character, code);
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
        return "HuffmanTree{" +
                "data=" + data +
                '}';
    }
}
