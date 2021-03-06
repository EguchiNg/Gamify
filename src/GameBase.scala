import scala.swing.SimpleSwingApplication
import Array._

import scala.util._
import swing._
import Swing._
import event._
import ListView._




object GameBase extends SimpleSwingApplication {

  var gameStarted = 0
  var player: Player = _

  def top = new MainFrame {
    Console.println("Please input an int for the size of the game")
    title = "Gamify UI"



    //Textfields, buttons and miscellaneous.
    //Effective but messy way. Lots of buttons and stuff everywhere
    val button = new Button {
      text = "Quick Start"
    }
    //Button that goes into the management menu on the start screen
    val manageButton = new Button {
      text = "Custom Start"
    }
    //A simple back button
    val backButton = new Button {
      text = "back"
    }
    //A secondary back button
    val backButton2 = new Button {
      text = "back"
    }

    //Button that goes into the add location interface.
    val locationButton = new Button {
      text = "Add location"
    }

    //button to go to the add monster interface.
    val monsterButton = new Button {
      text = "Add a monster"
    }
    //Button to set the map size
    val mapButton = new Button {
      text = "Change map size"
    }
    val playButton = new Button {
      text = "Play"
    }
    val submitLocation = new Button {
      text = "submit"
    }
    val submitMonster = new Button {
      text = "submit"
    }

    val ContinueButton = new Button {
      text = "Continue"
    }

    //Standard label that displays most of our info
    val label = new Label {
      text = "Start the game"
      horizontalAlignment = Alignment.Center
      maximumSize = new Dimension(800,100)
    }

    //Label that comes into effect during combat
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
    //Text Fields for creating stuff
    object NameInput extends TextField {
      columns = 10
      maximumSize = new Dimension(1600,50)
      horizontalAlignment = Alignment.Center
    }
    object DescriptionInput extends TextField {
      columns = 10
      maximumSize = new Dimension(1600,50)
      horizontalAlignment = Alignment.Center
    }
    object XInput extends TextField {
      columns = 10
      maximumSize = new Dimension(1600,50)
      horizontalAlignment = Alignment.Center
    }
    object YInput extends TextField {
      columns = 10
      maximumSize = new Dimension(1600,50)
      horizontalAlignment = Alignment.Center
    }
    object InitInput2 extends TextField {
      maximumSize = new Dimension(1600,50)
      horizontalAlignment = Alignment.Center

    }
    object LevelInput extends TextField {
      maximumSize = new Dimension(1600,50)
      horizontalAlignment = Alignment.Center
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += button
      contents += manageButton
      contents += label
    }
    //MENUBAR
    menuBar = new MenuBar {
      contents += new Menu("File") {
        contents += new MenuItem(Action("Open"){
          player = openFile
          gameStarted = 1
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

    listenTo(button)
    listenTo(ContinueButton)
    listenTo(manageButton)
    listenTo(InitInput2)
    listenTo(backButton2)
    listenTo(monsterButton)
    listenTo(mapButton)
    listenTo(playButton)
    listenTo(submitLocation)
    listenTo(submitMonster)
    listenTo(locationButton)

    //Reactions to specific events given by the user.
    reactions += {
      case ButtonClicked(`button`) =>
        if(gameStarted == 0) {
          label.text = "Please put the size of the map you want to initialize."
          contents = new BoxPanel(Orientation.Vertical) {
            contents += label
            contents += InitInput
          }
          player = new Player("Sat", 1)

          player.setClass(new Warrior)
          gameStarted = 1
          listenTo(InitInput)
        }
        else if(gameStarted == 1) {
          initializeGame(30)
          label.text = "Please input where you want to go: forward, back, left or right. You may also look or exit."
          runGame(player)
          contents = new BoxPanel(Orientation.Vertical) {
            contents += label
            contents += TextInput

          }
          size = new Dimension(800, 600)
          deafTo(InitInput)
          listenTo(TextInput)

        }
        size = new Dimension(800, 600)
        listenTo(InitInput)
        listenTo(CombatInput)
      case ButtonClicked(`backButton2`) =>
        contents = new BoxPanel(Orientation.Vertical) {
          contents += backButton
          contents += locationButton
          contents += monsterButton
          contents += mapButton
          contents += playButton
          contents += label
        }
        size = new Dimension(800, 600)

     //Enter management interface
      case ButtonClicked(`manageButton`) =>
        //Go into the management interface
        contents = new BoxPanel(Orientation.Vertical) {
          contents += backButton
          contents += locationButton
          contents += monsterButton
          contents += mapButton
          contents += playButton
          contents += label
        }
        if(gameStarted == 0) {
          player = new Player("Sat", 1)
          player.setClass(new Warrior)
        }
        size = new Dimension(800, 600)
      //Size setter
      case ButtonClicked(`mapButton`) =>
        label.text = "Enter size of map you want"
        contents = new BoxPanel(Orientation.Vertical) {
          contents += label
          contents += backButton2
          contents += InitInput2
        }
        size = new Dimension(800, 600)
        //Location maker
      case ButtonClicked(`locationButton`)=>
        label.text = "Put x and y coords of where you want to put the location, and a name and description"
        contents = new BoxPanel(Orientation.Vertical) {
          contents += label
          contents += backButton2
          contents += XInput
          contents += YInput
          contents += DescriptionInput
          contents += submitLocation
        }
        size = new Dimension(800, 600)
      //Manager monster maker
      case ButtonClicked(`monsterButton`)=>
        label.text = "Put x and y coords of where you want to put the monster, and a name and level"
        contents = new BoxPanel(Orientation.Vertical) {
          contents += label
          contents += backButton2
          contents += XInput
          contents += YInput
          contents += NameInput
          contents += LevelInput
          contents += submitMonster
        }
        size = new Dimension(800, 600)
      case ButtonClicked(`submitMonster`) =>
        label.text = "Monster Created."
        map(XInput.text.toInt)(YInput.text.toInt).addMonster(new Monsters(NameInput.text,LevelInput.text.toInt))
      case ButtonClicked(`submitLocation`) =>
        label.text = "Location created."
        map(XInput.text.toInt)(YInput.text.toInt) = new Locations
        map(XInput.text.toInt)(YInput.text.toInt).changeDescription(DescriptionInput.text)

      case ButtonClicked(`playButton`) =>
        label.text = "Please input where you want to go: forward, back, left or right. You may also look or exit."
        contents = new BoxPanel(Orientation.Vertical) {
          contents += label
          contents += TextInput
        }
        listenTo(TextInput)
        size = new Dimension(800, 600)


      //Acknowledge the presence of MONSTERS
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
        try {
          var intSelection = CombatInput.text.toInt

          val selection = loc.opponents(intSelection)
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
        } catch {
          case e:Exception => CombatInput.text = "Please input an int"
        }


      //InitInput2 is for the manager interface, InitInput is just for the quick start.
      case EditDone(InitInput2) =>
        initializeGame(InitInput2.text.toInt)
        gameStarted = 1
        runGame(player)
        label.text = "Map created"
      case EditDone(InitInput) =>
        initializeGame(InitInput.text.toInt)
        runGame(player)
        label.text = "Please input where you want to go: forward, back, left or right. You may also look or exit."


        contents = new BoxPanel(Orientation.Vertical) {
          contents += label
          contents += TextInput

        }
        size = new Dimension(800, 600)
        deafTo(InitInput)
        listenTo(TextInput)

      //This is the main case for exploration
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


  //Initialize the game space.
  def initializeGame(size: Int): Unit = {
    x = 0
    y = 0
    map = ofDim(size, size)
    space = size
    fillMap()
  }

  //Populate the map randomly for a quick start.
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

  //Simple function that finds the location on the map that the currently occupies
  def findLoc(xloc: Int, yloc: Int): Locations = {
    map(xloc)(yloc)
  }

  //Checks to see if combat is available here.
  def combatAvailable(loc: Locations): Boolean = {
    if (loc.monsterAliveCount <= 0) {
      false
    }
    else {
      true
    }
  }


  //This is the main driver for running the game. Text input so far.
  def runGame(play : Player ): Player = {
    //var done: Boolean = false
    val playe = play
    player.setLocationCoord(0, 0)
    player.setLocation(findLoc(player.x, player.y))
    return playe
  }

  def openFile : Player = {
    val chooser = new FileChooser
    var player = new Player("placeHolder", 1)
    if(chooser.showOpenDialog(null) == FileChooser.Result.Approve) {
      val source = scala.io.Source.fromFile(chooser.selectedFile)
      var counter = 0

      for (line <- source.getLines()) {
        counter match {
          case 0 =>
            player = new Player(line, 30)
            println(line)
            counter+=1
          case 1 =>
            println(line.toInt)
            player.Level = line.toInt
            counter+=1
          case 2 =>
            val placlass = line
            println(placlass)

            //Match class to set as
            placlass match {
              case "Warrior" =>
                player.setClass(new Warrior)
              case "Wizard" =>
                player.setClass(new Wizard)
              case "Thief" =>
                player.setClass(new Thief)
            }
        }
      }
      source.close

    }
    return player
  }

  def saveFile {
    val chooser = new FileChooser
    if(chooser.showSaveDialog(null) == FileChooser.Result.Approve) {
      val writer = new java.io.PrintWriter(chooser.selectedFile)
      //Saves the state of the player. Testing version for a generic one.

      if (gameStarted == 0) {
        writer.println("name")
        writer.println("3")
        writer.println("Warrior")
      }
      //Version for regular use
      else {
        writer.println(player.name)
        writer.println(player.Level)
        writer.println(player.plaClass.name)
      }
      writer.close()
    }
  }

  def exit{
    sys.exit(0)
  }



}
    
