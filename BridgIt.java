import java.util.ArrayList;
import java.util.Arrays;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// represent non-empty and empty cell
interface Cell {
  // return the Cell above
  public Cell getAbove();

  // return the Cell below
  public Cell getBelow();

  // return the Cell on the left
  public Cell getLeft();

  // return the Cell on the right
  public Cell getRight();

  // set the given Cell to its neighbor above
  public void setNeighbAbove(Cell above);

  // set the given Cell to its neighbor below
  public void setNeighbBelow(Cell below);

  // set the given Cell to its neighbor on the left
  public void setNeighbLeft(Cell left);

  // set the given Cell to its neighbor on the right
  public void setNeighbRight(Cell right);

  // return the WorldImage that draw the Cell on the given background image 
  // in its given x and y coordinate
  public WorldImage drawAt(int x, int y, WorldImage background);

  // check whether this cell is a player1 cell
  public boolean player1linkOrNot();

  // check whether this cell is a player2 cell
  public boolean player2linkOrNot();

  // check whether this cell is an empty cell
  public boolean isEmpty();

  // check whether this cell is a white cell
  // since only white cell can be clicked
  public boolean ableToClick();
}

// represent non-empty cell
abstract class NonEmptyCell implements Cell {

  Color c;
  Cell above;
  Cell below;
  Cell left;
  Cell right;

  // draw the cell at the given position with the given background
  public WorldImage drawAt(int x, int y, WorldImage background) {

    return (new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
        (new RegularPolyImage(50, 4, OutlineMode.SOLID, this.c)),
        - x * 50, - y * 50, background));
  }

  // get the cell above
  public Cell getAbove() {
    return this.above;
  }

  // get the cell below
  public Cell getBelow() {
    return this.below;
  }

  // get the cell on the left
  public Cell getLeft() {
    return this.left;
  }

  // get the cell on the right
  public Cell getRight() {
    return this.right;
  }

  // set the given cell to its above neighbor
  public void setNeighbAbove(Cell above) {
    this.above = above;
  }

  //set the given cell to its below neighbor
  public void setNeighbBelow(Cell below) {
    this.below = below;
  }

  //set the given cell to its left neighbor
  public void setNeighbLeft(Cell left) {
    this.left = left;
  }

  //set the given cell to its right neighbor
  public void setNeighbRight(Cell right) {
    this.right = right;
  }
}

// represent a palyer1 cell
class Player1Cell extends NonEmptyCell {

  // constructor of a player1 cell
  Player1Cell(Cell above, Cell below, Cell left, Cell right) {
    this.c = Color.MAGENTA;
    this.above = above;
    this.below = below;
    this.left = left;
    this.right = right;
  }

  // another constructor of a player1 cell
  Player1Cell() {
    this.c = Color.MAGENTA;
    this.above = this;
    this.below = this;
    this.left = this;
    this.right = this;
  }

  // return true since it's a player1 cell
  public boolean player1linkOrNot() {
    return true;
  }

  // return false since it's not a player2 cell
  public boolean player2linkOrNot() {
    return false;
  }

  // return false since it's not an empty list
  public boolean isEmpty() {
    return false;
  }

  // return false since it's not an white cell
  public boolean ableToClick() {
    return false;
  }
}

//represent a palyer1 cell
class Player2Cell extends NonEmptyCell {

  // constructor of a player1 cell
  Player2Cell(Cell above, Cell below, Cell left, Cell right) {
    this.c = Color.PINK;
    this.above = above;
    this.below = below;
    this.left = left;
    this.right = right;
  }

  // constructor of player1 cell with empty neighbor value
  Player2Cell() {
    this.c = Color.PINK;
    this.above = this;
    this.below = this;
    this.left = this;
    this.right = this;
  }

  // return false since it's not a player1 cell
  public boolean player1linkOrNot() {
    return false;
  }

  // return true since it's a player2 cell
  public boolean player2linkOrNot() {
    return true;
  }

  // return false since it's not an empty cell
  public boolean isEmpty() {
    return false;
  }

  // return false since it's not a white cell
  public boolean ableToClick() {
    return false;
  }
}

//represent a palyer1 cell
class WhiteCell extends NonEmptyCell {

  // constructor of a white cell
  WhiteCell(Cell above, Cell below, Cell left, Cell right) {
    this.c = Color.WHITE;
    this.above = above;
    this.below = below;
    this.left = left;
    this.right = right;
  }

  // constructor of a white cell with empty value of neighbors
  WhiteCell() {
    this.c = Color.WHITE;
    this.above = this;
    this.below = this;
    this.left = this;
    this.right = this;
  }

  // return false since it's not a player1 cell
  public boolean player1linkOrNot() {
    return false;
  }

  // return false since it's not a player2 cell
  public boolean player2linkOrNot() {
    return false;
  }

  // return false since it's not an empty cell
  public boolean isEmpty() {
    return false;
  }

  // return true since it's a white cell
  public boolean ableToClick() {
    return true;
  }
}

// represent an empty cell
class MptCell implements Cell {

  MptCell() {}

  // throw exception
  public WorldImage drawAt(int x, int y, WorldImage background) {
    throw new RuntimeException("cannot draw empty cell");
  }

  // throw exception
  public Cell getAbove() {
    throw new RuntimeException("cannot get above");
  }

  // throw exception
  public Cell getBelow() {
    throw new RuntimeException("cannot get below");
  }

  // throw exception
  public Cell getLeft() {
    throw new RuntimeException("cannot get left");
  }

  // throw exception
  public Cell getRight() {
    throw new RuntimeException("cannot get right");
  }

  // throw exception
  public void setNeighbAbove(Cell above) {
    throw new RuntimeException("cannot set neighbour");
  }

  // throw exception
  public void setNeighbBelow(Cell below) {
    throw new RuntimeException("cannot set neighbour");
  }

  // throw exception
  public void setNeighbLeft(Cell left) {
    throw new RuntimeException("cannot set neighbour");
  }

  // throw exception
  public void setNeighbRight(Cell right) {
    throw new RuntimeException("cannot set neighbour");
  }

  // throw exception
  public boolean player1linkOrNot() {
    throw new RuntimeException("cannot compare with non-empty cell");
  }

  // throw exception
  public boolean player2linkOrNot() {
    throw new RuntimeException("cannot compare with non-empty cell");
  }

  // return true as it's an empty cell
  public boolean isEmpty() {
    return true;
  }

  // throw exception
  public boolean ableToClick() {
    throw new RuntimeException("should not able to click on empty cell");
  }
}

// represent a world class to draw bridgIt
class BridgIt extends World {

  int size;
  ArrayList<ArrayList<Cell>> cells;
  Boolean player1Round;
  int player1moves;
  int player2moves;

  // constructor of BridgIt with given n value
  BridgIt(int n) {
    this.size = n;
    this.player1Round = true;
    this.player1moves = 0;
    this.player2moves = 0;

    // check whether the given size is an odd number and greater than 1
    if (n % 2 == 1 && n >= 3) {
      this.cells = this.generateArrayList();
      this.setNeighbour();
    }
  }

  // constructor of BridgIt with both given n value and arraylist of arraylist of cells
  BridgIt(int n, ArrayList<ArrayList<Cell>> cells) {
    this.size = n;
    this.cells = cells;
    this.player1Round = true;
    this.player1moves = 0;
    this.player2moves = 0;
  }

  // generate an ArrayList that particular cells are in their correct positions,
  // without neighbor values
  public ArrayList<ArrayList<Cell>> generateArrayList() {
    ArrayList<ArrayList<Cell>> output = new ArrayList<ArrayList<Cell>>(this.size);
    for (int i = 0; i < this.size; i ++) {
      output.add(new ArrayList<Cell>(this.size));
    }

    for (int i = 0; i < this.size; i ++) {
      for (int j = 0; j < this.size; j ++) {
        if (i % 2 == 0) {
          if (j % 2 == 0) {
            output.get(i).add(new WhiteCell());
          }
          else {
            output.get(i).add(new Player1Cell());
          }
        }
        else {
          if (j % 2 == 0) {
            output.get(i).add(new Player2Cell());
          }
          else {
            output.get(i).add(new WhiteCell());
          }
        }
      }
    }
    return output;
  }

  // set the neighbor values to the arraylist of arraylist of cells
  public void setNeighbour() {

    for (int i = 0; i < this.size; i ++) {
      for (int j = 0 ; j < this.size; j ++) {

        // set neighbor above 
        if (i == 0) {
          this.cells.get(i).get(j).setNeighbAbove(new MptCell());
        }

        else {
          this.cells.get(i).get(j).setNeighbAbove(
              this.cells.get(i - 1).get(j));
        }

        // set neighbor below
        if (i == this.size - 1) {
          this.cells.get(i).get(j).setNeighbBelow(new MptCell());
        }

        else {
          this.cells.get(i).get(j).setNeighbBelow(
              this.cells.get(i + 1).get(j));
        }

        // set neighbor on the left
        if (j == 0) {
          this.cells.get(i).get(j).setNeighbLeft(new MptCell());
        }

        else {
          this.cells.get(i).get(j).setNeighbLeft(
              this.cells.get(i).get(j - 1));
        }

        // set neighbor on the right
        if (j == this.size - 1) {
          this.cells.get(i).get(j).setNeighbRight(new MptCell());
        }

        else {
          this.cells.get(i).get(j).setNeighbRight(
              this.cells.get(i).get(j + 1));
        }
      }
    }
  }

  // draw every cell as a whole picture
  public WorldImage pic() {
    WorldImage whole = new FrameImage(new RectangleImage(50 * this.size, 50 * this.size,
        OutlineMode.SOLID, Color.WHITE));
    for (int i = 0; i < this.cells.size(); i ++) {
      for (int j = 0; j < this.cells.get(i).size(); j ++) {
        whole = this.cells.get(i).get(j).drawAt(j, i, whole);
      }
    }
    return whole;
  }

  // change the cell based on where the mouse click
  public void onMouseClicked(Posn pos) {
    // scene for player1 win 
    if (this.player1Win()) {
      this.endOfWorld("Player 1 Win. You Make " + this.player1moves + " Moves");
    }

    // scene for player2 win
    if (this.player2win()) {
      this.endOfWorld("Player 2 win. You Make " + this.player2moves + " Moves");
    }

    int cellX = pos.x / 50;
    int cellY = pos.y / 50;

    // check whether the mouse click on the valid position
    if ((cellX > 0 && cellX < this.size - 1) 
        && (cellY > 0 && cellY < this.size - 1)) {
      Cell currentCell = this.cells.get(cellY).get(cellX);
      if (currentCell.ableToClick()) {
        if (this.player1Round) {
          this.cells.get(cellY).set(cellX, new Player1Cell(currentCell.getAbove(), 
              currentCell.getBelow(), currentCell.getLeft(), currentCell.getRight()));
          this.setNeighbour();
          this.player1moves ++;
          this.player1Round = false;
        }

        else {
          this.cells.get(cellY).set(cellX, new Player2Cell(currentCell.getAbove(), 
              currentCell.getBelow(), currentCell.getLeft(), currentCell.getRight()));
          this.setNeighbour();
          this.player2moves ++;
          this.player1Round = true;
        }
      }
    }
  }

  // reset the game when press r
  public void onKeyEvent(String k) {
    if (k.equals("r")) {
      this.cells = this.generateArrayList();
      this.setNeighbour();
    }
  }

  // place end of world scene
  public WorldScene lastScene(String msg) {
    WorldImage winthegame;
    // return the image for player1 win
    if (this.player1Win()) {
      winthegame = new TextImage(msg, 24, FontStyle.BOLD, Color.MAGENTA);
    }

    // return the image for player2 win
    else {
      winthegame = new TextImage(msg, 24, FontStyle.BOLD, Color.pink);
    }

    WorldScene scene = new WorldScene(800, 800);
    scene.placeImageXY(winthegame, 400, 400);
    return scene;
  }

  // check whether player 1 win
  public boolean player1Win() { 
    boolean output = false;
    for (int i = 1; i < this.size; i += 2) {
      output = output || this.player1BelowSort(this.cells.get(0).get(i));
    }
    return output;
  }


  // check whether player2 win
  public boolean player2win() {
    boolean output = false;
    for (int i = 1; i < this.size; i += 2) {
      output = output || this.player2RightSort(this.cells.get(i).get(0));
    }
    return output;
  }

  public boolean player1AboveSort(Cell c) {
    if (c.isEmpty()) {
      return false;
    }

    else {
      if (c.player1linkOrNot()) {
        return (this.player1AboveSort(c.getAbove())
            || this.player1LeftSort(c.getLeft())
            || this.player1RightSort(c.getRight()));
      }

      else {
        return false;
      }
    }
  }

  public boolean player1BelowSort(Cell c) {
    if (c.isEmpty()) {
      return true;
    }

    else {
      if (c.player1linkOrNot()) {
        return (this.player1BelowSort(c.getBelow()) 
            || this.player1LeftSort(c.getLeft())
            || this.player1RightSort(c.getRight()));
      }

      else {
        return false;
      }
    }
  }

  public boolean player1LeftSort(Cell c) {
    if (c.player1linkOrNot()) {
      return (this.player1BelowSort(c.getBelow()) 
          || this.player1AboveSort(c.getAbove())
          || this.player1LeftSort(c.getLeft()));
    }

    else {
      return false;
    }
  }

  public boolean player1RightSort(Cell c) {
    if (c.player1linkOrNot()) {
      return (this.player1BelowSort(c.getBelow()) 
          || this.player1AboveSort(c.getAbove())
          || this.player1RightSort(c.getRight()));
    }

    else {
      return false;
    }
  }


  public boolean player2AboveSort(Cell c) {
    if (c.player2linkOrNot()) {
      return (this.player2AboveSort(c.getAbove())
          || this.player2LeftSort(c.getLeft())
          || this.player2RightSort(c.getRight()));
    }

    else {
      return false;
    }
  }

  public boolean player2BelowSort(Cell c) {
    if (c.player2linkOrNot()) {
      return (this.player2BelowSort(c.getBelow()) 
          || this.player2LeftSort(c.getLeft())
          || this.player2RightSort(c.getRight()));
    }

    else {
      return false;
    }
  }

  public boolean player2LeftSort(Cell c) {
    if (c.isEmpty()) {
      return false;
    }

    else {
      if (c.player2linkOrNot()) {
        return (this.player2BelowSort(c.getBelow()) 
            || this.player2AboveSort(c.getAbove())
            || this.player2LeftSort(c.getLeft()));
      }

      else {
        return false;
      }
    }
  }

  public boolean player2RightSort(Cell c) {
    if (c.isEmpty()) {
      return true;
    }

    else {
      if (c.player2linkOrNot()) {
        return (this.player2RightSort(c.getRight()) 
            || this.player2AboveSort(c.getAbove())
            || this.player2BelowSort(c.getBelow()));
      }

      else {
        return false;
      }
    }
  }

  // draw all the cells in a worldScene
  public void draw(WorldScene acc) {
    acc.placeImageXY(this.pic(), (50 * this.size) / 2, (50 * this.size) / 2);
  }


  // draw the scene of the BridgIt game
  public WorldScene makeScene() {
    WorldScene scc = new WorldScene(50 * this.size, 50 * this.size);
    if (!(size % 2 == 1 && size >= 3)) {
      scc.placeImageXY(new TextImage("Invalid Input!", this.size * 3, Color.red),
          (50 * this.size) / 2, (50 * this.size) / 2);
    }
    else {
      this.draw(scc);
    }
    return scc;
  }

}

// examples for BridgIt game
class ExampleBridgIt {
  void testGame(Tester t) {
    BridgIt g = new BridgIt(11);
    g.bigBang(800, 800);
  }

  ArrayList<Cell> loc1 = new ArrayList<Cell>(Arrays.asList(new WhiteCell(), 
      new Player1Cell(), new WhiteCell()));
  ArrayList<Cell> loc2 = new ArrayList<Cell>(Arrays.asList(new Player2Cell(), 
      new WhiteCell(), new Player2Cell()));
  ArrayList<Cell> loc3 = new ArrayList<Cell>(Arrays.asList(new WhiteCell(),
      new Player1Cell(), new WhiteCell()));
  ArrayList<ArrayList<Cell>> loloc =
      new ArrayList<ArrayList<Cell>>(Arrays.asList(this.loc1, this.loc2, this.loc3));
  ArrayList<Cell> locx = new ArrayList<Cell>(Arrays.asList(new WhiteCell(), 
      new WhiteCell(), new WhiteCell()));
  ArrayList<Cell> locy = new ArrayList<Cell>(Arrays.asList(new WhiteCell(), 
      new WhiteCell(), new WhiteCell()));
  ArrayList<Cell> locz = new ArrayList<Cell>(Arrays.asList(new WhiteCell(), 
      new WhiteCell(), new WhiteCell()));
  ArrayList<ArrayList<Cell>> loloc1 = 
      new ArrayList<ArrayList<Cell>>(Arrays.asList(this.locx, this.locy, this.locz));

  Cell c1;
  Cell c2;
  Cell c3;
  Cell c4;
  Cell c5;

  Cell P1Linked1 = new MptCell();
  Cell P1Linked2 = new MptCell();
  Cell P1Linked3 = new MptCell();
  Cell P1Linked4 = new MptCell();
  Cell P1Linked5 = new MptCell();
  Cell P1Linked6 = new MptCell();
  Cell P1Linked7 = new MptCell();
  Cell P1Linked8 = new MptCell();
  Cell P1Linked9 = new MptCell();
  Cell P1Linked10 = new MptCell();
  Cell P1Linked11 = new MptCell();
  Cell P1Linked12 = new MptCell();
  Cell P1Linked13 = new MptCell();
  Cell P1Linked14 = new MptCell();
  Cell P1Linked15 = new MptCell();
  Cell P1Linked16 = new MptCell();
  Cell P1Linked17 = new MptCell();
  Cell P1Linked18 = new MptCell();
  Cell P1Linked19 = new MptCell();
  Cell P1Linked20 = new MptCell();
  Cell P1Linked21 = new MptCell();
  Cell P1Linked22 = new MptCell();
  Cell P1Linked23 = new MptCell();
  Cell P1Linked24 = new MptCell();
  Cell P1Linked25 = new MptCell();

  Cell P2Linked1 = new MptCell();
  Cell P2Linked2 = new MptCell();
  Cell P2Linked3 = new MptCell();
  Cell P2Linked4 = new MptCell();
  Cell P2Linked5 = new MptCell();
  Cell P2Linked6 = new MptCell();
  Cell P2Linked7 = new MptCell();
  Cell P2Linked8 = new MptCell();
  Cell P2Linked9 = new MptCell();
  Cell P2Linked10 = new MptCell();
  Cell P2Linked11 = new MptCell();
  Cell P2Linked12 = new MptCell();
  Cell P2Linked13 = new MptCell();
  Cell P2Linked14 = new MptCell();
  Cell P2Linked15 = new MptCell();
  Cell P2Linked16 = new MptCell();
  Cell P2Linked17 = new MptCell();
  Cell P2Linked18 = new MptCell();
  Cell P2Linked19 = new MptCell();
  Cell P2Linked20 = new MptCell();
  Cell P2Linked21 = new MptCell();
  Cell P2Linked22 = new MptCell();
  Cell P2Linked23 = new MptCell();
  Cell P2Linked24 = new MptCell();
  Cell P2Linked25 = new MptCell();

  ArrayList<Cell> ArrP1Linked1;
  ArrayList<Cell> ArrP1Linked2;
  ArrayList<Cell> ArrP1Linked3;
  ArrayList<Cell> ArrP1Linked4;
  ArrayList<Cell> ArrP1Linked5;
  ArrayList<ArrayList<Cell>> ArrArrP1Linked;

  ArrayList<Cell> ArrP2Linked1;
  ArrayList<Cell> ArrP2Linked2;
  ArrayList<Cell> ArrP2Linked3;
  ArrayList<Cell> ArrP2Linked4;
  ArrayList<Cell> ArrP2Linked5;
  ArrayList<ArrayList<Cell>> ArrArrP2Linked;

  WorldImage bgI = new RectangleImage(40, 40, OutlineMode.OUTLINE, Color.WHITE);

  WorldScene ws1 = new WorldScene(150, 150);
  WorldScene ws2 = new WorldScene(150, 150);
  WorldScene ws3 = new WorldScene(150, 150);
  WorldScene ws4 = new WorldScene(150, 150);
  WorldScene ws5 = new WorldScene(150, 150);
  WorldScene ws6 = new WorldScene(150, 150);
  WorldScene ws7 = new WorldScene(50, 50);
  WorldScene ws8 = new WorldScene(200, 200);
  WorldScene ws9 = new WorldScene(550, 550);

  BridgIt g1;
  BridgIt g2;
  BridgIt g3;
  BridgIt g4;
  BridgIt g5;
  BridgIt gError;
  BridgIt gError1;
  BridgIt gP1Linked;
  BridgIt gP1Test;
  BridgIt gP2Linked;

  WorldScene scene1 = new WorldScene(800, 800);
  WorldScene scene2 = new WorldScene(800, 800);

  // initialize the data
  void initData() {

    this.c1 = new MptCell();
    this.c2 = new WhiteCell(new MptCell(), this.c4, this.c1, this.c4);
    this.c3 = new Player1Cell(new MptCell(), new MptCell(), new MptCell(), this.c2);
    this.c4 = new Player1Cell(this.c2, new MptCell(), new MptCell(), new MptCell());
    this.c5 = new Player2Cell(this.c2, new MptCell(), new MptCell(), new MptCell());

    this.P1Linked1 = new WhiteCell(new MptCell(), this.P1Linked6,
        new MptCell(), this.P1Linked2);
    this.P1Linked2 = new Player1Cell(new MptCell(), this.P1Linked7,
        this.P1Linked1, this.P1Linked3);
    this.P1Linked3 = new WhiteCell(new MptCell(), this.P1Linked8, 
        this.P1Linked2, this.P1Linked4);
    this.P1Linked4 = new Player1Cell(new MptCell(), this.P1Linked9, 
        this.P1Linked3, this.P1Linked5);
    this.P1Linked5 = new WhiteCell(new MptCell(), this.P1Linked10, 
        this.P1Linked4, new MptCell());
    this.P1Linked6 = new Player2Cell(this.P1Linked1, this.P1Linked11,
        new MptCell(), this.P1Linked7);
    this.P1Linked7 = new WhiteCell(this.P1Linked2, this.P1Linked12,
        this.P1Linked6, this.P1Linked8);
    this.P1Linked8 = new Player2Cell(this.P1Linked3, this.P1Linked13,
        this.P1Linked7, this.P1Linked9);
    this.P1Linked9 = new Player1Cell(this.P1Linked4, this.P1Linked14, 
        this.P1Linked8, this.P1Linked10);
    this.P1Linked10 = new Player2Cell(this.P1Linked5, this.P1Linked15,
        this.P1Linked9, new MptCell());
    this.P1Linked11 = new WhiteCell(this.P1Linked6, this.P1Linked16, 
        new MptCell(), this.P1Linked12);
    this.P1Linked12 = new Player1Cell(this.P1Linked7, this.P1Linked17,
        this.P1Linked11, this.P1Linked13);
    this.P1Linked13 = new Player1Cell(this.P1Linked8, this.P1Linked18, 
        this.P1Linked12, this.P1Linked14);
    this.P1Linked14 = new Player1Cell(this.P1Linked9, this.P1Linked19,
        this.P1Linked13, this.P1Linked15);
    this.P1Linked15 = new WhiteCell(this.P1Linked10, this.P1Linked20,
        this.P1Linked14, new MptCell());
    this.P1Linked16 = new Player2Cell(this.P1Linked11, this.P1Linked21,
        new MptCell(), this.P1Linked17);
    this.P1Linked17 = new Player1Cell(this.P1Linked12, this.P1Linked22, 
        this.P1Linked16, this.P1Linked18);
    this.P1Linked18 = new Player2Cell(this.P1Linked13, this.P1Linked23, 
        this.P1Linked17, this.P1Linked19);
    this.P1Linked19 = new Player1Cell(this.P1Linked14, this.P1Linked24,
        this.P1Linked18, this.P1Linked20);
    this.P1Linked20 = new Player2Cell(this.P1Linked15, this.P1Linked25, 
        this.P1Linked19, new MptCell());
    this.P1Linked21 = new WhiteCell(this.P1Linked16, new MptCell(), 
        new MptCell(), this.P1Linked22);
    this.P1Linked22 = new Player1Cell(this.P1Linked17, new MptCell(), 
        this.P1Linked21, this.P1Linked23);
    this.P1Linked23 = new WhiteCell(this.P1Linked18, new MptCell(), 
        this.P1Linked22, this.P1Linked24);
    this.P1Linked24 = new Player1Cell(this.P1Linked19, new MptCell(),
        this.P1Linked23, this.P1Linked25);
    this.P1Linked25 = new WhiteCell(this.P1Linked20, new MptCell(), 
        this.P1Linked24, new MptCell());

    this.P2Linked1 = new WhiteCell(new MptCell(), this.P2Linked6,
        new MptCell(), this.P2Linked2);
    this.P2Linked2 = new Player1Cell(new MptCell(), this.P2Linked7, 
        this.P2Linked1, this.P2Linked3);
    this.P2Linked3 = new WhiteCell(new MptCell(), this.P2Linked8,
        this.P2Linked2, this.P2Linked4);
    this.P2Linked4 = new Player1Cell(new MptCell(), this.P2Linked9, 
        this.P2Linked3, this.P2Linked5);
    this.P2Linked5 = new WhiteCell(new MptCell(), this.P2Linked10, 
        this.P2Linked4, new MptCell());
    this.P2Linked6 = new Player2Cell(this.P2Linked1, this.P2Linked11, 
        new MptCell(), this.P2Linked7);
    this.P2Linked7 = new Player2Cell(this.P2Linked2, this.P2Linked12, 
        this.P2Linked6, this.P2Linked8);
    this.P2Linked8 = new Player2Cell(this.P2Linked3, this.P2Linked13,
        this.P2Linked7, this.P2Linked9);
    this.P2Linked9 = new Player2Cell(this.P2Linked4, this.P2Linked14, 
        this.P2Linked8, this.P2Linked10);
    this.P2Linked10 = new Player2Cell(this.P2Linked5, this.P2Linked15,
        this.P2Linked9, new MptCell());
    this.P2Linked11 = new WhiteCell(this.P2Linked6, this.P2Linked16, 
        new MptCell(), this.P2Linked12);
    this.P2Linked12 = new Player1Cell(this.P2Linked7, this.P2Linked17, 
        this.P2Linked11, this.P2Linked13);
    this.P2Linked13 = new Player2Cell(this.P2Linked8, this.P2Linked18,
        this.P2Linked12, this.P2Linked14);
    this.P2Linked14 = new Player1Cell(this.P2Linked9, this.P2Linked19, 
        this.P2Linked13, this.P2Linked15);
    this.P2Linked15 = new WhiteCell(this.P2Linked10, this.P2Linked20, 
        this.P2Linked14, new MptCell());
    this.P2Linked16 = new Player2Cell(this.P2Linked11, this.P2Linked21, 
        new MptCell(), this.P2Linked17);
    this.P2Linked17 = new WhiteCell(this.P2Linked12, this.P2Linked22,
        this.P2Linked16, this.P2Linked18);
    this.P2Linked18 = new Player2Cell(this.P2Linked13, this.P2Linked23,
        this.P2Linked17, this.P2Linked19);
    this.P2Linked19 = new Player2Cell(this.P2Linked14, this.P2Linked24, 
        this.P2Linked18, this.P2Linked20);
    this.P2Linked20 = new Player2Cell(this.P2Linked15, this.P2Linked25, 
        this.P2Linked19, new MptCell());
    this.P2Linked21 = new WhiteCell(this.P2Linked16, new MptCell(),
        new MptCell(), this.P2Linked22);
    this.P2Linked22 = new Player1Cell(this.P2Linked17, new MptCell(), 
        this.P2Linked21, this.P2Linked23);
    this.P2Linked23 = new WhiteCell(this.P2Linked18, new MptCell(), 
        this.P2Linked22, this.P2Linked24);
    this.P2Linked24 = new Player1Cell(this.P2Linked19, new MptCell(), 
        this.P2Linked23, this.P2Linked25);
    this.P2Linked25 = new WhiteCell(this.P2Linked20, new MptCell(),
        this.P2Linked24, new MptCell());

    this.ArrP2Linked1 = new ArrayList<Cell>(Arrays.asList(this.P2Linked1,
        this.P2Linked2, this.P2Linked3, this.P2Linked4, this.P2Linked5));
    this.ArrP2Linked2 = new ArrayList<Cell>(Arrays.asList(this.P2Linked6,
        this.P2Linked7, this.P2Linked8, this.P2Linked9, this.P2Linked10));
    this.ArrP2Linked3 = new ArrayList<Cell>(Arrays.asList(this.P2Linked11,
        this.P2Linked12, this.P2Linked13, this.P2Linked14, this.P2Linked15));
    this.ArrP2Linked4 = new ArrayList<Cell>(Arrays.asList(this.P2Linked16,
        this.P2Linked17, this.P2Linked18, this.P2Linked19, this.P2Linked20));
    this.ArrP2Linked5 = new ArrayList<Cell>(Arrays.asList(this.P2Linked21, 
        this.P2Linked22, this.P2Linked23, this.P2Linked24, this.P2Linked25));
    this.ArrArrP2Linked = new ArrayList<ArrayList<Cell>>(Arrays.asList(this.ArrP2Linked1, 
        this.ArrP2Linked2, this.ArrP2Linked3, this.ArrP2Linked4, this.ArrP2Linked5));

    this.ArrP1Linked1 = new ArrayList<Cell>(Arrays.asList(this.P1Linked1,
        this.P1Linked2, this.P1Linked3, this.P1Linked4, this.P1Linked5));
    this.ArrP1Linked2 = new ArrayList<Cell>(Arrays.asList(this.P1Linked6, 
        this.P1Linked7, this.P1Linked8, this.P1Linked9, this.P1Linked10));
    this.ArrP1Linked3 = new ArrayList<Cell>(Arrays.asList(this.P1Linked11,
        this.P1Linked12, this.P1Linked13, this.P1Linked14, this.P1Linked15));
    this.ArrP1Linked4 = new ArrayList<Cell>(Arrays.asList(this.P1Linked16,
        this.P1Linked17, this.P1Linked18, this.P1Linked19, this.P1Linked20));
    this.ArrP1Linked5 = new ArrayList<Cell>(Arrays.asList(this.P1Linked21,
        this.P1Linked22, this.P1Linked23, this.P1Linked24, this.P1Linked25));
    this.ArrArrP1Linked =
        new ArrayList<ArrayList<Cell>>(Arrays.asList(this.ArrP1Linked1, this.ArrP1Linked2,
            this.ArrP1Linked3, this.ArrP1Linked4, this.ArrP1Linked5));

    this.g1 = new BridgIt(11);
    this.g2 = new BridgIt(3, this.loloc);
    this.g3 = new BridgIt(3, loloc1);
    this.g4 = new BridgIt(3, new ArrayList<ArrayList<Cell>>());
    this.g5 = new BridgIt(3);
    this.gError = new BridgIt(1, new ArrayList<ArrayList<Cell>>());
    this.gError1 = new BridgIt(4, new ArrayList<ArrayList<Cell>>());
    this.gP1Linked = new BridgIt(5, this.ArrArrP1Linked);
    this.gP1Test = new BridgIt(5);
    this.gP2Linked = new BridgIt(5, this.ArrArrP2Linked);

  }

  // test for neighbor of cell
  void testNeighbour(Tester t) {
    this.initData();
    t.checkExpect(this.g1.cells.get(0).get(0).getRight(), this.g1.cells.get(0).get(1));
    t.checkExpect(this.g1.cells.get(0).get(0).getLeft(), new MptCell());
    t.checkExpect(this.g1.cells.get(0).get(0).getAbove(), new MptCell());
    t.checkExpect(this.g1.cells.get(0).get(0).getBelow(), this.g1.cells.get(1).get(0));
    t.checkExpect(this.g1.cells.get(5).get(6).getAbove(), this.g1.cells.get(4).get(6));
    t.checkExpect(this.g1.cells.get(5).get(6).getBelow(), this.g1.cells.get(6).get(6));
    t.checkExpect(this.g1.cells.get(5).get(6).getLeft(), this.g1.cells.get(5).get(5));
    t.checkExpect(this.g1.cells.get(5).get(6).getRight(), this.g1.cells.get(5).get(7));
  }

  // test for drawAt
  void testdrawAt(Tester t) {
    this.initData();
    t.checkException(new RuntimeException("cannot draw empty cell"), new MptCell(),
        "drawAt", 1, 2, this.bgI);
    t.checkExpect(this.c2.drawAt(1, 3, bgI), 
        (new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
            (new RegularPolyImage(50, 4, OutlineMode.SOLID, Color.WHITE)),
            - 50, - 150, this.bgI)));
    t.checkExpect(this.c3.drawAt(1, 3, bgI),
        (new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
            (new RegularPolyImage(50, 4, OutlineMode.SOLID, Color.magenta)),
            - 50, - 150, this.bgI)));
    t.checkExpect(this.c5.drawAt(2, 3, bgI), 
        (new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
            (new RegularPolyImage(50, 4, OutlineMode.SOLID, Color.pink)),
            - 100, - 150, this.bgI)));
  }

  // test for getAbove
  void testgetAbove(Tester t) {
    this.initData();
    t.checkExpect(this.g1.cells.get(0).get(0).getAbove(), new MptCell());
    t.checkExpect(this.g1.cells.get(5).get(6).getAbove(), this.g1.cells.get(4).get(6));
    t.checkExpect(this.g1.cells.get(2).get(1).getAbove(), this.g1.cells.get(1).get(1));
    t.checkException(new RuntimeException("cannot get above"), new MptCell(),
        "getAbove");
  }

  // test for getBelow
  void testgetBelow(Tester t) {
    this.initData();
    t.checkExpect(this.g1.cells.get(0).get(0).getBelow(), this.g1.cells.get(1).get(0));
    t.checkExpect(this.g1.cells.get(5).get(6).getBelow(), this.g1.cells.get(6).get(6));
    t.checkExpect(this.g1.cells.get(10).get(1).getBelow(), new MptCell());
    t.checkException(new RuntimeException("cannot get below"), new MptCell(),
        "getBelow");
  }

  // test for getLeft
  void testgetLeft(Tester t) {
    this.initData();
    t.checkExpect(this.g1.cells.get(0).get(0).getLeft(), new MptCell());
    t.checkExpect(this.g1.cells.get(5).get(6).getLeft(), this.g1.cells.get(5).get(5));
    t.checkExpect(this.g1.cells.get(5).get(1).getLeft(), this.g1.cells.get(5).get(0));
    t.checkException(new RuntimeException("cannot get left"), new MptCell(),
        "getLeft");
  }

  // test for getRight
  void testgetRight(Tester t) {
    this.initData();
    t.checkExpect(this.g1.cells.get(10).get(10).getRight(), new MptCell());
    t.checkExpect(this.g1.cells.get(5).get(6).getRight(), this.g1.cells.get(5).get(7));
    t.checkExpect(this.g1.cells.get(2).get(1).getRight(), this.g1.cells.get(2).get(2));
    t.checkException(new RuntimeException("cannot get right"), new MptCell(),
        "getRight");
  }

  // tset for setNeighbAbove
  void testsetNeighbAbove(Tester t) {
    this.initData();
    this.c3.setNeighbAbove(this.c4);
    t.checkExpect(this.c3.getAbove(), this.c4);
    t.checkException(new RuntimeException("cannot set neighbour"), new MptCell(),
        "setNeighbAbove", this.c4);
    this.c2.setNeighbAbove(this.c4);
    t.checkExpect(this.c2.getAbove(), this.c4);
    this.c5.setNeighbAbove(this.c4);
    t.checkExpect(this.c5.getAbove(), this.c4);
  }

  // test for setNeighbBelow
  void testsetNeighbBelow(Tester t) {
    this.initData();
    this.c3.setNeighbBelow(this.c3);
    t.checkExpect(this.c3.getBelow(), this.c3);
    t.checkException(new RuntimeException("cannot set neighbour"), new MptCell(),
        "setNeighbBelow", this.c3);
    this.c2.setNeighbBelow(this.c3);
    t.checkExpect(this.c3.getBelow(), this.c3);
    this.c5.setNeighbBelow(this.c3);
    t.checkExpect(this.c5.getBelow(), this.c3);
  }

  // test for setNeighbLeft
  void testsetNeighbLeft(Tester t) {
    this.initData();
    this.c3.setNeighbLeft(this.c2);
    t.checkExpect(this.c3.getLeft(), this.c2);
    t.checkException(new RuntimeException("cannot set neighbour"), new MptCell(),
        "setNeighbLeft", this.c2);
    this.c2.setNeighbLeft(this.c2);
    t.checkExpect(this.c3.getLeft(), this.c2);
    this.c5.setNeighbLeft(this.c2);
    t.checkExpect(this.c5.getLeft(), this.c2);
  }

  // test for setNeighbRight
  void testsetNeighbRight(Tester t) {
    this.initData();
    this.c3.setNeighbRight(this.c1);
    t.checkExpect(this.c3.getRight(), this.c1);
    t.checkException(new RuntimeException("cannot set neighbour"), new MptCell(),
        "setNeighbRight", this.c1);
    this.c2.setNeighbRight(this.c1);
    t.checkExpect(this.c3.getRight(), this.c1);
    this.c5.setNeighbRight(this.c1);
    t.checkExpect(this.c5.getRight(), this.c1);
  }

  // test for generateArrayList
  void testGenerateArrayList(Tester t) {
    this.initData();
    t.checkExpect(this.g2.generateArrayList().get(0).get(0), new WhiteCell());
    t.checkExpect(this.g2.generateArrayList().get(2).get(1), new Player1Cell());
    t.checkExpect(this.g2.generateArrayList().get(1).get(2), new Player2Cell());
    t.checkExpect(this.g1.generateArrayList().get(0).get(0), new WhiteCell());
    t.checkExpect(this.g1.generateArrayList().get(3).get(0), new Player2Cell());
    t.checkExpect(this.g1.generateArrayList().get(0).get(3), new Player1Cell());
  }

  // test for setNeighbour
  void testsetNeighbour(Tester t) {
    this.initData();
    this.g2.setNeighbour();
    t.checkExpect(this.g2.cells.get(2).get(2).getAbove(), this.g2.cells.get(1).get(2));
    t.checkExpect(this.g2.cells.get(2).get(2).getBelow(), new MptCell());
    t.checkExpect(this.g2.cells.get(2).get(2).getLeft(), this.g2.cells.get(2).get(1));
    t.checkExpect(this.g2.cells.get(2).get(2).getRight(), new MptCell());
    t.checkExpect(this.g2.cells.get(1).get(1).getRight(), this.g2.cells.get(1).get(2));
    t.checkExpect(this.g2.cells.get(1).get(1).getLeft(), this.g2.cells.get(1).get(0));
    t.checkExpect(this.g2.cells.get(1).get(1).getAbove(), this.g2.cells.get(0).get(1));
    t.checkExpect(this.g2.cells.get(1).get(1).getBelow(), this.g2.cells.get(2).get(1));
  }

  // test for pic
  void testpic(Tester t) {
    this.initData();
    t.checkExpect(this.g2.pic(), 
        new WhiteCell().drawAt(2, 2, new Player1Cell().drawAt(1, 2,  
            new WhiteCell().drawAt(0, 2, new Player2Cell().drawAt(2, 1, 
                new WhiteCell().drawAt(1, 1, new Player2Cell().drawAt(0, 1, 
                    new WhiteCell().drawAt(2, 0, new Player1Cell().drawAt(1, 0,  
                        new WhiteCell().drawAt(0, 0, new FrameImage(
                            new RectangleImage(150, 150, OutlineMode.SOLID, Color.WHITE))))))))))));
    t.checkExpect(this.g3.pic(), 
        new WhiteCell().drawAt(2, 2, new WhiteCell().drawAt(1, 2,  
            new WhiteCell().drawAt(0, 2, new WhiteCell().drawAt(2, 1, 
                new WhiteCell().drawAt(1, 1, new WhiteCell().drawAt(0, 1, 
                    new WhiteCell().drawAt(2, 0, new WhiteCell().drawAt(1, 0,  
                        new WhiteCell().drawAt(0, 0, new FrameImage(
                            new RectangleImage(150, 150, OutlineMode.SOLID, Color.WHITE))))))))))));
    t.checkExpect(this.g4.pic(), 
        new FrameImage(
            new RectangleImage(150, 150, OutlineMode.SOLID, Color.WHITE)));
  }

  // test for draw
  void testdraw(Tester t) {
    this.initData();
    this.g2.draw(this.ws1);
    this.ws2.placeImageXY(this.g2.pic(), 75, 75);
    t.checkExpect(this.ws1, this.ws2);

    this.g3.draw(this.ws3);
    this.ws4.placeImageXY(this.g3.pic(), 75, 75);
    t.checkExpect(this.ws3, this.ws4);

    this.g4.draw(ws5);
    this.ws6.placeImageXY(new FrameImage(
        new RectangleImage(150, 150, 
            OutlineMode.SOLID, Color.WHITE)), 75, 75);
    t.checkExpect(this.ws5, this.ws6);
  }

  // test for makeScene
  void testmakeScene(Tester t) {
    this.initData();
    this.ws7.placeImageXY(new TextImage("Invalid Input!",
        3, Color.red),
        25, 25);
    t.checkExpect(this.gError.makeScene(), this.ws7);

    this.ws8.placeImageXY(new TextImage("Invalid Input!", 
        12, Color.red), 100, 100);
    t.checkExpect(this.gError1.makeScene(), this.ws8);

    this.g2.draw(ws1);
    t.checkExpect(this.g2.makeScene(), this.ws1);

    this.g4.draw(ws2);
    t.checkExpect(this.g4.makeScene(), this.ws2);

    this.g1.draw(ws9);
    t.checkExpect(this.g1.makeScene(), this.ws9);
  }

  // test for player1linkOrNot
  void testplayer1linkOrNot(Tester t) {
    this.initData();
    t.checkException(new RuntimeException("cannot compare with non-empty cell"), this.c1,
        "player1linkOrNot");
    t.checkExpect(this.c2.player1linkOrNot(), false);
    t.checkExpect(this.c3.player1linkOrNot(), true);
    t.checkExpect(this.c4.player1linkOrNot(), true);
    t.checkExpect(this.c5.player1linkOrNot(), false);
  }

  // test for player2linkOrNot
  void testplayer2linkOrNot(Tester t) {
    this.initData();
    t.checkException(new RuntimeException("cannot compare with non-empty cell"), this.c1,
        "player2linkOrNot");
    t.checkExpect(this.c2.player2linkOrNot(), false);
    t.checkExpect(this.c3.player2linkOrNot(), false);
    t.checkExpect(this.c4.player2linkOrNot(), false);
    t.checkExpect(this.c5.player2linkOrNot(), true);
  }

  // test for isEmpty
  void testisEmpty(Tester t) {
    this.initData();
    t.checkExpect(this.c1.isEmpty(), true);
    t.checkExpect(this.c2.isEmpty(), false);
    t.checkExpect(this.c3.isEmpty(), false);
    t.checkExpect(this.c4.isEmpty(), false);
    t.checkExpect(this.c5.isEmpty(), false);
  }

  // test for ableToClick
  void testableToClick(Tester t) {
    this.initData();
    t.checkException(new RuntimeException("should not able to click on empty cell"), this.c1,
        "ableToClick");
    t.checkExpect(this.c2.ableToClick(), true);
    t.checkExpect(this.c3.ableToClick(), false);
    t.checkExpect(this.c4.ableToClick(), false);
    t.checkExpect(this.c5.ableToClick(), false);
  }

  // test for player1win
  void testplayer1win(Tester t) {
    this.initData();
    t.checkExpect(this.gP1Linked.player1Win(), true);
    t.checkExpect(this.gP1Test.player1Win(), false);
    t.checkExpect(this.g1.player1Win(), false);
  }

  // test for player1AboveSort
  void testplayer1AboveSort(Tester t) {
    this.initData();
    t.checkExpect(this.gP1Linked.player1AboveSort(this.P1Linked24), true);
    t.checkExpect(this.gP1Linked.player1AboveSort(this.P1Linked4), false);
    t.checkExpect(this.gP1Linked.player1AboveSort(this.P1Linked12), true);
    t.checkExpect(this.gP1Linked.player1AboveSort(this.P1Linked9), false);
  }

  // test for player1BelowSort
  void testplayer1BelowSort(Tester t) {
    this.initData();
    t.checkExpect(this.gP1Linked.player1BelowSort(this.P1Linked4), true);
    t.checkExpect(this.gP1Linked.player1BelowSort(this.P1Linked2), false);
    t.checkExpect(this.gP1Linked.player1BelowSort(this.P1Linked9), true);
  }

  // test for player1LeftSort
  void testplayer1LeftSort(Tester t) {
    this.initData();
    t.checkExpect(this.gP1Linked.player1LeftSort(this.P1Linked4), true);
    t.checkExpect(this.gP1Linked.player1LeftSort(this.P1Linked14), true);
    t.checkExpect(this.gP1Linked.player1LeftSort(this.P1Linked2), false);
  }

  // test for player1RightSort
  void testplayer1RightSort(Tester t) {
    this.initData();
    t.checkExpect(this.gP1Linked.player1RightSort(this.P1Linked4), true);
    t.checkExpect(this.gP1Linked.player1RightSort(this.P1Linked12), true);
    t.checkExpect(this.gP1Linked.player1RightSort(this.P1Linked2), false);
  }

  // test for player2win
  void testplayer2win(Tester t) {
    this.initData();
    t.checkExpect(this.gP2Linked.player2win(), true);
    t.checkExpect(this.gP1Test.player2win(), false);
    t.checkExpect(this.g1.player2win(), false);
  }

  // test for player2RightSort
  void testplayer2RightSort(Tester t) {
    this.initData();
    t.checkExpect(this.gP2Linked.player2RightSort(this.P2Linked13), true);
    t.checkExpect(this.gP2Linked.player2RightSort(this.P2Linked6), true);
    t.checkExpect(this.gP2Linked.player2RightSort(this.P2Linked16), false);
  }

  // test for player2LeftSort
  void testplayer2LeftSort(Tester t) {
    this.initData();
    t.checkExpect(this.gP2Linked.player2LeftSort(this.P2Linked16), false);
    t.checkExpect(this.gP2Linked.player2LeftSort(this.P2Linked19), true);
    t.checkExpect(this.gP2Linked.player2LeftSort(this.P2Linked6), false);
  }

  // test for player2AboveSort
  void testplayer2AboveSort(Tester t) {
    this.initData();
    t.checkExpect(this.gP2Linked.player2AboveSort(this.P2Linked6), true);
    t.checkExpect(this.gP2Linked.player2AboveSort(this.P2Linked18), true);
    t.checkExpect(this.gP2Linked.player2AboveSort(this.P2Linked16), false);
  }

  // test for player2BelowSort
  void testplayer2BelowSort(Tester t) {
    this.initData();
    t.checkExpect(this.gP2Linked.player2BelowSort(this.P2Linked8), true);
    t.checkExpect(this.gP2Linked.player2BelowSort(this.P2Linked6), true);
    t.checkExpect(this.gP2Linked.player2BelowSort(this.P2Linked16), false);
  }

  // test for onMouseClick
  void testonMouseClick(Tester t) {
    this.initData();
    BridgIt gp1Current = this.gP1Linked;
    this.gP1Linked.onMouseClicked(new Posn(780, 780));
    t.checkExpect(this.gP1Linked.cells, gp1Current.cells);
    this.gP1Linked.onMouseClicked(new Posn(110, 90));
    t.checkExpect(this.gP1Linked.cells, gp1Current.cells);
    this.gP1Linked.onMouseClicked(new Posn(80, 90));
    t.checkExpect(this.gP1Linked.cells.get(1).get(1),
        new Player1Cell(this.P1Linked2, this.P1Linked12,
            this.P1Linked6, this.P1Linked8));
  }

  // test for onKeyEvent
  void testonKeyEvent(Tester t) {
    this.initData();
    this.gP1Linked.onKeyEvent("r");
    t.checkExpect(this.gP1Linked.cells, this.gP1Test.cells);
    this.g2.onKeyEvent("r");
    t.checkExpect(this.g2.cells, this.g5.cells);
    this.g3.onKeyEvent("r");
    t.checkExpect(this.g3.cells, this.g5.cells);
    this.g4.onKeyEvent("r");
    t.checkExpect(this.g4.cells, this.g5.cells);
  }

  // test for lastScene
  void testlastScene(Tester t) {
    this.initData();
    this.scene1.placeImageXY(new TextImage("haha", 24, FontStyle.BOLD, Color.MAGENTA), 400, 400);
    t.checkExpect(this.gP1Linked.lastScene("haha"), this.scene1);
    this.scene2.placeImageXY(new TextImage("hoho", 24, FontStyle.BOLD, Color.MAGENTA), 400, 400);
    t.checkExpect(this.gP1Linked.lastScene("hoho"), this.scene2);
  }
}