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
    private static List<String> keyList;
    private static Map<String, String> mapOfValues;

    private static double checkTime(Date ob1, Date ob2){
        long msElapsedTime;
        msElapsedTime = ob2.getTime() - ob1.getTime();
        Long longObject = new Long(String.valueOf(msElapsedTime));
        return longObject.doubleValue() * 0.001;
    }

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
        String path = "C:\\test\\test_pictures\\test_pictures";
        final File folder = new File(path);
        List<String> listOfImagesNames = listFilesForFolder(folder);
        List<String> listOfBase64Images = new ArrayList<>();
        for (String image: listOfImagesNames){
            listOfBase64Images.add(getBase64(path + "\\" + image));
        }

        //Redis insert time
        Redis redis = new Redis();
        int count = 1;
        Date startDate1 = new Date();
        for (String image: listOfBase64Images){
            redis.insert(String.valueOf(count), image);
            count++;
        }
        Date endDate1 = new Date();
        System.out.println("Redis insert time: " + checkTime(startDate1, endDate1));
        System.out.println();

        //Redis get data time
        Date startDate2 = new Date();
        keyList = redis.getKeyList();
        for(int i=0;i<=keyList.size() - 1;i++)
        {
            mapOfValues = redis.getKeyMap(keyList.get(i));
            try {
                decodeBase64(mapOfValues.get("image"), "C:\\test\\test_image_redis\\" + keyList.get(i) + ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Date endDate2 = new Date();
        System.out.println("Redis get time: " + checkTime(startDate2, endDate2));
        System.out.println();


        //PostgreSQL insert time
        Postgres postgres = new Postgres();
        postgres.query("CREATE TABLE IF NOT EXISTS test(" +
                "id SERIAL, " +
                "image TEXT NOT NULL);");

        Date startDate3 = new Date();
        for (String image: listOfBase64Images){
            postgres.query("INSERT INTO test(image) VALUES ('" + image + "');");
        }
        Date endDate3 = new Date();
        System.out.println("Postgres insert time: " + checkTime(startDate3, endDate3));
        System.out.println();

        //PostgreSQL select time
        ResultSet resultSet = postgres.selectAllquery("SELECT image FROM test;");

        //Write result into folder
        Date startDate4 = new Date();
        try {
            int k = 1;
            while(resultSet.next()){
                decodeBase64(resultSet.getString("image"), "C:\\test\\test_image_postgres\\" + k + ".jpg");
                k++;
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        Date endDate4 = new Date();
        System.out.println("Postgres get time: " + checkTime(startDate4, endDate4));
        System.out.println();
    }
}
