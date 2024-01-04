package io;
import verwaltungsImp.verkaufsAutomat;

import java.io.*;

public class Serializer {

    public static void serializer(String fileName, verkaufsAutomat vk){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
            oos.writeObject(vk);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object deserialize(String fileName){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
            return ois.readObject();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


