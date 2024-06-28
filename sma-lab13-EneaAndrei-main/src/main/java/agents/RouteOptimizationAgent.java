package agents;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class RouteOptimizationAgent extends Agent {
    protected void setup() {
        System.out.println("Route Optimization Agent " + getAID().getName() + " is ready.");

        addBehaviour(new TickerBehaviour(this, 10000) {
            protected void onTick() {
                String routeSuggestion = "Use route GHI to avoid congestion at intersection XYZ";
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new jade.core.AID("DriverInfoAgent", jade.core.AID.ISLOCALNAME));
                msg.setContent(routeSuggestion);
                send(msg);
                System.out.println("Route Optimization sent: " + routeSuggestion);
            }
        });
    }
}
