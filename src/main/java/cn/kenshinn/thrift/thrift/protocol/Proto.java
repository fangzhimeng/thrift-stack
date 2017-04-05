package cn.kenshinn.thrift.thrift.protocol;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TSerializer;

/**
 * Created by kenshinn on 17-3-31.
 */
public abstract class Proto {

    public static final TDeserializer deserializer = new TDeserializer();

    public static final TSerializer serializer = new TSerializer();

}
