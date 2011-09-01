package de.freebits.omt.web.beans;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.Serializable;

/**
 * Bean for an uploaded file.
 */
@Named
public class UploadedFile implements Serializable {

    private File file;

    @Inject
    private Song song;

    public static UploadedFile createUploadedFile(final File file, final Song song){
        final UploadedFile uf = new UploadedFile();
        uf.setFile(file);
        uf.setSong(song);
        return uf;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
