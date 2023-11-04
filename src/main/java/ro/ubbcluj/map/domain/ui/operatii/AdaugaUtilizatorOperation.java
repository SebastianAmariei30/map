package ro.ubbcluj.map.domain.ui.operatii;

import ro.ubbcluj.map.domain.ui.Comenzi;
import ro.ubbcluj.map.domain.ui.Operatie;
import ro.ubbcluj.map.domain.validators.ValidationException;
import ro.ubbcluj.map.service.Service;

public class AdaugaUtilizatorOperation extends Operatie {
    public AdaugaUtilizatorOperation(String[] args, Service service){
        super(Comenzi.adauga_utilizator,args,service);
    }
    @Override
    public void executeOneOperation() {
        String id=args[0];
        String nume=args[1];
        String prenume=args[2];
        try{
            service.adaugaUtilizator(Long.parseLong(id),nume,prenume);
            System.out.println("Utilizator adaugat cu succes!");
        }
        catch (IllegalArgumentException | ValidationException e){
            System.out.println(e.getMessage());
        }
    }
}
