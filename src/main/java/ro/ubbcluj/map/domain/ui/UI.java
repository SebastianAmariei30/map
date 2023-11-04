package ro.ubbcluj.map.domain.ui;

import ro.ubbcluj.map.domain.validators.ValidationException;
import ro.ubbcluj.map.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UI {
    private Service service;

    private ExpressionParser expressionParser = new ExpressionParser();
    public UI(Service service) {
        this.service = service;
    }
    public void run()  {
        while(true){
            Scanner in= new Scanner(System.in);
            String s=in.nextLine();
            if(s.equals("exit"))
                break;
            Operatie op= expressionParser.parseExpression(s,service);
            if(op==null)
                System.out.println("Comanda invalida!");
            else
                op.execute();
        }
    }
}
