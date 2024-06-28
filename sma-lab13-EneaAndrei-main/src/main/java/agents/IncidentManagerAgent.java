package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class IncidentManagerAgent extends Agent {
    protected void setup() {
        System.out.println("Incident Manager Agent " + getAID().getName() + " is ready.");

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String content = msg.getContent();
                    System.out.println("Incident Manager received: " + content);
                    handleIncident(content);
                }
                block();
            }
        });
    }

    private void handleIncident(String incidentData) {
        System.out.println("Handling incident: " + incidentData);
    }
}
