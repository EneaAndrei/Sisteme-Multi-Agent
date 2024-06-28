package agents;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class AnalysisReportingAgent extends Agent {
    protected void setup() {
        System.out.println("Analysis Reporting Agent " + getAID().getName() + " is ready.");

        addBehaviour(new TickerBehaviour(this, 30000) {
            protected void onTick() {
                String analysisReport = "Traffic analysis report generated.";
                System.out.println(analysisReport);
                sendAnalysisReport();
            }
        });
    }

    private void sendAnalysisReport() {
        System.out.println("Sending analysis report to relevant agents.");
    }
}
