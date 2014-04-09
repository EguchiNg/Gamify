object GameBase {
  import Array._

  import scala.util._
  import swing._
  import scala._
  	
    var map = ofDim[Locations](0, 0)
    var x: Int = _; //x location of player
    var y: Int = _; //y location of player
    var space: Int = _ //Size of the game space
  
    //Initialize the game space. 
    def initializeGame(size: Int) : Unit = {
      x = 0
      y = 0
      map = ofDim(size, size)
      space = size
      fillMap()
      runGame()
    }
    
    def fillMap() : Unit = {
      val rand = new Random()
      var a = 0
      var b = 0
      for(a <- 0 until space ){
        for(b <- 0 until space){
            rand.nextInt(3) match {
            case 0 =>
              map(a)(b) = new CaveRoom
            case 1 =>
              map(a)(b) = new EmptyCave
            case 2 =>
              map(a)(b) = new HealingFountain
          }
        }
      }
        
    }
    
    def findLoc(xloc: Int, yloc: Int) : Locations = {
      map(xloc)(yloc)
      
    }
    
    def combat(player: Player, loc: Locations) : Unit = {
      if(combatAvailable(loc)){
    	  Console.println("You've encountered some monsters! Input the number of the monster you want to attack")
      while(combatAvailable(loc)) {
      Console.println("Monsters:")
      var i = 0
      for(i <- 0 until loc.monsterAliveCount ){
    	  Console.println(i + " " + loc.opponents(i).name)
      }
      	val selection = loc.opponents(readInt())
      	if(player.attack(selection)){
      	  loc.monsterAliveCount -= 1
      	}
      	else {
      	  if(selection.attack(player)){
      	    Console.println("You died.")
      	    sys.exit(0)
      	  }
      	}
      }
    	  Console.println("You have won the fight!")
      }
      
      
    }
    
    def combatAvailable(loc: Locations) : Boolean = {
      if(loc.monsterAliveCount <= 0){
        false
      }
      else{
        
        true
      }
    }
    
    
    
    //This is the main driver for running the game. Text input so far.
    def runGame() : Unit = {
      var done: Boolean = false
      var player = new Player("Sat", space)
      player.setClass(new Warrior)
      player.setLocationCoord(0, 0)
      player.setLocation(findLoc(player.x,player.y))
      
      while(!done){
        Console.println("Please input where you want to go: forward, " +
            "back, left or right")
        done = player.act(readLine)
        player.setLocation(findLoc(player.x,player.y))
        combat(player, player.currentLocation)
      }
    }

  
    
  def main(args: Array[String])  {
    Console.println("Please input an int for the size of the game")
    val userInput = readInt
    initializeGame(userInput)
    
    
  }
  
}