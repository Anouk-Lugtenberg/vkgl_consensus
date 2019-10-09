package org.molgenis.IO;

import org.molgenis.model.ConsensusVariant;
import org.molgenis.model.UMCName;
import org.molgenis.model.Variant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class VariantIterator {
    private UMCName umcName;
    private ArrayList<ConsensusVariant> consensusVariantArrayList = new ArrayList<>();
    private int countBenign = 0;
    private int countLikelyBenign = 0;
    private int countVous = 0;
    private int countLikelyPathogenic = 0;
    private int countPathogenic = 0;

    public void start(UMCName umcName, File file) {
        this.umcName = umcName;
        countBenign = 0;
        countLikelyBenign = 0;
        countVous = 0;
        countLikelyPathogenic = 0;
        countPathogenic = 0;
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            if (consensusVariantArrayList.isEmpty()) {
                while ((line = reader.readLine()) != null) {
                    Variant variant = createVariantFields(line);
                    countClassificationPerUMC(variant.getClassification());
                    addVariantToConsensusList(variant);
                }
            } else {
                while (( line = reader.readLine()) != null) {
                    Variant variant = createVariantFields(line);
                    countClassificationPerUMC(variant.getClassification());
                    ConsensusVariant consensusVariant = checkIfVariantAlreadyInList(variant);
                    if (consensusVariant == null) {
                        //variant does not exist in the list yet
                        addVariantToConsensusList(variant);
                    } else {
                        setClassificationForConsensusVariant(consensusVariant, variant);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("countBenign = " + countBenign);
        System.out.println("countLikelyBenign = " + countLikelyBenign);
        System.out.println("countVous = " + countVous);
        System.out.println("countLikelyPathogenic = " + countLikelyPathogenic);
        System.out.println("countPathogenic = " + countPathogenic);
    }

    private void countClassificationPerUMC(String classification) {
        if (classification.equals("BENIGN")) {
            countBenign++;
        } else if (classification.equals("LIKELY_BENIGN")) {
            countLikelyBenign++;
        } else if (classification.equals("VOUS")) {
            countVous++;
        } else if (classification.equals("LIKELY_PATHOGENIC")) {
            countLikelyPathogenic++;
        } else if (classification.equals("PATHOGENIC")) {
            countPathogenic++;
        }
    }

    private Variant createVariantFields(String line) {
        Variant variant = new Variant();
        String[] columns = line.split("\t");
        variant.setChromosome(columns[0]);
        variant.setPosition(Integer.parseInt(columns[1]));
        variant.setREF(columns[2]);
        variant.setALT(columns[3]);
        variant.setClassification(columns[4]);
        return variant;
    }

    private void addVariantToConsensusList(Variant variant) {
        consensusVariantArrayList.add(createConsensusVariant(variant));
    }

    private ConsensusVariant createConsensusVariant(Variant variant) {
        ConsensusVariant consensusVariant = new ConsensusVariant();
        consensusVariant.setChromosome(variant.getChromosome());
        consensusVariant.setPosition(variant.getPosition());
        consensusVariant.setREF(variant.getREF());
        consensusVariant.setALT(variant.getALT());
        setClassificationForConsensusVariant(consensusVariant, variant);
        return consensusVariant;
    }

    private ConsensusVariant checkIfVariantAlreadyInList(Variant variant) {
        return consensusVariantArrayList
                .stream()
                .filter(consensusVariant ->
                        consensusVariant.getChromosome().equals(variant.getChromosome())
                                && consensusVariant.getPosition() == variant.getPosition()
                                && consensusVariant.getREF().equals(variant.getREF())
                                && consensusVariant.getALT().equals(variant.getALT()))
                .findFirst()
                .orElse(null);
    }

    private void setClassificationForConsensusVariant(ConsensusVariant consensusVariant, Variant variant) {
        switch (umcName) {
            case AMC:
                consensusVariant.setClassificationAMC(variant.getClassification());
                break;
            case RADBOUD:
                consensusVariant.setClassificationRadboud(variant.getClassification());
                break;
            case NKI:
                consensusVariant.setClassificationNKI(variant.getClassification());
                break;
            case LUMC:
                consensusVariant.setClassificationLUMC(variant.getClassification());
                break;
            case UMCG:
                consensusVariant.setClassificationUMCG(variant.getClassification());
                break;
            case UMCU:
                consensusVariant.setClassificationUMCU(variant.getClassification());
                break;
            case VUMC:
                consensusVariant.setClassificationVUMC(variant.getClassification());
                break;
            case ERASMUS:
                consensusVariant.setClassificationErasmus(variant.getClassification());
                break;
        }
    }

    public ArrayList<ConsensusVariant> getConsensusVariantArrayList() {
        this.consensusVariantArrayList.sort(ConsensusVariant::compareTo);
        return this.consensusVariantArrayList;
    }


}
