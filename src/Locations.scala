class Locations {
  var opponents: Array[Monsters] = _
  var monsterAliveCount: Int = 0
  
  
  
}

class CaveRoom extends Locations {
  opponents = Array(new Goblin(1));
  monsterAliveCount = 1
  
  
}

class TreasureRoom extends Locations {
  
  
}

class EmptyCave extends Locations {
  
  
}

class HealingFountain extends Locations {
  
}