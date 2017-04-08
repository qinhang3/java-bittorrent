package win.qinhang3.javabittorrent;

import win.qinhang3.javabittorrent.common.*;

import java.io.*;
import java.util.*;

/**
 * Created by hang on 17/4/7.
 */
public class BDecoding {

    //String  3:abc
    //Integer i-3e
    //List l4:spam4:eggse
    //{'spam': ['a', 'b']} d4:spaml1:a1:bee

    public static Entity decoding(InputStream inputStream) throws IOException {
        int i = inputStream.read();
        if (i == -1){
            return null;
        }
        Entity entity;
        if (i == 'e'){
            return null;
        }
        if (i == 'i'){
            int value = 0;
            while (( i = inputStream.read()) != 'e'){
                if (i < '0' || i > '9'){
                    throw new RuntimeException("read Integer failed.got " + (char)i);
                }
                value = value * 10 + i - '0';
            }
            entity = new IntegerEntity(value);
        } else if (i == 'l'){
            List<Entity> list = new LinkedList<>();
            Entity temp;
            while((temp = decoding(inputStream)) != null){
                list.add(temp);
            }
            entity = new ListEntity(list);
        } else if (i == 'd'){
            Map<Entity, Entity> map = new LinkedHashMap<>();
            Entity key;
            Entity value;
            while ((key = decoding(inputStream)) != null){
                value = decoding(inputStream);
                map.put(key, value);
            }
            entity = new MapEntity(map);
        } else if (i >'0' && i <= '9'){
            i = i - '0';
            int length = i;
            while((i = inputStream.read()) != ':'){
                if (i < '0' || i > '9'){
                    throw new RuntimeException("read length of String failed.got " + (char)i);
                }
                length = length * 10 + i - '0';
            }
            byte[] bytes = new byte[length];
            i = inputStream.read(bytes);
            if (i != length){
                throw new RuntimeException("require string length = " + length + " but got " + i);
            }
//            StringBuilder stringBuilder = new StringBuilder();
//            for (byte aByte : bytes) {
//                stringBuilder.append((char)(aByte >> 4)).append((char)(aByte & ((1 << 4) - 1)));
//            }
            entity = new StringEntity(bytes);
        } else {
            throw new RuntimeException("find start with "+ (char) i + " which is illegal");
        }
//        System.out.println("find " + entity);
        return entity;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/hang/Downloads/[Thz.LA][Thz.la]cnd-190.torrent");
        Entity entity = decoding(new FileInputStream(file));
        System.out.println(entity);
    }
}
