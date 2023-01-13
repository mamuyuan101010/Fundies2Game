import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// a class to represent a tile with a value, an x and a y position
class Tile {
  // The number on the tile.  Use 0 to represent the hole
  int value;
  int x;
  int y;

  // constructor of tile
  Tile(int value, int x, int y) {
    this.value = value;
    this.x = x;
    this.y = y;
  }

  // Draws this tile onto the background at the specified logical coordinates
  WorldImage drawAt(int col, int row, WorldImage background) {
    if (this.value == 0) {
      return (new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
          (new RegularPolyImage(30, LengthMode.SIDE, 4, OutlineMode.SOLID,
              Color.WHITE)), - (col) * 30, - (row) * 30, background));
    }

    else {
      return (new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
          (new OverlayImage(new TextImage(Integer.toString(this.value),18, Color.BLACK),
              new RegularPolyImage(30, LengthMode.SIDE, 4, OutlineMode.SOLID,
                  Color.GREEN))), - (col) * 30, - (row) * 30, background));
    }
  }
}

// represent a world class to draw the fifteen game
class FifteenGame extends World {
  // represents the rows of tiles
  ArrayList<String> keyHistory;
  ArrayList<ArrayList<Tile>> tiles;

  // constructor of fifteen game with randomly generated tiles
  FifteenGame() {
    this.keyHistory = new ArrayList<String>();
    this.tiles = this.initData();
  }

  // constructor of fifteen game with given tiles
  FifteenGame(ArrayList<ArrayList<Tile>> tiles) {
    this.keyHistory = new ArrayList<String>();
    this.tiles = tiles;
  }



  // draw every tile as a whole picture
  public WorldImage pic() {
    WorldImage whole = new FrameImage(new RectangleImage(120, 120, OutlineMode.SOLID, Color.WHITE));
    for (int i = 0; i < this.tiles.size(); i ++) {
      for (int j = 0; j < this.tiles.get(i).size(); j ++) {
        whole = this.tiles.get(i).get(j).drawAt(j, i, whole);
      }
    }
    return whole;
  }

  // return the x position of the space
  public int locateWhiteX() {
    int xvalue = 0;
    for (int i = 0; i < this.tiles.size(); i ++) {
      for (int j = 0; j < this.tiles.get(i).size(); j ++) {
        if (this.tiles.get(i).get(j).value == 0) {
          xvalue = this.tiles.get(i).get(j).x - 1;
        }
      }
    }
    return xvalue;
  }

  // get the y position of the space
  public int locateWhiteY() {
    int yvalue = 0;
    for (int i = 0; i < this.tiles.size(); i ++) {
      for (int j = 0; j < this.tiles.get(i).size(); j ++) {
        if (this.tiles.get(i).get(j).value == 0) {
          yvalue = this.tiles.get(i).get(j).y - 1;
        }
      }
    }
    return yvalue;
  }

  // replace the value of the tiles from each other of the two given array lists
  // of tiles that appear in the given index
  public void replaceListinInd(ArrayList<Tile> lot1, ArrayList<Tile> lot2, int index) {
    int tempTile1 = lot1.get(index).value;
    int tempTile2 = lot2.get(index).value;
    lot1.get(index).value = tempTile2;
    lot2.get(index).value = tempTile1;
  }

  // replace two values of the tiles to each other's position that in the given index of
  // the given list.
  public void replaceItems(int index1, int index2, ArrayList<Tile> lot) {
    int tempTile1 = lot.get(index1).value;
    int tempTile2 = lot.get(index2).value;
    lot.get(index1).value = tempTile2;
    lot.get(index2).value = tempTile1;
  }

  // draw all the tiles in a worldScene
  public void draw(WorldScene acc) {
    acc.placeImageXY(this.pic(), 60, 60);
  }

  // return the reverse command of the given one
  public String reverseRun(String k) {
    if (k.equals("up")) {
      return "down";
    }
    else if (k.equals("down")) {
      return "up";
    }
    else if (k.equals("right")) {
      return "left";
    }
    else {
      return "right";
    }
  }

  // draws the game
  public WorldScene makeScene() {
    WorldScene scc = new WorldScene(120, 120);
    this.draw(scc);
    return scc;
  }

  // check which key it input and run the corresponding command 
  public void onKeyEvent(String k) {
    if (this.win()) {
      this.endOfWorld("You Win");
    }
    
    if (k.equals("up")) {
      if (this.locateWhiteY() != 3) {
        this.replaceListinInd(this.tiles.get(this.locateWhiteY()), 
            this.tiles.get(this.locateWhiteY() + 1), this.locateWhiteX());
        this.keyHistory.add(k);
      }
    }

    else if (k.equals("down")) {
      if (this.locateWhiteY() != 0) {
        this.replaceListinInd(this.tiles.get(this.locateWhiteY()), 
            this.tiles.get(this.locateWhiteY() - 1), this.locateWhiteX());
        this.keyHistory.add(k);
      }
    }

    else if (k.equals("left")) {
      if (this.locateWhiteX() != 3) {
        this.replaceItems(this.locateWhiteX(), this.locateWhiteX() + 1, 
            this.tiles.get(this.locateWhiteY()));
        this.keyHistory.add(k);
      }
    }

    else if (k.equals("right")) {
      if (this.locateWhiteX() != 0) {
        this.replaceItems(this.locateWhiteX(), this.locateWhiteX() - 1, 
            this.tiles.get(this.locateWhiteY()));
        this.keyHistory.add(k);
      }
    }

    else if (k.equals("u")) {
      if (this.keyHistory.size() != 0) {
        int lastIndex = this.keyHistory.size() - 1;
        String prevCommand = this.keyHistory.get(lastIndex);
        this.keyHistory.remove(lastIndex);
        this.onKeyEvent(this.reverseRun(prevCommand));
        this.keyHistory.remove(lastIndex);
      }
    }
  }

  // check whether the scene has already win
  public boolean win() {
    return this.tiles.get(0).get(0).value == 1
        && this.tiles.get(0).get(1).value == 2
        && this.tiles.get(0).get(2).value == 3
        && this.tiles.get(0).get(3).value == 4
        && this.tiles.get(1).get(0).value == 5
        && this.tiles.get(1).get(1).value == 6
        && this.tiles.get(1).get(2).value == 7
        && this.tiles.get(1).get(3).value == 8
        && this.tiles.get(2).get(0).value == 9
        && this.tiles.get(2).get(1).value == 10
        && this.tiles.get(2).get(2).value == 11
        && this.tiles.get(2).get(3).value == 12
        && this.tiles.get(3).get(0).value == 13
        && this.tiles.get(3).get(1).value == 14
        && this.tiles.get(3).get(2).value == 15
        && this.tiles.get(3).get(3).value == 0;
  }

  // place end of world scene
  public WorldScene lastScene(String msg) {
    WorldImage winthegame = new TextImage(msg, 24, FontStyle.BOLD, Color.GREEN);
    WorldScene scene = new WorldScene(120, 120);
    scene.placeImageXY(winthegame, 60, 60);
    return scene;
  }

  // initialize the randomized tiles
  public ArrayList<ArrayList<Tile>> initData() {
    Random rand = new Random();
    ArrayList<Integer> a = new ArrayList<Integer>();
    while (a.size() < 16) {
      int randInt = rand.nextInt(16);
      if (! a.contains(randInt)) {
        a.add(randInt);
      }
    }
    Tile t1 = new Tile(a.get(0), 1, 1);
    Tile t2 = new Tile(a.get(1), 2, 1);
    Tile t3 = new Tile(a.get(2), 3, 1);
    Tile t4 = new Tile(a.get(3), 4, 1);
    Tile t5 = new Tile(a.get(4), 1, 2);
    Tile t6 = new Tile(a.get(5), 2, 2);
    Tile t7 = new Tile(a.get(6), 3, 2);
    Tile t8 = new Tile(a.get(7), 4, 2);
    Tile t9 = new Tile(a.get(8), 1, 3);
    Tile t10 = new Tile(a.get(9), 2, 3);
    Tile t11 = new Tile(a.get(10), 3, 3);
    Tile t12 = new Tile(a.get(11), 4, 3);
    Tile t13 = new Tile(a.get(12), 1, 4);
    Tile t14 = new Tile(a.get(13), 2, 4);
    Tile t15 = new Tile(a.get(14), 3, 4);
    Tile t16 = new Tile(a.get(15), 4, 4);
    ArrayList<Tile> a1 = new ArrayList<Tile>(Arrays.asList(t1, t2, t3, t4));
    ArrayList<Tile> a2 = new ArrayList<Tile>(Arrays.asList(t5, t6, t7, t8));
    ArrayList<Tile> a3 = new ArrayList<Tile>(Arrays.asList(t9, t10, t11, t12));
    ArrayList<Tile> a4 = new ArrayList<Tile>(Arrays.asList(t13, t14, t15, t16));
    ArrayList<ArrayList<Tile>> lolTile = 
        new ArrayList<ArrayList<Tile>>(Arrays.asList(a1, a2, a3, a4));
    return lolTile;
  }

  // check whether a list of list of tile contain the given integer as value
  public boolean containValue(ArrayList<ArrayList<Tile>> lolTile, int v) {
    boolean result = false;
    for (int i = 0; i < lolTile.size(); i ++) {
      for (int j = 0; j < lolTile.get(i).size(); j ++) {
        if (lolTile.get(i).get(j).value == v) {
          result = true;
        }
      }
    }
    return result;
  }
}

class ExampleFifteenGame { 
  void testGame(Tester t) {
    FifteenGame g = new FifteenGame();
    g.bigBang(120, 120);
  }

  Tile t1;
  Tile t2;
  Tile t3;
  Tile t4;
  Tile t5;
  Tile t6;
  Tile t7;
  Tile t8;
  Tile t9;
  Tile t10;
  Tile t11;
  Tile t12;
  Tile t13;
  Tile t14;
  Tile t15;
  Tile t15a;
  Tile t16;
  Tile t16a;

  ArrayList<Tile> l1;
  ArrayList<Tile> l2;
  ArrayList<Tile> l3;
  ArrayList<Tile> l3a;
  ArrayList<Tile> l4;
  ArrayList<Tile> l4a;
  ArrayList<Tile> l4b;
  ArrayList<Tile> l4c;

  ArrayList<ArrayList<Tile>> lot;
  ArrayList<ArrayList<Tile>> lota;
  ArrayList<ArrayList<Tile>> lotb;
  ArrayList<ArrayList<Tile>> lotc;

  FifteenGame f1;
  FifteenGame f2;
  FifteenGame f20;
  FifteenGame f3;

  WorldImage background1 = new RectangleImage(120, 120, OutlineMode.SOLID, Color.WHITE);
  WorldImage background2 = new RectangleImage(120, 120, OutlineMode.SOLID, Color.WHITE);

  WorldScene scene = new WorldScene(120, 120);
  WorldScene scene1 = new WorldScene(120, 120);
  WorldScene scene3 = new WorldScene(12, 12);
  WorldScene scene4 = new WorldScene(12, 12);
  WorldScene lastScene0 = new WorldScene(120, 120);
  WorldScene lastScene1 = new WorldScene(120, 120);

  void init() {

    this.t1 = new Tile(1, 1, 1);
    this.t2 = new Tile(2, 2, 1);
    this.t3 = new Tile(3, 3, 1);
    this.t4 = new Tile(4, 4, 1);
    this.t5 = new Tile(5, 1, 2);
    this.t6 = new Tile(6, 2, 2);
    this.t7 = new Tile(7, 3, 2);
    this.t8 = new Tile(8, 4, 2);
    this.t9 = new Tile(9, 1, 3);
    this.t10 = new Tile(10, 2, 3);
    this.t11 = new Tile(11, 3, 3);
    this.t12 = new Tile(12, 4, 3);
    this.t13 = new Tile(13, 1, 4);
    this.t14 = new Tile(14, 2, 4);
    this.t15 = new Tile(15, 3, 4);
    this.t15a = new Tile(0, 3, 4);
    this.t16 = new Tile(0, 4, 4);
    this.t16a = new Tile(15, 4, 4);

    this.l1 = new ArrayList<Tile>(Arrays.asList(this.t1, this.t2, this.t3, this.t4));
    this.l2 = new ArrayList<Tile>(Arrays.asList(this.t5, this.t6, this.t7, this.t8));
    this.l3 = new ArrayList<Tile>(Arrays.asList(this.t9, this.t10, this.t11, this.t12));
    this.l4 = new ArrayList<Tile>(Arrays.asList(this.t13, this.t14, this.t15, this.t16));
    this.l4a = new ArrayList<Tile>(Arrays.asList(this.t13, this.t14, this.t15a, this.t16a));

    this.lot = 
        new ArrayList<ArrayList<Tile>>(Arrays.asList(this.l1, this.l2, this.l3, this.l4));
    this.lota = 
        new ArrayList<ArrayList<Tile>>(Arrays.asList(this.l1, this.l2, this.l3, this.l4a));
    this.lotb = 
        new ArrayList<ArrayList<Tile>>(Arrays.asList(this.l1, this.l2, this.l3a, this.l4b));

    this.f1 = new FifteenGame(this.lot);
    this.f2 = new FifteenGame(this.lota);
    this.f20 = new FifteenGame(this.lota);
    this.f3 = new FifteenGame();

  }

  //drawAt
  void testdrawAt(Tester t) {
    this.init();
    t.checkExpect(this.t1.drawAt(1, 1, this.background1),
        (new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
            (new OverlayImage(new TextImage(Integer.toString(1),18, Color.BLACK),
                new RegularPolyImage(30, LengthMode.SIDE, 4, OutlineMode.SOLID,
                    Color.GREEN))), -30, -30, this.background1)));
    t.checkExpect(this.t2.drawAt(2, 1, this.background2),
        (new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
            (new OverlayImage(new TextImage(Integer.toString(2),18, Color.BLACK),
                new RegularPolyImage(30, LengthMode.SIDE, 4, OutlineMode.SOLID,
                    Color.GREEN))), -60, -30, this.background2)));
    t.checkExpect(this.t4.drawAt(4, 1, this.background1), 
        (new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
            (new OverlayImage(new TextImage(Integer.toString(4),18, Color.BLACK),
                new RegularPolyImage(30, LengthMode.SIDE, 4, OutlineMode.SOLID,
                    Color.GREEN))), -120, -30, this.background1)));
    t.checkExpect(this.t16.drawAt(4, 4, this.background1), 
        (new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, 
            (new RegularPolyImage(30, LengthMode.SIDE, 4, OutlineMode.SOLID,
                Color.WHITE)), -120, -120, this.background1)));
  }

  //pic
  void testpic(Tester t) {
    this.init();
    t.checkExpect(this.f1.pic(), this.t16.drawAt(3, 3, 
        this.t15.drawAt(2, 3, this.t14.drawAt(1, 3, this.t13.drawAt(0, 3, this.t12.drawAt(3, 2, 
            this.t11.drawAt(2, 2, this.t10.drawAt(1, 2, this.t9.drawAt(0, 2, this.t8.drawAt(3, 1, 
                this.t7.drawAt(2, 1, this.t6.drawAt(1, 1, this.t5.drawAt(0, 1, this.t4.drawAt(3, 0, 
                    this.t3.drawAt(2, 0, this.t2.drawAt(1, 0, this.t1.drawAt(0, 0,
                        new FrameImage(new RectangleImage(120,
                            120, OutlineMode.SOLID, Color.WHITE)))))))))))))))))));

    t.checkExpect(this.f2.pic(), this.t16a.drawAt(3, 3, 
        this.t15a.drawAt(2, 3, this.t14.drawAt(1, 3, this.t13.drawAt(0, 3, this.t12.drawAt(3, 2, 
            this.t11.drawAt(2, 2, this.t10.drawAt(1, 2, this.t9.drawAt(0, 2, this.t8.drawAt(3, 1, 
                this.t7.drawAt(2, 1, this.t6.drawAt(1, 1, this.t5.drawAt(0, 1, this.t4.drawAt(3, 0, 
                    this.t3.drawAt(2, 0, this.t2.drawAt(1, 0, this.t1.drawAt(0, 0,
                        new FrameImage(new RectangleImage(120,
                            120, OutlineMode.SOLID, Color.WHITE)))))))))))))))))));
  }

  //locateWhiteX
  void testlocateWhiteX(Tester t) {
    this.init();
    t.checkExpect(this.f1.locateWhiteX(), 3);
    t.checkExpect(this.f2.locateWhiteX(), 2);
  }

  //locateWhiteY
  void testlocateWhiteY(Tester t) {
    this.init();
    t.checkExpect(this.f1.locateWhiteY(), 3);
    t.checkExpect(this.f2.locateWhiteY(), 3);
  }

  //replaceListinInd
  void testreplaceListinInd(Tester t) {
    this.init();
    this.f1.replaceListinInd(this.l1, this.l2, 1);
    t.checkExpect(this.l1.get(1), new Tile(6, 2, 1));
    t.checkExpect(this.l2.get(1), new Tile(2, 2, 2));
  }

  //replaceItems
  void testreplaceItems(Tester t) {
    this.init();
    this.f2.replaceItems(0, 1, this.l1);
    t.checkExpect(this.l1.get(0), new Tile(2, 1, 1));
    t.checkExpect(this.l1.get(1), new Tile(1, 2, 1));
  }

  //draw
  void testdraw(Tester t) {
    this.init();
    this.f1.draw(this.scene);
    this.scene1.placeImageXY(this.f1.pic(), 60, 60);
    t.checkExpect(this.scene, this.scene1);
    this.f2.draw(this.scene3);
    this.scene4.placeImageXY(this.f2.pic(), 60, 60);
    t.checkExpect(this.scene3, this.scene4);
  }

  //reverseRun
  void testreverseRun(Tester t) {
    this.init();
    t.checkExpect(this.f1.reverseRun("up"), "down");
    t.checkExpect(this.f1.reverseRun("down"), "up");
    t.checkExpect(this.f2.reverseRun("left"), "right");
    t.checkExpect(this.f2.reverseRun("right"), "left");
  }

  //makeScene
  void testmakeScene(Tester t) {
    this.init();
    this.f1.draw(scene1);
    this.f2.draw(scene);
    t.checkExpect(this.f1.makeScene(), scene1);
    t.checkExpect(this.f2.makeScene(), scene);
  }

  //onKeyEvent
  void testonKeyEvent(Tester t) {
    this.init();
    this.f2.onKeyEvent("up");
    t.checkExpect(this.f2, this.f20);
    this.f2.onKeyEvent("down");
    t.checkExpect(this.f2.tiles.get(2).get(2).value, 0);
    t.checkExpect(this.f2.tiles.get(3).get(2).value, 11);
    this.f2.onKeyEvent("left");
    t.checkExpect(this.f2.tiles.get(2).get(2).value, 12);
    t.checkExpect(this.f2.tiles.get(2).get(3).value, 0);
    this.f2.onKeyEvent("right");
    t.checkExpect(this.f2.tiles.get(2).get(3).value, 12);
    t.checkExpect(this.f2.tiles.get(2).get(2).value, 0);
    t.checkExpect(this.f2.tiles.get(3).get(2).value, 11);
    t.checkExpect(this.f2.keyHistory,
        new ArrayList<String>(Arrays.asList("down", "left", "right")));
    this.f2.onKeyEvent("u");
    t.checkExpect(this.f2.tiles.get(2).get(2).value, 12);
    t.checkExpect(this.f2.tiles.get(2).get(3).value, 0);

  }

  //win
  void testwin(Tester t) {
    this.init();
    t.checkExpect(this.f1.win(), true);
    t.checkExpect(this.f2.win(), false);
  }

  //lastScene
  void testlastScene(Tester t) {
    this.init();
    this.lastScene0.placeImageXY(new TextImage("haha", 
        24, FontStyle.BOLD, Color.GREEN), 60, 60);
    t.checkExpect(this.f1.lastScene("haha"), this.lastScene0);
    this.lastScene1.placeImageXY(new TextImage("hoho", 
        24, FontStyle.BOLD, Color.GREEN), 60, 60);
    t.checkExpect(this.f1.lastScene("hoho"), this.lastScene1);
  }

  //initData
  void testinitData(Tester t) {
    this.init();
    t.checkExpect(this.f3.containValue(this.f1.initData(), 0), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 1), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 2), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 3), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 4), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 5), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 6), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 7), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 8), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 9), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 10), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 11), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 12), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 13), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 14), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 15), true);
    t.checkExpect(this.f3.containValue(this.f1.initData(), 16), false);
  }

  // containValue
  void testContainValue(Tester t) {
    this.init();
    t.checkExpect(this.f1.containValue(this.lot, 0), true);
    t.checkExpect(this.f1.containValue(this.lot, 14), true);
    t.checkExpect(this.f1.containValue(this.lot, 17), false);
  }
}