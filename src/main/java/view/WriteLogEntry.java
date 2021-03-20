package view;

//import model.Phase;

import model.LogEntryBuffer;
import model.Observable;
import model.Observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteLogEntry implements Observer {
    String d_fileName="log.txt";
    static String d_store;
    //static StringBuffer msgs;
    FileWriter d_WriteFile;
    BufferedWriter bwr ;
    //StringBuffer d_Phase;
    //StringBuffer d_Action;
    //StringBuffer d_Message;

    public WriteLogEntry() {
        //msgs =new StringBuffer("");

        try{
            d_WriteFile = new FileWriter("log.txt");
            bwr = new BufferedWriter(d_WriteFile);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Observable o) {

        LogEntryBuffer LogBuff = (LogEntryBuffer)o;
        if(LogBuff.getGamePhaseSet()){

            d_store=LogBuff.getPhaseValue();
            //msgs.append(d_store+'\n');
            LogBuff.setGamePhaseSet(false);
        }
        else if(LogBuff.getCommandSet()){
            d_store=LogBuff.getCommand();
           // msgs.append(d_store+'\n');
            LogBuff.setCommandSet(false);
        }
        else if(LogBuff.getMessageSet()){
            d_store=LogBuff.getMessage();
            //msgs.append(d_store+'\n');
            LogBuff.setMessageSet(false);
        }

        try {
            bwr.newLine();
            bwr.write(d_store.toString());
            bwr.flush();
            System.out.println("data duplication");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
