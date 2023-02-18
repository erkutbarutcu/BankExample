package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void addToDB(){
        List<User> UserList=new ArrayList<User>();

        UserList.add(new User("Ahmet","1136",1111,5000,"1234"));
        UserList.add(new User("Cengiz","2225",2222,2500,"12345"));
        UserList.add(new User("Mehmet","1126",3333,3000,"12"));


        JSONArray UserListJSON = new JSONArray();

        UserList.forEach(var->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",var.getName());
            jsonObject.put("TC",var.getTC());
            jsonObject.put("AccountID",var.getAccountID());
            jsonObject.put("Password",var.getPassword());
            jsonObject.put("AccountValue",var.getAccountValue());
            UserListJSON.add(jsonObject);
        });

        try {
            FileWriter file = new FileWriter("database.json");
            file.write(UserListJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<User> ReadDB() {

        FileReader fileReader;
        JsonParser jsonParser=new JsonParser();
        try {
            fileReader = new FileReader("database.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();


        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
        ArrayList<User> userArray = gson.fromJson(jsonParser.parse(fileReader), userListType);

        return userArray;
    }

    public static void WriteDB(List<User> users){

        JSONArray UserListJSON = new JSONArray();


        users.forEach(var->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",var.getName());
            jsonObject.put("TC",var.getTC());
            jsonObject.put("AccountID",var.getAccountID());
            jsonObject.put("Password",var.getPassword());
            jsonObject.put("AccountValue",var.getAccountValue());
            UserListJSON.add(jsonObject);
        });

        try {
            FileWriter file = new FileWriter("database.json");
            file.write(UserListJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args){
        boolean isLogin=false;
        User theUser = null;

        List<User> users= (List<User>) ReadDB();
        Scanner input=new Scanner(System.in);

        System.out.print("Welcome,");
        while(!isLogin){
            theUser=Login(users,input);
            isLogin=theUser!=null;
        }
        System.out.println("You've successfully logged.!");


        int section=0;
        while(section!=-1) {
            ClearScreen();
            MainMenu();
            section = input.nextInt();
            ClearScreen();
            switch (section) {
                case 1:
                    WriteDB(TransferMenu(theUser,users,input));
                    break;
                case 2:
                    CheckAccount(theUser);
                    input.nextInt();
                    break;
                case -1:
                    theUser=null;
                    isLogin=false;
                    break;
                default:
                    System.out.println("Try Again Please...");
            }
        }
        System.out.println("You've logged out.");
        System.out.println("Have a good day.");



    }

    public static User Login(List<User> users, Scanner input){
        boolean approvedTC=false;
        String  TC;
        String password;
        User freeUser=new User();


        System.out.println("Please Login!");
        System.out.println("TC");
        TC=input.next();

        for (User var:users) {
            if(TC.equals(var.getTC())){
                approvedTC=true;
                freeUser.setName(var.getName());
                freeUser.setTC(var.getTC());
                freeUser.setAccountID(var.getAccountID());
                freeUser.setPassword(var.getPassword());
                freeUser.setAccountValue(var.getAccountValue());
                break;
            }
        }

        if(approvedTC){
            System.out.println("Password");
            password=input.next();

            return freeUser.getPassword().equals(password)?freeUser:null;
        }
        return null;
    }

    public static void MainMenu(){

        System.out.println("----------------------------");
        System.out.println("|  Please Choose Section.! |");
        System.out.println("----------------------------");
        System.out.println("|  1==== Money Transfer    |");
        System.out.println("----------------------------");
        System.out.println("|  2==== Check Account     |");
        System.out.println("----------------------------");
        System.out.println("|  -1==== Log Out          |");
        System.out.println("----------------------------");

    }

    public static List<User> TransferMenu(User theUser, List<User> users,Scanner input){

        System.out.println("----------------------------");
        System.out.println("|      Account Info        |");
        System.out.println("----------------------------");
        System.out.println("| "+theUser.getAccountValue()+"   |");
        System.out.println("----------------------------");
        System.out.println("|  Press 1  For Transfer   |");
        System.out.println("----------------------------");
        System.out.println("|  Press -1  For Menu      |");
        System.out.println("----------------------------");

        int transferCheck;
        int SecondCheck;
        int Accountid;
        int ValueOfTransfer;
        boolean ActionDone = false;
        transferCheck=input.nextInt();

        if(transferCheck==-1)
        {
            return users;
        }
        if( transferCheck==1) {
            ClearScreen();
            System.out.println("----------------------------");
            System.out.println("| Target Account Id        |");
            System.out.println("----------------------------");

            Accountid = input.nextInt();

            ClearScreen();
            System.out.println("----------------------------");
            System.out.println("| Money You Want Transfer  |");
            System.out.println("----------------------------");

            ValueOfTransfer = input.nextInt();

            ClearScreen();
            System.out.println("----------------------------");
            System.out.println("| To Continue Press 1  |");
            System.out.println("----------------------------");

            SecondCheck = input.nextInt();
            ClearScreen();
            if (SecondCheck == 1) {

                for (User var : users) {
                    if (Accountid == var.getAccountID()) {
                        var.setAccountValue(var.getAccountValue() + ValueOfTransfer);
                        users.stream()
                                .filter(variable -> theUser.getAccountID() == variable.getAccountID())
                                .findAny().orElseThrow()
                                .setAccountValue(theUser.getAccountValue() - ValueOfTransfer);
                        theUser.setAccountValue(theUser.getAccountValue() - ValueOfTransfer);
                        ActionDone = true;
                        break;
                    }
                }
            }
        }
        if(ActionDone){
            System.out.println("----------------------------------");
            System.out.println("|Transfer Successfully Completed |");
            System.out.println("----------------------------------");
            return users;
        }

        return users;
    }

    public static void CheckAccount(User theUser){

        System.out.println("----------------------------");
        System.out.println("|   Account Info           |");
        System.out.println("----------------------------");
        System.out.println("|  "+theUser.getName()+"    |");
        System.out.println("----------------------------");
        System.out.println("|  "+theUser.getAccountID()+"   |");
        System.out.println("----------------------------");
        System.out.println("|   Account Balance        |");
        System.out.println("----------------------------");
        System.out.println("|  "+theUser.getAccountValue()+"         |");
        System.out.println("----------------------------");
        System.out.println("|    Press Any For Menu    |");
        System.out.println("----------------------------");
    }


    public static void ClearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}