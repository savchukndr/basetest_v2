import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by savch on 19.12.2016.
 * All rights is okey =)
 */
class Redis{
    private Jedis jedis;
    private Connector connect;

    public Redis(){
        connect = new Connector();
        jedis = connect.getJedis();
    }

    public void insert(String id, String image){
        HashMap<String, String> redisMap = new HashMap<>();
        redisMap.put("image", image);
        jedis.hmset("id:" + id, redisMap);
    }

    void getData(){

    }



//    void insertCar(){
//        try {
//            carInfo = new HashMap<>();
//            for(int i=1; i < models.length; i++) {
//                for (int j=1; j < engines.length; j++) {
////                    System.out.println("Inserting [" + id_car + "] key-value for cars");
//                    carInfo.put("model", models[i]);
//                    carInfo.put("engine", engines[j]);
//                    jedis.hmset("carID:" + id_car, carInfo);
////                    System.out.println("Inserted [" + id_car + "] key-value for cars...\n");
//                    id_car++;
//                    counter++;
//                }
//            }
//        }catch (Exception e){
//            System.out.println("Error \"insertCar()\" is: " + e);
//        }
//    }
//
//    void insertMaster(){
//        try {
//            id_car--;
//            masterInfo = new HashMap<>();
//            for(int i=1; i<=numRow; i++) {
////                System.out.println("Inserting [" + i + "] key-value for masters");
//                masterInfo.put("name", "Name" + i);
//                int index = randomGenerator.nextInt(id_car - 1) + 1;
//                masterInfo.put("car", "carID:" + index);
//                jedis.hmset("masterID:" + i, masterInfo);
////                System.out.println("Inserted [" + i + "] key-value for masters...\n");
//                counter++;
//            }
//        }catch (Exception e){
//            System.out.println("Error \"insertMaster()\" is: " + e);
//        }
//    }
//
//
//    void retreiveRecord(){
//        try {
//            String cId, mId;
//            String valEng = "", valMod = "", valName = "", resCar = "", tmp;
//            for(int i=1; i<=counter; i++){
//                mId = "masterID:" + i;
//                Map<String, String> resM = jedis.hgetAll(mId);
//                Set set = resM.entrySet();
//                for (Object aSet : set) {
//                    Map.Entry me = (Map.Entry) aSet;
//                    if (me.getKey().equals("name")) {
//                        valName = me.getKey() + ": " + me.getValue();
//                    }
//                    if (me.getKey().equals("car")) {
//                        for(int j=1; j<54; j++){
//                            cId = "carID:" + j;
//                            tmp = me.getValue() + "";
//                            if (cId.equals(tmp)) {
//                                Map<String, String> resC = jedis.hgetAll(cId);
//                                Set set1 = resC.entrySet();
//                                for (Object bSet : set1) {
//                                    Map.Entry ke = (Map.Entry) bSet;
//                                    if (ke.getKey().equals("model")) {
//                                        valMod = ke.getKey() + ": " + ke.getValue();
//                                    }
//                                    if (ke.getKey().equals("engine")) {
//                                        valEng = ke.getKey() + ": " + ke.getValue();
//                                    }
//                                }
//                                resCar = cId + ": {" + valMod + ", " + valEng +"}";
//                                break;
//                            }
//                        }
////                        System.out.println(mId + "{" + valName + ", " + resCar + "}");
//                    }
//                }
//            }
//        }catch (Exception e){
//            System.out.println("Error \"retreiveData()\" is: " + e);
//        }
//    }
//
//    void deleteRecord(){
//        try {
//            jedis.flushAll();
//        }catch (Exception e){
//            System.out.println("Error \"ideleteRecord()\" is: " + e);
//        }
//    }
}
