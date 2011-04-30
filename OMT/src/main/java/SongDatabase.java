import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Static song pseudo-database that is getting loaded once for the whole application lifetime.
 */
@Named
public class SongDatabase {

	private static List<Song> songList = new ArrayList<Song>();

	static {
		songList.add(new Song("TEST"));
	}

	public final List<Song> getSongList() {
		return songList;
	}

	public final Song getDefaultSong() {
		return songList.get(0);
	}
}
