/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author daan
 */
public class RoutingTable {

    private ArrayList<TableEntry> routingTable;

    public RoutingTable() {
        this.routingTable = new ArrayList<>();
    }

    public RoutingTable(ArrayList<TableEntry> rt) {
        this.routingTable = rt;
    }

    /*
    This method adds a new entry in the routing table of the router
     */
    public void addEntry(Node destionationRouter, Node nextRouter, Integer numberHops) {
        TableEntry newEntry = new TableEntry(destionationRouter, nextRouter, numberHops);
        this.routingTable.add(newEntry);
    }

    /*
    call from the current node to update its table
    runs over all the table entry searcing for a entry to update
     */
    public void updateEntry(Node destinationRouter, Node nextRouter, Integer totalCost) {

        for (TableEntry entry : this.routingTable) {

            if (entry.getDestinationRouter().equals(destinationRouter)) {

                entry.setCost(totalCost);
                entry.setNextRouter(nextRouter);

            }
        }
    }

    public TableEntry getEntry(Node nDest) {
        for (TableEntry entry : this.routingTable) {

            if (entry.getDestinationRouter().equals(nDest)) {

                return entry;

            }
        }
        return null;
    }

    public ArrayList<TableEntry> getRoutingTable() {
        return routingTable;
    }

    public void setRoutingTable(ArrayList<TableEntry> routingTable) {
        this.routingTable = routingTable;
    }

    @Override
    public String toString() {
        String r = "";
        for (TableEntry e : this.routingTable) {
            r += e.toString() + "\n";
        }
        return r;
    }
}
