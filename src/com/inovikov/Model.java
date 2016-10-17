package com.inovikov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


class Model {

    private ArrayList<Island> islandList = new ArrayList<>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    boolean modelStart() throws IOException {
        // k - Количество островов
        int k;
        while (true) {
            try {
                k = Integer.parseInt(reader.readLine());
                if(k <= 0){
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Введите количество островов(целое число).");
            }
        }
        // создаем острова и заполняем их ячейки
        createIslands(k);
        for(Island isl : islandList){
            System.out.println(isl.getResult());

        }
        return false;
    }


    //Создаем острова
    private void createIslands(int k) throws IOException {
        //m - строки, n - столбцы
        int m;
        int n;
       for (int i = 0; i < k; i++) {
            while (true) {
                try {

               String[] args = reader.readLine().split(" ");
               if(args.length != 2){
                   throw new Exception();
               }
               m = Integer.parseInt(args[0]);
               n = Integer.parseInt(args[1]);
               if(m > 50 || n > 50){
                   throw new Exception();
               }
               break;
           } catch (Exception e){
               System.out.println("Введите размерность матрицы через пробел. Максимально допустимые размеры 50х50.");
           }
       }
           Island isl = new Island(m, n);
           // Заполнить ячейки
           isl.fillCells();
           isl.calculateIsland();
           islandList.add(isl);

       }
    }
}
