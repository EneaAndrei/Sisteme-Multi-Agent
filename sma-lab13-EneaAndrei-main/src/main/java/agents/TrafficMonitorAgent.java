package agents;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class TrafficMonitorAgent extends Agent {
    protected void setup() {
        System.out.println("Traffic Monitor Agent " + getAID().getName() + " is ready.");

        addBehaviour(new TickerBehaviour(this, 3000) {
            protected void onTick() {
                String trafficData = "High traffic at intersection XYZ";
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new jade.core.AID("IntersectionManager", jade.core.AID.ISLOCALNAME));
                msg.addReceiver(new jade.core.AID("TrafficLightManager", jade.core.AID.ISLOCALNAME));
                msg.setContent(trafficData);
                send(msg);
                System.out.println("Traffic Monitor sent: " + trafficData);
            }
        });
    }
}
