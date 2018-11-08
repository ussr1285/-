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
}
interface Sql_input{
    void input_data(String target_db, String target_table,  String sql_id, String sql_pw) throws SQLException;
}

interface Sql_make_table{
    void make_table(String target_db, String table_name, String sql_id, String sql_pw) throws SQLException;
}

abstract class Abstract_Problem implements Function_input, Sql_input{
    abstract void print_info();
    abstract void print_file(int year, int month, String subject, int grade) throws IOException;
}


public class Problem extends Abstract_Problem{ //
    private int num;
    private String question;
    private String example;
    private String select[] = new String[5];
    private int correct;

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

    void print_info() {
        System.out.println("문제번호 : " + num);
        System.out.println("문제질문 : " + question);
        System.out.println("문제 보기 : " + example);
        for (int i = 0; i < 5; i++) {
            System.out.println((i + 1) + "번째선택지 : " + select[i]);
        }
        System.out.println("문제 정답 : " + correct);
        System.out.println("");


    }
    void print_file(int year, int month, String subject, int grade) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("d:/out.txt", true));
        pw.println("INSERT INTO list VALUES(NULL,"+ num +",\'" + question + "\'" + ", " + "\'" + example + "\'" + ", " + "\'" + select[0] + "\'" + ", " + "\'" + select[1] + "\'" + ", " + "\'" + select[2] + "\'" + ", " + "\'" + select[3] + "\'" + ", " + "\'" + select[4] + "\'" + ",NULL,NULL," + correct + ", "+year+", "+month+", \""+subject+"\", "+ grade+");");
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
