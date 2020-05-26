package com.example.demo.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.PropertyBuilder;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by bruce on 2020/5/10 14:57
 */
public class MyBeanSerializerFactory extends BeanSerializerFactory {
    private static final long serialVersionUID = 8507702577778663260L;

    private NumberSerializers.DoubleSerializer defDoubleSerializer;

    public MyBeanSerializerFactory(SerializerFactoryConfig config) {
        super(config);

        _concrete.put(Double.class.getName(), new DoubleSerializer(Double.class));
        _concrete.put(Double.TYPE.getName(), new DoubleSerializer(Double.TYPE));

        defDoubleSerializer = new NumberSerializers.DoubleSerializer(Double.class);
    }

    @Override
    protected BeanPropertyWriter _constructWriter(SerializerProvider prov, BeanPropertyDefinition propDef, PropertyBuilder pb, boolean staticTyping, AnnotatedMember accessor) throws JsonMappingException {

        BeanPropertyWriter beanPropertyWriter = super._constructWriter(prov, propDef, pb, staticTyping, accessor);

        JsonSerializer<Object> serializer = beanPropertyWriter.getSerializer();
        if (serializer == null && propDef.getField().getAnnotation(UseDefaultJsonSer.class) != null) {
            if (propDef.getRawPrimaryType() == double.class || propDef.getRawPrimaryType() == Double.class) {
                beanPropertyWriter.assignSerializer(defDoubleSerializer);
            }
        }
        return beanPropertyWriter;
    }

    @JacksonStdImpl
    public static class DoubleSerializer extends NumberSerializers.Base<Object> {
        private static final long serialVersionUID = 5971645539232333165L;

        public DoubleSerializer(Class<?> cls) {
            super(cls, JsonParser.NumberType.DOUBLE, "number");
        }

        @Override
        public void serialize(Object value, JsonGenerator gen,
                              SerializerProvider provider) throws IOException {
            double v = (double) (Math.round((Double) value * 100)) / 100;
            gen.writeNumber(v);
        }

        // IMPORTANT: copied from `NonTypedScalarSerializerBase`
        @Override
        public void serializeWithType(Object value, JsonGenerator g,
                                      SerializerProvider provider, TypeSerializer typeSer)
                throws IOException {
            // 08-Feb-2018, tatu: Except that as per [databind#2236], NaN values need
            //    special handling
            Double d = (Double) value;
            if (notFinite(d)) {
                WritableTypeId typeIdDef = typeSer.writeTypePrefix(g,
                        // whether to indicate it's number or string is arbitrary; important it is scalar
                        typeSer.typeId(value, JsonToken.VALUE_NUMBER_FLOAT));
                g.writeNumber(d);
                typeSer.writeTypeSuffix(g, typeIdDef);
            } else {
                g.writeNumber(d);
            }
        }

        public static boolean notFinite(double value) {
            // `jackson-core` has helper method in 3 but not yet
            return Double.isNaN(value) || Double.isInfinite(value);
        }
    }


}
