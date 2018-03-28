package pers.hugh.dozerdemo.invoke;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import pers.hugh.dozerdemo.entity.Source;
import pers.hugh.dozerdemo.entity.SourceSub;
import pers.hugh.dozerdemo.entity.Target;

import java.util.Arrays;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/3/28</pre>
 */
public class MapperStater {

    public static void main(String[] args) {
        Mapper mapper = DozerBeanMapperBuilder.create().withMappingFiles("dozer/dozer-mapping.xml").build();
        Source source = new Source();
        source.setA(99);
        source.setB("sss");
        source.setList(Arrays.asList("s1","s2","s3"));
        SourceSub sourceSub = new SourceSub();
        sourceSub.setSubA(9999);
        sourceSub.setSubB("sssbbb");
        sourceSub.setList(Arrays.asList("sb1","sb2","sb3"));
        source.setList2(Arrays.asList(sourceSub));

        Target target = mapper.map(source, Target.class);

        System.out.println(target.toString());
    }
}
