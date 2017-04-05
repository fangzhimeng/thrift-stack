package cn.kenshinn.thrift.thrift.protocol;

import cn.kenshinn.thrift.thrift.models.DemoModel;
import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;
import org.apache.thrift.TException;

/**
 * Created by kenshinn on 17-4-1.
 */
public class DemoModelDecoder extends Proto implements Decoder<DemoModel> {

    public DemoModelDecoder() {}

    public DemoModelDecoder(VerifiableProperties properties) {
        // Nothing to do
    }

    @Override
    public DemoModel fromBytes(byte[] bytes) {
        DemoModel demoModel = new DemoModel();
        try {
            deserializer.deserialize(demoModel, bytes);
            return demoModel;
        } catch (TException e) {
            e.printStackTrace();
        }

        return null;
    }

}
