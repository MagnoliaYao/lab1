package P1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
* 1.判断是否是幻方
*    1.1 行列数是否相同
*    1.2 矩阵数字是否都是正整数
*    1.3  数字之间分隔符是不是\t
* */
public class MagicSquares {
    public static void main(String[] args) throws IOException {
        for (int i = 1; i <= 5; i++) {
            boolean Answer = isLegalMagicSquare(i + ".txt");
            System.out.println(Answer);
        }
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        while (n <= 0 || n % 2 == 0) {
            System.out.println("Input Wrong");
            n = sc.nextInt();
        }
        generateMagicSquare(n);
        System.out.println("6" + " " + String.valueOf(isLegalMagicSquare("src/P1/txt/" + "6" + ".txt")));
        return;
    }

    public static boolean isLegalMagicSquare(String fileName) {
       //行，列 //由于开数组不确定txt文件的大小，所以使用ArrayList
        int diagonal = 0,rDiagonal = 0;
        String root = System.getProperty("user.dir");
        String filePath = root + File.separator + "P1" + File.separator + "txt" + File.separator + fileName; //"+File.separator+"表示适应系统的文件分隔符（linux、Windows不同）
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File(filePath);
            InputStreamReader input = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(input);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        int width = arrayList.get(0).split("\t").length;
        int array[][] = new int[length][width];
        for (int i = 0; i < length; i++) {
            if (arrayList.get(i).split(" ") != null) {
                System.out.println("不是幻方");
                return false;
            }
                if (arrayList.get(i).split("-") != null || arrayList.get(i).split("\\.") != null) {
                    System.out.println("不是幻方");
                    return false;
                }
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                String s = arrayList.get(i).split("\t")[j];
                array[i][j] = Integer.parseInt(s);
            }
        }
        if(length!=width){
            System.out.println("不是幻方");
            return false;
        }


        int row[] = new int[length*width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                row[i] += array[i][j];  //每行的和
                row[i+width] += array[j][i];  //每列的行
                diagonal += array[i][i];  //正对角线
                rDiagonal += array[i][length-i-1];  //反对角线
             }
        }
        for (int i = 0; i < row.length; i++) {
            if (row[i] == row[0] && diagonal == rDiagonal && row[0] == diagonal) {
                System.out.println("是幻方");
                return true;
            }
            else{
                System.out.println("不是幻方");
                return false;
            }
        }


       return true;
    }

    public static boolean generateMagicSquare(int n) throws IOException {
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            if (i % n == 0)
                row++;
            else {
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
        File file = new File("src/P1/txt/6.txt");
        PrintWriter output = new PrintWriter(file);
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                output.print(magic[i][j] + "\t");
            output.println();
        }
        output.close();
        return true;
    }

}
