package P3;

import java.util.*;

public class FriendshipGraph {
    public static void main(String[] args) throws Exception {
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
        System.out.println(graph.getDistance(rachel, ross));  //should print 1
        System.out.println(graph.getDistance(ross, rachel));  //should print -1
        System.out.println(graph.getDistance(rachel, ben));  //should print 2
        System.out.println(graph.getDistance(rachel, rachel));  //should print 0
        System.out.println(graph.getDistance(rachel, kramer)); //should print -1

    }
    public Map<Person, List<Person>> map = new HashMap<Person, List<Person>>();

    public void addVertex(Person a)throws Exception {
        if (this.map.containsKey(a)) {//避免重复添加
            throw new Exception("Wrong!Each person has a unique name");
        } else {
            List<Person> friend = new ArrayList<Person>();//为他创建一个List存朋友们
            map.put(a, friend);
        }

    }
    public void addEdge(Person a,Person b){
        int size = map.get(a).size();
        int i = 0;
        boolean add = true;
        while(i<size){
            if(map.get(a).get(i) == b) {
                add = false;
                break;  //减少无用循环次数
            }
            i++;
        }
        if(add)
            map.get(a).add(b);
    }

    public int getDistance(Person a, Person b) {
        Person now = a;
        Person f = b;
        int i = 0;
        int lev = 0;
        Queue<Person> queue = new LinkedList<Person>();
        List<Person> crowed = new ArrayList<Person>();
        if (b == a) {//同一个人距离设为0
            return lev;
        }
        queue.offer(now);
        crowed.add(now);
        while (!queue.isEmpty()) {//深度优先遍历，寻找m2在m1的第几层关系
            now = queue.poll();
            lev++;
            int n = map.get(now).size();
            while (i < n) {
                f = map.get(now).get(i);
                if (f == b)
                    return lev;//找到则返回当前层数，不必继续寻找
                if (!crowed.contains(f)) {
                    queue.offer(f);
                    crowed.add(f);
                }
                i++;
            }
            i = 0;
        }
        return -1;//找不到说明m1m2没关系，返回-1
    }

}
