package pers.hugh.common.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.JSONPObject;
import java.io.IOException;

/**
 * 简单封装Jackson实现JSON<->Java Object的Mapper.
 * 
 * 封装不同的输出风格, 使用不同的builder函数创建实例.
 */
public class JacksonMapper {

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

		// allow JSON Strings to contain unquoted control characters
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,isAllowUnquotedControlChars);
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
	 *创建序列化时忽略注解的Mapper.
	 */
    public static JacksonMapper buildAnnotationIgnoreMapper(){
        JacksonMapper JacksonMapper = buildNormalMapper();
        JacksonMapper.getMapper().configure(MapperFeature.USE_ANNOTATIONS, false);
        return JacksonMapper;
    }


    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     * 
    	 * 如需读取集合如List/Map, 且不是List<String>这种简单类型时使用如下语句,使用后面的函数.
     */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (isEmpty(jsonString)) {
			return null;
		}

		try {
			T t = (T) mapper.readValue(jsonString, clazz);
			return t;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需读取集合如List/Map, 且不是List<String>时,
	 * 先用constructParametricType(List.class,MyBean.class)構造出JavaType,再调用本函数
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, JavaType javaType) {
		if (isEmpty(jsonString)) {
			return null;
		}

		try {
			T t = (T) mapper.readValue(jsonString, javaType);
			return t;
		} catch (IOException e) {
			return null;
		}
	}
	
	
	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需读取集合如List/Map, 且不是List<String>時,
	 * TypeReference 可以 通过 new 方法来解决：比如
	 * 
	 *  List<String>  通过 new TypeReference<List<String>>(){} 相对于JavaType 更直观明了些
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, TypeReference<T> type) {
		if (isEmpty(jsonString)) {
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
	 * 构造泛型的Type如List<MyBean>, Map<String,MyBean>
	 */
	public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
		return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
	}

	/**
	 * 如果对象为Null, 返回"null".
	 * 如果集合为空集合, 返回"[]".
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
	 * 输出JSONP格式数据
	 */
	public String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}

	/**
	 * 设定是否使用Enum的toString函数来读写Enum,
	 * 为false时使用Enum的name()函数来读写Enum, 默认为false.
	 * 注意本函数一定要在Mapper创建后, 所有的读写动作之前调用.
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


    private boolean isEmpty(String s) {
        return s == null || s.trim().equals("")? true : false;
    }
}
