package Simulation1;

import kuchen.Kuchen;
import kuchenImp.KuchenImp;
import kuchenImp.ObstkuchenImp;
import verwaltungsImp.verkaufsAutomat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.random.*;

public class DeleteKuchenThread extends Thread{
    public verkaufsAutomat automat;
    public DeleteKuchenThread(verkaufsAutomat imput){
        this.automat = imput;
    }
    @Override
    public void run() {
        while (true){
            synchronized (this.automat){
                List<KuchenImp> kuchen = new ArrayList<>(this.automat.readKuchen());
                Collections.shuffle(kuchen);
                if (kuchen.size() == 0){
                    continue;
                }
                boolean returnvalue = this.automat.delete(kuchen.get(0).getFachnummer());
                if (returnvalue){
                    System.out.println("Fachnummer: " + kuchen.get(0).getFachnummer() + "\t| Kuchen wurde erfolgreich gelöscht | from Thread: " + Thread.currentThread().threadId());
                }else{
                    System.out.println("Kuchen konnte nicht gelöscht werden | from Thread: " + Thread.currentThread().threadId());
                }
            }
        }
    }
}