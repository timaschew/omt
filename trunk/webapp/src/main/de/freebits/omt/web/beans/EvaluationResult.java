package de.freebits.omt.web.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Evaluation result class.
 */
@Named
@SessionScoped
public class EvaluationResult implements Serializable {

    private double overallSimilarity;
    private List<List<String>> errorTable = new ArrayList<List<String>>();

    public double getOverallSimilarity() {
        return overallSimilarity;
    }

    public void setOverallSimilarity(double overallSimilarity) {
        this.overallSimilarity = overallSimilarity;
    }

    public static String formattedSimilarity(double similarity) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(similarity * 100);
    }

    public void addErrorTableEntry(String key, String value) {
        List<String> newList = new ArrayList<String>();
        newList.add(key);
        newList.add(value);
        errorTable.add(newList);
    }

    public List<List<String>> getErrorTable() {
        return errorTable;
    }

    public void setErrorTable(List<List<String>> errorTable) {
        this.errorTable = errorTable;
    }
}
