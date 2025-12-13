package brasil.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class ImageCloudinaryConfig {

    private static final Cloudinary cloudinary = new Cloudinary(
        ObjectUtils.asMap(
            "cloud_name", "ds4uom6xp",
            "api_key", "851972682531273",
            "api_secret", "qJRq4KyB_jspyhXdyJ_wYTne4Ys",
            "secure", true
        )
    );

    private ImageCloudinaryConfig() {}

    public static Cloudinary getInstance() {
        return cloudinary;
    }
}

