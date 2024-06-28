package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class IntersectionManagerAgent extends Agent {
    protected void setup() {
        System.out.println("Intersection Manager Agent " + getAID().getName() + " is ready.");

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String content = msg.getContent();
                    System.out.println("Intersection Manager received: " + content);
                    if (content.contains("High traffic")) {
                        adjustTrafficLights("XYZ", "increase");
                    } else if (content.contains("Weather update")) {
                        adjustTrafficLightsForWeather(content);
                    }
                }
                block();
            }
        });
    }

    private void adjustTrafficLights(String intersection, String action) {
        System.out.println("Adjusting traffic lights at " + intersection + " with action: " + action);
    }

    private void adjustTrafficLightsForWeather(String weatherUpdate) {
        System.out.println("Adjusting traffic lights for weather: " + weatherUpdate);
    }
}
