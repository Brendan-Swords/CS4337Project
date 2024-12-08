package com.example.demo;

public class Main
{
    public static void main(String[] args)
    {
        Requests requests = new Requests();
        Session session = null;
        String userInput;
        boolean validInput = false;

        System.out.println("Welcome to Bookstore");
        System.out.println("Enter first letter of option to select");
        System.out.println("Select an option:");
        System.out.println("(c)reate an account");
        System.out.println("(s)ign In");
        System.out.println("(e)xit");


        while (!validInput)
        {
            userInput = System.console().readLine();
            switch (userInput)
            {
                case "c":
                    session = requests.createAccount();
                    validInput = true;
                    break;
                case "s":
                    session = requests.signIn();
                    validInput = true;
                    break;
                case "e":
                    return;
                default:
                    break;
            }
        }

        if (session.publisher)
        {
            while (true)
            {
                System.out.println("Enter first letter of option to select");
                System.out.println("Select an option:");

                System.out.println("(p)ublish a book");
                System.out.println("(e)xit");
                userInput = System.console().readLine();
                switch (userInput)
                {
                    case "p":
                        requests.publish(session);
                        break;
                    case "e":
                        return;
                }
            }
        }
        else
        {
            while (true)
            {
                System.out.println("Enter first letter of option to select");
                System.out.println("Select an option:");

                System.out.println("(c)reate an order");
                System.out.println("(d)elete an order");
                System.out.println("(e)xit");
                userInput = System.console().readLine();
                switch (userInput)
                {
                    case "c": requests.createOrder(session);
                    break;
                    case "d": requests.deleteOrder(session);
                    break;
                    case "e":
                        return;
                    default:
                        break;
                }
            }
        }
    }
}