package ysomap.payloads;

import ysomap.bullets.Bullet;
import ysomap.core.serializer.Serializer;
import ysomap.core.serializer.SerializerFactory;
import ysomap.core.util.ReflectionHelper;

/**
 * @author wh1t3P1g
 * @since 2021/6/13
 */
public interface Payload<T> {

    default Payload<T> set(String key, Object value) throws Exception {
        ReflectionHelper.setFieldValue(this, key, value);
        return this;
    }

    default boolean has(String key) {
        return ReflectionHelper.getField(this.getClass(), key) != null;
    }

    T getObject() throws Exception;

    void setBullet(Bullet bullet);

    default boolean checkObject(Object obj) {
        return true;// 默认不检查也可以，如需检查重载该函数
    }

    default Serializer<?> getSerializer(){
        return SerializerFactory.createSerializer("default");
    }

    Bullet getDefaultBullet(Object... args) throws Exception;

    /**
     * 装弹，将最终达成的利用效果拼接反序列化利用链
     * 反序列化利用链的实现也在这部分实现
     * @param obj
     * @return
     */
    T pack(Object obj) throws Exception;
}