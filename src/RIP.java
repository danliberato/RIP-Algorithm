/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daan
 */
public class RIP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<Node> nodeList = new ArrayList<>();
        Random random = new Random();
        int i = 0;
        //instancia os nós e inicializa as distancias para cada
        Node node0 = new Node("R0");
        Node node1 = new Node("R1");
        Node node2 = new Node("R2");
        Node node3 = new Node("R3");

        System.out.println("Initializing R0 table");
        RoutingTable rt0 = new RoutingTable();
        rt0.addEntry(node1, node0, 1);
        rt0.addEntry(node2, node0, 3);
        rt0.addEntry(node3, node0, 7);
        node0.setRoutingTable(rt0);
        nodeList.add(node0);

        System.out.println("Initializing R1 table");
        RoutingTable rt1 = new RoutingTable();
        rt1.addEntry(node0, node1, 1);
        rt1.addEntry(node2, node1, 1);
        rt1.addEntry(node3, node1, 999);
        node1.setRoutingTable(rt1);
        nodeList.add(node1);

        System.out.println("Initializing R2 table");
        RoutingTable rt2 = new RoutingTable();
        rt2.addEntry(node0, node2, 3);
        rt2.addEntry(node1, node2, 1);
        rt2.addEntry(node3, node2, 2);
        node2.setRoutingTable(rt2);
        nodeList.add(node2);

        System.out.println("Initializing R3 table");
        RoutingTable rt3 = new RoutingTable();
        rt3.addEntry(node0, node3, 7);
        rt3.addEntry(node1, node3, 999);
        rt3.addEntry(node2, node3, 2);
        node3.setRoutingTable(rt3);
        nodeList.add(node3);

        /*for (Node n : nodeList) {
            System.out.println(n.getNodeId() + " -  ROUND ");
            n.sendUpdateTable();
            }
        
        System.out.println("\n ---------------------------------------- "
                + "\n\n\n TABELAS FINAIS");
        node0.showRoutingTable();
        node1.showRoutingTable();
        node2.showRoutingTable();
        node3.showRoutingTable();*/
        
        System.out.println("---------- TABELAS INICIALIZADAS ----------");
        node0.showRoutingTable();
        node1.showRoutingTable();
        node2.showRoutingTable();
        node3.showRoutingTable();
        System.out.println("---------- Inicio da execução ----------");
        
        try {
            node0.start();
            Thread.sleep(random.nextInt(1500));
            node1.start();
            Thread.sleep(random.nextInt(1500));
            node2.start();
            Thread.sleep(random.nextInt(1500));
            node3.start();
        } catch (InterruptedException ex) {
            Logger.getLogger(RIP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
