package cn.kenshinn.thrift.thrift.protocol;

import cn.kenshinn.thrift.thrift.models.DemoModel;
import kafka.coordinator.Dead;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;
import org.apache.thrift.TException;

/**
 * Created by kenshinn on 17-4-1.
 */
public class DemoModelEncoder extends Proto implements Encoder<DemoModel> {

    public DemoModelEncoder(){}

    public DemoModelEncoder(VerifiableProperties properties) {
        // Nothing to do
    }

    @Override
    public byte[] toBytes(DemoModel demoModel) {
        try {
            return serializer.serialize(demoModel);
        } catch (TException e) {
            e.printStackTrace();
        }

        return null;
    }

}
