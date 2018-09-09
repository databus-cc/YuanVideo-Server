package cc.databus.common;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class UUIDGenerator {
    private static TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());

    public static String generate() {
        return gen.generate().toString();
    }
}
