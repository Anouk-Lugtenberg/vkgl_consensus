package org.molgenis;

import org.molgenis.CLIParser.CLIParser;
import org.molgenis.IO.FileProcessor;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        CLIParser cliParser = new CLIParser();
        cliParser.parseCLI(args);
        Path inputDirectory = cliParser.getInputDirectory();
        new FileProcessor(inputDirectory, cliParser.getSpliceOnType());
    }
}
