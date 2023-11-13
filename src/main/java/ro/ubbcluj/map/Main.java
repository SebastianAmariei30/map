package ro.ubbcluj.map;

import com.jogamp.common.util.Bitfield;
import ro.ubbcluj.map.domain.Prietenie;
import ro.ubbcluj.map.domain.Tuple;
import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.ui.UI;
import ro.ubbcluj.map.domain.validators.PrietenieValidator;
import ro.ubbcluj.map.domain.validators.UtilizatorValidator;
//import ro.ubbcluj.map.repository.AbstractFileRepository;
import ro.ubbcluj.map.repository.FriendshipsDPRepo;
import ro.ubbcluj.map.repository.InMemoryRepository;
//import ro.ubbcluj.map.repository.UtilizatorFileRepository;
import ro.ubbcluj.map.repository.UsersDBRepo;
import ro.ubbcluj.map.service.Service;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        //InMemoryRepository<Long, Utilizator> re1pou=new InMemoryRepository<>(new UtilizatorValidator());
        UsersDBRepo repou=new UsersDBRepo("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new UtilizatorValidator());
        FriendshipsDPRepo repop=new FriendshipsDPRepo("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new PrietenieValidator());
        //InMemoryRepository<Tuple<Long,Long>, Prietenie> repop=new InMemoryRepository<>(new PrietenieValidator());
        Service service=new Service(repou,repop);
        UI ui=new UI(service);
        ui.run();
    }
}
