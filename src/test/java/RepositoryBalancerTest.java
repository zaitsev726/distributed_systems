import org.junit.Test;
import ru.nsu.zebra.client.domain.Context;
import ru.nsu.zebra.client.domain.Node;
import ru.nsu.zebra.client.migration.impl.RepositoryBalancerManagerImpl;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class RepositoryBalancerTest {
    private final RepositoryBalancerManagerImpl balancerManager = new RepositoryBalancerManagerImpl();

    @Test
    public void emptyContext_ShouldThrowException() {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                balancerManager.selectNamespace(new Context(10)));

        var message = "Context nodes list is empty";
        assertEquals(exception.getMessage(), message);
    }

    @Test
    public void invalidNodeCreationParameter_ShouldThrowException() {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                Node.createNode("some_namespace", 1, 101, 2, 2));
        var message = "Can't create Node with value 1. It should be in range (10:30)";
        assertEquals(exception.getMessage(), message);
    }

    @Test
    public void invalidRepositoryInsertion_ShouldThrowException() {
        var node = Node.createNode(
                "name_space",
                10,
                500,
                5,
                3
        );
        for (int i = 0; i < 10; i++) {
            node.increaseNumberOfRepositories();
        }
        var exception = assertThrows(IllegalArgumentException.class, node::increaseNumberOfRepositories);
        var message = "Current count 10 is equals to max repository amount.";
        assertEquals(exception.getMessage(), message);
    }

    @Test
    public void getFullNodeScoreTest() {
        var node = Node.createNode(
                "name_space",
                10,
                500,
                5,
                3
        );
        for (int i = 0; i < 10; i++) {
            node.increaseNumberOfRepositories();
        }
        assertEquals(BigDecimal.ONE.negate(), node.getNodeScore());
    }

    @Test
    public void getNodeScoreTest() {
        var node = Node.createNode(
                "name_space",
                20,
                500,
                5,
                3
        );

        assertEquals(node.getNodeScore(), BigDecimal.valueOf(2.18));
    }

    @Test
    public void getNodeTest() {
        var firstNode = Node.createNode("first_node", 20, 500, 5, 3);
        var secondNode = Node.createNode("second_node", 10, 200, 2, 1);

        assertEquals(0, firstNode.getNumberOfRepositories());
        var context = new Context(10);
        context.contextAddNode(firstNode);
        context.contextAddNode(secondNode);

        var resultNode = balancerManager.selectNamespace(context);
        assertEquals(resultNode.namespace, firstNode.namespace);
        assertEquals(1, resultNode.getNumberOfRepositories());
    }
}
