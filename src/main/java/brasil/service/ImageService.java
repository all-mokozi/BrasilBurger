package brasil.service;

import java.io.File;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class ImageService {

    private final Cloudinary cloudinary;

    public ImageService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "ds4uom6xp",
            "api_key", "851972682531273",
            "api_secret", "qJRq4KyB_jspyhXdyJ_wYTne4Ys",
            "secure", true
        ));
    }

    public String uploadImage(File imageFile) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                imageFile,
                ObjectUtils.asMap("folder", "brasilburger")
            );

            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Erreur upload image Cloudinary", e);
        }
    }
}
