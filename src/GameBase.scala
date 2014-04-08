object GameBase {
  import Array._;
  import scala.io.Source
  import scala.util._
  import swing._
  import java.awt.Color
  
  	
    var map = ofDim[Int](0, 0);
    var x: Int = _; //x location of player
    var y: Int = _; //y location of player
    var space: Int = _ //Size of the game space
  
    //Initialize the game space. 
    def initializeGame(size: Int) : Unit = {
      x = 0
      y = 0
      map = ofDim(size, size);
      space = size
      fillMap
      runGame
    }
    
    def fillMap() : Unit = {
      var rand = new Random()
      var a = 0
      var b = 0
      for(a <- 0 until space ){
        for(b <- 0 until space){
          map(a)(b) = rand.nextInt(5)
          println(map(a)(b))
        }
      }
        
    }
    
    
    //This is the main driver for running the game. Text input so far.
    def runGame() : Unit = {
      var done: Boolean = false
      var player = new Player("Sat", space);
      player.setLocation(x, y)
      while(!done){
        println("Please input where you want to go: forward, " +
            "back, left or right")
        player.act(readLine)
      }
    }

  
    
  def main(args: Array[String]) : Unit = {
    println("Please input an int for the size of the game")
    val userInput = readInt
    initializeGame(userInput)
    
    
  }
  
}