import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Graph {
    private ArrayList<Edge> graph;
    private int length;
    private int numV;

    public static int index = 0;
    public static int check = 0;
    private ArrayList<Edge> rankingList;
    public static ArrayList<Edge> kruskalTree = new ArrayList<>();
    public static ArrayList<Edge> primTree = new ArrayList<>();
    public static boolean flag;
    public Graph(ArrayList<Edge> graph,int length,int numV){
        this.graph = graph;
        this.length = length;
        this.numV = numV;
        setRankingList();
        kruskalTree.addAll(rankingList);
        primTree.addAll(rankingList);
    }

    private void setRankingList(){
        rankingList = new ArrayList<>();
        HashMap<Integer,ArrayList<Edge>> hashMap = new HashMap<>();
        int maxKey = Integer.MIN_VALUE;
        for (int i = 0; i < length ; i++) {
            Edge edge = graph.get(i);
            int dis = edge.getDistance();
            if (maxKey<dis){
                maxKey=dis;
            }
            if (hashMap.get(dis)==null){
                ArrayList<Edge> arrayList = new ArrayList<>();
                arrayList.add(edge);
                hashMap.put(dis,arrayList);
            }else {
                hashMap.get(dis).add(edge);
            }
        }
        for(int j=0;j<maxKey+1;j++){
            ArrayList<Edge> alistEdge = hashMap.get(j);
            if (alistEdge==null){continue;}
            for (int k=0;k<alistEdge.size();k++){
                rankingList.add(alistEdge.get(k));
            }
        }
    }

    public void showGraph(){
        System.out.println("This is the original graph!");
        for (int i = 0; i < length; i++) {
            Edge edge = graph.get(i);
            System.out.println(edge.toString());
        }
    }

    public void showRinkingGraph(){
        System.out.println("This is the Graph which have ranked!");
        for (int i = 0; i < length; i++) {
            Edge edge = rankingList.get(i);
            System.out.println(edge.toString());
        }
    }

    public ArrayList<Edge> getRankingList() {
        return rankingList;
    }

    public ArrayList<Edge> getKruskal(){
        index = 0;
        check = 1;
        Scanner sc = new Scanner(System.in);
        int thread = sc.nextInt();

        KruskalThread[] kruskalThreads = new KruskalThread[thread];
        for (int t =0 ;t<thread;t++){
            KruskalThread kruskalThread = new KruskalThread(t);
            kruskalThread.start();
            kruskalThreads[t] = kruskalThread;
        }
        for (KruskalThread k:kruskalThreads) {
            try {
                k.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(kruskalTree.size());
        for (int k = 0;k<kruskalTree.size();k++){
            System.out.println(kruskalTree.get(k));
        }
        return kruskalTree;
    }

    public synchronized ArrayList<Edge> getPrimTree(){
       return null;
    }

    synchronized public static ArrayList<Edge> getCheckList(int state,ArrayList<Edge> edges){
        if (state == 0) {
            if (check>kruskalTree.size()-1){
                index++;
                check = index+1;
            }
            if (index>=kruskalTree.size()-1){
                flag=true;
            }else {
                for (int i = edges.size(); i < index + 1; i++) {
                    edges.add(kruskalTree.get(i));
                }
                edges.add(kruskalTree.get(check));
                check++;
            }
        }
        if (state==1){
            kruskalTree.remove(edges.get(0));
        }
        return edges;
    }
}
