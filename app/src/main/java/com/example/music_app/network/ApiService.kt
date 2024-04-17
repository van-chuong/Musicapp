import com.example.music_app.models.Album
import com.example.music_app.models.AlbumResponse
import com.example.music_app.models.ArtistResponse
import retrofit2.Call
import retrofit2.http.GET
import com.example.music_app.models.GenreResponse
import com.example.music_app.models.PlaylistResponse
import com.example.music_app.models.TrackResponse
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre")
    fun getGenres(): Call<GenreResponse>

    @GET("chart/0/artists")
    fun getArtists(): Call<ArtistResponse>

    @GET("chart/0/albums")
    fun getAlbums(): Call<AlbumResponse>

    @GET("chart/0/tracks")
    fun getTopTracks(): Call<TrackResponse>
    @GET("chart/0/playlists")
    fun getPopularPlaylists(): Call<PlaylistResponse>

}