package com.jd.appoint.common.utils;


import com.jd.jim.cli.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 包装自己需要的缓存
 * Created by lijianzhen1 on 2017/3/23.
 */
public class RedisCache {

    private static Logger logger = LoggerFactory.getLogger(RedisCache.class);
    @Resource(name = "jimClient")
    private Cluster jimClient;

    /**
     * 返回key的value。如果key不存在，返回null。如果key的value不是string，就返回错误，因为GET只处理string类型的values。
     *
     * @param key
     * @return
     */
    public String get(String key) {
        try {
            return jimClient.get(key);
        } catch (Exception e) {
            logger.error("RedisCache.get().error!key: " + key, e);
        }

        return null;
    }

    /**
     * @param key
     * @return
     */
    public Object getObject(String key) {
        try {
            return jimClient.getObject(key);
        } catch (Exception e) {
            logger.error("RedisCache.getObject().error!key: " + key, e);
        }

        return null;
    }

    /**
     * 获取对象
     *
     * @param key
     * @param requiredType
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> requiredType) {

        T rtObj = null;

        try {
            Object obj = jimClient.getObject(key);
            if (obj != null) {
                rtObj = (T) obj;
            }
        } catch (Exception e) {
            logger.error("RedisCache.<T> T get().error! key: " + key, e);
        }

        return rtObj;
    }

    /**
     * 将key和value对应。如果key已经存在了，它会被覆盖，而不管它是什么类型。
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        try {
            jimClient.set(key, value);
        } catch (Exception e) {
            logger.error("RedisCache.set().error!", e);
        }
    }

    /**
     * 将key和value对应。如果key已经存在了，它会被覆盖，而不管它是什么类型。
     *
     * @param key
     * @param value
     */
    public void setObject(String key, Object value) {
        try {
            jimClient.setObject(key, value);
        } catch (Exception e) {
            logger.error("RedisCache.setObject().error!", e);
        }
    }

    /**
     * 将key和value对应。如果key已经存在了，它会被覆盖，而不管它是什么类型。并进行设置过期时间
     *
     * @param key
     * @param seconds
     * @param value
     */
    public void set(String key, long seconds, String value) {
        if (value == null) {
            logger.error("set() value is null!");
            return;
        }
        try {
            jimClient.setEx(key, value, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("RedisCache.setEx().error!", e);
        }
    }

    /**
     * 将key和value对应。如果key已经存在了，它会被覆盖，而不管它是什么类型。并进行设置过期时间
     *
     * @param key
     * @param seconds
     * @param value
     */
    public void setObject(String key, long seconds, Object value) {
        if (value == null) {
            logger.error("setObject() value is null!");
            return;
        }

        setObject(key, value);
        expire(key, seconds);
    }

    /**
     * 设置数据的超时时间
     *
     * @param key
     * @param seconds
     * @return
     */
    public boolean expire(String key, long seconds) {
        return expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 返回key的string类型value的长度。如果key对应的非string类型，就返回错误。
     *
     * @param key
     * @return key对应的字符串value的长度，或者0（key不存在）
     */
    public Long strLen(String key) {
        try {
            return jimClient.strLen(key);
        } catch (Exception e) {
            logger.error("RedisCache.strLen().error!", e);
        }

        return -1L;
    }

    /**
     * 设置数据的超时时间
     *
     * @param key
     * @param timeout
     * @return
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        try {
            return jimClient.expire(key, timeout, unit);
        } catch (Exception e) {
            logger.error("RedisCache.expire().error!", e);
        }
        return false;
    }

    /**
     * 删除某个key对应的值, 如果删除的key不存在，则直接忽略
     *
     * @param key
     * @return 被删除的keys的数量
     */
    public Long delete(String key) {
        try {
            return jimClient.del(key);
        } catch (Exception e) {
            logger.error("RedisCache.delete().error!", e);
        }

        return -1L;
    }

    /**
     * 检查该key是否存在 ,即使值是空的字符串，也是返回true
     *
     * @param key
     * @return 存在返回true，不存在返回false, 报异常返回null
     */
    public Boolean exists(String key) {

        Boolean result = null;

        try {
            result = jimClient.exists(key);
        } catch (Exception e) {
            logger.error("RedisCache.exists().error!", e);
        }

        return result;
    }

    /**
     * 将该key的数据加1，如果该key不存在该key的数据将被设置成0，然后再加1
     *
     * @param key
     * @return 返回加过之后的数据 ,报异常返回null（注意：如果之前该key存储不为数值类型，会报异常）
     */
    public Long incr(String key) {
        Long result = null;

        try {
            result = jimClient.incr(key);
        } catch (Exception e) {
            logger.error("RedisCache.incr().error!", e);
        }

        return result;
    }

    /**
     * 对key对应的数字做减1操作。如果key不存在，那么在操作之前，这个key对应的值会被置为0。
     * 如果key有一个错误类型的value或者是一个不能表示成数字的字符串，就返回错误。
     *
     * @param key
     * @return 减小之后的value
     */
    public Long decr(String key) {
        Long result = null;
        try {
            result = jimClient.decr(key);
        } catch (Exception e) {
            logger.error("RedisCache.decr().error!", e);
        }
        return result;
    }

    /**
     * 如果key已经存在并且是一个字符串，APPEND命令将value追加到key原来的值之后。
     * 如果key不存在，APPEND就简单地将指定key设为value，就像SET key value一样。
     *
     * @param key
     * @param value
     * @return 追加value之后字符串的长度, 如果抛异常，则返回-1
     */
    public Long append(String key, String value) {

        try {
            return jimClient.append(key, value);
        } catch (Exception e) {
            logger.error("RedisCache.append().error!", e);
        }

        return -1L;
    }

    /**
     * 将所有指定的值插入到存于 key 的列表的头部。如果 key 不存在，那么在进行 push 操作前会创建一个空列表。
     * 如果 key 对应的值不是一个 list 的话，那么会返回一个错误。
     *
     * @param key
     * @param values
     * @return 在 push 操作后的 list 长度。如果抛异常，则返回-1
     */
    public Long lPush(String key, String... values) {
        try {
            return jimClient.lPush(key, values);
        } catch (Exception e) {
            logger.error("RedisCache.lPush().error!", e);
        }

        return -1L;
    }

    /**
     * 向存于 key 的列表的尾部插入所有指定的值。如果 key 不存在，那么会创建一个空的列表然后再进行 push 操作。
     * 当 key 保存的不是一个列表，那么会返回一个错误。
     *
     * @param key
     * @param values
     * @return 在 push 操作后的 list 长度。如果抛异常，则返回-1
     */
    public Long rPush(String key, String... values) {
        try {
            return jimClient.rPush(key, values);
        } catch (Exception e) {
            logger.error("RedisCache.rPush().error!", e);
        }

        return -1L;
    }

    /**
     * 只有当 key 已经存在并且存着一个 list 的时候，在这个 key 下面的 list 的头部插入 value。
     * 与 LPUSH 相反，当 key 不存在的时候不会进行任何操作。
     *
     * @param key
     * @param value
     * @return 在 push 操作后的 list 长度。 如果抛异常，则返回-1
     */
    public Long lPushX(String key, String value) {
        try {
            return jimClient.lPushX(key, value);
        } catch (Exception e) {
            logger.error("RedisCache.lpushX().error!", e);
        }

        return -1L;
    }

    /**
     * 将值 value 插入到列表 key 的表尾, 当且仅当 key 存在并且是一个列表。 和 RPUSH 命令相反, 当 key 不存在时，RPUSHX 命令什么也不做。
     *
     * @param key
     * @param value
     * @return RPUSHX 命令执行之后，表的长度。
     */
    public Long rPushX(String key, String value) {
        try {
            return jimClient.rPushX(key, value);
        } catch (Exception e) {
            logger.error("RedisCache.rPushX().error!", e);
        }

        return -1L;
    }

    /**
     * 移除并且返回 key 对应的 list 的第一个元素。
     *
     * @param key
     * @return 返回第一个元素的值，或者当 key 不存在时返回 null(注意：当抛异常时也返回null)
     */
    public String lPop(String key) {
        try {
            return jimClient.lPop(key);
        } catch (Exception e) {
            logger.error("RedisCache.lPop().error!", e);
        }

        return null;
    }

    /**
     * 移除并返回存于 key 的 list 的最后一个元素。
     *
     * @param key
     * @return 最后一个元素的值，或者当 key 不存在的时候返回 null。
     */
    public String rPop(String key) {
        try {
            return jimClient.rPop(key);
        } catch (Exception e) {
            logger.error("RedisCache.rPop().error!", e);
        }

        return null;
    }

    /**
     * 返回存储在 key 里的list的长度。 如果 key 不存在，那么就被看作是空list，并且返回长度为 0。
     * 当存储在 key 里的值不是一个list的话，会返回error。
     *
     * @param key
     * @return key对应的list的长度。 如果抛异常，则返回-1
     */
    public Long lLen(String key) {
        try {
            return jimClient.lLen(key);
        } catch (Exception e) {
            logger.error("RedisCache.lLen().error!", e);
        }

        return -1L;
    }

    /**
     * 返回存储在 key 的列表里指定范围内的元素。 begin 和 end 偏移量都是基于0的下标，即list的第一个元素下标是0（list的表头），
     * 第二个元素下标是1，以此类推。偏移量也可以是负数，表示偏移量是从list尾部开始计数。
     * 例如， -1 表示列表的最后一个元素，-2 是倒数第二个，以此类推。
     * <p>
     * 需要注意的是，如果你有一个list，里面的元素是从0到100，那么 LRANGE list 0 10 这个命令会返回11个元素，即最右边的那个元素也会被包含在内。
     * <p>
     * 当下标超过list范围的时候不会产生error。 如果begin比list的尾部下标大的时候，会返回一个空列表。
     * 如果end比list的实际尾部大的时候，Redis会当它是最后一个元素的下标。
     *
     * @param key
     * @param begin
     * @param end
     * @return 指定范围里的列表元素。
     */
    public List<String> lRange(String key, long begin, long end) {
        try {
            return jimClient.lRange(key, begin, end);
        } catch (Exception e) {
            logger.error("RedisCache.lRange().error!", e);
        }

        return null;
    }

    /**
     * 返回列表里的元素的索引 index 存储在 key 里面。
     * 负数索引用于指定从列表尾部开始索引的元素。在这种方法下，-1 表示最后一个元素，-2 表示倒数第二个元素，并以此往前推。
     * 当 key 位置的值不是一个列表的时候，会返回一个error。
     *
     * @param key
     * @param index
     * @return 请求的对应元素，或者当 index 超过范围的时候返回 null。
     */
    public String lIndex(String key, long index) {

        try {
            return jimClient.lIndex(key, index);
        } catch (Exception e) {
            logger.error("RedisCache.lIndex().error!", e);
        }

        return null;

    }

    /**
     * 修剪(trim)一个已存在的 list，这样 list 就会只包含指定范围的指定元素。
     * begin 和 end 也可以用负数来表示与表尾的偏移量，比如 -1 表示列表里的最后一个元素， -2 表示倒数第二个，等等。
     * <p>
     * 如果 begin 超过列表尾部，或者 begin > end，结果会是列表变成空表（即该 key 会被移除）。
     * 如果 end 超过列表尾部，Redis 会将其当作列表的最后一个元素。
     *
     * @param key
     * @param begin
     * @param end
     */
    public void lTrim(String key, long begin, long end) {

        try {
            jimClient.lTrim(key, begin, end);
        } catch (Exception e) {
            logger.error("RedisCache.lTrim().error!", e);
        }

    }

    /**
     * 设置 key 指定的哈希集中指定字段的值。如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联。
     * 如果field在哈希集中存在，它将被重写。
     *
     * @param key
     * @param field 哈希集的k
     * @param value 哈希集的v
     * @return
     */
    public Boolean hSet(String key, String field, String value) {

        Boolean result = null;

        try {
            result = jimClient.hSet(key, field, value);
        } catch (Exception e) {
            logger.error("RedisCache.hSet().error!", e);
        }

        return result;

    }

    /**
     * 返回 key 指定的哈希集中该字段(field)所关联的值
     *
     * @param key
     * @param field
     * @return 该字段(field)所关联的值。当字段不存在或者 key 不存在时返回null。
     */
    public String hGet(String key, String field) {

        try {
            return jimClient.hGet(key, field);
        } catch (Exception e) {
            logger.error("RedisCache.hGet().error!", e);
        }

        return null;
    }

    /**
     * 返回字段是否是 key 指定的哈希集中存在的字段。
     *
     * @param key
     * @param field
     * @return
     */
    public boolean hExists(String key, String field) {

        Boolean result = null;

        try {
            result = jimClient.hExists(key, field);
        } catch (Exception e) {
            logger.error("RedisCache.hExists().error!", e);
        }

        return result;
    }

    /**
     * 设置 key 指定的哈希集中指定字段(field)的值。该命令将重写所有在哈希集中存在的字段(field)。
     * 如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联
     *
     * @param key
     * @param hashes
     */
    public void hMSet(String key, Map<String, String> hashes) {

        try {
            jimClient.hMSet(key, hashes);
        } catch (Exception e) {
            logger.error("RedisCache.hMSet().error!", e);
        }
    }

    /**
     * 设置 key 指定的哈希集中指定字段(field)的值。该命令将重写所有在哈希集中存在的字段(field)。
     * 如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联
     *
     * @param key
     * @param seconds
     * @param hashes
     */
    public void hMSet(String key, long seconds, Map<String, String> hashes) {

        try {
            jimClient.hMSet(key, hashes);
            expire(key, seconds);
        } catch (Exception e) {
            logger.error("RedisCache.hMSet().error!", e);
        }
    }

    /**
     * 返回 key 指定的哈希集中指定字段的值。
     * 对于哈希集中不存在的每个字段，返回 null 值。
     * 因为不存在的key被认为是一个空的哈希集，对一个不存在的 key 执行 HMGET 将返回一个只含有 null 值的列表
     *
     * @param key
     * @param fields
     * @return
     */
    public List<String> hMGet(String key, String... fields) {

        try {
            return jimClient.hMGet(key, fields);
        } catch (Exception e) {
            logger.error("RedisCache.hMGet().error!", e);
        }

        return null;
    }

    /**
     * 返回 key 指定的哈希集中所有的字段和值.
     *
     * @param key
     * @return 哈希集中字段和值的列表。当 key 指定的哈希集不存在时返回空列表。
     */
    public Map<String, String> hGetAll(String key) {

        try {
            return jimClient.hGetAll(key);
        } catch (Exception e) {
            logger.error("RedisCache.hGetAll().error!", e);
        }

        return null;
    }

    /**
     * 从 key 指定的哈希集中移除指定的域。在哈希集中不存在的域将被忽略。
     * 如果 key 指定的哈希集不存在，它将被认为是一个空的哈希集，该命令将返回0。
     *
     * @param key
     * @param fields
     * @return 返回从哈希集中成功移除的域的数量，不包括指出但不存在的那些域
     */
    public Long hDel(String key, String... fields) {

        try {
            return jimClient.hDel(key, fields);
        } catch (Exception e) {
            logger.error("RedisCache.hDel().error!", e);
        }

        return -1L;
    }

    /**
     * 返回 key 指定的哈希集包含的字段的数量。
     *
     * @param key
     * @return 哈希集中字段的数量，当 key 指定的哈希集不存在时返回 0
     */
    public Long hLen(String key) {

        try {
            return jimClient.hLen(key);
        } catch (Exception e) {
            logger.error("RedisCache.hLen().error!", e);
        }

        return -1L;
    }

    /**
     * 返回 key 指定的哈希集中所有字段的名字。
     *
     * @param key
     * @return 哈希集中的字段列表，当 key 指定的哈希集不存在时返回空列表。
     */
    public Set<String> hKeys(String key) {

        try {
            return jimClient.hKeys(key);
        } catch (Exception e) {
            logger.error("RedisCache.hKeys().error!", e);
        }

        return null;
    }

    /**
     * 返回 key 指定的哈希集中所有字段的值。
     *
     * @param key
     * @return 哈希集中的值的列表，当 key 指定的哈希集不存在时返回空列表。
     */
    public List<String> hVals(String key) {

        try {
            return jimClient.hVals(key);
        } catch (Exception e) {
            logger.error("RedisCache.hVals().error!", e);
        }

        return null;
    }

    /**
     * 添加一个或多个指定的member元素到集合的 key中.指定的一个或者多个元素member 如果已经在集合key中存在则忽略.
     * 如果集合key 不存在，则新建集合key,并添加member元素到集合key中.
     * 如果key 的类型不是集合则返回错误.
     *
     * @param key
     * @param values
     * @return 返回新成功添加到集合里元素的数量，不包括已经存在于集合中的元素.
     */
    public Long sAdd(String key, String... values) {
        try {
            return jimClient.sAdd(key, values);
        } catch (Exception e) {
            logger.error("RedisCache.sAdd().error!", e);
        }

        return -1L;
    }

    /**
     * 在key集合中移除指定的元素. 如果指定的元素不是key集合中的元素则忽略 如果key集合不存在则被视为一个空的集合，该命令返回0.
     * 如果key的类型不是一个集合,则返回错误.
     *
     * @param key
     * @param values
     * @return
     */
    public Long sRem(String key, String... values) {
        try {
            return jimClient.sRem(key, values);
        } catch (Exception e) {
            logger.error("RedisCache.sAdd().error!", e);
        }

        return -1L;
    }

    /**
     * 返回集合存储的key的基数 (集合元素的数量)
     *
     * @param key
     * @return 集合的基数(元素的数量), 如果key不存在, 则返回 0.
     */
    public Long sCard(String key) {
        try {
            return jimClient.sCard(key);
        } catch (Exception e) {
            logger.error("RedisCache.sAdd().error!", e);
        }

        return -1L;
    }

    /**
     * 返回成员 member 是否是存储的集合 key的成员.
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean sIsMember(String key, String value) {

        Boolean result = null;

        try {
            result = jimClient.sIsMember(key, value);
        } catch (Exception e) {
            logger.error("RedisCache.sIsMember().error!", e);
        }

        return result;
    }

    /**
     * 返回key集合所有的元素.
     *
     * @param key
     * @return
     */
    public Set<String> sMembers(String key) {

        try {
            return jimClient.sMembers(key);
        } catch (Exception e) {
            logger.error("RedisCache.sMembers().error!", e);
        }

        return null;
    }

    public Long hIncrBy(String key, String field, Long num){
        return jimClient.hIncrBy(key, field, num);
    }

}
