package com.inovikov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

class Island {
    private HashMap<Integer, Cell> cells;
    private int m;
    private int n;
    private int result;


    //Конструктор
    Island(int m, int n) {
        this.m = m;
        this.n = n;
        cells = new HashMap<>();
    }

    int getResult() {
        return result;
    }

    // Метод заполнения ячеек
    void fillCells() throws IOException {
        // Заполняем ячейки острова
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean beachZone = false;

        for (int i = 0; i < m; i++) {
            while (true) {
                try {
                    String line = reader.readLine();
                    String[] args = line.split(" ");
                    if (args.length != n) {
                        throw new Exception();
                    }
                    for (int j = 0; j < n; j++) {
                        if (i == 0 || i == m - 1 || j == 0 || j == n - 1) { // Если это крайние значения матрицы, то это берег.
                            beachZone = true;
                        } else {
                            beachZone = false;
                        }
                        int height = Integer.parseInt(args[j]);
                        if(height>1000){
                            throw new Exception();
                        }
                        Cell newCell = new Cell(beachZone, height, i, j);
                        cells.put(newCell.getCellHashCode(), newCell);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Введите " + n + " чисел через пробел. Максимальная величина числа - 1000.");
                }
            }
        }

    }

    // Получить соседей ячейки
    private ArrayList<Integer> getNeibhorOfCell(Cell cell) {
        ArrayList<Integer> neibhorsList = null;

        neibhorsList = new ArrayList<>();
        int x = cell.getxCoord();
        int y = cell.getyCoord();
        if (!cell.isBeachZone()) {
            neibhorsList.add(cell.calculateHashCode(x + 1, y));
            neibhorsList.add(cell.calculateHashCode(x - 1, y));
            neibhorsList.add(cell.calculateHashCode(x, y + 1));
            neibhorsList.add(cell.calculateHashCode(x, y - 1));
        } else {
            if (x < m - 2) neibhorsList.add(cell.calculateHashCode(x + 1, y));
            if (x > 0) neibhorsList.add(cell.calculateHashCode(x - 1, y));
            if (y < n - 2) neibhorsList.add(cell.calculateHashCode(x, y + 1));
            if (y > 0) neibhorsList.add(cell.calculateHashCode(x, y - 1));
        }

        return neibhorsList;
    }
    private boolean someNeighFlows(ArrayList<Integer> neighbors){
        for(int i: neighbors){
            if(cells.get(i).getState() == Cell.State.Flows){
                return true;
            }

        }
        return false;
    }
    // Алгоритм подсчета острова
    void calculateIsland() {
        int result = 0;
        boolean hasDry = true; // Есть ли сухие ячейки, цикл идет пока не останется сухих ячеек(hasDry = false)
        for (int layer = 1; hasDry; layer++) { // Идем по высоте, начиная с 0
            for (Map.Entry<Integer, Cell> entry : cells.entrySet()) { // для каждой ячейки
                Cell cell = entry.getValue();
                cell.setState(Cell.State.Fill);
            }
            for (Map.Entry<Integer, Cell> entry : cells.entrySet()) {
                Cell cell = entry.getValue();
                checkCell(layer, cell);
            }
            hasDry = false;
            for (Map.Entry<Integer, Cell> entry : cells.entrySet()) { // В цикле проверяем
                Cell cell = entry.getValue();
                if (cell.getState() == Cell.State.Dry) {
                    hasDry = true;
                } else if (cell.getState() == Cell.State.Fill) {
                    result++;
                }
            }
        }
        this.result = result;
        cells.clear();
    }
    // Проверка ячейки
    private void checkCell(int layer, Cell cell) {

        if (cell.getState() != Cell.State.Fill) {
            return;
        }
        if (cell.getHeight() >= layer) {
            cell.setState(Cell.State.Dry);
            return;
        }
        if (cell.isBeachZone()) {
            cell.setState(Cell.State.Flows);
            return;
        }
        ArrayList<Integer> neighbors = getNeibhorOfCell(cell);
        // Если хотя бы один сосед переполнен(flows) - значит текущая ячейка тоже вытекает и нужно проверить всех остальных соседей.
        if(someNeighFlows(neighbors)){
            cell.setState(Cell.State.Flows);
            for(int i: neighbors){
                checkCell(layer, cells.get(i));
            }
        }
    }
}
