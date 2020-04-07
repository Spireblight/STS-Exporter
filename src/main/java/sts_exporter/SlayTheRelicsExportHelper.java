package sts_exporter;

import com.megacrit.cardcrawl.cards.AbstractCard;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class SlayTheRelicsExportHelper {

    public static SlayTheRelicsExportHelper instance;

    public ArrayList<String> backgrounds;
    public ArrayList<String> foregrounds;

    public static void init() {
        instance = new SlayTheRelicsExportHelper();
    }

    private SlayTheRelicsExportHelper() {
        backgrounds = new ArrayList<>();
        foregrounds = new ArrayList<>();
    }

    public static String getBackgroundPath(AbstractCard.CardColor color, AbstractCard.CardType type) {
        return String.format("export/str/img/cards/%s/background_%s.png", color.toString(), type.toString());
    }

    public static String getForegroundPath(AbstractCard.CardColor color, AbstractCard.CardType type, AbstractCard.CardRarity rarity) {
        return String.format("export/str/img/cards/%s/frame_%s_%s.png", color.toString(), type.toString(), rarity.toString());
    }

    public static String getPortraitPath(AbstractCard.CardColor color, String cardId) {
        String name = "";
        if (cardId.contains(":"))
            name = cardId.split(":", 2)[1];
        else
            name = cardId;

        return String.format("export/str/img/cards/%s/portraits/%s.png", color.toString(), name);
    }

    public static boolean fileExists(String path) {
        return new File(path).exists();
    }

    public static void makeDirs(String path) {
        try {
            File f = new File(path).getParentFile();
            if (!f.exists())
                Files.createDirectories(f.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void convertPNGToJPG(String source) throws IOException {
        FileInputStream inputStream = new FileInputStream(source);
        FileOutputStream outputStream = new FileOutputStream(source.replace(".png", ".jpg"));

        // reads input image from file
        BufferedImage inputImage = ImageIO.read(inputStream);

        JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
        jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(0.8f);

        final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        writer.setOutput(new FileImageOutputStream(new File(source.replace(".png", ".jpg"))));

        writer.write(null, new IIOImage(inputImage, null, null), jpegParams);

        // needs to close the streams
        outputStream.close();
        inputStream.close();
    }
}
