class Classes {
  var attackBonusMod: Int = _
  var feats: Array[Int] = _

}
class Wizard extends Classes{
  attackBonusMod = 3;
  feats = Array(1, 3, 6, 9, 12, 15, 18, 20)
  }
  

class Thief extends Classes{
  attackBonusMod = 2
  feats = Array(1, 3, 6, 9, 12, 15, 18, 20)
    
    
  }
class Warrior extends Classes{
    attackBonusMod = 1;
    feats = Array(1, 2, 3, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20)
    
  }