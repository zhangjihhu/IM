package netty.attribute;

import io.netty.util.AttributeKey;
import netty.session.Session;

public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
