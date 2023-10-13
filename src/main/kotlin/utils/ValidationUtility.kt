package utils

import java.util.regex.Pattern
   fun isValidGender(gender: Char): Boolean {
        return when (gender) {
            in listOf<Char>('F', 'M', 'O') -> {
                true
            }
            else -> false
        }
    }

    fun validGender(genderToValidate: Char): Char {
        if (genderToValidate.uppercaseChar() in listOf('F', 'M', 'O'))
            return genderToValidate
        else
            return ' '
    }
    fun isValidInRange(minValue: Double, maxValue: Double, valueToCheck: Double): Boolean{
        return (valueToCheck >= minValue) && (valueToCheck <= maxValue)
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
        return Pattern.matches(emailRegex, email)
    }