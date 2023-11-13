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
        if(args.length!=2){
            System.out.println("numar parametri invalid!");
        }
        else{
            String nume=args[0];
            String prenume=args[1];
            try{
                service.adaugaUtilizator(nume,prenume);
                System.out.println("Utilizator adaugat cu succes!");
            }
            catch (IllegalArgumentException | ValidationException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
