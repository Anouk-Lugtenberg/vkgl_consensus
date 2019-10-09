package org.molgenis.IO;

import org.molgenis.CLIParser.CLIParser;
import org.molgenis.model.ConsensusVariant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class ConsensusVariantWriter {

    public ConsensusVariantWriter(Path inputDirectory, ArrayList<ConsensusVariant> variants, boolean splice) {
        try {
            File variantFile = new File(inputDirectory + File.separator + "consensus.txt");
            createHeader(variantFile);
            for (ConsensusVariant variant : variants) {
                String line = createVariantLine(variant);
                BufferedWriter writerVariants = new BufferedWriter(new FileWriter(variantFile, true));
                writerVariants.append(line);
                writerVariants.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (splice) {
            File snpFile = new File(inputDirectory + File.separator + "consensus_snps.txt");
            File insertionFile = new File(inputDirectory + File.separator + "consensus_insertions.txt");
            File deletionFile = new File(inputDirectory + File.separator + "consensus_deletions.txt");
            File delinsFile = new File(inputDirectory + File.separator + "consensus_delins.txt");
            createHeader(snpFile);
            createHeader(insertionFile);
            createHeader(deletionFile);
            createHeader(delinsFile);
            for (ConsensusVariant variant : variants) {
                String line = createVariantLine(variant);
                if (variant.getREF().length() == 1 && variant.getALT().length() == 1) {
                    writeLine(snpFile, line);
                } else if (variant.getREF().length() == 1 && variant.getALT().length() > 1) {
                    writeLine(insertionFile, line);
                } else if (variant.getREF().length() > 1 && variant.getALT().length() == 1) {
                    writeLine(deletionFile, line);
                } else if (variant.getREF().length() > 1 && variant.getALT().length() > 1) {
                    writeLine(delinsFile, line);
                }
            }
        }
    }

    private void createHeader(File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.append("chr\tpos\tref\talt\tamc\terasmus\tlumc\tnki\tradboud\tumcg\tumcu\tvumc\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeLine(File file, String line) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(line);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createVariantLine(ConsensusVariant variant) {
        return variant.getChromosome() +
                "\t" +
                variant.getPosition() +
                "\t" +
                variant.getREF() +
                "\t" +
                variant.getALT() +
                "\t" +
                variant.getClassificationAMC() +
                "\t" +
                variant.getClassificationErasmus() +
                "\t" +
                variant.getClassificationLUMC() +
                "\t" +
                variant.getClassificationNKI() +
                "\t" +
                variant.getClassificationRadboud() +
                "\t" +
                variant.getClassificationUMCG() +
                "\t" +
                variant.getClassificationUMCU() +
                "\t" +
                variant.getClassificationVUMC() +
                "\n";
    }
}
