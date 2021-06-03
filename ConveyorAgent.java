
/*
The contain of the REQUEST message from the Dummy Agent
should be like this:
{"source":"Conveyor2","dest":"Conveyor12","midPoints":[]}
 */

package agents;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.util.Logger;
import jade.util.*;
import jade.core.behaviours.WakerBehaviour;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.List;


public class ConveyorAgent extends Agent {
    //Declaring variables
    private List<String> nextConveyor = new ArrayList<String>();
    private int solutionCounter;
    private int viaPointCounter;
    private List<String> solutionComp = new ArrayList<String>();
    private List<String> solutionFinal = null;
    private Logger myLogger = Logger.getMyLogger(getClass().getName());

    //adding Behaviour

    private class WaitStartMessage extends CyclicBehaviour {

        public WaitStartMessage(Agent a) {
            super(a);
        }

        public void action() {
            ACLMessage  msg = myAgent.receive();
            if(msg != null){
                ACLMessage reply = msg.createReply();

                if(msg.getPerformative()== ACLMessage.REQUEST){
                    String content = msg.getContent();

                    if ((content != null)) {
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("Solution requested");

                        //execute for only one time
                        myAgent.addBehaviour(new OneShotBehaviour(myAgent) {
                            public void action() {
                                //retrieve data from JSON object
                                JSONParser parser = new JSONParser();
                                try {
                                    JSONObject json = (JSONObject) parser.parse(content);
                                    String source = (String) json.get("source");
                                    String dest = (String) json.get("dest");
                                    JSONArray midPoints=(JSONArray)json.get("midPoints");

                                    //destination reached or not
                                    if(!json.get("dest").equals(getLocalName())) {
                                        JSONArray midPointsJArr=(JSONArray)json.get("midPoints");
                                        boolean cotinue = false; //declaring default value for determining -> should the local agent added to the midPoints Array

                                        if (midPointsJArr.size()==0){
                                            cotinue= true;
                                        }


                                       // Chaking the midPoints repitation
                                        else if (midPointsJArr.size()!=0){
                                            for(int i = 0; i < midPointsJArr.size(); i++){
                                                if (!midPointsJArr.get(i).equals(getLocalName())){
                                                    cotinue= true;
                                                }
                                                else if (midPointsJArr.get(i).equals(getLocalName())){
                                                    cotinue= false;
                                                    // preventing the infinite loop                                                    //
                                                    break;
                                                }
                                            }
                                        }


                                        //adding the midPoints to the message
                                        if (cotinue) {
                                            ((JSONArray) json.get("midPoints")).add(getLocalName());
                                            String JESONmsg = "{\"source\":\"" + source + "\",\"dest\":\"" + dest + "\",\"midPoints\":" + midPoints + "}";
                                            for (int i = 0; i < nextConveyor.size(); i++) {
                                                //Sending request to the next conveyor
                                                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                                                msg.setContent(JESONmsg);
                                                msg.addReceiver(new AID((String) nextConveyor.get(i), AID.ISLOCALNAME));
                                                send(msg);
                                            }
                                        }
                                    }

                                    //Infrom Soution found
                                    else if( json.get("dest").equals(getLocalName())) {
                                        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                                        ((JSONArray) json.get("midPoints")).add(getLocalName());
                                        String JESONmsg = "{\"source\":\"" + source + "\",\"dest\":\"" + dest + "\",\"midPoints\":" + midPoints + "}";
                                        msg.setContent(JESONmsg);
                                        msg.addReceiver(new AID((String) source, AID.ISLOCALNAME));
                                        send(msg);

                                        //Converting JSONArr to list of strings & printing JSONArray
                                        List<String> list = new ArrayList<String>();
                                        JSONArray midPointsJArr=(JSONArray)json.get("midPoints");
                                        for(int i = 0; i < midPointsJArr.size(); i++){
                                            list.add((String) midPointsJArr.get(i));
                                        }
                                        //System.out.println(list);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    else{
                        myLogger.log(Logger.INFO, "Agent "+getLocalName()+" - Unexpected request ["+content+"] received from "+msg.getSender().getLocalName());
                        reply.setPerformative(ACLMessage.REFUSE);
                        reply.setContent("( UnexpectedContent ("+content+"))");
                        send(reply);
                    }




                   // Checking the shortest possible path
                }else if(msg.getPerformative()== ACLMessage.INFORM){
                    String solution = msg.getContent();
                    JSONParser parser = new JSONParser();
                    JSONObject json = null;
                    solutionCounter++;

                    try {
                        json = (JSONObject) parser.parse(solution);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    List<String> list = new ArrayList<String>();
                    JSONArray midPointsJArr = (JSONArray) json.get("midPoints");

                    for (int i = 0; i < midPointsJArr.size(); i++) {
                        list.add((String) midPointsJArr.get(i));
                    }
                    System.out.println("\t\t\t"+getLocalName()+": Solution received;-------size: "+list.size()+" ------Solution path no"+solutionCounter+" is----\n \t\t\t"+list+"\n" );
                    solutionComp=list;
                    if (solutionFinal==null){
                        solutionFinal=solutionComp;
                    }
                    else if (solutionComp.size()<solutionFinal.size()) {
                        solutionFinal = solutionComp;

                    }
                    else if (solutionComp.size()>solutionFinal.size()) {
                        solutionComp=solutionFinal;
                    }
                    System.out.println("\t\t\t"+getLocalName()+": Solution;-------size: "+solutionFinal.size()+" ------New shortest solution path is----\n \t\t\t"+solutionFinal+"\n" );
                }
            }
            else {

            }
        }

    }


    protected void setup() {
        System.out.println("Hello World! I am a conveyor and my name is: "+getLocalName());
        Object[] args = getArguments();      //get the nextcoveyor data fron the LayoutAgent

        //checking the existence of more than one neighbour.
        if (args != null && args.length > 0) {
            for(int i=0;i<args.length;i++) {
                nextConveyor.add((String)args[i]);
                System.out.println(getLocalName() + ": the next conveyor is: " + nextConveyor.get(i));
            }
        }
        WaitStartMessage StartBehaviour = new  WaitStartMessage(this);
        addBehaviour(StartBehaviour);
    }
}