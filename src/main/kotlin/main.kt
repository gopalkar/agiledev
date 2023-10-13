import controllers.UserStore
import models.User
import mu.KotlinLogging
import utils.Conversion
import utils.readValidEmail
import utils.readValidGender

//var user = User()
val userStore = UserStore()
private val logger = KotlinLogging.logger {}
fun main() {
    print("Welcome to Health Tracker")

    //Some Temporary Test Data
    userStore.create((User(1, "Homer Simpson", "homer@simpson.com", 178.0, 2.0f, 'M')))
    userStore.create((User(2, "Marge Simpson", "marge@simpson.com", 140.0, 1.6f, 'F')))
    userStore.create((User(3, "Lisa Simpson", "lisa@simpson.com", 100.0, 1.2f, 'F')))
    userStore.create((User(4, "Bart Simpson", "bart@simpson.com", 80.0, 1.0f, 'M')))
    userStore.create((User(5, "Maggie Simpson", "maggie@simpson.com", 50.0, 0.7f, 'F')))

    runApp()
}

fun addUser() {
    var user = User()
    user = getUserDetails()
    userStore.create(user)
}

fun listUser() {
    //print("The user details are: ${userStore.findAll()}")
    println("The user details are:")
    userStore.findAll()
        .sortedBy { it.name }
        .forEach{println(it)}
}

fun getUserById(): User? {
    print("Enter the id of the user: ")
    return userStore.findOne(readLine()?.toIntOrNull() ?: -1)
}

fun searchById() {
    val user = getUserById()
    if ( user == null )
        //print("No User Found")
        logger.info { "Search - No user found" }
    else
        println(user)
}

fun deleteUser() {
    if ( userStore.delete(getUserById()) )
        println("User Deleted")
    else
        println("User Not Found")
}

fun updateUser() {
    listUser()
    val foundUser = getUserById()
    if ( foundUser != null ) {
        val user = getUserDetails()
        user.id = foundUser.id

        if ( userStore.updateUser(user) )
            print("User Successfully Updated")
        else
            print("User Update Failed")
    }
    else
        print("User Not Found")
}

fun getUserDetails(): User {
    val user = User()

    print("Please enter the following for the user:\n")
    print("    Name: ")
    user.name = readLine()!!

    //print("    Email: ")
    user.email = readValidEmail()

    print("    Weight: ")
    user.weight = readLine()?.toDoubleOrNull() ?: 0.0

    print("    Height: ")
    user.height = readLine()?.toFloatOrNull() ?: 0F

    //Get Gender
    user.gender = readValidGender()
    return user
}

fun searchByGender() {
    val user: List<User> = userStore.findByGender(readValidGender())
    if (user.isNotEmpty()) {
        user.sortedBy { it.name }
            .forEach{println(it)}
    }
    else
        logger.info { "No user with given gender found" }
}

fun usersReport() {

    val users = userStore.findAll()

    println("""
        |------------------------
        |     USERS REPORT
        |------------------------
        |
        |  Number of Users:  ${users.size}
        |  Gender Breakdown: ${users.groupingBy{it.gender}.eachCount()}
        |  Average Weight:   ${users.sumOf { it.weight }.div(users.size)} kg
        |  Min Weight:       ${users.minByOrNull { it.weight }?.weight} kg
        |  Max Weight:       ${users.maxByOrNull { it.weight }?.weight} kg
        |  Average Height:   ${Conversion.round(users.sumOf { it.height.toDouble() }.div(users.size), 2.0)} metres
        |  Min Height:       ${users.minByOrNull { it.height }?.height} metres
        |  Max Height:       ${users.maxByOrNull { it.height }?.height} metres
        |
        |------------------------
        |""".trimMargin())
}

fun usersImperial() {
    println("The user details (imperial) are:")
    userStore.findAll()
        .sortedBy { it.name }
        .forEach{println("  " + it.name
                + "(" + it.email + "), "
                + Conversion.convertKGtoPounds(it.weight, 1.0) + " pounds, "
                + Conversion.convertMetresToInches(it.height.toDouble(), 1.0) + " inches."
        )}
}
fun menu(): Int {
    println("""
        |Main Menu: 
        |  1. Add User
        |  2. List User
        |  3. Search by  Id
        |  4. Delete
        |  5. Update
        |  6. Search by Gender
        |  7. User Report
        |  8. List User (Imperial)
        |  0. Exit
        |Please enter your option: """.trimMargin())
    return readLine()?.toIntOrNull() ?: -1
}

fun runApp() {
    var input: Int
    do {
        input = menu()
        when(input) {
            1 -> addUser()
            2 -> listUser()
            3 -> searchById()
            4 -> deleteUser()
            5 -> updateUser()
            6 -> searchByGender()
            7 -> usersReport()
            8 -> usersImperial()
            0 -> println("Bye...")
            else -> print("Invalid Option")
        }
    }while (input != 0)
}