package de.freebits.omt.web.beans;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;

/**
 * This beans provides information for an evaluable song.
 *
 * @author Marcel Karras
 */
@Named
@SessionScoped
public class Song extends SelectItem implements Serializable {

    // title of the song
    private String title;

    // filename of the song
    private String filename;

    /**
     * Set the song's title.
     *
     * @param title title string
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Get the song's title.
     *
     * @return song's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the song's filename.
     *
     * @return song's filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Set the song's filename.
     *
     * @param filename song's filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return title;
    }


    public static Song createSong(final String title, final String filename){
        final Song song = new Song();
        song.setTitle(title);
        song.setFilename(filename);
        return song;
    }
}
