namespace java cn.kenshinn.thrift.models
namespace py cn.kenshinn.thrift.models

struct DemoModel {
    1: required string attr1,
    2: required string attr2,
    3: required string attr3,
    //should be timestamp in millisecond, not seconds
    4: required i64 long4
}
