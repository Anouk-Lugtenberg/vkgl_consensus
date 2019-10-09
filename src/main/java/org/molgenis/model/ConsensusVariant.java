package org.molgenis.model;

import java.util.Comparator;

public class ConsensusVariant {
    private String chromosome;
    private int position;
    private String REF;
    private String ALT;
    private String classificationAMC;
    private String classificationErasmus;
    private String classificationLUMC;
    private String classificationNKI;
    private String classificationRadboud;
    private String classificationUMCG;
    private String classificationUMCU;
    private String classificationVUMC;

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getREF() {
        return REF;
    }

    public void setREF(String REF) {
        this.REF = REF;
    }

    public String getALT() {
        return ALT;
    }

    public void setALT(String ALT) {
        this.ALT = ALT;
    }

    public String getClassificationAMC() {
        return classificationAMC;
    }

    public void setClassificationAMC(String classificationAMC) {
        this.classificationAMC = classificationAMC;
    }

    public String getClassificationErasmus() {
        return classificationErasmus;
    }

    public void setClassificationErasmus(String classificationErasmus) {
        this.classificationErasmus = classificationErasmus;
    }

    public String getClassificationLUMC() {
        return classificationLUMC;
    }

    public void setClassificationLUMC(String classificationLUMC) {
        this.classificationLUMC = classificationLUMC;
    }

    public String getClassificationNKI() {
        return classificationNKI;
    }

    public void setClassificationNKI(String classificationNKI) {
        this.classificationNKI = classificationNKI;
    }

    public String getClassificationRadboud() {
        return classificationRadboud;
    }

    public void setClassificationRadboud(String classificationRadboud) {
        this.classificationRadboud = classificationRadboud;
    }

    public String getClassificationUMCG() {
        return classificationUMCG;
    }

    public void setClassificationUMCG(String classificationUMCG) {
        this.classificationUMCG = classificationUMCG;
    }

    public String getClassificationUMCU() {
        return classificationUMCU;
    }

    public void setClassificationUMCU(String classificationUMCU) {
        this.classificationUMCU = classificationUMCU;
    }

    public String getClassificationVUMC() {
        return classificationVUMC;
    }

    public void setClassificationVUMC(String classificationVUMC) {
        this.classificationVUMC = classificationVUMC;
    }

    @Override
    public String toString() {
        return this.chromosome + ", Position: " + this.position + ", REF: " + this.REF + ", ALT: " + this.ALT +
                ", Erasmus: " + this.classificationErasmus + ", Radboud: " + this.classificationRadboud + ", UMCG: " + this.classificationUMCG;
    }

    public int compareTo(ConsensusVariant consensusVariant) {
        return Comparators.CHROMOSOME_AND_POSITION.compare(this, consensusVariant);
    }

    public static class Comparators {
        public static final Comparator<ConsensusVariant> CHROMOSOME_AND_POSITION =
                Comparator.comparing(ConsensusVariant::getChromosome, Comparator.comparingInt(Comparators::extractInt))
                .thenComparing(ConsensusVariant::getPosition);

        static int extractInt(String chromosome) {
            String num = chromosome.replaceAll("\\D", "");
            return num.isEmpty() ? chromosome.charAt(0) : Integer.parseInt(num);
        }
    }
}
