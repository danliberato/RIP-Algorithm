
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author daan
 */
public class Node {

    private String nodeId;
    private RoutingTable routingTable;
    private int countingNonUpdates;

    public Node(String Id) {
        this.nodeId = Id;
        this.countingNonUpdates = 0;
    }

    /* (rtupdateN)
    The core of RIP
    This method updates the routing table of the router
    Basicly the router receives a RIP advertisement and than updates its table
     */
    public void updateRoutingTable(RoutingTable neighborTable, Node whoSent) {
        int costToWhoSent = 0;
        //compara as entradas
        for (TableEntry neighborEntry : neighborTable.getRoutingTable()) {
            for (TableEntry currentEntry : this.routingTable.getRoutingTable()) {

                Node nDest = neighborEntry.getDestinationRouter();

                //get cost to the router that sent the routing table
                if (currentEntry.getDestinationRouter() == whoSent) {
                    costToWhoSent = currentEntry.getCost();
                }

                //if destination router are both the same...
                if (currentEntry.getDestinationRouter() == nDest) {
                    int cost = (neighborEntry.getCost() + costToWhoSent);
                    //if the cost to the node is unknown
                    if (currentEntry.getCost() == 999 || cost < currentEntry.getCost()) {
                        System.out.println("*-> " + this.getNodeId() + " será atualizado");
                        this.routingTable.updateEntry(nDest, whoSent, cost);
                        this.showRoutingTable();
                    }
                }
            }
        }
    }

    /*
    Announces its routing table to each immediate neighbor
     */
    public void sendUpdateTable() {
        for (Node n : this.getNeighborList()) {
            n.updateRoutingTable(this.routingTable, this);
        }
    }

    /*
    creates a list of all neighbor
     */
    public List<Node> getNeighborList() {
        List<Node> nList = new ArrayList<>();
        for (int i = 0; i < this.routingTable.getRoutingTable().size(); i++) {
            if (this.routingTable.getRoutingTable().get(i).getCost() < 999) {
                nList.add(this.routingTable.getRoutingTable().get(i).getDestinationRouter());
            }
        }
        return nList;
    }

    public void showRoutingTable() {
        List<TableEntry> table = routingTable.getRoutingTable();
        System.out.println("------------ " + this.getNodeId() + " RoutingTable -----------");
        System.out.println("|    Dest    |    Next    |    Cost    |");

        for (TableEntry t : table) {
            System.out.println("----------------------------------------");
            System.out.print("      " + t.getDestinationRouter().getNodeId() + "     ");
            System.out.print("      " + t.getNextRouter().getNodeId() + "     ");
            System.out.print("      " + t.getCost());
            System.out.println("");
        }
        System.out.println("----------------------------------------\n");

    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    public void setRoutingTable(RoutingTable routingTable) {
        this.routingTable = routingTable;
    }

    @Override
    public String toString() {
        return this.getNodeId();
    }
}
