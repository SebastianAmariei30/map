package ro.ubbcluj.map.domain.ui.operatii;

import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.ui.Comenzi;
import ro.ubbcluj.map.domain.ui.Operatie;
import ro.ubbcluj.map.service.Service;

import java.time.LocalDateTime;

public class AfiseazaPrieteniiOperation extends Operatie {
    public AfiseazaPrieteniiOperation(String[] args, Service service){
        super(Comenzi.afiseaza_prietenii,args,service);
    }

    @Override
    public void executeOneOperation() {
        if(args.length!=0){
            System.out.println("numar parametri invalid!");
        }
        else{
            service.obtinePrietenii().forEach(x->{
                Utilizator u1=service.obtineUtilizator(x.getId().getLeft()).get();
                Utilizator u2=service.obtineUtilizator(x.getId().getRight()).get();
                LocalDateTime l=x.getDate();
                System.out.println("Utilizatorul "+u1.getFirstName()+" "+u1.getLastName()+" este prieten cu utilzatorul "+
                        u2.getFirstName()+" "+u2.getLastName()+" de la data si ora "+l.toString());
            });
        }
    }
}
