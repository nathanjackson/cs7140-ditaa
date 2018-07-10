package org.stathissideris.ascii2image.test;
import org.stathissideris.ascii2image.core.CommandLineConverter;

import java.io.File;
import java.io.IOException;

public class BatchTester {
    public static void main(String [] args) {

        //missing input size check
    String inputFolder = args[0];
    String outputFolder = args[1];


    //For Each file in the
    File folder = new File(inputFolder);
    if(folder.exists())
        for(File file: folder.listFiles())
        {
            String ditaaArgs[] = new String[3];
            try {
                ditaaArgs[0] = file.getCanonicalPath();
                ditaaArgs[1] = outputFolder + file.getName().replaceAll(".txt",".eps");
                ditaaArgs[2] = "-eps";
                //Run DITAA, we probably want to implement a silent mode for ditaa
                // (ignoring output, out we just write our results to a file)
                CommandLineConverter.main(ditaaArgs);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
