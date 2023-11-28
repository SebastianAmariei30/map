package ro.ubbcluj.map.demogui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.validators.MessageValidator;
import ro.ubbcluj.map.demogui.domain.validators.PrietenieValidator;
import ro.ubbcluj.map.demogui.domain.validators.UtilizatorValidator;
import ro.ubbcluj.map.demogui.repository.FriendshipsDPRepo;
import ro.ubbcluj.map.demogui.repository.MessagesDPRepo;
import ro.ubbcluj.map.demogui.repository.UsersDBRepo;
import ro.ubbcluj.map.demogui.service.Service;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-view.fxml"));
        UsersDBRepo repou=new UsersDBRepo("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new UtilizatorValidator());
        FriendshipsDPRepo repop=new FriendshipsDPRepo("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new PrietenieValidator());
        MessagesDPRepo repom=new MessagesDPRepo("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new MessageValidator());
        Service service=new Service(repou,repop, repom);
        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));
        UserController messageTaskController = fxmlLoader.getController();
        messageTaskController.setService(service);
        stage.show();
    }

    public static void main(String[] args) {
//        //InMemoryRepository<Long, Utilizator> re1pou=new InMemoryRepository<>(new UtilizatorValidator());
//        UsersDBRepo repou=new UsersDBRepo("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new UtilizatorValidator());
//        FriendshipsDPRepo repop=new FriendshipsDPRepo("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new PrietenieValidator());
//        //InMemoryRepository<Tuple<Long,Long>, Prietenie> repop=new InMemoryRepository<>(new PrietenieValidator());
//        Service service=new Service(repou,repop);
//        UI ui=new UI(service);
//        ui.run();
        launch();
    }
}