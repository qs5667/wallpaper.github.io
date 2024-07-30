import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.json.JSONObject;

public class BingWallpaperDownloader {
    private static final String BING_API_URL = "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";
    private static final String BING_BASE_URL = "https://www.bing.com";

    public static void main(String[] args) throws IOException {
        String imageUrl = getImageUrl();
        downloadImage(imageUrl);
    }

    private static String getImageUrl() throws IOException {
        URL url = new URL(BING_API_URL);
        try (InputStream is = url.openStream()) {
            String jsonContent = new String(is.readAllBytes());
            JSONObject json = new JSONObject(jsonContent);
            String relativeImageUrl = json.getJSONArray("images").getJSONObject(0).getString("url");
            return BING_BASE_URL + relativeImageUrl;
        }
    }

    private static void downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        String fileName = "bing_wallpaper_" + System.currentTimeMillis() + ".jpg";
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
        }
        System.out.println("Wallpaper downloaded: " + fileName);
    }
}
