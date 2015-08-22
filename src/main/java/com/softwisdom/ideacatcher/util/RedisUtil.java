package com.softwisdom.ideacatcher.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

public class RedisUtil {
    public static int expireSeconds = 30 * 24 * 3600;
    public static ShardedJedisPool shardedJedisPool = null;
    private static Logger logger = LogManager.getLogger(RedisUtil.class.getName());

    static {
        List<JedisShardInfo> jedisShardInfos = new ArrayList<JedisShardInfo>();
        jedisShardInfos.add(new JedisShardInfo("182.92.222.78", 6389, 1000));

        JedisPoolConfig poolConfig = new JedisPoolConfig();
//		poolConfig.setMaxActive(60000);
        poolConfig.setMaxIdle(1000);
//		poolConfig.setMaxWait(1000L);
//		poolConfig.setTestOnBorrow(true);

        shardedJedisPool = new ShardedJedisPool(poolConfig, jedisShardInfos);
    }

    public static Object update(String key, Object obj) {
        return save(key, obj);
    }

    public static Object save(String key, Object obj) {
        long startTime = System.currentTimeMillis();
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            shardedJedis.setex(key.getBytes(), expireSeconds, SerializableUtil.serialize(obj));

            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 5) {
                logger.info("key:" + key + ", cost:" + (endTime - startTime) + " ms.");
            }

            shardedJedisPool.returnResource(shardedJedis);
            return obj;
        } catch (Exception e) {
            logger.error(e);
            if (shardedJedis != null) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            }
        }
        return null;
    }

    public static Long setLong(String key, Long num) {
        long startTime = System.currentTimeMillis();
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            shardedJedis.setex(key, expireSeconds, num.toString());

            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 5) {
                logger.info("key:" + key + ", cost:" + (endTime - startTime) + " ms.");
            }

            shardedJedisPool.returnResource(shardedJedis);
            return num;
        } catch (Exception e) {
            logger.error(e);
            if (shardedJedis != null) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            }
        }
        return null;
    }

    public static Long getLong(String key) {
        long startTime = System.currentTimeMillis();

        ShardedJedis jedis = shardedJedisPool.getResource();
        try {
            String num = jedis.get(key);
            Long result = null;
            try {
                result = Long.parseLong(num);
            } catch (Exception e) {
            }

            shardedJedisPool.returnResource(jedis);

            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 5) {
                logger.info("key:" + key + ", cost:" + (endTime - startTime) + " ms.");
            }
            return result;
        } catch (Exception e) {
            logger.error(e);
            if (jedis != null) {
                shardedJedisPool.returnBrokenResource(jedis);
            }
        }
        return null;
    }

    public static Long incr(String key) {
        long startTime = System.currentTimeMillis();
        ShardedJedis jedis = shardedJedisPool.getResource();
        try {
            Long count = jedis.incr(key);
            jedis.expire(key, expireSeconds);

            shardedJedisPool.returnResource(jedis);

            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 5) {
                logger.info("key:" + key + ", cost:" + (endTime - startTime) + " ms.");
            }
            return count;
        } catch (Exception e) {
            logger.error(e);
            if (jedis != null) {
                shardedJedisPool.returnBrokenResource(jedis);
            }
        }
        return null;
    }

    public static Long incrBy(String key, long incrCount) {
        long startTime = System.currentTimeMillis();
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            Long count = shardedJedis.incrBy(key, incrCount);
            shardedJedis.expire(key, expireSeconds);

            shardedJedisPool.returnResource(shardedJedis);
            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 5) {
                logger.info("key:" + key + ", cost:" + (endTime - startTime) + " ms.");
            }
            return count;
        } catch (Exception e) {
            logger.error(e);
            if (shardedJedis != null) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            }
        }
        return null;
    }

    public static Long decrBy(String key, long incrCount) {
        long startTime = System.currentTimeMillis();
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            Long count = shardedJedis.decrBy(key, incrCount);
            shardedJedis.expire(key, expireSeconds);
            shardedJedisPool.returnResource(shardedJedis);
            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 5) {
                logger.info("key:" + key + ", cost:" + (endTime - startTime) + " ms.");
            }
            return count;
        } catch (Exception e) {
            logger.error(e);
            if (shardedJedis != null) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            }
        }
        return null;
    }

    public static List<Object> getVideonRealTimePlayCount(Long videoId) {
        long startTime = System.nanoTime();

        String videoKey = GlobalCacheKey.video + videoId;
        String videoPlayCountRealTimekey = GlobalCacheKey.videoPlayCount + videoId;

        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        ShardedJedisPipeline pipeline = shardedJedis.pipelined();

        try {
            pipeline.get(videoKey.getBytes());
            pipeline.get(videoPlayCountRealTimekey);
            List<Object> results = pipeline.syncAndReturnAll();
            shardedJedisPool.returnResource(shardedJedis);

            long endTime = System.nanoTime();
            if (endTime - startTime > 5e6) {
                logger.info("videoId " + videoId + ", cost: " + (endTime - startTime) / 1.0e6 + "ms.");
            }
            return results;
        } catch (Exception e) {
            logger.error(e);
            if (shardedJedis != null) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            }
        }
        return null;
    }

    public static List<Object> getVideonRealTimePlayCounts(Long[] videoIdArr) {
        if (videoIdArr == null || videoIdArr.length == 0) {
            return null; // FIXME!
        }
        long startTime = System.nanoTime();
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        ShardedJedisPipeline pipeline = shardedJedis.pipelined();

        try {
            for (int i = 0; i < videoIdArr.length; i++) {
                Long videoId = videoIdArr[i];

                String videoKey = GlobalCacheKey.video + videoId;
                String videoPlayCountRealTimekey = GlobalCacheKey.videoPlayCount + videoId;

                pipeline.get(videoKey.getBytes());
                pipeline.get(videoPlayCountRealTimekey);
            }
            List<Object> results = pipeline.syncAndReturnAll();
            shardedJedisPool.returnResource(shardedJedis);

            long endTime = System.nanoTime();
            if (endTime - startTime > 5e6) {
                StringBuilder logstr = new StringBuilder("videoId ");
                for (int k = 0; k < videoIdArr.length; k++) {
                    logstr.append(videoIdArr[k] + ",");
                }
                logstr.append(" cost: " + (endTime - startTime) / 1.0e6 + "ms.");
                logger.info(logstr.toString());
            }
            return results;
        } catch (Exception e) {
            logger.error(e);
            if (shardedJedis != null) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            }
        }
        return null;
    }

    public static Object get(String key) {
        long startTime = System.currentTimeMillis();

        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            Object obj = (Object) SerializableUtil.deSerialize(shardedJedis.get(key.getBytes()));
            shardedJedisPool.returnResource(shardedJedis);

            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 5) {
                logger.info("key:" + key + ", cost:" + (endTime - startTime) + " ms.");
            }
            return obj;
        } catch (Exception e) {
            logger.error(e);
            if (shardedJedis != null) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            }
        }
        return null;
    }

    public static boolean remove(String key) {
        long startTime = System.currentTimeMillis();
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            shardedJedis.del(key);
            shardedJedisPool.returnResource(shardedJedis);

            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 5) {
                logger.info("key:" + key + ", cost:" + (endTime - startTime) + " ms.");
            }
            return true;
        } catch (Exception e) {
            logger.error(e);
            if (shardedJedis != null) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            }
        }
        return false;
    }

    public static String get2(String key) {
        long startTime = System.currentTimeMillis();

        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {
            String obj = shardedJedis.get(key);
            shardedJedisPool.returnResource(shardedJedis);

            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 5) {
                logger.info("key:" + key + ", cost:" + (endTime - startTime) + " ms.");
            }
            return obj;
        } catch (Exception e) {
            logger.error(e);
            if (shardedJedis != null) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            }
        }
        return null;
    }
}
