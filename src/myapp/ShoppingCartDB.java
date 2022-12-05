package myapp;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;






public class ShoppingCartDB {

    public static final String login = "login";
    public static final String add = "add";
    public static final String list = "list";
    public static final String save = "save";
    public static final String exit = "exit";
    public static final String users = "users";

    public static final List <String> VALID_COMMANDS = Arrays.asList 
    (login, add, list, save, exit, users);

    private CartDBinMemory db;
    private String currentUser;
    private String baseFolder;

    //constructors
    public ShoppingCartDB() {
        this.baseFolder = "db"; // default
        this.setup();
        this.db = new CartDBinMemory(this.baseFolder); //initialising the data structure 
        // db is private in this class hence we stated as 'this.db'
        
    }

    public ShoppingCartDB(String baseFolder) {
        this.baseFolder = baseFolder;
        this.setup();
        this.db = new CartDBinMemory(this.baseFolder);
        
    }
    //setup to check if path exists, if not, create new directory
    public void setup(){
        Path p = Paths.get(this.baseFolder); // turning a String into a path
        if (Files.isDirectory(p)){ //if path is a directory
            //SKIP if directory already exists
        } else {
            try{
                Files.createDirectory(p); //creates a new directory
            } catch (IOException e){ //failed or interupted input/output operations
                System.out.println("Error: " +e.getMessage()); // returns error message
            }
        }
    }

    public void startShell() {
        System.out.println("Welcome to MultiUser Shopping Cart >> ");

        Scanner sc = new Scanner(System.in); //New scanner; to capture inputs in console; similar to Console cons = System.console;
        //sc.nextLine();//current line; till the end
        //sc.next(); //give first word before white space
        //String command = sc.next();
        //String input = sc.nextLine();
        String line;
        boolean stop = false;

        //while ((line = sc.nextLine()) != null){
            while (!stop){
                line = sc.nextLine(); //prints out entire line
                line = line.trim();
                System.out.println("=> " + line);
            
            //System.out.println(line);
            if (line.equalsIgnoreCase("exits")){//if input = 'exits', ignoring upper and lowercase
                System.out.println("Exiting");
                stop = true; //exit while loop
        
            }
            //Validate command
            if (!this.validateInput(line)) { //validate if user input is not part of VALID_COMMANDS List <String>
                System.out.println("Invalid Input: ^^");
            } else {
                System.out.println("Processing : " + line);
                this.ProcessInput(line);
            }
            }    
            sc.close();    //closes this scanner, right before closing this startShell() method
        }
        
        public boolean validateInput (String input){
            String[] parts = input.split(" ");
            String command = parts[0].trim();
            //Another way
            /* Scanner lsc = new.Scanner(input);
             * String command = lsc.next().trim()
             */
            return VALID_COMMANDS.contains(command);
        }

        //process command
        public void ProcessInput (String input) {
            Scanner sc = new Scanner(input); //New scanner
            String command = sc.next().trim();//to process inputs in VALID_COMMANDS List<String>
        
            switch(command) {//Using Switch statement to depict scenarios

                case login:
                    String username = sc.nextLine().trim();
                    this.LoginAction(username);
                    System.out.println("Current user logged in: " + this.currentUser);
                    break;

                case add:
                    String[] items = sc.nextLine().trim().split(","); // splitting apple,orange into String[0] and [1]
                    this.AddAction(items);
                    break;

                case list:
                    this.ListAction();//calls for ListAction() method below
                    break;

                case save:
                this.SaveAction();//calls for SaveAction() method below;
                    break;

                case exit:
                    break;

                case users:
                this.UsersAction();
                    break;

                default:
                    break;
            }
            
            sc.close(); //closes scanner, close after switch statement but before end of ProcessInput method
        
        }
        public void LoginAction (String username){
            if (!this.db.userMap.containsKey(username)){//if the database does not contain this username
                //user already exit
                this.db.userMap.put(username, new ArrayList<String>());//to add user into database with empty List
            }
            this.currentUser = username;//currenUser in this class references username String
        }

        public void AddAction(String[] items) {
                for (String item : items) {// loop over all item in String[] items
                    this.db.userMap.get(this.currentUser).add(item.trim());//add item(s) to currentUser
                }
        }

        public void ListAction() {
            for (String item : this.db.userMap.get(this.currentUser)) //loop over all item in HashMap database associated to currentUser
              System.out.println("Item => " + item); //And print
            }

        public void SaveAction() {
            //Prepare the filePath = "db/<username>.db"
            String outputFileName = String.format("%s/%s.db",
            this.baseFolder, this.currentUser);

            try {
                FileWriter fw = new FileWriter((outputFileName));
               //Save the contents for this user in Map to a file.
               for (String item : this.db.userMap.get(this.currentUser)){ //currentUser is the key or also known as the index
                fw.write(item +"\n");
               }
               fw.flush(); //save before closing
               fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            }
            public void UsersAction() {
                Set <String> keys = this.db.userMap.keySet();
                for (String key : keys) {
                    System.out.println(key);
                }
            }

        }
            //Command: login <username>
            //Login function

            //Command: add <item1>, <item2>
            //Add items function: Push items for the current user

            //Command: list
            //list items function: Show the items added for the current user

            //Command: users
            //list all the users in the system

            //Command: save
            //Save function: Dump contents of the current user to a file base_folder/username.db

        



            
          
   

    

