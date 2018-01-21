package pers.hugh.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/10/9</pre>
 */
public class JsonUtil {

    public static JacksonMapper mapper = JacksonMapper.buildNormalMapper();

    public static <T> T readJsonToObject(String jsonString, Class<T> clazz) {
        return mapper.toObject(jsonString, clazz);
    }

    public static <T> T readJsonToObject(String jsonString, TypeReference<T> type) {
        return mapper.toObject(jsonString, type);
    }

    public static String writeObjectToJson(Object object) {
        return mapper.toJson(object);
    }

    public static Map readJsonToMap(String jsonString) {
        return mapper.toObject(jsonString, Map.class);
    }

    public static String writeMapToJson(Map<String, String> map) {
        return mapper.toJson(map);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String addJsonKeyValue(String json, String key, String value) {
        Map info = readJsonToMap(json);
        info.put(key, value);
        return writeMapToJson(info);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String removeJsonKeyValue(String json, String key) {
        Map info = readJsonToMap(json);
        info.remove(key);
        return writeMapToJson(info);
    }

    public static String getJsonValueByKey(String json, String key) {
        @SuppressWarnings("unchecked")
        Map<String, String> info = readJsonToMap(json);
        return info.get(key);
    }

    /**
     * 简单封装Jackson实现JSON<->Java Object的Mapper.
     * 封装不同的输出风格, 使用不同的builder函数创建实例.
     */
    static class JacksonMapper {

        private ObjectMapper mapper;

        public JacksonMapper(JsonInclude.Include include) {
            mapper = new ObjectMapper();
            //设置序列化时包含属性的风格
            mapper.setSerializationInclusion(include);
            //序列化时，允许属性为空的beans创建
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            //反序列化时，忽略在JSON字符串中存在但Java对象实际没有的属性
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //允许字段名不带引号
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        }

        public JacksonMapper(JsonInclude.Include include, boolean isAllowUnquotedControlChars) {
            mapper = new ObjectMapper();
            //设置序列化时包含属性的风格
            mapper.setSerializationInclusion(include);

            //序列化时，允许属性为空的beans创建
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

            //反序列化时，忽略在JSON字符串中存在但Java对象实际没有的属性
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            //允许字段名不带引号
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            //是否允许JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）。 如果该属性关闭，则如果遇到这些字符，则会抛出异常。
            //JSON标准说明书要求所有控制符必须使用引号，因此这是一个非标准的特性。
            //注意：默认时候，该属性关闭的
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, isAllowUnquotedControlChars);
        }

        /**
         * 创建输出全部属性到Json字符串的Mapper.
         */
        public static JacksonMapper buildNormalMapper() {
            return new JacksonMapper(JsonInclude.Include.ALWAYS);
        }

        /**
         * 创建只输出非空属性到Json字符串的Mapper.
         */
        public static JacksonMapper buildNonNullMapper() {
            return new JacksonMapper(JsonInclude.Include.NON_NULL);
        }

        /**
         * 创建只输出初始值被改变的属性到Json字符串的Mapper.
         */
        public static JacksonMapper buildNonDefaultMapper() {
            return new JacksonMapper(JsonInclude.Include.NON_DEFAULT);
        }

        /**
         * 创建序列化时忽略注解的Mapper.
         */
        public static JacksonMapper buildAnnotationIgnoreMapper() {
            JacksonMapper JacksonMapper = buildNormalMapper();
            JacksonMapper.getMapper().configure(MapperFeature.USE_ANNOTATIONS, false);
            return JacksonMapper;
        }


        /**
         * 如果JSON字符串为null或"null"字符串, 返回null
         * 如果JSON字符串为"[]", 返回空集合
         * 读取集合如List/Map
         *
         * @param jsonString
         * @param clazz
         * @param <T>
         * @return
         */
        public <T> T toObject(String jsonString, Class<T> clazz) {
            if (isBlank(jsonString)) {
                return null;
            }
            try {
                T t = mapper.readValue(jsonString, clazz);
                return t;
            } catch (IOException e) {
                return null;
            }
        }

        /**
         * 如果JSON字符串为null或"null"字符串, 返回null
         * 如果JSON字符串为"[]", 返回空集合
         * 读取集合如List/Map
         * TypeReference 可以 通过 new 方法来解决：比如
         * List<String>  通过 new TypeReference<List<String>>(){}
         *
         * @param jsonString
         * @param type
         * @param <T>
         * @return
         */
        @SuppressWarnings("unchecked")
        public <T> T toObject(String jsonString, TypeReference<T> type) {
            if (isBlank(jsonString)) {
                return null;
            }

            try {
                T t = (T) mapper.readValue(jsonString, type);
                return t;
            } catch (IOException e) {
                return null;
            }
        }

        /**
         * 如果对象为null, 返回"null"
         * 如果集合为空集合, 返回"[]"
         */
        public String toJson(Object object) {
            try {
                return mapper.writeValueAsString(object);
            } catch (IOException e) {
                return null;
            }
        }

        /**
         * 当JSON里只含有Bean的部分属性时，更新一個已存在Bean，只覆盖部分的属性.
         */
        @SuppressWarnings("unchecked")
        public <T> T update(T object, String jsonString) {
            try {
                return (T) mapper.readerForUpdating(object).readValue(jsonString);
            } catch (IOException e) {
                return null;
            }
        }

        /**
         * 设定是否使用Enum的toString函数来读写Enum,为false时使用Enum的name()函数来读写Enum, 默认为false.
         * 注意本函数一定要在Mapper创建后, 所有的读写动作之前调用.
         *
         * @param value
         */
        public void setEnumUseToString(boolean value) {
            mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, value);
            mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, value);
        }

        /**
         * 取出Mapper做进一步的设置或使用其他序列化API.
         */
        public ObjectMapper getMapper() {
            return mapper;
        }

        private boolean isBlank(String s) {
            return s == null || s.trim().length() == 0;
        }
    }

    //以下代码为用法示例
    public static void main(String[] args) {
        System.out.println(writeObjectToJson(null));
        System.out.println(writeObjectToJson(new ArrayList<>()));
        System.out.println(readJsonToObject("[\"abc\",\"def\"]", List.class).get(0).getClass());
        System.out.println(readJsonToObject("[\"abc\",\"def\"]", new TypeReference<List<String>>(){}).get(0).getClass());

        TestBean testBean = new TestBean(123, null, new ArrayList<>());
        System.out.println(writeObjectToJson(testBean));
        System.out.println(readJsonToObject(writeObjectToJson(testBean), TestBean.class).toString());
        String testBeanList = "[{\"num\":123,\"str\":null,\"list\":[]},{\"num\":123,\"str\":null,\"list\":[\"abc\",\"def\"]}]";
        System.out.println(readJsonToObject(testBeanList, List.class).get(0).getClass());
        System.out.println(readJsonToObject(testBeanList, new TypeReference<List<TestBean>>(){}).get(0).getClass());
    }

    static class TestBean {
        private int num;
        private String str;
        private List<String> list;

        public TestBean() {
        }

        public TestBean(int num, String str, List<String> list) {
            this.num = num;
            this.str = str;
            this.list = list;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "TestBean{" +
                    "num=" + num +
                    ", str='" + str + '\'' +
                    ", list=" + list +
                    '}';
        }
    }
}
