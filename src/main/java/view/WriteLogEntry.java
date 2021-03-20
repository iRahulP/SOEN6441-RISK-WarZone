package view;

import model.LogEntryBuffer;
import model.Observable;
import model.Observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteLogEntry implements Observer {
    String d_fileName="log.txt";
    private static String d_Store;
    private FileWriter d_WriteFile;
    private BufferedWriter d_Bwr ;

    public WriteLogEntry() {

        try{
            d_WriteFile = new FileWriter("log.txt");
            d_Bwr = new BufferedWriter(d_WriteFile);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Observable o) {

        LogEntryBuffer LogBuff = (LogEntryBuffer)o;
        if(LogBuff.getGamePhaseSet()){

            d_Store=LogBuff.getPhaseValue();
            LogBuff.setGamePhaseSet(false);
        }
        else if(LogBuff.getCommandSet()){
            d_Store=LogBuff.getCommand();
            LogBuff.setCommandSet(false);
        }
        else if(LogBuff.getMessageSet()){
            d_Store=LogBuff.getMessage();
            LogBuff.setMessageSet(false);
        }

        try {
            d_Bwr.newLine();
            d_Bwr.write(d_Store.toString());
            d_Bwr.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
