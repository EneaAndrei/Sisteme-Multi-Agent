package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class TrafficLightManagerAgent extends Agent {
    protected void setup() {
        System.out.println("Traffic Light Manager Agent " + getAID().getName() + " is ready.");

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String content = msg.getContent();
                    System.out.println("Traffic Light Manager received: " + content);
                    controlTrafficLights(content);
                }
                block();
            }
        });
    }

    private void controlTrafficLights(String trafficData) {
        System.out.println("Controlling traffic lights based on: " + trafficData);
    }
}
