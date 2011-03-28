import java.util.ArrayList;
import java.util.List;

public class SongDatabase {
	private static List<Song> songList = new ArrayList<Song>();

	static {
		songList.add(new Song("TEST"));
	}
}
