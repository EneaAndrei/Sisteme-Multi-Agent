package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class DriverInfoAgent extends Agent {
    protected void setup() {
        System.out.println("Driver Info Agent " + getAID().getName() + " is ready.");

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String content = msg.getContent();
                    System.out.println("Driver Info Agent received: " + content);
                    sendRealTimeNotification(content);
                }
                block();
            }
        });
    }

    private void sendRealTimeNotification(String notification) {
        System.out.println("Sending real-time notification to drivers: " + notification);
    }
}
