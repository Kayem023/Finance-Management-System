package financemanagement;

import FileClass.FileClassDemo;

public class RandomGiving {

    public static void main(String[] args) {
        String[] myarray = {"Income", "Bills", "Business", "Education", "Entertainment", "Fees", "Financial", "Food", "Gift", "Donation", "Technology", "Health", "Home", "Kids", "Pets", "Shopping", "Taxes", "Transfer", "Travel", "Uncategorized"};
        FileClassDemo fd = new FileClassDemo("AllFile/Tawhidul Bhuiyan_0000Transaction.txt");
        for (int i = 1; i < 32; i++) {
            
            fd.appendwrite(i + "*" + "JAN*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JAN*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JAN*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JAN*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JAN*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
        }
        
        for (int i = 1; i < 30; i++) {
            fd.appendwrite(i + "*" + "FEB*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "FEB*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "FEB*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "FEB*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "FEB*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
        }
        
        for (int i = 1; i < 32; i++) {
            fd.appendwrite(i + "*" + "MAR*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "MAR*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "MAR*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "MAR*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "MAR*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
        }
        
        for (int i = 1; i < 31; i++) {
            //System.out.println(((int)Math.pow((Math.random()*10),2))%20);
            //System.out.println((int)(Math.random()*10000));
            fd.appendwrite(i + "*" + "APR*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "APR*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "APR*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "APR*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "APR*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
        }
        
        for (int i = 1; i < 32; i++) {
            //System.out.println(((int)Math.pow((Math.random()*10),2))%20);
            //System.out.println((int)(Math.random()*10000));
            fd.appendwrite(i + "*" + "MAY*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "MAY*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "MAY*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "MAY*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "MAY*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
        }
        
        for (int i = 1; i < 31; i++) {
            //System.out.println(((int)Math.pow((Math.random()*10),2))%20);
            //System.out.println((int)(Math.random()*10000));
            fd.appendwrite(i + "*" + "JUN*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JUN*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JUN*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JUN*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JUN*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
        }
        
        for (int i = 1; i < 32; i++) {
            //System.out.println(((int)Math.pow((Math.random()*10),2))%20);
            //System.out.println((int)(Math.random()*10000));
            fd.appendwrite(i + "*" + "JUL*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JUL*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JUL*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JUL*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "JUL*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
        }
        
        for (int i = 1; i < 32; i++) {
            //System.out.println(((int)Math.pow((Math.random()*10),2))%20);
            //System.out.println((int)(Math.random()*10000));
            fd.appendwrite(i + "*" + "AUG*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "AUG*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "AUG*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "AUG*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "AUG*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
        }
        
        for (int i = 1; i < 31; i++) {
            //System.out.println(((int)Math.pow((Math.random()*10),2))%20);
            //System.out.println((int)(Math.random()*10000));
            fd.appendwrite(i + "*" + "SEP*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "SEP*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "SEP*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "SEP*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "SEP*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
        }
        
        for (int i = 1; i < 32; i++) {
            //System.out.println(((int)Math.pow((Math.random()*10),2))%20);
            //System.out.println((int)(Math.random()*10000));
            fd.appendwrite(i + "*" + "OCT*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "OCT*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "OCT*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "OCT*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "OCT*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
        }
        
        for (int i = 1; i < 31; i++) {
            //System.out.println(((int)Math.pow((Math.random()*10),2))%20);
            //System.out.println((int)(Math.random()*10000));
            fd.appendwrite(i + "*" + "NOV*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "NOV*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "NOV*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "NOV*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "NOV*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
        }
        
        for (int i = 1; i < 32; i++) {
            //System.out.println(((int)Math.pow((Math.random()*10),2))%20);
            //System.out.println((int)(Math.random()*10000));
            fd.appendwrite(i + "*" + "DEC*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "DEC*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "DEC*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "DEC*" + "2016*" + myarray[0] + "*" + (int) (Math.random() * 10000) + "#");
            fd.appendwrite(i + "*" + "DEC*" + "2016*" + myarray[(((int) Math.pow((Math.random() * 10), 2)) % 20)] + "*" + (int) (Math.random() * 10000) + "#");
        }
    }
}
