package org.stathissideris.ascii2image.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.stathissideris.ascii2image.core.CommandLineConverter;

import org.catapult.sa.tribble.FuzzResult;
import org.catapult.sa.tribble.FuzzTest;

public class Fuzzer implements FuzzTest {

    public FuzzResult test(byte[] data) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String input = String.format("/tmp/%s.txt", uuid);
        String output = String.format("/tmp/%s.eps", uuid);
        FileOutputStream stream = new FileOutputStream(input);
        stream.write(data);
        stream.close();
        String[] args = {
            "--eps",
            input,
            output
        };
        CommandLineConverter.main(args);
        System.out.printf("input=%s output=%s\n", input, output);
        return FuzzResult.OK;
    }

}
