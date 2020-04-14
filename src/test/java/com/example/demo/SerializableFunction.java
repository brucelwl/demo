package com.example.demo;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Created by bruce on 2020/4/10 14:16
 */
@FunctionalInterface
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {


}
