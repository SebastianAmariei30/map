package ro.ubbcluj.map.service;

import com.jogamp.common.util.Bitfield;
import ro.ubbcluj.map.domain.Prietenie;
import ro.ubbcluj.map.domain.Tuple;
import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.validators.ValidationException;
import ro.ubbcluj.map.graph.Graph;
import ro.ubbcluj.map.repository.InMemoryRepository;
import ro.ubbcluj.map.repository.Repository;
import ro.ubbcluj.map.repository.UsersDBRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Service {
    private Repository<Long,Utilizator> repou;
    private Repository<Tuple<Long,Long>, Prietenie>repop;
    public Service(Repository<Long,Utilizator> repou,Repository<Tuple<Long,Long>, Prietenie>repop) {
        this.repou = repou;
        this.repop = repop;
    }

    public void adaugaUtilizator(String nume, String prenume){
        Utilizator utilizator = new Utilizator(nume,prenume);
        repou.save(utilizator);
    }
    public void stergeUtilizator(Long id){
        //List<Long> idprieteni= new ArrayList<>();
//        for (Utilizator utilizator:repou.findOne(id).get().getFriends()){
//            idprieteni.add(utilizator.getId());
//        }
        List<Long> l=new ArrayList<>();
        repop.findAll().forEach(y->{
            if(Objects.equals(y.getId().getRight(),id))
                l.add(y.getId().getLeft());
            if(Objects.equals(y.getId().getLeft(),id))
                l.add(y.getId().getRight());
        });
        l.forEach(x->this.stergePrietenie(id,x));
//        for(Long x : idprieteni)
//            this.stergePrietenie(id,x);
        repou.delete(id);
    }

    public void adaugaPrietenie(Long id1,Long id2,LocalDateTime l){
        repou.findOne(id1);repou.findOne(id2);
        repop.save(new Prietenie(id1,id2, l));
//        if(repou.findOne(id1).isPresent()&&repou.findOne(id2).isPresent()) {
//            repou.findOne(id1).get().getFriends().add(repou.findOne(id2).get());
//            repou.findOne(id2).get().getFriends().add(repou.findOne(id1).get());
//        }
    }
    public void stergePrietenie(Long id1,Long id2){
        if(repop.findOne(new Tuple<>(id1,id2)).isPresent())
            repop.delete(new Tuple<>(id1, id2));
        if(repop.findOne(new Tuple<>(id2,id1)).isPresent())
            repop.delete(new Tuple<>(id2, id1));
//        if(repou.findOne(id1).isPresent())
//            repou.findOne(id1).get().getFriends().removeIf(x->x.getId().equals(id2));
//        if(repou.findOne(id2).isPresent())
//            repou.findOne(id2).get().getFriends().removeIf(x->x.getId().equals(id1));
    }
    public Iterable<Prietenie> obtinePrietenii(){
        return repop.findAll();
    }
    public Optional<Prietenie> obtinePrietenie(Tuple<Long,Long> p){return  repop.findOne(p);}
    public Optional<Utilizator> obtineUtilizator(Long id){
        return repou.findOne(id);
    }
    public List<Tuple<Utilizator,LocalDateTime>> utilizatorPrieteniiLuna(Long id,Long luna){
        repou.findOne(id);
        List<Tuple<Utilizator,LocalDateTime>> f=new ArrayList<>();
        List<Prietenie> l= new ArrayList<>();
        repop.findAll().forEach(l::add);
        l.stream().filter(x->x.getDate().getMonth().getValue()==luna)
                .filter(x->x.getId().getLeft().equals(id)||x.getId().getRight().equals(id))
                .forEach(x->{
                    if(Objects.equals(x.getId().getRight(), id))
                        f.add(new Tuple<>(repou.findOne(x.getId().getLeft()).get(),x.getDate()));
                    if(Objects.equals(x.getId().getLeft(), id))
                        f.add(new Tuple<>(repou.findOne(x.getId().getRight()).get(),x.getDate()));
                });
        return f;
    }
    public Iterable<Utilizator> obtineUtilizatori(){
        Iterable<Utilizator> utilizators= repou.findAll();
        utilizators.forEach(x->{
            List<Utilizator> l=new ArrayList<>();
            repop.findAll().forEach(y->{
                if(Objects.equals(y.getId().getRight(), x.getId()))
                    l.add(repou.findOne(y.getId().getLeft()).get());
                if(Objects.equals(y.getId().getLeft(),x.getId()))
                    l.add(repou.findOne(y.getId().getRight()).get());
            });
            x.setFriends(l);
        });
        return utilizators;
    }
    public List<Tuple<Long,Long>> obtineLegaturi(){
        List<Tuple<Long,Long>> leg=new ArrayList<>();
        repop.findAll().forEach(p-> {
            leg.add(p.getId());
            Tuple<Long,Long> t=new Tuple<>(p.getId().getRight(),p.getId().getLeft());
            leg.add(t);
        });
//        for(Prietenie p: repop.findAll()){
//            leg.add(p.getId());
//        }
        return leg;
    }
    public int nrComunitati(){
        Graph graph=new Graph(obtineLegaturi());
        return graph.numarComponenteConexe();
    }
    public List<Utilizator> ceaMaiSociabila(){
        Graph graph=new Graph(obtineLegaturi());
        List<Utilizator> users= new ArrayList<>();
        if(graph.drumLungMax()!=null) {
            graph.drumLungMax().stream()
                    .filter(id -> repou.findOne(id).isPresent())
                    .forEach(id ->users.add(repou.findOne(id).get()));
        }
//        for(Long id: graph.drumLungMax())
//            if (repou.findOne(id).isPresent())
//                users.add(repou.findOne(id).get());
        return users;
    }
}
