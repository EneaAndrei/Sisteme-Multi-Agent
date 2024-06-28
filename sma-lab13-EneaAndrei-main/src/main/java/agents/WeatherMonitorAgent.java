package agents;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class WeatherMonitorAgent extends Agent {
    protected void setup() {
        System.out.println("Weather Monitor Agent " + getAID().getName() + " is ready.");

        addBehaviour(new TickerBehaviour(this, 60000) {
            protected void onTick() {
                String weatherData = "Weather update: Heavy rain expected.";
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new jade.core.AID("IntersectionManager", jade.core.AID.ISLOCALNAME));
                msg.setContent(weatherData);
                send(msg);
                System.out.println("Weather Monitor sent: " + weatherData);
            }
        });
    }
}
