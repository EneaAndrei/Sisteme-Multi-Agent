package agents;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jade.wrapper.AgentContainer;

public class TrafficManagementSystem {
    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
        Profile pMain = new ProfileImpl(null, 8888, null);
        AgentContainer mainContainer = rt.createMainContainer(pMain);

        try {
            // Start the RMA agent to open the JADE GUI
            AgentController rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
            rma.start();

            // Create and start other agents
            AgentController intersectionManager = mainContainer.createNewAgent("IntersectionManager", "agents.IntersectionManagerAgent", null);
            AgentController trafficMonitor = mainContainer.createNewAgent("TrafficMonitor", "agents.TrafficMonitorAgent", null);
            AgentController trafficLightManager = mainContainer.createNewAgent("TrafficLightManager", "agents.TrafficLightManagerAgent", null);
            AgentController incidentManager = mainContainer.createNewAgent("IncidentManager", "agents.IncidentManagerAgent", null);
            AgentController driverInfo = mainContainer.createNewAgent("DriverInfoAgent", "agents.DriverInfoAgent", null);
            AgentController routeOptimization = mainContainer.createNewAgent("RouteOptimizationAgent", "agents.RouteOptimizationAgent", null);
            AgentController weatherMonitor = mainContainer.createNewAgent("WeatherMonitor", "agents.WeatherMonitorAgent", null);
            AgentController emergencyResourceManager = mainContainer.createNewAgent("EmergencyResourceManager", "agents.EmergencyResourceManagerAgent", null);
            AgentController publicTransportManager = mainContainer.createNewAgent("PublicTransportManager", "agents.PublicTransportManagerAgent", null);
            AgentController analysisReporting = mainContainer.createNewAgent("AnalysisReportingAgent", "agents.AnalysisReportingAgent", null);

            intersectionManager.start();
            trafficMonitor.start();
            trafficLightManager.start();
            incidentManager.start();
            driverInfo.start();
            routeOptimization.start();
            weatherMonitor.start();
            emergencyResourceManager.start();
            publicTransportManager.start();
            analysisReporting.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
