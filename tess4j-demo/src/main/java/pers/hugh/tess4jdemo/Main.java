package pers.hugh.tess4jdemo;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws TesseractException, IOException {
        Resource tessdataResource = new ClassPathResource("tessdata");
        String tessdataPath = tessdataResource.getFile().getPath();
        final ITesseract instance = new Tesseract();
        // 语言库位置
        instance.setDatapath(tessdataPath);
        // 中英文库
        instance.setLanguage("eng+chi_sim+chi_sim_vert");
        // 简体中文库
//        instance.setLanguage("chi_sim");

        // 待识别的图片路径
        Resource imagesResource = new ClassPathResource("images");
        for (File image : imagesResource.getFile().listFiles()) {
            System.out.println(image.getName() + "---\n" + instance.doOCR(image));
        }
    }
}
