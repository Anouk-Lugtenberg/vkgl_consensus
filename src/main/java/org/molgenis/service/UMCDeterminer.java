package org.molgenis.service;

import org.molgenis.model.UMCName;

import java.io.File;

public class UMCDeterminer {

    public UMCName determineUMC(File inputFile) {
        String file = inputFile.toString().toLowerCase();
        UMCName umcName;
        if (file.contains("amc")) {
            umcName = UMCName.AMC;
        } else if (file.contains("erasmus")) {
            umcName = UMCName.ERASMUS;
        } else if (file.contains("lumc")) {
            umcName = UMCName.LUMC;
        } else if (file.contains("nki")) {
            umcName = UMCName.NKI;
        } else if (file.contains("radboud")) {
            umcName = UMCName.RADBOUD;
        } else if (file.contains("umcg")) {
            umcName = UMCName.UMCG;
        } else if (file.contains("umcu")) {
            umcName = UMCName.UMCU;
        } else if (file.contains("vumc")) {
            umcName = UMCName.VUMC;
        } else {
            umcName = UMCName.UNKNOWN;
        }
        return umcName;
    }
}
