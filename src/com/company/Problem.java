package com.company;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

interface Function_input{
    void get_question(String question);
    void get_example(String example);
    void get_select(int num, String select_string);
    void get_correct(int correct);
    void get_hsj(String hsj);
}
interface Sql_input{
    void input_data(String target_db, String target_table,  String sql_id, String sql_pw) throws SQLException;
}

interface Sql_make_table{
    void make_table(String target_db, String table_name, String sql_id, String sql_pw) throws SQLException;
}

abstract class Abstract_Problem implements Function_input, Sql_input{
    abstract void print_info();
    abstract void print_file(int i, int year, int month, String subject, int grade) throws IOException;
}


public class Problem extends Abstract_Problem{ //
    private int num;
    private String question;
    private String example;
    private String select[] = new String[5];
    private int correct;
    private String hsj;
    private String bigtype = "-";
    private String smalltype;

    Problem(int num) {
        this.num = num + 1;
    }

    public void get_question(String question) {
        this.question = question;
    }
    public void get_example(String example) {
        this.example = example;
    }
    public void get_select(int num, String select_string) {
        this.select[num] = select_string;
    }
    public void get_correct(int correct) {
        this.correct = correct;
    }
    public void get_hsj(String hsj){
        this.hsj = hsj;
    }
    public void get_bigtype(String bigtype){
        this.bigtype = bigtype;
    }
    public void get_smalltype(String smalltype){
        this.smalltype = smalltype;
    }

    void print_info() {
        System.out.println("문제번호 : " + num);
        System.out.println("문제질문 : " + question);
        System.out.println("문제 보기 : " + example);
        for (int i = 0; i < 5; i++) {
            System.out.println((i + 1) + "번째선택지 : " + select[i]);
        }
        System.out.println("문제 정답 : " + correct);
        System.out.println("문제 해설 : " + hsj);
        System.out.println("큰 유형 : " + bigtype);
        System.out.println("작은 유형 : " + smalltype);
        System.out.println("");


    }
    void print_file(int i, int year, int month, String subject, int grade) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("d:/out.txt", true));
        String temp_subject;
        if(subject.equals("영어")) {
            temp_subject = "english";
            if(num<17){
                pw.println("INSERT INTO list VALUES(NULL,"+ num +",\'" + question + "\'" + ", " + "\'" + example + "\'" + ", " + "\'" + select[0] + "\'" + ", " + "\'" + select[1] + "\'" + ", " + "\'" + select[2] + "\'" + ", " + "\'" + select[3] + "\'" + ", " + "\'" + select[4] + "\'" + ", "+ "\'"+"/sound/"+year+"_"+month+"_"+grade+"_"+temp_subject+"/"+num+".mp3"+"\'" +", NULL, " + correct + ", "+ "\""+hsj+"\", " +"\""+bigtype+"\", " +"\""+smalltype+"\", " +year+", "+month+", \""+subject+"\", "+ grade+");");
            }
            else if(num == 17){
                pw.println("INSERT INTO list VALUES(NULL,"+ num +",\'" + question + "\'" + ", " + "\'" + example + "\'" + ", " + "\'" + select[0] + "\'" + ", " + "\'" + select[1] + "\'" + ", " + "\'" + select[2] + "\'" + ", " + "\'" + select[3] + "\'" + ", " + "\'" + select[4] + "\'" + ", "+ "\'"+"/sound/"+year+"_"+month+"_"+grade+"_"+temp_subject+"/"+(num-1)+".mp3"+"\'" +", NULL, " + correct + ", "+ "\""+hsj+"\", " +"\""+bigtype+"\", " +"\""+smalltype+"\", " +year+", "+month+", \""+subject+"\", "+ grade+");");
            }
            else{
                pw.println("INSERT INTO list VALUES(NULL,"+ num +",\'" + question + "\'" + ", " + "\'" + example + "\'" + ", " + "\'" + select[0] + "\'" + ", " + "\'" + select[1] + "\'" + ", " + "\'" + select[2] + "\'" + ", " + "\'" + select[3] + "\'" + ", " + "\'" + select[4] + "\'" + ", "+ "NULL" +", NULL, " + correct + ", "+ "\""+hsj+"\", "  +"\""+bigtype+"\", " +"\""+smalltype+"\", " +year+", "+month+", \""+subject+"\", "+ grade+");");
            }
        }
        else if(subject.equals("국어")) {
            temp_subject = "korean";
        }
        else if(subject.equals("수학")) {
            temp_subject = "math";
        }


        pw.close();
    }

    public void input_data(String target_db, String target_table, String sql_id, String sql_pw) throws SQLException{
        //System.out.println();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://" + target_db + "?autoReconnect=true&useSSL=false", sql_id, sql_pw); // 연결
            System.out.println("Mysql DB Connection.");

            Statement stmt = con.createStatement();

            stmt.executeUpdate(
                    "INSERT INTO " + target_table + "VALUES("+ num +",\'" + question + "\'" + ", " + "\'" + example + "\'" + ", " + "\'" + select[0] + "\'" + ", " + "\'" + select[1] + "\'" + ", " + "\'" + select[2] + "\'" + ", " + "\'" + select[3] + "\'" + ", " + "\'" + select[4] + "\'" + ",NULL,NULL,NULL" +", 2018, 9, \"영어\", 2);");
            System.out.println("Insert Data Complete");
            con.close();
        } catch (Exception e) {
            System.out.println("Mysql Server Not Connection.");
            e.printStackTrace();
        }
    }
}

class SQLer implements Sql_make_table {
    public void make_table(String target_db, String table_name, String sql_id, String sql_pw) throws SQLException{
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://" + target_db + "?autoReconnect=true&useSSL=false", sql_id, sql_pw); // 연결
            System.out.println("Mysql DB Connection.");

            Statement stmt = con.createStatement();

            stmt.execute("CREATE TABLE list(id INT PRIMARY KEY AUTO_INCREMENT, num INT, question TEXT NOT NULL, example TEXT, select1 TEXT, select2 TEXT, select3 TEXT, select4 TEXT, select5 TEXT, sound TEXT, picture TEXT, thing INT, year INT,month INT, subject VARCHAR(11), grade INT);");
            System.out.println("Table Created OR SELECTED");
        } catch (Exception e) {
            System.out.println("Mysql Server Not Connection.");
            e.printStackTrace();
        }
    }
}
