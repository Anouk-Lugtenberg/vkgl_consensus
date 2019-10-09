package org.molgenis.IO;

import org.apache.commons.io.FileUtils;
import org.molgenis.model.UMCName;
import org.molgenis.service.UMCDeterminer;

import java.io.File;
import java.nio.file.Path;
import java.util.Iterator;

public class FileProcessor {
    public FileProcessor(Path inputDirectory, boolean splice) {
        String[] fileExtensions = new String[1];
        fileExtensions[0] = "vcf";
        UMCDeterminer umcDeterminer = new UMCDeterminer();
        Iterator<File> iterator = FileUtils.iterateFiles(new File(inputDirectory.toString()), fileExtensions, false);
        VariantIterator variantIterator = new VariantIterator();
        while (iterator.hasNext()) {
            File file = iterator.next();
            UMCName umcName = umcDeterminer.determineUMC(file);
            System.out.println("\nCreating consensus for: " + umcName);
            variantIterator.start(umcName, file);
        }
        new ConsensusVariantWriter(inputDirectory, variantIterator.getConsensusVariantArrayList(), splice);
    }
}
