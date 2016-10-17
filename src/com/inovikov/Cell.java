package com.inovikov;

class Cell {
    enum State {Dry, Fill, Flows } // Индикаторы ячейки - Сухая, Заполненная, Вытекает
    private boolean beachZone = false;
    private int height = 0;
    private int xCoord;
    private int yCoord;
    private State state = State.Flows; // Ячейки изначально заполнены
    private int cellHashCode;

    Cell(boolean beachZone, int height, int xCoord, int yCoord) {
        this.beachZone = beachZone;
        this.height = height;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.cellHashCode = calculateHashCode(xCoord, yCoord);
    }

    boolean isBeachZone() {
        return beachZone;
    }

    int getHeight() {
        return height;
    }

    State getState() { return state; }

    int getxCoord() {
        return xCoord;
    }

    int getyCoord() {
        return yCoord;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    int getCellHashCode() {
        return cellHashCode;
    }
    // HashCode для ячейки, чтобы проще найти соседей из Map
    int calculateHashCode(int x, int y){
        return 17*x+x + 37*y+y + 9*x*y;
    }
    void setState(State state) {
        this.state = state;
    }
}
