import scala.util.Random

class Monsters {
  var name: String = _
  var level: Int = _
  var MaxHP: Int = _
  var HP: Int = _
  var Intelligence: Int = _
  var Strength: Int = _
  var Dexterity: Int = _
  var Constitution: Int = _
  var Wisdom: Int = _
  var attackBonus: Int = _
  var armorRating: Int = _
  var meleeDamage: Int = _
  var monsterClass: Classes = _
  
  
  def attack(player: Player) : Unit = {
    var rand = new Random()
    val roll = rand.nextInt(20);
    if((attackBonus + roll) >= (10 + player.armorRating)) {
      player.HP -= meleeDamage
      println(name + " hit " + player.name + " for " + meleeDamage)
    }
    else {
      println(name + " missed " + player.name)
    }   
    
  }
  

}

class Goblin(initLevel: Int) extends Monsters{
  name = "Goblin"
  level = initLevel
  MaxHP = 5
  HP = 5
  Intelligence = 8
  Strength= 10
  Dexterity= 12
  Constitution = 10
  Wisdom = 10
  attackBonus = Calculations.attackPowerStr(Strength, level, monsterClass.attackBonusMod)
  armorRating = 5
  monsterClass = new Warrior
  
  
 
  
  
  
  
    
}