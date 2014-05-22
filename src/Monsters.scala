import scala.util.Random

class Monsters(name1: String, level1: Int) {
  var name: String = name1
  var level: Int = level1
  var MaxHP: Int = level1 * 2
  var HP: Int = level1 * 2
  var Intelligence: Int = _
  var Strength: Int = _
  var Dexterity: Int = _
  var Constitution: Int = _
  var Wisdom: Int = _
  var attackBonus: Int = level1/5
  var armorRating: Int = 0
  var meleeDamage: Int = level1 * 1
  var monsterClass: Classes = _
  var Experience: Int = 100*level1
  
  
  def attack(player: Player) : String = {
    var rand = new Random()
    val roll = rand.nextInt(20);
    if((attackBonus + roll) >= (10 + player.armorRating)) {
      player.HP -= meleeDamage
      if(player.HP <= 0) {
        return name + " has killed" + player.name
      }
      else {
        return name + " hit " + player.name + " for " + meleeDamage

      }

    }
    else {
      return name + " missed " + player.name
    }
    
  }
  

}

