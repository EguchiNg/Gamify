import scala.swing.SimpleSwingApplication
import Array._

import scala.util._
import swing._
import Swing._
import event._
import ListView._




object GameBase extends SimpleSwingApplication {

  def top = new MainFrame {
    Console.println("Please input an int for the size of the game")
    title = "Gamify UI"
    val button = new Button {
      text = "Start Game"
    }
    val label = new Label {
      text = "Start the game"
    }

    object TextInput extends TextField{
      columns = 10
    }


    contents = new BoxPanel(Orientation.Vertical) {
      contents += button
      contents += label
      border = Swing.EmptyBorder(500, 30, 10, 300)
    }


    listenTo(button)
    reactions += {
      case ButtonClicked(b) => {
        label.text = "Game Started. Please input where you want to go: forward, \" +\n        \"back, left or right. You may also look or exit.\""

        contents = new BoxPanel(Orientation.Vertical) {
          contents += label
          contents += TextInput
        }
        listenTo(TextInput)

       }
      case EditDone(TextInput) =>{

        label.text = "What next?"
        TextInput.text = ""

      }


    }

     //var userInput = readInt()
    // initializeGame(userInput)


    }


  var map = ofDim[Locations](0, 0)
  var x: Int = _
  //x location of player
  var y: Int = _
  //y location of player
  var space: Int = _ //Size of the game space

  //Initialize the game space.
  def initializeGame(size: Int): Unit = {
    x = 0
    y = 0
    map = ofDim(size, size)
    space = size
    fillMap()
    runGame()
  }

  def fillMap(): Unit = {
    val rand = new Random()

    for (a <- 0 until space) {
      for (b <- 0 until space) {
        rand.nextInt(3) match {
          case 0 =>
            map(a)(b) = new CaveRoom
          case 1 =>
            map(a)(b) = new EmptyCave
          case 2 =>
            map(a)(b) = new HealingFountain
        }
      }
    }

  }

  def findLoc(xloc: Int, yloc: Int): Locations = {
    map(xloc)(yloc)

  }

  def combat(player: Player, loc: Locations): Unit = {
    if (combatAvailable(loc)) {
      Console.println("You've encountered some monsters! Input the number of the monster you want to attack")
      while (combatAvailable(loc)) {
        Console.println("Monsters:")

        for (i <- 0 until loc.monsterAliveCount) {
          Console.println(i + " " + loc.opponents(i).name)
        }
        val selection = loc.opponents(readInt())
        if (player.attack(selection)) {
          loc.monsterAliveCount -= 1
        }
        else {
          if (selection.attack(player)) {
            Console.println("You died.")
            sys.exit(0)
          }
        }
      }
      Console.println("You have won the fight!")
    }


  }

  def combatAvailable(loc: Locations): Boolean = {
    if (loc.monsterAliveCount <= 0) {
      false
    }
    else {

      true
    }
  }


  //This is the main driver for running the game. Text input so far.
  def runGame(): Unit = {
    var done: Boolean = false
    var player = new Player("Sat", space)
    player.setClass(new Warrior)
    player.setLocationCoord(0, 0)
    player.setLocation(findLoc(player.x, player.y))

    while (true) {
      Console.println("Please input where you want to go: forward, " +
        "back, left or right. You may also look or exit.")
      player.act(readLine())
      player.setLocation(findLoc(player.x, player.y))
      combat(player, player.currentLocation)
      Console.println()
    }
  }




}
    
