import java.util.ArrayList;

public class KruskalThread extends  Thread {

    private int name;

    private static boolean[] booleans;
    private static ArrayList<Edge> order;

    public KruskalThread(int name){
        this.name=name;
    }
    public void run(){
        System.out.println("Thread-" + name + " is going to Start!");
        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(Graph.kruskalTree.get(0));
        while (true) {
            ArrayList<Edge> arrayList = Graph.getCheckList(0,edges);
            int length = arrayList.size();
            Edge e = arrayList.get(length - 1);
            arrayList.remove(length - 1);
            System.out.println("当前监测边： "+e+" 已选取边： "+arrayList.size());
            boolean iscircle = isCircle(arrayList, e);
            if (iscircle) {
                ArrayList<Edge> del = new ArrayList<>();
                del.add(e);
                Graph.getCheckList(1,del);
            }
            if (Graph.flag){
                break;
            }
            if (Graph.index>=Graph.kruskalTree.size()-1){
                break;
            }
        }
    }
    public static boolean isCircle(ArrayList<Edge> arrayList,Edge edge){
        boolean result;
        int length = arrayList.size();
        boolean flag_end = false;
        boolean flag_start = false;
        boolean flag = false;
        int end = edge.getEnd();
        int start = edge.getStart();
        for (int j = 0; j < length; j++) {
            if (arrayList.get(j).getEnd()==end||arrayList.get(j).getStart()==end){
                flag_end=true;
            }
            if (arrayList.get(j).getStart()==start||arrayList.get(j).getEnd()==start){
                flag_start=true;
            }
            if (flag_end&&flag_start){
                flag = true;
                break;
            }
        }
        if (!flag){
            result = false;
        } else {
            arrayList.add(edge);
            order = new ArrayList<>();
            order = (ArrayList<Edge>) arrayList.clone();
            arrayList.remove(edge);
            booleans=new boolean[arrayList.size()+1];
            for (int b=0;b<arrayList.size()+1;b++){
                booleans[b]=false;
            }
            result = findCircle(edge,arrayList);
        }
        return result;
    }
    private static boolean findCircle(Edge edge, ArrayList<Edge> arrayList){
        boolean result=false;
        booleans[order.indexOf(edge)]=true;
        int start = edge.getStart();
        int end = edge.getEnd();
        for (int i = 0;i<arrayList.size()- 1;i++){
            Edge cur = arrayList.get(i);
            if (cur.getEnd()==start||cur.getStart()==start||cur.getEnd()==end||cur.getStart()==end){
                if (booleans[order.indexOf(cur)]){
                    result=true;
                    break;
                }else {
                    ArrayList<Edge> temp = new ArrayList<>();
                    temp= (ArrayList<Edge>) arrayList.clone();
                    temp.add(edge);
                    temp.remove(cur);
                    result = findCircle(cur, temp);
                    if(result){break;}
                }
            }
        }
        return result;
    }
    private boolean isTriangle(ArrayList<Edge> arrayList,Edge edge){
        boolean result = true;
        int start = edge.getStart();
        int count_start=1;
        int end = edge.getEnd();
        int count_end=1;
        int length = arrayList.size();
        for (int i=0;i<length;i++){
            if (arrayList.get(i).getStart()==start||arrayList.get(i).getEnd()==start){
                count_start++;
                if (count_start==3){
                    result = true;
                    break;
                }
            }
            if (arrayList.get(i).getStart()==end||arrayList.get(i).getEnd()==end){
                count_end++;
                if (count_end==3){
                    result = true;
                    break;
                }
            }
        }
        if (count_end!=3&&count_start!=3){
            result = false;
        }
        return result;
    }
}
