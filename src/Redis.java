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
        connect.connectRedis();
        jedis = connect.getJedis();
    }

    public void insert(String id, String image){
        HashMap<String, String> redisMap = new HashMap<>();
        redisMap.put("image", image);
        jedis.hmset("id_" + id, redisMap);
    }

    public Map<String, String> getKeyMap(String key){
        return jedis.hgetAll(key);
    }

    public List<String> getKeyList(){
        Set<String> keys = jedis.keys("id_*");
        List<String> list = new ArrayList<>(keys);
        Collections.sort(list);
        return list;
    }
}
