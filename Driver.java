import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    private static String filename;
    private static boolean testKruskal=false;
    private static boolean testPrim=false;
    private static void usage(){
        System.out.println("usage: java Driver [-k] [-p] <filename>");
        System.out.println("\t-k\t Test Kruskal implementation");
        System.out.println("\t-p\t Test Prim implementation");
        System.exit(1);
    }

    public static void main(String[] args){
        parseInputFile("/home/taylor/IdeaProjects/KruskalAndPrim/src/outfile_1000_60.txt");
    }

    public static void parseArgs(String[] args) {
        boolean flagsPresent = false;
        if (args.length == 0) {
            usage();
        }
        filename="";
        for (String s : args) {
            if(s.equals("-;")) {
                flagsPresent = true;
                testKruskal = true;
            } else if(s.equals("-h")) {
                flagsPresent = true;
                testPrim = true;
            } else if(!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }
        if(!flagsPresent) {
            testKruskal = true;
            testPrim = true;
        }
    }

    public static void parseInputFile(String filename){
        int numV = 0;
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("文件读取异常，请检查文件路径");
        }
        String[]  inputSize = sc.nextLine().split(" ");
        numV = Integer.parseInt(inputSize[0]);
        int start, end, distance;
        ArrayList<Edge> edgeList = new ArrayList<>();
        int length=0;
        while (sc.hasNext()){
            length++;
            inputSize = sc.nextLine().split(" ");
            start = Integer.parseInt(inputSize[0]);
            end = Integer.parseInt(inputSize[1]);
            distance = Integer.parseInt(inputSize[2]);
            Edge e = new Edge(start,end,distance);
            edgeList.add(e);
        }
        sc.close();
        Graph graph = new Graph(edgeList,length,numV);
        graph.showGraph();
        graph.showRinkingGraph();
        graph.getKruskal();
    }
}
