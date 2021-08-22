package com.netty.rpc.serializer;

/**
 * @author hdz
 * @description TODO
 * @create 2021年08月21日 20:25
 */
public abstract class Serializer {
    public abstract <T> byte[] serialize(T obj);

    public abstract <T> Object deserialize(byte[] bytes, Class<T> clazz);
}