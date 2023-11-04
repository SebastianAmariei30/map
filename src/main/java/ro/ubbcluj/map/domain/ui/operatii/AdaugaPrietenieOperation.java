package ro.ubbcluj.map.domain.ui.operatii;

import ro.ubbcluj.map.domain.ui.Comenzi;
import ro.ubbcluj.map.domain.ui.Operatie;
import ro.ubbcluj.map.domain.validators.ValidationException;
import ro.ubbcluj.map.service.Service;

public class AdaugaPrietenieOperation extends Operatie {
    public AdaugaPrietenieOperation(String[] args, Service service){
        super(Comenzi.adauga_prietenie,args,service);
    }

    @Override
    public void executeOneOperation() {
        String id1=args[0];
        String id2=args[1];
        try{
            service.adaugaPrietenie(Long.parseLong(id1),Long.parseLong(id2));
            System.out.println("Prietenie adaugata cu succes!");
        }
        catch(IllegalArgumentException | ValidationException e){
            System.out.println(e.getMessage());
        }
    }
}
