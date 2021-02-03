package ServerComponent;

import FileClass.FileClassDemo;
import java.net.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;

class TotalSum {

    Double first;
    Double second;

    public TotalSum(Double first, Double second) {
        this.first = first;
        this.second = second;
    }

}

public class EchoMultiServer extends Thread {

    protected Socket clientSocket;

    Hashtable<String, Double> tablet = new Hashtable<>();

    PrintWriter out;
    BufferedReader in;
    static ServerSocket serverSocket = null;

    public static void main(String[] args) throws IOException {

        try {
            System.out.println(InetAddress.getLocalHost());
            serverSocket = new ServerSocket(10008);
            System.out.println("Connection Socket Created");

            go();

        } catch (IOException e) {
            System.err.println("Could not listen on port: 10008.");

        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not close port: 10008.");

            }
        }
    }

    static void go() {
        try {
            while (true) {
                System.out.println("Waiting for Connection");
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress());
                new EchoMultiServer(socket);
            }
        } catch (IOException e) {
            System.err.println("Accept failed." + e);

        }
    }

    private EchoMultiServer(Socket clientSoc) {
        clientSocket = clientSoc;
        makedatatable();
        start();
    }

    public void loginControl(String string) {
        String[] a = string.split("#");
        FileClassDemo fcd = new FileClassDemo("AllFile/" + "Alluser.txt");
        fcd.appendwrite("");
        if (!fcd.read().contains(a[0])) {
            out.println("notlog");
        } else {
            out.println("log");
        }
    }

    public void registerControl(String string) {
        String[] a = string.split("#");
        FileClassDemo fcd = new FileClassDemo("AllFile/" + "Alluser.txt");
        fcd.appendwrite("");
        if (fcd.read().contains(a[0])) {
            out.println("notregister");
        } else {
            fcd.appendwrite(a[0] + "#");
            FileClassDemo fcd2 = new FileClassDemo(a[0] + "Transaction.txt");
//            FileClassDemo fcd3 = new FileClassDemo(a[0] + "Category.txt");
//            FileClassDemo fcd4 = new FileClassDemo(a[0] + "Monthwise.txt");
            try {
                fcd2.open();
//                fcd3.open();
//                fcd4.open();
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
            out.println("register");
        }
    }

    public void run() {
        System.out.println("New Communication Thread Started");

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Server: " + inputLine);
                if (inputLine.contains("login")) {
                    loginControl(inputLine);
                } else if (inputLine.contains("register")) {
                    registerControl(inputLine);
                } else if (inputLine.contains("budget")) {
                    inputLine = inputLine.replace("budget#", "");
                    String[] d = inputLine.replace("\n", "").split("#");
                    String clientfileType = d[0].replace("*", "_");
                    inputLine = inputLine.replace((d[0] + "#"), "");
                    FileClassDemo fd = new FileClassDemo("AllFile/" + clientfileType + "Transaction" + ".txt");
                    fd.appendwrite(inputLine);
                    System.out.println("Complete");
                } else if (inputLine.contains("history")) {
                    inputLine = inputLine.replace("#history", "");
                    String clientname = inputLine.replace("*", "_");
                    String filename = "AllFile/" + clientname + "Transaction.txt";
                    FileClassDemo fd = new FileClassDemo(filename);
                    String s = fd.read();
                    s = s.replace("\n", "");
                    out.println(s);
                } else if (inputLine.contains("pie&")) {
                    inputLine = inputLine.replace("#pie", "");
                    String[] first = inputLine.split("&");
                    String clientname = first[0].replace("*", "_");
                    String filename = "AllFile/" + clientname + "Transaction.txt";
                    FileClassDemo fd = new FileClassDemo(filename);
                    String s = fd.read();
                    //s = s.replace("\n", "");
                    String g = makedatatable(s, first[1]);

                    out.println(g);
                    System.out.println(g);

                } else if (inputLine.contains("pie")) {
                    inputLine = inputLine.replace("#pie", "");
                    String clientname = inputLine.replace("*", "_");
                    String filename = "AllFile/" + clientname + "Transaction.txt";
                    FileClassDemo fd = new FileClassDemo(filename);
                    String s = fd.read();
                    //s = s.replace("\n", "");
                    String g = makedatatable(s);

                    out.println(g);
                    System.out.println(g);

                } else if (inputLine.contains("logout")) {
                    go();
                } else if (inputLine.contains("updown&")) {
                    inputLine = inputLine.replace("updown&", "");
                    String[] split1 = inputLine.split("#");
                    String year = split1[1];
                    String clientname = split1[0].replace("*", "_");
                    String filename = "AllFile/" + clientname + "Transaction.txt";
                    FileClassDemo fd = new FileClassDemo(filename);
                    String s = fd.read();
                    System.out.println(s);
                    String m = makeUpDown(s, year);
                    out.println(m);
                    System.out.println(m);
                } else if (inputLine.contains("comparison&")) {
                    inputLine = inputLine.replace("comparison&", "");
                    String[] firStrings = inputLine.split("#");
                    String clientname = firStrings[0].replace("*", "_");
                    String search = firStrings[1];
                    String goString = makeCompare(clientname, search);
                    out.println(goString);
                    System.out.println(goString);

                }
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            go();
        }
    }

    private String makeCompare(String client, String search) {

        client = client + "Transaction.txt";

        Hashtable<String, TotalSum> tableCompare = new Hashtable<>();
        tableCompare.put("JAN", new TotalSum(new Double(0), new Double(0)));
        tableCompare.put("FEB", new TotalSum(new Double(0), new Double(0)));
        tableCompare.put("MAR", new TotalSum(new Double(0), new Double(0)));
        tableCompare.put("APR", new TotalSum(new Double(0), new Double(0)));
        tableCompare.put("MAY", new TotalSum(new Double(0), new Double(0)));
        tableCompare.put("JUN", new TotalSum(new Double(0), new Double(0)));
        tableCompare.put("JUL", new TotalSum(new Double(0), new Double(0)));
        tableCompare.put("AUG", new TotalSum(new Double(0), new Double(0)));
        tableCompare.put("SEP", new TotalSum(new Double(0), new Double(0)));
        tableCompare.put("OCT", new TotalSum(new Double(0), new Double(0)));
        tableCompare.put("NOV", new TotalSum(new Double(0), new Double(0)));
        tableCompare.put("DEC", new TotalSum(new Double(0), new Double(0)));

        String k = "";
        FileClassDemo alluser = new FileClassDemo("AllFile/" + "Alluser.txt");
        String aluserid = alluser.read();
        aluserid = aluserid.replace("*", "_");
        String[] arrayuser = aluserid.split("#");
        for (String index : arrayuser) {
            String filename = "AllFile/" + index + "Transaction.txt";
            FileClassDemo fd = new FileClassDemo(filename);
            String s = fd.read();
            String[] arrray = s.split("#");
            for (String kk : arrray) {
                if (kk.contains(search)) {
                    String g = kk.replace("*", " ");
                    String array2[] = g.split(" ");
                    TotalSum tt = tableCompare.get(array2[1]);
                    if (filename.contains(client)) {
                        tt.first += Double.parseDouble(array2[4]);
                    } else {
                        tt.second += Double.parseDouble(array2[4]);
                    }
                }
            }
        }
        
        Enumeration<String> names = tableCompare.keys();
        
        String sendString="JAN "+tableCompare.get("JAN").first+" "+tableCompare.get("JAN").second+"#";
        sendString+="FEB "+tableCompare.get("FEB").first+" "+tableCompare.get("FEB").second+"#";
        sendString+="MAR "+tableCompare.get("MAR").first+" "+tableCompare.get("MAR").second+"#";
        sendString+="APR "+tableCompare.get("APR").first+" "+tableCompare.get("APR").second+"#";
        sendString+="MAY "+tableCompare.get("MAY").first+" "+tableCompare.get("MAY").second+"#";
        sendString+="JUN "+tableCompare.get("JUN").first+" "+tableCompare.get("JUN").second+"#";
        sendString+="JUL "+tableCompare.get("JUL").first+" "+tableCompare.get("JUL").second+"#";
        sendString+="AUG "+tableCompare.get("AUG").first+" "+tableCompare.get("AUG").second+"#";
        sendString+="SEP "+tableCompare.get("SEP").first+" "+tableCompare.get("SEP").second+"#";
        sendString+="OCT "+tableCompare.get("OCT").first+" "+tableCompare.get("OCT").second+"#";
        sendString+="NOV "+tableCompare.get("NOV").first+" "+tableCompare.get("NOV").second+"#";
        sendString+="DEC "+tableCompare.get("DEC").first+" "+tableCompare.get("DEC").second+"#";

        while (names.hasMoreElements()) {

            String str = names.nextElement();
            TotalSum ttSum = tableCompare.get(str);
            k += str + " " + ttSum.first + " " + ttSum.second + "#";
        }
        out.println(arrayuser.length);
        //k+=arrayuser.length;
        return sendString;
    }

    private String makeUpDown(String s, String year) {
        String k = "";
        Hashtable<String, TotalSum> tableUpDown = new Hashtable<>();
        tableUpDown.put("JAN", new TotalSum(new Double(0), new Double(0)));
        tableUpDown.put("FEB", new TotalSum(new Double(0), new Double(0)));
        tableUpDown.put("MAR", new TotalSum(new Double(0), new Double(0)));
        tableUpDown.put("APR", new TotalSum(new Double(0), new Double(0)));
        tableUpDown.put("MAY", new TotalSum(new Double(0), new Double(0)));
        tableUpDown.put("JUN", new TotalSum(new Double(0), new Double(0)));
        tableUpDown.put("JUL", new TotalSum(new Double(0), new Double(0)));
        tableUpDown.put("AUG", new TotalSum(new Double(0), new Double(0)));
        tableUpDown.put("SEP", new TotalSum(new Double(0), new Double(0)));
        tableUpDown.put("OCT", new TotalSum(new Double(0), new Double(0)));
        tableUpDown.put("NOV", new TotalSum(new Double(0), new Double(0)));
        tableUpDown.put("DEC", new TotalSum(new Double(0), new Double(0)));

        String[] arrray = s.split("#");
        for (String kk : arrray) {
            if (kk.contains(year)) {
                String g = kk.replace("*", " ");
                String array2[] = g.split(" ");
                TotalSum tt = tableUpDown.get(array2[1]);
                if (array2[3].equals("Income")) {
                    tt.first += Double.parseDouble(array2[4]);
                } else {
                    tt.second += Double.parseDouble(array2[4]);
                }
            }
        }

        Enumeration<String> names = tableUpDown.keys();

        while (names.hasMoreElements()) {

            String str = names.nextElement();
            TotalSum ttSum = tableUpDown.get(str);
            k += str + " " + ttSum.first + " " + ttSum.second + "#";
        }
        
        String sendString="JAN "+tableUpDown.get("JAN").first+" "+tableUpDown.get("JAN").second+"#";
        sendString+="FEB "+tableUpDown.get("FEB").first+" "+tableUpDown.get("FEB").second+"#";
        sendString+="MAR "+tableUpDown.get("MAR").first+" "+tableUpDown.get("MAR").second+"#";
        sendString+="APR "+tableUpDown.get("APR").first+" "+tableUpDown.get("APR").second+"#";
        sendString+="MAY "+tableUpDown.get("MAY").first+" "+tableUpDown.get("MAY").second+"#";
        sendString+="JUN "+tableUpDown.get("JUN").first+" "+tableUpDown.get("JUN").second+"#";
        sendString+="JUL "+tableUpDown.get("JUL").first+" "+tableUpDown.get("JUL").second+"#";
        sendString+="AUG "+tableUpDown.get("AUG").first+" "+tableUpDown.get("AUG").second+"#";
        sendString+="SEP "+tableUpDown.get("SEP").first+" "+tableUpDown.get("SEP").second+"#";
        sendString+="OCT "+tableUpDown.get("OCT").first+" "+tableUpDown.get("OCT").second+"#";
        sendString+="NOV "+tableUpDown.get("NOV").first+" "+tableUpDown.get("NOV").second+"#";
        sendString+="DEC "+tableUpDown.get("DEC").first+" "+tableUpDown.get("DEC").second+"#";


        return sendString;
    }

    private void makedatatable() {
        tablet.put("Income", 0.0);
        tablet.put("Bills", 0.0);
        tablet.put("Business", 0.0);
        tablet.put("Education", 0.0);
        tablet.put("Entertainment", 0.0);
        tablet.put("Fees", 0.0);
        tablet.put("Financial", 0.0);
        tablet.put("Food", 0.0);
        tablet.put("Gift", 0.0);
        tablet.put("Donation", 0.0);
        tablet.put("Technology", 0.0);
        tablet.put("Health", 0.0);
        tablet.put("Home", 0.0);
        tablet.put("Kids", 0.0);
        tablet.put("Pets", 0.0);
        tablet.put("Shopping", 0.0);
        tablet.put("Taxes", 0.0);
        tablet.put("Transfer", 0.0);
        tablet.put("Travel", 0.0);
        tablet.put("Uncategorized", 0.0);
    }

    private String makedatatable(String s) {
        String string = "";
        s = s.replace("*", " ");
        String[] arStrings = s.split("#");

        for (String s1 : arStrings) {
            String[] ddddStrings = s1.split(" ");
            Double d = tablet.get(ddddStrings[3]);
            d += Double.parseDouble(ddddStrings[4]);
            tablet.put(ddddStrings[3], d);
        }

        Enumeration<String> names = tablet.keys();

        while (names.hasMoreElements()) {

            String str = names.nextElement();
            Double dd = tablet.get(str);
            string += str + "*" + dd + "#";

        }

        return string;
    }

    private String makedatatable(String s, String kString) {
        makedatatable();
        String string = "";
        s = s.replace("*", " ");
        String[] arStrings = s.split("#");

        for (String s1 : arStrings) {
            if (s1.contains(kString)) {
                String[] ddddStrings = s1.split(" ");
                Double d = tablet.get(ddddStrings[3]);
                d += Double.parseDouble(ddddStrings[4]);
                tablet.put(ddddStrings[3], d);
            }
        }

        Enumeration<String> names = tablet.keys();

        while (names.hasMoreElements()) {

            String str = names.nextElement();
            Double dd = tablet.get(str);
            string += str + "*" + dd + "#";

            //System.out.println(str + ": " +  balance.get(str));
        }
        return string;
    }
}
