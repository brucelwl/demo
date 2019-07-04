package com.zto.boot.example;

import org.junit.Test;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionService;

/**
 * Created by bruce on 2019/7/2 9:59
 */
public class ApplicationConversionServiceTest {

    @Test
    public void test() {
        ConversionService sharedInstance = ApplicationConversionService.getSharedInstance();

        Integer convert = sharedInstance.convert("12", Integer.class);
        System.out.println(convert);

        Integer convert1 = sharedInstance.convert("154", int.class);
        System.out.println(convert1);

    }


}
