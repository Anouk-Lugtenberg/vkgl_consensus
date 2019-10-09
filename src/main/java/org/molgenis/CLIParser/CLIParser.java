package org.molgenis.CLIParser;

import org.apache.commons.cli.*;

import java.io.File;
import java.nio.file.Path;

public class CLIParser {
    private Options options = new Options();
    private Path inputDirectory;
    private boolean spliceOnType = false;

    public CLIParser() {
        options.addOption("i", "inputDirectory", true, "Directory containing VCF like files");
        options.addOption("splice", "spliceOnType", false, "Create consensus files based on type (snp/ins/del");
    }

    public void parseCLI(String[] args) {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("i")) {
                this.inputDirectory = new File(cmd.getOptionValue("i")).toPath();
            } else {
                throw new IllegalArgumentException("Missing directory for input files.");
            }
            if (cmd.hasOption("splice")) {
                spliceOnType = true;
            }
        } catch (ParseException e) {
            System.out.println("Something went wrong while parsing the command line arguments");
        }
    }

    public Path getInputDirectory() {
        return this.inputDirectory;
    }

    public boolean getSpliceOnType() {
        return this.spliceOnType;
    }

}
