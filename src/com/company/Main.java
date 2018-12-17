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

        String total_text;
        StringBuilder temp_total_text = new StringBuilder();
        int index_num[] = new int[amount_problem];
        int index_question_dot[] = new int[amount_problem];
        int index_select[][] = new int[amount_problem][5];
        String temp_string[] = new String[amount_problem];

        BufferedReader br1 = new BufferedReader(new FileReader(file_input_address));
        while(true) {
            String line = br1.readLine();
            if (line==null) break;
            temp_total_text.append(line);
        }
        br1.close();
        total_text = temp_total_text.toString();

        for (int i=0; i<amount_problem; i++) {
            index_num[i] = total_text.indexOf(Integer.toString(i + 1) + "."); //
            //System.out.println("-------- "+i+"st's index is aright. -----------");
        }
        //   System.out.println("first end");
        for (int i=0; i<amount_problem; i++){
            System.out.println("---------first "+i+"st is problem?! ---------");

            if(i < amount_problem-1){
                System.out.println(index_num[i]+" "+index_num[i+1]);
                temp_string[i] = total_text.substring(index_num[i], index_num[i+1]);
            }
            else{
                // System.out.println(index_num[i]);
                temp_string[i] = total_text.substring(index_num[i]);
            }
            //System.out.println(temp_string[i]);
            index_select[i][0] = temp_string[i].indexOf("① ");
            index_select[i][1] = temp_string[i].indexOf("② ");
            index_select[i][2] = temp_string[i].indexOf("③ ");
            index_select[i][3] = temp_string[i].indexOf("④ ");
            index_select[i][4] = temp_string[i].indexOf("⑤ ");

            int temp_index_dot;
            int dot_mark;
            int question_mark;
            if(temp_string[i].substring(3).indexOf("?") != -1){ question_mark = temp_string[i].substring(3).indexOf("?"); }
            else{ question_mark = 999999999; }
            if(temp_string[i].substring(3).indexOf(".") != -1){ dot_mark = temp_string[i].substring(3).indexOf("."); }
            else{ dot_mark = 999999999; }

            if (dot_mark < question_mark){
                temp_index_dot = dot_mark;
                //System.out.println("---"+i+"------d---"+dot_mark);
                //System.out.println("------------"+question_mark);
            }
            else{
                temp_index_dot = question_mark;
                //System.out.println("---"+i+"------q---"+question_mark);
                //System.out.println("------------"+dot_mark);
            }
            //int temp_index_dot = temp_string[i].substring(3).indexOf(".");
            if (temp_index_dot != -1){
                index_question_dot[i] = temp_index_dot+3; // 바로 위의 변수 초기화에서 2부터 시작하는 문장에서의 .을 찾으므로 +2를 해주어야 원래 위치.
            }
            System.out.println("--------first "+i+"st is not problem. -----------");
        }

        //    System.out.println("second end");
        for(int i=0; i<amount_problem; i++){
            System.out.println("---------second "+i+"st is problem?! ---------");
            System.out.println("---------"+index_question_dot[i]+"--"+index_select[i][0]);

            problem[i].get_question(temp_string[i].substring(3, index_question_dot[i]+1)); //점 포함을 위해 +1
            problem[i].get_example(temp_string[i].substring(index_question_dot[i]+1, index_select[i][0])); //점 제외를 위해 +1
            for (int j = 0; j < 5; j++){
                if(j < 4){ // 5 - 1 = 4
                    //System.out.println("----------------"+index_select[i][j]);
                    problem[i].get_select(j, temp_string[i].substring(index_select[i][j]+1, index_select[i][j+1])); //번호 제외를 위해 +1
                    //problem[i].get_select(j, temp_string[i].substring(index_select[i][j], index_select[i][j+1]));
                }
                else{
                    problem[i].get_select(j, temp_string[i].substring(index_select[i][j]+1)); //동일
                    //problem[i].get_select(j, temp_string[i].substring(index_select[i][j]));
                }
            }
            System.out.println("--------second "+i+"st is not problem. -----------");
        }


        System.out.println("정답이 담긴 파일 경로를 입력해 주십시오. ( ex. C:\\correct.txt )"); // 정답 수집기
        String file_correct_address = sc.next(); //"D:\\correct.txt";
        //String file_correct_address = file_input_address.substring(0,15)+"_correct"+".txt";
        String total_correct_text;
        StringBuilder temp_correct_text = new StringBuilder();
        int correct_num[] = new int[amount_problem];

        BufferedReader br2 = new BufferedReader(new FileReader(file_correct_address));
        while(true) {
            String line = br2.readLine();
            if (line==null) break;
            temp_correct_text.append(line);
        }
        br2.close();
        total_correct_text = temp_correct_text.toString();
        for (int i=0; i<amount_problem; i++) {
            correct_num[i] = total_correct_text.indexOf(Integer.toString(i + 1)); //
        }
        for(int i=0; i<amount_problem; i++){
            int temp_correct=0;
            if (i != amount_problem-1){
                String correct_string_temp = total_correct_text.substring(correct_num[i],correct_num[i+1]);
                if (correct_string_temp.contains("①")){temp_correct = 1;}
                else if (correct_string_temp.contains("②")){temp_correct = 2;}
                else if (correct_string_temp.contains("③")){temp_correct = 3;}
                else if (correct_string_temp.contains("④")){temp_correct = 4;}
                else if (correct_string_temp.contains("⑤")){temp_correct = 5;}
            }
            else{
                String correct_string_temp = total_correct_text.substring(correct_num[i]);

                if (correct_string_temp.contains("①")){temp_correct = 1;}
                else if (correct_string_temp.contains("②")){temp_correct = 2;}
                else if (correct_string_temp.contains("③")){temp_correct = 3;}
                else if (correct_string_temp.contains("④")){temp_correct = 4;}
                else if (correct_string_temp.contains("⑤")){temp_correct = 5;}
            }
            problem[i].get_correct(temp_correct);
        }

/*
        System.out.println("해설이 담긴 파일 경로를 입력해 주십시오. ( ex. C:\\hsj.txt )"); // 해설 수집기
        String file_hsj_address = sc.next(); //"D:\\hsj.txt";
        //String file_hsj_address =  file_input_address.substring(0,5)+"hsj"+file_input_address.substring(8);
        String total_hsj_text;
        String temp_hsj_string[] = new String[amount_problem];
        int hsj_index_question_dot[] = new int[amount_problem];
        int hsj_index_num[] = new int[amount_problem];
        int hsj_index_select[][] = new int[amount_problem][5];

        StringBuilder temp_hsj_text = new StringBuilder();
        BufferedReader br3 = new BufferedReader(new FileReader(file_hsj_address));
        while(true) {
            String line = br3.readLine();
            if (line==null) break;
            temp_hsj_text.append(line);
        }
        br3.close();

        total_hsj_text = temp_hsj_text.toString();
        for (int i=0; i<amount_problem; i++) {
            hsj_index_num[i] = total_hsj_text.indexOf(Integer.toString(i + 1)+".");
        }
        for (int i=0; i<amount_problem; i++){
            System.out.println(i+"start------------------");
            if(i < amount_problem-1){
                temp_hsj_string[i] = total_hsj_text.substring(hsj_index_num[i], hsj_index_num[i+1]);
            }
            else{
                temp_hsj_string[i] = total_hsj_text.substring(hsj_index_num[i]);
            }

            int temp_index_dot;
            int dot_mark;
            int question_mark;

            if(temp_hsj_string[i].substring(3).indexOf("?") != -1){ question_mark = temp_hsj_string[i].substring(3).indexOf("?"); }
            else{ question_mark = 999999999; }
            if(temp_hsj_string[i].substring(3).indexOf(".") != -1){ dot_mark = temp_hsj_string[i].substring(3).indexOf("."); }
            else{ dot_mark = 999999999; }

            if (dot_mark < question_mark){
                temp_index_dot = dot_mark;
            }
            else {
                temp_index_dot = question_mark;
            }
            if (temp_index_dot != -1){
                hsj_index_question_dot[i] = temp_index_dot+3; // 바로 위의 변수 초기화에서 2부터 시작하는 문장에서의 .을 찾으므로 +2를 해주어야 원래 위치.
            }
            System.out.println(i+"end------------------");
        }
        for(int i=0; i<amount_problem; i++) {
            problem[i].get_hsj(temp_hsj_string[i].substring(0,hsj_index_question_dot[i])+"<br>"+temp_hsj_string[i].substring(hsj_index_question_dot[i]+1));
        }
*/

        System.out.println("추출할 모의고사 문제의 년도를 입력해주세요 : ");
        int year =  sc.nextInt();
        System.out.println("추출할 모의고사 문제의 월을 입력해주세요 : ");
        int month = sc.nextInt();
        System.out.println("추출할 모의고사 문제의 과목 입력해주세요 : ");
        String subject = sc.next();
        System.out.println("추출할 모의고사 문제의 학년을 입력해주세요 : ");
        int grade = sc.nextInt();
        //2018, 3, \"영어\", 2);");

        for (int i=0; i<amount_problem;i++) {
            problem[i].print_info();
            problem[i].print_file(i, year, month, subject, grade);
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
