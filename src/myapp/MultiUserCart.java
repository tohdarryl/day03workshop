package myapp;

public class MultiUserCart {

public static void main(String[] args) {
    System.out.println("Welcome to your shopping cart");

    ShoppingCartDB cart = new ShoppingCartDB("cartdb");// default folder: "db"
    cart.setup();
    cart.startShell();






}


}