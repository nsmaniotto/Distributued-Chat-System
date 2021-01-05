/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversation;

import project.insa.idchatsystem.Exceptions.NoPortAvailable;

/**
 *
 * @author smani
 */
public class TestMain {
    public static void main(String[] args) throws NoPortAvailable {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                testCase(2);
            } catch (NoPortAvailable noPortAvailable) {
                noPortAvailable.printStackTrace();
            }
        });
    }
    
    private static void testCase(int testNumber) throws NoPortAvailable {
        switch (testNumber) {
            case 1 -> {
                ClientTestNathan clientTestNathan = new ClientTestNathan();
            }
            case 2 -> {
                ClientTestRobin clientTestRobin = new ClientTestRobin();
            }
            default -> {
            }
        }
    }
}
