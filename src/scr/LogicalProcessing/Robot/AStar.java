package scr.LogicalProcessing.Robot;

import java.util.ArrayList;
import java.util.List;


//第一遍看懂了怎么找到最短路径，但是没看出它是怎么避过障碍物的，可是它却实现了避过障碍物


/**
 * 修改版  GitHub: GiyaYon 增加了斜向的代价
 * 1.准备地图 Nodes
 * 2.最短路径 path: F=G+H
 *      G：起点到当前点的距离
 *      H ：当前点到终点的距离
 *      F：等于前面两者之和，用于判定下一节点该往哪里走。
 * 3.先将周围节点添加到周围节点表里，计算里面的每一个G值，再计算H值,最后得出F值
 * 4.选择最小的那个F值，加入列表
 * 4.循环执行输出，得出最短路径列表
 */

public class AStar {
//    public static final int[][] NODES = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0, 0, 0 },
//            { 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
    public static int[][] NODES = new int[800][600];

    public AStar() {
    }
    public AStar(int w,int h) {
        NODES = new int[w][h];
    }
    public static final int STEP = 10;
    public static final int INCLINED_STEP = 14;
    private ArrayList<Node> openList = new ArrayList<Node>();
    private ArrayList<Node> closeList = new ArrayList<Node>();

    public Node findMinFNodeInOpenList() {
        Node tempNode = openList.get(0); // 先以第一个元素的F为最小值，然后遍历openlist的所有值，找出最小值
        for (Node node : openList) {
            if (node.F < tempNode.F) {
                tempNode = node;
            }
        }
        return tempNode;
    }

    // 考虑周围节点的时候，就不把节点值为1的节点考虑在内，所以自然就直接避开了障碍物
    public ArrayList<Node> findNeighborNodes(Node currentNode) {
        ArrayList<Node> arrayList = new ArrayList<Node>();
        int topX = currentNode.x;
        int topY = currentNode.y - 1;
        // canReach方法确保下标没有越界 exists方法确保此相邻节点不存在于closeList中，也就是之前没有遍历过
        if (canReach(topX, topY) && !exists(closeList, topX, topY)) {
            arrayList.add(new Node(topX, topY));
        }
        int bottomX = currentNode.x;
        int bottomY = currentNode.y + 1;
        if (canReach(bottomX, bottomY) && !exists(closeList, bottomX, bottomY)) {
            arrayList.add(new Node(bottomX, bottomY));
        }
        int leftX = currentNode.x - 1;
        int leftY = currentNode.y;
        if (canReach(leftX, leftY) && !exists(closeList, leftX, leftY)) {
            arrayList.add(new Node(leftX, leftY));
        }
        int rightX = currentNode.x + 1;
        int rightY = currentNode.y;
        if (canReach(rightX, rightY) && !exists(closeList, rightX, rightY)) {
            arrayList.add(new Node(rightX, rightY));
        }

        int topLeftX = currentNode.x - 1;
        int topLeftY = currentNode.y - 1;
        if (canReach(topLeftX, topLeftY) && !exists(closeList, topLeftX, topLeftY)) {
            arrayList.add(new Node(topLeftX, topLeftY,INCLINED_STEP));
        }

        int topRightX = currentNode.x + 1;
        int topRightY = currentNode.y - 1;
        if (canReach(topRightX, topRightY) && !exists(closeList, topRightX, topRightY)) {
            arrayList.add(new Node(topRightX, topRightY,INCLINED_STEP));
        }

        int botttomRightX = currentNode.x + 1;
        int botttomRightY = currentNode.y + 1;
        if (canReach(botttomRightX, botttomRightY) && !exists(closeList, botttomRightX, botttomRightY)) {
            arrayList.add(new Node(botttomRightX, botttomRightY,INCLINED_STEP));
        }

        int botttomLeftX = currentNode.x - 1;
        int botttomLeftY = currentNode.y + 1;
        if (canReach(botttomLeftX, botttomLeftY) && !exists(closeList, botttomLeftX, botttomLeftY)) {
            arrayList.add(new Node(botttomLeftX, botttomLeftY,INCLINED_STEP));
        }

        return arrayList;
    }

    public boolean canReach(int x, int y) {
        if (x >= 0 && x < NODES.length && y >= 0 && y < NODES[0].length) {
            return NODES[x][y] == 0; // 原来是在这里避过障碍物的啊。。如果节点值不为0，说明不可到达。
        }
        return false;
    }

    //核心代码，找路径
    public Node findPath(Node startNode, Node endNode) {
        // 把起点加入 open list
        openList.add(startNode);
        while (openList.size() > 0) {
            // 遍历 open list ，查找 F值最小的节点，把它作为当前要处理的节点
            Node currentNode = findMinFNodeInOpenList();

            // F值最小的节点从open list中移除
            openList.remove(currentNode);
            // 把这个节点移到 close list，closelist就是存储路径的链表
            closeList.add(currentNode);

            // 查找不存在于close list当中的周围节点（不考虑斜边的邻居）
            ArrayList<Node> neighborNodes = findNeighborNodes(currentNode);

            // openlist其实就是存储的外围的节点集合
            for (Node node : neighborNodes) {// 总之要把邻居节点添加进openlist当中
                if (exists(openList, node)) { // 如果邻居节点在openlist当中
                    foundPoint(currentNode, node);
                } else {
                    // 如果邻居节点不在openlist中，那就添加进openlist
                    notFoundPoint(currentNode, endNode, node);
                }
            }
            // 如果在openlist中找到了终点，那么就说明已经找到了路径，返回终点
            if (find(openList, endNode) != null) {
                return find(openList, endNode);
            }
        }
        return find(openList, endNode);
    }

    // 此种情况就是发现周围的F值最小的节点是之前已经遍历过了的，所以这个节点的G,H,F值都是已经计算过了的
    // 此时H值肯定不会变，所以要比较G值，如果现在的G值比之前的小，说明现在的路径更优
    // 接着就重置此节点的父指针，G值和F值
    private void foundPoint(Node tempStart, Node node) {
        int G = calcG(tempStart, node);
        if (G < node.G) {
            node.parent = tempStart;
            node.G = G;
            node.calcF();
        }
    }

    // 这种情况是之前没有计算过此节点的值，所以在这里要计算一遍G,H,F值，然后确认父指针指向，然后加入openlist当中
    private void notFoundPoint(Node tempStart, Node end, Node node) {
        node.parent = tempStart;
        node.G = calcG(tempStart, node);
        node.H = calcH(end, node);
        node.calcF();
        openList.add(node);
    }

    private int calcG(Node start, Node node) {
        int G = STEP;
        if(start.stepType==INCLINED_STEP) G = INCLINED_STEP;
        int parentG = node.parent != null ? node.parent.G : 0;
        return G + parentG;
    }

    // 这个是最简单粗暴的计算H值得方法，当然还有其它方法，这里先理解AStar的思想先，以后可以自己改进这个计算H值得方法
    // 来源 ：Manhattan Distance
    private int calcH(Node end, Node node) {
        int step = Math.abs(node.x - end.x) + Math.abs(node.y - end.y);
        return step * STEP;
    }

    public static Node find(List<Node> nodes, Node point) {
        for (Node n : nodes)
            if ((n.x == point.x) && (n.y == point.y)) {
                return n;
            }
        return null;
    }

    public static boolean exists(List<Node> nodes, Node node) {
        for (Node n : nodes) {
            if ((n.x == node.x) && (n.y == node.y)) {
                return true;
            }
        }
        return false;
    }

    public static boolean exists(List<Node> nodes, int x, int y) {
        for (Node n : nodes) {
            if ((n.x == x) && (n.y == y)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Node startNode = new Node(5, 1);
        Node endNode = new Node(5, 5);
        Node parent = new AStar().findPath(startNode, endNode);// 返回的是终点，但是此时父节点已经确立，可以追踪到开始节点

        //打印走之前的地图
        for (int i = 0; i < NODES.length; i++) {
            for (int j = 0; j < NODES[0].length; j++) {
                System.out.print(NODES[i][j] + ", ");
            }
            System.out.println();
        }
        ArrayList<Node> arrayList = new ArrayList<Node>();

        while (parent != null) {// 遍历刚才找到的路径。没问题
            // System.out.println(parent.x + ", " + parent.y);
            arrayList.add(new Node(parent.x, parent.y));
            parent = parent.parent;
        }
        System.out.println("n");

        //打印之后的地图
        for (int i = 0; i < NODES.length; i++) {
            for (int j = 0; j < NODES[0].length; j++) {
                if (exists(arrayList, i, j)) {// 把路径经过的点用@表示
                    System.out.print("@, ");
                } else {
                    System.out.print(NODES[i][j] + ", ");
                }

            }
            System.out.println();
        }
    }


}