public class Edge {
    private int start;
    private int end;
    private int distance;
    public Edge(int start,int end,int distance){
        this.start=start;
        this.end=end;
        this.distance=distance;
    }

    public int getDistance() {
        return distance;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(start);
        stringBuilder.append("--(");
        stringBuilder.append(distance);
        stringBuilder.append(")-->");
        stringBuilder.append(end);
        String result = stringBuilder.toString();
        return result;
    }
}
