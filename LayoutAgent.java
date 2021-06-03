/**
 * *********************
 * JADE - Java Agent DEvelopment Framework is a framework to develop
 * multi-agent systems in compliance with the FIPA specifications.
 * Copyright (C) 2000 CSELT S.p.A.
 *
 * GNU Lesser General Public License
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation,
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 * **********************
 */
package agents;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.util.Logger;
import jade.core.behaviours.WakerBehaviour;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.*;

public class LayoutAgent extends Agent {

    private AgentController t1 = null;
    private AgentController t2 = null;
    private AgentController t3 = null;
    private AgentController t4 = null;
    private AgentController t5 = null;
    private AgentController t6 = null;
    private AgentController t7 = null;
    private AgentController t8 = null;
    private AgentController t9 = null;
    private AgentController t10 = null;
    private AgentController t11 = null;
    private AgentController t12 = null;
    private AgentController t13 = null;
    private AgentController t14 = null;



    protected void setup() {
        System.out.println("Hello World! I am a LayoutManager and my name is: "+getLocalName());
        String t1AgentName = "Conveyor1";
        String t2AgentName = "Conveyor2";
        String t3AgentName = "Conveyor3";
        String t4AgentName = "Conveyor4";
        String t5AgentName = "Conveyor5";
        String t6AgentName = "Conveyor6";
        String t7AgentName = "Conveyor7";
        String t8AgentName = "Conveyor8";
        String t9AgentName = "Conveyor9";
        String t10AgentName = "Conveyor10";
        String t11AgentName = "Conveyor11";
        String t12AgentName = "Conveyor12";
        String t13AgentName = "Conveyor13";
        String t14AgentName = "Conveyor14";

        try {
            // create agent t1 on the same container of the creator agent
            AgentContainer container = (AgentContainer)getContainerController(); // get a container controller for creating new agents
            t1 = container.createNewAgent(t1AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor2"});
            t1.start();

            t2 = container.createNewAgent(t2AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor3"});
            t2.start();

            t3 = container.createNewAgent(t3AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor4","Conveyor13"});
            t3.start();

            t4 = container.createNewAgent(t4AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor5"});
            t4.start();

            t5 = container.createNewAgent(t5AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor6"});
            t5.start();

            t6 = container.createNewAgent(t6AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor7"});
            t6.start();

            t7 = container.createNewAgent(t7AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor8"});
            t7.start();

            t8 = container.createNewAgent(t8AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor9","Conveyor14"});
            t8.start();

            t9 = container.createNewAgent(t9AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor10"});
            t9.start();

            t10 = container.createNewAgent(t10AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor11"});
            t10.start();

            t11 = container.createNewAgent(t11AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor12"});
            t11.start();

            t12 = container.createNewAgent(t12AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor1"});
            t12.start();

            t13 = container.createNewAgent(t13AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor9","Conveyor14"});
            t13.start();

            t14 = container.createNewAgent(t14AgentName, "agents.ConveyorAgent", new Object[] {"Conveyor12"});
            t14.start();

            System.out.println(getLocalName()+" CREATED AND STARTED NEW ConveyorAgents ON CONTAINER "+container.getContainerName());
        } catch (Exception any) {
            any.printStackTrace();
        }
    }
}

