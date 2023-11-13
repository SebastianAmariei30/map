package ro.ubbcluj.map.domain.ui.operatii;

import ro.ubbcluj.map.domain.ui.Comenzi;
import ro.ubbcluj.map.domain.ui.Operatie;
import ro.ubbcluj.map.service.Service;

public class NumarComunitatiOperation extends Operatie {
    public NumarComunitatiOperation(String[] args, Service service){
        super(Comenzi.nr_comunitati,args,service);
    }

    @Override
    public void executeOneOperation() {
        if(args.length!=0){
            System.out.println("numar parametri invalid!");
        }
        else
            System.out.println("In total sunt: "+service.nrComunitati()+" comunitati");
    }
}
