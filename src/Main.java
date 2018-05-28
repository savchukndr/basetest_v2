import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Andrii Savchuk on 28.05.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class Main {
    private static String base64;

    public static String getBase64(String picturePath) {
        FileInputStream fis11 = null;
        try {
            fis11 = new FileInputStream(picturePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bos11 = new ByteArrayOutputStream();
        byte[] buf = new byte[8096];
        try {
            assert fis11 != null;
            for (int readNum; (readNum = fis11.read(buf)) != -1; ) {
                bos11.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        byte[] bytes = bos11.toByteArray();
        base64 = Base64.getEncoder().encodeToString(bytes);
        return base64;
    }

    public static void decodeBase64(String decString, String filePath) throws IOException {
        byte[] decodedString;
        decodedString = new sun.misc.BASE64Decoder().decodeBuffer(decString);
        File of = new File(filePath);
        FileOutputStream osf = new FileOutputStream(of);
        osf.write(decodedString);
        osf.flush();
    }

    private static List<String> listFilesForFolder(final File folder) {
        List<String> list = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                list.add(fileEntry.getName());
            }
        }
        return list;
    }

    public static void main(String[] args){
        Postgres postgres = new Postgres();
        postgres.query("CREATE TABLE IF NOT EXISTS test(" +
                "id SERIAL, " +
                "image TEXT NOT NULL);");
        String path = "C:\\test\\test_pictures\\test_pictures";
        final File folder = new File(path);
        List<String> listOfImagesNames = listFilesForFolder(folder);
        List<String> listOfBase64Images = new ArrayList<>();
        for (String image: listOfImagesNames){
            listOfBase64Images.add(getBase64(path + "\\" + image));
        }

        //PostgreSQL insert time
        for (String image: listOfBase64Images){
//            String name = String.valueOf(rn.nextInt(k - 1) + 1);
            postgres.query("INSERT INTO test(image) VALUES ('" + image + "');");
        }

        //PostgreSQL select time
        ResultSet resultSet = postgres.selectAllquery("SELECT image FROM test;");


        try {
            int k = 1;
            FileWriter fileWriter = null;
            while(resultSet.next()){
//                resultSet.getString("image");
                decodeBase64(resultSet.getString("image"), "C:\\test\\test_image\\" + k + ".jpg");
                k++;
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

//        int count = 1;
//        for (String image: resList) {
////            FileWriter fileWriter = null;
//            try {
//                decodeBase64(image, "C:\\test\\test_image\\" + count + ".jpg");
//                fileWriter = new FileWriter(of);
//                fileWriter.write(getBase64(path + "\\" + image) + "\n");
//                fileWriter.flush();
//                fileWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            count++;
//        }

////        File of = new File("C:\\test\\test_image.txt");
//        int count = 1;
//        for (String image: listOfImages) {
////            FileWriter fileWriter = null;
//            try {
//                decodeBase64(getBase64(path + "\\" + image), "C:\\test\\test_image\\" + count + ".jpg");
//                fileWriter = new FileWriter(of);
//                fileWriter.write(getBase64(path + "\\" + image) + "\n");
//                fileWriter.flush();
//                fileWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            count++;
//        }
    }
}
