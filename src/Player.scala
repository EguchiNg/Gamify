import scala.util.Random

class Player(nameInput: String, space: Int) {
  var Level: Int = _
  var Experience: Int = _
  var MaxHP: Int = _
  var HP: Int = _
  var x: Int = _;
  var y: Int = _;
  var playerSpace: Int = space;
  var name = nameInput
  var attackBonus: Int = _
  var meleeDamage: Int = 1
  var armorRating: Int = _
  var plaClass: Classes = _
  var currentLocation: Locations = _
  
  def setClass(select: Classes){
    plaClass = select;
    MaxHP = 10
    HP = 10
    attackBonus = 1
    meleeDamage = 2
    armorRating = 2
  }
  
  //Player Stats
  var Intelligence: Int = _
  var Strength: Int = _
  var Dexterity: Int = _
  var Constitution: Int = _
  var Wisdom: Int = _
  
  def setLocationCoord(xloc: Int, yloc: Int) : Unit = {
    x = xloc
    y = yloc
  }
  
  def setLocation(loc: Locations) : Unit = {
    currentLocation = loc
  }
  
  def gainExperience(amount: Int) : Unit = {
    Experience += amount
  }
  //Attack command. 
  def attack(monster: Monsters) : Boolean = {
    var rand = new Random()
    val roll = rand.nextInt(20);
    if((attackBonus + roll) >= (10 + monster.armorRating)) {
      monster.HP -= meleeDamage
      println("You do " + meleeDamage + " damage to the "  + monster.name)
    }
    else {
      println("You missed the " + monster.name)
    }   
    
    if(monster.HP <= 0){
      println(monster.name + " dies")
      println("You gain " + monster.Experience + " experience")
      gainExperience(monster.Experience)
      
      return true;
    }
    else
      return false;
    
  }
  
  def levelUp() : Unit = {
    
  }
  
  
  def act(input: String) : Unit = {
      
      input match {
      case "forward" => 
        if(y < space){
        println("You go forward")
        y += 1
        }
        else if (y == playerSpace){
          println("You hit the wall.")
        }
      case "back" => 
      	if(y > 0){
        println("You go backwards")
        y -= 1
        }
        else if (y == 0){
          println("You hit the wall.")
        }
      case "left" => 
        if(x > 0){
        println("You go left")
        x -= 1
        }
        else if (x == 0){
          println("You hit the wall.")
        }
      case "right" => 
        if(x < playerSpace){
        println("You go right")
        x += 1
        }
        else if (x == playerSpace){
          println("You hit the wall.")
        }
      case bad =>
        println("That is not an act.");
        
    }
    }

}