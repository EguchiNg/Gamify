

/**
 * Created by Anthony on 4/26/2014.
 */
class Items {
  var name: String = _
  var owner: Player = _
  var monsterOwner: Monsters = _




}

class Inventory(initSize: Int, player: Player) extends Items{
  owner = player
  var maxSize: Int = initSize
  var inventoryContents: Array[Items] = new Array(maxSize)
  var occupied: Int = 0


  def addItem(item: Items) {
    item match {
      case _: Inventory => println("Error, cannot add inventory to an inventory")
      case _ => //Any other object
        if (occupied < maxSize) {
          inventoryContents = inventoryContents :+ item //Add the item to the inventory
          item.owner = owner
          occupied+= 1
        }
        else
          Console.println("There is not enough space in inventory.")
    }
  }

  def removeItem(itemName: String) {
    val search = inventoryContents.find(s => s.name == itemName)
    search match {
      case None =>
        Console.println("Object not found")
      case Some(search) =>
        inventoryContents.take(inventoryContents.indexOf(search))
        occupied-= 1
    }
  }

} //End of inventory


class Weapons extends Items {
  var damageModifier: Int = _
  var attackBonus: Int = _


}

//End of weapons subclass

class Armor extends Items {


}

//End of armor subclass

class Consumables extends Items {


}

//End of consumables subclass

class misc extends Items {


}//End of misc



