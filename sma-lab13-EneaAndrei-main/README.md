# Sisteme Multi-agent

## Laborator 13

### Obiectivele laboratorului
-	Mobilitatea agenților
-	Mutarea și clonarea
-	Interacțiunea agenților cu AMS



### Mobilitatea agenților
Conform definițiilor standard, agenții mobili sunt tot ceea ce sunt și agenții non-mobili (adică autonomi, reactivi, proactivi și sociali), dar în plus au și proprietatea de mobilitate; pot migra între platforme pentru a îndeplini sarcinile atribuite.
Din punct de vedere al sistemelor distribuite, un agent mobil este un program cu o identitate unică care își poate muta codul, datele și starea între dispozitivele aflate în rețea. Pentru a realiza acest lucru, agenții mobili pot să-și suspende execuția în orice moment și să continue odată ce ajung într-o altă locație.
Mobilitatea unui agent se referă la faptul că acesta se poate muta, sau poate fi clonat, din containerul în care se află într-un alt container din interiorul platformei curente. Această mobilitate se obține prin apelarea metodei *doMove()* moștenită de la clasa Agent, care are un parametru care indică destinația-containerul:

```java
public class MobileAgent extends Agent
{ 
    @Override
    public void setup()
    {
        System.out.println("Agent " + getAID().getName() + "started...");
        doMove(new ContainerID("container1", null));
    }
    @Override
    public void beforeMove()
    { 
        System.out.println("Agent " + getAID().getName() + " began moving...");
    }
    @Override
    public void afterMove()
    { 
        System.out.println("Agent " + getAID().getName() + " finished moving..."); 
    } 
}
```

În exemplul de mai sus, agentul, după ce a fost pornit, se va muta automat în *container1*. JADE oferă, de asemenea, două metode care pot fi suprascrise pentru a implementa funcționalitățile necesare înainte și după mutarea agentului: *beforeMove()* și *afterMove()*. Aceste două metode sunt apelate automat de JADE înainte și după ce agentul trece din starea ACTIVE în starea TRANSIT, specifică ciclului de viață al agentului mobil.
Pe lângă mutarea unui agent, JADE pune la dispoziție și metoda *doClone()*, care copiază agentul într-un alt container (sau în același container, dacă este necesar). Prin urmare, se obțin două instanțe ale agentului. De asemenea, similar procesului de migrare a agentului, în JADE există metodele *beforeClone()* și *afterClone()* pentru executarea codului înainte și după clonarea agentului.

```java
public class CloningAgent extends Agent
{
    @Override
    public void setup()
    {
        System.out.println("Agent " + getAID().getName() + "started...");
        doWait(2000);
        doClone(new ContainerID("container1", null), getAID().getName() + "_Clone");
    }
    @Override
    public void beforeClone()
    {
        System.out.println("Cloning agent " + getAID().getName() + " ...");
    }
    @Override
    public void afterClone()
    {
        System.out.println("Clone " + getAID().getName() + " ready...");
    }
}

```

### Mobilitatea indirectă 
Mutarea unui agent poate avea loc din afara clasei sale, folosind AMS (Agent Management Service), implementată prin clasa AMS Service. AMS poate primi solicitări de mutare a agenților în alte containere folosind un limbaj standardizat (FIPA-SL).

Această procedură poate fi simplificată prin utilizarea acțiunii de mutare a agentului (prin clasa **MoveAction**), în care se specifică numele agentului care urmează să fie mutat, precum și destinația (*ContainerID*). În JADE există mai multe clase de tip acțiune (**Action**) care comunică cu serviciul AMS pentru diferite scopuri. Următoarea metodă poate fi utilizată pentru a stabili ontologia și tipul de comunicare cu AMS, indiferent de acțiunea care urmează să fie executată:

```java
public class Common
{
    // sends a request to the AMS
    public static void sendRequest(Agent caller, Action action)
    {
        // registers the ontology and the standard language for communicating with the AMS
        caller.getContentManager().registerLanguage(new SLCodec());    
        caller.getContentManager().registerOntology(MobilityOntology.getInstance());
        // Send the request to the AMS
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setLanguage(new SLCodec().getName());	              
        request.setOntology(MobilityOntology.getInstance().getName());
        try
        {		
            caller.getContentManager().fillContent(request, action);
            request.addReceiver(action.getActor());
            caller.send(request);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    } 
}
```

În continuare, metoda *sendRequest()* este utilizată pentru a trimite cererea de mutare a unui agent. Pentru a simplifica acest lucru, este implementat un agent *MobilityServiceAgent* personalizat, cu un unic scop, de a muta un alt agent:

```java
public class MobilityServiceAgent extends Agent
{
    private AID agentToMove;
    private ContainerID newContainer;
    ...
     
    //agentToMove = arguments[0];
    //newContainer = arguments[1];
    
    @Override
    public void setup()
    {
        addBehaviour(newRequestToMoveAgentBehaviour(agentToMove, newContainer));
    }

    class RequestToMoveAgentBehaviour extends OneShotBehaviour 
    {
        private AID agentToMove;
        private ContainerID newContainer;
        public RequestToMoveAgentBehaviour(AID AgentToMove, ContainerID NewContainer) {
            agentToMove = AgentToMove;
            newContainer = NewContainer;
        }
        @Override
        public void action()
        {
            MoveAction moveAction = new MoveAction();
            MobileAgentDescription describeMovement = new MobileAgentDescription(); 
            describeMovement.setName(agentToMove); 
            describeMovement.setDestination(newContainer);
            moveAction.setMobileAgentDescription(describeMovement);
            Common.sendRequest(myAgent, new Action(myAgent.getAMS(), moveAction));
        } 
    }
}
```

## Exerciții de laborator

**E1.** Creați o aplicație cu două containere și două tipuri de agenți: 
- *AgentDeMonitorizare*, care va afișa acțiunile agenților din cele două containere. Așteaptă mesaje de la ceilalți agenți cu privire la locația lor actuală, unde doresc să se mute, etc.
```
    Ion si-a inceput executia
    Ana si-a inceput executia
    Ana se va muta din Container1
    Jane s-a mutat in Container2
    Ion se va muta din Container2
    …
```
- *AgentMobil*, care reprezintă agenții capabili să se deplaseze între containere și care vor trimite către agentul de monitorizare actualizări regulate cu privire la starea, acțiunile și locația lor.

**E2.** Dezvoltați o aplicație cu mai multe containere, fiecare dintre acestea incluzând inițial un număr inegal de agenți. Creați un nou tip de agent, *AgentDistribuitor*, care ar trebui să redistribuie agenții între containere, astfel încât agenții să fie distribuiți uniform în sistem. Pe măsură ce agenții obișnuiți, *AgentMobil*, sunt creați, aceștia vor trimite mesaje cu locația lor către agentul distribuitor. Agentul distribuitor va ține o evidență a agenților din sistem, și în ce containere au fost creați, și le va trimite mesaje acestor agenți solicitându-le să se deplaseze printre containere. Scopul este de a ajunge la containere echilibrate, care să conțină un număr (aproape) egal de agenți.