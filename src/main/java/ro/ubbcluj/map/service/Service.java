package ro.ubbcluj.map.service;

import com.jogamp.common.util.Bitfield;
import ro.ubbcluj.map.domain.Prietenie;
import ro.ubbcluj.map.domain.Tuple;
import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.validators.ValidationException;
import ro.ubbcluj.map.graph.Graph;
import ro.ubbcluj.map.repository.InMemoryRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private InMemoryRepository<Long, Utilizator> repou;
    private InMemoryRepository<Tuple<Long,Long>, Prietenie>repop;
    public Service(InMemoryRepository<Long, Utilizator> repou, InMemoryRepository<Tuple<Long, Long>, Prietenie> repop) {
        this.repou = repou;
        this.repop = repop;
    }

    public void adaugaUtilizator(Long id, String nume, String prenume){
        Utilizator utilizator = new Utilizator(nume,prenume);
        utilizator.setId(id);
        repou.save(utilizator);
    }
    public void stergeUtilizator(Long id){
        List<Long> idprieteni= new ArrayList<>();
        for (Utilizator utilizator:repou.findOne(id).get().getFriends()){
            idprieteni.add(utilizator.getId());
        }
        for(Long x : idprieteni)
            this.stergePrietenie(id,x);
        repou.delete(id);
    }

    public void adaugaPrietenie(Long id1,Long id2){
        repou.findOne(id1);repou.findOne(id2);
        repop.save(new Prietenie(id1,id2, LocalDateTime.now()));
        repop.save(new Prietenie(id2,id1,LocalDateTime.now()));
        if(repou.findOne(id1).isPresent()&&repou.findOne(id2).isPresent()) {
            repou.findOne(id1).get().getFriends().add(repou.findOne(id2).get());
            repou.findOne(id2).get().getFriends().add(repou.findOne(id1).get());
        }
    }

    public void stergePrietenie(Long id1,Long id2){
        repop.delete(new Tuple<>(id1, id2));
        repop.delete(new Tuple<>(id2, id1));
        if(repou.findOne(id1).isPresent())
            repou.findOne(id1).get().getFriends().removeIf(x->x.getId().equals(id2));
        if(repou.findOne(id2).isPresent())
            repou.findOne(id2).get().getFriends().removeIf(x->x.getId().equals(id1));
    }
    public Iterable<Utilizator> obtineUtilizator(){
        return repou.findAll();
    }
    public List<Tuple<Long,Long>> obtineLegaturi(){
        List<Tuple<Long,Long>> leg=new ArrayList<>();
        for(Prietenie p: repop.findAll()){
            leg.add(p.getId());
        }
        return leg;
    }
    public int nrComunitati(){
        Graph graph=new Graph(obtineLegaturi());
        return graph.numarComponenteConexe();
    }
    public List<Utilizator> ceaMaiSociabila(){
        Graph graph=new Graph(obtineLegaturi());
        List<Utilizator> users= new ArrayList<>();
        for(Long id: graph.drumLungMax())
            if (repou.findOne(id).isPresent())
                users.add(repou.findOne(id).get());
        return users;
    }
}
