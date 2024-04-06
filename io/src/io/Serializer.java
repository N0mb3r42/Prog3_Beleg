package io;
import verwaltungsImp.verkaufsAutomat;

import java.io.*;

public class Serializer {

    public Serializer(){}

    public void serializer(String fileName, verkaufsAutomat vk){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
            oos.writeObject(vk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object deserialize(String fileName){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


