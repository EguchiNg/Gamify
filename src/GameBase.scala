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


    //Textfields, buttons and miscellaneous
    val button = new Button {
      text = "Quick Start"
    }
    val manageButton = new Button {
      text = "Custom Start"
    }

    val ContinueButton = new Button {
      text = "Continue"
    }
    val label = new Label {
      text = "Start the game"
      horizontalAlignment = Alignment.Center
      maximumSize = new Dimension(800,100)
    }

    var combatLabel = new Label {
      horizontalAlignment = Alignment.Center
      maximumSize = new Dimension(800,100)
    }

    object TextInput extends TextField {
      columns = 10
      maximumSize = new Dimension(1600,50)
      horizontalAlignment = Alignment.Center
    }

    object InitInput extends TextField {
      columns = 10
      maximumSize = new Dimension(1600,50)
      horizontalAlignment = Alignment.Center
    }

    object CombatInput extends TextField {
      columns = 10
      maximumSize = new Dimension(1600,50)
      horizontalAlignment = Alignment.Center
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += button
      contents += label

    }

    //MENUBAR
    menuBar = new MenuBar {
      contents += new Menu("File") {
        contents += new MenuItem(Action("Open"){
          openFile
        })
        contents += new MenuItem(Action("Save"){
          saveFile
        })
        contents += new Separator
        contents += new MenuItem(Action("Exit"){
          exit
        })
      }
    }

    var player: Player = _


    listenTo(button)
    listenTo(ContinueButton)
    //Reactions to specific events given by the user.
    reactions += {
      case ButtonClicked(`button`) =>
        label.text = "Please put the size of the map you want to initialize."
        contents = new BoxPanel(Orientation.Vertical) {
          contents += label
          contents += InitInput

        }
        size = new Dimension(800, 600)
        listenTo(InitInput)
        listenTo(CombatInput)
      case ButtonClicked(`ContinueButton`) =>
        val loc = player.currentLocation
        var temp = "Monsters: <br/>"
        combatLabel.text = "Monsters: " + '\n'
        for (i <- 0 until loc.monsterAliveCount) {
          temp = temp + i + " " + loc.opponents(i).name +" HP = " + loc.opponents(i).HP + "<br/>"
        }
        combatLabel = new Label("<html>" + temp + "</html>") {
          maximumSize = new Dimension(1600,50)
          horizontalAlignment = Alignment.Center
        }
        label.text = "Input the number of the monster you want to attack"

        contents = new BoxPanel(Orientation.Vertical) {
          contents += combatLabel
          contents += label
          contents += CombatInput

        }
        size = new Dimension(800, 600)

      case EditDone(CombatInput) =>
        val loc = player.currentLocation
        val selection = loc.opponents(CombatInput.text.toInt)
        var temp1 = ""
        var temp = "Monsters: <br/>"
        temp = player.attack(selection)
        if(combatAvailable(loc)){
          temp = temp + "<br/>" + selection.attack(player)
          label.text = "<html>" + temp + "</html>"
          for (i <- 0 until loc.monsterAliveCount) {
            temp1 = temp1 + i + " " + loc.opponents(i).name +" HP = " + loc.opponents(i).HP + "<br/>"
          }
          combatLabel.text = "<html>" + temp1 + "</html>"

        }
        //Combat has ended
        else {
          combatLabel.text = ""
          label.text = "<html>"+"You have won! Please input where you want to go: forward, back, left or right. You may also look or exit.<br/>"+ temp + "</html>"
          contents = new BoxPanel(Orientation.Vertical) {
            contents += combatLabel
            contents += label
            contents += TextInput

          }
          size = new Dimension(800, 600)
          deafTo(CombatInput)
        }
        CombatInput.text = ""
        TextInput.text = ""

      case EditDone(InitInput) =>
        label.text = "Please input where you want to go: forward, back, left or right. You may also look or exit."
        initializeGame(InitInput.text.toInt)
        player = runGame()


        contents = new BoxPanel(Orientation.Vertical) {
          contents += label
          contents += TextInput

        }
        size = new Dimension(800, 600)
        deafTo(InitInput)
        listenTo(TextInput)

      case EditDone(TextInput) =>

        val temp = player.act(TextInput.text)
        player.setLocation(findLoc(player.x, player.y))
        val loc = player.currentLocation

        if(combatAvailable(loc)){
          label.text = "You have encountered some monsters!"
          listenTo(CombatInput)

          contents = new BoxPanel(Orientation.Vertical) {
            contents += ContinueButton
            contents += label

          }
          size = new Dimension(800, 600)
          TextInput.text = ""
        }
        else {
          label.text ="<html>" + temp + "<br/>" +  "Please input where you want to go: forward, back, left or right. You may also look or exit.</html>"

          TextInput.text = ""
          CombatInput.text = ""
        }

    }

    size = new Dimension(800, 600)
    centerOnScreen()

  }


  var map = ofDim[Locations](0, 0)
  var x: Int = _
  //x location of player
  var y: Int = _
  //y location of player
  var space: Int = _ //Size of the game space


  def managerInitialize() : Unit = {

  }
  //Initialize the game space.
  def initializeGame(size: Int): Unit = {
    x = 0
    y = 0
    map = ofDim(size, size)
    space = size
    fillMap()
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

  def combatAvailable(loc: Locations): Boolean = {
    if (loc.monsterAliveCount <= 0) {
      false
    }
    else {
      true
    }
  }


  //This is the main driver for running the game. Text input so far.
  def runGame(): Player = {
    var done: Boolean = false
    val player = new Player("Sat", space)
    player.setClass(new Warrior)
    player.setLocationCoord(0, 0)
    player.setLocation(findLoc(player.x, player.y))
    return player
  }

  def openFile{
    val chooser = new FileChooser
    if(chooser.showOpenDialog(null) == FileChooser.Result.Approve) {
      val source = scala.io.Source.fromFile(chooser.selectedFile)
      source.close
    }
  }

  def saveFile{
    val chooser = new FileChooser
    if(chooser.showSaveDialog(null) == FileChooser.Result.Approve) {
      val printWriter = new java.io.PrintWriter(chooser.selectedFile)
      printWriter.close()
    }
  }

  def exit{
    sys.exit(0)
  }



}
    
