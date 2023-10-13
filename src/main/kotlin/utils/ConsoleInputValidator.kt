package utils

    fun readValidEmail(): String {
        var email = ""
        do {
            print("    Email: ")
            email = readLine()!!
            when {
                isValidEmail(email) -> return email
                else -> print ("       Invalid Email format.  Try again.")
            }
        }while (true)
    }

    fun readValidGender(): Char {
        var gender: Char
        do {
            print("    Gender: ")
            gender = readLine()?.getOrNull(0)!!
            when {
                isValidGender(gender) -> return gender
                else -> print("Invalid Gender, please enter valid value (F-Female, M-Male, O-Other)")
            }
        } while(true)
    }