package ru.nsu.zebra.client.migration.impl;

import ru.nsu.zebra.client.domain.Context;
import ru.nsu.zebra.client.domain.Node;
import ru.nsu.zebra.client.migration.RepositoryBalancerManager;

import java.util.Comparator;

public class RepositoryBalancerManagerImpl implements RepositoryBalancerManager {

    @Override
    public Node selectNamespace(Context context) {
        if (context.getNodes().isEmpty()) {
            throw new IllegalArgumentException("Context nodes list is empty");
        }

        var node = context.getNodes()
                .stream()
                .max(new NodeComparator())
                .orElseThrow(() -> new IllegalArgumentException("Node isn't present"));
        node.increaseNumberOfRepositories();
        return node;
    }

    static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.getNodeScore().compareTo(o2.getNodeScore());
        }
    }
}
