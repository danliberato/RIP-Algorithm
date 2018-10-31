
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author daan
 */
public class Node extends Thread {

    private String nodeId;
    private RoutingTable routingTable;
    private int countNonUpd;

    public Node(String Id) {
        this.nodeId = Id;
        this.countNonUpd = 0;
    }

    /* (rtupdateN)
    The core of RIP
    This method updates the routing table of the current router
    Basicly the router receives a RIP advertisement and than updates its table
     */
    public synchronized void updateRoutingTable(RoutingTable neighborTable, Node whoSent) {
        int costToWhoSent = 0;
        boolean wasUpd = false;
        //compara as entradas
        for (TableEntry neighborEntry : neighborTable.getRoutingTable()) {
            for (TableEntry currentEntry : this.routingTable.getRoutingTable()) {

                Node nDest = neighborEntry.getDestinationRouter();

                //get cost to the router that sent the routing table
                    costToWhoSent = this.getRoutingTable().getEntry(whoSent).getCost();

                //if destination router are both the same...
                if (currentEntry.getDestinationRouter() == nDest) {
                    //int cost = (neighborEntry.getCost() + costToWhoSent);
                    int totalCost = (neighborEntry.getCost() + costToWhoSent);
                    //if the cost to the node is unknown
                    //OR
                    //if total cost (including the path to who sent) is less than actual cost to the same destin
                    if (currentEntry.getCost() == 999 || totalCost < currentEntry.getCost()) {
                        System.out.println("*-> " + this.getNodeId() + " será atualizado (Anuncio vindo de "+whoSent+")");
                        System.out.println("Entrada "+ currentEntry);
                        System.out.println("Agora é "+ nDest + " | "+whoSent + " | "+totalCost);
                        currentEntry.setDestinationRouter(nDest);
                        currentEntry.setNextRouter(whoSent);
                        currentEntry.setCost(totalCost);
                        this.sendUpdateTable();
                        this.showRoutingTable();
                    }
                }
            }
        }
        if (wasUpd) {
            System.out.println("*-> " + this.getNodeId() + " será atualizado");
            this.showRoutingTable();
        } else {
            //System.out.println("*-> " + this.getNodeId() + " NÃO foi atualizado");
            countNonUpd++;
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

    public synchronized void showRoutingTable() {
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

    @Override
    public void run() {

        //while (countNonUpd < 2) {
            try {
                sleep(2000);
                this.sendUpdateTable();
            } catch (InterruptedException ex) {
                Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
            }

        //}
        System.out.println(" -------------------------\n"
                + "TABELA FINAL -> " + this);
        this.showRoutingTable();

    }
}
