import scala.util.Random

class Player(nameInput: String, space: Int) {
  var Level: Int = 1
  var Experience: Int = 0
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

  //Set class
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

  //Set coordinate
  def setLocationCoord(xloc: Int, yloc: Int) : Unit = {
    x = xloc
    y = yloc
  }

  //A little setLocation thing
  def setLocation(loc: Locations) : Unit = {
    currentLocation = loc
  }

  //Gain experience, levelup if it gets to 1000.
  def gainExperience(amount: Int) : Unit = {
    Experience += amount
    if (Experience >= 1000)
      levelUp()
  }
  //Attack command. 
  def attack(monster: Monsters) : String = {
    val rand = new Random()
    if((attackBonus + rand.nextInt(20)) >= (10 + monster.armorRating)) {
      monster.HP -= meleeDamage
      if(monster.HP <= 0) {
        currentLocation.monsterAliveCount -= 1
        gainExperience(monster.Experience)
        return "You do " + meleeDamage + " damage to the " + monster.name + "<br/>" + monster.name + " dies" + "<br/>" + "You gain " + monster.Experience + " experience"

      }
      else
        return "You do " + meleeDamage + " damage to the "  + monster.name
    }
    else {
      return "You missed the " + monster.name
    }   

    
  }
  
  def levelUp() : Unit = {
    Level += 1
    if(Level%plaClass.attackBonusMod == 0) {
      attackBonus += 1
    }
    MaxHP += 5
    HP = MaxHP
    Experience = 0

  }
  
  //Takes in a string input and performs an action based on the player's input
  def act(input: String) : String = {
      var returnString = ""
      input match {
      case "forward" => 
        if(y < space){
          y += 1
          return "You go forward"
        }
        else if (y == playerSpace){
          return "You hit the wall."
        }

      case "back" => 
      	if(y > 0){
          y -= 1
          return "You go backwards"

        }
        else if (y == 0){
          return "You hit the wall."
        }

      case "left" => 
        if(x > 0){
          x -= 1
          return "You go left"

        }
        else if (x == 0){
          return "You hit the wall."
        }

      case "right" => 
        if(x < playerSpace){
          x += 1
          return "You go right"

        }
        else if (x == playerSpace){
          return "You hit the wall."
        }
      case "look" =>
        return currentLocation.description
      case "drink" =>
        if(currentLocation.isInstanceOf[HealingFountain]) {
          if(HP+5 < MaxHP)
            HP+=5
          else
            HP = MaxHP
            return "You feel healthier."
        }
        else
          return "There is nothing to drink."
      case "exit" =>
        return "Goodbye."
        sys.exit(0)

      case "status" =>
        return "Your HP is " + HP + " Your level is" + Level + " Your class is " + plaClass.name + " YOUR NAME IS: " + name

      case bad =>
        return "That is not an act."
    }
    return returnString
    }

}