object GameBase {
  import Array._;
  
  class Player(input: String){
    
    var name: String = input
    var map = ofDim[Int](3, 3);
    var x = Int; //x location of player
    var y = Int; //y location of player
    
    def move(input: String) : Unit = {
    input match {
      case "up" => println("You go up");
      case "down" => println("You go down");
      case "left" => println("You go left")
      case "right" => println("You go right")
    }
  }
  }
  
  
  
  

  
    
  def main(args: Array[String]) : Unit = {
    println("This worked");
    var player = new Player("Bob")
    player.move("up")
    println(player.name)
  }
  
}