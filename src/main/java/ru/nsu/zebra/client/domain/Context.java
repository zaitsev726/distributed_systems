package ru.nsu.zebra.client.domain;

import java.util.ArrayList;
import java.util.List;

public class Context {
    /* максимальное кол-во узлов */
    public final int maxNodeCount;

    /* массив узлов */
    private ArrayList<Node> nodes;

    public Context(int maxNodeCount) {
        this.maxNodeCount = maxNodeCount;
        this.nodes = new ArrayList<>();
    }

    /* добавление нового узла в рабочее окружение */
    public List<Node> contextAddNode(Node node) {
        if (nodes.size() >= maxNodeCount) {
            throw new IllegalArgumentException("Nodes size is more than max node count: " + maxNodeCount);
        }
        nodes.add(node);
        return nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    /* полное удаление рабочего окружения */
    public void destroy() {
        this.nodes = new ArrayList<>();
    }
}
