package pers.hugh.dozerdemo.converter;

import org.dozer.CustomConverter;
import pers.hugh.dozerdemo.entity.SourceSub;
import pers.hugh.dozerdemo.entity.Target;
import pers.hugh.dozerdemo.entity.TargetSub;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/3/28</pre>
 */
public class SourceSubConverter implements CustomConverter{

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }
        return "targetSub:" + sourceFieldValue;
    }
}
