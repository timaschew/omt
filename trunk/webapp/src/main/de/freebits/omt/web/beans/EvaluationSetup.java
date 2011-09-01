package de.freebits.omt.web.beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@Named
@SessionScoped
public class EvaluationSetup implements Serializable {

    private String id;
    private String title;
    private EvaluationResult result;
    private UploadedFile uploadedFile;
    private Date date;

    public EvaluationSetup(final String id, final String title, final UploadedFile uploadedFile) {
        this.id = id;
        this.title = title;
        this.uploadedFile = uploadedFile;
        date = new Date();
    }

    public final String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public String getUploadedFileName() {
        if (uploadedFile != null && uploadedFile.getFile() != null)
            return uploadedFile.getFile().getName();
        return null;
    }

    public EvaluationResult getResult() {
        return result;
    }

    public void setResult(EvaluationResult result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        return df.format(date);
    }
}
