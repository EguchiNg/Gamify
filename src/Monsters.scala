class Monsters {
  var level: Int = _
  var MaxHP: Int = _
  var HP: Int = _
  var Intelligence: Int = _
  var Strength: Int = _
  var Dexterity: Int = _
  var Constitution: Int = _
  var Wisdom: Int = _
  var attackPower: Int = _
  var armorRating: Int = _
  
  

}

class Goblin extends Monsters{
  level = 1
  MaxHP = 5
  Intelligence = 8
  Strength= 10
  Dexterity= 12
  Constitution = 10
  Wisdom = 10
  attackPower = Calculations.attackPowerStr(Strength, level)
  armorRating = 5
  
  
    
}