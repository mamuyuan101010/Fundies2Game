import tester.*;     
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import javalib.worldimages.*;   
import javalib.funworld.*;      
import java.awt.Color;     

// a class to represent a fish with a scale, a color, and a location
class Fish {
  int scale;
  Color c;
  int x;
  int y;
  int direction;
  // fish constructor for user fish
  
  Fish(int scale, Color c, int x, int y) {
    this.scale = scale;
    this.c = c;
    this.x = x;
    this.y = y;
  }
  
  // fish constructor for continuing background fish, for direction
  // 0 means forward and 1 means backward
  Fish(int scale, Color c, int x, int y, int direction) {
    this.scale = scale;
    this.c = c;
    this.x = x;
    this.y = y;
    this.direction = direction;
  }
  
  // fish constructor for initializing random fish
  Fish(int y) {
    Random rand = new Random();
    this.scale = rand.nextInt(13) + 15;
    this.c = new Color(rand.nextInt(255) + 1, rand.nextInt(255) + 1, rand.nextInt(255) + 1);
    this.x = rand.nextInt(500) + 1;
    this.y = y;
    this.direction = rand.nextInt(2);
  }

  // draw the fish as its shape
  WorldImage draw(int s, Color c) {
    return new CircleImage(s, OutlineMode.SOLID, c);
  }

  // move the fish follow its direction set
  Fish move() {
    if (this.direction == 0) {
      return new Fish(this.scale, this.c, this.x + 2, this.y, this.direction);
    }
    else {
      return new Fish(this.scale, this.c, this.x - 2, this.y, this.direction);
    }
  }

  // return the fish to an oppose site if the fish is not in the range
  Fish loopTheFish() {
    if (this.x > 800) {
      return new Fish(this.scale, this.c, this.x - 800, this.y, this.direction);
    }
    else if (this.x < 0) {
      return new Fish(this.scale, this.c, this.x + 800, this.y, this.direction);
    }
    else if (this.y > 500) {
      return new Fish(this.scale, this.c, this.x, this.y - 500);
    }
    else if (this.y < 0) {
      return new Fish(this.scale, this.c, this.x, this.y + 500);
    }
    else {
      return this;
    }
  }

  // expand the fish
  Fish expand() {
    return new Fish(this.scale + 5, this.c, this.x, this.y);
  }

  // whether a fish is close to another fish
  boolean notCloseto(Fish f) {
    return Math.sqrt(Math.pow((this.x - f.x), 2) + Math.pow((this.y - f.y), 2)) 
        >= this.scale + f.scale;
  }
}

interface IList<T> {
  //filter this list by the given predicate
  IList<T> filter(Predicate<T> pred);
  
  //maps a function onto each member of the list, producing a list of the 
  //results
  <U> IList<U> map(Function<T, U> fun);
  
  //combines the items in this list using the given function
  <U> U foldr(BiFunction<T, U, U> fun, U base);
  
  // get the first item in the list
  T getFirst();
  
  // check whether the IList is empty
  boolean isEmpty();
}

class MtList<T> implements IList<T> {
  //filter this list by the given predicate
  public IList<T> filter(Predicate<T> pred) {
    return this;
  }
  
  //maps a function onto each member of the list, producing a list of the 
  //results
  public <U> IList<U> map(Function<T, U> fun) {
    return new MtList<U>();
  }
  
  //combines the items in this list using the given function
  public <U> U foldr(BiFunction<T, U, U> fun, U base) {
    return base;
  }
  
  // throw exception
  public T getFirst() {
    throw new RuntimeException("Error");
  }
  
  // return true
  public boolean isEmpty() {
    return true;
  }

}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  
  //filter this list by the given predicate
  public IList<T> filter(Predicate<T> pred) {
    if (pred.test(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }
  
  //maps a function onto each member of the list, producing a list of the 
  //results
  public <U> IList<U> map(Function<T, U> fun) {
    return new ConsList<U>(fun.apply(this.first), this.rest.map(fun));
  }
  
  //combines the items in this list using the given function
  public <U> U foldr(BiFunction<T, U, U> fun, U base) {
    return fun.apply(this.first, this.rest.foldr(fun, base));
  }
  
  // get the first item in the list
  public T getFirst() {
    return this.first;
  }
  
  // return false due to not a empty list
  public boolean isEmpty() {
    return false;
  }
}

// represent a world class to animate a list of fishes
class FishesWorld extends World {
  IList<Fish> fishes;
  Fish user;
  boolean win;
  boolean lose;
  WorldImage lostthegame;
  WorldImage winthegame;
  
  //constructor of FishesWorld
  FishesWorld(IList<Fish> fishes, Fish user) {
    this.fishes = fishes;
    this.user = user;
    this.win = false;
    this.lose = false;
    this.lostthegame = new TextImage("you lose", 24, FontStyle.BOLD, Color.RED);
    this.winthegame = new TextImage("you win", 24, FontStyle.BOLD, Color.GREEN);
  }
  
  // a biFunction to inTake a worldScene and a fish to generate the 
  // fish on the scene in the corresponding location
  public WorldScene placeImg(WorldScene acc, Fish f) {
    return acc.placeImageXY(f.draw(f.scale, f.c), f.x, f.y);
  }

  // draw the whole fish graph
  public WorldScene draw(WorldScene acc) {
    return this.fishes.foldr((x, y) -> placeImg(y, x),
        acc).placeImageXY(user.draw(this.user.scale,
            this.user.c), this.user.x, this.user.y);
  }

  // draw the lost scene
  public WorldScene makeLost() {
    return new WorldScene(800, 500).placeImageXY(this.lostthegame, 400, 250);
  }

  // draw the win scene
  public WorldScene makewin() {
    return new WorldScene(800, 500).placeImageXY(this.winthegame, 400, 250);
  }

  // reappear the list of fish from opposite site when the fish 
  // is out of range
  public IList<Fish> loopTheListOfFish(IList<Fish> lof) {
    return this.fishes.map((x) -> x.loopTheFish());
  }

  // move the background fishes, generate the fishes out of bound to their opposite 
  // site, and delete the background fishes that collide to the user fish
  public IList<Fish> move() {
    return this.fishes.map((x) -> x.loopTheFish()).map((x) -> x.move());
  }

  public IList<Fish> notCloseFilter() {
    return this.fishes.filter((x) -> x.notCloseto(this.user));
  }

  public IList<Fish> closeFilter() {
    return this.fishes.filter((x) -> ! x.notCloseto(this.user));
  }

  // onTick helper
  public FishesWorld onTickHelper() {
    if (this.fishes.isEmpty()) {
      this.win = true;
      return this;
    }
    else {
      if (this.fishes.foldr((x,y) -> x.notCloseto(this.user) 
          && y, true)) {
        return new FishesWorld(this.move(), this.user.loopTheFish());
      }
      else {
        if (this.closeFilter().getFirst().scale < this.user.scale) {
          return new FishesWorld(new FishesWorld(this.notCloseFilter(),
              this.user.loopTheFish()).move(),
              this.user.expand().loopTheFish());
        }
        else {
          this.lose = true;
          return this;
        }
      }
    }

  }

  // move the whole graph properly based on the move method
  public World onTick() {
    return onTickHelper();
  }

  // draw fishes on the background
  public WorldScene makeScene() {
    return this.draw(new WorldScene(800, 500));
  }

  // move the user fish by key
  public World onKeyEvent(String key) {
    if (key.equals("up")) {
      return new FishesWorld(this.fishes, 
          new Fish(this.user.scale, 
              this.user.c, this.user.x, this.user.y - 4));
    }
    
    else if (key.equals("down")) {
      return new FishesWorld(this.fishes, 
          new Fish(this.user.scale,
              this.user.c, this.user.x, this.user.y + 4));
    }
    
    else if (key.equals("left")) {
      return new FishesWorld(this.fishes,
          new Fish(this.user.scale,
              this.user.c, this.user.x - 4, this.user.y));
    }
    
    else if (key.equals("right")) {
      return new FishesWorld(this.fishes,
          new Fish(this.user.scale, 
              this.user.c, this.user.x + 4, this.user.y));
    }
    
    else {
      return this;
    }
  }
  
  // end the game when detected win or lose
  public WorldEnd worldEnds() {
    if (this.win) {
      return new WorldEnd(true, this.makewin());
    } 
    else if (this.lose) {
      return new WorldEnd(true, this.makeLost());
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }


}

// examples of FeedingFrenzy
class ExamplesFeedingFrenzy {

  IList<Fish> mt = new MtList<Fish>();
  Fish user = new Fish(20, Color.cyan, 400, 20);
  Fish f1 = new Fish(100);
  Fish f2 = new Fish(150);
  Fish f3 = new Fish(200);
  Fish f4 = new Fish(250);
  Fish f5 = new Fish(300);
  Fish f6 = new Fish(350);
  Fish f7 = new Fish(400);
  Fish f8 = new Fish(450);

  Fish test1 = new Fish(40, Color.CYAN, 200, 40, 0);
  Fish test2 = new Fish(30, Color.CYAN, 20, 70, 1);
  Fish test3 = new Fish(88, Color.BLUE, 250, 80, 0);
  Fish test4 = new Fish(23, Color.green, 22, 90, 1);
  Fish test5 = new Fish(22, Color.green, -22, 90, 1);
  Fish test6 = new Fish(22, Color.green, 803, 90, 1);
  Fish test7 = new Fish(22, Color.green, 300, 600, 1);
  Fish test8 = new Fish(22, Color.green, 300, -500, 1);
  Fish test9 = new Fish(22, Color.green, 303, -500, 1);
  Fish test10 = new Fish(40, Color.CYAN, 202, 40, 0);
  Fish usertest1 = new Fish(20, Color.cyan, 400, 16);
  Fish usertest2 = new Fish(20, Color.cyan, 400, 24);
  Fish usertest3 = new Fish(20, Color.cyan, 396, 20);
  Fish usertest4 = new Fish(20, Color.cyan, 404, 20);

  IList<Fish> lof1 = new ConsList<Fish>(this.f1, this.mt);
  IList<Fish> lof2 = new ConsList<Fish>(this.f2, this.lof1);
  IList<Fish> lof3 = new ConsList<Fish>(this.f3, this.lof2);
  IList<Fish> lof4 = new ConsList<Fish>(this.f4, this.lof3);
  IList<Fish> lof5 = new ConsList<Fish>(this.f5, this.lof4);
  IList<Fish> lof6 = new ConsList<Fish>(this.f6, this.lof5);
  IList<Fish> lof7 = new ConsList<Fish>(this.f7, this.lof6);
  IList<Fish> lof8 = new ConsList<Fish>(this.f8, this.lof7);
  IList<Fish> lof9 = new ConsList<Fish>(this.test1, this.mt);
  IList<Fish> lof10 = new ConsList<Fish>(this.test5, this.mt);
  IList<Fish> lof11 = new ConsList<Fish>(this.test10, this.mt);

  FishesWorld w1 = new FishesWorld(this.lof1, this.user);
  FishesWorld w2 = new FishesWorld(this.lof9, this.test1);
  FishesWorld w3 = new FishesWorld(this.mt, this.test1);
  FishesWorld w4 = new FishesWorld(this.lof9, this.test2);
  FishesWorld w5 = new FishesWorld(this.lof11, this.test2);


  WorldScene ws1 = new WorldScene(800, 500);

  //test big bang
  boolean testBigBang(Tester t) {
    FishesWorld world = new FishesWorld(this.lof8, this.user);
    int WorldHeight = 800;
    int WorldWidth = 500;
    double speed = 0.005;
    return world.bigBang(WorldHeight, WorldWidth, speed);
  }
  
  // test for moving fish and a list of fish
  boolean testMove(Tester t) {
    return t.checkExpect(this.test1.move(), new Fish(40, Color.CYAN, 202, 40, 0))
        && t.checkExpect(this.test2.move(), new Fish(30, Color.CYAN, 18, 70, 1))
        && t.checkExpect(this.test3.move(), new Fish(88, Color.BLUE, 252, 80, 0))
        && t.checkExpect(this.test4.move(), new Fish(23, Color.green, 20, 90, 1));
  }
  
  // test for draw
  boolean testDraw(Tester t) {
    return t.checkExpect(this.test1.draw(5, Color.black),
        new CircleImage(5, OutlineMode.SOLID, Color.black))
        && t.checkExpect(this.test2.draw(6, Color.yellow),
            new CircleImage(6, OutlineMode.SOLID, Color.yellow))
        && t.checkExpect(this.test3.draw(50, Color.blue),
            new CircleImage(50, OutlineMode.SOLID, Color.blue))
        && t.checkExpect(this.test4.draw(30, Color.red),
            new CircleImage(30, OutlineMode.SOLID, Color.red));
  }
  
  // test for loop the fish
  boolean testLoopofFish(Tester t) {
    return t.checkExpect(this.test1.loopTheFish(), new Fish(40, Color.CYAN, 200, 40, 0))
        && t.checkExpect(this.test5.loopTheFish(), new Fish(22, Color.green, 778, 90, 1))
        && t.checkExpect(this.test6.loopTheFish(), new Fish(22, Color.green, 3, 90, 1))
        && t.checkExpect(this.test7.loopTheFish(), new Fish(22, Color.green, 300, 100, 0))
        && t.checkExpect(this.test8.loopTheFish(), new Fish(22, Color.green, 300, 0, 0));
  }
  
  // test for expand
  boolean testExpand(Tester t) {
    return t.checkExpect(this.test2.expand(), new Fish(35, Color.CYAN, 20, 70, 0))
        && t.checkExpect(this.test3.expand(), new Fish(93, Color.BLUE, 250, 80, 0))
        && t.checkExpect(this.test4.expand(), new Fish(28, Color.green, 22, 90, 0))
        && t.checkExpect(this.test5.expand(), new Fish(27, Color.green, -22, 90, 0));
  }
  
  // test for notCloseto
  boolean testnotcloseto(Tester t) {
    return t.checkExpect(this.test8.notCloseto(this.test9), false)
        && t.checkExpect(this.test3.notCloseto(this.test4), true)
        && t.checkExpect(this.test4.notCloseto(this.test6), true);
  }
  
  // test for getFirst
  boolean testgetfirst(Tester t) {
    return t.checkExpect(this.lof4.getFirst(), this.f4)
        && t.checkExpect(this.lof3.getFirst(), this.f3)
        && t.checkException(new RuntimeException("Error"),
            this.mt, "getFirst");
  }
  
  // test for isEmpty
  boolean testisEmpty(Tester t) {
    return t.checkExpect(this.lof1.isEmpty(), false)
        && t.checkExpect(this.lof5.isEmpty(), false)
        && t.checkExpect(this.lof7.isEmpty(), false);
  }
  
  // test for placeImage
  boolean testPlaceImage(Tester t) {
    return t.checkExpect(this.w2.placeImg(this.ws1, test1),
        this.ws1.placeImageXY(test1.draw(40, Color.CYAN), 200, 40))
        && t.checkExpect(this.w1.placeImg(this.ws1, test1), 
            this.ws1.placeImageXY(test1.draw(40, Color.CYAN), 200, 40));
  }
  
  // test for draw scene
  boolean testDrawScene(Tester t) {
    return t.checkExpect(this.w2.draw(ws1), ws1.placeImageXY(this.test1.draw(40,
        Color.CYAN), 200, 40).placeImageXY(test1.draw(40, Color.CYAN), 200, 40))
        && t.checkExpect(this.w3.draw(ws1), ws1.placeImageXY(this.test1.draw(40,
            Color.CYAN), 200, 40));
  }
  
  // test for makeLost
  boolean testmakeLost(Tester t) {
    return t.checkExpect(this.w1.makeLost(),
        new WorldScene(800, 500).placeImageXY(new TextImage("you lose", 
            24, FontStyle.BOLD, Color.RED), 400, 250))
        && t.checkExpect(this.w2.makeLost(),
            new WorldScene(800, 500).placeImageXY(new TextImage("you lose", 
                24, FontStyle.BOLD, Color.RED), 400, 250));
  }
  
  // test for makeWin
  boolean testmakeWin(Tester t) {
    return t.checkExpect(this.w1.makewin(),
        new WorldScene(800, 500).placeImageXY(new TextImage("you win",
            24, FontStyle.BOLD, Color.GREEN), 400, 250))
        && t.checkExpect(this.w2.makewin(),
            new WorldScene(800, 500).placeImageXY(new TextImage("you win",
                24, FontStyle.BOLD, Color.GREEN), 400, 250));
  }
  
  // test for loop a list of fish
  boolean testlooplist(Tester t) {
    return t.checkExpect(this.w1.loopTheListOfFish(lof1), new ConsList<Fish>(f1, mt))
        && t.checkExpect(this.w3.loopTheListOfFish(lof1), new MtList<Fish>());
  }
  
  // test for move a list of fish
  boolean testmovelist(Tester t) {
    return t.checkExpect(this.w2.move(), 
        new ConsList<Fish>(new Fish(40, Color.CYAN, 202, 40, 0), this.mt))
        && t.checkExpect(this.w3.move(), new MtList<Fish>());
  }
  
  //test for not close filter
  boolean testnotCloseFilter(Tester t) {
    return t.checkExpect(this.w2.notCloseFilter(), new MtList<Fish>())
        && t.checkExpect(this.w3.notCloseFilter(), new MtList<Fish>());
  }

  // test for close filter
  boolean testCloseFilter(Tester t) {
    return t.checkExpect(this.w2.closeFilter(), 
        new ConsList<Fish>(this.test1, this.mt))
        && t.checkExpect(this.w3.closeFilter(), new MtList<Fish>());
  }

  // test for onTick helper
  boolean testOnTickHelper(Tester t) {
    return t.checkExpect(this.w4.onTickHelper(), w5)
        && t.checkExpect(this.w3.onTickHelper(), w3);
  }

  // test for onTick
  boolean testOntick(Tester t) {
    return t.checkExpect(this.w3.onTick(), w3)
        && t.checkExpect(this.w4.onTick(), w5);
  }
  
  // test for makeScene
  boolean testmakeScene(Tester t) {
    return t.checkExpect(this.w3.makeScene(), w3.draw(new WorldScene(800, 500)))
        && t.checkExpect(this.w1.makeScene(), w1.draw(new WorldScene(800, 500)))
        && t.checkExpect(this.w2.makeScene(), w2.draw(new WorldScene(800, 500)));
  }
  
  // test for onKey
  boolean testOnkey(Tester t) {
    return t.checkExpect(this.w1.onKeyEvent("up"), new FishesWorld(this.lof1, this.usertest1))
        && t.checkExpect(this.w1.onKeyEvent("down"), new FishesWorld(this.lof1, this.usertest2))
        && t.checkExpect(this.w1.onKeyEvent("left"), new FishesWorld(this.lof1, this.usertest3))
        && t.checkExpect(this.w1.onKeyEvent("right"), new FishesWorld(this.lof1, this.usertest4))
        && t.checkExpect(this.w2.onKeyEvent("g"), this.w2);
  }
  
  // test for worldEnd
  boolean testWorldEnd(Tester t) {
    w1.lose = true;
    w3.win = true;
    return t.checkExpect(this.w3.worldEnds(), 
        new WorldEnd(true, new WorldScene(800,
            500).placeImageXY(new TextImage("you win",
                24, FontStyle.BOLD, Color.GREEN), 400, 
                250)))
        && t.checkExpect(this.w1.worldEnds(), 
            new WorldEnd(true, new WorldScene(800,
                500).placeImageXY(new TextImage("you lose",
                    24, FontStyle.BOLD, Color.RED), 400, 
                    250)))
        && t.checkExpect(this.w2.worldEnds(),
            new WorldEnd(false, w2.draw(new WorldScene(800, 500))));
  }



}