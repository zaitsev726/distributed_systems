package ru.nsu.zebra.client.migration;

import ru.nsu.zebra.client.domain.Context;
import ru.nsu.zebra.client.domain.Node;

public interface RepositoryBalancerManager {
    Node selectNamespace(Context context);
}
