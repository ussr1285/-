package com.company;
import java.lang.String;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main (String[] args) throws IOException, SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("분리하려는 문제들이 담긴 텍스트파일의 경로를 입력해 주십시오. ( ex. C:\\file.txt ) : ");
        String file_input_address = sc.next(); //"D:\\total.txt";
        System.out.println("당신이 분리하려는 것의 문제 수를 입력해 주십시오 :");
        int amount_problem = sc.nextInt(); //45;
        Problem problem[] = new Problem[amount_problem];
        for (int i=0; i<amount_problem; i++){
            problem[i] = new Problem(i);
        }

        String total_text = null;
        StringBuilder temp_total_text = new StringBuilder();
        int index_num[] = new int[amount_problem];
        int index_question_dot[] = new int[amount_problem];
        int index_select[][] = new int[amount_problem][5];
        String temp_string[] = new String[amount_problem];

        BufferedReader br = new BufferedReader(new FileReader(file_input_address));
        while(true) {
            String line = br.readLine();
            if (line==null) break;
            temp_total_text.append(line);
        }
        br.close();
        total_text = temp_total_text.toString();

        for (int i=0; i<amount_problem; i++) {
            index_num[i] = total_text.indexOf(Integer.toString(i + 1) + ".");
            //System.out.println("-------- "+i+"st's index is aright. -----------");
        }
        //   System.out.println("first end");
        for (int i=0; i<amount_problem; i++){
            if(i < amount_problem-1){
                //System.out.println(index_num[i]+" "+index_num[i+1]);
                temp_string[i] = total_text.substring(index_num[i], index_num[i+1]);
            }
            else{
                // System.out.println(index_num[i]);
                temp_string[i] = total_text.substring(index_num[i]);
            }
            //System.out.println(temp_string[i]);
            index_select[i][0] = temp_string[i].indexOf("①");
            index_select[i][1] = temp_string[i].indexOf("②");
            index_select[i][2] = temp_string[i].indexOf("③");
            index_select[i][3] = temp_string[i].indexOf("④");
            index_select[i][4] = temp_string[i].indexOf("⑤");

            int temp_index_dot;
            int dot_mark;
            int question_mark;
            if(temp_string[i].substring(3).indexOf("?") != -1){
                 question_mark = temp_string[i].substring(3).indexOf("?");
            }
            else{
                 question_mark = 999999999;
            }

            if(temp_string[i].substring(3).indexOf(".") != -1){
                dot_mark = temp_string[i].substring(3).indexOf(".");
            }
            else{
                dot_mark = 999999999;
            }

            if (dot_mark < question_mark){
                temp_index_dot = dot_mark;
                System.out.println("---"+i+"------d---"+dot_mark);
                System.out.println("------------"+question_mark);
            }
            else{
                temp_index_dot = question_mark;
                System.out.println("---"+i+"------q---"+question_mark);
                System.out.println("------------"+dot_mark);
            }
            System.out.println("@@@@@@@@@"+temp_index_dot);
            //int temp_index_dot = temp_string[i].substring(3).indexOf(".");
            if (temp_index_dot != -1){
                index_question_dot[i] = temp_index_dot+3; // 바로 위의 변수 초기화에서 2부터 시작하는 문장에서의 .을 찾으므로 +2를 해주어야 원래 위치.
            }

        }

        //    System.out.println("second end");
        for(int i=0; i<amount_problem; i++){
            System.out.println("--------- "+i+"st is problem?! ---------");
            System.out.println("---------"+index_question_dot[i]+"--"+index_select[i][0]);

            problem[i].get_question(temp_string[i].substring(3, index_question_dot[i]+1)); //점 포함을 위해 +1
            problem[i].get_example(temp_string[i].substring(index_question_dot[i]+1, index_select[i][0])); //점 제외를 위해 +1
            for (int j = 0; j < 5; j++){
                if(j < 4){ // 5 - 1 = 4
                    //System.out.println("----------------"+index_select[i][j]);
                    problem[i].get_select(j, temp_string[i].substring(index_select[i][j]+0, index_select[i][j+1])); //번호 제외를 위해 +1
                }
                else{
                    problem[i].get_select(j, temp_string[i].substring(index_select[i][j]+0)); //동일
                }
            }
            System.out.println("-------- "+i+"st is not problem. -----------");
        }
        for (int i=0; i<amount_problem;i++) {
            problem[i].print_info();
            problem[i].print_file();
        }

        System.out.println("분리완료");
        //    System.out.println("fourth end");

        System.out.println("데이터베이스의 정보를 입력해주십시오.( ex.localhost:3306/db ) :"); //db
        String target_db = sc.next(); //"localhost:3306/db";
        System.out.println("로그인할 MYSQL 아이디를 입력하세요.");
        String sql_id = sc.next(); //"root";
        System.out.println("로그인 할 MYSQL 비밀번호를 입력하세요.");
        String sql_pw = sc.next(); //"MINI5313";
        System.out.println("데이터를 넣으려는 테이블의 이름을 입력해주십시오. :"); //english
        String target_table = sc.next();// "english";

        SQLer sqLer = new SQLer();
        sqLer.make_table(target_db, target_table, sql_id, sql_pw);

        System.out.println("데이터 주입을 시작합니다.");


        for (int i=0; i<amount_problem;i++){
            problem[i].input_data(target_db, target_table, sql_id, sql_pw);
            System.out.println(i);
        }
        System.out.println("데이터들을 성공적으로 "+target_db+"의 "+target_table+"에 넣었습니다.");

//       System.out.println("five end");

        System.out.println("\n\n\nIs it work?");
    }

}
