package ro.ubbcluj.map.domain.ui.operatii;

import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.ui.Comenzi;
import ro.ubbcluj.map.domain.ui.Operatie;
import ro.ubbcluj.map.domain.validators.ValidationException;
import ro.ubbcluj.map.service.Service;

public class PrieteniLunaOperation  extends Operatie {
    public PrieteniLunaOperation(String[] args, Service service){
        super(Comenzi.prieteni_luna,args,service);
    }

    @Override
    public void executeOneOperation() {
        if(args.length!=2){
            System.out.println("numar parametri invalid!");
        }
        else {
            try{
                Long id=Long.parseLong(args[0]);
                Long luna=Long.parseLong(args[1]);
                if(service.utilizatorPrieteniiLuna(id, luna).isEmpty())
                    System.out.println("Utilizatorul dat nu are prieteni in luna data");
                else{
                    Utilizator u=service.obtineUtilizator(id).get();
                    System.out.println("Utilizatorul "+ u.getFirstName() + " " + u.getLastName()+" este prieten cu:");
                    service.utilizatorPrieteniiLuna(id,luna).forEach(x->{
                        System.out.println(x.getLeft().getFirstName()+" " +x.getLeft().getLastName()+" din "+x.getRight().toString());
                            }
                    );
                }
            }
            catch (IllegalArgumentException | ValidationException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
