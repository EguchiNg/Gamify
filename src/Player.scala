import scala.util.Random

class Player(nameInput: String, space: Int) {
  var Level: Int = _
  var Experience: Int = _
  var MaxHP: Int = _
  var HP: Int = _
  var x: Int = _
  var y: Int = _
  var playerSpace: Int = space
  var name = nameInput
  var attackBonus: Int = _
  var meleeDamage: Int = 1
  var armorRating: Int = _
  var plaClass: Classes = _
  var currentLocation: Locations = _
  
  def setClass(select: Classes){
    plaClass = select
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
  def attack(monster: Monsters) : String = {
    val rand = new Random()
    //val roll = rand.nextInt(20)
    if((attackBonus + rand.nextInt(20)) >= (10 + monster.armorRating)) {
      monster.HP -= meleeDamage
      if(monster.HP <= 0) {
        currentLocation.monsterAliveCount -= 1
        return "You do " + meleeDamage + " damage to the " + monster.name + '\n' + monster.name + " dies" + '\n' + "You gain " + monster.Experience + " experience"

      }
      else
        return "You do " + meleeDamage + " damage to the "  + monster.name
    }
    else {
      return "You missed the " + monster.name
    }   

    
  }
  
  def levelUp() : Unit = {
    
  }
  
  
  def act(input: String) : String = {
      var returnString = ""
      input match {
      case "forward" => 
        if(y < space){
          y += 1
          returnString = "You go forward"
        }
        else if (y == playerSpace){
          returnString = "You hit the wall."
        }

      case "back" => 
      	if(y > 0){
          y -= 1

          returnString = "You go backwards"

        }
        else if (y == 0){
          returnString = "You hit the wall."
        }

      case "left" => 
        if(x > 0){
          x -= 1
          returnString = "You go left"

        }
        else if (x == 0){
          returnString = "You hit the wall."
        }

      case "right" => 
        if(x < playerSpace){
          x += 1
          returnString = "You go right"

        }
        else if (x == playerSpace){
          returnString = "You hit the wall."
        }
      case "look" =>
        Console.println(currentLocation.description)
      case "drink" =>
        if(currentLocation.isInstanceOf[HealingFountain]) {
          if(HP+5 < MaxHP)
            HP+=5
          else
            HP = MaxHP
            returnString = "You feel healthier."
        }
        else
          returnString = "There is nothing to drink."
      case "exit" =>
        returnString = "Goodbye."
        sys.exit(0)

      case "status" =>
        returnString = "Your HP is " + HP

      case bad =>
        returnString = "That is not an act."
    }
    return returnString
    }

}