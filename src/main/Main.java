package main;

import function.Evaluator;
import function.ParserException;


public class Main /*extends Application*/{
//    @Override
//    public void start(Stage stage) throws Exception {
//
//    }
    public static void main(String[] args) throws ParserException{
        System.out.println(Evaluator.evaluate("sum()+sum(5*6,5+7,average(5,4))"));
//        launch(args);
    }

}
