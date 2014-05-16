class Locations {
  var opponents: Array[Monsters] = _
  var monsterAliveCount: Int = 0
  var description: String = _

  def addMonster(monster: Monsters) {

  }

  def changeDescription(newDescription: String) {



  }
  
  
}
class CaveRoom extends Locations {

  opponents = Array(new Goblin(1))
  monsterAliveCount = 1
  description = "This looked like the lair of the goblin."
  
  
}

class TreasureRoom extends Locations {
  description= "This room glitters with gold."
  
}

class EmptyCave extends Locations {

  description = "This room is absolutely empty."
  
  
}

class HealingFountain extends Locations {
  description= "A fountain sits in the middle of this room. You get a feeling of calm from it."
}