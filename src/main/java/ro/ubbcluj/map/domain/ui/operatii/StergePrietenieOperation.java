package ro.ubbcluj.map.domain.ui.operatii;

import ro.ubbcluj.map.domain.ui.Comenzi;
import ro.ubbcluj.map.domain.ui.Operatie;
import ro.ubbcluj.map.service.Service;

public class StergePrietenieOperation extends Operatie {
    public StergePrietenieOperation(String[] args, Service service){
        super(Comenzi.sterge_prietenie,args,service);
    }

    @Override
    public void executeOneOperation() {
        String id1=args[0];
        String id2=args[1];
        try{
            service.stergePrietenie(Long.parseLong(id1),Long.parseLong(id2));
            System.out.println("Prietenie stearsa cu succes!");
        }
        catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }
}
