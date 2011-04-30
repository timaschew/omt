import org.primefaces.event.FileUploadEvent;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * File upload controller for midi file uploads.
 */
@Named
public class FileUploadBean {

	private static final String UPLOAD_SUCCESS_MESSAGE = "MIDI Upload erfolgreich";
	private static final int BUFFER_SIZE = 6124;

	/**
	 * Handler for midi file uploads.
	 *
	 * @param event upload event
	 */
	public void handleFileUpload(final FileUploadEvent event) {
		// get the real path for the upload directory
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		File result = new File(extContext.getRealPath("/upload") + "/" + event.getFile().getFileName());

		try {
			// write temporary file to a real file destination
			FileOutputStream fileOutputStream = new FileOutputStream(result);
			byte[] buffer = new byte[BUFFER_SIZE];

			int bulk;
			InputStream inputStream = event.getFile().getInputstream();
			while (true) {
				bulk = inputStream.read(buffer);
				if (bulk < 0) {
					break;
				}
				fileOutputStream.write(buffer, 0, bulk);
				fileOutputStream.flush();
			}

			fileOutputStream.close();
			inputStream.close();

			// success message
			FacesMessage msg = new FacesMessage(UPLOAD_SUCCESS_MESSAGE, event.getFile().getFileName() +
					" wurde erfolgreich hochgeladen.");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (IOException e) {
			e.printStackTrace();

			// error message
			FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler",
					event.getFile().getFileName() + " konnte nicht hochgeladen werden.");
			FacesContext.getCurrentInstance().addMessage(null, error);
		}
	}
}
