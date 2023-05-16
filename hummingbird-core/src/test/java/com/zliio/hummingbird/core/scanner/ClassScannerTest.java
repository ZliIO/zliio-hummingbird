package com.zliio.hummingbird.core.scanner;

import com.zliio.hummingbird.core.annotation.bean.ComponentScan;
import com.zliio.hummingbird.core.factory.ClassFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * 类扫描相关的实现注解
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
@ComponentScan("com.upuphub.Hummingbird")
public class ClassScannerTest {

    @Test
    public void scanClassByPackageName(){
        ClassFactory.loadClass(new String[]{ClassScannerTest.class.getPackage().getName()});
        assertEquals(1, ClassFactory.CLASSES.size());
    }

}
