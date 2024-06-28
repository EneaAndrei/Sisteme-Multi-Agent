package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class PublicTransportManagerAgent extends Agent {
    protected void setup() {
        System.out.println("Public Transport Manager Agent " + getAID().getName() + " is ready.");

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String content = msg.getContent();
                    System.out.println("Public Transport Manager received: " + content);
                    managePublicTransport(content);
                }
                block();
            }
        });
    }

    private void managePublicTransport(String transportData) {
        System.out.println("Managing public transport based on: " + transportData);
    }
}
