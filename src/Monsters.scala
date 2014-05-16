import scala.util.Random

class Monsters(name1: String, level1: Int) {
  var name: String = name1
  var level: Int = 1
  var MaxHP: Int = level*2
  var HP: Int = level*2
  var Intelligence: Int = _
  var Strength: Int = _
  var Dexterity: Int = _
  var Constitution: Int = _
  var Wisdom: Int = _
  var attackBonus: Int = 0
  var armorRating: Int = 0
  var meleeDamage: Int = 1
  var monsterClass: Classes = _
  var Experience: Int = 100*level
  
  
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

/**class Goblin(name1: String, level1: Int) extends Monsters{
  name = "Goblin"
  level = level1
  MaxHP = 5
  HP = 5
  Intelligence = 8
  Strength= 10
  Dexterity= 12
  Constitution = 10
  Wisdom = 10
  
  armorRating = -5
  monsterClass = new Warrior
  attackBonus = Calculations.attackPowerStr(Strength, level, monsterClass.attackBonusMod)
  meleeDamage = 1
  Experience = 100*level
  
  
 
  
  
  
  
    
}**/