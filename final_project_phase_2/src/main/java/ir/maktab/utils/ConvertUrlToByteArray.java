package ir.maktab.utils;


import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


@Component
public class ConvertUrlToByteArray {

    public byte[] convertToByteArray(String url) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(url));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        return bos.toByteArray();
    }
}
