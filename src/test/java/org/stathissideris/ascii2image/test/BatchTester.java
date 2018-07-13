package org.stathissideris.ascii2image.test;
import org.stathissideris.ascii2image.core.CommandLineConverter;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class BatchTester {
    public static void main(String [] args) {

        //missing input size check
    String inputFolder = args[0];
    String outputFolder = args[1];
    String expectedOutputFolder = args[2];

    int pass = 0;
    int fail = 0;
    //For Each file in the
    File folder = new File(inputFolder);
    if(folder.exists())
    {
        StringBuilder report = new StringBuilder();
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
                File outputFile = new File(outputFolder + file.getName().replaceAll(".txt",".eps"));
                File expectedOutputFile = new File(expectedOutputFolder + file.getName().replaceAll(".txt",".eps"));

                boolean passed = false;
                if(outputFile.exists())
                {
                   if(expectedOutputFile.exists())
                   {
                       if(FileUtils.contentEquals(outputFile,expectedOutputFile))
                       {
                           report.append(file.getName() + " PASS\n");
                           passed = true;
                       }
                       else {
                           report.append(file.getName() + " FAIL (Output doesn't match expected output)\n");
                       }
                   }
                   else
                   {
                       report.append(file.getName() + " FAIL (Expected EPS file missing)\n");
                   }
                }
                else
                {
                    report.append(file.getName() + " FAIL (No EPS file created)\n");
                }

                if(passed)
                {
                    pass++;
                }
                else
                {
                    fail++;
                }
                ///
            } catch (Exception e) {
                e.printStackTrace();
                fail++;
                report.append(file.getName() + " FAIL (Exception was thrown)\n");
            }

        }
        report.append(pass+ "/" + (pass+fail) + " tests succeeded.\n");
        System.out.println(report.toString());
    }
    }
}
