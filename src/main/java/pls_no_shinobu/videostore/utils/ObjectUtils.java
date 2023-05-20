package pls_no_shinobu.videostore.utils;

import pls_no_shinobu.videostore.model.Entity;

public interface ObjectUtils<T extends Entity> {
    T parse(String str);
    
    String serialize(T obj);
}