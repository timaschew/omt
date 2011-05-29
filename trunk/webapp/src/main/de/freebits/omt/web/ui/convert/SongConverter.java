package de.freebits.omt.web.ui.convert;

import de.freebits.omt.web.beans.SongDatabase;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This converter converts a song object to its string representative and vice versa.
 *
 * @author Marcel Karras
 */
@Named
public class SongConverter implements Converter {

	@Inject
	private SongDatabase songDatabase;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return songDatabase.getSongByTitle(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value == null ? null : value.toString();
	}
}
