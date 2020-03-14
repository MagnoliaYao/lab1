package P3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FriendshipGraphTest {

    /**
     * Tests when adding the same vertex.
     */
    @Test(expected = Exception.class)   //对异常的测试
    public void addVertexTest() throws Exception {
        FriendshipGraph graph = new FriendshipGraph();
        Person ross1 = new Person("Ross");
        graph.addVertex(ross1);
        graph.addVertex(ross1);
    }

    /**
     * Tests easy graph.
     */
    @Test
    public void getEasyDistanceTest() throws Exception {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
//        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);

        assertEquals(1, graph.getDistance(rachel, ross));
        assertEquals(-1, graph.getDistance(ross,rachel));
        assertEquals(2, graph.getDistance(rachel, ben));
        assertEquals(0, graph.getDistance(rachel, rachel));
        assertEquals(-1, graph.getDistance(rachel, kramer));

    }

    /**
     * Tests difficult graph.
     */
    @Test
    public void getDifficultDistanceTest() throws Exception {
        FriendshipGraph graph = new FriendshipGraph();
        Person a = new Person("A");
        Person b = new Person("B");
        Person c = new Person("C");
        Person d = new Person("D");
        Person e = new Person("E");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);

        graph.addEdge(a,b);
        graph.addEdge(b,a);
        graph.addEdge(b,d);
        graph.addEdge(b,c);
        graph.addEdge(d,b);
        graph.addEdge(a,d);
        graph.addEdge(d,a);
        graph.addEdge(a,c);
        graph.addEdge(c,a);
        graph.addEdge(a,e);
        graph.addEdge(e,a);

        assertEquals(1, graph.getDistance(a,b));
        assertEquals(1, graph.getDistance(a,c));
        assertEquals(1, graph.getDistance(a,d));
        assertEquals(1, graph.getDistance(a,e));
        assertEquals(1, graph.getDistance(b,c));
        assertEquals(3, graph.getDistance(d,e));
        assertEquals(2, graph.getDistance(d,c));
    }


}
