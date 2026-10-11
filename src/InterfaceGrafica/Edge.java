public class Edge {
    private Node from, to;
    private int weight;

    public Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Node getFrom() { return from; }
    public Node getTo() { return to; }
    public int getWeight() { return weight; }
    public Node getOtherNode(Node n) { return n.equals(from) ? to : from; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return (from.equals(edge.from) && to.equals(edge.to)) ||
                (from.equals(edge.to) && to.equals(edge.from));
    }

    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode();
    }
}