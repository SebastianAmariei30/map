package ro.ubbcluj.map.domain.ui.operatii;

import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.ui.Comenzi;
import ro.ubbcluj.map.domain.ui.Operatie;
import ro.ubbcluj.map.service.Service;

import java.util.ArrayList;
import java.util.List;

public class CeaMaiSociabilaOperation extends Operatie {
    public CeaMaiSociabilaOperation(String[] args, Service service){
        super(Comenzi.comunitate_sociabila,args,service);
    }

    @Override
    public void executeOneOperation() {
        System.out.println("Cea mai activa comunitate este formata din:");
        for(Utilizator utilizator: service.ceaMaiSociabila())
            System.out.println(utilizator);
    }
}
