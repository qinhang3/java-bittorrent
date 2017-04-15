package win.qinhang3.javabittorrent.util;

import win.qinhang3.javabittorrent.common.metadata.entity.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hang on 17/4/7.
 */
public class BDecoding {

    //String  3:abc
    //Integer i-3e
    //List l4:spam4:eggse
    //{'spam': ['a', 'b']} d4:spaml1:a1:bee

    public static Entity decoding(byte[] input, AtomicInteger index) throws IOException {
        Entity entity;
        int start = index.get();
        char c = (char) input[index.getAndIncrement()];
        if (c == 'e'){
            return null;
        }
        if (c == 'i'){
            int value = 0;
            while (( c = (char)input[index.getAndIncrement()]) != 'e'){
                if (c < '0' || c > '9'){
                    throw new RuntimeException("read Integer failed.got " + c );
                }
                value = value * 10 + c - '0';
            }
            entity = new IntegerEntity(value);
        } else if (c == 'l'){
            List<Entity> list = new LinkedList<>();
            Entity temp;
            while((temp = decoding(input, index)) != null){
                list.add(temp);
            }
            entity = new ListEntity(list);
        } else if (c == 'd'){
            Map<Entity, Entity> map = new LinkedHashMap<>();
            Entity key;
            Entity value;
            while ((key = decoding(input, index)) != null){
                value = decoding(input, index);
                map.put(key, value);
            }
            entity = new MapEntity(map);
        } else if (c >'0' && c<= '9'){
            int length = c - '0';
            while((c = (char)input[index.getAndIncrement()]) != ':'){
                if (c < '0' || c > '9'){
                    throw new RuntimeException("read length of String failed.got " + c);
                }
                length = length * 10 + c - '0';
            }
            byte[] bytes = Arrays.copyOfRange(input, index.get(), index.addAndGet(length));
            entity = new ByteArrayEntity(bytes);
        } else {
            throw new RuntimeException("find start with "+ c + " which is illegal");
        }
        entity.setStart(start);
        entity.setEnd(index.get());
        return entity;
    }
}
