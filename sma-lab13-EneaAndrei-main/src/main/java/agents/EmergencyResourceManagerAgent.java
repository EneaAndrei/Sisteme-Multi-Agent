package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class EmergencyResourceManagerAgent extends Agent {
    protected void setup() {
        System.out.println("Emergency Resource Manager Agent " + getAID().getName() + " is ready.");

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String content = msg.getContent();
                    System.out.println("Emergency Resource Manager received: " + content);
                    manageEmergencyResources(content);
                }
                block();
            }
        });
    }

    private void manageEmergencyResources(String incidentData) {
        System.out.println("Managing emergency resources for incident: " + incidentData);
    }
}
