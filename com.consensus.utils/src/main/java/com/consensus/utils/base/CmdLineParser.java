package com.consensus.utils.base;

import java.util.Set;


public class CmdLineParser{

    public CmdLineParser(String[] args){

        parseCmdLine(args);

    }
    public Set<String> producerTopics;

    public Set<String> consumerTopics;

    // = "0.0.0.0:9092"
    public String bootServer;
    //= "singleProducer"
    public String groupId ;

    public String message;

    public void parseCmdLine(String[] args){
        for(String arg: args){
            if (arg.contains("-Ptopics")){
//replace(" ","")
                producerTopics = Set.of(arg.split("=")[1].split(","));
            }
            if (arg.contains("-Ctopics")){
                consumerTopics = Set.of(arg.split("=")[1].split(","));
            }
            if (arg.contains("-BootServer")){
                bootServer = arg.split("=")[1];

            }
            if (arg.contains("-GroupId")){
                groupId = arg.split("=")[1];
            }
            if (arg.contains("-Message")){
                message = arg.split("=")[1];
            }

        }
    }


}